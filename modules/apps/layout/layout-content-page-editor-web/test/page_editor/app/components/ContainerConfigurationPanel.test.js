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

import {ContainerConfigurationPanel} from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/floating-toolbar/ContainerConfigurationPanel';
import {CONTAINER_TYPES} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/containerTypes';
import {config} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/index';
import {StoreAPIContextProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/app/store';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			paddingOptions: [
				{
					label: '0',
					value: '0',
				},
				{
					label: '1',
					value: '3',
				},
				{
					label: '2',
					value: '4',
				},
				{
					label: '4',
					value: '5',
				},
				{
					label: '6',
					value: '6',
				},
				{
					label: '8',
					value: '7',
				},
				{
					label: '10',
					value: '8',
				},
			],
			themeColorsCssClasses: [],
		},
	})
);

const renderComponent = (dispatch = () => {}) =>
	render(
		<StoreAPIContextProvider dispatch={dispatch}>
			<ContainerConfigurationPanel
				item={{
					children: [],
					config: {type: CONTAINER_TYPES.fluid},
					itemId: '',
					parentId: '',
					type: '',
				}}
			/>
		</StoreAPIContextProvider>
	);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig',
	() => (...args) => args
);

describe('ContainerConfigurationPanel', () => {
	afterEach(cleanup);

	['top', 'bottom', 'horizontal'].forEach(paddingDirection => {
		const paddingLabel = `padding-${paddingDirection}`;
		const paddingPropName = `padding${paddingDirection
			.charAt(0)
			.toUpperCase()}${paddingDirection.slice(1)}`;

		describe(`${paddingDirection} configuration input`, () => {
			it('shows a select input', () => {
				const {getByLabelText} = renderComponent();
				const input = getByLabelText(paddingLabel);

				expect(input).toBeInTheDocument();
				expect(input).toBeInstanceOf(HTMLSelectElement);
			});

			config.paddingOptions.forEach(({value}) => {
				it(`updates configuration when select input is changed to ${value}`, async () => {
					const dispatch = jest.fn();
					const {getByLabelText} = renderComponent(dispatch);
					const input = getByLabelText(paddingLabel);

					await fireEvent.change(input, {
						target: {value},
					});

					expect(dispatch).toHaveBeenCalledWith([
						expect.objectContaining({
							itemConfig: {[paddingPropName]: Number(value)},
						}),
					]);
				});
			});
		});
	});
});
