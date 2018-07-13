import {LocalStorageMechanism, Storage} from 'metal-storage';
import middlewares from './middlewares/defaults';

// Gateways
import AsahClient from './AsahClient/AsahClient';
import LCSClient from './LCSClient/LCSClient';

import defaultPlugins from './plugins/defaults';
import fingerprint from './utils/fingerprint';
import hash from './utils/hash';
import uuidv1 from 'uuid/v1';

// Constants
const ENV = window || global;
const FLUSH_INTERVAL = 2000;
const REQUEST_TIMEOUT = 5000;

// Local Storage keys
const STORAGE_KEY_EVENTS = 'lcs_client_batch';
const STORAGE_KEY_CONTEXTS = 'lcs_client_context';
const STORAGE_KEY_USER_ID = 'lcs_client_user_id';
const STORAGE_KEY_IDENTITY_HASH = 'lcs_client_identity';

// Creates LocalStorage wrapper
const storage = new Storage(new LocalStorageMechanism());

let instance = null;

/**
 * Analytics class that is desined to collect events that are captured
 * for later processing. It persists the events in the LocalStorage using the
 * metal-storage implementation and flushes it to the defined endpoint at
 * regular intervals.
 */
class Analytics {
	/**
	 * Returns an Analytics instance and triggers the automatic flush loop
	 * @param {object} config object to instantiate the Analytics tool
	 */
	constructor(config) {
		if (!instance) {
			instance = this;
		}

		const lcsClient = new LCSClient(config.uri);
		const asahClient = new AsahClient();

		instance.client = lcsClient;

		instance._sendData = userId => {
			asahClient.send(instance, userId);
			return lcsClient.send(instance, userId);
		};

		instance.config = config;

		const analyticsKey = config.analyticsKey;

		instance.asahIdentityEndpoint = `https://osbasahfarobackend-asahlfr.lfr.io/${analyticsKey}/identity/`;
		instance.lcsIdentityEndpoint =
			'https://analytics-gw.liferay.com/api/identitycontextgateway/send-identity-context';

		instance.events = storage.get(STORAGE_KEY_EVENTS) || [];
		instance.contexts = storage.get(STORAGE_KEY_CONTEXTS) || [];
		instance.isFlushInProgress = false;

		// Initializes default plugins

		instance._pluginDisposers = defaultPlugins.map(plugin =>
			plugin(instance)
		);

		// Starts flush loop

		if (instance.flushInterval) {
			clearInterval(instance.flushInterval);
		}

		instance.flushInterval = setInterval(
			() => instance.flush(),
			config.flushInterval || FLUSH_INTERVAL
		);

		return instance;
	}

	/**
	 * Clears interval and calls plugins disposers if available
	 */
	disposeInternal() {
		if (this.flushInterval) {
			clearInterval(this.flushInterval);
		}

		instance._pluginDisposers
			.filter(disposer => typeof disposer === 'function')
			.forEach(disposer => disposer());
	}

	/**
	 * Persists the event queue to the LocalStorage
	 * @protected
	 */
	_persist(key, data) {
		storage.set(key, data);

		return data;
	}

	/**
	 * Serializes data and returns the result appending a timestamp to the returned
	 * data as well
	 * @param {string} eventId The event Id
	 * @param {string} applicationId The application Id
	 * @param {object} properties Additional properties to serialize
	 * @protected
	 * @return {object}
	 */
	_serialize(eventId, applicationId, properties, contextHash) {
		const eventDate = new Date().toISOString();

		return {
			applicationId,
			contextHash,
			eventDate,
			eventId,
			properties,
		};
	}

	/**
	 * Returns a promise that times out after the given time limit is exceeded
	 * @param {number} timeout
	 * @return {object} Promise
	 */
	_timeout(timeout) {
		return new Promise((resolve, reject) => setTimeout(reject, timeout));
	}

	/**
	 * Returns a unique identifier for an event
	 * @param {object} The event
	 * @return {string} The generated id
	 */
	_getEventKey({applicationId, eventDate, eventId}) {
		return `${applicationId}${eventDate}${eventId}`;
	}

	/**
	 * Returns an unique identifier for an user
	 * @return {string} The generated id
	 */
	_generateUserId() {
		return uuidv1();
	}

	_getContext() {
		const {context} = middlewares.reduce(
			(request, middleware) => middleware(request, this),
			{context: {}}
		);

		return context;
	}

	/**
	 * Gets the userId for the existing analytics user. Previously generated ids
	 * are stored and retrieved before generating a new one and attempting to update
	 * the Identity Service.
	 * @return {Promise} A promise resolved with the stored or generated userId
	 */
	_getUserId() {
		let userId = storage.get(STORAGE_KEY_USER_ID);

		if (userId) {
			return Promise.resolve(userId);
		} else {
			userId = this._generateUserId();

			this._persist(STORAGE_KEY_USER_ID, userId);

			return Promise.resolve(userId);
		}
	}

	/**
	 * Sends the identity information and user id to the Identity Service.
	 * @param {Object} identity The identity information about an user.
	 * @param {String} userId The unique user id.
	 * @return {Promise} A promise returned by the fetch request.
	 */
	_sendIdentity(identity, userId) {
		const bodyData = {
			...fingerprint(),
			analyticsKey: this.config.analyticsKey,
			identity,
			userId,
		};

		const storedIdentityHash = storage.get(STORAGE_KEY_IDENTITY_HASH);
		const newIdentityHash = hash(bodyData);

		if (newIdentityHash !== storedIdentityHash) {
			instance._persist(STORAGE_KEY_IDENTITY_HASH, newIdentityHash);

			const body = JSON.stringify(bodyData);
			const headers = new Headers();

			headers.append('Content-Type', 'application/json');

			const request = {
				body,
				cache: 'default',
				credentials: 'same-origin',
				headers,
				method: 'POST',
				mode: 'cors',
			};

			fetch(this.asahIdentityEndpoint, request);

			return fetch(this.lcsIdentityEndpoint, request).then(
				() => newIdentityHash
			);
		}

		return Promise.resolve(storedIdentityHash);
	}

	/**
	 * Flushes the event queue and sends it to the registered endpoint. If no data
	 * is pending or a flush is already in progress, the request will be ignored.
	 * @return {object} A Promise that resolves successfully when the data has been
	 * sent or no pending data was left to be sent
	 */
	flush() {
		let result;

		if (!this.isFlushInProgress && this.events.length) {
			this.isFlushInProgress = true;

			const eventKeys = this.events.map(event =>
				this._getEventKey(event)
			);

			result = Promise.race([
				this._getUserId().then(userId =>
					Promise.all([
						this._sendIdentity(this.config.identity, userId),
						instance._sendData(userId),
					])
				),
				this._timeout(REQUEST_TIMEOUT),
			])
				.then(() => {
					const events = this.events.filter(
						event =>
							eventKeys.indexOf(this._getEventKey(event)) > -1
					);

					this.reset(events);
				})
				.catch(console.error)
				.then(() => (this.isFlushInProgress = false));
		} else {
			result = Promise.resolve();
		}

		return result;
	}

	/**
	 * Registers the given plugin and executes its initialistion logic
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
		if (typeof middleware === 'function') {
			this.client.use(middleware);
		}
	}

	/**
	 * Resets the event queue
	 * @param {Array} A list of events to be cleared from the queue. If not passed,
	 * clears everything.
	 */
	reset(events) {
		if (events) {
			this.events = this.events.filter(event => {
				const eventKey = this._getEventKey(event);

				return !events.find(evt => {
					return this._getEventKey(evt) === eventKey;
				});
			});
		} else {
			this.events.length = 0;
		}

		if (!this.events.length) {
			const context = this._getContext();

			this.contexts = this.contexts.filter(
				storedContext => hash(context) == hash(storedContext)
			);
		}

		this._persist(STORAGE_KEY_EVENTS, this.events);
		this._persist(STORAGE_KEY_CONTEXTS, this.contexts);
	}

	/**
	 * Registers an event that is to be sent to the LCS endpoint
	 * @param {string} eventId Id of the event
	 * @param {string} applicationId ID of the application that triggered the event
	 * @param {object} eventProps Complementary information about the event
	 */
	send(eventId, applicationId, eventProps) {
		const currentContext = this._getContext();
		const currentContextHash = hash(currentContext);

		const hasStoredContext = this.contexts.find(
			storedContext => hash(storedContext) === currentContextHash
		);

		if (!hasStoredContext) {
			this.contexts = [...this.contexts, currentContext];
		}

		this.events = [
			...this.events,
			this._serialize(
				eventId,
				applicationId,
				eventProps,
				currentContextHash
			),
		];

		this._persist(STORAGE_KEY_EVENTS, this.events);
		this._persist(STORAGE_KEY_CONTEXTS, this.contexts);
	}

	/**
	 * Sets the current user identity in the system. This is meant to be invoked
	 * by consumers every time an identity change is detected. If the identity is
	 * different than the previously stored one, we will save this new identity and
	 * send a request updating the Identity Service.
	 * @param {object} identity A key-value pair object that identifies the user
	 * @return {Promise} A promise resolved with the generated identity hash
	 */
	setIdentity(identity) {
		this.config.identity = identity;

		return this._getUserId().then(userId =>
			this._sendIdentity(identity, userId)
		);
	}

	/**
	 * Creates a singleton instance of Analytics
	 * @param {object} config Configuration object
	 * @example
	 * Analytics.create(
	 *   {
	 *	   flushInterval: 2000,
	 *	   uri: 'https://analytics-gw.liferay.com/api/analyticsgateway/send-analytics-events'
	 *	   userId: 'id-s7uatimmxgo',
	 *     analyticsKey: 'MyAnalyticsKey',
	 *   }
	 * );
	 */
	static create(config = {}) {
		const instance = new Analytics(config);

		ENV.Analytics = instance;
		ENV.Analytics.create = Analytics.create;
		ENV.Analytics.dispose = Analytics.dispose;

		return instance;
	}

	/**
	 * Disposes events and stops interval timer
	 * @example
	 * Analytics.dispose();
	 */
	static dispose() {
		const instance = ENV.Analytics;

		if (instance) {
			instance.disposeInternal();
		}
	}
}

// Exposes Analytics.create to the global scope
ENV.Analytics = {
	create: Analytics.create,
};

export {Analytics};
export default Analytics;