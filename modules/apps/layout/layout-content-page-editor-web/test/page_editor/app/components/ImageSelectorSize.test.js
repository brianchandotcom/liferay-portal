/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {act, render} from '@testing-library/react';
import React from 'react';

import {useGlobalContext} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/GlobalContext';
import {StoreAPIContextProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import ImageService from '../../../../src/main/resources/META-INF/resources/page_editor/app/services/ImageService';
import {ImageSelectorSize} from '../../../../src/main/resources/META-INF/resources/page_editor/common/components/ImageSelectorSize';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/GlobalContext'
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/services/ImageService'
);

const renderImageSelectorSize = (
	{imageSizeId = '1000px', imageSizeLimit = null, onImageSizeIdChanged} = {
		imageSizeId: '1000px',
		imageSizeLimit: null,
	}
) =>
	render(
		<StoreAPIContextProvider>
			<ImageSelectorSize
				fieldValue={{fileEntryId: '1234'}}
				imageSizeId={imageSizeId}
				imageSizeLimit={imageSizeLimit}
				onImageSizeIdChanged={onImageSizeIdChanged}
			/>
		</StoreAPIContextProvider>
	);

const imageSizesPromise = Promise.resolve([
	{
		label: '480px',
		mediaQuery: '(max-width: 480px)',
		size: 50,
		value: '480px',
		width: 480,
	},
	{
		label: '1000px',
		mediaQuery: '(max-width: 1000px)',
		size: 200,
		value: '1000px',
		width: 1000,
	},
	{
		label: 'Auto',
		size: 300,
		value: 'auto',
		width: 2560,
	},
]);

describe('ImageSelectorSize', () => {
	beforeEach(() => {
		Liferay.FeatureFlags['LPS-187285'] = true;

		useGlobalContext.mockReturnValue({
			document: {
				body: {
					getBoundingClientRect: () => ({
						width: 300,
					}),
				},
			},

			window: {
				matchMedia: () => ({
					matches: false,
				}),
			},
		});

		ImageService.getAvailableImageConfigurations.mockReturnValue(
			imageSizesPromise
		);
	});

	afterEach(() => {
		Liferay.FeatureFlags['LPS-187285'] = false;

		useGlobalContext.mockClear();
		ImageService.getAvailableImageConfigurations.mockClear();
	});

	it('renders viewport width if there are no image sizes', async () => {
		ImageService.getAvailableImageConfigurations.mockReturnValue(
			Promise.resolve([])
		);

		const {container} = renderImageSelectorSize();

		await act(() => imageSizesPromise);
		expect(container.firstChild.textContent).toBe('width:300px');
	});

	it('allows modifying imageSizeId of onImageSizeIdChange is specified', async () => {
		const onImageSizeIdChanged = jest.fn();

		const {getByLabelText} = renderImageSelectorSize({
			onImageSizeIdChanged,
		});

		await act(() => imageSizesPromise);

		const imageSizes = await imageSizesPromise;
		const select = getByLabelText('resolution');

		expect(select).toBeInTheDocument();

		expect(
			Array.from(select.options).map((option) => ({
				label: option.text,
				value: option.value,
			}))
		).toEqual(
			imageSizes.map((imageSize) => ({
				label: imageSize.label,
				value: imageSize.value,
			}))
		);
	});

	it('shows image width for selected imageSizeId', async () => {
		const {getByText} = renderImageSelectorSize();

		await act(() => imageSizesPromise);

		const widthLabel = getByText('width:', {exact: false});

		expect(widthLabel.parentElement.textContent).toBe('width:1000px');
	});

	it('shows image size for selected imageSizeId', async () => {
		const {getByText} = renderImageSelectorSize();

		await act(() => imageSizesPromise);

		const widthLabel = getByText('file-size:', {exact: false});

		expect(widthLabel.parentElement.textContent).toBe('file-size:200.00kB');
	});

	it('uses given imageSizeId if specified', async () => {
		const {getByText} = renderImageSelectorSize();

		await act(() => imageSizesPromise);

		const widthLabel = getByText('width:', {exact: false});

		expect(widthLabel.parentElement.textContent).toBe('width:1000px');
	});

	it('uses global context viewport width if imageSizeId is auto', async () => {
		ImageService.getAvailableImageConfigurations.mockReturnValue(
			Promise.resolve([])
		);

		const {getByText} = renderImageSelectorSize({
			imageSizeId: 'auto',
		});

		await act(() => imageSizesPromise);

		const widthLabel = getByText('width:', {exact: false});

		expect(widthLabel.parentElement.textContent).toBe('width:300px');
	});

	it('shows a warning if the image is larger than the specified size', async () => {
		const {getByText} = renderImageSelectorSize({
			imageSizeLimit: 100,
		});

		await act(() => imageSizesPromise);

		expect(
			getByText(
				'big-image-file-size-used-please-consider-configuring-adaptive-media-lazy-loading-or-reducing-the-image-size'
			)
		).toBeInTheDocument();
	});
});
