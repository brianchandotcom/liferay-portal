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

// Gateway

import uuidv1 from 'uuid/v1';

import Client from './client';
import EventQueue from './eventQueue';
import middlewares from './middlewares/defaults';
import defaultPlugins from './plugins/defaults';
import {
	FLUSH_INTERVAL,
	STORAGE_KEY_CONTEXTS,
	STORAGE_KEY_EVENTS,
	STORAGE_KEY_IDENTITY,
	STORAGE_KEY_USER_ID,
} from './utils/constants';
import {normalizeEvent} from './utils/events';
import hash from './utils/hash';
import {getItem, setItem} from './utils/storage';

// Constants

export const ENV = window || global;

let instance;

/**
 * Analytics class that is designed to collect events that are captured
 * for later processing. It persists the events in localStorage
 * and flushes it to the defined endpoint at regular intervals.
 */
class Analytics {

	/**
	 * Returns an Analytics instance and triggers the automatic flush loop
	 * @param {Object} config object to instantiate the Analytics tool
	 */
	constructor(config) {
		if (!instance) {
			instance = this;
		}

		if (this._isTrackingDisabled()) {
			return instance;
		}

		const client = new Client();

		const endpointUrl = (config.endpointUrl || '').replace(/\/$/, '');

		instance.client = client;
		instance.config = config;
		instance.identityEndpoint = `${endpointUrl}/identity`;
		instance.delay = config.flushInterval || FLUSH_INTERVAL;

		this._initializeEventQueue();

		// Initializes default plugins

		instance._pluginDisposers = defaultPlugins.map((plugin) =>
			plugin(instance)
		);

		this._ensureIntegrity();

		return instance;
	}

	/**
	 * Create member instance of EventQueue to store events.
	 */
	_initializeEventQueue() {
		const eventQueue = new EventQueue(STORAGE_KEY_EVENTS, {
			analyticsInstance: instance,
			flushDelay: instance.delay,
			onFlushSuccess: (unflushedEvents) => {
				if (!unflushedEvents.length) {
					this.resetContext();
				}
			},
			shouldFlush: () => {
				if (this._isTrackingDisabled()) {
					this.disposeInternal();

					return false;
				}
				else {
					return true;
				}
			},
		});

		instance._eventQueue = eventQueue;
	}

	/**
	 * Creates a singleton instance of Analytics
	 * @param {Object} config Configuration object
	 * @example
	 * Analytics.create(
	 *   {
	 *     endpointUrl: 'https://osbasahpublisher-projectid.lfr.cloud'
	 *     flushInterval: 2000,
	 *     userId: 'id-s7uatimmxgo',
	 *     channelId: '123456789',
	 *     dataSourceId: 'MyDataSourceId',
	 *   }
	 * );
	 */
	static create(config = {}) {
		const self = new Analytics(config);

		ENV.Analytics = self;
		ENV.Analytics.create = Analytics.create;
		ENV.Analytics.dispose = Analytics.dispose;

		return self;
	}

	/**
	 * Disposes events and stops interval timer
	 * @example
	 * Analytics.dispose();
	 */
	static dispose() {
		const self = ENV.Analytics;

		if (self) {
			self.disposeInternal();
		}
	}

	getEvents() {
		return this._eventQueue.getEvents();
	}

	/**
	 * Clear event queue and set stored context to the current context.
	 */
	reset() {
		this._eventQueue.reset();

		this.resetContext();
	}

	/**
	 * Set stored context to the current context.
	 */
	resetContext() {
		setItem(STORAGE_KEY_CONTEXTS, [this._getContext()]);
	}

	/**
	 * Registers the given plugin and executes its initialization logic
	 * @param {Function} plugin An Analytics Plugin
	 */
	registerPlugin(plugin) {
		if (typeof plugin === 'function') {
			plugin(this);
		}
	}

	/**
	 * Registers the given middleware. This middleware will be later on called
	 * with the request object and this Analytics instance
	 * @param {Function} middleware A function that will be invoked on every request
	 * @example
	 * Analytics.registerMiddleware(
	 *   (request, analytics) => {
	 *     ...
	 *   }
	 * );
	 */
	registerMiddleware(middleware) {
		if (this._isTrackingDisabled()) {
			return;
		}

		if (typeof middleware === 'function') {
			this.client.use(middleware);
		}
	}

	/**
	 * Registers an event that is to be sent to Analytics Cloud
	 * @param {string} eventId Id of the event
	 * @param {string} applicationId ID of the application that triggered the event
	 * @param {Object} eventProps Complementary information about the event
	 */
	send(eventId, applicationId, eventProps) {
		if (this._isTrackingDisabled() || !applicationId) {
			return;
		}

		const currentContextHash = this._getCurrentContextHash();

		instance._eventQueue.addItem(
			normalizeEvent(
				eventId,
				applicationId,
				eventProps,
				currentContextHash
			)
		);
	}

	/**
	 * Sets the current user identity in the system. This is meant to be invoked
	 * by consumers every time an identity change is detected. If the identity is
	 * different than the previously stored one, we will save this new identity and
	 * send a request updating the Identity Service.
	 * @param {Object} identity A key-value pair object that identifies the user
	 * @returns {Promise} A promise resolved with the generated identity hash
	 */
	setIdentity(identity) {
		if (this._isTrackingDisabled()) {
			return;
		}

		this.config.identity = identity;

		return this._getUserId().then((userId) =>
			this._sendIdentity(identity, userId)
		);
	}

	_ensureIntegrity() {
		const userId = getItem(STORAGE_KEY_USER_ID);

		if (userId) {
			this._setCookie(STORAGE_KEY_USER_ID, userId);
		}
	}

	/**
	 * Clears interval and calls plugins disposers if available
	 */
	disposeInternal() {
		instance._eventQueue.dispose();

		if (instance._pluginDisposers) {
			instance._pluginDisposers
				.filter((disposer) => typeof disposer === 'function')
				.forEach((disposer) => disposer());
		}
	}

	/**
	 * Returns a unique identifier for a user, additionally it stores
	 * the generated token to the local storage cache and clears
	 * previously stored identity hash.
	 * @returns {string} The generated id
	 */
	_generateUserId() {
		const userId = uuidv1();

		setItem(STORAGE_KEY_USER_ID, userId);
		this._setCookie(STORAGE_KEY_USER_ID, userId);

		localStorage.removeItem(STORAGE_KEY_IDENTITY);

		return userId;
	}

	_getCurrentContextHash() {
		const currentContext = this._getContext();
		const currentContextHash = hash(currentContext);

		const storedContexts = getItem(STORAGE_KEY_CONTEXTS) || [];

		const hasStoredContext = storedContexts.find(
			(storedContext) => hash(storedContext) === currentContextHash
		);

		if (!hasStoredContext) {
			setItem(STORAGE_KEY_CONTEXTS, [...storedContexts, currentContext]);
		}

		return currentContextHash;
	}

	_getContext() {
		const {context} = middlewares.reduce(
			(request, middleware) => middleware(request, this),
			{context: {}}
		);

		return context;
	}

	_getIdentityHash(dataSourceId, identity, userId) {
		const bodyData = {
			dataSourceId,
			identity,
			userId,
		};

		return hash(bodyData);
	}

	/**
	 * Gets the userId for the existing analytics user. Previously generated ids
	 * are stored and retrieved before generating a new one. If an anonymous
	 * navigation is started after an identified navigation, the user ID token
	 * is regenerated.
	 * @returns {Promise} A promise resolved with the stored or generated userId
	 */
	_getUserId() {
		const newUserIdRequired = this._isNewUserIdRequired();

		let userId = Promise.resolve(getItem(STORAGE_KEY_USER_ID));

		if (newUserIdRequired) {
			userId = Promise.resolve(this._generateUserId());
		}

		return userId;
	}

	_isNewUserIdRequired() {
		const {dataSourceId} = this.config;
		const {identity} = this.config;

		const storedIdentityHash = getItem(STORAGE_KEY_IDENTITY);
		const storedUserId = getItem(STORAGE_KEY_USER_ID);

		let newUserIdRequired = false;

		// During logout or session expiration, identity object becomes undefined
		// because the client object is being instantiated on every page navigation,
		// in such cases, we force a new user ID token.

		if (!storedUserId || (storedIdentityHash && !identity)) {
			newUserIdRequired = true;
		}

		// After logout or session expiration, it is not guaranteed a new user ID
		// is generated. The login/logout process can redirect the user to page
		// where the analytics.js is not loaded. In such cases, we must verify
		// the identity hashes match and generate a new user ID token otherwise.

		if (
			storedUserId &&
			identity &&
			storedIdentityHash &&
			storedIdentityHash !==
				this._getIdentityHash(dataSourceId, identity, storedUserId)
		) {
			newUserIdRequired = true;
		}

		return newUserIdRequired;
	}

	_isTrackingDisabled() {
		if (
			ENV.ac_client_disable_tracking ||
			navigator.doNotTrack == '1' ||
			navigator.doNotTrack == 'yes'
		) {
			return true;
		}

		return false;
	}

	/**
	 * Sends the identity information and user id to the Identity Service.
	 * @param {Object} identity The identity information about an user.
	 * @param {String} userId The unique user id.
	 * @returns {Promise} A promise returned by the fetch request.
	 */
	_sendIdentity(identity, userId) {
		const {channelId, dataSourceId} = this.config;

		const newIdentityHash = this._getIdentityHash(
			dataSourceId,
			identity,
			userId
		);
		const storedIdentityHash = getItem(STORAGE_KEY_IDENTITY);

		let identityHash = Promise.resolve(storedIdentityHash);

		if (newIdentityHash !== storedIdentityHash) {
			setItem(STORAGE_KEY_IDENTITY, newIdentityHash);

			identityHash = instance.client
				.send({
					payload: {
						channelId,
						dataSourceId,
						identity,
						userId,
					},
					url: this.identityEndpoint,
				})
				.then(() => newIdentityHash);
		}

		return identityHash;
	}

	/**
	 * Sets a browser cookie
	 * @protected
	 */
	_setCookie(key, data) {
		const expirationDate = new Date();

		expirationDate.setDate(expirationDate.getDate() + 365);

		document.cookie = `${key}=${data}; expires= ${expirationDate.toUTCString()}; path=/`;
	}
}

// Exposes Analytics.create to the global scope

ENV.Analytics = {
	create: Analytics.create,
};

export {Analytics};
export default Analytics;
