/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import ViewportSizeSelector from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/ViewportSizeSelector';

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
