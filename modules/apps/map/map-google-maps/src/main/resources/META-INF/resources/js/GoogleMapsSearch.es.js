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

import {isInputNode} from 'map-common/js/validators.es';
import {toElement} from 'metal-dom';
import State, {Config} from 'metal-state';

/**
 * GoogleMapsSearch
 * @review
 */
class GoogleMapsSearch extends State {
	/**
	 * Creates a new search handler using Google Map's API
	 * @param  {Array} args List of arguments to be passed to State
	 * @review
	 */
	constructor(...args) {
		super(...args);
		const inputNode = toElement(this.inputNode);
		this._handlePlaceChanged = this._handlePlaceChanged.bind(this);

		this._autocomplete = new google.maps.places.Autocomplete(inputNode);
		this._bindUI();
	}

	/**
	 * Removes the listeners that have been added to the search input.
	 * @review
	 */
	destructor() {
		this._eventHandlers.forEach(item => {
			google.maps.event.removeListener(item);
		});
	}

	/**
	 * Adds listeners for the created map object.
	 * It listens for a custom 'place_changed' event and executes
	 * GoogleMapsSearch._handlePlaceChanged.
	 * @protected
	 * @review
	 */
	_bindUI() {
		this._eventHandlers = [
			google.maps.event.addListener(
				this._autocomplete,
				'place_changed',
				this._handlePlaceChanged
			)
		];
	}

	/**
	 * Gets the new place that has been processed by Google Maps and emits a
	 * 'search' event with the location information and the address.
	 * @protected
	 * @review
	 */
	_handlePlaceChanged() {
		const place = this._autocomplete.getPlace();

		if (place && typeof place === 'object' && place.geometry) {
			const geolocation = place.geometry.location;

			this.emit('search', {
				position: {
					address: place.formatted_address,
					location: {
						lat: geolocation.lat(),
						lng: geolocation.lng()
					}
				}
			});
		}
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
GoogleMapsSearch.STATE = {
	/**
	 * Input element that will be used for searching addresses.
	 * @review
	 * @type {HTMLInputElement}
	 */
	inputNode: Config.validator(isInputNode).value(null)
};

export default GoogleMapsSearch;
export {GoogleMapsSearch};
