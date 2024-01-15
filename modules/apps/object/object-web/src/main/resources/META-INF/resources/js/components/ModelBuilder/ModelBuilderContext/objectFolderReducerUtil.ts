/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function convertAllObjectFieldsToUnselected(
	objectFields: ObjectFieldNodeRow[]
) {
	return objectFields.map((objectField) => ({
		...objectField,
		selected: false,
	})) as ObjectFieldNodeRow[];
}

export function objectFieldsCustomSort(objectFields: ObjectFieldNodeRow[]) {
	const fieldOrder = ['id', 'externalReferenceCode'];

	const compareFields = (
		fieldA: ObjectFieldNodeRow,
		fieldB: ObjectFieldNodeRow
	) => {
		const fieldAIndex = fieldOrder.indexOf(fieldA.name as string);
		const fieldBIndex = fieldOrder.indexOf(fieldB.name as string);

		if (fieldAIndex !== -1 && fieldBIndex !== -1) {
			return fieldAIndex - fieldBIndex;
		}
		else if (fieldAIndex !== -1) {
			return -1;
		}
		else if (fieldBIndex !== -1) {
			return 1;
		}

		if (fieldA.required && !fieldB.required) {
			return -1;
		}
		else if (!fieldA.required && fieldB.required) {
			return 1;
		}

		return 0;
	};

	return objectFields.sort(compareFields);
}
