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

import {CONTAINER_DISPLAY_OPTIONS} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/containerDisplayOptions';
import {VIEWPORT_SIZES} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/viewportSizes';
import {StoreAPIContextProvider} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import updateItemConfig from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig';
import {ContainerStylesPanel} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/browser/components/page-structure/components/item-configuration-panels/ContainerStylesPanel';

const renderComponent = ({
	dispatch = () => {},
	itemConfig = {
		tablet: {styles: {}},
	},
	selectedViewportSize = VIEWPORT_SIZES.desktop,
} = {}) =>
	render(
		<StoreAPIContextProvider
			dispatch={dispatch}
			getState={() => ({
				segmentsExperienceId: '0',
				selectedViewportSize,
			})}
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
			availableViewportSizes: {
				desktop: {label: 'Desktop', sizeId: 'desktop'},
				mobile: {label: 'Mobile', sizeId: 'mobile'},
				tablet: {label: 'Tablet', sizeId: 'tablet'},
			},
			commonStyles: [],
			defaultSegmentsExperienceId: '0',
			marginOptions: [],
			paddingOptions: [],
		},
	})
);

describe('ContainerStylesPanel', () => {
	afterEach(() => {
		cleanup();
		updateItemConfig.mockClear();
	});

	it('renders correctly', () => {
		const {getByLabelText} = renderComponent();

		expect(getByLabelText('container-width')).toBeInTheDocument();
	});

	it('shows custom styles panel', async () => {
		const {getByText} = renderComponent({});

		expect(getByText('custom-styles')).toBeInTheDocument();
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
					widthType: 'fixed',
				},
			})
		);
	});

	it('calls dispatch method when changing the content display', async () => {
		const {getByLabelText} = renderComponent();

		const contentDisplaySelect = getByLabelText('content-display');

		await fireEvent.change(contentDisplaySelect, {
			target: {value: 'flex-row'},
		});

		expect(updateItemConfig).toBeCalledWith(
			expect.objectContaining({
				itemConfig: {
					contentDisplay: 'flex-row',
				},
			})
		);
	});

	it('shows Align and Justify selects when item is flex container', async () => {
		const {getByLabelText} = renderComponent({
			itemConfig: {contentDisplay: CONTAINER_DISPLAY_OPTIONS.flexRow},
		});

		expect(getByLabelText('align-items')).toBeInTheDocument();
		expect(getByLabelText('justify-content')).toBeInTheDocument();
	});

	it('sets correct default values for Align and Justify when flex is selected', async () => {
		const {getByLabelText} = renderComponent({
			itemConfig: {contentDisplay: CONTAINER_DISPLAY_OPTIONS.flexRow},
		});

		const alignInput = getByLabelText('align-items');
		const justifyInput = getByLabelText('justify-content');

		expect(alignInput.value).toBe('align-items-stretch');
		expect(justifyInput.value).toBe('justify-content-start');
	});
});
