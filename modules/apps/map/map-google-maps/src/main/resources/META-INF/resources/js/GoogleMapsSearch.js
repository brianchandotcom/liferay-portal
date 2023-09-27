/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EventEmitter} from 'frontend-js-web';

/**
 * GoogleMapsSearch
 * @review
 */
class GoogleMapsSearch extends EventEmitter {
	get inputNode() {
		return this._STATE_.inputNode;
	}

	set inputNode(inputNode) {
		this._STATE_.inputNode = inputNode;
	}

	/**
	 * Creates a new search handler using Google Map's API
	 * @param  {Array} args List of arguments to be passed to State
	 * @review
	 */
	constructor(args) {
		super(args);

		const {inputNode} = args;

		this._STATE_ = {
			inputNode,
		};

		this._handlePlaceChanged = this._handlePlaceChanged.bind(this);

		this._autocomplete = new google.maps.places.Autocomplete(inputNode);

		this._bindUI();
	}

	/**
	 * Removes the listeners that have been added to the search input.
	 * @review
	 */
	destructor() {
		this._eventHandlers.forEach((item) => {
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
			),
			google.maps.event.addDomListener(
				this.inputNode,
				'keydown',
				(event) => {
					if (event.keyCode === 13) {
						event.preventDefault();
					}
				}
			),
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
						lng: geolocation.lng(),
					},
				},
			});
		}
	}
}

export default GoogleMapsSearch;
export {GoogleMapsSearch};
