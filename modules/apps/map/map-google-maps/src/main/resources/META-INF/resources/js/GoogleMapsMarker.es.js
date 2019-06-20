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

import MarkerBase from 'map-common/js/MarkerBase.es';

/**
 * GoogleMapsMarker
 * @review
 */
class GoogleMapsMarker extends MarkerBase {
	/**
	 * If a marked has been created, sets the marker location to the given one
	 * @param {Object} location Location to set the native marker in
	 * @review
	 */
	setPosition(location) {
		if (this._nativeMarker) {
			this._nativeMarker.setPosition(location);
		}
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	_getNativeMarker(location, map) {
		if (!this._nativeMarker) {
			this._nativeMarker = new google.maps.Marker({
				draggable: true,
				map,
				position: location
			});

			google.maps.event.addListener(
				this._nativeMarker,
				'click',
				this._getNativeEventFunction('click')
			);

			google.maps.event.addListener(
				this._nativeMarker,
				'dblclick',
				this._getNativeEventFunction('dblclick')
			);

			google.maps.event.addListener(
				this._nativeMarker,
				'drag',
				this._getNativeEventFunction('drag')
			);

			google.maps.event.addListener(
				this._nativeMarker,
				'dragend',
				this._getNativeEventFunction('dragend')
			);

			google.maps.event.addListener(
				this._nativeMarker,
				'dragstart',
				this._getNativeEventFunction('dragstart')
			);

			google.maps.event.addListener(
				this._nativeMarker,
				'mousedown',
				this._getNativeEventFunction('mousedown')
			);

			google.maps.event.addListener(
				this._nativeMarker,
				'mouseout',
				this._getNativeEventFunction('mouseout')
			);

			google.maps.event.addListener(
				this._nativeMarker,
				'mouseover',
				this._getNativeEventFunction('mouseover')
			);
		}

		return this._nativeMarker;
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	_getNormalizedEventData(nativeEvent) {
		return {
			location: {
				lat: nativeEvent.latLng.lat(),
				lng: nativeEvent.latLng.lng()
			}
		};
	}
}

export default GoogleMapsMarker;
export {GoogleMapsMarker};
