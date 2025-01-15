/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import KeyboardMovementManager, {
	getInitialTarget,
} from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/keyboard_movement/KeyboardMovementManager';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {
	useDisableKeyboardMovement,
	useMovementSources,
	useSetMovementTarget,
} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/KeyboardMovementContext';
import moveItems from '../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/moveItems';
import StoreMother from '../../../../src/main/resources/META-INF/resources/page_editor/test_utils/StoreMother';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/KeyboardMovementContext',
	() => {
		const initialTarget = {
			itemId: 'item-2',
			position: 'bottom',
		};

		const sources = [
			{
				fieldTypes: [],
				fragmentEntryType: 'component',
				isWidget: false,
				itemId: 'item-3',
				name: 'Item 3',
				type: 'fragment',
			},
		];

		const disableMovement = jest.fn();
		const setTarget = jest.fn();
		const setText = jest.fn();

		return {
			useDisableKeyboardMovement: () => disableMovement,
			useMovementSources: jest.fn(() => sources),
			useMovementTarget: () => initialTarget,
			useSetMovementTarget: () => setTarget,
			useSetMovementText: () => setText,
		};
	}
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/moveItems',
	() => jest.fn()
);

const renderComponent = ({dispatch = () => {}} = {}) =>
	render(
		<StoreMother.Component
			dispatch={dispatch}
			getState={() => ({
				fragmentEntryLinks: {
					['item-1-fragment-entry-link']: {
						fragmentEntryLinkId: 'item-1-fragment-entry-link',
						fragmentEntryType: 'component',
					},
					['item-2-fragment-entry-link']: {
						fragmentEntryLinkId: 'item-2-fragment-entry-link',
						fragmentEntryType: 'component',
					},
					['item-3-fragment-entry-link']: {
						fragmentEntryLinkId: 'item-3-fragment-entry-link',
						fragmentEntryType: 'component',
					},
				},
				layoutData: {
					items: {
						['item-1']: {
							children: [],
							config: {
								fragmentEntryLinkId:
									'item-1-fragment-entry-link',
							},
							itemId: 'item-1',
							parentId: 'root-id',
							type: LAYOUT_DATA_ITEM_TYPES.fragment,
						},
						['item-2']: {
							children: [],
							config: {
								fragmentEntryLinkId:
									'item-2-fragment-entry-link',
							},
							itemId: 'item-2',
							parentId: 'root-id',
							type: LAYOUT_DATA_ITEM_TYPES.fragment,
						},
						['item-3']: {
							children: [],
							config: {
								fragmentEntryLinkId:
									'item-3-fragment-entry-link',
							},
							itemId: 'item-3',
							parentId: 'root-id',
							type: LAYOUT_DATA_ITEM_TYPES.fragment,
						},
						['item-4']: {
							children: [],
							config: {},
							itemId: 'item-4',
							parentId: 'root-id',
							type: LAYOUT_DATA_ITEM_TYPES.fragment,
						},
						['root-id']: {
							children: ['item-1', 'item-2', 'item-3', 'item-4'],
							config: {},
							itemId: 'root-id',
							type: LAYOUT_DATA_ITEM_TYPES.root,
						},
					},
				},
			})}
		>
			<KeyboardMovementManager />
		</StoreMother.Component>
	);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/config/index',
	() => ({
		config: {
			formTypes: [
				{
					label: 'Form Type 1',
					subtypes: [],
					value: 'form-type-1',
				},
			],
		},
	})
);

describe('KeyboardMovementManager', () => {
	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('calculates previous drop position when pressing up arrow', () => {
		renderComponent();

		const setTarget = useSetMovementTarget();

		document.body.dispatchEvent(
			new KeyboardEvent('keydown', {
				code: 'ArrowUp',
			})
		);

		expect(setTarget).toBeCalledWith(
			expect.objectContaining({
				itemId: 'item-1',
				name: 'fragment',
				position: 'bottom',
			})
		);
	});

	it('calculates next drop position when pressing up down', () => {
		renderComponent();

		const setTarget = useSetMovementTarget();

		document.body.dispatchEvent(
			new KeyboardEvent('keydown', {
				code: 'ArrowDown',
			})
		);

		expect(setTarget).toBeCalledWith(
			expect.objectContaining({
				itemId: 'item-3',
				name: 'Item 3',
				position: 'bottom',
			})
		);
	});

	it('disables movement when pressing escape', () => {
		renderComponent();

		const disableMovement = useDisableKeyboardMovement();

		document.body.dispatchEvent(
			new KeyboardEvent('keydown', {
				code: 'Escape',
			})
		);

		expect(disableMovement).toBeCalled();
	});

	it('calls move item thunk when pressing enter', () => {
		const mockDispatch = jest.fn((a) => {
			if (typeof a === 'function') {
				return a(mockDispatch);
			}
		});

		renderComponent({dispatch: mockDispatch});

		document.body.dispatchEvent(
			new KeyboardEvent('keydown', {
				code: 'Enter',
			})
		);

		expect(moveItems).toBeCalledWith(
			expect.objectContaining({
				itemIds: ['item-3'],
				parentItemIds: ['root-id'],
				positions: [2],
			})
		);
	});

	it('calls move item thunk with two items when pressing enter', () => {
		const mockDispatch = jest.fn((a) => {
			if (typeof a === 'function') {
				return a(mockDispatch);
			}
		});

		useMovementSources.mockImplementation(() => [
			{
				fieldTypes: [],
				fragmentEntryType: 'component',
				isWidget: false,
				itemId: 'item-3',
				name: 'Item 3',
				type: 'fragment',
			},
			{
				fieldTypes: [],
				fragmentEntryType: 'component',
				isWidget: false,
				itemId: 'item-1',
				name: 'Item 1',
				type: 'fragment',
			},
		]);

		renderComponent({dispatch: mockDispatch});

		document.body.dispatchEvent(
			new KeyboardEvent('keydown', {
				code: 'Enter',
			})
		);

		expect(moveItems).toBeCalledWith(
			expect.objectContaining({
				itemIds: ['item-3', 'item-1'],
				parentItemIds: ['root-id'],
				positions: [1],
			})
		);
	});

	it('does not call move item thunk when pressing enter and some of the active items are the same as the target', () => {
		const mockDispatch = jest.fn((a) => {
			if (typeof a === 'function') {
				return a(mockDispatch);
			}
		});

		useMovementSources.mockImplementation(() => [
			{
				fieldTypes: [],
				fragmentEntryType: 'component',
				isWidget: false,
				itemId: 'item-3',
				name: 'Item 3',
				type: 'fragment',
			},
			{
				fieldTypes: [],
				fragmentEntryType: 'component',
				isWidget: false,
				itemId: 'item-2',
				name: 'Item 2',
				type: 'fragment',
			},
		]);

		renderComponent({dispatch: mockDispatch});

		document.body.dispatchEvent(
			new KeyboardEvent('keydown', {
				code: 'Enter',
			})
		);

		expect(moveItems).toBeCalledTimes(0);
	});

	it('looks for initial target recursively', () => {
		const layoutDataWithUnmappedForm = {
			deletedItems: [],

			items: {
				'form-id': {
					children: [],
					config: {
						classNameId: 'form-type-1',
						classTypeId: '0',
					},
					itemId: 'form-id',
					parentId: 'root-id',
					type: 'form',
				},
				'root-id': {
					children: ['form-id'],
					itemId: 'root-id',
					type: 'root',
				},
			},
			pageRules: [],
			rootItems: {
				main: 'root-id',
			},
		};

		const formInputSource = {
			fieldTypes: [],
			fragmentEntryKey: 'INPUTS-date-input',
			fragmentEntryType: 'input',
			name: 'Date',
			type: 'fragment',
		};

		const fragmentEntryLinksRef = {current: {}};
		const layoutDataRef = {current: layoutDataWithUnmappedForm};
		const widgetsRef = {current: {}};

		expect(
			getInitialTarget(
				[formInputSource],
				layoutDataRef,
				fragmentEntryLinksRef,
				widgetsRef
			)
		).toMatchObject({
			itemId: 'form-id',
			name: 'form-container',
			position: 'middle',
		});
	});
});
