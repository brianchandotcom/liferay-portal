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
