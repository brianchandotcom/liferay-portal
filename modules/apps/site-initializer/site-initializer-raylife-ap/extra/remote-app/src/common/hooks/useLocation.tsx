/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		}
		catch (error) {
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
		}
		catch (error) {
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
