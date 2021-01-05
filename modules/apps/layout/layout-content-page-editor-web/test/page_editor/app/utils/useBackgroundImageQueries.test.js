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

import {act, renderHook} from '@testing-library/react-hooks';

import ImageService from '../../../../src/main/resources/META-INF/resources/page_editor/app/services/ImageService';
import {useBackgroundImageMediaQueries} from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/useBackgroundImageQueries';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/services/ImageService'
);

const renderCustomHook = (image = {fileEntryId: 123}) =>
	renderHook(() => useBackgroundImageMediaQueries('id', image));

describe('useBackgroundImageQueries', () => {
	afterEach(() => {
		ImageService.getAvailableImageConfigurations.mockClear();
	});

	it('returns an empty string if image has no fileEntryId', () => {
		const {result} = renderCustomHook({});

		expect(result.current).toBe('');
	});

	it('returns an empty string if there are no image sizes', async () => {
		const imageSizesPromise = Promise.resolve([]);

		ImageService.getAvailableImageConfigurations.mockReturnValue(
			imageSizesPromise
		);

		const {result} = renderCustomHook();

		await act(() => imageSizesPromise);

		expect(result.current).toBe('');
	});

	it('creates media queries for each returned image size', async () => {
		const imageSizesPromise = Promise.resolve([
			{mediaQuery: '(min-width:1px)', url: 'image.jpg'},
			{mediaQuery: '(min-width:1234px)', url: 'image-big.jpg'},
		]);

		ImageService.getAvailableImageConfigurations.mockReturnValue(
			imageSizesPromise
		);

		const {result} = renderCustomHook();

		await act(() => imageSizesPromise);

		expect(result.current.replace(/\s+/g, '')).toBe(
			`
			@media (min-width:1px) { #id { background-image: url(image.jpg) !important; } }
			@media (min-width:1234px) { #id { background-image: url(image-big.jpg) !important; } }
		`.replace(/\s+/g, '')
		);
	});

	it('ignores image sizes without url or mediaQuery', async () => {
		const imageSizesPromise = Promise.resolve([
			{url: 'nothing.jpg'},
			{mediaQuery: '(min-width:1px)', url: 'image.jpg'},
			{mediaQuery: '(min-width:1234px)'},
		]);

		ImageService.getAvailableImageConfigurations.mockReturnValue(
			imageSizesPromise
		);

		const {result} = renderCustomHook();

		await act(() => imageSizesPromise);

		expect(result.current.replace(/\s+/g, '')).toBe(
			`
			@media (min-width:1px) { #id { background-image: url(image.jpg) !important; } }
		`.replace(/\s+/g, '')
		);
	});
});
