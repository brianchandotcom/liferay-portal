/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, render, screen} from '@testing-library/react';
import React from 'react';

import {SWITCH_SIDEBAR_PANEL} from '../../../../src/main/resources/META-INF/resources/page_editor/app/actions/types';
import ShortcutManager from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/ShortcutManager';
import StoreMother from '../../../../src/main/resources/META-INF/resources/page_editor/test_utils/StoreMother';

const DEFAULT_STATE = {
	permissions: {
		UPDATE: true,
	},
	sidebar: {},
};

const renderComponent = ({dispatch = () => {}, state = DEFAULT_STATE} = {}) =>
	render(
		<StoreMother.Component dispatch={dispatch} getState={() => state}>
			<ShortcutManager />
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

	it('triggers show shorcuts modal when pressing shift + ?', () => {
		renderComponent();

		jest.useFakeTimers();

		// Clay modal have an animation when are opened
		// This will make sure that the body is visible before asserting

		act(() => {
			document.body.dispatchEvent(
				new KeyboardEvent('keydown', {
					key: '?',
					metaKey: false,
					shiftKey: true,
				})
			);
		});

		act(() => {
			jest.runAllTimers();
		});

		screen.getByText('keyboard-shortcuts');
	});
});
