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

import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {SWITCH_SIDEBAR_PANEL} from '../../../../src/main/resources/META-INF/resources/page_editor/app/actions/types';
import HideSidebarButton from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/HideSidebarButton';
import StoreMother from '../../../../src/main/resources/META-INF/resources/page_editor/test-utils/StoreMother';

const DEFAULT_STATE = {
	sidebar: {},
};

const renderComponent = ({dispatch = () => {}, state = DEFAULT_STATE} = {}) =>
	render(
		<StoreMother.Component dispatch={dispatch} getState={() => state}>
			<HideSidebarButton />
		</StoreMother.Component>
	);

describe('HideSidebarButton', () => {
	it('triggers hide sidebar action', () => {
		const mockDispatch = jest.fn((a) => {
			if (typeof a === 'function') {
				return a(mockDispatch);
			}
		});

		renderComponent({dispatch: mockDispatch});

		userEvent.click(screen.getByTitle('toggle-sidebars', {exact: false}));

		expect(mockDispatch).toBeCalledWith(
			expect.objectContaining({hidden: true, type: SWITCH_SIDEBAR_PANEL})
		);
	});

	it('triggers show sidebar action when sidebar is hidden', () => {
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

		userEvent.click(screen.getByTitle('toggle-sidebars', {exact: false}));

		expect(mockDispatch).toBeCalledWith(
			expect.objectContaining({hidden: false, type: SWITCH_SIDEBAR_PANEL})
		);
	});
});
