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

import setStoreValue from '../../../../src/main/resources/META-INF/resources/liferay/util/store/set_store_value.es';

describe('Liferay.Util.Store.setStoreValue', () => {
	it('throws error if key parameter is not a string', () => {
		expect(() => setStoreValue(0, '')).toThrow('must be a string');
	});

	it('throws error if value parameter is not an object or string', () => {
		expect(() => setStoreValue('', 0)).toThrow(
			'must be boolean or an object or string'
		);
	});

	it('sets the value of a Store entry with given key', () => {
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

		const key = 'foo';
		const value = 'abc';

		global.fetch = jest.fn((resource, init) => {
			expect(resource).toEqual(
				'http://sampleurl.com/portal/session_click?p_auth=abcd&doAsUserId=efgh'
			);

			const formData = new FormData();

			formData.append(key, value);

			expect(init.body).toEqual(formData);
			expect(init.method).toEqual('POST');
		});

		setStoreValue(key, value);

		global.Liferay = globalLiferay;
	});
});
