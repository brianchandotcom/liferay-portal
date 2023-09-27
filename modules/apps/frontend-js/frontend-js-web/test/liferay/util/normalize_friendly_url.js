/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import normalizeFriendlyURL from '../../../src/main/resources/META-INF/resources/liferay/util/normalize_friendly_url';

describe('Liferay.Util.normalizeFriendlyURL', () => {
	it('throws error if text parameter is not a string', () => {
		expect(() => normalizeFriendlyURL({})).toThrow(
			'parameter text must be a string'
		);
	});

	it('returns modified text if text is in uppercase and contains spaces', () => {
		expect(normalizeFriendlyURL('TITLE WITH SPACES IN CAPS')).toEqual(
			'title-with-spaces-in-caps'
		);
	});

	it('returns modified text if text starts with dash', () => {
		expect(normalizeFriendlyURL('-startswithdash')).toEqual(
			'startswithdash'
		);
	});
});
