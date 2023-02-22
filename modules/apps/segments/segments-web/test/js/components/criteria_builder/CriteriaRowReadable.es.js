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

import {render} from '@testing-library/react';
import React from 'react';

import CriteriaRowReadable from '../../../../src/main/resources/META-INF/resources/js/components/criteria_builder/CriteriaRowReadable.es';
import {dateToInternationalHuman} from '../../../../src/main/resources/META-INF/resources/js/utils/utils.es';

import '@testing-library/jest-dom/extend-expect';

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

const eventCriterion = {
	assetId: '545188693724480037',
	day: {
		operatorName: 'gt',
		value: '2023-02-07',
	},
	operatorName: 'ge',
	propertyName: 'downloadedDocuments',
	value: 1,
};

const eventCriterionEver = {
	assetId: '545188693724480037',
	operatorName: 'gt',
	propertyName: 'downloadedDocuments',
	value: 1,
};

const eventProperty = {
	label: 'Downloaded Documents and Media',
	name: 'downloadedDocuments',
	options: [],
	type: 'event',
};

describe('CriteriaRowReadable', () => {
	it('renders string criterion', () => {
		const {getByText} = render(
			<CriteriaRowReadable
				criterion={stringCriterion}
				selectedOperator={equalsOperator}
				selectedProperty={stringProperty}
			/>
		);

		expect(getByText(stringProperty.label)).toBeInTheDocument();
		expect(getByText(equalsOperator.label)).toBeInTheDocument();
		expect(getByText(stringCriterion.value)).toBeInTheDocument();
	});

	it('renders boolean criterion', () => {
		const {getByText} = render(
			<CriteriaRowReadable
				criterion={booleanCriterion}
				selectedOperator={equalsOperator}
				selectedProperty={booleanProperty}
			/>
		);

		expect(getByText(booleanProperty.label)).toBeInTheDocument();
		expect(getByText(equalsOperator.label)).toBeInTheDocument();
		expect(getByText(booleanCriterion.value)).toBeInTheDocument();
	});

	it('renders date criterion', () => {
		const {getByText} = render(
			<CriteriaRowReadable
				criterion={dateCriterion}
				selectedOperator={equalsOperator}
				selectedProperty={dateProperty}
			/>
		);

		expect(getByText(dateProperty.label)).toBeInTheDocument();
		expect(getByText(equalsOperator.label)).toBeInTheDocument();
		expect(
			getByText(dateToInternationalHuman(dateCriterion.value))
		).toBeInTheDocument();
	});

	it('renders entity criterion', () => {
		const {getByText} = render(
			<CriteriaRowReadable
				criterion={entityCriterion}
				selectedOperator={equalsOperator}
				selectedProperty={entityProperty}
			/>
		);

		expect(getByText(entityProperty.label)).toBeInTheDocument();
		expect(getByText(equalsOperator.label)).toBeInTheDocument();
		expect(getByText(entityCriterion.displayValue)).toBeInTheDocument();
	});

	it('renders collection criterion', () => {
		const {getByText} = render(
			<CriteriaRowReadable
				criterion={collectionCriterion}
				selectedOperator={equalsOperator}
				selectedProperty={collectionProperty}
			/>
		);

		expect(getByText(collectionProperty.label)).toBeInTheDocument();
		expect(getByText(equalsOperator.label)).toBeInTheDocument();
		expect(getByText(collectionCriterion.value)).toBeInTheDocument();
	});

	it('renders double criterion', () => {
		const {getByText} = render(
			<CriteriaRowReadable
				criterion={doubleCriterion}
				selectedOperator={equalsOperator}
				selectedProperty={doubleProperty}
			/>
		);

		expect(getByText(doubleProperty.label)).toBeInTheDocument();
		expect(getByText(equalsOperator.label)).toBeInTheDocument();
		expect(getByText(doubleCriterion.value)).toBeInTheDocument();
	});

	it('renders event criterion', () => {
		const {getByText} = render(
			<CriteriaRowReadable
				criterion={eventCriterion}
				selectedProperty={eventProperty}
			/>
		);

		expect(getByText(eventProperty.label)).toBeInTheDocument();
		expect(getByText('has')).toBeInTheDocument();
		expect(getByText('at-least')).toBeInTheDocument();
		expect(getByText(eventCriterion.value)).toBeInTheDocument();
	});

	it('renders event criterion with not operator', () => {
		const eventCriterionNotOperator = {
			...eventCriterion,
			...{operatorNot: true},
		};
		const {getByText} = render(
			<CriteriaRowReadable
				criterion={eventCriterionNotOperator}
				selectedProperty={eventProperty}
			/>
		);

		expect(getByText(eventProperty.label)).toBeInTheDocument();
		expect(getByText('has-not')).toBeInTheDocument();
		expect(getByText('at-least')).toBeInTheDocument();
		expect(getByText(eventCriterionNotOperator.value)).toBeInTheDocument();
	});

	it('renders event criterion with at most operator', () => {
		const eventCriterionAtMost = {
			...eventCriterion,
			...{operatorName: 'le'},
		};
		const {getByText} = render(
			<CriteriaRowReadable
				criterion={eventCriterionAtMost}
				selectedProperty={eventProperty}
			/>
		);

		expect(getByText(eventProperty.label)).toBeInTheDocument();
		expect(getByText('at-most')).toBeInTheDocument();
		expect(getByText(eventCriterionAtMost.value)).toBeInTheDocument();
	});

	it('renders event criterion with on date modifier', () => {
		const eventCriterionOn = {
			...eventCriterion,
			...{
				day: {
					operatorName: 'eq',
					value: '2023-02-07',
				},
			},
		};
		const {getByText} = render(
			<CriteriaRowReadable
				criterion={eventCriterionOn}
				selectedProperty={eventProperty}
			/>
		);

		expect(getByText(eventProperty.label)).toBeInTheDocument();
		expect(getByText('on')).toBeInTheDocument();
		expect(
			getByText(dateToInternationalHuman(eventCriterionOn.day.value), {
				exact: false,
			})
		).toBeInTheDocument();
	});

	it('renders event criterion with after date modifier', () => {
		const {getByText} = render(
			<CriteriaRowReadable
				criterion={eventCriterion}
				selectedProperty={eventProperty}
			/>
		);

		expect(getByText(eventProperty.label)).toBeInTheDocument();
		expect(getByText('after')).toBeInTheDocument();
		expect(
			getByText(dateToInternationalHuman(eventCriterion.day.value), {
				exact: false,
			})
		).toBeInTheDocument();
	});

	it('renders event criterion with before date modifier', () => {
		const eventCriterionBefore = {
			...eventCriterion,
			...{
				day: {
					operatorName: 'lt',
					value: '2023-02-07',
				},
			},
		};
		const {getByText} = render(
			<CriteriaRowReadable
				criterion={eventCriterionBefore}
				selectedProperty={eventProperty}
			/>
		);

		expect(getByText(eventProperty.label)).toBeInTheDocument();
		expect(getByText('before')).toBeInTheDocument();
		expect(
			getByText(
				dateToInternationalHuman(eventCriterionBefore.day.value),
				{
					exact: false,
				}
			)
		).toBeInTheDocument();
	});

	it('renders event criterion with ever date modifier', () => {
		const {getByText} = render(
			<CriteriaRowReadable
				criterion={eventCriterionEver}
				selectedProperty={eventProperty}
			/>
		);

		expect(getByText(eventProperty.label)).toBeInTheDocument();
		expect(getByText(eventCriterionEver.value)).toBeInTheDocument();
		expect(getByText('ever')).toBeInTheDocument();
	});

	it('renders event criterion with between date modifier', () => {
		const eventCriterionBetween = {
			...eventCriterion,
			...{
				day: {
					operatorName: 'between',
					value: {
						end: '2024-01-26T23:00:00.000Z',
						start: '2023-01-26T23:00:00.000Z',
					},
				},
			},
		};

		const {getByText} = render(
			<CriteriaRowReadable
				criterion={eventCriterionBetween}
				selectedProperty={eventProperty}
			/>
		);

		expect(getByText(eventProperty.label)).toBeInTheDocument();
		expect(getByText('between')).toBeInTheDocument();
		expect(
			getByText(
				dateToInternationalHuman(eventCriterionBetween.day.value.start),
				{
					exact: false,
				}
			)
		).toBeInTheDocument();
		expect(
			getByText(
				dateToInternationalHuman(eventCriterionBetween.day.value.end),
				{
					exact: false,
				}
			)
		).toBeInTheDocument();
	});

	it('renders event criterion with since date modifier', () => {
		const eventCriterionSince = {
			...eventCriterion,
			...{
				day: {
					operatorName: 'gt',
					value: 'last28Days',
				},
			},
		};

		const {getByText} = render(
			<CriteriaRowReadable
				criterion={eventCriterionSince}
				selectedProperty={eventProperty}
			/>
		);

		expect(getByText(eventProperty.label)).toBeInTheDocument();
		expect(getByText('since')).toBeInTheDocument();
		expect(getByText(eventCriterionSince.value)).toBeInTheDocument();
	});
});
