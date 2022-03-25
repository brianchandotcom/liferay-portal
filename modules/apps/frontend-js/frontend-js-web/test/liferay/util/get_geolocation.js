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

import getGeolocation from '../../../src/main/resources/META-INF/resources/liferay/util/get_geolocation';

describe('Liferay.Util.getGeolocation', () => {
	it('calls navigator.geolocation.getCurrentPosition if location is enabled', () => {
		const success = jest.fn();
		const fallback = jest.fn();

		const mockGeolocation = {
			getCurrentPosition: jest.fn(),
			watchPosition: jest.fn(),
		};

		global.navigator.geolocation = mockGeolocation;

		getGeolocation(success, fallback);

		expect(navigator.geolocation.getCurrentPosition).toHaveBeenCalled();
	});

	it('returns a null island location if location is disabled', () => {
		const success = jest.fn();
		const fallback = jest.fn();

		global.navigator.geolocation = null;

		getGeolocation(success, fallback);

		expect(fallback).toHaveBeenCalled();
	});
});
