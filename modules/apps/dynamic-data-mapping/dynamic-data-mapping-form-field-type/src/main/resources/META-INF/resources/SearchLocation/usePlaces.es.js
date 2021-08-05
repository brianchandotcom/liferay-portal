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
	const script = document.createElement('script');
	script.setAttribute('type', 'text/javascript');
	const scriptReadyState = script.getAttribute('readyState');

	if (scriptReadyState) {
		script.setAttribute('onreadystatechange', () => {
			if (
				scriptReadyState === 'loaded' ||
				scriptReadyState === 'complete'
			) {
				script.setAttribute('onreadystatechange', null);
				callback();
			}
		});
	}
	else {
		script.onload = () => callback();
	}

	let url = 'https://maps.googleapis.com/maps/api/js?libraries=places';

	if (googlePlacesAPIKey) {
		url += '&key=' + googlePlacesAPIKey;
	}

	script.setAttribute('src', url);
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
		postal_code: 'short_name',
		route: 'long_name',
	};
	const address = {
		administrative_area_level_1: '',
		administrative_area_level_2: '',
		country: '',
		formatted_address: place?.formatted_address,
		postal_code: '',
		route: '',
	};

	for (let i = 0; i < addressComponents.length; i++) {
		const addressType = addressComponents[i].types[0];

		address[addressType] = addressComponents[i][addressTypes[addressType]];
	}

	setValue({
		target: {
			value: JSON.stringify({
				address: address.route,
				city: address.administrative_area_level_2,
				country: address.country,
				place: address.formatted_address,
				['postal-code']: address.postal_code,
				state: address.administrative_area_level_1,
			}),
		},
	});
}

const usePlaces = ({elementId, googlePlacesAPIKey, isReadOnly, onChange}) => {
	const autoComplete = useRef();
	const [listener, setListener] = useState();
	const [value, setValue] = useState();

	useEffect(() => {
		loadScript(isReadOnly, elementId, googlePlacesAPIKey, () =>
			handleScriptLoad(autoComplete, elementId, setListener, setValue)
		);

		return () => {
			window.google.maps.event.removeListener(listener);
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		if (value) {
			onChange(value);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [value]);
};

export default usePlaces;
