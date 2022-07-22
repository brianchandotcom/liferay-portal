/* eslint-disable no-console */
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

import {useEffect, useState} from 'react';

import {GoogleMapsService} from '../services/google-maps/google-maps';
import {MockService} from '../services/google-maps/mock';

export function useLocation() {
	const [data, setData] = useState<any>();
	const [error, setError] = useState<any>();

	const _loadUSStates = async () => {
		try {
			const response = await MockService.getUSStates();
			setData(response);
		} catch (error) {
			console.warn(error);
			setError(error);
		}
	};

	useEffect(() => {
		_loadUSStates();
	}, []);

	const setAutoComplete = (
		htmlElement: HTMLInputElement,
		callback: (address: any) => void
	) => {
		try {
			const autocomplete = GoogleMapsService.autocomplete(htmlElement);
			const infoWindow = GoogleMapsService.InfoWindow();

			autocomplete.addListener('place_changed', () => {
				infoWindow.close();

				const address = GoogleMapsService.getAutocompletePlaces(
					autocomplete
				);

				callback(address);
			});
		} catch (error) {
			console.warn(error);
		}
	};

	return {
		isError: error,
		isLoading: !data && !error,
		setAutoComplete,
		states: data || [],
	};
}
