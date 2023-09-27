/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import {StoreAPIContextProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import ItemSelector from '../../../../src/main/resources/META-INF/resources/page_editor/common/components/ItemSelector';
import {openItemSelector} from '../../../../src/main/resources/META-INF/resources/page_editor/common/openItemSelector';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/config/index',
	() => ({
		config: {
			infoItemSelectorUrl: 'infoItemSelectorUrl',
			portletNamespace: 'portletNamespace',
		},
	})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/common/openItemSelector',
	() => ({
		openItemSelector: jest.fn(() => {}),
	})
);

jest.mock('frontend-js-web', () => ({
	...jest.requireActual('frontend-js-web'),
	sub: jest.fn((langKey, args) => langKey.replace('x', args)),
}));

function renderItemSelector({
	pageContents = [],
	selectedItemClassPK = '',
	selectedItemTitle = '',
}) {
	const state = {
		pageContents,
	};

	return render(
		<StoreAPIContextProvider dispatch={() => {}} getState={() => state}>
			<ItemSelector
				label="itemSelectorLabel"
				onItemSelect={() => {}}
				selectedItem={
					selectedItemTitle
						? {
								classPK: selectedItemClassPK,
								title: selectedItemTitle,
						  }
						: null
				}
				transformValueCallback={() => {}}
			/>
		</StoreAPIContextProvider>
	);
}

describe('ItemSelector', () => {
	afterEach(() => {
		openItemSelector.mockClear();
	});

	it('renders correctly', () => {
		const {getByText} = renderItemSelector({});

		expect(getByText('itemSelectorLabel')).toBeInTheDocument();
	});

	it('renders the placeholder correctly', () => {
		const {getByPlaceholderText} = renderItemSelector({});

		expect(
			getByPlaceholderText('select-itemSelectorLabel')
		).toBeInTheDocument();
	});

	it('renders the aria label button correctly when no item is selected', () => {
		const {getByLabelText} = renderItemSelector({});

		expect(getByLabelText('select-itemSelectorLabel')).toBeInTheDocument();
	});

	it('renders the aria label button correctly when an item is selected', () => {
		const selectedItemTitle = 'itemTitle';

		const {getByLabelText} = renderItemSelector({selectedItemTitle});

		expect(getByLabelText('change-itemSelectorLabel')).toBeInTheDocument();
	});

	it('shows selected item title correctly when receiving it in props', () => {
		const selectedItemTitle = 'itemTitle';

		const {getByLabelText} = renderItemSelector({
			selectedItemTitle,
		});

		expect(getByLabelText('itemSelectorLabel')).toHaveValue(
			selectedItemTitle
		);
	});

	it('does not show any title when not receiving it in props', () => {
		const {getByLabelText} = renderItemSelector({});

		expect(getByLabelText('itemSelectorLabel')).toBeEmpty();
	});

	it('calls openItemSelector when there are not mapping items and plus button is clicked', () => {
		const {getByLabelText} = renderItemSelector({});

		fireEvent.click(getByLabelText('select-itemSelectorLabel'));

		expect(openItemSelector).toBeCalled();
	});

	it('shows recent items dropdown instead of calling openItemSelector when there are mapping items', () => {
		const pageContents = [
			{classNameId: '001', classPK: '002', title: 'Mapped Item Title'},
		];

		const {getByLabelText, getByText} = renderItemSelector({
			pageContents,
		});

		fireEvent.click(getByLabelText('select-itemSelectorLabel'));

		expect(getByText('Mapped Item Title')).toBeInTheDocument();

		expect(openItemSelector).not.toBeCalled();
	});

	it('removes selected item correctly when clear button is clicked', () => {
		const selectedItemTitle = 'itemTitle';

		const {getByLabelText, getByText} = renderItemSelector({
			selectedItemTitle,
		});

		fireEvent.click(getByText('remove-itemSelectorLabel'));

		expect(getByLabelText('itemSelectorLabel')).toBeEmpty();
	});

	it('adds addItem content-related option if possible', () => {
		const {getByText} = renderItemSelector({
			pageContents: [
				{
					actions: {
						addItems: [
							{
								href: 'http://me.local/addItemOneURL',
								label: 'Add Item One',
							},
						],
					},
					classPK: 'sampleItem-classPK',
					title: 'itemTitle',
				},
			],
			selectedItemClassPK: 'sampleItem-classPK',
			selectedItemTitle: 'itemTitle',
		});

		const addSubMenuButton = getByText('add-items');

		expect(addSubMenuButton).toBeInTheDocument();
		expect(addSubMenuButton.tagName).toBe('BUTTON');

		const addItemLink = getByText('Add Item One');

		expect(addItemLink).toBeInTheDocument();
		expect(addItemLink.href).toBe('http://me.local/addItemOneURL');
	});

	it('adds editURL content-related option if possible', () => {
		const {getByText} = renderItemSelector({
			pageContents: [
				{
					actions: {editURL: 'http://me.local/editURL'},
					classPK: 'sampleItem-classPK',
					title: 'itemTitle',
				},
			],
			selectedItemClassPK: 'sampleItem-classPK',
			selectedItemTitle: 'itemTitle',
		});

		const editItemLink = getByText('edit-itemSelectorLabel');

		expect(editItemLink).toBeInTheDocument();
		expect(editItemLink.href).toBe('http://me.local/editURL');
	});

	it('adds permissionsURL content-related option if possible', () => {
		const {getByText} = renderItemSelector({
			pageContents: [
				{
					actions: {permissionsURL: 'http://me.local/permissionsURL'},
					classPK: 'sampleItem-classPK',
					title: 'itemTitle',
				},
			],
			selectedItemClassPK: 'sampleItem-classPK',
			selectedItemTitle: 'itemTitle',
		});

		const editItemButton = getByText('edit-itemSelectorLabel-permissions');

		expect(editItemButton).toBeInTheDocument();
		expect(editItemButton.tagName).toBe('BUTTON');
	});

	it('adds viewItemsURL content-related option if possible', () => {
		const {getByText} = renderItemSelector({
			pageContents: [
				{
					actions: {viewItemsURL: 'http://me.local/viewItemsURL'},
					classPK: 'sampleItem-classPK',
					title: 'itemTitle',
				},
			],
			selectedItemClassPK: 'sampleItem-classPK',
			selectedItemTitle: 'itemTitle',
		});

		const viewItemsButton = getByText('view-items');

		expect(viewItemsButton).toBeInTheDocument();
		expect(viewItemsButton.tagName).toBe('BUTTON');
	});

	it('adds viewUsagesURL content-related option if possible', () => {
		const {getByText} = renderItemSelector({
			pageContents: [
				{
					actions: {viewUsagesURL: 'http://me.local/viewUsagesURL'},
					classPK: 'sampleItem-classPK',
					title: 'itemTitle',
				},
			],
			selectedItemClassPK: 'sampleItem-classPK',
			selectedItemTitle: 'itemTitle',
		});

		const viewUsagesButton = getByText('view-itemSelectorLabel-usages');

		expect(viewUsagesButton).toBeInTheDocument();
		expect(viewUsagesButton.tagName).toBe('BUTTON');
	});
});
