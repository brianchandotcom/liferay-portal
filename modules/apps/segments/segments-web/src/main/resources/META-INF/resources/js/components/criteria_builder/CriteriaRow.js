/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import classNames from 'classnames';
import {fetch} from 'frontend-js-web';
import {PropTypes} from 'prop-types';
import React, {useCallback, useContext, useEffect} from 'react';
import {DropTarget as dropTarget} from 'react-dnd';

import ThemeContext from '../../ThemeContext.es';
import {
	POSITIONS,
	useMovementSource,
	useMovementTarget,
} from '../../contexts/KeyboardMovementContext';
import {PROPERTY_TYPES, SUPPORTED_OPERATORS} from '../../utils/constants';
import {DragTypes} from '../../utils/dragTypes';
import getDropZoneElementClassname from '../../utils/getDropZoneElementClassName';
import {
	createNewGroup,
	getSupportedOperatorsFromType,
	objectToFormData,
} from '../../utils/utils';
import CriteriaRowEditable from './CriteriaRowEditable';
import CriteriaRowReadable from './CriteriaRowReadable';

const acceptedDragTypes = [DragTypes.CRITERIA_ROW, DragTypes.PROPERTY];

const DISPLAY_VALUE_NOT_FOUND_ERROR = 'displayValue not found';

/**
 * Prevents rows from dropping onto itself and adding properties to not matching
 * contributors.
 * This method must be called `canDrop`.
 * @param {Object} props Component's current props.
 * @param {DropTargetMonitor} monitor
 * @returns {boolean} True if the target should accept the item.
 */
function canDrop(props, monitor) {
	const {
		groupId: destGroupId,
		index: destIndex,
		propertyKey: contributorPropertyKey,
	} = props;

	const {
		groupId: startGroupId,
		index: startIndex,
		propertyKey: sidebarItemPropertyKey,
	} = monitor.getItem();

	return (
		(destGroupId !== startGroupId || destIndex !== startIndex) &&
		contributorPropertyKey === sidebarItemPropertyKey
	);
}

/**
 * Implements the behavior of what will occur when an item is dropped.
 * Items dropped on top of rows will create a new grouping.
 * This method must be called `drop`.
 * @param {Object} props Component's current props.
 * @param {DropTargetMonitor} monitor
 */
function drop(props, monitor) {
	const {
		criterion,
		groupId: destGroupId,
		index: destIndex,
		onChange,
		onMove,
	} = props;

	const {
		criterion: droppedCriterion,
		groupId: startGroupId,
		index: startIndex,
	} = monitor.getItem();

	const {
		defaultValue,
		displayValue,
		operatorName,
		propertyName,
		type,
		value,
	} = droppedCriterion;

	const droppedCriterionValue = value || defaultValue;

	const operators = getSupportedOperatorsFromType(type);

	const newCriterion = {
		displayValue,
		operatorName: operatorName ? operatorName : operators[0].name,
		propertyName,
		value: droppedCriterionValue,
	};

	const itemType = monitor.getItemType();

	const newGroup = createNewGroup([criterion, newCriterion]);

	if (itemType === DragTypes.PROPERTY) {
		onChange(newGroup);
	}
	else if (itemType === DragTypes.CRITERIA_ROW) {
		onMove(
			startGroupId,
			startIndex,
			destGroupId,
			destIndex,
			newGroup,
			true
		);
	}
}

/**
 * Gets the selected item object with a `name` and `label` property for a
 * selection input. If one isn't found, a new object is returned using the
 * idSelected for name and label.
 * @param {Array} list The list of objects to search through.
 * @param {string} idSelected The name to match in each object in the list.
 * @return {object} An object with a `name`, `label` and `type` property.
 */
function getSelectedItem(list, idSelected) {
	const selectedItem = list.find((item) => item.name === idSelected);

	return selectedItem
		? selectedItem
		: {
				label: idSelected,
				name: idSelected,
				notFound: true,
				type: PROPERTY_TYPES.STRING,
			};
}

function CriteriaRow({
	canDrop,
	connectDropTarget,
	criterion = {},
	dragging,
	editing = true,
	entityName,
	groupId,
	hover,
	index,
	onAdd,
	onChange,
	onDelete,
	propertyKey,
	renderEmptyValuesErrors = false,
	supportedProperties = [],
}) {
	const themeContext = useContext(ThemeContext);

	const _fetchEntityName = useCallback(() => {
		const {propertyName, value} = criterion;

		const data = Liferay.Util.ns(themeContext.namespace, {
			entityName,
			fieldName: propertyName,
			fieldValue: value,
		});

		fetch(themeContext.requestFieldValueNameURL, {
			body: objectToFormData(data),
			method: 'POST',
		})
			.then((response) => response.json())
			.then(({fieldValueName: displayValue}) => {
				if (displayValue === undefined) {
					throw new Error(DISPLAY_VALUE_NOT_FOUND_ERROR);
				}

				onChange({...criterion, displayValue, unknownEntity: false});
			})
			.catch((error) => {
				if (error && error.message === DISPLAY_VALUE_NOT_FOUND_ERROR) {
					onChange({
						...criterion,
						displayValue: value,
						unknownEntity: true,
					});
				}
				else {
					onChange({...criterion, displayValue: value});
				}
			});
	}, [criterion, onChange, themeContext, entityName]);

	useEffect(() => {
		const _selectedProperty = getSelectedItem(
			supportedProperties,
			criterion.propertyName
		);

		if (
			_selectedProperty.type === PROPERTY_TYPES.ID &&
			criterion.value &&
			!criterion.displayValue
		) {
			_fetchEntityName();
		}
	}, [criterion, supportedProperties, _fetchEntityName]);

	const _renderErrorMessages = ({errorOnProperty, unknownEntityError}) => {
		const errors = [];
		if (errorOnProperty) {
			const message = editing
				? Liferay.Language.get('criteria-error-message-edit')
				: Liferay.Language.get('criteria-error-message-view');

			errors.push({
				message,
			});
		}

		if (unknownEntityError) {
			const message = editing
				? Liferay.Language.get('unknown-element-message-edit')
				: Liferay.Language.get('unknown-element-message-view');

			errors.push({
				message,
			});
		}

		return errors.map((error, index) => {
			return (
				<ClayAlert
					className="bg-transparent border-0 mt-1 p-1"
					displayType="danger"
					key={index}
					title={Liferay.Language.get('error')}
				>
					{error.message}
				</ClayAlert>
			);
		});
	};

	const _renderWarningMessages = () => {
		const warnings = [];
		const message = editing
			? Liferay.Language.get('criteria-warning-message-edit')
			: Liferay.Language.get('criteria-warning-message-view');

		warnings.push({
			message,
		});

		return warnings.map((warning, index) => {
			return (
				<ClayAlert
					className="bg-transparent border-0 mt-1 p-1"
					displayType="warning"
					key={index}
					title={Liferay.Language.get('warning')}
				>
					{warning.message}
				</ClayAlert>
			);
		});
	};

	const {unknownEntity} = criterion;

	const selectedOperator = getSelectedItem(
		SUPPORTED_OPERATORS,
		criterion.operatorName
	);

	const selectedProperty = getSelectedItem(
		supportedProperties,
		criterion.propertyName
	);

	const value = criterion ? criterion.value : '';
	const errorOnProperty = selectedProperty.notFound;
	const error = errorOnProperty || unknownEntity;
	const warningOnProperty =
		selectedProperty.options === undefined
			? false
			: !selectedProperty.options?.length
				? false
				: selectedProperty.options.find((option) => {
						return (
							option.value === value &&
							option.disabled === undefined
						);
					});
	const warning = !(warningOnProperty || warningOnProperty === false);

	if (
		selectedProperty.options !== undefined &&
		!!selectedProperty.options?.length &&
		selectedProperty.options.find((option) => {
			return option.value === value;
		}) === undefined &&
		warning
	) {
		selectedProperty.options.unshift({
			disabled: true,
			label: value,
			value,
		});
	}

	const movementSource = useMovementSource();
	const movementTarget = useMovementTarget();

	const isKeyboardTarget =
		movementSource?.propertyKey === propertyKey &&
		movementTarget?.groupId === groupId &&
		movementTarget?.index === index &&
		movementTarget.position === POSITIONS.middle;

	const dropZoneClassName = getDropZoneElementClassname(
		propertyKey,
		groupId,
		index,
		POSITIONS.middle
	);

	return (
		<>
			{connectDropTarget(
				<div
					className={classNames(
						'criterion-row-root',
						`drop-zone-${propertyKey}`,
						dropZoneClassName,
						{
							'criterion-row-root-error': error,
							'criterion-row-root-warning': warning,
							'dnd-drag': dragging,
							'dnd-hover': (hover && canDrop) || isKeyboardTarget,
						}
					)}
				>
					{editing ? (
						<CriteriaRowEditable
							criterion={criterion}
							error={error}
							groupId={groupId}
							index={index}
							item={getSelectedItem(
								supportedProperties,
								criterion.propertyName
							)}
							onAdd={onAdd}
							onChange={onChange}
							onDelete={onDelete}
							propertyKey={propertyKey}
							renderEmptyValuesErrors={renderEmptyValuesErrors}
							selectedOperator={selectedOperator}
							selectedProperty={selectedProperty}
						/>
					) : (
						<CriteriaRowReadable
							criterion={criterion}
							selectedOperator={selectedOperator}
							selectedProperty={selectedProperty}
						/>
					)}
				</div>
			)}
			{error &&
				_renderErrorMessages({
					errorOnProperty,
					unknownEntityError: unknownEntity,
				})}
			{warning && _renderWarningMessages()}
			{!value && renderEmptyValuesErrors && (
				<ClayAlert
					className="pr-6 text-right"
					displayType="danger"
					title={Liferay.Language.get(
						'a-value-needs-to-be-added-or-selected-in-the-blank-field'
					)}
					variant="feedback"
				/>
			)}
		</>
	);
}

CriteriaRow.propTypes = {
	canDrop: PropTypes.bool,
	connectDragPreview: PropTypes.func,
	connectDropTarget: PropTypes.func,
	criterion: PropTypes.object,
	dragging: PropTypes.bool,
	editing: PropTypes.bool,
	entityName: PropTypes.string,
	groupId: PropTypes.string.isRequired,
	hover: PropTypes.bool,
	index: PropTypes.number.isRequired,
	modelLabel: PropTypes.string,
	onAdd: PropTypes.func.isRequired,
	onChange: PropTypes.func.isRequired,
	onDelete: PropTypes.func.isRequired,
	onMove: PropTypes.func.isRequired,
	propertyKey: PropTypes.string.isRequired,
	renderEmptyValuesErrors: PropTypes.bool,
	supportedProperties: PropTypes.array,
};

export default dropTarget(
	acceptedDragTypes,
	{
		canDrop,
		drop,
	},
	(connect, monitor) => ({
		canDrop: monitor.canDrop(),
		connectDropTarget: connect.dropTarget(),
		hover: monitor.isOver(),
	})
)(CriteriaRow);
