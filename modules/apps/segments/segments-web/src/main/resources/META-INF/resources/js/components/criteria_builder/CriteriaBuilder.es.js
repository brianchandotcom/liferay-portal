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

import PropTypes from 'prop-types';
import React, {Component} from 'react';

import {
	conjunctionShape,
	criteriaShape,
	propertyShape,
	propertyTypesShape
} from '../../utils/types.es';
import {
	insertAtIndex,
	removeAtIndex,
	replaceAtIndex,
	sub
} from '../../utils/utils.es';
import CriteriaGroup from './CriteriaGroup.es';

class CriteriaBuilder extends Component {
	static propTypes = {
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
		supportedConjunctions: PropTypes.arrayOf(conjunctionShape),
		supportedOperators: PropTypes.array,
		supportedProperties: PropTypes.arrayOf(propertyShape).isRequired,
		supportedPropertyTypes: propertyTypesShape
	};

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
	_cleanCriteriaMapItems(criteriaItems, root) {
		const criteria = criteriaItems
			.filter(({items}) => {
				return items ? items.length : true;
			})
			.map(item => {
				let cleanedItem = item;

				if (item.items) {
					if (item.items.length === 1) {
						const soloItem = item.items[0];

						if (soloItem.items) {
							cleanedItem = {
								conjunctionName: soloItem.conjunctionName,
								groupId: soloItem.groupId,
								items: this._cleanCriteriaMapItems(
									soloItem.items
								)
							};
						} else {
							cleanedItem = root ? item : soloItem;
						}
					} else {
						cleanedItem = {
							...item,
							items: this._cleanCriteriaMapItems(item.items)
						};
					}
				}

				return cleanedItem;
			});

		return criteria;
	}

	/**
	 * Cleans and updates the criteria with the newer criteria.
	 * @param {Object} newCriteria The criteria with the most recent changes.
	 */
	_handleCriteriaChange = newCriteria => {
		const items = this._cleanCriteriaMapItems([newCriteria], true);

		this.props.onChange(items[items.length - 1], this.props.propertyKey);
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
	_handleCriterionMove = (...args) => {
		const newCriteria = this._searchAndUpdateCriteria(
			this.props.criteria,
			...args
		);

		this._handleCriteriaChange(newCriteria);
	};

	/**
	 * Checks if an item is a group item by checking if it contains an items
	 * property with at least 1 item.
	 * @param {object} item The criteria item to check.
	 * @returns True if the item is a group.
	 */
	_isGroupItem(item) {
		return item.items && item.items.length;
	}

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
	_searchAndUpdateCriteria = (
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
			items: updatedCriteriaItems.map(item => {
				return this._isGroupItem(item)
					? this._searchAndUpdateCriteria(
							item,
							startGroupId,
							startIndex,
							destGroupId,
							destIndex,
							addCriterion,
							replace
					  )
					: item;
			})
		};
	};

	render() {
		const {
			criteria,
			editing,
			emptyContributors,
			entityName,
			modelLabel,
			propertyKey,
			supportedConjunctions,
			supportedOperators,
			supportedProperties,
			supportedPropertyTypes
		} = this.props;

		return (
			<div className="criteria-builder-root">
				<h4 className="sheet-subtitle">
					{sub(
						Liferay.Language.get('x-with-property-x'),
						[modelLabel, ''],
						false
					)}
				</h4>

				{(!emptyContributors || editing) && (
					<CriteriaGroup
						criteria={criteria}
						editing={editing}
						emptyContributors={emptyContributors}
						entityName={entityName}
						groupId={criteria && criteria.groupId}
						modelLabel={modelLabel}
						onChange={this._handleCriteriaChange}
						onMove={this._handleCriterionMove}
						propertyKey={propertyKey}
						root
						supportedConjunctions={supportedConjunctions}
						supportedOperators={supportedOperators}
						supportedProperties={supportedProperties}
						supportedPropertyTypes={supportedPropertyTypes}
					/>
				)}
			</div>
		);
	}
}

export default CriteriaBuilder;
