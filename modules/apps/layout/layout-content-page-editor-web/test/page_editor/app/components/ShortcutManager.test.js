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
	useSetCopiedItemIds,
} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ClipboardContext';
import {ControlsProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext';
import {
	ShortcutContextProvider,
	useSetEditedNodeId,
} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ShortcutContext';
import deleteItem from '../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/deleteItem';
import duplicateItem from '../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/duplicateItem';
import pasteItem from '../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/pasteItem';
import updateItemStyle from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/updateItemStyle';
import StoreMother from '../../../../src/main/resources/META-INF/resources/page_editor/test_utils/StoreMother';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ClipboardContext',
	() => {
		const setCopiedItemIds = jest.fn();

		return {
			...jest.requireActual(
				'../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ClipboardContext'
			),
			useSetCopiedItemIds: () => setCopiedItemIds,
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
	'../../../../src/main/resources/META-INF/resources/page_editor/app/utils/canBeDuplicated',
	() => jest.fn(() => true)
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
	'../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/pasteItem',
	() => jest.fn()
);

const DEFAULT_STATE = {
	fragmentEntryLinks: {
		fragmentEntryLinkId: {},
	},
	layoutData: {
		items: {
			fragment01: {
				itemId: 'fragment01',
				type: LAYOUT_DATA_ITEM_TYPES.fragment,
			},
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

	it('sets the node id to be renamed when pressing ctrl + alt + R', () => {
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

	it('sets the node id to be cut when pressing ctrl + X', () => {
		Liferay.FeatureFlags['LPD-18221'] = true;

		const setCopiedItemIds = useSetCopiedItemIds();

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

		expect(setCopiedItemIds).toBeCalledWith(['fragment01']);

		Liferay.FeatureFlags['LPD-18221'] = false;
	});

	it('sets the node id to be copied when pressing ctrl + C', () => {
		Liferay.FeatureFlags['LPD-18221'] = true;

		const setCopiedItemIds = useSetCopiedItemIds();

		renderComponent({
			activeItemIds: ['fragment01'],
		});

		document.body.dispatchEvent(
			new KeyboardEvent('keydown', {
				code: 'KeyC',
				ctrlKey: true,
			})
		);

		expect(setCopiedItemIds).toBeCalledWith(['fragment01']);

		Liferay.FeatureFlags['LPD-18221'] = false;
	});

	it('sets the node id to be pasted when pressing ctrl + V', () => {
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

		expect(pasteItem).toBeCalledWith(
			expect.objectContaining({
				copyItemIds: [],
				parentItemId: 'fragment01',
			})
		);

		Liferay.FeatureFlags['LPD-18221'] = false;
	});

	it('sets the node id to be duplicated when pressing ctrl + alt + D', () => {
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
