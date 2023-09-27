/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import KeyboardMovementManager from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/KeyboardMovementManager';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {
	useDisableKeyboardMovement,
	useSetMovementTarget,
} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/KeyboardMovementContext';
import moveItem from '../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/moveItem';
import StoreMother from '../../../../src/main/resources/META-INF/resources/page_editor/test_utils/StoreMother';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/KeyboardMovementContext',
	() => {
		const initialTarget = {
			itemId: 'item-2',
			position: 'bottom',
		};

		const source = {
			fragmentEntryType: 'component',
			isWidget: false,
			itemId: 'item-3',
			name: 'Item 3',
			type: 'fragment',
		};

		const disableMovement = jest.fn();
		const setTarget = jest.fn();
		const setText = jest.fn();

		return {
			useDisableKeyboardMovement: () => disableMovement,
			useMovementSource: () => source,
			useMovementTarget: () => initialTarget,
			useSetMovementTarget: () => setTarget,
			useSetMovementText: () => setText,
		};
	}
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/moveItem',
	() => jest.fn()
);

const renderComponent = ({dispatch = () => {}} = {}) =>
	render(
		<StoreMother.Component
			dispatch={dispatch}
			getState={() => ({
				fragmentEntryLinks: [],
				layoutData: {
					items: {
						['item-1']: {
							children: [],
							config: {},
							itemId: 'item-1',
							parentId: 'root-id',
							type: LAYOUT_DATA_ITEM_TYPES.fragment,
						},
						['item-2']: {
							children: [],
							config: {},
							itemId: 'item-2',
							parentId: 'root-id',
							type: LAYOUT_DATA_ITEM_TYPES.fragment,
						},
						['item-3']: {
							children: [],
							config: {},
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

describe('KeyboardMovementManager', () => {
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
				code: 'ArrrwDown',
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

		expect(moveItem).toBeCalledWith(
			expect.objectContaining({
				itemId: 'item-3',
				parentItemId: 'root-id',
				position: 2,
			})
		);
	});
});
