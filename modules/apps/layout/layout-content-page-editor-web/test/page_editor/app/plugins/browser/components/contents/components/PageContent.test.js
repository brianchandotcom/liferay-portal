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

import {cleanup, fireEvent, render, wait} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import {useSelectItem} from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext';
import {
	useEditableProcessorUniqueId,
	useSetEditableProcessorUniqueId,
} from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/EditableProcessorContext';
import {StoreContextProvider} from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import PageContent from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/browser/components/contents/components/PageContent';

jest.mock(
	'../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext',
	() => {
		const selectItem = jest.fn();
		const hoverItem = jest.fn();
		const hoveredItemId = null;

		return {
			useHoverItem: () => hoverItem,
			useHoveredItemId: () => hoveredItemId,
			useSelectItem: () => selectItem,
		};
	}
);

jest.mock(
	'../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/EditableProcessorContext'
);

const contents = [
	{
		actions: {
			editURL: 'editURL',
			permissionsURL: 'permissionsURL',
			viewUsagesURL: 'viewUsagesURL',
		},
		classPK: '11111',
		subtype: 'Web Content Article',
		title: 'Test Web Content',
	},
	{
		actions: {
			addItems: [
				{
					href: 'URL',
					label: 'Basic Web Content to be added',
				},
			],
			editURL: 'editURL',
			otherAction: 'other-action',
			permissionsURL: 'permissionsURL',
			viewItemsURL: 'viewItemsURL',
			viewUsagesURL: 'viewUsagesURL',
		},
		classPK: '11112',
		subtype: 'Collection',
		title: 'Test Collection',
	},
	{
		actions: {
			editImage: {
				editImageURL: '/editImageURL',
				fileEntryId: '40571',
				previewURL: '/previewURL',
			},
		},
		classPK: '40571',
		subtype: 'Basic Document',
		title: 'image.png',
		type: 'Document',
	},
];

const inlineText = {
	editableId: '11113-element-text',
	title: 'Heading Example',
};

const renderPageContent = (props = contents[0]) =>
	render(
		<StoreContextProvider
			initialState={{
				layoutData: {items: {}},
				pageContents: contents,
				permissions: {UPDATE: true, UPDATE_LAYOUT_CONTENT: true},
			}}
		>
			<PageContent {...props} />
		</StoreContextProvider>
	);

describe('PageContent', () => {
	afterEach(cleanup);

	useSetEditableProcessorUniqueId.mockImplementation(() => jest.fn);

	it('shows properly the title of the content', () => {
		const {getByText} = renderPageContent();

		expect(getByText('Test Web Content')).toBeInTheDocument();
	});

	it('shows properly the content subtype', () => {
		const {getByText} = renderPageContent();

		expect(getByText('Test Web Content')).toBeInTheDocument();
	});

	it('shows all expected editing actions in dropdown menu', () => {
		const shownActions = [
			'edit',
			'permissions',
			'add-items',
			'view-items',
			'view-usages',
		];
		const {queryByText} = renderPageContent(contents[1]);

		fireEvent.click(queryByText('open-actions-menu'));

		shownActions.forEach((action) => {
			expect(queryByText(action)).toBeInTheDocument();
		});
		expect(queryByText('other-action')).not.toBeInTheDocument();
	});

	it('shows all items to be added when the Add Item action is clicked', () => {
		const {queryByText} = renderPageContent(contents[1]);

		fireEvent.click(queryByText('open-actions-menu'));
		fireEvent.click(queryByText('add-items'));

		expect(
			queryByText('Basic Web Content to be added')
		).toBeInTheDocument();
	});

	it('open image editor modal when the Edit Image action is clicked', async () => {
		const {baseElement, queryByText} = renderPageContent(contents[2]);

		fireEvent.click(queryByText('open-actions-menu'));
		fireEvent.click(queryByText('edit-image'));

		await wait(() => {
			expect(
				baseElement.querySelector('.image-editor-modal')
			).toBeInTheDocument();
		});
	});

	it('shows the edit button if the content is inline text', () => {
		const {getByText} = renderPageContent(inlineText);

		expect(getByText('edit-inline-text')).toBeInTheDocument();
	});

	it('selects the corresponding element on the page when edit button is clicked', async () => {
		const selectItem = useSelectItem();
		const {getByText} = renderPageContent(inlineText);

		fireEvent.click(getByText('edit-inline-text'));

		await wait(() => {
			expect(selectItem).toHaveBeenCalledWith('11113-element-text', {
				itemType: 'editable',
				origin: 'sidebar',
			});
		});
	});

	it('disables edit button when an inline text is being edited', () => {
		useEditableProcessorUniqueId.mockImplementation(
			() => '11113-element-text'
		);

		const {getByText} = renderPageContent(inlineText);

		expect(getByText('edit-inline-text').parentElement).toBeDisabled();
	});
});
