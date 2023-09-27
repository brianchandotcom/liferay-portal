/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PagesVisitor} from './visitors.es';

export function checkValidFieldNameCharacter(character) {
	return /[A-Za-z0-9_]/g.test(character);
}

export function normalizeFieldName(fieldName) {
	let nextUpperCase = false;
	let normalizedFieldName = '';

	fieldName = fieldName.trim();

	for (let i = 0; i < fieldName.length; i++) {
		let item = fieldName[i];

		if (item === ' ') {
			nextUpperCase = true;

			continue;
		}
		else if (!checkValidFieldNameCharacter(item)) {
			continue;
		}

		if (nextUpperCase) {
			item = item.toUpperCase();

			nextUpperCase = false;
		}

		normalizedFieldName += item;
	}

	if (/^\d/.test(normalizedFieldName)) {
		normalizedFieldName = `_${normalizedFieldName}`;
	}

	return normalizedFieldName;
}

export function getFields(pages) {
	const fields = [];
	const visitor = new PagesVisitor(pages);

	visitor.visitFields((field) => {
		fields.push(field);
	});

	return fields;
}

export function hasFieldSet(field) {
	return field?.type === 'fieldset' && field.ddmStructureId;
}
