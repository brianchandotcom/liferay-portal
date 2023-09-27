/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Property} from '../../types/Criteria';
import {Conjunction} from './constants';
import {buildQueryString} from './odata';
interface Contributor {
	conjunctionId: Conjunction;
	conjunctionInputId: string;
	criteriaMap: Record<string, unknown>;
	entityName: string;
	initialQuery?: Parameters<typeof buildQueryString>[0][0];
	inputId: string;
	modelLabel: string;
	properties: Property[];
	propertyKey: unknown;
	query: string;
}
interface PropertyGroup {
	entityName: string;
	name: string;
	properties: Property[];
	propertyKey: string;
}

/**
 * Produces a list of Contributors from a list of initialContributors
 * and a list of propertyGroups.
 */
export declare function initialContributorsToContributors(
	initialContributors: Contributor[],
	propertyGroups: PropertyGroup[]
): {
	conjunctionId: Conjunction;
	conjunctionInputId: string;
	criteriaMap:
		| import('../../types/Criteria').CriteriaItem
		| import('../../types/Criteria').Criteria
		| null;
	entityName: string | undefined;
	inputId: string;
	modelLabel: string | undefined;
	properties: Property[] | undefined;
	propertyKey: unknown;
	query: string;
}[];

/**
 * Applies a criteria change to a contributor from a list in both the
 * criteriaMap and query properties.
 */
export declare function applyCriteriaChangeToContributors(
	contributors: Contributor[],
	change: {
		criteriaChange: Contributor['initialQuery'];
		propertyKey: PropertyKey;
	}
): (
	| Contributor
	| {
			criteriaMap:
				| import('../../types/Criteria').CriteriaItem
				| import('../../types/Criteria').Criteria
				| null
				| undefined;
			query: string;
			conjunctionId: Conjunction;
			conjunctionInputId: string;
			entityName: string;
			initialQuery?:
				| import('../../types/Criteria').CriteriaItem
				| import('../../types/Criteria').Criteria
				| null
				| undefined;
			inputId: string;
			modelLabel: string;
			properties: Property[];
			propertyKey: unknown;
	  }
)[];

/**
 * Applies a conjunction change to the whole array of contributors.
 */
export declare function applyConjunctionChangeToContributor(
	contributors: Contributor[],
	conjunctionName: Conjunction
): Contributor[];
export {};
