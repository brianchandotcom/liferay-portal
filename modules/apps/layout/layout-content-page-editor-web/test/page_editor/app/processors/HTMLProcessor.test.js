/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import HTMLProcessor from '../../../../src/main/resources/META-INF/resources/page_editor/app/processors/HTMLProcessor';

describe('HTMLProcessor', () => {
	describe('render', () => {
		it('injects the given string as innerHTML', () => {
			const element = document.createElement('div');

			HTMLProcessor.render(element, 'Jordi Kappler');
			expect(element.innerHTML).toBe('Jordi Kappler');
		});
	});
});
