/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MapOpenStreetMap from './MapOpenStreetMap';

export default function App({
	boundingBox,
	data,
	geolocation,
	isMobile,
	latitude,
	longitude,
	name,
	portletId,
}) {
	const MapControls = Liferay.MapBase.CONTROLS;

	const mapConfig = {boundingBox, geolocation};

	if (geolocation) {
		if (isMobile) {
			mapConfig.controls = [MapControls.HOME, MapControls.SEARCH];
		}
		else {
			mapConfig.controls = [
				MapControls.HOME,
				MapControls.PAN,
				MapControls.SEARCH,
				MapControls.TYPE,
				MapControls.ZOOM,
			];
		}
	}

	if (data) {
		mapConfig.data = data;
	}

	if (latitude !== 0 && longitude !== 0) {
		mapConfig.position = {
			location: {
				lat: latitude,
				lng: longitude,
			},
		};
	}

	const createMap = function () {
		const map = new MapOpenStreetMap(mapConfig);

		Liferay.MapBase.register(name, map, portletId);
	};

	createMap();
}
