/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import TextProcessor from '../../../../src/main/resources/META-INF/resources/page_editor/app/processors/TextProcessor';

describe('TextProcessor', () => {
	describe('render', () => {
		it('sets prefix to the href', () => {
			const div = document.createElement('div');

			TextProcessor.render(div, 'text content', {
				href: 'pablo@pablo.me',
				prefix: 'mailto:',
				target: '_blank',
			});

			const anchor = div.querySelector('a');

			expect(anchor.getAttribute('href')).toBe('mailto:pablo@pablo.me');
			expect(anchor.getAttribute('target')).toBe('_blank');
		});
	});
});
