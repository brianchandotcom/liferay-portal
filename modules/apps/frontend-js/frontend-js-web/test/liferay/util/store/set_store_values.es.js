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

import setStoreValues from '../../../../src/main/resources/META-INF/resources/liferay/util/store/set_store_values.es';

describe('Liferay.Util.Store.setStoreValues', () => {
	it('throws error if key parameter is not an object', () => {
		expect(() => setStoreValues(0)).toThrow('must be an object');
	});

	it('sets values of Store entries that match keys from given data object', () => {
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
				'http://sampleurl.com/portal/session_click?p_auth=abcd&doAsUserId=efgh'
			);

			const formData = new FormData();

			formData.append('foo', 'abc');

			expect(init.body).toEqual(formData);
			expect(init.method).toEqual('POST');
		});

		setStoreValues({foo: 'abc'});

		global.Liferay = globalLiferay;
	});
});
