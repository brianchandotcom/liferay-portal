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

import getStoreValue from '../../../../src/main/resources/META-INF/resources/liferay/util/store/get_store_value.es';

describe('Liferay.Util.Store.getStoreValue', () => {
	it('throws error if key parameter is not a string', () => {
		expect(() => getStoreValue(1)).toThrow(
			'Parameter key must be a string'
		);
	});

	it('gets the value of a Store entry with the given key', () => {
		let globalLiferay = global.Liferay;

		global.Liferay = {
			authToken: 'abcd',
			ThemeDisplay: {
				getPathMain: jest.fn(() => {
					return 'http://sampleurl.com';
				}),
				getPortalURL: jest.fn(() => {
					return 'http://localhost:8888';
				}),
				getDoAsUserIdEncoded: jest.fn(() => {
					return 'efgh';
				})
			}
		};

		const storedValue = 'fooValue';

		global.fetch = jest.fn(resource => {
			expect(resource).toEqual(
				'http://sampleurl.com/portal/session_click?cmd=get&key=foo&p_auth=abcd&doAsUserId=efgh'
			);

			return Promise.resolve({
				text: jest.fn(() => Promise.resolve(storedValue))
			});
		});

		const callback = value => {
			expect(value).toEqual(storedValue);
		};

		getStoreValue('foo', callback);

		global.Liferay = globalLiferay;
	});
});
