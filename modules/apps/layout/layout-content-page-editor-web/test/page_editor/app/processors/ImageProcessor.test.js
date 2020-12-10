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

import ImageProcessor from '../../../../src/main/resources/META-INF/resources/page_editor/app/processors/ImageProcessor';
import {openImageSelector} from '../../../../src/main/resources/META-INF/resources/page_editor/core/openImageSelector';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/core/openImageSelector',
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
					fileEntryId: undefined,
					url: 'sample-image.jpg',
				},
				{imageTitle: 'sample-image.jpg'}
			);
		});

		it('calls changeCallback with an empty string if the image title is not found', () => {
			openImageSelector.mockImplementation((changeCallback) =>
				changeCallback({url: 'sample-image.jpg'})
			);

			const changeCallback = jest.fn();

			ImageProcessor.createEditor(null, changeCallback, () => {}, {});
			expect(changeCallback).toHaveBeenCalledWith(
				{
					fileEntryId: undefined,
					url: 'sample-image.jpg',
				},
				{imageTitle: ''}
			);
		});

		it('calls changeCallback with an empty string if the image url is not found', () => {
			openImageSelector.mockImplementation((changeCallback) =>
				changeCallback({
					thisIsNotAnImage: 'victor.profile',
					title: 'victor-profile.jpg',
				})
			);

			const changeCallback = jest.fn();

			ImageProcessor.createEditor(null, changeCallback, () => {}, {});
			expect(changeCallback).toHaveBeenCalledWith(
				{
					fileEntryId: undefined,
					url: '',
				},
				{imageTitle: 'victor-profile.jpg'}
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
	});
});
