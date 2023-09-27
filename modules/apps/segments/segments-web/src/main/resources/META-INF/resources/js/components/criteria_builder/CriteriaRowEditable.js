/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {PropTypes} from 'prop-types';
import React from 'react';

import {
	PROPERTY_TYPES,
	SUPPORTED_OPERATORS,
	SUPPORTED_PROPERTY_TYPES,
} from '../../utils/constants';
import {createNewGroup, getSupportedOperatorsFromType} from '../../utils/utils';
import BooleanInput from '../inputs/BooleanInput';
import CollectionInput from '../inputs/CollectionInput';
import DateTimeInput from '../inputs/DateTimeInput';
import DecimalInput from '../inputs/DecimalInput';
import IntegerInput from '../inputs/IntegerInput';
import SelectEntityInput from '../inputs/SelectEntityInput';
import StringInput from '../inputs/StringInput';

export default function CriteriaRowEditable({
	connectDragSource,
	criterion = {},
	error,
	index,
	onAdd,
	onChange,
	onDelete,
	renderEmptyValuesErrors,
	selectedOperator,
	selectedProperty,
}) {
	const _handleDelete = (event) => {
		event.preventDefault();

		onDelete(index);
	};

	const _handleDuplicate = (event) => {
		event.preventDefault();

		onAdd(index + 1, criterion);
	};

	const _handleInputChange = (propertyName) => (event) => {
		onChange({
			...criterion,
			[propertyName]: event.target.value,
		});
	};

	/**
	 * Updates the criteria with a criterion value change. The param 'value'
	 * will only be an array when selecting multiple entities (see
	 * {@link SelectEntityInput.es.js}). And in the case of an array, a new
	 * group with multiple criterion rows will be created.
	 * @param {Array|object} value The properties or list of objects with
	 * properties to update.
	 */
	const _handleTypedInputChange = (value) => {
		if (Array.isArray(value)) {
			const items = value.map((item) => ({
				...criterion,
				...item,
			}));

			onChange(createNewGroup(items));
		}
		else {
			onChange({
				...criterion,
				...value,
			});
		}
	};

	const _renderEditableProperty = ({
		error,
		propertyLabel,
		selectedOperator,
		selectedProperty,
		value,
	}) => {
		const disabledInput = !!error;

		const propertyType = selectedProperty ? selectedProperty.type : '';

		const filteredSupportedOperators = getSupportedOperatorsFromType(
			SUPPORTED_OPERATORS,
			SUPPORTED_PROPERTY_TYPES,
			propertyType
		);

		return (
			<>
				<span className="c-ml-2 criterion-string">
					<b className="font-weight-bold">{propertyLabel}</b>
				</span>

				<ClaySelectWithOption
					aria-label={`${propertyLabel}: ${Liferay.Language.get(
						'select-property-operator-option'
					)}`}
					className="criterion-input operator-input"
					disabled={disabledInput}
					onChange={_handleInputChange('operatorName')}
					options={filteredSupportedOperators.map(
						({label, name}) => ({
							label,
							value: name,
						})
					)}
					value={selectedOperator && selectedOperator.name}
				/>

				{_renderValueInput(
					disabledInput,
					propertyLabel,
					renderEmptyValuesErrors,
					selectedProperty,
					value
				)}
			</>
		);
	};

	const _renderValueInput = (
		disabled,
		propertyLabel,
		renderEmptyValuesErrors,
		selectedProperty,
		value
	) => {
		const inputComponentsMap = {
			[PROPERTY_TYPES.BOOLEAN]: BooleanInput,
			[PROPERTY_TYPES.COLLECTION]: CollectionInput,
			[PROPERTY_TYPES.DATE]: DateTimeInput,
			[PROPERTY_TYPES.DATE_TIME]: DateTimeInput,
			[PROPERTY_TYPES.DOUBLE]: DecimalInput,
			[PROPERTY_TYPES.ID]: SelectEntityInput,
			[PROPERTY_TYPES.INTEGER]: IntegerInput,
			[PROPERTY_TYPES.STRING]: StringInput,
		};

		const InputComponent =
			inputComponentsMap[selectedProperty.type] ||
			inputComponentsMap[PROPERTY_TYPES.STRING];

		return (
			<InputComponent
				disabled={disabled}
				displayValue={criterion.displayValue || ''}
				onChange={_handleTypedInputChange}
				options={selectedProperty.options}
				propertyLabel={propertyLabel}
				propertyType={selectedProperty.type}
				renderEmptyValueErrors={renderEmptyValuesErrors}
				selectEntity={selectedProperty.selectEntity}
				value={value}
			/>
		);
	};

	const value = criterion.value;

	const propertyLabel = selectedProperty ? selectedProperty.label : '';

	return (
		<div className="edit-container">
			{connectDragSource(
				<div className="drag-icon">
					<ClayIcon symbol="drag" />
				</div>
			)}

			{_renderEditableProperty({
				error,
				propertyLabel,
				selectedOperator,
				selectedProperty,
				value,
			})}

			{error ? (
				<ClayButton
					className="btn-outline-danger btn-sm"
					displayType=""
					onClick={_handleDelete}
				>
					{Liferay.Language.get('delete-segment-property')}
				</ClayButton>
			) : (
				<>
					<ClayButton
						aria-label={Liferay.Language.get(
							'duplicate-segment-property'
						)}
						className="btn-outline-borderless btn-sm c-mr-1"
						displayType="secondary"
						monospaced
						onClick={_handleDuplicate}
						title={Liferay.Language.get(
							'duplicate-segment-property'
						)}
					>
						<ClayIcon symbol="paste" />
					</ClayButton>

					<ClayButton
						aria-label={Liferay.Language.get(
							'delete-segment-property'
						)}
						className="btn-outline-borderless btn-sm"
						displayType="secondary"
						monospaced
						onClick={_handleDelete}
						title={Liferay.Language.get('delete-segment-property')}
					>
						<ClayIcon symbol="times-circle" />
					</ClayButton>
				</>
			)}
		</div>
	);
}

CriteriaRowEditable.propTypes = {
	connectDragSource: PropTypes.func,
	criterion: PropTypes.object.isRequired,
	error: PropTypes.bool,
	index: PropTypes.number.isRequired,
	onAdd: PropTypes.func.isRequired,
	onChange: PropTypes.func.isRequired,
	onDelete: PropTypes.func.isRequired,
	renderEmptyValuesErrors: PropTypes.bool,
	selectedOperator: PropTypes.object,
	selectedProperty: PropTypes.object.isRequired,
};
