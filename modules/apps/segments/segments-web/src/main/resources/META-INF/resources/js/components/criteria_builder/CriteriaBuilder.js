/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import {criteriaShape, propertyShape} from '../../utils/types.es';
import {
	insertAtIndex,
	removeAtIndex,
	replaceAtIndex,
	sub,
} from '../../utils/utils';
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
	 * Cleans criteria items by performing the following:
	 * 1. Remove any groups with no items.
	 * 2. Flatten groups that directly contain a single group.
	 * 3. Flatten groups that contain a single criterion.
	 * @param {Array} criteriaItems A list of criterion and criteria groups
	 * @param {boolean} root True if the criteriaItems are from the root group.
	 * to clean.
	 * @returns {*}
	 */
	const _cleanCriteriaMapItems = (criteriaItems, root) => {
		const criteria = criteriaItems
			.filter(({items}) => {
				return items ? items.length : true;
			})
			.map((item) => {
				let cleanedItem = item;

				if (item.items) {
					if (item.items.length === 1) {
						const soloItem = item.items[0];

						if (soloItem.items) {
							cleanedItem = {
								conjunctionName: soloItem.conjunctionName,
								groupId: soloItem.groupId,
								items: _cleanCriteriaMapItems(soloItem.items),
							};
						}
						else {
							cleanedItem = root ? item : soloItem;
						}
					}
					else {
						cleanedItem = {
							...item,
							items: _cleanCriteriaMapItems(item.items),
						};
					}
				}

				return cleanedItem;
			});

		return criteria;
	};

	/**
	 * Cleans and updates the criteria with the newer criteria.
	 * @param {Object} newCriteria The criteria with the most recent changes.
	 */
	const _handleCriteriaChange = (newCriteria) => {
		const items = _cleanCriteriaMapItems([newCriteria], true);

		onChange(items[items.length - 1], propertyKey);
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
		const newCriteria = _searchAndUpdateCriteria(criteria, ...args);

		_handleCriteriaChange(newCriteria);
	};

	/**
	 * Checks if an item is a group item by checking if it contains an items
	 * property with at least 1 item.
	 * @param {object} item The criteria item to check.
	 * @returns True if the item is a group.
	 */
	const _isGroupItem = (item) => {
		return item.items && item.items.length;
	};

	/**
	 * Searches through the criteria object and adds or replaces and removes
	 * the criterion at their respective specified index. insertAtIndex must
	 * come before removeAtIndex since the startIndex is incremented by 1
	 * when the destination index comes before the start index in the same
	 * group. The startIndex is not incremented if a replace is occurring.
	 * This is used for moving a criterion between groups.
	 * @param {object} criteria The criteria object to update.
	 * @param {string} startGroupId Group ID of the item to remove.
	 * @param {number} startIndex Index in the group to remove.
	 * @param {string} destGroupId Group ID of the item to add.
	 * @param {number} destIndex Index in the group where the criterion will
	 * be added.
	 * @param {object} addCriterion The criterion that is being moved.
	 * @param {boolean} replace True if the destIndex should replace rather than
	 * insert.
	 */
	const _searchAndUpdateCriteria = (
		criteria,
		startGroupId,
		startIndex,
		destGroupId,
		destIndex,
		addCriterion,
		replace
	) => {
		let updatedCriteriaItems = criteria.items;

		if (criteria.groupId === destGroupId) {
			updatedCriteriaItems = replace
				? replaceAtIndex(addCriterion, updatedCriteriaItems, destIndex)
				: insertAtIndex(addCriterion, updatedCriteriaItems, destIndex);
		}

		if (criteria.groupId === startGroupId) {
			updatedCriteriaItems = removeAtIndex(
				updatedCriteriaItems,
				destGroupId === startGroupId &&
					destIndex < startIndex &&
					!replace
					? startIndex + 1
					: startIndex
			);
		}

		return {
			...criteria,
			items: updatedCriteriaItems.map((item) => {
				return _isGroupItem(item)
					? _searchAndUpdateCriteria(
							item,
							startGroupId,
							startIndex,
							destGroupId,
							destIndex,
							addCriterion,
							replace
					  )
					: item;
			}),
		};
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
