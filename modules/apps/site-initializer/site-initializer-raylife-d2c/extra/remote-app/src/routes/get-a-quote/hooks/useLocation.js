/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import {GoogleMapsService} from '../../../common/services/google-maps';
import {MockService} from '../../../common/services/mock';

export function useLocation() {
	const [data, setData] = useState();
	const [error, setError] = useState();

	useEffect(() => {
		_loadUSStates();
	}, []);

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

	const setAutoComplete = (htmlElement, callback) => {
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
