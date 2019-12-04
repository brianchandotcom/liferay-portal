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

import ItemSelectorPreview from '../../src/main/resources/META-INF/resources/item_selector_preview/js/ItemSelectorPreview.es';

const basicMetadata = {
	groups: [
		{
			data: [
				{
					key: Liferay.Language.get('format'),
					value: "jpg"
				},
				{
					key: Liferay.Language.get('name'),
					value: "test image.jpg"
				}
			],
			title: 'file-info'
		}
	]
};
const itemTitle = 'test image.jpg';
const itemUrl = 'image1.jpg';
const headerTitle = 'Images';

const items = [
	{
		metadata: JSON.stringify(basicMetadata),
		returntype: 'returntype',
		title: itemTitle,
		url: itemUrl,
		value: itemUrl,
	}
];

const previewProps = {
	container: document.createElement('div'),
	handleSelectedItem: jest.fn(),
	headerTitle: headerTitle,
	items
}

const renderPreviewComponent = (props) => render(<ItemSelectorPreview {...props} />);

describe('ItemSelectorPreview', () => {
	beforeAll(() => {
		Liferay.SideNavigation = jest.fn();
		Liferay.SideNavigation.initialize = jest.fn();
	});

	afterEach(cleanup);

	it('renders the ItemSelectorPreview component with the fullscreen class', () => {
		const {container} = renderPreviewComponent(previewProps);

		expect(container.firstChild.classList).toContain('fullscreen');
	});

	it('renders the header component', () => {
		const {getByText} = renderPreviewComponent(previewProps);

		expect(getByText(headerTitle));
	});

	it('renders the carousel component', () => {
		const {getAllByRole} = renderPreviewComponent(previewProps);

		expect(getAllByRole('img').length).toBe(1);
	});
});
