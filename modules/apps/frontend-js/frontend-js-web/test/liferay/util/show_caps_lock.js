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
