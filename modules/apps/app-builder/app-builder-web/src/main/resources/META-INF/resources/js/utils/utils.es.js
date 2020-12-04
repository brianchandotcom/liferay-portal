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

export const concatValues = (values) =>
	values
		.join(', ')
		.replace(
			/, ([^,]*)$/,
			` ${Liferay.Language.get('and').toLowerCase()} $1`
		);

export const isEqualObjects = (firstObj, secondObj) => {
	if (typeof firstObj !== 'object' || typeof secondObj !== 'object') {
		return false;
	}

	return JSON.stringify(firstObj) === JSON.stringify(secondObj);
};

export const getValidName = (defaultName, name) => {
	if (name?.toLowerCase() === 'null') {
		return defaultName;
	}

	return name;
};

export const normalizeNames = ({
	allowEmptyKeys = true,
	defaultName = '',
	localizableValue,
}) => {
	const name = {};

	Object.keys(localizableValue).forEach((languageId) => {
		const value = localizableValue[languageId];
		const normalizedValue = getValidName(defaultName, value)?.trim();

		if (!allowEmptyKeys && !normalizedValue) {
			return;
		}

		name[languageId] = normalizedValue;
	});

	return name;
};
