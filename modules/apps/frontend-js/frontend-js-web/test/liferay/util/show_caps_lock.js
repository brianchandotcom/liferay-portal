/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {showCapsLock} from '../../../src/main/resources/META-INF/resources/index.es';

describe('Liferay.Util.showCapsLock', () => {
	it('finds an element by id and sets the display property to an empty string', () => {
		const input = document.createElement('input');

		input.id = 'foo';

		document.body.appendChild(input);

		showCapsLock(
			{
				getModifierState: () => true,
			},
			'foo'
		);

		expect(input.style.display).toBe('');

		document.body.removeChild(input);
	});

	it('does not change the display style of the caps lock notice if caps lock is not enabled', () => {
		const input = document.createElement('input');

		input.id = 'foo';

		document.body.appendChild(input);

		showCapsLock(
			{
				getModifierState: () => false,
			},
			'foo'
		);

		expect(input.style.display).toBe('none');

		document.body.removeChild(input);
	});
});
