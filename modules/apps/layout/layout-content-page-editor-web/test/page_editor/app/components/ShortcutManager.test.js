/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, render, screen} from '@testing-library/react';
import React from 'react';

import {SWITCH_SIDEBAR_PANEL} from '../../../../src/main/resources/META-INF/resources/page_editor/app/actions/types';
import ShortcutManager from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/ShortcutManager';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {ControlsProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext';
import {
	ShortcutContextProvider,
	useSetEditedNodeId,
} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ShortcutContext';
import updateItemStyle from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/updateItemStyle';
import StoreMother from '../../../../src/main/resources/META-INF/resources/page_editor/test_utils/StoreMother';

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
	'../../../../src/main/resources/META-INF/resources/page_editor/app/utils/updateItemStyle',
	() => jest.fn(() => () => Promise.resolve())
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
				<ShortcutContextProvider>
					<ShortcutManager />
				</ShortcutContextProvider>
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
});
