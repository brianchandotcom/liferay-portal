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
