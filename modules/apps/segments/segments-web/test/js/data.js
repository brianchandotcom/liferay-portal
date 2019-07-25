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
	RELATIONAL_OPERATORS
} from '../../src/main/resources/META-INF/resources/js/utils/constants.es';

const {AND, OR} = CONJUNCTIONS;
const {EQ} = RELATIONAL_OPERATORS;

function generateItems(times) {
	const items = [];

	for (let i = 0; i < times; i++) {
		items.push({
			operatorName: EQ,
			propertyName: 'firstName',
			value: 'test'
		});
	}

	return items;
}

export function mockCriteria(numOfItems) {
	return {
		conjunctionName: AND,
		groupId: 'group_01',
		items: generateItems(numOfItems)
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
								items: generateItems(2)
							},
							...generateItems(1)
						]
					},
					...generateItems(1)
				]
			},
			...generateItems(1)
		]
	};
}
