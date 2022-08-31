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

import {act, render, screen} from '@testing-library/react';
import React from 'react';

import {SWITCH_SIDEBAR_PANEL} from '../../../../src/main/resources/META-INF/resources/page_editor/app/actions/types';
import ShortcutManager from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/ShortcutManager';
import StoreMother from '../../../../src/main/resources/META-INF/resources/page_editor/test-utils/StoreMother';

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

		Liferay.FeatureFlags['LPS-153452'] = true;
	});

	afterAll(() => {
		Liferay.FeatureFlags['LPS-153452'] = false;
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
				key: '.',
				keyCode: 190,
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
				key: '.',
				keyCode: 190,
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
