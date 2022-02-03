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

export declare const DEFAULT_DDM_FIELD_PROPERTIES: Set<string>;

/**
 * Gets a data definition from a field
 *
 * @param {object} field - The field
 * @param {Object[]} field.nestedFields - The array containing all nested fields.
 * 										  It may be undefined
 * @param {object} field.settingsContext - The settings context of a field
 */
export declare function fieldToDataDefinition({
	nestedFields,
	settingsContext,
}: Field): DataDefinition;
