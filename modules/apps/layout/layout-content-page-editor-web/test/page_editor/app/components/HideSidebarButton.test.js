/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import HideSidebarButton from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/HideSidebarButton';
import useOnToggleSidebars from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/useOnToggleSidebars';
import StoreMother from '../../../../src/main/resources/META-INF/resources/page_editor/test_utils/StoreMother';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/components/useOnToggleSidebars',
	() => {
		const onToggleSidebars = jest.fn();

		return () => onToggleSidebars;
	}
);

const INITIAL_STATE = {
	sidebar: {},
};

const renderComponent = () =>
	render(
		<StoreMother.Component getState={() => INITIAL_STATE}>
			<HideSidebarButton />
		</StoreMother.Component>
	);

describe('HideSidebarButton', () => {
	it('calls onToggleSidebars when the Toggle Sidebars button is pressed', () => {
		renderComponent();

		const onToggleSidebars = useOnToggleSidebars();

		userEvent.click(
			screen.getByLabelText('toggle-sidebars', {exact: false})
		);

		expect(onToggleSidebars).toBeCalled();
	});
});
