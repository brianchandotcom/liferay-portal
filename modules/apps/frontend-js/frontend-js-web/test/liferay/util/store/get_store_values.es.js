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

'use strict';

import getStoreValues from '../../../../src/main/resources/META-INF/resources/liferay/util/store/get_store_values.es';

describe('Liferay.Util.Store.getStoreValues', () => {
	it('throws error if keys parameter is not an array', () => {
		expect(() => getStoreValues(0)).toThrow('must be an array');
	});

	it('gets values of Store entries with given keys', () => {
		let globalLiferay = global.Liferay;

		global.Liferay = {
			authToken: 'abcd',
			ThemeDisplay: {
				getPathMain: jest.fn(() => {
					return 'http://sampleurl.com';
				}),
				getDoAsUserIdEncoded: jest.fn(() => {
					return 'efgh';
				})
			}
		};

		global.fetch = jest.fn((resource, init) => {
			expect(resource).toEqual(
				'http://sampleurl.com/portal/session_click?cmd=getAll&p_auth=abcd&doAsUserId=efgh'
			);

			const formData = new FormData();

			formData.append('key', '["foo","bar"]');

			expect(init.body).toEqual(formData);
			expect(init.method).toEqual('POST');

			return Promise.resolve({
				json: jest.fn(() => Promise.resolve('{foo: 1, bar: 2}'))
			});
		});

		const callback = values => {
			expect(values).toEqual('{foo: 1, bar: 2}');
		};

		getStoreValues(['foo', 'bar'], callback);

		global.Liferay = globalLiferay;
	});
});
