/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import {GoogleMapsService} from '../../../../../../common/services/google-maps/google-maps';
import {getWebDavUrl} from '../../../../../../common/utils/webdav';

type LocationType = {
	address: string;
};

const GoogleMaps = ({address}: LocationType) => {
	const [position, setPosition] = useState({lat: -8.03437, lng: -34.92374});
	const [hasGoogleKey, setGoogleKey] = useState<boolean>();

	useEffect(() => {
		try {
			(async () => {
				const [
					latitude,
					longitude,
				] = await GoogleMapsService.GeoLocation(address);

				setPosition({lat: latitude, lng: longitude});
			})();
		}
		catch (error) {
			setGoogleKey(false);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		try {
			const mapOptions = {
				center: position,
				zoom: 15,
			};

			const google = window.google;

			const googleMap = new google.maps.Map(
				document.querySelector('.google-maps-container') as HTMLElement,
				mapOptions
			);

			const marker = new google.maps.Marker({
				position,
				title: 'Liferay location',
			});

			marker.setMap(googleMap);
		}
		catch (error) {
			setGoogleKey(false);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [position]);

	return hasGoogleKey ? (
		<></>
	) : (
		<img
			className="img-fluid"
			src={`${getWebDavUrl()}/google-maps-fallback.svg`}
		/>
	);
};

export default GoogleMaps;
