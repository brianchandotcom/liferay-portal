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

import {useEffect, useRef, useState} from 'react';

const loadScript = (readOnly, elementId, googlePlacesAPIKey, callback) => {
	let script = document.getElementById('googleMapsScript');

	if (!script) {
		script = document.createElement('script');

		script.addEventListener('load', () => {
			script.setAttribute('data-loaded', 'true');
		});

		script.setAttribute('id', 'googleMapsScript');
		script.setAttribute('type', 'text/javascript');

		let url = 'https://maps.googleapis.com/maps/api/js?libraries=places';

		if (googlePlacesAPIKey) {
			url += '&key=' + googlePlacesAPIKey;
		}

		script.setAttribute('src', url);
	}

	const dataLoaded = script.dataset.loaded;

	if (dataLoaded) {
		callback();
	}
	else {
		script.addEventListener('load', callback);
	}

	const element = document.getElementById(elementId);
	/* eslint-disable-next-line no-unused-expressions */
	element && !readOnly ? element.appendChild(script) : null;
};

function handleScriptLoad(autoComplete, elementId, setListener, setValue) {
	const element = document.getElementById(elementId);
	autoComplete.current = new window.google.maps.places.Autocomplete(element);
	autoComplete.current.setFields(['address_component', 'formatted_address']);
	const listener = autoComplete.current.addListener('place_changed', () =>
		handlePlaceSelect(autoComplete, setValue)
	);
	setListener(listener);
}

async function handlePlaceSelect(autoComplete, setValue) {
	const place = autoComplete.current.getPlace();
	const addressComponents = place?.address_components;
	const addressTypes = {
		administrative_area_level_1: 'short_name',
		administrative_area_level_2: 'long_name',
		country: 'long_name',
		locality: 'long_name',
		postal_code: 'short_name',
		route: 'long_name',
		street_number: 'long_name',
	};
	const address = {
		administrative_area_level_1: '',
		administrative_area_level_2: '',
		country: '',
		formatted_address: place?.formatted_address,
		locality: '',
		postal_code: '',
		route: '',
		street_number: '',
	};

	for (let i = 0; i < addressComponents.length; i++) {
		const addressType = addressComponents[i].types[0];

		address[addressType] = addressComponents[i][addressTypes[addressType]];
	}

	setValue({
		target: {
			value: JSON.stringify({
				address: address.street_number + ' ' + address.route,
				city: address.locality,
				country: address.country,
				place: address.formatted_address,
				['postal-code']: address.postal_code,
				state: address.administrative_area_level_1,
			}),
		},
	});
}

const usePlaces = ({
	elementId,
	googlePlacesAPIKey,
	isReadOnly,
	onChange,
	viewMode,
}) => {
	const autoCompleteRef = useRef();
	const [listener, setListener] = useState();
	const [value, setValue] = useState();

	useEffect(() => {
		if (viewMode) {
			loadScript(isReadOnly, elementId, googlePlacesAPIKey, () =>
				handleScriptLoad(
					autoCompleteRef,
					elementId,
					setListener,
					setValue
				)
			);

			return () => {
				window.google.maps.event.removeListener(listener);
			};
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		if (viewMode && value) {
			onChange(value);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [value]);

	useEffect(() => {
		const onScroll = () => {
			const autoCompleteDropdown = document.querySelector(
				'.pac-container:not([style*="display: none"])'
			);
			const element = document.getElementById(elementId);

			if (autoCompleteDropdown && element === document.activeElement) {
				const {height, top} = element?.getBoundingClientRect();
				const scrollTop =
					window.pageYOffset || document.documentElement.scrollTop;

				autoCompleteDropdown.style.top =
					height + scrollTop + top + 'px';
			}
		};

		document.addEventListener('scroll', onScroll, true);

		return () => document.removeEventListener('scroll', onScroll, true);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);
};

export default usePlaces;
