/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
