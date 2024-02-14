/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import LinkProcessor from '../../../../src/main/resources/META-INF/resources/page_editor/app/processors/LinkProcessor';

describe('LinkProcessor', () => {
	describe('render', () => {
		it('sets prefix to the href', () => {
			const anchor = document.createElement('a');

			LinkProcessor.render(anchor, 'link text content', {
				href: 'pablo@pablo.me',
				prefix: 'mailto:',
				target: '_blank',
			});

			expect(anchor.getAttribute('href')).toBe('mailto:pablo@pablo.me');
			expect(anchor.getAttribute('target')).toBe('_blank');
		});
	});
});
