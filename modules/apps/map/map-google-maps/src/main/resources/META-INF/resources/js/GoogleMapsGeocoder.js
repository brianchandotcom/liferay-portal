/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * GoogleMapsGeocoder
 * @review
 */
class GoogleMapsGeocoder {

	/**
	 * Creates a new geocoder using Google Map's API
	 * @review
	 */
	constructor() {
		this._geocoder = new google.maps.Geocoder();
	}

	/**
	 * Handles the server response of a successfull address/location resolution
	 * @param {function} callback Callback that will be executed on success
	 * @param {Object} location Raw location information
	 * @param {Object} response Server response
	 * @param {Object} status Server response status
	 * @protected
	 * @review
	 */
	_handleGeocoderResponse(callback, location, response, status) {
		const result = {
			data: {},
			error: status === google.maps.GeocoderStatus.OK ? null : status,
		};

		if (!result.error) {
			const geocoderResult = response[0];
			const geolocation = geocoderResult.geometry.location;

			result.data = {
				address: geocoderResult.formatted_address,
				location: {
					lat: geolocation.lat(),
					lng: geolocation.lng(),
				},
			};
		}
		else {
			result.data = {
				address: '',
				location,
			};
		}

		callback(result);
	}

	/**
	 * Transforms a given address into valid latitude and longitude
	 * @param {string} query Address to be transformed into latitude and longitude
	 * @param {function} callback Callback that will be executed on success
	 * @review
	 */
	forward(query, callback) {
		const payload = {
			address: query,
		};

		this._geocoder.geocode(
			payload,
			this._handleGeocoderResponse.bind(this, callback)
		);
	}

	/**
	 * Transforms a given location object (lat, lng) into a valid address
	 * @param {string} location Location information to be sent to the server
	 * @param {function} callback Callback that will be executed on success
	 * @review
	 */
	reverse(location, callback) {
		const payload = {
			location,
		};

		this._geocoder.geocode(
			payload,
			this._handleGeocoderResponse.bind(this, callback, location)
		);
	}
}

export default GoogleMapsGeocoder;
export {GoogleMapsGeocoder};
