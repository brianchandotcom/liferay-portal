/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render} from '@testing-library/react';
import React from 'react';

import {LocationMap} from '../../../components/Location/LocationMap';
import {useLocationMap} from '../../../components/Location/useLocationMap';

jest.mock('../../../components/Location/useLocationMap', () => ({
	useLocationMap: jest.fn(),
}));

const VALUE = {
	address: 'Gran Vía 1, Madrid',
	coordinates: {latitude: 40.42, longitude: -3.7},
};

function getMap() {
	const handlers: Record<string, () => void> = {};

	const nativeMarker = {
		bindTooltip: jest.fn(),
		closeTooltip: jest.fn(),
		options: {
			icon: {options: {iconAnchor: [12, 41], tooltipAnchor: [16, -28]}},
		},
		toggleTooltip: jest.fn(),
	};

	return {
		_geolocationMarker: {
			_nativeMarker: nativeMarker,
			on: jest.fn((eventName: string, handler: () => void) => {
				handlers[eventName] = handler;
			}),
		},
		getNativeMap: () => 'native-map',
		handlers,
		nativeMarker,
	};
}

function renderLocationMap(props: Partial<Parameters<typeof LocationMap>[0]>) {
	render(<LocationMap id="locationMap" value={VALUE} {...props} />);

	const {onMapReady} = (useLocationMap as jest.Mock).mock.calls[0][0];

	const map = getMap();

	onMapReady(map);

	return map;
}

describe('LocationMap', () => {
	afterEach(() => {
		jest.clearAllMocks();

		delete (window as any).google;
	});

	it('creates a read-only map on the value', () => {
		const {container} = render(
			<LocationMap id="locationMap" value={VALUE} />
		);

		const {disabled, mapElementId, value} = (useLocationMap as jest.Mock)
			.mock.calls[0][0];

		expect(disabled).toBe(true);
		expect(value).toBe(VALUE);
		expect(
			container.querySelector('.object-field__location-map')
		).toHaveAttribute('id', mapElementId);
	});

	it('keeps the popover open with Google Maps', () => {
		const infoWindow = {close: jest.fn(), open: jest.fn()};

		(window as any).google = {
			maps: {InfoWindow: jest.fn(() => infoWindow)},
		};

		const map = renderLocationMap({
			mapProviderKey: 'GoogleMaps',
			popover: 'always',
			title: 'Madrid Office',
		});

		expect(infoWindow.open).toHaveBeenCalled();
		expect(map._geolocationMarker.on).not.toHaveBeenCalled();
	});

	it('keeps the popover open with OpenStreetMap', () => {
		const {nativeMarker} = renderLocationMap({
			popover: 'always',
			title: 'Madrid Office',
		});

		expect(nativeMarker.bindTooltip.mock.calls[0][1]).toEqual(
			expect.objectContaining({permanent: true})
		);
	});

	it('opens and closes the popover on hover with Google Maps', () => {
		const infoWindow = {close: jest.fn(), open: jest.fn()};

		(window as any).google = {
			maps: {InfoWindow: jest.fn(() => infoWindow)},
		};

		const {handlers, nativeMarker} = renderLocationMap({
			mapProviderKey: 'GoogleMaps',
			popover: 'hover',
			title: 'Madrid Office',
		});

		expect(infoWindow.open).not.toHaveBeenCalled();

		handlers.mouseover();

		expect(infoWindow.open).toHaveBeenCalledWith({
			anchor: nativeMarker,
			map: 'native-map',
		});

		handlers.mouseout();

		expect(infoWindow.close).toHaveBeenCalled();
	});

	it('opens the popover on click with Google Maps', () => {
		const infoWindow = {close: jest.fn(), open: jest.fn()};

		(window as any).google = {
			maps: {InfoWindow: jest.fn(() => infoWindow)},
		};

		const {handlers, nativeMarker} = renderLocationMap({
			mapProviderKey: 'GoogleMaps',
			title: 'Madrid Office',
		});

		expect(infoWindow.open).not.toHaveBeenCalled();

		handlers.click();

		expect(infoWindow.open).toHaveBeenCalledWith({
			anchor: nativeMarker,
			map: 'native-map',
		});
	});

	it('shows no popover without a title or an address', () => {
		render(
			<LocationMap
				id="locationMap"
				popover="hover"
				value={{...VALUE, address: ''}}
			/>
		);

		const {onMapReady} = (useLocationMap as jest.Mock).mock.calls[0][0];

		const map = getMap();

		onMapReady(map);

		expect(map.nativeMarker.bindTooltip).not.toHaveBeenCalled();
	});

	it('shows the title and the address on hover with OpenStreetMap', () => {
		const {nativeMarker} = renderLocationMap({
			popover: 'hover',
			title: 'Madrid Office',
		});

		const [content, options] = nativeMarker.bindTooltip.mock.calls[0];

		expect(content).toHaveTextContent('Madrid Office');
		expect(content).toHaveTextContent('Gran Vía 1, Madrid');
		expect(options).toEqual(
			expect.objectContaining({
				direction: 'top',
				offset: [-16, -13],
				permanent: false,
			})
		);
	});

	it('toggles the popover on click with OpenStreetMap by default', () => {
		const {handlers, nativeMarker} = renderLocationMap({
			title: 'Madrid Office',
		});

		expect(nativeMarker.bindTooltip.mock.calls[0][1]).toEqual(
			expect.objectContaining({permanent: true})
		);
		expect(nativeMarker.closeTooltip).toHaveBeenCalled();
		expect(nativeMarker.toggleTooltip).not.toHaveBeenCalled();

		handlers.click();

		expect(nativeMarker.toggleTooltip).toHaveBeenCalled();
	});
});
