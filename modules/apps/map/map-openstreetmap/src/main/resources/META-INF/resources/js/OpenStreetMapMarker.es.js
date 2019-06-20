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
 * OpenStreetMapMarker
 * @review
 */
class OpenStreetMapMarker extends MarkerBase {
	/**
	 * @inheritDoc
	 * @review
	 */
	_getNativeMarker(location, map) {
		if (!this._nativeMarker) {
			this._nativeMarker = L.marker(location, {
				draggable: true
			}).addTo(map);

			this._nativeMarker.on(
				'click',
				this._getNativeEventFunction('click')
			);
			this._nativeMarker.on(
				'dblclick',
				this._getNativeEventFunction('dblclick')
			);
			this._nativeMarker.on('drag', this._getNativeEventFunction('drag'));
			this._nativeMarker.on(
				'dragend',
				this._getNativeEventFunction('dragend')
			);
			this._nativeMarker.on(
				'dragstart',
				this._getNativeEventFunction('dragstart')
			);
			this._nativeMarker.on(
				'mousedown',
				this._getNativeEventFunction('mousedown')
			);
			this._nativeMarker.on(
				'mouseout',
				this._getNativeEventFunction('mouseout')
			);
			this._nativeMarker.on(
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
			location: nativeEvent.target.getLatLng()
		};
	}

	/**
	 * If a marked has been created, sets the marker location to the given one
	 * @param {Object} location Location to set the native marker in
	 * @review
	 */
	setPosition(location) {
		if (this._nativeMarker) {
			this._nativeMarker.setLatLng(location);
		}
	}
}

export default OpenStreetMapMarker;
export {OpenStreetMapMarker};
