/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EventEmitter} from 'frontend-js-web';

/**
 * MarkerBase
 * Each instance represents a marker being shown in the map.
 * The implemented abstract methods will allow adding markers
 * to the map instance and detecting click events on them.
 * @abstract
 * @review
 */
export default class MarkerBase extends EventEmitter {

	/**
	 * Initializes the instance with a native marker which will handle all
	 * the execution. This function may be moved to the class constructor in
	 * the future, but currently it supports the legacy API.
	 * @review
	 */
	constructor({location, map}) {
		super({location, map});

		this.location = location;
		this.map = map;

		this._nativeMarker = this._getNativeMarker(this.location, this.map);
	}

	/**
	 * Generates a function that, when fired, emits an event with a normalized
	 * version of the received event and the given event type.
	 * @param {string} externalEventType Event that will be emited when the
	 * 	function is executed.
	 * @return {function} Generated event handler.
	 * @review
	 */
	_getNativeEventFunction(externalEventType) {
		const functionName = `_nativeEventHandler_${externalEventType}`;

		this[functionName] =
			this[functionName] ||
			((nativeEvent) => {
				this._handleNativeEvent(nativeEvent, externalEventType);
			});

		return this[functionName];
	}

	/**
	 * Returns a nativeMarket object for a given location and map.
	 * At this point any extra event should be added to the object, using
	 * the implemented method _handleNativeEvent as handler.
	 * @abstract
	 * @param {Object} location
	 * @param {Object} map
	 * @return {Object} Generated native marker
	 * @review
	 */
	_getNativeMarker(_location, _map) {
		throw new Error('Must be implemented');
	}

	/**
	 * For a given event, it returns a normalized version of it
	 * with a common structure
	 * @abstract
	 * @param {Event} nativeEvent
	 * @return {{ lat: number, lng: number }}
	 * @review
	 */
	_getNormalizedEventData(_nativeEvent) {
		throw new Error('Must be implemented');
	}

	/**
	 * Parses the given nativeEvent and emits a new event with the given
	 * event type.
	 * @param {Event} nativeEvent Native event that will be parsed.
	 * @param {string} externalEventType Event type that will be emitted.
	 * @review
	 */
	_handleNativeEvent(nativeEvent, externalEventType) {
		this.emit(externalEventType, this._getNormalizedEventData(nativeEvent));
	}
}

window.Liferay = window.Liferay || {};

window.Liferay.MapMarkerBase = MarkerBase;
