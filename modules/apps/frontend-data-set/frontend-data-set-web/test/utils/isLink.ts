/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import isLink from '../../src/main/resources/META-INF/resources/utils/isLink';

describe('isLink utility', () => {
	it('returns false when it is called with a callback function', () => {
		const inputProps = {
			onClick: () => 'callback call',
			target: 'headless',
		};

		expect(isLink(inputProps.target, inputProps.onClick)).toBe(false);
	});

	it('returns false if it does not have a callback function', () => {
		const inputProps = {
			target: 'async',
		};

		expect(isLink(inputProps.target, null)).toBe(false);
	});

	it('returns true if the target is of type "link" and does not have a callback function', () => {
		const inputProps = {
			target: 'link',
		};

		expect(isLink(inputProps.target, null)).toBe(true);
	});
});
