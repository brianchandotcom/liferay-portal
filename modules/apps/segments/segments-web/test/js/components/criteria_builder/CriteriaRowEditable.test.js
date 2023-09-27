/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import CriteriaRowEditable from '../../../../src/main/resources/META-INF/resources/js/components/criteria_builder/CriteriaRowEditable';

import '@testing-library/jest-dom/extend-expect';
import {format, parse} from 'date-fns';

import {
	booleanCriterion,
	booleanProperty,
	collectionCriterion,
	collectionProperty,
	dateCriterion,
	dateProperty,
	doubleCriterion,
	doubleProperty,
	entityCriterion,
	entityProperty,
	stringCriterion,
	stringProperty,
} from '../../mockData';

const equalsOperator = {label: 'Equals', name: 'eq'};

const connectDnd = jest.fn((element) => element);

describe('CriteriaRowEditable', () => {
	it('renders string criterion', () => {
		const {getByTestId, getByText} = render(
			<CriteriaRowEditable
				connectDragSource={connectDnd}
				criterion={stringCriterion}
				index={0}
				onAdd={jest.fn()}
				onChange={jest.fn()}
				onDelete={jest.fn()}
				selectedOperator={equalsOperator}
				selectedProperty={stringProperty}
			/>
		);

		expect(getByText(stringProperty.label)).toBeInTheDocument();
		expect(
			getByText(equalsOperator.label.toLowerCase())
		).toBeInTheDocument();

		expect(getByTestId('simple-string').value).toBe(stringCriterion.value);
	});

	it('renders boolean criterion', () => {
		const {getByText} = render(
			<CriteriaRowEditable
				connectDragSource={connectDnd}
				criterion={booleanCriterion}
				index={0}
				onAdd={jest.fn()}
				onChange={jest.fn()}
				onDelete={jest.fn()}
				selectedOperator={equalsOperator}
				selectedProperty={booleanProperty}
			/>
		);

		expect(getByText(booleanProperty.label)).toBeInTheDocument();
		expect(
			getByText(equalsOperator.label.toLowerCase())
		).toBeInTheDocument();
		expect(getByText(booleanCriterion.value)).toBeInTheDocument();
	});

	it('renders date criterion', () => {
		const {getByTestId, getByText} = render(
			<CriteriaRowEditable
				connectDragSource={connectDnd}
				criterion={dateCriterion}
				index={0}
				onAdd={jest.fn()}
				onChange={jest.fn()}
				onDelete={jest.fn()}
				selectedOperator={equalsOperator}
				selectedProperty={dateProperty}
			/>
		);

		expect(getByText(dateProperty.label)).toBeInTheDocument();
		expect(
			getByText(equalsOperator.label.toLowerCase())
		).toBeInTheDocument();

		const dateValue = parse(
			dateCriterion.value,
			'yyyy-MM-dd',
			new Date()
		).toISOString();

		expect(getByTestId('date-input').value).toBe(
			format(new Date(dateValue), 'yyyy/MM/dd')
		);
	});

	it('renders entity criterion', () => {
		const {getByTestId, getByText} = render(
			<CriteriaRowEditable
				connectDragSource={connectDnd}
				criterion={entityCriterion}
				index={0}
				onAdd={jest.fn()}
				onChange={jest.fn()}
				onDelete={jest.fn()}
				selectedOperator={equalsOperator}
				selectedProperty={entityProperty}
			/>
		);

		expect(getByText(entityProperty.label)).toBeInTheDocument();
		expect(
			getByText(equalsOperator.label.toLowerCase())
		).toBeInTheDocument();
		expect(getByTestId('entity-select-input').value).toBe(
			entityCriterion.value
		);
	});

	it('renders collection criterion', () => {
		const {getByTestId, getByText} = render(
			<CriteriaRowEditable
				connectDragSource={connectDnd}
				criterion={collectionCriterion}
				index={0}
				onAdd={jest.fn()}
				onChange={jest.fn()}
				onDelete={jest.fn()}
				selectedOperator={equalsOperator}
				selectedProperty={collectionProperty}
			/>
		);

		expect(getByText(collectionProperty.label)).toBeInTheDocument();
		expect(
			getByText(equalsOperator.label.toLowerCase())
		).toBeInTheDocument();
		expect(getByTestId('collection-value-input').value).toBe(
			collectionCriterion.value.split('=')[1]
		);
	});

	it('renders double criterion', () => {
		const {getByTestId, getByText} = render(
			<CriteriaRowEditable
				connectDragSource={connectDnd}
				criterion={doubleCriterion}
				index={0}
				onAdd={jest.fn()}
				onChange={jest.fn()}
				onDelete={jest.fn()}
				selectedOperator={equalsOperator}
				selectedProperty={doubleProperty}
			/>
		);

		expect(getByText(doubleProperty.label)).toBeInTheDocument();
		expect(
			getByText(equalsOperator.label.toLowerCase())
		).toBeInTheDocument();
		expect(getByTestId('decimal-number').value).toBe(doubleCriterion.value);
	});
});
