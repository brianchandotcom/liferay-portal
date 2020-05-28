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

import {normalizeFieldName} from 'dynamic-data-mapping-form-renderer';

import {findFieldByName} from './findFieldByName.es';

export default (
	{dataDefinitionFields},
	desiredName,
	currentName = null,
	blacklist = []
) => {
	let counter = 0;
	let name = normalizeFieldName(desiredName);

	let existingField;

	while (
		((existingField = findFieldByName(dataDefinitionFields, name)) &&
			existingField &&
			existingField.name !== currentName) ||
		blacklist.includes(name)
	) {
		name = normalizeFieldName(`${desiredName}${++counter}`);
	}

	return name;
};
