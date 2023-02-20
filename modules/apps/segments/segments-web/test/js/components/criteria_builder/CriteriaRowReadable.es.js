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

const equalsOperator = {label: 'Equals', name: 'eq'};

const stringCriterion = {
	operatorName: 'eq',
	propertyName: 'firstName',
	value: 'string value',
};

const stringProperty = {
	label: 'First Name',
	name: 'firstName',
	options: [],
	type: 'string',
};

const booleanCriterion = {
	operatorName: 'eq',
	propertyName: 'signedIn',
	value: 'true',
};

const booleanProperty = {
	label: 'Signed In',
	name: 'signedIn',
	options: [],
	type: 'boolean',
};

const dateCriterion = {
	operatorName: 'eq',
	propertyName: 'birthDate',
	value: '2023-09-22',
};

const dateProperty = {
	label: 'Date of Birth',
	name: 'birthDate',
	options: [],
	type: 'date',
};

const entityCriterion = {
	displayValue: 'Liferay DXP',
	operatorName: 'eq',
	propertyName: 'groupIds',
	unknownEntity: false,
	value: '20121',
};

const entityProperty = {
	label: 'Site',
	name: 'groupIds',
	options: [],
	selectEntity: {
		id: 'selectEntity',
		multiple: false,
		title: 'Select Site',
		uri: 'http://localhost:8080/test',
	},
	type: 'id',
};

const collectionCriterion = {
	operatorName: 'eq',
	propertyName: 'cookies',
	value: 'key_name=key_value',
};

const collectionProperty = {
	label: 'Cookies',
	name: 'cookies',
	options: [],
	type: 'collection',
};

const doubleCriterion = {
	operatorName: 'eq',
	propertyName: 'deviceScreenResolutionHeight',
	value: '700.00',
};

const doubleProperty = {
	label: 'Device Screen Resolution Height',
	name: 'deviceScreenResolutionHeight',
	options: [],
	type: 'double',
};

const eventCriterion = {
	assetId: '545188693724480037',
	day: {
		operator: 'gt',
		value: '2023-02-07',
	},
	operatorName: 'ge',
	operatorNot: true,
	propertyName: 'downloadedDocuments',
	value: 1,
};

const eventCriterionAtMost = {
	assetId: '545188693724480037',
	day: {
		operator: 'gt',
		value: '2023-02-07',
	},
	operatorName: 'le',
	operatorNot: true,
	propertyName: 'downloadedDocuments',
	value: 1,
};

const eventCriterionBetween = {
	assetId: '545188693724480037',
	day: {
		operatorName: 'between',
		value: {
			end: '2024-01-26T23:00:00.000Z',
			start: '2023-01-26T23:00:00.000Z',
		},
	},
	operatorName: 'gt',
	operatorNot: true,
	propertyName: 'downloadedDocuments',
	value: 1,
};

const eventCriterionEver = {
	assetId: '545188693724480037',
	operatorName: 'gt',
	operatorNot: true,
	propertyName: 'downloadedDocuments',
	value: 1,
};

const eventCriterionSince = {
	assetId: '545188693724480037',
	day: {
		operatorName: 'gt',
		value: 'last28Days',
	},
	operatorName: 'gt',
	operatorNot: true,
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
		expect(getByText('at-least')).toBeInTheDocument();
		expect(getByText(eventCriterion.value)).toBeInTheDocument();
	});

	it('renders event criterion with at most operator', () => {
		const {getByText} = render(
			<CriteriaRowReadable
				criterion={eventCriterionAtMost}
				selectedProperty={eventProperty}
			/>
		);

		expect(getByText(eventProperty.label)).toBeInTheDocument();
		expect(getByText('at-most')).toBeInTheDocument();
		expect(getByText(eventCriterion.value)).toBeInTheDocument();
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
