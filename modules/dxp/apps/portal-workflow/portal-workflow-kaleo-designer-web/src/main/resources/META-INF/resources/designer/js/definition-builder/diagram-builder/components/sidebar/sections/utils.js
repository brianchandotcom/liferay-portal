/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isIdDuplicated} from '../utils';

function checkLabelErrors(errors, target) {
	if (target.value.trim() === '') {
		return {...errors, label: true};
	}
	else {
		return {...errors, label: false};
	}
}

function checkIdErrors(elements, errors, target) {
	if (target.value.trim() === '') {
		return {
			...errors,
			id: {duplicated: false, empty: true},
		};
	}
	else {
		if (isIdDuplicated(elements, target.value.trim())) {
			return {
				...errors,
				id: {duplicated: true, empty: false},
			};
		}
		else {
			return {
				...errors,
				id: {duplicated: false, empty: false},
			};
		}
	}
}

function getUpdatedLabelItem(key, selectedItem, target) {
	return {
		...selectedItem,
		data: {
			...selectedItem.data,
			label: {
				...selectedItem.data.label,
				[key]: target.value,
			},
		},
	};
}

function limitValue({defaultValue, min, value}) {
	if (isNaN(value) || value < min) {
		return defaultValue;
	}

	return value;
}

function sortElements(array, property) {
	array.sort((a, b) => (a[property] > b[property] ? 1 : -1));
}

export {
	checkLabelErrors,
	checkIdErrors,
	getUpdatedLabelItem,
	limitValue,
	sortElements,
};
