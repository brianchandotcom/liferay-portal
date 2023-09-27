/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Property} from '../../types/Criteria';
import {CONJUNCTIONS, Conjunction, SUPPORTED_CONJUNCTIONS} from './constants';
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
export function initialContributorsToContributors(
	initialContributors: Contributor[],
	propertyGroups: PropertyGroup[]
) {
	const DEFAULT_CONTRIBUTOR = {conjunctionId: CONJUNCTIONS.AND};
	const {conjunctionId: initialConjunction} =
		initialContributors.find((c) => c.conjunctionId) || DEFAULT_CONTRIBUTOR;

	return initialContributors.map((initialContributor) => {
		const propertyGroup =
			propertyGroups &&
			propertyGroups.find(
				(propertyGroup) =>
					initialContributor.propertyKey === propertyGroup.propertyKey
			);

		return {
			conjunctionId:
				initialContributor.conjunctionId || initialConjunction,
			conjunctionInputId: initialContributor.conjunctionInputId,
			criteriaMap: initialContributor.initialQuery || null,
			entityName: propertyGroup && propertyGroup.entityName,
			inputId: initialContributor.inputId,
			modelLabel: propertyGroup && propertyGroup.name,
			properties: propertyGroup && propertyGroup.properties,
			propertyKey: initialContributor.propertyKey,
			query: initialContributor.initialQuery
				? buildQueryString(
						[initialContributor.initialQuery],
						initialContributor.conjunctionId || initialConjunction,
						propertyGroup?.properties || []
				  )
				: '',
		};
	});
}

/**
 * Applies a criteria change to a contributor from a list in both the
 * criteriaMap and query properties.
 */
export function applyCriteriaChangeToContributors(
	contributors: Contributor[],
	change: {
		criteriaChange: Contributor['initialQuery'];
		propertyKey: PropertyKey;
	}
) {
	return contributors.map((contributor) => {
		const {conjunctionId, properties, propertyKey} = contributor;

		return change.propertyKey === propertyKey
			? {
					...contributor,
					criteriaMap: change.criteriaChange,
					query: buildQueryString(
						[change.criteriaChange || null],
						conjunctionId,
						properties
					),
			  }
			: contributor;
	});
}

/**
 * Applies a conjunction change to the whole array of contributors.
 */
export function applyConjunctionChangeToContributor(
	contributors: Contributor[],
	conjunctionName: Conjunction
) {
	const conjunctionIndex = SUPPORTED_CONJUNCTIONS.findIndex(
		(item) => item.name === conjunctionName
	);

	if (conjunctionIndex === -1) {
		return contributors;
	}

	const nextContributors = contributors.map((contributor) => ({
		...contributor,
		conjunctionId: conjunctionName,
	}));

	return nextContributors;
}
