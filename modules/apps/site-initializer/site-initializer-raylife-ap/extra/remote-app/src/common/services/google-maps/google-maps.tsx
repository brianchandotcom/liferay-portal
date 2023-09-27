/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Loader} from '@googlemaps/js-api-loader';

/**
 * @description Load google global variable asynchronously
 * @returns {void} Google Maps Autocomplete Instance
 */

type _adaptGoogleMapsAddressIntoAddress = (
	addressComponents: any
) => {
	city: string;
	country: string;
	state: string;
	street: string;
	streetNumber: string;
	zip: string;
};

const setup = (GOOGLE_API: any) => {
	try {
		const googleMapsLoader = new Loader({
			apiKey: GOOGLE_API,
			libraries: ['places'],
		});

		googleMapsLoader.load();
	}
	catch (error) {
		console.warn(error);
	}
};

/**
 * @param {HTMLElement} input - Pass HTML element
 * @returns {any} Google Maps Autocomplete Instance
 */
const autocomplete = (input: any) => {
	if (!google) {
		throw new Error(
			'google is not defined. Please check the Google Maps API key within System Settings and ensure a valid API key is entered'
		);
	}

	if (!input) {
		throw new Error(
			'No HTMLElement was found as input. Ensure a valid HTMLElement reference is passed!'
		);
	}

	// Prevent crashes if the user hits enter in a autocomplete search

	input.addEventListener('keydown', (event: any) => {
		if (event.keyCode === 13 || event.key === 'Enter') {
			event.preventDefault();
		}
	});

	return new google.maps.places.Autocomplete(input, {
		componentRestrictions: {country: 'us'},
		fields: ['address_components'],
	});
};

/**
 * @returns {any} Google Maps InfoWindow Instance
 */
const InfoWindow = () => {
	if (!google) {
		throw new Error(
			'google is not defined. Please check the Google Maps API key within System Settings and ensure a valid API key is entered'
		);
	}

	return new google.maps.InfoWindow();
};

const GeoLocation = async (addressLocation: string) => {
	let lat = 0;
	let lng = 0;
	const address = addressLocation;

	const google = window.google;

	if (google) {
		const geocoder = new google.maps.Geocoder();

		await geocoder.geocode({address}, (results, status) => {
			if (status === google.maps.GeocoderStatus.OK && results) {
				lat = results[0].geometry.location.lat();
				lng = results[0].geometry.location.lng();
			}
		});
	}

	return [lat, lng];
};

/**
 * @param {any} autocomplete - Google Maps Autocomplete Instance
 * @returns {Address} Normalized Address Object
 */
const getAutocompletePlaces = (autocomplete: any) => {
	const place = autocomplete.getPlace();

	return _adaptGoogleMapsAddressIntoAddress(place.address_components);
};

/**
 * @param {Array} addressComponents - Google Maps Address Component
 * @returns {Address} Normalized Address Object
 */
const _adaptGoogleMapsAddressIntoAddress = (addressComponents: any) => {
	const address = {
		city: '',
		country: '',
		state: '',
		street: '',
		streetNumber: '',
		zip: '',
	};

	addressComponents.forEach(({long_name, short_name, types}: any) => {
		switch (types[0]) {
			case 'street_number':
				address.streetNumber = long_name;
				break;

			case 'route':
				address.street = long_name;
				break;

			case 'locality':
				address.city = long_name;
				break;

			case 'administrative_area_level_1':
				address.state = short_name;
				break;

			case 'country':
				address.country = long_name;
				break;

			case 'postal_code':
				address.zip = short_name;
				break;

			default:
				break;
		}
	});

	return address;
};

export const GoogleMapsService = {
	GeoLocation,
	InfoWindow,
	autocomplete,
	getAutocompletePlaces,
	setup,
};
