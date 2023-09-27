/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import addParams from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/fetch/add_params';

Liferay.ThemeDisplay.getPathContext = () => '';

describe('addParams', () => {
	it('adds a parameter to a url', () => {
		expect(
			addParams('/o/search-experiences-rest/v1.0/sxp-elements', {
				test: 'value',
			}).href
		).toEqual(
			'http://localhost:8080/o/search-experiences-rest/v1.0/sxp-elements?test=value'
		);
	});

	it('adds multiple parameters to a url', () => {
		expect(
			addParams('/o/search-experiences-rest/v1.0/sxp-elements', {
				test1: 'value1',
				test2: 'value2',
			}).href
		).toEqual(
			'http://localhost:8080/o/search-experiences-rest/v1.0/sxp-elements?test1=value1&test2=value2'
		);
	});
});
