/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import DateTimeProcessor from '../../../../src/main/resources/META-INF/resources/page_editor/app/processors/DateTimeProcessor';

describe('DateTimeProcessor', () => {
	describe('createEditor', () => {
		it('does not modify element since date processor is only meant to be mapped', () => {
			const element = document.createElement('div');

			DateTimeProcessor.createEditor(element);
			expect(element.hasAttribute('contenteditable')).toBe(false);
		});

		it('call destroy callback directly since there is', () => {
			const element = document.createElement('div');
			const destroyFn = jest.fn();

			DateTimeProcessor.createEditor(element, () => {}, destroyFn);

			expect(destroyFn).toBeCalled();
		});
	});
});
