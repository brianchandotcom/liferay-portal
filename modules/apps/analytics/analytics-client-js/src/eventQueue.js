/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ProcessLock from 'browser-tabs-lock';
import uuidv4 from 'uuid/v4';

import MessageQueue from './messageQueue';
import {
	FLUSH_INTERVAL,
	LIMIT_FAILED_ATTEMPTS,
	QUEUE_STORAGE_LIMIT,
	REQUEST_TIMEOUT,
	STORAGE_KEY_CONTEXTS,
	STORAGE_KEY_MESSAGES,
} from './utils/constants';
import {sortByEventDate} from './utils/events';
import hash from './utils/hash';
import {getItem, setItem, verifyStorageLimitForKey} from './utils/storage';

/**
 * An Analytics Event.
 *
 * @typedef {Object} AnalyticsEvent
 * @property {string} applicationId - The entity type of the event, like Page.
 * @property {string} contextHash - Hash of event context.
 * @property {string} eventDate - Server time of event in ISO 8601.
 * @property {string} eventId - The actual event like pageViewed or pageLoaded.
 * @property {string} eventLocalDate - Local time of event in ISO 8601.
 * @property {Object} properties - Extra event properties.
 */

/**
 * An Analytics Message Item.
 *
 * @typedef {Object} AnalyticsMessageItem
 * @property {Object} context - The context for all of the events in this message.
 * @property {string} dataSourceId - The Data Source ID for all of the events in this message.
 * @property {Array.<AnalyticsEvent>} events - Server time of event in ISO 8601.
 * @property {string} id - The unique identifier of this message.
 * @property {string} userId - The user id hash.
 */

/**
 * EventQueue class that is designed to accumulate events in localStorage.
 * At a specified interval, EventQueue will flush its queue of events,
 * tagged with any identifiers, to MessageQueue.
 */
class EventQueue {
	constructor(name, config) {
		this.keys = {
			contexts: STORAGE_KEY_CONTEXTS,
			eventQueue: name,
			messageQueue: STORAGE_KEY_MESSAGES,
		};

		if (!getItem(this.keys.eventQueue)) {
			setItem(this.keys.eventQueue, []);
		}

		this.analyticsInstance = config.analyticsInstance;
		this.flushDelay = config.flushInterval || FLUSH_INTERVAL;
		this.maxSize = config.maxSize || QUEUE_STORAGE_LIMIT;
		this.onFlushSuccess = config.onFlushSuccess;
		this.shouldFlush = config.shouldFlush;

		this._initializeMessageQueue();

		this._startsFlushLoop();
	}

	/**
	 * Adds an item to the queue.
	 *
	 * Note: This method has to synchronously enqueue an item or
	 * we would not be able to capture pageUnloaded events.
	 *
	 * @param {AnalyticsEvent}
	 */
	addItem(event) {
		this._enqueue(event);

		verifyStorageLimitForKey(this.keys.eventQueue, this.maxSize);
	}

	/**
	 * Add all of the context and identifier information to an event batch.
	 *
	 * @param {AnalyticsEvent}
	 * @returns {AnalyticsMessage}
	 */
	_createMessage({context, events, userId}) {
		const {channelId, ...updatedContext} = context;
		const {dataSourceId} = this.analyticsInstance.config;

		return {
			channelId,
			context: updatedContext,
			dataSourceId,
			events,
			id: uuidv4(),
			userId,
		};
	}

	/**
	 * Method for clearing any scheduled flushes.
	 */
	dispose() {
		if (this.flushInterval) {
			clearInterval(this.flushInterval);
		}

		this._messageQueue.dispose();
	}

	/**
	 * Add an event to queue in localStorage.
	 *
	 * @param {AnalyticsEvent}
	 */
	_enqueue(event) {
		const queue = this.getEvents();

		setItem(this.keys.eventQueue, [...queue, event]);
	}

	/**
	 * Flushes the event queue and sends it to the registered endpoint. If no data
	 * is pending or a flush is already in progress, the request will be ignored.
	 *
	 * Note: Because we are using a ProcessLock, no other process should
	 * be able to acquire a lock for a particular key to run its callback
	 * until the process with the active lock releases it.
	 *
	 */
	_flush() {
		if (typeof this.shouldFlush === 'function') {
			if (!this.shouldFlush()) {
				return;
			}
		}

		const events = this.getEvents();
		const {analyticsInstance} = this;

		if (!events.length) {
			return;
		}

		const lock = new ProcessLock();

		lock.acquireLock(this.keys.eventQueue).then((success) => {
			if (success) {
				const storedContexts = getItem(this.keys.contexts) || [];
				const eventsByContextHash = this._groupEventsByContextHash(
					this.getEvents()
				);

				analyticsInstance
					._getUserId()
					.then((userId) =>
						this._pushEventBatchesToMessageQueue(
							eventsByContextHash,
							storedContexts,
							userId
						)
					)
					.then(() => {
						const otherEvents = this._getEventsWithNoStoredContext(
							eventsByContextHash,
							storedContexts
						).sort(sortByEventDate);

						if (typeof this.onFlushSuccess === 'function') {
							this.onFlushSuccess(otherEvents);
						}

						this.reset(otherEvents);

						lock.releaseLock(this.keys.eventQueue);
					})
					.catch(() => {
						lock.releaseLock(this.keys.eventQueue);
					});
			}
		});
	}

	/**
	 * Returns events currently in queue.
	 *
	 * @returns {Array.<AnalyticsEvent>}
	 */
	getEvents() {
		return getItem(this.keys.eventQueue) || [];
	}

	/**
	 * Returns events that do not match any currently stored context.
	 *
	 * @param {Object}
	 * @param {Array}
	 * @returns {Object}
	 */
	_getEventsWithNoStoredContext(contextHashEventMap, storedContexts) {
		let retVal = [];

		const storedContextHashes = storedContexts.map(hash);

		for (const contextHash in contextHashEventMap) {
			if (storedContextHashes.indexOf(contextHash) === -1) {
				retVal = retVal.concat(contextHashEventMap[contextHash]);
			}
		}

		return retVal;
	}

	/**
	 * Returns an object with keys being context hash and values
	 * being events with that context hash.
	 *
	 * @example {
	 * 				1A2B3: [event, event],
	 * 				4A5B6: [event, event]
	 * 			}
	 * @param {Array.<AnalyticsEvent>}
	 * @returns {Object}
	 */
	_groupEventsByContextHash(events) {
		return events.reduce((contextEventMap, event) => {
			if (contextEventMap[event.contextHash]) {
				contextEventMap[event.contextHash].push(event);
			}
			else {
				contextEventMap[event.contextHash] = [event];
			}

			return contextEventMap;
		}, {});
	}

	/**
	 * Create member instance of MessageQueue and set processing
	 * function to send event batch messages to endpoint.
	 */
	_initializeMessageQueue() {
		const {
			client,
			config: {endpointUrl},
		} = this.analyticsInstance;

		const messageQueue = new MessageQueue(this.keys.messageQueue, {
			maxRetries: LIMIT_FAILED_ATTEMPTS,
			processFn: (item, done) => {
				return client
					.sendWithTimeout({
						payload: item,
						timeout: REQUEST_TIMEOUT,
						url: endpointUrl,
					})
					.then(() => done(true))
					.catch(() => done(false));
			},
		});

		this._messageQueue = messageQueue;
	}

	/**
	 * Map through stored contexts and create messages
	 * for any contexts that have matching events.
	 *
	 * @param {Object} contextHashEventMap
	 * @param {Array.<Object>} storedContexts
	 * @param {string} userId
	 * @returns {Promise}
	 */
	_pushEventBatchesToMessageQueue(
		contextHashEventMap,
		storedContexts,
		userId
	) {
		return Promise.all(
			storedContexts.map((context) => {
				const events = contextHashEventMap[hash(context)];

				if (!events) {
					return;
				}

				return this._messageQueue.addItem(
					this._createMessage({
						context,
						events,
						userId,
					})
				);
			})
		);
	}

	reset(events = []) {
		setItem(this.keys.eventQueue, events);
	}

	/**
	 * Start a timer to process messages at a specified interval.
	 */
	_startsFlushLoop() {
		if (this.flushInterval) {
			clearInterval(this.flushInterval);
		}

		this.flushInterval = setInterval(() => this._flush(), this.flushDelay);
	}
}

export default EventQueue;
