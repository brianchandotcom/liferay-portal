/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {useIsMobileDevice} from '@clayui/shared';
import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import PreviewSelector, {
	LayoutSelector,
	LayoutTypeSelector,
} from '../../src/main/resources/META-INF/resources/js/style-book-editor/PreviewSelector';
import {LAYOUT_TYPES} from '../../src/main/resources/META-INF/resources/js/style-book-editor/constants/layoutTypes';
import {LayoutContextProvider} from '../../src/main/resources/META-INF/resources/js/style-book-editor/contexts/LayoutContext';
import openItemSelector from '../../src/main/resources/META-INF/resources/js/style-book-editor/openItemSelector';

jest.mock('@clayui/shared', () => ({
	...jest.requireActual('@clayui/shared'),
	useIsMobileDevice: jest.fn(),
}));

jest.mock(
	'../../src/main/resources/META-INF/resources/js/style-book-editor/openItemSelector',
	() => jest.fn(() => {})
);

jest.mock(
	'../../src/main/resources/META-INF/resources/js/style-book-editor/config',
	() => ({
		config: {
			previewOptions: [
				{
					data: {
						itemSelectorURL: 'master-item-selector-url',
						recentLayouts: [],
						totalLayouts: 0,
					},
					type: 'master',
				},
				{
					data: {
						itemSelectorURL: 'page-item-selector-url',
						recentLayouts: [
							{
								name: 'Page 1',
								private: false,
								url: 'page-1-url',
							},
							{
								name: 'Page 2',
								private: false,
								url: 'page-2-url',
							},
							{
								name: 'Page 3',
								private: false,
								url: 'page-3-url',
							},
							{
								name: 'Page 4',
								private: true,
								url: 'page-4-url',
							},
						],
						totalLayouts: 6,
					},
					type: 'page',
				},
				{
					data: {
						itemSelectorURL: 'page-template-item-selector-url',
						recentLayouts: [
							{
								name: 'Page Template 1',
								private: false,
								url: 'page-template-1-url',
							},
						],
						totalLayouts: 1,
					},
					type: 'pageTemplate',
				},
				{
					data: {
						itemSelectorURL: 'display-page-item-selector-url',
						recentLayouts: [
							{
								name: 'Display Page 1',
								private: false,
								url: 'display-page-1-url',
							},
						],
						totalLayouts: 1,
					},
					type: 'displayPageTemplate',
				},
				{
					data: {
						itemSelectorURL: 'fragment-collection-selector-url',
						recentLayouts: [
							{
								name: 'Fragment Collection 1',
								private: false,
								url: 'fragment-collection-1-url',
							},
						],
						totalLayouts: 1,
					},
					type: 'fragmentCollection',
				},
			],
		},
	})
);

const renderPreviewSelector = (layoutType = LAYOUT_TYPES.page) => {
	return render(
		<>
			<LayoutTypeSelector
				layoutType={layoutType}
				setLayoutType={() => {}}
			/>
			<LayoutSelector layoutType={layoutType} />
		</>
	);
};

describe('PreviewSelector', () => {
	beforeEach(() => {
		useIsMobileDevice.mockReturnValue(false);
	});

	afterEach(() => {
		openItemSelector.mockClear();
	});

	it('renders an icon dropdown on small screens', () => {
		useIsMobileDevice.mockReturnValue(true);

		const {getByRole} = render(
			<LayoutContextProvider
				initialState={{
					previewLayout: {
						name: 'Page 1',
						url: 'page-1-url',
					},
					previewLayoutType: LAYOUT_TYPES.page,
				}}
			>
				<PreviewSelector />
			</LayoutContextProvider>
		);

		expect(getByRole('button', {name: /preview/i})).toBeInTheDocument();
	});

	it('renders inline dropdowns on large screens', () => {
		useIsMobileDevice.mockReturnValue(false);

		const {queryByRole} = render(
			<LayoutContextProvider
				initialState={{
					previewLayout: {
						name: 'Page 1',
						url: 'page-1-url',
					},
					previewLayoutType: LAYOUT_TYPES.page,
				}}
			>
				<PreviewSelector />
			</LayoutContextProvider>
		);

		expect(
			queryByRole('button', {name: /preview/i})
		).not.toBeInTheDocument();
	});

	it('does not show layout type if it does not have at least one item', () => {
		const {queryByText} = renderPreviewSelector();

		expect(queryByText('masters')).not.toBeInTheDocument();
	});

	it('shows correct items in layout selector when selecting a type', () => {
		const {getByText} = renderPreviewSelector(
			LAYOUT_TYPES.displayPageTemplate
		);

		expect(getByText('Display Page 1')).toBeInTheDocument();
	});

	it('shows More button and number of items info when selected type has more than 4 items', () => {
		const {getByText} = renderPreviewSelector();

		expect(getByText('more')).toBeInTheDocument();
		expect(getByText('showing-x-of-x-items')).toBeInTheDocument();
	});

	it('does not show More button and number of items info when selected type has 4 items or less', () => {
		const {queryByText} = renderPreviewSelector(LAYOUT_TYPES.pageTemplate);

		expect(queryByText('more')).not.toBeInTheDocument();
		expect(queryByText('showing-x-of-x-items')).not.toBeInTheDocument();
	});

	it('calls openItemSelector with correct url when clicking More button', () => {
		const {getByText} = renderPreviewSelector();

		fireEvent.click(getByText('more'));

		expect(openItemSelector).toBeCalledWith(
			expect.objectContaining({
				itemSelectorURL: 'page-item-selector-url',
			})
		);
	});
});
