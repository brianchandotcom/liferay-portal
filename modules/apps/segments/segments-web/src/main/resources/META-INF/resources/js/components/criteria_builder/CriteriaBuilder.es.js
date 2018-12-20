import React, {Component} from 'react';
import PropTypes from 'prop-types';
import ClayToggle from '../shared/ClayToggle.es';
import CriteriaGroup from './CriteriaGroup.es';
import CriteriaSidebar from '../criteria_sidebar/CriteriaSidebar.es';
import {DragDropContext as dragDropContext} from 'react-dnd';
import getCN from 'classnames';
import HTML5Backend from 'react-dnd-html5-backend';
import {insertAtIndex, removeAtIndex, replaceAtIndex, sub} from '../../utils/utils.es';

const CRITERIA_GROUP_SHAPE = {
	conjunctionName: PropTypes.string,
	groupId: PropTypes.string,
	items: PropTypes.array
};

const CRITERION_SHAPE = {
	operatorName: PropTypes.string,
	propertyName: PropTypes.string,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.array])
};

class CriteriaBuilder extends Component {
	static propTypes = {
		criteria: PropTypes.shape(
			{
				conjunctionName: PropTypes.string,
				groupId: PropTypes.string,
				items: PropTypes.arrayOf(
					PropTypes.oneOfType(
						[
							PropTypes.shape(CRITERIA_GROUP_SHAPE),
							PropTypes.shape(CRITERION_SHAPE)
						]
					)
				)
			}
		),
		modelLabel: PropTypes.string,
		onChange: PropTypes.func,
		supportedConjunctions: PropTypes.arrayOf(
			PropTypes.shape(
				{
					label: PropTypes.string,
					name: PropTypes.string.isRequired
				}
			)
		),
		supportedOperators: PropTypes.array,
		supportedProperties: PropTypes.arrayOf(
			PropTypes.shape(
				{
					entityUrl: PropTypes.string,
					label: PropTypes.string,
					name: PropTypes.string.isRequired,
					options: PropTypes.array,
					type: PropTypes.string.isRequired
				}
			)
		).isRequired,
		supportedPropertyTypes: PropTypes.object
	};

	static defaultProps = {
		readOnly: false
	};

	state = {
		editing: false
	};

	/**
	 * Cleans criteria items by performing the following:
	 * 1. Remove any groups with no items.
	 * 2. Flatten groups that directly contain a single group.
	 * 3. Flatten groups that contain a single criterion.
	 * @param {Array} criteriaItems A list of criterion and criteria groups
	 * @param {boolean} root True if the criteriaItems are from the root group.
	 * to clean.
	 */
	_cleanCriteriaMapItems(criteriaItems, root) {
		return criteriaItems
			.filter(
				({items}) => {
					return items ? items.length : true;
				}
			)
			.map(
				item => {
					let cleanedItem = item;

					if (item.items) {
						if (item.items.length === 1) {
							const soloItem = item.items[0];

							if (soloItem.items) {
								cleanedItem = {
									conjunctionName: soloItem.conjunctionName,
									groupId: soloItem.groupId,
									items: this._cleanCriteriaMapItems(soloItem.items)
								};
							}
							else {
								cleanedItem = root ? item : soloItem;
							}
						}
						else {
							cleanedItem = {
								...item,
								items: this._cleanCriteriaMapItems(item.items)
							};
						}
					}

					return cleanedItem;
				}
			);
	}

	/**
	 * Switches the edit state between true and false.
	 */
	_handleToggleEdit = () => {
		this.setState(
			{
				editing: !this.state.editing
			}
		);
	};

	/**
	 * Cleans and updates the criteria with the newer criteria.
	 * @param {Object} newCriteria The criteria with the most recent changes.
	 */
	_handleCriteriaChange = newCriteria => {
		const items = this._cleanCriteriaMapItems([newCriteria], true);

		this.props.onChange(items[items.length - 1]);
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
	}

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
			updatedCriteriaItems = replace ?
				replaceAtIndex(
					addCriterion,
					updatedCriteriaItems,
					destIndex
				) :
				insertAtIndex(
					addCriterion,
					updatedCriteriaItems,
					destIndex
				);
		}

		if (criteria.groupId === startGroupId) {
			updatedCriteriaItems = removeAtIndex(
				updatedCriteriaItems,
				destGroupId === startGroupId && destIndex < startIndex && !replace ?
					startIndex + 1 :
					startIndex
			);
		}

		return {
			...criteria,
			items: updatedCriteriaItems.map(
				item => {
					return this._isGroupItem(item) ?
						this._searchAndUpdateCriteria(
							item,
							startGroupId,
							startIndex,
							destGroupId,
							destIndex,
							addCriterion,
							replace
						) :
						item;
				}
			)
		};
	}

	render() {
		const {
			criteria,
			modelLabel,
			supportedConjunctions,
			supportedOperators,
			supportedProperties,
			supportedPropertyTypes
		} = this.props;

		const {editing} = this.state;

		const classes = getCN(
			'criteria-builder-root',
			{
				'read-only': !editing
			}
		);

		return (
			<div className={classes}>
				<div className="criteria-builder-section-main">
					<div className="sheet sheet-lg">
						<div className="criteria-builder-toolbar">
							<ClayToggle
								checked={editing}
								iconOff="pencil"
								iconOn="pencil"
								onChange={this._handleToggleEdit}
							/>
						</div>

						<CriteriaGroup
							criteria={criteria}
							editing={editing}
							groupId={criteria && criteria.groupId}
							modelLabel={modelLabel}
							onChange={this._handleCriteriaChange}
							onMove={this._handleCriterionMove}
							root
							supportedConjunctions={supportedConjunctions}
							supportedOperators={supportedOperators}
							supportedProperties={supportedProperties}
							supportedPropertyTypes={supportedPropertyTypes}
						/>
					</div>
				</div>

				<div className="criteria-builder-section-sidebar">
					<CriteriaSidebar
						supportedProperties={supportedProperties}
						title={sub(
							Liferay.Language.get('x-properties'),
							[modelLabel]
						)}
					/>
				</div>
			</div>
		);
	}
}

export default dragDropContext(HTML5Backend)(CriteriaBuilder);