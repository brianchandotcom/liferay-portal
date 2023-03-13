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

import {Criteria, CriteriaItem, Property} from '../../types/Criteria';
import {Conjunction} from './constants';

/**
 * Handles the single quotes present in the value.
 * Should un-escape the single quotes present in odata format before rendering.
 */
declare function unescapeSingleQuotes<T>(value: T): string | T;

/**
 * Recursively traverses the criteria object to build an oData filter query
 * string. Properties is required to parse the correctly with or without quotes
 * and formatting the query differently for certain types like collection.
 * @param {object} criteria The criteria object.
 * @param {string} queryConjunction The conjunction name value to be used in the
 * query.
 * @param {array} properties The list of property objects. See
 * ContributorBuilder for valid property object shape.
 * @returns An OData query string built from the criteria object.
 */
declare function buildQueryString(
	criteria: Array<Criteria | CriteriaItem | null>,
	queryConjunction: Conjunction,
	properties: Property[]
): string;
export {buildQueryString, unescapeSingleQuotes};
