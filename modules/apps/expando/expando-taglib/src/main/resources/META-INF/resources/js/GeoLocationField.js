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

import MapBase from '@liferay/map-common/js/MapBase.es';

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
