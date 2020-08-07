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
import userEvent from '@testing-library/user-event';
import React from 'react';

import {StoreAPIContextProvider} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/store/index';
import updateItemConfig from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig';
import {ContainerStylesPanel} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/page-structure/components/item-configuration-panels/ContainerStylesPanel';

const renderComponent = ({itemConfig = {}, dispatch = () => {}} = {}) =>
	render(
		<StoreAPIContextProvider
			dispatch={dispatch}
			getState={() => ({segmentsExperienceId: 0})}
		>
			<ContainerStylesPanel
				item={{
					children: [],
					config: itemConfig,
					itemId: '0',
					parentId: '',
					type: '',
				}}
			/>
		</StoreAPIContextProvider>
	);

jest.mock(
	'../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig',
	() => jest.fn()
);

jest.mock(
	'../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			defaultSegmentsExperienceId: 0,
			marginOptions: [],
			paddingOptions: [],
			themeColorsCssClasses: ['danger'],
		},
	})
);

describe('ContainerStyles', () => {
	afterEach(() => {
		cleanup();
		updateItemConfig.mockClear();
	});

	it('renders correctly', () => {
		const {getByLabelText} = renderComponent();

		expect(getByLabelText('container-width')).toBeInTheDocument();
	});

	it('calls dispatch method when changing the container width', async () => {
		const {getByLabelText} = renderComponent();

		const containerWidthSelect = getByLabelText('container-width');

		await fireEvent.change(containerWidthSelect, {
			target: {value: 'fixed'},
		});

		expect(updateItemConfig).toBeCalledWith(
			expect.objectContaining({
				itemConfig: {
					marginLeft: '',
					marginRight: '',
					widthType: 'fixed',
				},
			})
		);
	});

	it('calls dispatch method when changing the color', () => {
		renderComponent();

		const colorButton = document.body.querySelector('.bg-danger');

		userEvent.click(colorButton);

		expect(updateItemConfig).toBeCalledWith(
			expect.objectContaining({
				itemConfig: {
					backgroundColorCssClass: 'danger',
				},
			})
		);
	});

	it('disables left and right margin and set the content to auto when selecting fixed width', async () => {
		const {queryAllByText} = renderComponent({
			itemConfig: {widthType: 'fixed'},
		});

		const options = queryAllByText('auto');

		expect(options.length).toBe(2);

		options.forEach((select) => {
			expect(select.parentElement.disabled).toBe(true);
		});
	});
});
