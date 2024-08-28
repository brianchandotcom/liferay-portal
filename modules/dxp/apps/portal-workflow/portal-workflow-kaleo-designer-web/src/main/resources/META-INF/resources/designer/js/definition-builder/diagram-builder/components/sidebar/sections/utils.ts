/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isIdDuplicated} from '../utils';

import type {Elements, Node} from 'react-flow-renderer';

export type NodeInformationError = {
	id:
		| false
		| {
				duplicated: boolean;
				empty: boolean;
		  };
	label: boolean;
};

export function checkLabelErrors(
	errors: NodeInformationError,
	target: HTMLInputElement
) {
	if (target.value.trim() === '') {
		return {...errors, label: true};
	}
	else {
		return {...errors, label: false};
	}
}

export function checkIdErrors(
	elements: Elements,
	errors: NodeInformationError,
	target: HTMLInputElement
) {
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

export function getUpdatedLabelItem(
	key: Liferay.Language.Locale,
	selectedItem: Node,
	target: HTMLInputElement
) {
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

export function sortElements<T extends object>(array: T[], property: keyof T) {
	array.sort((a, b) => (a[property] > b[property] ? 1 : -1));
}
