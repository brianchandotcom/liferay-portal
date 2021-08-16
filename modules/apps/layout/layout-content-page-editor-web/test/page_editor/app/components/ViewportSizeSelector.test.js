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
import {cleanup, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import ViewportSizeSelector from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/ViewportSizeSelector';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			availableViewportSizes: {
				desktop: {label: 'Desktop', sizeId: 'desktop'},
				mobile: {label: 'Mobile', sizeId: 'mobile'},
				tablet: {label: 'Tablet', sizeId: 'tablet'},
			},
		},
	})
);

const defaultState = {
	selectedViewportSize: 'desktop',
};

const renderComponent = ({onSelect = () => {}, state}) => {
	return render(
		<ViewportSizeSelector
			onSizeSelected={onSelect}
			selectedSize={state.selectedViewportSize}
		/>
	);
};

describe('ViewportSizeSelector', () => {
	afterEach(cleanup);

	it('renders ViewportSizeSelector component', () => {
		const {getByLabelText} = renderComponent({state: defaultState});

		expect(getByLabelText('Desktop')).toBeInTheDocument();
		expect(getByLabelText('Mobile')).toBeInTheDocument();
		expect(getByLabelText('Tablet')).toBeInTheDocument();
	});

	it('calls onSizeSelected with sizeId when a size is selected', () => {
		const onSelect = jest.fn();
		const {getByLabelText} = renderComponent({
			onSelect,
			state: defaultState,
		});
		const button = getByLabelText('Mobile');

		userEvent.click(button);

		expect(onSelect).toHaveBeenLastCalledWith('mobile');
	});
});
