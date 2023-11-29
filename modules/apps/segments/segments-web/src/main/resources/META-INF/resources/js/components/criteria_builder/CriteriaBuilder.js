/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import cleanCriteria from '../../utils/cleanCriteria';
import searchAndUpdateCriteria from '../../utils/searchAndUpdateCriteria';
import {criteriaShape, propertyShape} from '../../utils/types.es';
import {sub} from '../../utils/utils';
import CriteriaGroup from './CriteriaGroup';

export default function CriteriaBuilder({
	criteria,
	editing,
	emptyContributors,
	entityName,
	modelLabel,
	onChange,
	propertyKey,
	renderEmptyValuesErrors,
	supportedProperties,
}) {

	/**
	 * Cleans and updates the criteria with the newer criteria.
	 * @param {Object} newCriteria The criteria with the most recent changes.
	 */
	const _handleCriteriaChange = (newCriteria) => {
		onChange(cleanCriteria(newCriteria), propertyKey);
	};

	/**
	 * Moves the criterion to the specified index by removing and adding, and
	 * updates the criteria.
	 * @param {string} startGroupId Group ID of the item to remove.
	 * @param {number} startIndex Index in the group to remove.
	 * @param {string} destGroupId Group ID of the item to add.
	 * @param {number} destIndex Index in the group where the criterion will
	 * be added.
	 * @param {object} criterion The criterion that is being moved.
	 * @param {boolean} replace True if the destIndex should replace rather than
	 * insert.
	 */
	const _handleCriterionMove = (...args) => {
		_handleCriteriaChange(searchAndUpdateCriteria(criteria, ...args));
	};

	return (
		<div className="criteria-builder-root">
			<h3 className="sheet-subtitle">
				{sub(
					Liferay.Language.get('x-with-property-x'),
					[modelLabel, ''],
					false
				)}
			</h3>

			{(!emptyContributors || editing) && (
				<CriteriaGroup
					criteria={criteria}
					editing={editing}
					emptyContributors={emptyContributors}
					entityName={entityName}
					groupId={criteria && criteria.groupId}
					modelLabel={modelLabel}
					onChange={_handleCriteriaChange}
					onMove={_handleCriterionMove}
					propertyKey={propertyKey}
					renderEmptyValuesErrors={renderEmptyValuesErrors}
					root
					supportedProperties={supportedProperties}
				/>
			)}
		</div>
	);
}

CriteriaBuilder.propTypes = {
	criteria: criteriaShape,
	editing: PropTypes.bool.isRequired,
	emptyContributors: PropTypes.bool.isRequired,

	/**
	 * Name of the entity that a set of properties belongs to, for example,
	 * "User". This value it not displayed anywhere. Only used in
	 * CriteriaRow for requesting a field value's name.
	 * @default undefined
	 * @type {?(string|undefined)}
	 */
	entityName: PropTypes.string.isRequired,

	/**
	 * Name displayed to label a contributor and its' properties.
	 * @default undefined
	 * @type {?(string|undefined)}
	 */
	modelLabel: PropTypes.string,
	onChange: PropTypes.func,
	propertyKey: PropTypes.string.isRequired,
	renderEmptyValuesErrors: PropTypes.bool,
	supportedProperties: PropTypes.arrayOf(propertyShape).isRequired,
};
