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

import SelectTypeAndSubtype from '../../../src/main/resources/META-INF/resources/js/SelectTypeAndSubtype';

const mockProps = {
	contentDashboardItemTypes: [
		{
			icon: 'web-content',
			itemSubtypes: [
				{
					className:
						'com.liferay.dynamic.data.mapping.model.DDMStructure',
					classPK: '40873',
					label: 'Basic Web Content',
					selected: false,
				},
			],
			label: 'Web Content Article',
		},
		{
			icon: 'documents-and-media',
			itemSubtypes: [
				{
					className:
						'com.liferay.document.library.kernel.model.DLFileEntryType',
					classPK: '0',
					label: 'Basic Document',
					selected: true,
				},
				{
					className:
						'com.liferay.document.library.kernel.model.DLFileEntryType',
					classPK: '40709',
					label: 'External Video Shortcut',
					selected: true,
				},
				{
					className:
						'com.liferay.document.library.kernel.model.DLFileEntryType',
					classPK: '40761',
					label: 'Google Drive Shortcut',
					selected: false,
				},
			],

			label: 'Document',
		},
	],
	itemSelectorSaveEvent:
		'_com_liferay_content_dashboard_web_portlet_ContentDashboardAdminPortlet_selectedContentDashboardItemSubtype',
	portletNamespace:
		'_com_liferay_item_selector_web_portlet_ItemSelectorPortlet_',
};

describe('SelectTypeAndSubtype', () => {
	beforeEach(() => {
		cleanup();

		window.Liferay.Util.getOpener = jest.fn().mockReturnValue({
			Liferay: {
				fire: jest.fn(),
			},
		});
	});

	it('renders a Treeview with parent nodes and one child expanded', () => {
		const {getByRole, getByText} = render(
			<SelectTypeAndSubtype {...mockProps} />
		);

		const {className} = getByRole('tree');
		expect(className).toContain('lfr-treeview-node-list');

		expect(
			getByText('Web Content Article', {exact: false})
		).toBeInTheDocument();
		expect(getByText('Document (3 items)')).toBeInTheDocument();
		expect(getByText('Basic Web Content')).toBeInTheDocument();
	});

	it('renders a Treeview with filtered nodes if query is set', () => {
		const {getByPlaceholderText, getByText, queryByText} = render(
			<SelectTypeAndSubtype {...mockProps} />
		);

		const input = getByPlaceholderText('search');
		fireEvent.change(input, {target: {value: 'external'}});
		expect(getByText('External Video Shortcut')).toBeInTheDocument();
		expect(
			queryByText('Web Content Article', {exact: false})
		).not.toBeInTheDocument();
	});

	it('renders a Treeview with a selected nodes if selected is true', () => {
		const {container} = render(<SelectTypeAndSubtype {...mockProps} />);

		expect(container.getElementsByClassName('selected').length).toBe(2);
	});
});
