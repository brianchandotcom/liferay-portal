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

import '@testing-library/jest-dom/extend-expect';
import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import {
	useHoverItem,
	useIsActive,
} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/Controls';
import FloatingToolbar from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/floating-toolbar/FloatingToolbar';
import {StoreAPIContextProvider} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/store';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/components/Controls',
	() => ({
		useHoverItem: jest.fn(() => {}),
		useIsActive: jest.fn(() => () => true),
	})
);

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/floatingToolbarConfigurations',
	() => ({
		FLOATING_TOOLBAR_CONFIGURATIONS: {
			second_button_panel: () => <div role="panel" />,
		},
	})
);

const buttons = [
	{
		icon: 'icon',
		id: 'first_button',
		panelId: 'first_button_panel',
		title: 'first button',
	},
	{
		icon: 'icon',
		id: 'second_button',
		panelId: 'second_button_panel',
		title: 'second button',
	},
];
const item = {
	children: [],
	config: {fragmentEntryLinkId: '1'},
	itemId: 'id_1',
	parentId: 'parent_id_1',
	type: 'fragment',
};

const renderFloatingToolbar = ({onButtonClick = () => {}}) => {
	const itemRef = React.createRef();

	return render(
		<StoreAPIContextProvider>
			<div className="page-editor__sidebar__content"></div>
			<FloatingToolbar
				buttons={buttons}
				item={item}
				itemRef={itemRef}
				onButtonClick={onButtonClick}
			/>
		</StoreAPIContextProvider>
	);
};

describe('FloatingToolbar', () => {
	beforeEach(() => {
		cleanup();
		useHoverItem.mockClear();
		useIsActive.mockClear();
	});

	it('renders FloatingToolbar component', () => {
		const {getByTitle} = renderFloatingToolbar({});

		expect(getByTitle('first button')).toBeInTheDocument();
		expect(getByTitle('second button')).toBeInTheDocument();
	});

	it('calls handleButtonClick when first button is clicked', () => {
		const onButtonClick = jest.fn();
		const {getByTitle} = renderFloatingToolbar({onButtonClick});
		const button = getByTitle('first button');

		fireEvent.click(button);

		expect(onButtonClick).toHaveBeenCalled();
	});

	it('opens the panel when the second button is clicked', () => {
		const {getByRole, getByTitle} = renderFloatingToolbar({});
		const button = getByTitle('second button');

		fireEvent.click(button);

		expect(getByRole('panel')).toBeInTheDocument();
	});
});
