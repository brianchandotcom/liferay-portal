/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {v4 as uuidv4} from 'uuid';

import middlewares from './middlewares/defaults';
import defaultPlugins from './plugins/defaults';
import QueueFlushService from './queueFlushService';
import EventMessageQueue from './queues/eventMessageQueue';
import EventQueue from './queues/eventsQueue';
import IdentityMessageQueue from './queues/identityMessageQueue';
import {
	ANALYTICS_CLIENT_VERSION,
	FLUSH_INTERVAL,
	QUEUE_PRIORITY_DEFAULT,
	QUEUE_PRIORITY_IDENTITY,
	STORAGE_KEY_CHANNEL_ID,
	STORAGE_KEY_EVENTS,
	STORAGE_KEY_IDENTITY,
	STORAGE_KEY_MESSAGES,
	STORAGE_KEY_MESSAGE_IDENTITY,
	STORAGE_KEY_PREV_EMAIL_ADDRESS_HASHED,
	STORAGE_KEY_USER_ID,
	TRACK_DEFAULT_OPTIONS,
	VALIDATION_CONTEXT_VALUE_MAXIMUM_LENGTH,
} from './utils/constants';
import {getContexts, setContexts} from './utils/contexts';
import {normalizeEvent} from './utils/events';
import hash from './utils/hash';
import {getItem, removeItem, setItem} from './utils/storage';
import {upgradeStorage} from './utils/storage_version';
import {isValidEvent} from './utils/validators';

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
	constructor(config, middlewares) {
		if (!instance) {
			instance = this;
		}

		if (this._isTrackingDisabled()) {
			return instance;
		}

		instance._disposed = false;

		const endpointUrl = (config.endpointUrl || '').replace(/\/$/, '');

		instance.config = Object.assign(config, {
			endpointUrl,
			flushInterval: config.flushInterval || FLUSH_INTERVAL,
			identityEndpoint: `${endpointUrl}/identity`,
		});

		instance.version = ANALYTICS_CLIENT_VERSION;

		// Register initial middlewares

		middlewares.forEach((middleware) =>
			instance.registerMiddleware(middleware)
		);

		instance._queueFlushService = new QueueFlushService(instance.config);

		this._initializeEventQueue();
		this._initializeEventMessageQueue();
		this._initializeIdentityMessageQueue();

		// Upgrade storage

		upgradeStorage();

		// Initializes default plugins

		instance._pluginDisposers = defaultPlugins.map((plugin) =>
			plugin(instance)
		);

		this._ensureIntegrity();

		return instance;
	}

	/**
	 * Creates a singleton instance of Analytics
	 * @param {Object} config Configuration object
	 * @example
	 * Analytics.create(
	 *   {
	 *     channelId: '123456789',
	 *     dataSourceId: 'MyDataSourceId',
	 *     endpointUrl: 'https://osbasahpublisher-projectid.lfr.cloud'
	 *     flushInterval: 2000,
	 *     projectId: '123456'
	 *     userId: 'id-s7uatimmxgo',
	 *   }
	 * );
	 */
	static create(config = {}, middlewares = []) {
		const self = new Analytics(config, middlewares);
		const Liferay = window.Liferay;

		ENV.Analytics = self;
		ENV.Analytics.create = Analytics.create;
		ENV.Analytics.dispose = Analytics.dispose;

		let email = '';
		let name = '';

		if (
			Liferay &&
			Liferay.ThemeDisplay &&
			Liferay.ThemeDisplay.getUserEmailAddress &&
			!!Liferay.ThemeDisplay.getUserEmailAddress().length &&
			Liferay.ThemeDisplay.getUserName &&
			!!Liferay.ThemeDisplay.getUserName().length
		) {
			email = Liferay.ThemeDisplay.getUserEmailAddress();
			name = Liferay.ThemeDisplay.getUserName();
		}

		self.setIdentity({
			email,
			name,
		});

		return self;
	}

	/**
	 * Disposes events and stops interval timer
	 * @example
	 * Analytics.dispose();
	 */
	static dispose() {
		const self = ENV.Analytics;

		if (self && !self._isTrackingDisabled()) {
			self._disposeInternal();
		}
	}

	getEvents() {
		return this[STORAGE_KEY_EVENTS].getItems();
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
			middlewares.push(middleware);
		}
	}

	/**
	 * Clear event queue and set stored context to the current context.
	 */
	reset() {
		if (this._isTrackingDisabled()) {
			return;
		}

		this[STORAGE_KEY_EVENTS].reset();

		this.resetContext();
	}

	/**
	 * Set stored context to the current context.
	 */
	resetContext() {
		const context = this._getContext();

		const contextsMap = new Map();
		contextsMap.set(hash(context), context);

		setContexts(contextsMap);
	}

	/**
	 * Registers an event that is to be sent to Analytics Cloud
	 * @param {string} eventId Id of the event
	 * @param {Object} eventProps Complementary information about the event
	 * @param {Object} options Complementary information about the request
	 */
	track(eventId, eventProps, options = {}) {
		const {assetType, ...otherEventProps} = eventProps || {};

		// eslint-disable-next-line
		const mergedOptions = Object.assign({}, TRACK_DEFAULT_OPTIONS, options);

		const applicationId = assetType || mergedOptions.applicationId;

		if (
			this._isTrackingDisabled() ||
			instance._disposed ||
			!isValidEvent({applicationId, eventId, eventProps: otherEventProps})
		) {
			return;
		}

		const currentContextHash = this._getCurrentContextHash();

		instance[STORAGE_KEY_EVENTS].addItem(
			normalizeEvent(
				eventId,
				applicationId,
				otherEventProps,
				currentContextHash
			)
		);
	}

	/**
	 * Registers an event that is to be sent to Analytics Cloud
	 * @param {string} eventId Id of the event
	 * @param {string} applicationId ID of the application that triggered the event
	 * @param {Object} eventProps Complementary information about the event
	 */
	send(eventId, applicationId, eventProps) {
		if (!applicationId) {
			return;
		}

		this.track(eventId, eventProps, {applicationId});
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

		const hashedIdentity = {
			emailAddressHashed: identity.email
				? hash(identity.email.toLowerCase())
				: '',
		};

		this.config.identity = hashedIdentity;

		const userId = this._getUserId();

		this._sendIdentity(hashedIdentity, userId);

		return Promise.resolve(userId);
	}

	/**
	 * Clears interval and calls plugins disposers if available
	 */
	_disposeInternal() {
		instance._disposed = true;

		if (instance._queueFlushService) {
			instance._queueFlushService.dispose();
		}

		if (instance._pluginDisposers) {
			instance._pluginDisposers
				.filter((disposer) => typeof disposer === 'function')
				.forEach((disposer) => disposer());
		}
	}

	_ensureIntegrity() {
		const userId = getItem(STORAGE_KEY_USER_ID);

		if (userId) {
			this._setCookie(STORAGE_KEY_USER_ID, userId);
		}
	}

	_getCurrentContextHash() {
		const currentContext = this._getContext();
		const currentContextHash = hash(currentContext);
		const contextsMap = getContexts();

		if (!contextsMap.has(currentContextHash)) {
			contextsMap.set(currentContextHash, currentContext);

			setContexts(contextsMap);
		}

		return currentContextHash;
	}

	_getContext() {
		const {context} = middlewares.reduce(
			(request, middleware) => middleware(request, this),
			{context: {channelId: instance.config.channelId}}
		);

		for (const key in context) {
			context[key] = String(context[key]).slice(
				0,
				VALIDATION_CONTEXT_VALUE_MAXIMUM_LENGTH
			);
		}

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
		let userId = getItem(STORAGE_KEY_USER_ID);

		const {emailAddressHashed} = this.config.identity;
		const previousEmailAddressHashed = getItem(
			STORAGE_KEY_PREV_EMAIL_ADDRESS_HASHED
		);

		if (!userId) {
			userId = this._generateUserId();
		}

		if (
			emailAddressHashed &&
			emailAddressHashed !== previousEmailAddressHashed
		) {
			if (previousEmailAddressHashed) {
				userId = this._generateUserId();
			}

			setItem(STORAGE_KEY_PREV_EMAIL_ADDRESS_HASHED, emailAddressHashed);
		}

		return userId;
	}

	/**
	 * Returns a unique identifier for a user, additionally it stores
	 * the generated token to the local storage cache and clears
	 * previously stored identity hash.
	 * @returns {string} The generated id
	 */
	_generateUserId() {
		const userId = uuidv4();

		setItem(STORAGE_KEY_USER_ID, userId);
		this._setCookie(STORAGE_KEY_USER_ID, userId);

		removeItem(STORAGE_KEY_IDENTITY);

		return userId;
	}

	_isTrackingDisabled() {
		return (
			ENV.ac_client_disable_tracking ||
			navigator.doNotTrack === '1' ||
			navigator.doNotTrack === 'yes'
		);
	}

	/**
	 * Sends the identity information and user id to the Identity Service.
	 * @param {Object} identity The identity information about an user.
	 * @param {String} userId The unique user id.
	 * @returns {Promise} A promise returned by the fetch request.
	 */
	_sendIdentity(identity, userId) {
		const {dataSourceId} = this.config;
		const {channelId} = this._getContext();

		const identityHash = this._getIdentityHash(
			dataSourceId,
			identity,
			userId
		);
		const storedIdentityHash = getItem(STORAGE_KEY_IDENTITY);
		const storedChannelId = getItem(STORAGE_KEY_CHANNEL_ID);

		if (
			identityHash !== storedIdentityHash ||
			channelId !== storedChannelId
		) {
			const {emailAddressHashed} = identity;

			setItem(STORAGE_KEY_CHANNEL_ID, channelId);
			setItem(STORAGE_KEY_IDENTITY, identityHash);

			instance[STORAGE_KEY_MESSAGE_IDENTITY].addItem({
				channelId,
				dataSourceId,
				emailAddressHashed,
				id: identityHash,
				userId,
			});
		}
	}

	/**
	 * Sets a browser cookie
	 * @protected
	 */
	_setCookie(key, data) {
		const Liferay = window.Liferay;
		const expires = new Date();

		expires.setDate(expires.getDate() + 365);

		// Checks if the client is being loaded with the Liferay global
		// variable and if there is a Cookie method because the client
		// is Liferay Portal agnostic and may have versions that do not
		// yet have the Cookie method.

		if (Liferay?.Util?.Cookie) {
			Liferay.Util.Cookie.set(
				key,
				data,
				Liferay.Util.Cookie.TYPES.PERSONALIZATION,
				{
					expires,
					secure: true,
				}
			);
		}
		else {
			document.cookie = `${key}=${data}; expires=${expires.toUTCString()}; path=/; Secure`;
		}

		return;
	}

	/**
	 * Create member instance of EventQueue to store events.
	 */
	_initializeEventQueue() {
		const eventQueue = new EventQueue({
			analyticsInstance: instance,
		});

		instance[STORAGE_KEY_EVENTS] = eventQueue;
		instance._queueFlushService.addQueue(eventQueue, {
			priority: QUEUE_PRIORITY_DEFAULT,
		});
	}

	/**
	 * Create member instance of EventMessageQueue to store event messages.
	 */
	_initializeEventMessageQueue() {
		const eventMessageQueue = new EventMessageQueue({
			analyticsInstance: instance,
		});

		instance[STORAGE_KEY_MESSAGES] = eventMessageQueue;
		instance._queueFlushService.addQueue(eventMessageQueue, {
			priority: QUEUE_PRIORITY_DEFAULT,
		});
	}

	/**
	 * Create member instance of IdentityMessageQueue to store identity messages.
	 */
	_initializeIdentityMessageQueue() {
		const identityMessageQueue = new IdentityMessageQueue({
			analyticsInstance: instance,
		});

		instance[STORAGE_KEY_MESSAGE_IDENTITY] = identityMessageQueue;
		instance._queueFlushService.addQueue(identityMessageQueue, {
			priority: QUEUE_PRIORITY_IDENTITY,
		});
	}
}

// Exposes Analytics.create to the global scope

ENV.Analytics = {
	create: Analytics.create,
};

export {Analytics};
export default Analytics;
