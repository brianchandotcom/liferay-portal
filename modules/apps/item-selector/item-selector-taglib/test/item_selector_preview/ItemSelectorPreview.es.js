/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import ItemSelectorPreview from '../../src/main/resources/META-INF/resources/item_selector_preview/js/ItemSelectorPreview.es';

const basicMetadata = {
	groups: [
		{
			data: [
				{
					key: Liferay.Language.get('format'),
					value: 'jpg',
				},
				{
					key: Liferay.Language.get('name'),
					value: 'test image.jpg',
				},
			],
			title: 'file-info',
		},
	],
};
const item1Title = 'item1.jpg';
const item2Title = 'item2.jpg';
const itemUrl = 'image1.jpg';
const headerTitle = 'Images';

const items = [
	{
		metadata: JSON.stringify(basicMetadata),
		mimeType: 'image/jpeg',
		returntype: 'returntype',
		title: item1Title,
		url: itemUrl,
		value: itemUrl,
	},
	{
		metadata: JSON.stringify(basicMetadata),
		mimeType: 'image/jpeg',
		returntype: 'returntype',
		title: item2Title,
		url: itemUrl,
		value: itemUrl,
	},
];

const previewProps = {
	handleSelectedItem: jest.fn(),
	headerTitle,
	items,
};

const renderPreviewComponent = (props) =>
	render(<ItemSelectorPreview {...props} />);

describe('ItemSelectorPreview', () => {
	beforeAll(() => {
		Liferay.component = jest.fn();
		Liferay.SideNavigation = {
			destroy: jest.fn(),
			initialize: jest.fn(),
		};
	});

	afterEach(cleanup);

	it('initialize/destroy the sidebar properly', () => {
		renderPreviewComponent(previewProps);

		expect(
			Liferay.SideNavigation.initialize.mock.calls.length -
				Liferay.SideNavigation.destroy.mock.calls.length
		).toBe(1);
	});

	it('renders the ItemSelectorPreview component with the fullscreen class', () => {
		const {container} = renderPreviewComponent(previewProps);

		expect(container.firstChild.classList).toContain('fullscreen');
	});

	it('renders the header component', () => {
		const {getByText} = renderPreviewComponent(previewProps);

		expect(getByText(headerTitle)).toBeTruthy();
	});

	it('renders the carousel component', () => {
		const {getAllByRole} = renderPreviewComponent(previewProps);

		expect(getAllByRole('img').length).toBe(1);
	});

	it('renders a specific item', () => {
		const {getByText} = renderPreviewComponent({
			...previewProps,
			currentIndex: 1,
		});

		expect(getByText(item2Title)).toBeTruthy();
	});

	it('shows the next item when requested', () => {
		const {container, getByText} = renderPreviewComponent({
			...previewProps,
		});

		expect(getByText(item1Title)).toBeTruthy();

		const rigthArrowButton = container.querySelectorAll('.icon-arrow')[1];

		fireEvent.click(rigthArrowButton);

		expect(getByText(item2Title)).toBeTruthy();
	});

	it('returns to the first item when requested the next item for the last one', () => {
		const {container, getByText} = renderPreviewComponent({
			...previewProps,
			currentIndex: 1,
		});

		const rigthArrowButton = container.querySelectorAll('.icon-arrow')[1];

		fireEvent.click(rigthArrowButton);
		expect(getByText(item1Title)).toBeTruthy();
	});

	it('shows the previous item when pressed the left key event', () => {
		const {getByText} = renderPreviewComponent({
			...previewProps,
		});

		fireEvent.keyDown(document.documentElement, {
			key: 'ArrowLeft',
			keyCode: 37,
			which: 37,
		});

		expect(getByText(item2Title)).toBeTruthy();
	});

	it('handleSelectedItem is called when Add button is clicked', () => {
		const {getByText} = renderPreviewComponent({...previewProps});

		const addButton = getByText('add');

		fireEvent.click(addButton);

		expect(previewProps.handleSelectedItem).toHaveBeenCalledTimes(1);
	});
});
