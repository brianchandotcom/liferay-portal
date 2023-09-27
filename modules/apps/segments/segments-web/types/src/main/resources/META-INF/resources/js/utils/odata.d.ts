/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
