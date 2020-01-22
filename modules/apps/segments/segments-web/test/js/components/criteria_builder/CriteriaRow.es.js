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

import {cleanup, render, wait} from '@testing-library/react';
import React from 'react';

import CriteriaRow from '../../../../src/main/resources/META-INF/resources/js/components/criteria_builder/CriteriaRow.es';
import {PROPERTY_TYPES} from '../../../../src/main/resources/META-INF/resources/js/utils/constants.es';

import '@testing-library/jest-dom/extend-expect';

const connectDnd = jest.fn(el => el);

describe('CriteriaRow', () => {
	afterEach(cleanup);

	it('renders', () => {
		const OriginalCriteriaRow = CriteriaRow.DecoratedComponent;

		const {asFragment} = render(
			<OriginalCriteriaRow
				connectDragPreview={connectDnd}
				connectDragSource={connectDnd}
				connectDropTarget={connectDnd}
				criterion={{
					operatorName: PROPERTY_TYPES.STRING,
					propertyName: 'test_prop',
					value: 'test_val'
				}}
				groupId="group_01"
				index={0}
				onAdd={jest.fn()}
				onChange={jest.fn()}
				onDelete={jest.fn()}
				onMove={jest.fn()}
				propertyKey="user"
				supportedProperties={[
					{
						label: 'Test Property',
						name: 'test_prop',
						type: PROPERTY_TYPES.STRING
					}
				]}
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders and inform there is an unknown property', () => {
		const OriginalCriteriaRow = CriteriaRow.DecoratedComponent;

		const {asFragment} = render(
			<OriginalCriteriaRow
				connectDragPreview={connectDnd}
				connectDragSource={connectDnd}
				connectDropTarget={connectDnd}
				criterion={{
					operatorName: PROPERTY_TYPES.STRING,
					propertyName: 'unknown_prop',
					value: 'test_val'
				}}
				groupId="group_01"
				index={0}
				onAdd={jest.fn()}
				onChange={jest.fn()}
				onDelete={jest.fn()}
				onMove={jest.fn()}
				propertyKey="user"
				supportedProperties={[
					{
						label: 'Test Property',
						name: 'test_prop',
						type: PROPERTY_TYPES.STRING
					}
				]}
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('informs user of an unknown entity in edit mode', async () => {
		const OriginalCriteriaRow = CriteriaRow.DecoratedComponent;

		global.fetch = jest.fn(() =>
			Promise.resolve({
				json: () => Promise.resolve({})
			})
		);

		const {getByDisplayValue, getByText} = render(
			<OriginalCriteriaRow
				connectDragPreview={connectDnd}
				connectDragSource={connectDnd}
				connectDropTarget={connectDnd}
				criterion={{
					operatorName: PROPERTY_TYPES.ID,
					propertyName: 'test_prop',
					unknownEntity: true,
					value: '1234'
				}}
				groupId="group_01"
				index={0}
				onAdd={jest.fn()}
				onChange={jest.fn()}
				onDelete={jest.fn()}
				onMove={jest.fn()}
				propertyKey="user"
				supportedProperties={[
					{
						label: 'Test Property',
						name: 'test_prop',
						type: PROPERTY_TYPES.ID
					}
				]}
			/>
		);

		await wait(() =>
			expect(
				getByText('unknown-element-message-edit')
			).toBeInTheDocument()
		);

		expect(getByDisplayValue('1234')).toBeInTheDocument();
	});

	it('informs user of an unknown entity in view mode', async () => {
		const OriginalCriteriaRow = CriteriaRow.DecoratedComponent;

		global.fetch = jest.fn(() =>
			Promise.resolve({
				json: () => Promise.resolve({})
			})
		);

		const {getByText} = render(
			<OriginalCriteriaRow
				connectDragPreview={connectDnd}
				connectDragSource={connectDnd}
				connectDropTarget={connectDnd}
				criterion={{
					operatorName: PROPERTY_TYPES.ID,
					propertyName: 'test_prop',
					unknownEntity: true,
					value: '1234'
				}}
				editing={false}
				groupId="group_01"
				index={0}
				onAdd={jest.fn()}
				onChange={jest.fn()}
				onDelete={jest.fn()}
				onMove={jest.fn()}
				propertyKey="user"
				supportedProperties={[
					{
						label: 'Test Property',
						name: 'test_prop',
						type: PROPERTY_TYPES.ID
					}
				]}
			/>
		);

		await wait(() =>
			expect(
				getByText('unknown-element-message-view')
			).toBeInTheDocument()
		);

		expect(getByText('1234')).toBeInTheDocument();
	});

	it('reports change when it finds the name of an entity', async () => {
		const OriginalCriteriaRow = CriteriaRow.DecoratedComponent;

		global.fetch = jest.fn(() =>
			Promise.resolve({
				json: () =>
					Promise.resolve({
						fieldValueName: 'Known Entity'
					})
			})
		);

		const onChangeMock = jest.fn(() => {});

		render(
			<OriginalCriteriaRow
				connectDragPreview={connectDnd}
				connectDragSource={connectDnd}
				connectDropTarget={connectDnd}
				criterion={{
					operatorName: PROPERTY_TYPES.ID,
					propertyName: 'test_prop',
					value: '1234'
				}}
				editing={false}
				groupId="group_01"
				index={0}
				onAdd={jest.fn()}
				onChange={onChangeMock}
				onDelete={jest.fn()}
				onMove={jest.fn()}
				propertyKey="user"
				supportedProperties={[
					{
						label: 'Test Property',
						name: 'test_prop',
						type: PROPERTY_TYPES.ID
					}
				]}
			/>
		);

		await wait(() => expect(onChangeMock).toHaveBeenCalled());

		expect(onChangeMock).toHaveBeenCalledWith(
			expect.objectContaining({
				displayValue: 'Known Entity'
			})
		);
	});

	it('reports change when it cannot find an entity name', async () => {
		const OriginalCriteriaRow = CriteriaRow.DecoratedComponent;

		global.fetch = jest.fn(() =>
			Promise.resolve({
				json: () => Promise.resolve({})
			})
		);

		const onChangeMock = jest.fn(() => {});

		render(
			<OriginalCriteriaRow
				connectDragPreview={connectDnd}
				connectDragSource={connectDnd}
				connectDropTarget={connectDnd}
				criterion={{
					operatorName: PROPERTY_TYPES.ID,
					propertyName: 'test_prop',
					value: '1234'
				}}
				editing={false}
				groupId="group_01"
				index={0}
				onAdd={jest.fn()}
				onChange={onChangeMock}
				onDelete={jest.fn()}
				onMove={jest.fn()}
				propertyKey="user"
				supportedProperties={[
					{
						label: 'Test Property',
						name: 'test_prop',
						type: PROPERTY_TYPES.ID
					}
				]}
			/>
		);

		await wait(() => expect(onChangeMock).toHaveBeenCalled());

		expect(onChangeMock).toHaveBeenCalledWith(
			expect.objectContaining({
				displayValue: '1234',
				unknownEntity: true
			})
		);
	});
});
