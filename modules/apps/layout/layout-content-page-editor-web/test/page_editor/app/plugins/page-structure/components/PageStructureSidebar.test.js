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
import {cleanup, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {ControlsProvider} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/components/Controls';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/editableFragmentEntryProcessor';
import {LAYOUT_DATA_ITEM_TYPE_LABELS} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypeLabels';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {VIEWPORT_SIZES} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/viewportSizes';
import {StoreAPIContextProvider} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/store/index';
import PageStructureSidebar from '../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/page-structure/components/PageStructureSidebar';

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({config: {layoutType: 'content'}})
);

const renderComponent = ({
	activeItemId = null,
	hasUpdatePermissions = true,
	lockedExperience = false,
	masterRootItemChildren = ['11-container'],
	rootItemChildren = ['01-container'],
	viewportSize = VIEWPORT_SIZES.desktop,
} = {}) => {
	Liferay.Util.sub.mockImplementation((langKey, args) =>
		[langKey, ...args].join('-')
	);

	return render(
		<ControlsProvider
			activeInitialState={{
				activationOrigin: null,
				activeItemId,
				activeItemType: null,
			}}
			hoverInitialState={{
				hoveredItemId: null,
			}}
		>
			<StoreAPIContextProvider
				getState={() => ({
					fragmentEntryLinks: {
						'001': {
							content: {
								value: {
									content: '<div>001</div>',
								},
							},
							editableTypes: {
								'05-editable': 'text',
							},
							editableValues: {
								[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
									'05-editable': {
										defaultValue: 'defaultValue',
									},
								},
							},
							fragmentEntryLinkId: '001',
							name: 'Fragment 1',
						},
					},

					layoutData: {
						items: {
							'00-main': {
								children: rootItemChildren,
								config: {},
								itemId: '00-main',
								parentId: null,
								type: LAYOUT_DATA_ITEM_TYPES.root,
							},
							'01-container': {
								children: ['02-row'],
								config: {},
								itemId: '01-container',
								parentId: 'main',
								type: LAYOUT_DATA_ITEM_TYPES.container,
							},
							'02-row': {
								children: ['03-column'],
								config: {},
								itemId: '02-row',
								parentId: '01-container',
								type: LAYOUT_DATA_ITEM_TYPES.row,
							},
							'03-column': {
								children: ['04-fragment'],
								config: {},
								itemId: '03-column',
								parentId: '02-row',
								type: LAYOUT_DATA_ITEM_TYPES.column,
							},
							'04-fragment': {
								children: [],
								config: {
									fragmentEntryLinkId: '001',
								},
								itemId: '04-fragment',
								parentId: '03-column',
								type: LAYOUT_DATA_ITEM_TYPES.fragment,
							},
							'05-row': {
								children: [],
								config: {
									fragmentEntryLinkId: '001',
								},
								itemId: '05-row',
								parentId: '03-column',
								type: LAYOUT_DATA_ITEM_TYPES.fragment,
							},
						},

						rootItems: {main: '00-main'},
						version: 1,
					},

					masterLayout: {
						masterLayoutData: {
							items: {
								'10-main': {
									children: masterRootItemChildren,
									config: {},
									itemId: '10-main',
									parentId: null,
									type: LAYOUT_DATA_ITEM_TYPES.root,
								},
								'11-container': {
									children: ['12-dropzone'],
									config: {},
									itemId: '11-container',
									parentId: '10-main',
									type: LAYOUT_DATA_ITEM_TYPES.container,
								},
								'12-dropzone': {
									children: [],
									config: {},
									itemId: '12-dropzone',
									parentId: '11-container',
									type: LAYOUT_DATA_ITEM_TYPES.dropZone,
								},
							},

							rootItems: {main: '10-main'},
							version: 1,
						},
						masterLayoutPlid: '0',
					},

					permissions: {
						LOCKED_SEGMENTS_EXPERIMENT: lockedExperience,
						UPDATE: hasUpdatePermissions,
					},

					selectedViewportSize: viewportSize,
				})}
			>
				<PageStructureSidebar />
			</StoreAPIContextProvider>
		</ControlsProvider>
	);
};

describe('PageStructureSidebar', () => {
	afterEach(cleanup);

	it('has a sidebar panel title', () => {
		const {getByText} = renderComponent();

		expect(getByText('selection')).toBeInTheDocument();
	});

	it('has a warning message when there is no content', () => {
		const {getByText} = renderComponent({
			masterRootItemChildren: [],
			rootItemChildren: [],
		});

		expect(
			getByText('there-is-no-content-on-this-page')
		).toBeInTheDocument();
	});

	it('uses fragments names as labels', () => {
		const {getByText} = renderComponent({
			activeItemId: '11-container',
			rootItemChildren: ['04-fragment'],
		});

		expect(getByText('Fragment 1')).toBeInTheDocument();
	});

	it('uses default labels for containers, columns, rows', () => {
		const {getAllByText} = renderComponent({
			activeItemId: '11-container',
			rootItemChildren: ['01-container', '02-row', '03-column'],
		});

		['container', 'row', 'column'].forEach((itemLabel) =>
			getAllByText(
				LAYOUT_DATA_ITEM_TYPE_LABELS[itemLabel]
			).forEach((element) => expect(element).toBeInTheDocument())
		);
	});

	it('sets activeItemId as selected item', () => {
		const {getByLabelText} = renderComponent({
			activeItemId: '11-container',
		});

		expect(getByLabelText('Collapse container')).toHaveAttribute(
			'aria-expanded',
			'true'
		);
	});

	it('disables items that are in masterLayout', () => {
		const {getByLabelText} = renderComponent();
		const button = getByLabelText('select-x-container');
		expect(button).toBeDisabled();
	});

	it('allows removing items of certain types', () => {
		const {queryByLabelText} = renderComponent({
			activeItemId: '11-container',
			rootItemChildren: [
				'01-container',
				'02-row',
				'03-column',
				'04-fragment',
			],
		});

		expect(queryByLabelText('remove-x-container')).toBeInTheDocument();
		expect(queryByLabelText('remove-x-grid')).toBeInTheDocument();
		expect(queryByLabelText('remove-x-module')).toBe(null);
		expect(queryByLabelText('remove-x-Fragment 1')).toBeInTheDocument();
	});

	it('scans fragments editables', () => {
		const {queryByLabelText} = renderComponent({
			activeItemId: '04-fragment',
			rootItemChildren: ['04-fragment'],
		});

		expect(queryByLabelText('select-x-text')).toBeInTheDocument();
		expect(queryByLabelText('remove-x-text')).toBe(null);
	});

	it('sets element as active item', () => {
		const {getByLabelText} = renderComponent({
			activeItemId: '03-column',
		});
		const button = getByLabelText('select-x-grid');

		userEvent.click(button);

		expect(button.parentElement).toHaveAttribute('aria-selected', 'true');
	});

	it('sets element as active item when it is a fragment', () => {
		const {getByLabelText} = renderComponent({
			activeItemId: '03-column',
		});
		const button = getByLabelText('select-x-Fragment 1');

		userEvent.click(button);

		expect(button.parentElement).toHaveAttribute('aria-selected', 'true');
	});

	it('sets element as active item when it is a column', () => {
		const {getByLabelText} = renderComponent({
			activeItemId: '02-row',
		});
		const button = getByLabelText('select-x-module');

		userEvent.click(button);

		expect(button.parentElement).toHaveAttribute('aria-selected', 'false');
	});

	it('does not allow removing items if user has no permissions', () => {
		const {queryByLabelText} = renderComponent({
			hasUpdatePermissions: false,
			rootItemChildren: ['01-container', '02-row', '04-fragment'],
		});

		expect(queryByLabelText('remove-x-container')).toBe(null);
		expect(queryByLabelText('remove-x-grid')).toBe(null);
		expect(queryByLabelText('remove-x-Fragment 1')).toBe(null);
	});

	it('does not allow removing items if viewport is not desktop', () => {
		const {queryByLabelText} = renderComponent({
			activeItemId: '11-container',
			rootItemChildren: ['01-container', '02-row', '04-fragment'],
			viewportSize: VIEWPORT_SIZES.portraitMobile,
		});

		expect(queryByLabelText('remove-x-container')).toBe(null);
		expect(queryByLabelText('remove-x-grid')).toBe(null);
		expect(queryByLabelText('remove-x-Fragment 1')).toBe(null);
	});
});
