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

import {CONJUNCTIONS, SUPPORTED_CONJUNCTIONS} from './constants.es';
import {buildQueryString, translateQueryToCriteria} from './odata.es';

/**
 * Produces a list of Contributors
 * from a list of initialContributors
 * and a list of propertyGroups
 *
 * @export
 * @param {Object[]} initialContributors
 * @param {string} initialContributors[].conjunctionId
 * @param {string} initialContributors[].conjunctionInputId
 * @param {Object} initialContributors[].criteriaMap
 * @param {string} initialContributors[].entityName
 * @param {string} initialContributors[].inputId
 * @param {string} initialContributors[].modelLabel
 * @param {Array} initialContributors[].properties
 * @param {string} initialContributors[].propertyKey
 * @param {string} initialContributors[].query
 *
 * @param {Object[]} propertyGroups
 * @param {string} propertyGroups[].entityName
 * @param {string} propertyGroups[].name
 * @param {Array} propertyGroups[].properties
 * @param {string} propertyGroups[].propertyKey
 *
 * @typedef {{
 *   conjunctionId: string,
 *   conjunctionInputId: string,
 *   criteriaMap: Object,
 *   entityName: string,
 *   inputId: string,
 *   modelLabel: string,
 *   properties: Array,
 *   propertyKey: string,
 *   query: string
 * }} Contributor
 *
 * @return {Contributor[]} contributors
 */
export function initialContributorsToContributors(
	initialContributors,
	propertyGroups
) {
	const DEFAULT_CONTRIBUTOR = {conjunctionId: CONJUNCTIONS.AND};
	const {conjunctionId: initialConjunction} =
		initialContributors.find(c => c.conjunctionId) || DEFAULT_CONTRIBUTOR;

	return initialContributors.map(initialContributor => {
		const propertyGroup =
			propertyGroups &&
			propertyGroups.find(
				propertyGroup =>
					initialContributor.propertyKey === propertyGroup.propertyKey
			);

		return {
			conjunctionId:
				initialContributor.conjunctionId || initialConjunction,
			conjunctionInputId: initialContributor.conjunctionInputId,
			criteriaMap: initialContributor.initialQuery
				? translateQueryToCriteria(initialContributor.initialQuery)
				: null,
			entityName: propertyGroup && propertyGroup.entityName,
			inputId: initialContributor.inputId,
			modelLabel: propertyGroup && propertyGroup.name,
			properties: propertyGroup && propertyGroup.properties,
			propertyKey: initialContributor.propertyKey,
			query: initialContributor.initialQuery
		};
	});
}

/**
 * Applies a criteria change to a contributor from a list
 * in both the criteriaMap and query properties
 *
 * @export
 * @param {Contributor[]} contributors
 * @param {{ propertyKey: string, criteriaChange: Array }} change - Contains the criteria change and an identifier to locate the right contributor
 *
 * @return {Contributor[]} contributors
 */
export function applyCriteriaChangeToContributors(contributors, change) {
	return contributors.map(contributor => {
		const {conjunctionId, properties, propertyKey} = contributor;

		return change.propertyKey === propertyKey
			? {
					...contributor,
					criteriaMap: change.criteriaChange,
					query: buildQueryString(
						[change.criteriaChange],
						conjunctionId,
						properties
					)
			  }
			: contributor;
	});
}

/**
 * Applies a conjunction change to the whole array of contributors
 *
 * @export
 * @param {Contributor[]} contributors
 * @return {Contributor[]} contributors
 */
export function applyConjunctionChangeToContributor(contributors) {
	const prevConjunction = contributors[0] && contributors[0].conjunctionId;

	const conjunctionIndex = SUPPORTED_CONJUNCTIONS.findIndex(
		item => item.name === prevConjunction
	);

	const conjunctionSelected =
		conjunctionIndex === SUPPORTED_CONJUNCTIONS.length - 1
			? SUPPORTED_CONJUNCTIONS[0].name
			: SUPPORTED_CONJUNCTIONS[conjunctionIndex + 1].name;

	const nextContributors = contributors.map(contributor => ({
		...contributor,
		conjunctionId: conjunctionSelected
	}));
	return nextContributors;
}
