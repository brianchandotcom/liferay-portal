'use strict';

import {isDefAndNotNull, isFunction, isString} from 'metal';

const portletRegex = '^portlet[.].*';

/**
 * PortletInit
 * @review
 */

class PortletInit {

	/**
	 * Constructor for PortletInit
	 * @param {string} portletId
	 * @review
	 */

	constructor(portletId) {
		this._portletId = portletId;
	}

	/**
	 * Initiates a portlet action using the specified action parameters and
	 * element arguments.
	 *
	 * @memberof PortletInit
	 * @param {PortletParameters} params Action parameters to be added to the
	 * URL
	 * @param {HTMLFormElement} element DOM element of form to be submitted
	 * @returns {Promise} A Promise object that is resolved with no argument
	 * when the action request has completed.
	 * @review
	 * @throws {TypeError} Thrown if the input parameters are invalid
	 * @throws {AccessDeniedException} Thrown if a blocking operation is
	 * already in progress.
	 * @throws {NotInitializedException} Thrown if a portlet ID is provided,
	 * but no onStateChange listener has been registered.
	 */

	action(params, element) {
		throw new Error('"action" is not yet implemented');
	}

	/**
	 * Adds a listener function for specified event type.
	 *
	 * @memberof PortletInit
	 * @param {string} type The type of listener
	 * @param {function} handler Function called when event occurs
	 * @returns {object} A handle that can be used to remove the event listener
	 * @review
	 * @throws {TypeError} Thrown if the input parameters are invalid
	 */

	addEventListener(type, handler) {
		if (arguments.length > 2) {
			throw new TypeError(
				'Too many arguments passed to addEventListener'
			);
		}

		if (!isString(type) || !isFunction(handler)) {
			throw new TypeError(
				'Invalid arguments passed to addEventListener'
			);
		}

		if (type.startsWith('portlet.')) {
			throw new Error('System event listeners are not yet implemented');
		}
		else {
			return this._addClientEventListener(type, handler);
		}
	}

	/**
	 * Returns a promise for a resource URL with parameters set appropriately
	 * for the page state according to the resource parameters, cacheability
	 * option, and resource ID provided.
	 *
	 * @memberof   PortletInit
	 * @param {PortletParameters} params Resource parameters to be added to
	 * the URL
	 * @param {string} cache Cacheability option. The strings defined under
	 * {@link PortletConstants} should be used to specifiy cacheability.
	 * @param {string} resourceId Resource ID.
	 * @returns {Promise} A Promise object. Returns a string representing the
	 * resource URL on successful resolution. Returns an Error object containing
	 * a descriptive message on failure.
	 * @review
	 * @throws {TypeError} Thrown if the input parameters are invalid
	 */

	createResourceUrl(params, cache, resourceId) {
		throw new Error('"createResourceUrl" is not yet implemented');
	}

	/**
	 * Dispatches a client event.
	 *
	 * @memberof PortletInit
	 * @param {string} type The type of listener
	 * @param {any} payload The payload to be delivered
	 * @returns {number} The number of events queued for delivery
	 * @review
	 * @throws {TypeError} Thrown if the type is a system event type
	 */

	dispatchClientEvent(type, payload) {
		if (arguments.length > 2) {
			throw new TypeError(
				'Too many arguments passed to dispatchClientEvent'
			);
		}

		if (!isString(type)) {
			throw new TypeError('Event type must be a string');
		}

		if (type.match(portletRegex)) {
			throw new TypeError('The event type is invalid: ' + type);
		}

		return PortletInit._clientEventListeners.reduce(
			(amount, listener) => {
				if (type.match(listener.type)) {
					listener.handler(payload);

					amount++;
				}

				return amount;
			},
			0
		);
	}

	/**
	 * Tests whether a blocking operation is in progress.
	 *
	 * @memberof PortletInit
	 * @returns {boolean} true if a blocking
	 * @review
	 */

	isInProgress() {
		return PortletInit._busy;
	}

	/**
	 * Creates and returns a new PortletParameters object.
	 *
	 * @memberof PortletInit
	 * @param {PortletParameters} param An optional PortletParameters object to
	 * be copied
	 * @returns {PortletParameters} The new parameters object
	 * @review
	 */

	newParameters(param) {
		throw new Error('"newParameters" is not yet implemented');
	}

	/**
	 * Creates and returns a new RenderState object.
	 *
	 * @memberof PortletInit
	 * @param {RenderState} state An optional RenderState object to be copied
	 * @returns {RenderState} The new RenderState object
	 * @review
	 */

	newState(state) {
		throw new Error('"newState" is not yet implemented');
	}

	/**
	 * Removes a previously added listener function designated by the handle.
	 * The handle must be the same object previously returned by the
	 * addEventListener function.
	 *
	 * @memberof PortletInit
	 * @param {object} handle The handle of the listener to be removed
	 * @review
	 * @throws {TypeError} Thrown if the input parameters are invalid
	 * @throws {AccessDeniedException} Thrown if the event listener associated
	 * with this handle was registered by a different portlet
	 */

	removeEventListener(handle) {
		if (arguments.length > 1) {
			throw new TypeError(
				'Too many arguments passed to removeEventListener'
			);
		}

		if (!isDefAndNotNull(handle)) {
			throw new TypeError(
				'The event handle provided is ' + typeof handle
			);
		}

		// Currently only checking for client events, eventually system events
		// will need to be checked as well.

		const index = PortletInit._clientEventListeners.indexOf(handle);

		if (index !== -1) {
			PortletInit._clientEventListeners.splice(index, 1);
		}
		else {
			throw new TypeError(
				'The event listener handle doesn\'t match any listeners.'
			);
		}
	}

	/**
	 * Sets the render state, which consists of the public and private render
	 * parameters, the portlet mode, and the window state.
	 *
	 * @memberof PortletInit
	 * @param {RenderState} state The new state to be set
	 * @review
	 * @throws {TypeError} Thrown if the input parameters are invalid
	 * @throws {AccessDeniedException} Thrown if a blocking operation is
	 * already in progress.
	 * @throws {NotInitializedException} Thrown if a portlet ID is provided, but
	 * no onStateChange listener has been registered.
	 */

	setRenderState(state) {
		throw new Error('"setRenderState" is not yet implemented');
	}

	/**
	 * Starts partial action processing and returns a
	 * {@link PartialActionInit} object to the caller.
	 *
	 * @memberof PortletInit
	 * @param {PortletParameters} params Action parameters to be added to the
	 * URL
	 * @returns {Promise} A Promise object. Returns a {PortletActionInit} object
	 * containing a partial action URL and the setPageState callback function on
	 * successful resolution. Returns an Error object containing a descriptive
	 * message on failure.
	 * @review
	 * @throws {TypeError} Thrown if the input parameters are invalid
	 * @throws {AccessDeniedException} Thrown if a blocking operation is already
	 * in progress.
	 * @throws {NotInitializedException} Thrown if a portlet ID is provided, but
	 * no onStateChange listener has been registered.
	 */

	startPartialAction(params) {
		throw new Error('"startPartialAction" is not yet implemented');
	}

	/**
	 * Adds a client event listener.
	 *
	 * @param {string} type The type of listener
	 * @param {function} handler Function called when event occurs
	 * @returns {object} A handle that can be used to remove the event listener
	 * @review
	 */

	_addClientEventListener(type, handler) {
		const listener = {
			handler,
			type
		};

		PortletInit._clientEventListeners.push(listener);

		return listener;
	}
}

/**
 * Determines if blocking action is currently in process.
 *
 * @memberof PortletInit
 * @review
 * @static
 * @type {boolean}
 */

PortletInit._busy = false;

/**
 * Contains client event listeners added from all instances of PortletInit.
 *
 * @memberof PortletInit
 * @review
 * @static
 * @type {array}
 */

PortletInit._clientEventListeners = [];

export default PortletInit;