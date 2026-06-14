/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MapGoogleMaps from '../../src/main/resources/META-INF/resources/js/MapGoogleMaps';

describe('MapGoogleMaps', () => {
	const getLocation = () => ({lat: Math.random(), lng: Math.random()});

	const getMap = () => ({
		controls: {},
		data: {setStyle() {}},
		fitBounds: jest.fn(),
		setCenter: jest.fn(),
		setZoom: jest.fn(),
	});

	let map;
	let mapImpl;

	class MapImpl extends MapGoogleMaps {
		_createMap() {
			return getMap();
		}
	}

	beforeEach(() => {
		global.google = {
			maps: {
				Geocoder: jest.fn(),
				event: {addListener: jest.fn()},
			},
		};

		mapImpl = new MapImpl();

		map = getMap();

		mapImpl._map = map;
	});

	describe('_handleSearchButtonClicked()', () => {
		it('updates the instance position', () => {
			const position = {location: getLocation()};

			mapImpl._handleSearchButtonClicked({position});

			expect(mapImpl.position).toBe(position);
		});

		it('fits the map to the viewport when the position has one', () => {
			const position = {location: getLocation(), viewport: {name: 'box'}};

			mapImpl._handleSearchButtonClicked({position});

			expect(map.fitBounds).toHaveBeenCalledTimes(1);
			expect(map.fitBounds.mock.calls[0][0]).toBe(position.viewport);
		});

		it('does not set a fallback zoom when the position has a viewport', () => {
			const position = {location: getLocation(), viewport: {name: 'box'}};

			mapImpl._handleSearchButtonClicked({position});

			expect(map.setZoom).not.toHaveBeenCalled();
		});

		it('centers the map and applies the fallback zoom when the position has no viewport', () => {
			const position = {location: getLocation()};

			mapImpl._handleSearchButtonClicked({position});

			expect(map.setCenter.mock.calls[0][0]).toBe(position.location);
			expect(map.setZoom).toHaveBeenCalledTimes(1);
			expect(map.setZoom.mock.calls[0][0]).toBe(17);
		});
	});
});
