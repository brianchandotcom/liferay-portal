/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MapBase from '@liferay/map-common/js/MapBase';

export default function ({inputName, mapName}) {
	MapBase.get(mapName, (map) => {
		map.on('positionChange', (event) => {
			const inputNode = document.querySelector(
				'[name="' + inputName + '"]'
			);

			const location = event.newVal.location;

			if (inputNode) {
				inputNode.setAttribute(
					'value',
					JSON.stringify({
						latitude: location.lat,
						longitude: location.lng,
					})
				);
			}

			const locationNode = document.getElementById(
				inputName + 'Location'
			);

			if (locationNode) {
				locationNode.innerHTML = event.newVal.address;
			}
		});
	});
}
