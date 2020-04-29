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
import {
	act,
	cleanup,
	fireEvent,
	getByLabelText,
	render,
} from '@testing-library/react';
import React from 'react';

import {ContainerConfigurationPanel} from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/floating-toolbar/ContainerConfigurationPanel';
import {CONTAINER_TYPES} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/containerTypes';
import {config} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/index';
import {StoreAPIContextProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/app/store';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/services/InfoItemService',
	() => ({
		getAvailableAssetMappingFields: jest.fn(() =>
			Promise.resolve([
				{key: 'unmapped', label: 'unmapped'},
				{key: 'text-field-1', label: 'Text Field 1', type: 'text'},
			])
		),
	})
);

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

const getComponent = ({dispatch = () => {}, config = {}} = {}) => (
	<StoreAPIContextProvider
		dispatch={dispatch}
		getState={() => ({mappedInfoItems: []})}
	>
		<ContainerConfigurationPanel
			item={{
				children: [],
				config: {...config, type: CONTAINER_TYPES.fluid},
				itemId: '',
				parentId: '',
				type: '',
			}}
		/>
	</StoreAPIContextProvider>
);

const renderComponent = ({dispatch = () => {}, config = {}} = {}) =>
	render(getComponent({config, dispatch}), {baseElement: document.body});

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig',
	() => (...args) => args
);

describe('ContainerConfigurationPanel', () => {
	afterEach(cleanup);

	['top', 'bottom', 'horizontal'].forEach((paddingDirection) => {
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
					const {getByLabelText} = renderComponent({dispatch});
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

	describe('Image selector', () => {
		it('shows the manual source by default', () => {
			const {getByLabelText} = renderComponent();

			const imageSourceSelect = getByLabelText('image-source');

			expect(imageSourceSelect.value).toBe('manual_selection');
		});

		it('shows the manual source with manual url is set', () => {
			const {getByLabelText} = renderComponent({
				config: {
					backgroundImage: {
						title: 'image',
						url: 'http://someurl.com',
					},
				},
			});

			// debug();

			expect(getByLabelText('image-source').value).toBe(
				'manual_selection'
			);
			expect(getByLabelText('background-image').value).toBe('image');
		});

		it('shows the content mapping source when mapping is set', async () => {
			await act(async () => {
				renderComponent({
					config: {
						backgroundImage: {
							classNameId: '1',
							classPK: '2',
							fieldId: 'field',
						},
					},
				});
			});

			expect(getByLabelText(document.body, 'image-source').value).toBe(
				'content_mapping'
			);
		});

		it('does not change to manual source when selecting a content', async () => {
			const {getByLabelText, rerender} = renderComponent();

			const imageSourceSelect = getByLabelText('image-source');

			await act(async () => {
				fireEvent.change(imageSourceSelect, {
					target: {value: 'content_mapping'},
				});
			});

			await act(async () => {
				rerender(
					getComponent({
						config: {
							backgroundImage: {
								classNameId: '',
								classPK: '',
								fieldId: '',
							},
						},
					})
				);
			});

			expect(getByLabelText('image-source').value).toBe(
				'content_mapping'
			);
		});
	});
});
