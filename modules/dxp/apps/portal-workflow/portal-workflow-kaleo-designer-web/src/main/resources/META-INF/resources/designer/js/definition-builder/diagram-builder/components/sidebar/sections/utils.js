/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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

export {checkLabelErrors, checkIdErrors, getUpdatedLabelItem};
