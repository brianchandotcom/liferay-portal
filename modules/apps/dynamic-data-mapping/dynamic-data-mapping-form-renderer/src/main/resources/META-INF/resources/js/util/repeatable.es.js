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

const FIELD_NAME_REGEX = /(_\w+_)ddm\$\$(.+)\$(\w+)\$(\d+)\$\$(\w+)/;

const NESTED_FIELD_NAME_REGEX = /(_\w+_)ddm\$\$(.+)\$(\w+)\$(\d+)#(.+)\$(\w+)\$(\d+)\$\$(\w+)/;

export const generateInstanceId = () =>
	Math.random()
		.toString(36)
		.substr(2, 8);

export const generateName = (name, repeatedIndex) => {
	const parsedName = parseName(name);
	const {fieldName, instanceId, locale, portletNamespace} = parsedName;

	return `${portletNamespace}ddm$$${fieldName}$${instanceId}$${repeatedIndex}$$${locale}`;
};

export const generateNestedFieldName = (name, parentFieldName) => {
	const parsedName = parseNestedFieldName(name);
	const parsedParentFieldName = parseName(parentFieldName);

	const {
		fieldName,
		instanceId,
		locale,
		portletNamespace,
		repeatedIndex
	} = parsedName;

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
		locale
	].join('');
};

export const getRepeatedIndex = name => {
	let parsedName;

	if (NESTED_FIELD_NAME_REGEX.test(name)) {
		parsedName = parseNestedFieldName(name);
	}
	else {
		parsedName = parseName(name);
	}

	return parsedName.repeatedIndex;
};

export const parseName = name => {
	let parsed = {};
	const result = FIELD_NAME_REGEX.exec(name);

	if (result) {
		parsed = {
			fieldName: result[2],
			instanceId: result[3],
			locale: result[5],
			portletNamespace: result[1],
			repeatedIndex: Number(result[4])
		};
	}

	return parsed;
};

export const parseNestedFieldName = name => {
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
			repeatedIndex: Number(result[7])
		};
	}

	return parsed;
};
