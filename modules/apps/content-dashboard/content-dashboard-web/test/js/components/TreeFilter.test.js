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

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import TreeFilter from '../../../src/main/resources/META-INF/resources/js/components/TreeFilter';

const mockExtensionsProps = {
	childrenPropertyKey: 'fileExtensions',
	itemSelectorSaveEvent:
		'_com_liferay_content_dashboard_web_portlet_ContentDashboardAdminPortlet_selectedFileExtension',
	mandatoryFieldsForFiltering: ['id'],
	namePropertyKey: 'fileExtension',
	nodes: [
		{
			children: [
				{
					children: null,
					expanded: false,
					fileExtension: 'zip',
					id: 'zip',
					name: 'zip',
					selected: false,
				},
			],
			expanded: true,
			fileExtensions: [{fileExtension: 'zip', selected: false}],
			icon: 'document-compressed',
			id: '_0',
			label: 'Compressed',
			name: 'Compressed (1 Item)',
		},
		{
			children: [
				{
					children: null,
					expanded: false,
					fileExtension: 'gif',
					id: 'gif',
					name: 'gif',
					selected: false,
				},
				{
					children: null,
					expanded: false,
					fileExtension: 'jpg',
					id: 'jpg',
					name: 'jpg',
					selected: false,
				},
				{
					children: null,
					expanded: false,
					fileExtension: 'jpeg',
					id: 'jpeg',
					name: 'jpeg',
					selected: false,
				},
				{
					children: null,
					expanded: false,
					fileExtension: 'png',
					id: 'png',
					name: 'png',
					selected: false,
				},
			],
			expanded: false,
			fileExtensions: [
				{fileExtension: 'gif', selected: false},
				{fileExtension: 'jpg', selected: false},
				{fileExtension: 'jpeg', selected: false},
				{fileExtension: 'png', selected: false},
			],
			icon: 'document-image',
			id: '_1',
			label: 'Image',
			name: 'Image (4 Items)',
		},
		{
			children: [
				{
					children: null,
					expanded: false,
					fileExtension: 'mpga',
					id: 'mpga',
					name: 'mpga',
					selected: false,
				},
				{
					children: null,
					expanded: false,
					fileExtension: 'mp3',
					id: 'mp3',
					name: 'mp3',
					selected: true,
				},
				{
					children: null,
					expanded: false,
					fileExtension: 'mp2',
					id: 'mp2',
					name: 'mp2',
					selected: false,
				},
				{
					children: null,
					expanded: false,
					fileExtension: 'ogg',
					id: 'ogg',
					name: 'ogg',
					selected: false,
				},
				{
					children: null,
					expanded: false,
					fileExtension: 'wav',
					id: 'wav',
					name: 'wav',
					selected: false,
				},
			],
			expanded: false,
			fileExtensions: [
				{fileExtension: 'mpga', selected: false},
				{fileExtension: 'mp3', selected: false},
				{fileExtension: 'mp2', selected: false},
				{fileExtension: 'ogg', selected: false},
				{fileExtension: 'wav', selected: false},
			],
			icon: 'document-multimedia',
			id: '_2',
			label: 'Audio',
			name: 'Audio (5 Items)',
		},
	],
	portletNamespace:
		'_com_liferay_item_selector_web_portlet_ItemSelectorPortlet_',
};

const mockTypesProps = {
	childrenPropertyKey: 'itemSubtypes',
	itemSelectorSaveEvent:
		'_com_liferay_content_dashboard_web_portlet_ContentDashboardAdminPortlet_selectedContentDashboardItemSubtype',
	mandatoryFieldsForFiltering: ['className', 'classPK'],
	namePropertyKey: 'label',
	nodes: [
		{
			children: [
				{
					children: null,
					className:
						'com.liferay.dynamic.data.mapping.model.DDMStructure',
					classPK: '41895',
					expanded: false,
					id: 'Basic Web Content',
					label: 'Basic Web Content',
					name: 'Basic Web Content',
					selected: false,
				},
			],
			expanded: true,
			icon: 'web-content',
			id: '_0',
			itemSubtypes: [
				{
					className:
						'com.liferay.dynamic.data.mapping.model.DDMStructure',
					classPK: '41895',
					label: 'Basic Web Content (Global)',
					selected: false,
				},
			],
			label: 'Web Content Article',
			name: 'Web Content Article (1 Item)',
		},
		{
			children: [
				{
					children: null,
					className:
						'com.liferay.document.library.kernel.model.DLFileEntryType',
					classPK: '0',
					expanded: false,
					id: 'Basic Document',
					label: 'Basic Document',
					name: 'Basic Document',
					selected: false,
				},
				{
					children: null,
					className:
						'com.liferay.document.library.kernel.model.DLFileEntryType',
					classPK: '41842',
					expanded: false,
					id: 'Google Drive Shortcut',
					label: 'Google Drive Shortcut',
					name: 'Google Drive Shortcut',
					selected: false,
				},
				{
					children: null,
					className:
						'com.liferay.document.library.kernel.model.DLFileEntryType',
					classPK: '42001',
					expanded: false,
					id: 'External Video Shortcut',
					label: 'External Video Shortcut',
					name: 'External Video Shortcut',
					selected: false,
				},
				{
					children: null,
					className:
						'com.liferay.document.library.kernel.model.DLFileEntryType',
					classPK: '42390',
					expanded: false,
					id: 'Image with title',
					label: 'Image with title (Guest)',
					name: 'Image with title',
					selected: false,
				},
			],
			expanded: false,
			icon: 'documents-and-media',
			id: '_1',
			itemSubtypes: [
				{
					className:
						'com.liferay.document.library.kernel.model.DLFileEntryType',
					classPK: '0',
					label: 'Basic Document',
					selected: false,
				},
				{
					className:
						'com.liferay.document.library.kernel.model.DLFileEntryType',
					classPK: '41842',
					label: 'Google Drive Shortcut (Global)',
					selected: false,
				},
				{
					className:
						'com.liferay.document.library.kernel.model.DLFileEntryType',
					classPK: '42001',
					label: 'External Video Shortcut (Global)',
					selected: false,
				},
				{
					className:
						'com.liferay.document.library.kernel.model.DLFileEntryType',
					classPK: '42390',
					label: 'Image with title (Guest)',
					selected: false,
				},
			],
			label: 'Document',
			name: 'Document (4 Items)',
		},
	],
	portletNamespace:
		'_com_liferay_item_selector_web_portlet_ItemSelectorPortlet_',
};

const mockEmptyTreeProps = {
	childrenPropertyKey: 'itemSubtypes',
	itemSelectorSaveEvent:
		'_com_liferay_content_dashboard_web_portlet_ContentDashboardAdminPortlet_selectedContentDashboardItemSubtype',
	mandatoryFieldsForFiltering: ['className', 'classPK'],
	namePropertyKey: 'label',
	nodes: [],
	portletNamespace:
		'_com_liferay_item_selector_web_portlet_ItemSelectorPortlet_',
};

describe('SelectFileExtension', () => {
	beforeEach(() => {
		cleanup();

		window.Liferay.Util.getOpener = jest.fn().mockReturnValue({
			Liferay: {
				fire: jest.fn(),
			},
		});
	});

	it('renders a Treeview with parent nodes and only first node expanded', () => {
		const {getByRole, getByText, queryByText} = render(
			<TreeFilter {...mockTypesProps} />
		);

		const {className} = getByRole('tree');
		expect(className).toContain('lfr-treeview-node-list');

		expect(
			getByText('Web Content Article', {exact: false})
		).toBeInTheDocument();
		expect(getByText('Document', {exact: false})).toBeInTheDocument();
		expect(getByText('Basic Web Content')).toBeInTheDocument();
		expect(queryByText('External Video Shortcut')).not.toBeInTheDocument();
	});

	it('renders a Treeview with filtered nodes (parents and children) if query is set', () => {
		const {getByPlaceholderText, getByText, queryByText} = render(
			<TreeFilter {...mockTypesProps} />
		);

		const input = getByPlaceholderText('search');
		fireEvent.change(input, {target: {value: 'web content'}});

		expect(
			getByText('Web Content Article', {exact: false})
		).toBeInTheDocument();
		expect(
			getByText('Basic Web Content', {exact: false})
		).toBeInTheDocument();

		expect(queryByText('Basic Document')).not.toBeInTheDocument();
		expect(queryByText('Document')).not.toBeInTheDocument();
	});

	it('renders all children collapsed when the query matches the parent but no children', () => {
		const {getByPlaceholderText, getByText, queryByText} = render(
			<TreeFilter {...mockExtensionsProps} />
		);
		const input = getByPlaceholderText('search');
		fireEvent.change(input, {target: {value: 'Image'}});
		expect(getByText('Image (4 items)')).toBeInTheDocument();

		// The children must be collapsed

		expect(queryByText('gif')).not.toBeInTheDocument();
		expect(queryByText('jpg')).not.toBeInTheDocument();
		expect(queryByText('jpeg')).not.toBeInTheDocument();
		expect(queryByText('png')).not.toBeInTheDocument();
	});

	it('renders the parent node when the query matches any of its children', () => {
		const {getByPlaceholderText, getByText} = render(
			<TreeFilter {...mockExtensionsProps} />
		);
		const input = getByPlaceholderText('search');
		fireEvent.change(input, {target: {value: 'jp'}});
		expect(getByText('Image (2 items)')).toBeInTheDocument();

		expect(getByText('jpg')).toBeInTheDocument();
		expect(getByText('jpeg')).toBeInTheDocument();
	});

	it('renders a Treeview with a selected node if selected is true', () => {
		const {container} = render(<TreeFilter {...mockExtensionsProps} />);

		expect(container.getElementsByClassName('selected').length).toBe(1);
	});

	it('shows empty state when there are no nodes in the tree', () => {
		const {getByText} = render(<TreeFilter {...mockEmptyTreeProps} />);

		expect(getByText('no-results-were-found')).toBeInTheDocument();
	});

	it('shows empty state when the text input does not match with any of the parents or children', () => {
		const {getByPlaceholderText, getByText} = render(
			<TreeFilter {...mockExtensionsProps} />
		);

		const input = getByPlaceholderText('search');
		fireEvent.change(input, {target: {value: 'blabla'}});
		expect(getByText('no-results-were-found')).toBeInTheDocument();
	});

	it('clears the search results by hitting the times icon in the search bar', () => {
		const {container, getByPlaceholderText, getByText} = render(
			<TreeFilter {...mockExtensionsProps} />
		);

		// First we search for something

		const input = getByPlaceholderText('search');
		fireEvent.change(input, {target: {value: 'jp'}});
		expect(getByText('Image (2 items)')).toBeInTheDocument();

		// Then we clear the search input by hitting the clear buttpn

		const clear_button = container.getElementsByClassName(
			'tree-filter-clear'
		);

		expect(clear_button.length).toBe(1);

		fireEvent.click(clear_button[0]);
		expect(getByText('Image (4 Items)')).toBeInTheDocument();
	});

	it('shows the total number of elements selected in the list above the tree', () => {
		const {getByText} = render(<TreeFilter {...mockExtensionsProps} />);

		expect(getByText('1 item-selected')).toBeInTheDocument();

		const node = getByText('wav');
		fireEvent.click(node);

		expect(getByText('2 items-selected')).toBeInTheDocument();
	});
});
