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

import State from 'metal-state';

/**
 * OpenStreetMapGeocoder
 */
class OpenStreetMapGeocoder extends State {
	/**
	 * Handles the server response of a successfull address forward
	 * @param {Object} response Server response
	 * @param {function} callback Callback that will be executed on success
	 * @protected
	 * @review
	 */
	_handleForwardJSONP(response, callback) {
		callback(response);
	}

	/**
	 * Handles the server response of a successfull location reverse
	 * @param {Object} response Server response
	 * @param {function} callback Callback that will be executed on success
	 * @protected
	 * @review
	 */
	_handleReverseJSONP({display_name, error, lat, lon}, callback) {
		const result = {
			data: {},
			err: error
		};

		if (!result.err) {
			result.data = {
				address: display_name,
				location: {
					lat: parseFloat(lat) || 0,
					lng: parseFloat(lon) || 0
				}
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
		AUI().use('jsonp', A => {
			const forwardUrl = OpenStreetMapGeocoder.TPL_FORWARD_GEOCODING_URL.replace(
				'{query}',
				query
			);

			A.jsonp(forwardUrl, {
				context: this,
				on: {
					success: A.rbind('_handleForwardJSONP', this, callback)
				}
			});
		});
	}

	/**
	 * Transforms a given location object (lat, lng) into a valid address
	 * @param {string} location Location information to be sent to the server
	 * @param {function} callback Callback that will be executed on success
	 * @review
	 */
	reverse(location, callback) {
		AUI().use('jsonp', A => {
			const reverseUrl = OpenStreetMapGeocoder.TPL_REVERSE_GEOCODING_URL.replace(
				'{lat}',
				location.lat
			).replace('{lng}', location.lng);

			A.jsonp(reverseUrl, {
				context: this,
				on: {
					success: A.rbind('_handleReverseJSONP', this, callback)
				}
			});
		});
	}
}

/**
 * Url template used for OpenStreetMapGeocoder.forward() method
 * @review
 * @see OpenStreetMapGeocoder.forward()
 * @type {string}
 */
OpenStreetMapGeocoder.TPL_FORWARD_GEOCODING_URL =
	'//nominatim.openstreetmap.org/search?format=json&json_callback={callback}&q={query}';

/**
 * Url template used for OpenStreetMapGeocoder.reverse() method
 * @review
 * @see OpenStreetMapGeocoder.reverse()
 * @type {string}
 */
OpenStreetMapGeocoder.TPL_REVERSE_GEOCODING_URL =
	'//nominatim.openstreetmap.org/reverse?format=json&json_callback={callback}&lat={lat}&lon={lng}';

export default OpenStreetMapGeocoder;
export {OpenStreetMapGeocoder};
