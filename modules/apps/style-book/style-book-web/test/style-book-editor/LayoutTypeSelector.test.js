/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import {LayoutTypeSelector} from '../../src/main/resources/META-INF/resources/js/style-book-editor/LayoutTypeSelector';
import {LAYOUT_TYPES} from '../../src/main/resources/META-INF/resources/js/style-book-editor/constants/layoutTypes';
import {
	useSetLoading,
	useSetPreviewLayout,
	useSetPreviewLayoutType,
} from '../../src/main/resources/META-INF/resources/js/style-book-editor/contexts/LayoutContext';

jest.mock(
	'../../src/main/resources/META-INF/resources/js/style-book-editor/config',
	() => ({
		config: {
			previewOptions: [
				{
					data: {recentLayouts: [], totalLayouts: 0},
					type: 'displayPageTemplate',
				},
				{
					data: {recentLayouts: [], totalLayouts: 0},
					type: 'fragmentCollection',
				},
				{data: {recentLayouts: [], totalLayouts: 0}, type: 'master'},
				{
					data: {
						recentLayouts: [
							{name: 'Page 1', private: false, url: 'page-1-url'},
						],
						totalLayouts: 1,
					},
					type: 'page',
				},
				{
					data: {
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
			],
		},
	})
);

jest.mock(
	'../../src/main/resources/META-INF/resources/js/style-book-editor/contexts/LayoutContext',
	() => ({
		useSetLoading: jest.fn(),
		useSetPreviewLayout: jest.fn(),
		useSetPreviewLayoutType: jest.fn(),
	})
);

describe('LayoutTypeSelector', () => {
	const mockSetLoading = jest.fn();
	const mockSetPreviewLayout = jest.fn();
	const mockSetPreviewLayoutType = jest.fn();

	beforeEach(() => {
		useSetLoading.mockReturnValue(mockSetLoading);
		useSetPreviewLayout.mockReturnValue(mockSetPreviewLayout);
		useSetPreviewLayoutType.mockReturnValue(mockSetPreviewLayoutType);
	});

	afterEach(() => {
		jest.clearAllMocks();
	});

	it('does not call state setters when clicking the already-selected layout type', () => {
		const {getByRole} = render(
			<LayoutTypeSelector layoutType={LAYOUT_TYPES.page} />
		);

		fireEvent.click(getByRole('button'));

		fireEvent.click(getByRole('menuitem', {name: 'pages'}));

		expect(mockSetLoading).not.toHaveBeenCalled();
		expect(mockSetPreviewLayout).not.toHaveBeenCalled();
		expect(mockSetPreviewLayoutType).not.toHaveBeenCalled();
	});

	it('calls state setters with correct values when selecting a different layout type', () => {
		const {getByRole, getByText} = render(
			<LayoutTypeSelector layoutType={LAYOUT_TYPES.page} />
		);

		fireEvent.click(getByRole('button'));

		fireEvent.click(getByText('page-templates'));

		expect(mockSetLoading).toHaveBeenCalledWith(true);
		expect(mockSetPreviewLayout).toHaveBeenCalledWith({
			name: 'Page Template 1',
			private: false,
			url: 'page-template-1-url',
		});
		expect(mockSetPreviewLayoutType).toHaveBeenCalledWith(
			LAYOUT_TYPES.pageTemplate
		);
	});
});
