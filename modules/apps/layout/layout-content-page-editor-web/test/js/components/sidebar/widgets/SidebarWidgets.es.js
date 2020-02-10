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
import userEvent from '@testing-library/user-event';
import React from 'react';

import SidebarWidgets from '../../../../../src/main/resources/META-INF/resources/js/components/sidebar/widgets/SidebarWidgets.es';
import StateProvider from '../../../../../src/main/resources/META-INF/resources/js/store/StateProvider.es';
import StoreContext from '../../../../../src/main/resources/META-INF/resources/js/store/StoreContext.es';
import Store from '../../../../../src/main/resources/META-INF/resources/js/store/store.es';

import '@testing-library/jest-dom/extend-expect';

const widgets = [
	{
		portlets: [
			{
				instanceable: true,
				portletId: 'assetPublisher',
				title: 'Asset Publisher',
				used: false
			},
			{
				instanceable: true,
				portletId: 'DocumentsAndMedia',
				title: 'Documents and Media',
				used: false
			},
			{
				instanceable: true,
				portletId: 'NavigationMenu',
				title: 'Navigation Menu',
				used: false
			}
		],
		title: 'Highlighted'
	},
	{
		portlets: [
			{
				instanceable: false,
				portletId: 'Blogs',
				title: 'Blogs',
				used: false
			},
			{
				instanceable: false,
				portletId: 'BlogsAggregator',
				title: 'Blogs Aggregator',
				used: false
			}
		],
		title: 'Collaboration'
	}
];

const store = new Store({widgets}, () => {});

const RenderSidebarWidgets = () => {
	return (
		<StoreContext.Provider value={store}>
			<StateProvider>
				<SidebarWidgets />
			</StateProvider>
		</StoreContext.Provider>
	);
};

describe('SidebarWidgets', () => {
	afterEach(cleanup);

	it('renders the categories collapsed', () => {
		const {getByLabelText, getByText, queryByText} = render(
			<RenderSidebarWidgets />
		);

		expect(getByText('Collaboration')).toBeInTheDocument();
		expect(getByText('Highlighted')).toBeInTheDocument();
		expect(getByLabelText('search-form')).toBeInTheDocument();

		expect(queryByText('Asset Publisher')).toBe(null);
	});

	it('expands a category on click and closes it when clicking it again', () => {
		const {getByText, queryByText} = render(<RenderSidebarWidgets />);
		const highlightedCategory = getByText('Highlighted');

		fireEvent.click(highlightedCategory);

		expect(highlightedCategory.getAttribute('aria-expanded')).toBe('true');

		expect(getByText('Asset Publisher')).toBeInTheDocument();

		fireEvent.click(highlightedCategory);

		expect(highlightedCategory.getAttribute('aria-expanded')).toBe('false');

		expect(queryByText('Asset Publisher')).toBe(null);
	});

	it('finds a widget when you search it', () => {
		const {getByLabelText, getByText, queryByText} = render(
			<RenderSidebarWidgets />
		);

		userEvent.type(getByLabelText('search-form'), 'asset');

		expect(getByText('Asset Publisher')).toBeInTheDocument();

		expect(queryByText('Blogs')).toBe(null);
	});

	it('expands all categories when you type something in search form', () => {
		const {getByLabelText, getByText} = render(<RenderSidebarWidgets />);

		userEvent.type(getByLabelText('search-form'), 'a');

		expect(getByText('Collaboration').getAttribute('aria-expanded')).toBe(
			'true'
		);
		expect(getByText('Highlighted').getAttribute('aria-expanded')).toBe(
			'true'
		);
	});
});
