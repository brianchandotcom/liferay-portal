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

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import SelectFileExtension from '../../../src/main/resources/META-INF/resources/js/SelectFileExtension';

const mockProps = {
	fileExtensionGroups: [
		{
			fileExtensions: [
				{
					fileExtension: 'mpga',
					selected: false,
				},
				{
					fileExtension: 'mp3',
					selected: true,
				},
				{
					fileExtension: 'mp2',
					selected: false,
				},
				{
					fileExtension: 'ogg',
					selected: false,
				},
				{
					fileExtension: 'wav',
					selected: false,
				},
			],
			icon: 'document-multimedia',
			label: 'Audio',
		},
		{
			fileExtensions: [
				{
					fileExtension: 'gif',
					selected: false,
				},
				{
					fileExtension: 'jpg',
					selected: false,
				},
				{
					fileExtension: 'jpeg',
					selected: false,
				},
				{
					fileExtension: 'png',
					selected: false,
				},
			],
			icon: 'document-image',
			label: 'Image',
		},
	],
	itemSelectorSaveEvent:
		'_com_liferay_content_dashboard_web_portlet_ContentDashboardAdminPortlet_selectedFileExtension',
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

	it('renders a TreeFilter with parent nodes indicating the number of children', () => {
		const {getByRole, getByText, queryByText} = render(
			<SelectFileExtension {...mockProps} />
		);

		const {className} = getByRole('tree');
		expect(className).toContain('lfr-treeview-node-list');

		expect(
			getByText('Audio (5 items)', {exact: false})
		).toBeInTheDocument();
		expect(
			getByText('Image (4 items)', {exact: false})
		).toBeInTheDocument();
		expect(getByText('mp3')).toBeInTheDocument();
		expect(queryByText('gif')).not.toBeInTheDocument();
	});
});
