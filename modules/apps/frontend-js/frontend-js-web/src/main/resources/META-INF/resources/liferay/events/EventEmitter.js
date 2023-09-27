/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Disposable from './Disposable';
import EventHandle from './EventHandle';

const singleArray = [0];

/**
 * EventEmitter utility.
 * @extends {Disposable}
 */
class EventEmitter extends Disposable {

	/**
	 * EventEmitter constructor
	 */
	constructor() {
		super();

		/**
		 * Holds event listeners scoped by event type.
		 * @type {Object<string, !Array<!function()>>}
		 * @protected
		 */
		this._events = null;

		/**
		 * Handlers that are triggered when an event is listened to.
		 * @type {Array}
		 */
		this._listenerHandlers = null;

		/**
		 * Configuration option which determines if an event facade should be sent
		 * as a param of listeners when emitting events. If set to true, the facade
		 * will be passed as the first argument of the listener.
		 * @type {boolean}
		 * @protected
		 */
		this._shouldUseFacade = false;
	}

	/**
	 * Adds a handler to given holder variable. If the holder doesn't have a
	 * value yet, it will receive the handler directly. If the holder is an array,
	 * the value will just be added to it. Otherwise, the holder will be set to a
	 * new array containing its previous value plus the new handler.
	 * @param {*} holder
	 * @param {!function()|Object} handler
	 * @return {*} The holder's new value.
	 * @protected
	 */
	_addHandler(holder, handler) {
		if (!holder) {
			holder = handler;
		}
		else {
			if (!Array.isArray(holder)) {
				holder = [holder];
			}
			holder.push(handler);
		}

		return holder;
	}

	/**
	 * Adds a listener to the end of the listeners array for the specified events.
	 * @param {!(Array|string)} event
	 * @param {!Function} listener
	 * @param {boolean} defaultListener Flag indicating if this listener is a default
	 *   action for this event. Default actions are run last, and only if no previous
	 *   listener call `preventDefault()` on the received event facade.
	 * @return {!EventHandle} Can be used to remove the listener.
	 */
	addListener(event, listener, defaultListener) {
		this._validateListener(listener);

		const events = this._toEventsArray(event);
		for (let i = 0; i < events.length; i++) {
			this._addSingleListener(events[i], listener, defaultListener);
		}

		return new EventHandle(this, event, listener);
	}

	/**
	 * Adds a listener to the end of the listeners array for a single event.
	 * @param {string} event
	 * @param {!Function} listener
	 * @param {boolean} defaultListener Flag indicating if this listener is a default
	 *   action for this event. Default actions are run last, and only if no previous
	 *   listener call `preventDefault()` on the received event facade.
	 * @param {Function=} origin The original function that was added as a
	 *   listener, if there is any.
	 * @protected
	 */
	_addSingleListener(event, listener, defaultListener, origin) {
		this._runListenerHandlers(event);
		if (defaultListener || origin) {
			listener = {
				default: defaultListener,
				fn: listener,
				origin,
			};
		}
		this._events = this._events || {};
		this._events[event] = this._addHandler(this._events[event], listener);
	}

	/**
	 * Builds facade for the given event.
	 * @param {string} event
	 * @return {Object}
	 * @protected
	 */
	_buildFacade(event) {
		if (this.getShouldUseFacade()) {
			const facade = {
				preventDefault() {
					facade.preventedDefault = true;
				},
				target: this,
				type: event,
			};

			return facade;
		}
	}

	/**
	 * Disposes of this instance's object references.
	 * @override
	 */
	disposeInternal() {
		this._events = null;
	}

	/**
	 * Execute each of the listeners in order with the supplied arguments.
	 * @param {string} event
	 * @param {*} opt_args [arg1], [arg2], [...]
	 * @return {boolean} Returns true if event had listeners, false otherwise.
	 */
	emit(event) {
		const listeners = this._getRawListeners(event);
		if (!listeners.length) {
			return false;
		}

		const args = Array.prototype.slice.call(arguments, 1);
		this._runListeners(listeners, args, this._buildFacade(event));

		return true;
	}

	/**
	 * Gets the listener objects for the given event, if there are any.
	 * @param {string} event
	 * @return {!Array}
	 * @protected
	 */
	_getRawListeners(event) {
		const directListeners = toArray(this._events && this._events[event]);

		return directListeners.concat(
			toArray(this._events && this._events['*'])
		);
	}

	/**
	 * Gets the configuration option which determines if an event facade should
	 * be sent as a param of listeners when emitting events. If set to true, the
	 * facade will be passed as the first argument of the listener.
	 * @return {boolean}
	 */
	getShouldUseFacade() {
		return this._shouldUseFacade;
	}

	/**
	 * Returns an array of listeners for the specified event.
	 * @param {string} event
	 * @return {Array} Array of listeners.
	 */
	listeners(event) {
		return this._getRawListeners(event).map((listener) =>
			listener.fn ? listener.fn : listener
		);
	}

	/**
	 * Adds a listener that will be invoked a fixed number of times for the
	 * events. After each event is triggered the specified amount of times, the
	 * listener is removed for it.
	 * @param {!(Array|string)} event
	 * @param {number} amount The amount of times this event should be listened
	 * to.
	 * @param {!Function} listener
	 * @return {!EventHandle} Can be used to remove the listener.
	 */
	many(event, amount, listener) {
		const events = this._toEventsArray(event);
		for (let i = 0; i < events.length; i++) {
			this._many(events[i], amount, listener);
		}

		return new EventHandle(this, event, listener);
	}

	/**
	 * Adds a listener that will be invoked a fixed number of times for a single
	 * event. After the event is triggered the specified amount of times, the
	 * listener is removed.
	 * @param {string} event
	 * @param {number} amount The amount of times this event should be listened
	 * to.
	 * @param {!Function} listener
	 * @protected
	 */
	_many(event, amount, listener) {
		const self = this;

		if (amount <= 0) {
			return;
		}

		/**
		 *
		 */
		function handlerInternal() {
			if (--amount === 0) {
				self.removeListener(event, handlerInternal);
			}
			listener.apply(self, arguments);
		}

		self._addSingleListener(event, handlerInternal, false, listener);
	}

	/**
	 * Checks if a listener object matches the given listener function. To match,
	 * it needs to either point to that listener or have it as its origin.
	 * @param {!Object} listenerObj
	 * @param {!Function} listener
	 * @return {boolean}
	 * @protected
	 */
	_matchesListener(listenerObj, listener) {
		const fn = listenerObj.fn || listenerObj;

		return (
			fn === listener ||
			(listenerObj.origin && listenerObj.origin === listener)
		);
	}

	/**
	 * Removes a listener for the specified events.
	 * Caution: changes array indices in the listener array behind the listener.
	 * @param {!(Array|string)} event
	 * @param {!Function} listener
	 * @return {!Object} Returns emitter, so calls can be chained.
	 */
	off(event, listener) {
		this._validateListener(listener);
		if (!this._events) {
			return this;
		}

		const events = this._toEventsArray(event);
		for (let i = 0; i < events.length; i++) {
			this._events[events[i]] = this._removeMatchingListenerObjs(
				toArray(this._events[events[i]]),
				listener
			);
		}

		return this;
	}

	/**
	 * Adds a listener to the end of the listeners array for the specified events.
	 * @param {!(Array|string)} events
	 * @param {!Function} listener
	 * @return {!EventHandle} Can be used to remove the listener.
	 */
	on() {
		return this.addListener.apply(this, arguments);
	}

	/**
	 * Adds handler that gets triggered when an event is listened to on this
	 * instance.
	 * @param {!function()} handler
	 */
	onListener(handler) {
		this._listenerHandlers = this._addHandler(
			this._listenerHandlers,
			handler
		);
	}

	/**
	 * Adds a one time listener for the events. This listener is invoked only the
	 * next time each event is fired, after which it is removed.
	 * @param {!(Array|string)} events
	 * @param {!Function} listener
	 * @return {!EventHandle} Can be used to remove the listener.
	 */
	once(events, listener) {
		return this.many(events, 1, listener);
	}

	/**
	 * Removes all listeners, or those of the specified events. It's not a good
	 * idea to remove listeners that were added elsewhere in the code,
	 * especially when it's on an emitter that you didn't create.
	 * @param {(Array|string)=} event
	 * @return {!Object} Returns emitter, so calls can be chained.
	 */
	removeAllListeners(event) {
		if (this._events) {
			if (event) {
				const events = this._toEventsArray(event);
				for (let i = 0; i < events.length; i++) {
					this._events[events[i]] = null;
				}
			}
			else {
				this._events = null;
			}
		}

		return this;
	}

	/**
	 * Removes all listener objects from the given array that match the given
	 * listener function.
	 * @param {Array.<Object>} listenerObjs
	 * @param {!Function} listener
	 * @return {Array.<Object>|Object} The new listeners array for this event.
	 * @protected
	 */
	_removeMatchingListenerObjs(listenerObjs, listener) {
		const finalListeners = [];
		for (let i = 0; i < listenerObjs.length; i++) {
			if (!this._matchesListener(listenerObjs[i], listener)) {
				finalListeners.push(listenerObjs[i]);
			}
		}

		return finalListeners.length ? finalListeners : null;
	}

	/**
	 * Removes a listener for the specified events.
	 * Caution: changes array indices in the listener array behind the listener.
	 * @param {!(Array|string)} events
	 * @param {!Function} listener
	 * @return {!Object} Returns emitter, so calls can be chained.
	 */
	removeListener() {
		return this.off.apply(this, arguments);
	}

	/**
	 * Runs the handlers when an event is listened to.
	 * @param {string} event
	 * @protected
	 */
	_runListenerHandlers(event) {
		let handlers = this._listenerHandlers;
		if (handlers) {
			handlers = toArray(handlers);
			for (let i = 0; i < handlers.length; i++) {
				handlers[i](event);
			}
		}
	}

	/**
	 * Runs the given listeners.
	 * @param {!Array} listeners
	 * @param {!Array} args
	 * @param {Object} facade
	 * @protected
	 */
	_runListeners(listeners, args, facade) {
		if (facade) {
			args.push(facade);
		}

		const defaultListeners = [];
		for (let i = 0; i < listeners.length; i++) {
			const listener = listeners[i].fn || listeners[i];
			if (listeners[i].default) {
				defaultListeners.push(listener);
			}
			else {
				listener.apply(this, args);
			}
		}
		if (!facade || !facade.preventedDefault) {
			for (let j = 0; j < defaultListeners.length; j++) {
				defaultListeners[j].apply(this, args);
			}
		}
	}

	/**
	 * Sets the configuration option which determines if an event facade should
	 * be sent as a param of listeners when emitting events. If set to true, the
	 * facade will be passed as the first argument of the listener.
	 * @param {boolean} shouldUseFacade
	 * @return {!Object} Returns emitter, so calls can be chained.
	 */
	setShouldUseFacade(shouldUseFacade) {
		this._shouldUseFacade = shouldUseFacade;

		return this;
	}

	/**
	 * Converts the parameter to an array if only one event is given. Reuses the
	 * same array each time this conversion is done, to avoid using more memory
	 * than necessary.
	 * @param  {!(Array|string)} events
	 * @return {!Array}
	 * @protected
	 */
	_toEventsArray(events) {
		if (typeof events === 'string') {
			singleArray[0] = events;
			events = singleArray;
		}

		return events;
	}

	/**
	 * Checks if the given listener is valid, throwing an exception when it's not.
	 * @param  {*} listener
	 * @protected
	 */
	_validateListener(listener) {
		if (typeof listener !== 'function') {
			throw new TypeError('Listener must be a function');
		}
	}
}

/**
 * Converts to an array
 * @param {Object} val
 * @return {Array}
 */
function toArray(val) {
	val = val || [];

	return Array.isArray(val) ? val : [val];
}

export default EventEmitter;
