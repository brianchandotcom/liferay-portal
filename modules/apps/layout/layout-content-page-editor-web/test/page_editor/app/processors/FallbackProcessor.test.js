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

import FallbackProcessor from '../../../../src/main/resources/META-INF/resources/page_editor/app/processors/FallbackProcessor';

describe('FallbackProcessor', () => {
	describe('createEditor', () => {
		it('sets contenteditable attribute to the given element', () => {
			const element = document.createElement('div');

			FallbackProcessor.createEditor(element);
			expect(element.hasAttribute('contenteditable')).toBe(true);
		});
	});

	describe('destroyEditor', () => {
		it('removes contenteditable attribute from the given element', () => {
			const element = document.createElement('div');

			element.setAttribute('contenteditable', 'true');
			FallbackProcessor.destroyEditor(element);
			expect(element.hasAttribute('contenteditable')).toBe(false);
		});
	});

	describe('render', () => {
		it('injects the given string as innerHTML', () => {
			const element = document.createElement('div');

			FallbackProcessor.render(element, 'I am Juan Kastro');
			expect(element.innerHTML).toBe('I am Juan Kastro');
		});
	});
});
