/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, render, screen} from '@testing-library/react';
import React from 'react';

import {SWITCH_SIDEBAR_PANEL} from '../../../../src/main/resources/META-INF/resources/page_editor/app/actions/types';
import ShortcutManager from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/ShortcutManager';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {
	ClipboardContextProvider,
	useSetClipboard,
} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ClipboardContext';
import {
	ControlsProvider,
	useSelectItem,
} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext';
import {
	ShortcutContextProvider,
	useSetEditedNodeId,
} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ShortcutContext';
import deleteItem from '../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/deleteItem';
import duplicateItem from '../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/duplicateItem';
import pasteItems from '../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/pasteItems';
import updateItemStyle from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/updateItemStyle';
import StoreMother from '../../../../src/main/resources/META-INF/resources/page_editor/test_utils/StoreMother';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ClipboardContext',
	() => {
		const setClipboard = jest.fn();

		return {
			...jest.requireActual(
				'../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ClipboardContext'
			),
			useClipboard: () => ['fragment02'],
			useSetClipboard: () => setClipboard,
		};
	}
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ShortcutContext',
	() => {
		const setEditedNodeId = jest.fn();

		return {
			...jest.requireActual(
				'../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ShortcutContext'
			),
			useSetEditedNodeId: () => setEditedNodeId,
		};
	}
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext',
	() => {
		const selectItem = jest.fn();

		return {
			...jest.requireActual(
				'../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext'
			),
			useActiveItemType: () => 'layoutDataItem',
			useSelectItem: () => selectItem,
		};
	}
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/utils/canBeDuplicated',
	() => jest.fn(() => true)
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/utils/isMovementValid',
	() => ({
		isMovementValid: jest.fn(() => true),
	})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/duplicateItem',
	() => jest.fn()
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/deleteItem',
	() => jest.fn()
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/utils/updateItemStyle',
	() => jest.fn(() => () => Promise.resolve())
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/pasteItems',
	() => jest.fn()
);

const DEFAULT_STATE = {
	fragmentEntryLinks: {
		fragmentEntryLinkId: {},
	},
	layoutData: {
		items: {
			container01: {
				children: ['fragment01'],
				itemId: 'container01',
				type: LAYOUT_DATA_ITEM_TYPES.container,
			},
			fragment01: {
				children: [],
				config: {},
				itemId: 'fragment01',
				parentId: 'container01',
				type: LAYOUT_DATA_ITEM_TYPES.fragment,
			},
			fragment02: {
				itemId: 'fragment02',
				type: LAYOUT_DATA_ITEM_TYPES.fragment,
			},
			root01: {
				children: ['container01', 'fragment02'],
				itemId: 'root01',
				type: LAYOUT_DATA_ITEM_TYPES.root,
			},
		},
		rootItems: {
			main: 'root01',
		},
	},
	permissions: {
		UPDATE: true,
	},
	sidebar: {},
};

const renderComponent = ({
	activeItemIds = [],
	dispatch = () => {},
	state = DEFAULT_STATE,
} = {}) =>
	render(
		<StoreMother.Component dispatch={dispatch} getState={() => state}>
			<ControlsProvider
				activeInitialState={{
					activeItemIds,
				}}
			>
				<ClipboardContextProvider>
					<ShortcutContextProvider>
						<ShortcutManager />
					</ShortcutContextProvider>
				</ClipboardContextProvider>
			</ControlsProvider>
		</StoreMother.Component>
	);

describe('ShortcutManager', () => {
	beforeAll(() => {
		global.Liferay = {
			...global.Liferay,
			Browser: {
				isMac: () => true,
			},
		};

		window.getSelection = () => ({
			type: 'None',
		});
	});

	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('triggers hide sidebar action when pressing cmd + shift + .', () => {
		const mockDispatch = jest.fn((a) => {
			if (typeof a === 'function') {
				return a(mockDispatch);
			}
		});

		renderComponent({dispatch: mockDispatch});

		document.body.dispatchEvent(
			new KeyboardEvent('keydown', {
				code: 'Period',
				metaKey: true,
				shiftKey: true,
			})
		);

		expect(mockDispatch).toBeCalledWith(
			expect.objectContaining({hidden: true, type: SWITCH_SIDEBAR_PANEL})
		);
	});

	it('triggers show sidebar action when pressing cmd + shift + . and the sidebar is hidden', () => {
		const mockDispatch = jest.fn((a) => {
			if (typeof a === 'function') {
				return a(mockDispatch);
			}
		});

		renderComponent({
			dispatch: mockDispatch,
			state: {
				...DEFAULT_STATE,
				sidebar: {
					hidden: true,
				},
			},
		});

		document.body.dispatchEvent(
			new KeyboardEvent('keydown', {
				code: 'Period',
				metaKey: true,
				shiftKey: true,
			})
		);

		expect(mockDispatch).toBeCalledWith(
			expect.objectContaining({hidden: false, type: SWITCH_SIDEBAR_PANEL})
		);
	});

	it('triggers show shortcuts modal when pressing shift + ?', () => {
		renderComponent();

		jest.useFakeTimers();

		// Clay modal have an animation when are opened
		// This will make sure that the body is visible before asserting

		act(() => {
			document.body.dispatchEvent(
				new KeyboardEvent('keydown', {
					key: '?',
					shiftKey: true,
				})
			);
		});

		act(() => {
			jest.runAllTimers();
		});

		screen.getByText('keyboard-shortcuts');
	});

	it('calls selectItem to select the parent when pressing shift + Enter', () => {
		renderComponent({
			activeItemIds: ['fragment01'],
		});

		const selectItem = useSelectItem();

		document.body.dispatchEvent(
			new KeyboardEvent('keydown', {
				key: 'Enter',
				shiftKey: true,
			})
		);

		expect(selectItem).toBeCalledWith('container01', {
			itemType: 'layoutDataItem',
			origin: 'layout',
		});
	});

	it('sets the item id to be renamed when pressing ctrl + alt + R', () => {
		const setEditedNodeId = useSetEditedNodeId();

		renderComponent({
			activeItemIds: ['fragment01'],
		});

		document.body.dispatchEvent(
			new KeyboardEvent('keydown', {
				altKey: true,
				code: 'KeyR',
				ctrlKey: true,
			})
		);

		expect(setEditedNodeId).toBeCalledWith('fragment01');
	});

	it('calls updateItemStyle when pressing ctrl + H', () => {
		const newState = {...DEFAULT_STATE};

		newState.layoutData.items.fragment01 = {
			children: [],
			config: {
				fragmentEntryLinkId: 'fragmenEntryLinkId',
				styles: {diplay: 'none'},
			},
			itemId: 'fragment01',
		};

		renderComponent({
			activeItemIds: ['fragment01'],
			state: newState,
		});

		document.body.dispatchEvent(
			new KeyboardEvent('keydown', {
				altKey: true,
				code: 'KeyH',
				ctrlKey: true,
			})
		);

		expect(updateItemStyle).toBeCalledWith(
			expect.objectContaining({
				itemIds: ['fragment01'],
				selectedViewportSize: 'desktop',
				styleName: 'display',
				styleValue: 'none',
			})
		);
	});

	it('sets the item Id and calls deleteItem to be cut when pressing ctrl + X', () => {
		Liferay.FeatureFlags['LPD-18221'] = true;

		const setClipboard = useSetClipboard();

		renderComponent({
			activeItemIds: ['fragment01'],
		});

		document.body.dispatchEvent(
			new KeyboardEvent('keydown', {
				code: 'KeyX',
				ctrlKey: true,
			})
		);

		expect(deleteItem).toBeCalledWith(
			expect.objectContaining({
				itemIds: ['fragment01'],
			})
		);

		expect(setClipboard).toBeCalledWith(['fragment01']);

		Liferay.FeatureFlags['LPD-18221'] = false;
	});

	it('sets the item id to be copied when pressing ctrl + C', () => {
		Liferay.FeatureFlags['LPD-18221'] = true;

		const setClipboard = useSetClipboard();

		renderComponent({
			activeItemIds: ['fragment01'],
		});

		document.body.dispatchEvent(
			new KeyboardEvent('keydown', {
				code: 'KeyC',
				ctrlKey: true,
				shiftKey: false,
			})
		);

		expect(setClipboard).toBeCalledWith(['fragment01']);

		Liferay.FeatureFlags['LPD-18221'] = false;
	});

	it('calls pasteItems when pressing ctrl + V', () => {
		Liferay.FeatureFlags['LPD-18221'] = true;

		renderComponent({
			activeItemIds: ['fragment01'],
		});

		document.body.dispatchEvent(
			new KeyboardEvent('keydown', {
				code: 'KeyV',
				ctrlKey: true,
			})
		);

		expect(pasteItems).toBeCalledWith(
			expect.objectContaining({
				clipboard: ['fragment02'],
				parentItemId: 'fragment01',
			})
		);

		Liferay.FeatureFlags['LPD-18221'] = false;
	});

	it('item id will be copied to the root because no parents are selected', () => {
		Liferay.FeatureFlags['LPD-18221'] = true;

		renderComponent({
			activeItemIds: [],
		});

		document.body.dispatchEvent(
			new KeyboardEvent('keydown', {
				code: 'KeyV',
				ctrlKey: true,
				shiftKey: true,
			})
		);

		expect(pasteItems).toBeCalledWith(
			expect.objectContaining({
				clipboard: ['fragment02'],
				parentItemId: 'root01',
			})
		);

		Liferay.FeatureFlags['LPD-18221'] = false;
	});

	it('cannot paste items because multiple parents are selected', () => {
		Liferay.FeatureFlags['LPD-18221'] = true;

		renderComponent({
			activeItemIds: ['fragment01', 'fragment02'],
		});

		document.body.dispatchEvent(
			new KeyboardEvent('keydown', {
				code: 'KeyV',
				ctrlKey: true,
				shiftKey: true,
			})
		);

		expect(pasteItems).toBeCalledTimes(0);

		Liferay.FeatureFlags['LPD-18221'] = false;
	});

	it('calls duplicateItem when pressing ctrl + alt + D', () => {
		renderComponent({
			activeItemIds: ['fragment01'],
		});

		document.body.dispatchEvent(
			new KeyboardEvent('keydown', {
				altKey: true,
				code: 'KeyD',
				ctrlKey: true,
			})
		);

		expect(duplicateItem).toBeCalledWith(
			expect.objectContaining({
				itemIds: ['fragment01'],
			})
		);
	});
});
