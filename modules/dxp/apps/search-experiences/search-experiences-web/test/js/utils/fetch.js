/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {addParams} from '../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/fetch';

describe('fetch', () => {
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
});
