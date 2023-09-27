/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ImageProcessor from '../../../../src/main/resources/META-INF/resources/page_editor/app/processors/ImageProcessor';
import {openImageSelector} from '../../../../src/main/resources/META-INF/resources/page_editor/common/openImageSelector';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/common/openImageSelector',
	() => ({
		openImageSelector: jest.fn(),
	})
);

describe('ImageProcessor', () => {
	describe('createEditor', () => {
		it('calls changeCallback when an image is selected', () => {
			openImageSelector.mockImplementation((changeCallback) =>
				changeCallback({
					title: 'sample-image.jpg',
					url: 'sample-image.jpg',
				})
			);

			const changeCallback = jest.fn();

			ImageProcessor.createEditor(null, changeCallback, () => {}, {});
			expect(changeCallback).toHaveBeenCalledWith(
				{
					title: 'sample-image.jpg',
					url: 'sample-image.jpg',
				},
				{alt: ''}
			);
		});

		it('calls destroyCallback if the selector is closed without choosing an image', () => {
			openImageSelector.mockImplementation(
				(changeCallback, destroyCallback) => destroyCallback()
			);

			const destroyCallback = jest.fn();

			ImageProcessor.createEditor(null, () => {}, destroyCallback, {});
			expect(destroyCallback).toHaveBeenCalled();
		});
	});

	describe('render', () => {
		it('sets the editable image src', () => {
			const element = document.createElement('img');

			ImageProcessor.render(element, 'sandro-cv-photo.png');
			expect(element.getAttribute('src')).toBe('sandro-cv-photo.png');
		});

		it('looks for a child image if the editable element is not an image', () => {
			const element = document.createElement('div');
			const image = document.createElement('img');

			element.appendChild(image);
			ImageProcessor.render(element, 'default-image.gif');
			expect(image.getAttribute('src')).toBe('default-image.gif');
		});

		it('sets a configuration href and target if the editable element is a link', () => {
			const anchor = document.createElement('a');
			const image = document.createElement('img');

			anchor.appendChild(image);

			ImageProcessor.render(anchor, 'apple-pie.webp', {
				href: 'http://localpie',
				target: '_blank',
			});

			expect(anchor.getAttribute('href')).toBe('http://localpie');
			expect(anchor.getAttribute('target')).toBe('_blank');
			expect(image.getAttribute('src')).toBe('apple-pie.webp');
		});

		it('wraps everything with an anchor if the editable element is not a link', () => {
			const element = document.createElement('div');
			const image = document.createElement('img');

			element.appendChild(image);

			ImageProcessor.render(element, 'apple-pie.webp', {
				href: 'http://localpie',
				target: '_blank',
			});

			expect(image.parentElement instanceof HTMLAnchorElement).toBe(true);
			expect(image.parentElement.getAttribute('href')).toBe(
				'http://localpie'
			);
			expect(image.parentElement.getAttribute('target')).toBe('_blank');
			expect(image.getAttribute('src')).toBe('apple-pie.webp');
		});

		it('sets prefix to the href', () => {
			const div = document.createElement('div');
			const image = document.createElement('img');

			div.appendChild(image);

			ImageProcessor.render(div, 'apple-pie.webp', {
				href: 'pablo@pablo.me',
				prefix: 'mailto:',
				target: '_blank',
			});

			expect(image.parentElement.getAttribute('href')).toBe(
				'mailto:pablo@pablo.me'
			);
			expect(image.parentElement.getAttribute('target')).toBe('_blank');
			expect(image.getAttribute('src')).toBe('apple-pie.webp');
		});
	});
});
