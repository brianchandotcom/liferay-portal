/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TOTAL_OF_FIELD} from './constants';

export function isHabitational(value) {
	return value === 'habitational';
}

export function isThereSwimming(value) {
	return value === 'true' || value === true;
}

export function propertyTotalFields(properties) {
	let fieldCount = TOTAL_OF_FIELD.PROPERTY;

	if (
		properties?.basics?.properties?.segment.toLowerCase() === 'habitational'
	) {
		fieldCount++;
	}
	if (properties?.property?.isThereSwimming === 'true') {
		fieldCount++;
	}

	return fieldCount;
}
