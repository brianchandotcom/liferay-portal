/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {SWITCH_SIDEBAR_PANEL} from '../../../../src/main/resources/META-INF/resources/page_editor/app/actions/types';
import HideSidebarButton from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/HideSidebarButton';
import StoreMother from '../../../../src/main/resources/META-INF/resources/page_editor/test_utils/StoreMother';

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

		userEvent.click(
			screen.getByLabelText('toggle-sidebars', {exact: false})
		);

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

		userEvent.click(
			screen.getByLabelText('toggle-sidebars', {exact: false})
		);

		expect(mockDispatch).toBeCalledWith(
			expect.objectContaining({hidden: false, type: SWITCH_SIDEBAR_PANEL})
		);
	});
});
