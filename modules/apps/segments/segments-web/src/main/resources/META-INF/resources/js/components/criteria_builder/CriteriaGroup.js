/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {PropTypes} from 'prop-types';
import React, {Fragment} from 'react';

import useDragSource from '../../hooks/useDragSource';
import {CONJUNCTIONS} from '../../utils/constants';
import {DragTypes} from '../../utils/dragTypes';
import {
	generateGroupId,
	getChildGroupIds,
	getSupportedOperatorsFromType,
	insertAtIndex,
	replaceAtIndex,
} from '../../utils/utils';
import Conjunction from './Conjunction.es';
import CriteriaRow from './CriteriaRow';
import DropZone from './DropZone';
import EmptyDropZone from './EmptyDropZone';

export default function CriteriaGroup({
	criteria,
	draggingItem,
	editing,
	emptyContributors,
	entityName,
	groupId,
	index,
	modelLabel,
	onChange,
	onMove,
	parentGroupId,
	propertyKey,
	renderEmptyValuesErrors = false,
	root = false,
	supportedProperties,
}) {
	const _handleConjunctionSelect = (conjunctionName) => {
		onChange({
			...criteria,
			conjunctionName,
		});
	};

	const {handlerRef, isDragging} = useDragSource({
		item: {
			childGroupIds: getChildGroupIds(criteria),
			criterion: criteria,
			groupId: parentGroupId,
			index,
			type: DragTypes.CRITERIA_GROUP,
		},
	});

	/**
	 * Adds a new criterion in a group at the specified index. If the criteria
	 * was previously empty and is being added to the root group, a new group
	 * will be added as well.
	 * @param {number} index The position the criterion will be inserted in.
	 * @param {object} criterion The criterion that will be added.
	 * @memberof CriteriaGroup
	 */
	const _handleCriterionAdd = (index, criterion) => {
		const {
			defaultValue = '',
			operatorName,
			propertyName,
			type,
			value,
		} = criterion;

		const criterionValue = value || defaultValue;

		const operators = getSupportedOperatorsFromType(type);

		const newCriterion = {
			operatorName: operatorName ? operatorName : operators[0].name,
			propertyName,
			type,
			value: criterionValue,
		};

		if (root && !criteria) {
			onChange({
				conjunctionName: CONJUNCTIONS.AND,
				groupId: generateGroupId(),
				items: [newCriterion],
			});
		}
		else {
			onChange({
				...criteria,
				items: insertAtIndex(newCriterion, criteria.items, index),
			});
		}
	};

	const _handleCriterionChange = (index) => (newCriterion) => {
		onChange({
			...criteria,
			items: replaceAtIndex(newCriterion, criteria.items, index),
		});
	};

	const _handleCriterionDelete = (index) => {
		onChange({
			...criteria,
			items: criteria.items.filter((fItem, fIndex) => fIndex !== index),
		});
	};

	const _isCriteriaEmpty = () => {
		return criteria ? !criteria.items.length : true;
	};

	const _renderConjunction = (index) => {
		return (
			<>
				<Conjunction
					conjunctionName={criteria.conjunctionName}
					editing={editing}
					onSelect={_handleConjunctionSelect}
				/>

				<DropZone
					before
					groupId={groupId}
					index={index}
					onCriterionAdd={_handleCriterionAdd}
					onMove={onMove}
					propertyKey={propertyKey}
				/>
			</>
		);
	};

	const _renderCriterion = (criterion, index) => {
		return (
			<div
				className={classNames('criterion position-relative', {
					'criterion-group': criterion.items,
				})}
			>
				{criterion.items ? (
					<CriteriaGroup
						criteria={criterion}
						editing={editing}
						entityName={entityName}
						groupId={criterion.groupId}
						index={index}
						modelLabel={modelLabel}
						onChange={_handleCriterionChange(index)}
						onMove={onMove}
						parentGroupId={groupId}
						propertyKey={propertyKey}
						renderEmptyValuesErrors={renderEmptyValuesErrors}
						supportedProperties={supportedProperties}
					/>
				) : (
					<CriteriaRow
						criterion={criterion}
						editing={editing}
						entityName={entityName}
						groupId={groupId}
						index={index}
						modelLabel={modelLabel}
						onAdd={_handleCriterionAdd}
						onChange={_handleCriterionChange(index)}
						onDelete={_handleCriterionDelete}
						onMove={onMove}
						propertyKey={propertyKey}
						renderEmptyValuesErrors={renderEmptyValuesErrors}
						root={root}
						supportedProperties={supportedProperties}
					/>
				)}

				<DropZone
					groupId={groupId}
					index={index}
					onCriterionAdd={_handleCriterionAdd}
					onMove={onMove}
					propertyKey={propertyKey}
				/>
			</div>
		);
	};

	const singleRow = criteria && criteria.items && criteria.items.length === 1;

	const disabled =
		isDragging ||
		(draggingItem && draggingItem.propertyKey !== propertyKey);

	return (
		<div
			className={classNames(
				{
					'criteria-group-root my-2 p-2 w-100': criteria,
					disabled,
				},
				`color--${propertyKey} criteria-group-item${
					root ? '-root' : ''
				}`
			)}
		>
			{_isCriteriaEmpty() ? (
				<EmptyDropZone
					emptyContributors={emptyContributors}
					onCriterionAdd={_handleCriterionAdd}
					propertyKey={propertyKey}
				/>
			) : (
				<>
					<DropZone
						before
						groupId={groupId}
						index={0}
						onCriterionAdd={_handleCriterionAdd}
						onMove={onMove}
						propertyKey={propertyKey}
					/>

					{editing && singleRow && !root && (
						<div
							className="align-items-center d-flex drag-icon h-100 position-absolute top-0"
							ref={handlerRef}
						>
							<ClayIcon symbol="drag" />
						</div>
					)}

					{criteria.items &&
						criteria.items.map((criterion, index) => {
							return (
								<Fragment key={index}>
									{index !== 0 && _renderConjunction(index)}

									{_renderCriterion(criterion, index)}
								</Fragment>
							);
						})}
				</>
			)}
		</div>
	);
}

CriteriaGroup.propTypes = {
	connectDragPreview: PropTypes.func,
	criteria: PropTypes.object,
	dragging: PropTypes.bool,
	editing: PropTypes.bool,
	emptyContributors: PropTypes.bool,
	entityName: PropTypes.string,
	groupId: PropTypes.string,
	index: PropTypes.number,
	modelLabel: PropTypes.string,
	onChange: PropTypes.func,
	onMove: PropTypes.func,
	parentGroupId: PropTypes.string,
	propertyKey: PropTypes.string.isRequired,
	renderEmptyValuesErrors: PropTypes.bool,
	root: PropTypes.bool,
	supportedProperties: PropTypes.array,
};
