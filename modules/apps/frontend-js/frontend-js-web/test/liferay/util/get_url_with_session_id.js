/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
