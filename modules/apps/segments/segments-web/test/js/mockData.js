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

import {
	CONJUNCTIONS,
	RELATIONAL_OPERATORS,
} from '../../src/main/resources/META-INF/resources/js/utils/constants';

const {AND, OR} = CONJUNCTIONS;

function generateItems(
	times,
	operatorName = RELATIONAL_OPERATORS.EQ,
	value = 'test'
) {
	const items = [];

	for (let i = 0; i < times; i++) {
		items.push({
			operatorName,
			propertyName: 'firstName',
			value,
		});
	}

	return items;
}

export function mockCriteria(numOfItems) {
	return {
		conjunctionName: AND,
		groupId: 'group_01',
		items: generateItems(numOfItems),
	};
}

export function mockCriteriaNested() {
	return {
		conjunctionName: AND,
		groupId: 'group_01',
		items: [
			{
				conjunctionName: OR,
				groupId: 'group_02',
				items: [
					{
						conjunctionName: AND,
						groupId: 'group_03',
						items: [
							{
								conjunctionName: OR,
								groupId: 'group_04',
								items: generateItems(2),
							},
							...generateItems(1),
						],
					},
					...generateItems(1),
				],
			},
			...generateItems(1),
		],
	};
}

export const stringCriterion = {
	operatorName: 'eq',
	propertyName: 'firstName',
	value: 'string value',
};

export const stringProperty = {
	label: 'First Name',
	name: 'firstName',
	options: [],
	type: 'string',
};

export const booleanCriterion = {
	operatorName: 'eq',
	propertyName: 'signedIn',
	value: 'true',
};

export const booleanProperty = {
	label: 'Signed In',
	name: 'signedIn',
	options: [],
	type: 'boolean',
};

export const dateCriterion = {
	operatorName: 'eq',
	propertyName: 'birthDate',
	value: '2023-09-22',
};

export const dateProperty = {
	label: 'Date of Birth',
	name: 'birthDate',
	options: [],
	type: 'date',
};

export const entityCriterion = {
	displayValue: 'Liferay DXP',
	operatorName: 'eq',
	propertyName: 'groupIds',
	unknownEntity: false,
	value: '20121',
};

export const entityProperty = {
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

export const collectionCriterion = {
	operatorName: 'eq',
	propertyName: 'cookies',
	value: 'key_name=key_value',
};

export const collectionProperty = {
	label: 'Cookies',
	name: 'cookies',
	options: [],
	type: 'collection',
};

export const doubleCriterion = {
	operatorName: 'eq',
	propertyName: 'deviceScreenResolutionHeight',
	value: '700.00',
};

export const doubleProperty = {
	label: 'Device Screen Resolution Height',
	name: 'deviceScreenResolutionHeight',
	options: [],
	type: 'double',
};

export const mockCriteriaRowData = {
	booleanCriterion: {
		operatorName: 'eq',
		propertyName: 'signedIn',
		value: 'true',
	},

	booleanProperty: {
		label: 'Signed In',
		name: 'signedIn',
		options: [],
		type: 'boolean',
	},

	collectionCriterion: {
		operatorName: 'eq',
		propertyName: 'cookies',
		value: 'key_name=key_value',
	},

	collectionProperty: {
		label: 'Cookies',
		name: 'cookies',
		options: [],
		type: 'collection',
	},

	dateCriterion: {
		operatorName: 'eq',
		propertyName: 'birthDate',
		value: '2023-09-22',
	},

	dateProperty: {
		label: 'Date of Birth',
		name: 'birthDate',
		options: [],
		type: 'date',
	},

	doubleCriterion: {
		operatorName: 'eq',
		propertyName: 'deviceScreenResolutionHeight',
		value: '700.00',
	},

	doubleProperty: {
		label: 'Device Screen Resolution Height',
		name: 'deviceScreenResolutionHeight',
		options: [],
		type: 'double',
	},

	entityCriterion: {
		displayValue: 'Liferay DXP',
		operatorName: 'eq',
		propertyName: 'groupIds',
		unknownEntity: false,
		value: '20121',
	},

	entityProperty: {
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
	},

	stringCriterion: {
		operatorName: 'eq',
		propertyName: 'firstName',
		value: 'string value',
	},

	stringProperty: {
		label: 'First Name',
		name: 'firstName',
		options: [],
		type: 'string',
	},
};
