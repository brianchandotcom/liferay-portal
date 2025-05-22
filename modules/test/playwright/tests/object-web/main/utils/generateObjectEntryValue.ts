/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getRandomInt} from '../../../../utils/getRandomInt';
import getRandomString from '../../../../utils/getRandomString';
import {
	getObjectEntryAPIDateFormat,
	getObjectEntryUIDateFormat,
} from './dateFormat';
import {ObjectFieldBusinessTypes} from './mockObjectFields';

function getRandomDate(format: 'API' | 'UI'): string {
	const currentDate = new Date();

	const randomDate = new Date(currentDate.getTime() * Math.random());

	if (format === 'API') {
		return getObjectEntryAPIDateFormat(randomDate);
	}
	else {
		return getObjectEntryUIDateFormat(randomDate);
	}
}

export function generateRandomObjectFieldObjectEntryValue(
	format: 'API' | 'UI',
	listTypeDefinitionItems: string[],
	objectFieldBusinessType: ObjectFieldBusinessTypes
) {
	const listTypeDefinitionItemsRandomLength1 = Math.floor(
		Math.random() * listTypeDefinitionItems.length
	);
	const listTypeDefinitionItemsRandomLength2 = Math.floor(
		Math.random() * listTypeDefinitionItems.length
	);

	switch (objectFieldBusinessType) {
		case 'Boolean':
			return Math.random() < 0.5;
		case 'Date':
			return getRandomDate(format);
		case 'Decimal':
			return parseFloat(Math.random().toFixed(10)).toString();
		case 'Encrypted':
			return getRandomString();
		case 'Integer':
			return Math.floor(Math.random() * 100).toString();
		case 'LongInteger':
			return getRandomInt().toString();
		case 'LongText':
			return getRandomString();
		case 'MultiselectPicklist':
			return [
				listTypeDefinitionItems[listTypeDefinitionItemsRandomLength1],
				listTypeDefinitionItems[listTypeDefinitionItemsRandomLength2],
			];
		case 'Picklist':
			return {
				key: listTypeDefinitionItems[
					listTypeDefinitionItemsRandomLength1
				],
			};
		case 'PrecisionDecimal':
			return parseFloat(Math.random().toFixed(15)).toString();
		case 'RichText':
			return getRandomString().substring(0, 35);
		case 'Text':
			return getRandomString();
		default:
			return '';
	}
}
