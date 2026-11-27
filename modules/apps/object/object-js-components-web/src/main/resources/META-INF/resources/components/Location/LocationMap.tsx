/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';

import {LocationValue, MAP_PROVIDER, MapProviderKey} from './locationUtil';
import {useLocationMap} from './useLocationMap';

import './LocationInput.scss';

interface LocationMapProps {
	googleMapsAPIKey?: string;
	id: string;
	mapProviderKey?: MapProviderKey;
	popover?: 'always' | 'click' | 'hover';
	title?: string;
	value: LocationValue;
}

function getPopoverContent(title: string, address: string) {
	const content = document.createElement('div');

	if (title) {
		const titleElement = document.createElement('div');

		titleElement.className = 'font-weight-semi-bold';
		titleElement.textContent = title;

		content.appendChild(titleElement);
	}

	if (address) {
		const addressElement = document.createElement('div');

		addressElement.textContent = address;

		content.appendChild(addressElement);
	}

	return content;
}

/**
 * Renders a read-only map with a pin on `value`. The title and the address
 * are shown next to the pin when it is clicked, on hover, or all the time,
 * depending on `popover`.
 */
export function LocationMap({
	googleMapsAPIKey,
	id,
	mapProviderKey = MAP_PROVIDER.openStreetMap,
	popover = 'click',
	title = '',
	value,
}: LocationMapProps) {
	const [mapElementId] = useState(() => `${id}_map`.replace(/[^\w-]/g, '_'));

	const handleMapReady = (map: any) => {
		const marker = map._geolocationMarker;

		if (!marker || (!title && !value.address)) {
			return;
		}

		const content = getPopoverContent(title, value.address);
		const nativeMarker = marker._nativeMarker;

		if (mapProviderKey === MAP_PROVIDER.googleMaps) {
			const infoWindow = new (window as any).google.maps.InfoWindow({
				content,
				disableAutoPan: true,
			});

			const open = () =>
				infoWindow.open({
					anchor: nativeMarker,
					map: map.getNativeMap(),
				});

			if (popover === 'always') {
				open();

				return;
			}

			if (popover === 'click') {
				marker.on('click', open);

				return;
			}

			marker.on('mouseover', open);
			marker.on('mouseout', () => infoWindow.close());

			return;
		}

		const {iconAnchor, tooltipAnchor} = nativeMarker.options.icon.options;

		nativeMarker.bindTooltip(content, {
			direction: 'top',
			offset: [-tooltipAnchor[0], -(iconAnchor[1] + tooltipAnchor[1])],
			permanent: popover !== 'hover',
		});

		if (popover === 'click') {
			nativeMarker.closeTooltip();

			marker.on('click', () => nativeMarker.toggleTooltip());
		}
	};

	useLocationMap({
		disabled: true,
		googleMapsAPIKey,
		mapElementId,
		mapProviderKey,
		onMapReady: handleMapReady,
		onPositionChange: () => {},
		value,
	});

	return <div className="object-field__location-map" id={mapElementId} />;
}
