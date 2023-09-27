/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const FIELD_NAME_REGEX = /(_\w+_)ddm\$\$(.+)\$(\w+)\$(\d+)\$\$(\w+)/;

const NESTED_FIELD_NAME_REGEX = /(_\w+_)ddm\$\$(.+)\$(\w+)\$(\d+)#(.+)\$(\w+)\$(\d+)\$\$(\w+)/;

export function parseName(name) {
	const result = FIELD_NAME_REGEX.exec(name);

	return result
		? {
				editingLanguageId: result[5],
				fieldName: result[2],
				instanceId: result[3],
				portletNamespace: result[1],
				repeatedIndex: Number(result[4]),
		  }
		: {};
}

export function isNestedFieldName(name) {
	return NESTED_FIELD_NAME_REGEX.test(name);
}

export function generateName(name, props = {}) {
	const parsedName = parseName(name);
	const {
		editingLanguageId = parsedName.editingLanguageId,
		fieldName = parsedName.fieldName,
		instanceId = parsedName.instanceId,
		portletNamespace = parsedName.portletNamespace,
		repeatedIndex = parsedName.repeatedIndex,
	} = props;

	return `${portletNamespace}ddm$$${fieldName}$${instanceId}$${repeatedIndex}$$${editingLanguageId}`;
}

export function parseNestedFieldName(name) {
	let parsed = {};
	const result = NESTED_FIELD_NAME_REGEX.exec(name);

	if (result) {
		parsed = {
			fieldName: result[5],
			instanceId: result[6],
			locale: result[8],
			parentFieldName: result[2],
			parentInstanceId: result[3],
			parentRepeatedIndex: Number(result[4]),
			portletNamespace: result[1],
			repeatedIndex: Number(result[7]),
		};
	}

	return parsed;
}

export function updateNestedFieldNameIndex(name, repeatedIndex) {
	const parsedName = parseNestedFieldName(name);

	const {fieldName, instanceId, portletNamespace} = parsedName;

	return [
		portletNamespace,
		'ddm$$',
		parsedName.parentFieldName,
		'$',
		parsedName.parentInstanceId,
		'$',
		parsedName.parentRepeatedIndex,
		'#',
		fieldName,
		'$',
		instanceId,
		'$',
		repeatedIndex,
		'$$',
		parsedName.locale || parsedName.editingLanguageId,
	].join('');
}

export function generateNestedFieldName(name, parentFieldName) {
	const parsedParentFieldName = parseName(parentFieldName);
	let parsedName = parseNestedFieldName(name);

	if (!parsedName.fieldName) {
		parsedName = parseName(name);
	}

	const {fieldName, instanceId, portletNamespace, repeatedIndex} = parsedName;

	return [
		portletNamespace,
		'ddm$$',
		parsedParentFieldName.fieldName,
		'$',
		parsedParentFieldName.instanceId,
		'$',
		parsedParentFieldName.repeatedIndex,
		'#',
		fieldName,
		'$',
		instanceId,
		'$',
		repeatedIndex,
		'$$',
		parsedName.locale || parsedName.editingLanguageId,
	].join('');
}

export function getRepeatedIndex(name) {
	let parsedName;

	if (NESTED_FIELD_NAME_REGEX.test(name)) {
		parsedName = parseNestedFieldName(name);
	}
	else {
		parsedName = parseName(name);
	}

	return parsedName.repeatedIndex;
}
