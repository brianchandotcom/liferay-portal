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

import '@testing-library/jest-dom/extend-expect';
import {act, cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import {StoreAPIContextProvider} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import FragmentsSidebar from '../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/fragments-widgets/components/FragmentsSidebar';
import TabsPanel from '../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/fragments-widgets/components/TabsPanel';

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/index',
	() => ({
		config: {
			portletNamespace: 'FragmentSidebarPortlet',
		},
	})
);

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/fragments-widgets/components/TabsPanel',
	() => {
		return jest.fn(() => null);
	}
);

const STATE = {
	fragments: [
		{
			fragmentCollectionId: 'collection-1',
			fragmentEntries: [
				{
					fragmentEntryKey: 'fragment-1',
					groupId: '0',
					icon: 'fragment-1-icon',
					imagePreviewURL: '/fragment-1-image.png',
					label: 'Fragment 1',
					name: 'Fragment 1',
					type: 1,
				},
				{
					fragmentEntryKey: 'fragment-2',
					groupId: '0',
					icon: 'fragment-2-icon',
					imagePreviewURL: '/fragment-2-image.png',
					label: 'Fragment 2',
					name: 'Fragment 2',
					type: 1,
				},
				{
					fragmentEntryKey: 'fragment-3',
					groupId: '0',
					icon: 'fragment-3-icon',
					imagePreviewURL: '/fragment-3-image',
					label: 'Fragment 3',
					name: 'Fragment 3',
					type: 1,
				},
			],
			name: 'Collection 1',
		},
	],
	widgets: [
		{
			categories: [],
			path: 'widget-collection-1',
			portlets: [
				{
					instanceable: true,
					portletId: 'portlet-1',
					portletItems: [],
					title: 'Portlet 1',
					used: false,
				},
			],
			title: 'Widget Collection 1',
		},
	],
};

const NORMALIZED_TABS = [
	{
		collections: [
			{
				children: [
					{
						data: {
							fragmentEntryKey: 'fragment-1',
							groupId: '0',
							type: 1,
						},
						icon: 'fragment-1-icon',
						itemId: 'fragment-1',
						label: 'Fragment 1',
						preview: '/fragment-1-image.png',
						type: 'fragment',
					},
					{
						data: {
							fragmentEntryKey: 'fragment-2',
							groupId: '0',
							type: 1,
						},
						icon: 'fragment-2-icon',
						itemId: 'fragment-2',
						label: 'Fragment 2',
						preview: '/fragment-2-image.png',
						type: 'fragment',
					},
					{
						data: {
							fragmentEntryKey: 'fragment-3',
							groupId: '0',
							type: 1,
						},
						icon: 'fragment-3-icon',
						itemId: 'fragment-3',
						label: 'Fragment 3',
						preview: '/fragment-3-image',
						type: 'fragment',
					},
				],
				collectionId: 'collection-1',
				label: 'Collection 1',
			},
		],
		label: 'fragments',
	},
	{
		collections: [
			{
				children: [
					{
						data: {
							instanceable: true,
							portletId: 'portlet-1',
							portletItemId: null,
							used: false,
						},
						disabled: false,
						icon: 'cards2',
						itemId: 'portlet-1',
						label: 'Portlet 1',
						portletItems: null,
						preview: '',
						type: 'fragment',
					},
				],
				collectionId: 'widget-collection-1',
				label: 'Widget Collection 1',
			},
		],
		label: 'widgets',
	},
];

const renderComponent = (state = {}) => {
	return render(
		<DndProvider backend={HTML5Backend}>
			<StoreAPIContextProvider
				dispatch={() => Promise.resolve({})}
				getState={() => state}
			>
				<FragmentsSidebar />
			</StoreAPIContextProvider>
		</DndProvider>
	);
};

describe('FragmentsSidebar', () => {
	afterEach(() => {
		cleanup();
		TabsPanel.mockClear();
		jest.useFakeTimers();
	});

	it('has a sidebar panel title', () => {
		const {getByText} = renderComponent(STATE);

		expect(getByText('fragments-and-widgets')).toBeInTheDocument();
	});

	it('normalizes fragments and widgets format', () => {
		renderComponent(STATE);

		expect(TabsPanel).toHaveBeenCalledWith(
			{displayStyle: 'list', tabs: NORMALIZED_TABS},
			{}
		);
	});

	it('filters fragments and widgets according to a input value', () => {
		const {getByLabelText, queryByText} = renderComponent(STATE);
		const input = getByLabelText('search-form');

		act(() => {
			fireEvent.change(input, {
				target: {value: 't 1'},
			});

			jest.runAllTimers();
		});

		expect(queryByText('Portlet 1')).toBeInTheDocument();
		expect(queryByText('Fragment 1')).toBeInTheDocument();
		expect(queryByText('Fragment 2')).not.toBeInTheDocument();
		expect(queryByText('Fragment 3')).not.toBeInTheDocument();
	});

	it('filters collections according to a input value', () => {
		const {getByLabelText, queryByText} = renderComponent(STATE);
		const input = getByLabelText('search-form');

		act(() => {
			fireEvent.change(input, {
				target: {value: 'Widget Collection 1'},
			});

			jest.runAllTimers();
		});

		expect(queryByText('Widget Collection 1')).toBeInTheDocument();
		expect(queryByText('Portlet 1')).toBeInTheDocument();
		expect(queryByText('Fragment 1')).not.toBeInTheDocument();
		expect(queryByText('Fragment 2')).not.toBeInTheDocument();
		expect(queryByText('Fragment 3')).not.toBeInTheDocument();
	});

	it('sets square-hole icon when the widget is not instanceable', () => {
		const tabs = NORMALIZED_TABS;
		const state = STATE;

		state.widgets[0].portlets[0].instanceable = false;
		tabs[1].collections[0].children[0] = {
			data: {
				instanceable: false,
				portletId: 'portlet-1',
				portletItemId: null,
				used: false,
			},
			disabled: false,
			icon: 'square-hole',
			itemId: 'portlet-1',
			label: 'Portlet 1',
			portletItems: null,
			preview: '',
			type: 'fragment',
		};
		renderComponent(state);

		expect(TabsPanel).toHaveBeenCalledWith(
			{displayStyle: 'list', tabs},
			{}
		);
	});

	it('disables a widget when it is not instanceable and it is used', () => {
		const tabs = NORMALIZED_TABS;
		const state = STATE;

		state.widgets[0].portlets[0].instanceable = false;
		state.widgets[0].portlets[0].used = true;
		tabs[1].collections[0].children[0] = {
			data: {
				instanceable: false,
				portletId: 'portlet-1',
				portletItemId: null,
				used: true,
			},
			disabled: true,
			icon: 'square-hole',
			itemId: 'portlet-1',
			label: 'Portlet 1',
			portletItems: null,
			preview: '',
			type: 'fragment',
		};
		renderComponent(state);

		expect(TabsPanel).toHaveBeenCalledWith(
			{displayStyle: 'list', tabs},
			{}
		);
	});

	it('normalizes collection with portlets items', () => {
		const tabs = NORMALIZED_TABS;
		const state = STATE;

		state.widgets[0].portlets[0].portletItems[0] = {
			instanceable: true,
			portletId: 'portlet-item-1',
			title: 'Portlet Item 1',
			used: false,
		};
		tabs[1].collections[0].children[0].portletItems = [
			{
				data: {
					instanceable: true,
					portletId: 'portlet-item-1',
					portletItemId: null,
					used: false,
				},
				disabled: false,
				icon: 'cards2',
				itemId: 'portlet-item-1',
				label: 'Portlet Item 1',
				portletItems: null,
				preview: '',
				type: 'fragment',
			},
		];
		renderComponent(state);

		expect(TabsPanel).toHaveBeenCalledWith(
			{displayStyle: 'list', tabs},
			{}
		);
	});

	it('normalizes collection with more collections inside', () => {
		const tabs = NORMALIZED_TABS;
		const state = STATE;

		state.widgets[0].categories = [
			{
				categories: [
					{
						categories: [],
						path: 'collection-4',
						portlets: [
							{
								instanceable: true,
								portletId: 'collection-4-portlet',
								portletItems: [],
								title: 'Collection 4 Portlet',
								used: false,
							},
						],
						title: 'Widget Collection 3',
					},
				],
				path: 'widget-collection-2',
				portlets: [],
				title: 'Widget Collection 2',
			},
		];
		tabs[1].collections[0].collections = [
			{
				children: [],
				collectionId: 'widget-collection-2',
				collections: [
					{
						children: [
							{
								data: {
									instanceable: true,
									portletId: 'collection-4-portlet',
									portletItemId: null,
									used: false,
								},
								disabled: false,
								icon: 'cards2',
								itemId: 'collection-4-portlet',
								label: 'Collection 4 Portlet',
								portletItems: null,
								preview: '',
								type: 'fragment',
							},
						],
						collectionId: 'collection-4',
						label: 'Widget Collection 3',
					},
				],
				label: 'Widget Collection 2',
			},
		];

		renderComponent(state);

		expect(TabsPanel).toHaveBeenCalledWith(
			{displayStyle: 'list', tabs},
			{}
		);
	});
});
