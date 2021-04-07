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

import {FieldUtil} from 'dynamic-data-mapping-form-builder';

/**
 * This is a literary copy of the logic of the old LayoutProvider,
 * check the documentation for more details.
 */
const getFieldNameGenerator = (pages, generateFieldNameUsingFieldLabel) => (
	preferredName,
	currentName,
	blacklist = []
) =>
	FieldUtil.generateFieldName(
		pages,
		preferredName,
		currentName,
		blacklist,
		generateFieldNameUsingFieldLabel
	);

export const INITIAL_CONFIG_STATE = {
	cache: {},
	generateFieldNameUsingFieldLabel: false,
	getFieldNameGenerator,
};
