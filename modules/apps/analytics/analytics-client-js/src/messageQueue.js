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

import {
	FLUSH_INTERVAL as PROCESS_INTERVAL,
	QUEUE_STORAGE_LIMIT,
} from './utils/constants';
import {getRetryDelay} from './utils/delay';
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
 * @property {Array.<AnalyticsEvent>} events - Analytics events.
 * @property {string} id - The unique identifier of this message.
 * @property {string} userId - The user id hash.
 */

/**
 * An Analytics Message.
 *
 * @typedef {Object} AnalyticsMessage
 * @property {string} attemptNumber - The number of times this message has been processed.
 * @property {AnalyticsMessageItem} item - The message payload.
 * @property {Number} time - The timestamp to control when a message is processed.
 */

/**
 * MessageQueue class that is designed to receive event batches from
 * Analytics. MessageQueue will process items in its queue, dequeuing or requeuing
 * on success or failure, respectively. Its messages are persisted in localStorage.
 * It will periodically run a task to process messages in its queue.
 */
class MessageQueue {
	constructor(name, config) {
		this.maxRetries = config.maxRetries;
		this.maxSize = config.maxSize || QUEUE_STORAGE_LIMIT;
		this.name = name;
		this.processDelay = config.processDelay || PROCESS_INTERVAL;
		this.processFn = config.processFn;
		this.processing = false;

		if (!getItem(this.name)) {
			setItem(this.name, []);
		}

		this.addItem = this.addItem.bind(this);
		this._processMessages = this._processMessages.bind(this);

		this._startsProcessingLoop();
	}

	/**
	 * Adds an item to the queue.
	 *
	 * @param {AnalyticsMessageItem} item
	 * @returns {Promise}
	 */
	addItem(item) {
		return verifyStorageLimitForKey(this.name, this.maxSize).then(() => {
			this._enqueue({
				attemptNumber: 0,
				item,
				time: Date.now(),
			});
		});
	}

	/**
	 * Remove an item from the queue by id.
	 *
	 * @param {string} id - The Message ID.
	 */
	_dequeue(id) {
		const queue = this.getMessages();

		setItem(
			this.name,
			queue.filter(({item}) => item.id !== id)
		);
	}

	/**
	 * Method for clearing any scheduled processing.
	 */
	dispose() {
		if (this.processInterval) {
			clearInterval(this.processInterval);
		}
	}

	/**
	 * Add a message to the queue and process messages.
	 *
	 * @param {Message} entry
	 * @param {boolean} - Whether _processMessages should run immediately after enqueuing message.
	 */
	_enqueue(entry, processImmediate = true) {
		const queue = this.getMessages();

		queue.push(entry);

		setItem(this.name, queue);

		if (processImmediate) {
			this._processMessages();
		}
	}

	/**
	 * Format a message with the done callback.
	 *
	 * @param {Array.<AnalyticsMessage>} queue
	 * @returns {Array.<Object>} Returns messages with done callback attached.
	 */
	_formatMessagesForProcessing(queue) {
		const self = this;

		return queue.map(({attemptNumber, item}) => {
			return {
				done: (success) => {
					self._dequeue(item.id);

					if (!success) {
						self._requeue(item, attemptNumber + 1);
					}

					return Promise.resolve();
				},
				item,
			};
		});
	}

	/**
	 * Get queued messages.
	 *
	 * @returns {Array.<AnalyticsMessage>}
	 */
	getMessages() {
		return getItem(this.name) || [];
	}

	/**
	 * Go through the queue and process any messages that
	 * are scheduled for now or earlier.
	 *
	 * Note: Because we are using a ProcessLock, no other process should
	 * be able to acquire a lock for a particular key to run its callback
	 * until the process with the active lock releases it.
	 *
	 */
	_processMessages() {
		const messages = this.getMessages();

		if (!this.processing && messages.length) {
			const lock = new ProcessLock();

			lock.acquireLock(this.name).then((success) => {
				if (success) {
					this.processing = true;
					const now = Date.now();
					const queue = this.getMessages().filter(
						({time}) => time <= now
					);

					if (queue.length) {
						Promise.all(
							this._formatMessagesForProcessing(
								queue
							).map(({done, item}) => this.processFn(item, done))
						).then(() => {
							lock.releaseLock(this.name).then(() => {
								this.processing = false;
							});
						});
					}
					else {
						return lock.releaseLock(this.name).then(() => {
							this.processing = false;
						});
					}
				}
			});
		}
	}

	/**
	 * Requeue an item with a processing time that includes
	 * an increasing retry delay based on the attempt number.
	 *
	 * @param {AnalyticsMessage} item - The message to requeue.
	 * @param {Number} attemptNumber - The number of attempts to process this item.
	 */
	_requeue(item, attemptNumber) {
		this._enqueue(
			{
				attemptNumber,
				item,
				time:
					Date.now() + getRetryDelay(attemptNumber, this.maxRetries),
			},
			false
		);
	}

	/**
	 * Clear queue items.
	 */
	reset() {
		setItem(this.name, []);
	}

	/**
	 * Start a timer to process messages at a specified interval.
	 */
	_startsProcessingLoop() {
		if (this.processInterval) {
			clearInterval(this.processInterval);
		}

		this.processInterval = setInterval(
			() => this._processMessages(),
			this.processDelay
		);
	}
}

export {MessageQueue, getRetryDelay};
export default MessageQueue;
