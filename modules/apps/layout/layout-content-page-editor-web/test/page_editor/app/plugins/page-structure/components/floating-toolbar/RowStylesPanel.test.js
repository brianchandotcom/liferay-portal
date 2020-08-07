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

import {ResizeContextProvider} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/components/ResizeContext';
import {config} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/index';
import {StoreAPIContextProvider} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/store/index';
import updateItemConfig from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig';
import updateRowColumns from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateRowColumns';
import {RowStylesPanel} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/page-structure/components/floating-toolbar/RowStylesPanel';

const ITEM_CONFIG = {
	gutters: true,
	modulesPerRow: 2,
	numberOfColumns: 2,
	verticalAlignment: 'top',
};

const RESIZE_CONTEXT_STATE = {
	customRow: false,
	resizing: false,
	setCustomRow: () => null,
	setResizing: () => null,
	setUpdatedLayoutData: () => null,
	updatedLayoutData: null,
};

const STATE = {
	segmentsExperienceId: '0',
	selectedViewportSize: 'desktop',
};

const renderComponent = ({
	config = ITEM_CONFIG,
	state = STATE,
	contextState = RESIZE_CONTEXT_STATE,
	dispatch = () => {},
}) =>
	render(
		<StoreAPIContextProvider
			dispatch={dispatch}
			getState={() => ({...STATE, ...state})}
		>
			<ResizeContextProvider
				value={{...RESIZE_CONTEXT_STATE, ...contextState}}
			>
				<RowStylesPanel
					item={{
						children: [],
						config: {...ITEM_CONFIG, ...config},
						itemId: '0',
						parentId: '',
						type: '',
					}}
				/>
			</ResizeContextProvider>
		</StoreAPIContextProvider>
	);

jest.mock(
	'../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			availableViewportSizes: {
				desktop: {label: 'Desktop'},
				landscapeMobile: {label: 'landscapeMobile'},
			},
			responsiveEnabled: true,
		},
	})
);

jest.mock(
	'../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig',
	() => jest.fn()
);

jest.mock(
	'../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateRowColumns',
	() => jest.fn()
);

describe('RowStylesPanel', () => {
	afterEach(() => {
		cleanup();
		config.responsiveEnabled = true;
		updateItemConfig.mockClear();
		updateRowColumns.mockClear();
	});

	it('allows changing the modules per row', async () => {
		const {getByLabelText} = renderComponent({});
		const input = getByLabelText('layout');

		await fireEvent.change(input, {
			target: {value: '2'},
		});

		expect(updateItemConfig).toHaveBeenCalledWith({
			itemConfig: {
				modulesPerRow: 2,
			},
			itemId: '0',
			segmentsExperienceId: '0',
		});
	});

	it('allows custom value in modules per row when row is customized', async () => {
		const {getByLabelText} = renderComponent({
			contextState: {customRow: true},
		});
		const input = getByLabelText('layout');

		await fireEvent.change(input, {
			target: {value: '0'},
		});

		expect(updateItemConfig).toHaveBeenCalledWith({
			itemConfig: {
				modulesPerRow: 'custom',
			},
			itemId: '0',
			segmentsExperienceId: '0',
		});
	});

	it('allows changing the vertical alignment', async () => {
		const {getByLabelText} = renderComponent({});
		const input = getByLabelText('vertical-alignment');

		await fireEvent.change(input, {
			target: {value: 'middle'},
		});

		expect(updateItemConfig).toHaveBeenCalledWith({
			itemConfig: {
				verticalAlignment: 'middle',
			},
			itemId: '0',
			segmentsExperienceId: '0',
		});
	});

	it('allows inverse order when number of modules is 2 and modules per row is 1', async () => {
		const {getByLabelText} = renderComponent({
			config: {
				modulesPerRow: 1,
			},
		});
		const input = getByLabelText('inverse-order');

		await fireEvent.click(input);

		expect(updateItemConfig).toHaveBeenCalledWith({
			itemConfig: {
				reverseOrder: true,
			},
			itemId: '0',
			segmentsExperienceId: '0',
		});
	});

	it('allows changing configuration for a given viewport', async () => {
		const {getByLabelText} = renderComponent({
			state: {
				selectedViewportSize: 'landscapeMobile',
			},
		});
		const input = getByLabelText('layout');

		await fireEvent.change(input, {
			target: {value: '1'},
		});

		expect(updateItemConfig).toHaveBeenCalledWith({
			itemConfig: {
				landscapeMobile: {modulesPerRow: 1},
			},
			itemId: '0',
			segmentsExperienceId: '0',
		});
	});
});
