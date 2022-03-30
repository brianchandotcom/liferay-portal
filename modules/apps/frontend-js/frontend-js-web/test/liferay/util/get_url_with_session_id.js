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

import getURLWithSessionId from '../../../src/main/resources/META-INF/resources/liferay/util/get_url_with_session_id';

const BASE_URL = 'http://www.test.com';
const SESSION_ID = 'jsessionid=foo';

describe('Liferay.Util.getURLWithSessionId', () => {
	themeDisplay = {
		getSessionId: jest.fn(() => 'foo'),
		isAddSessionIdToURL: jest.fn(() => true),
	};

	it('returns a url with a jsessionid param added', () => {
		expect(getURLWithSessionId(`${BASE_URL}`)).toBe(
			`${BASE_URL}/;${SESSION_ID}`
		);

		expect(getURLWithSessionId(`${BASE_URL}/`)).toBe(
			`${BASE_URL}/;${SESSION_ID}`
		);

		expect(getURLWithSessionId(`${BASE_URL}/param=1&param=2/`)).toBe(
			`${BASE_URL}/param=1&param=2/;${SESSION_ID}`
		);
	});
});
