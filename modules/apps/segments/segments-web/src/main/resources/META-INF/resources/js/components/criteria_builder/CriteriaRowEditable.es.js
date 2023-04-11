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

import ClayButton from '@clayui/button';
import {ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {PropTypes} from 'prop-types';
import React, {Component} from 'react';

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
import SelectEntityInput from '../inputs/SelectEntityInput.es';
import StringInput from '../inputs/StringInput';

class CriteriaRowEditable extends Component {
	static propTypes = {
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

	static defaultProps = {
		criterion: {},
	};

	_handleDelete = (event) => {
		event.preventDefault();

		const {index, onDelete} = this.props;

		onDelete(index);
	};

	_handleDuplicate = (event) => {
		event.preventDefault();

		const {criterion, index, onAdd} = this.props;

		onAdd(index + 1, criterion);
	};

	_handleInputChange = (propertyName) => (event) => {
		const {criterion, onChange} = this.props;

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
	_handleTypedInputChange = (value) => {
		const {criterion, onChange} = this.props;

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

	_renderEditableProperty = ({
		error,
		propertyLabel,
		selectedOperator,
		selectedProperty,
		value,
	}) => {
		const disabledInput = !!error;

		const renderEmptyValuesErrors = this.props.renderEmptyValuesErrors;

		const propertyType = selectedProperty ? selectedProperty.type : '';

		const filteredSupportedOperators = getSupportedOperatorsFromType(
			SUPPORTED_OPERATORS,
			SUPPORTED_PROPERTY_TYPES,
			propertyType
		);

		return (
			<>
				<span className="criterion-string">
					<b>{propertyLabel}</b>
				</span>

				<ClaySelectWithOption
					aria-label={`${propertyLabel}: ${Liferay.Language.get(
						'select-property-operator-option'
					)}`}
					className="criterion-input form-control operator-input"
					disabled={disabledInput}
					onChange={this._handleInputChange('operatorName')}
					options={filteredSupportedOperators.map(
						({label, name}) => ({
							label,
							value: name,
						})
					)}
					value={selectedOperator && selectedOperator.name}
				/>

				{this._renderValueInput(
					disabledInput,
					propertyLabel,
					renderEmptyValuesErrors,
					selectedProperty,
					value
				)}
			</>
		);
	};

	_renderValueInput = (
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
				displayValue={this.props.criterion.displayValue || ''}
				onChange={this._handleTypedInputChange}
				options={selectedProperty.options}
				propertyLabel={propertyLabel}
				propertyType={selectedProperty.type}
				renderEmptyValueErrors={renderEmptyValuesErrors}
				selectEntity={selectedProperty.selectEntity}
				value={value}
			/>
		);
	};

	render() {
		const {
			connectDragSource,
			criterion,
			error,
			selectedOperator,
			selectedProperty,
		} = this.props;

		const value = criterion.value;

		const propertyLabel = selectedProperty ? selectedProperty.label : '';

		return (
			<div className="edit-container">
				{connectDragSource(
					<div className="drag-icon">
						<ClayIcon symbol="drag" />
					</div>
				)}

				{this._renderEditableProperty({
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
						onClick={this._handleDelete}
					>
						{Liferay.Language.get('delete-segment-property')}
					</ClayButton>
				) : (
					<>
						<ClayButton
							aria-label={Liferay.Language.get(
								'duplicate-segment-property'
							)}
							className="btn-outline-borderless btn-sm mr-1"
							displayType="secondary"
							monospaced
							onClick={this._handleDuplicate}
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
							onClick={this._handleDelete}
							title={Liferay.Language.get(
								'delete-segment-property'
							)}
						>
							<ClayIcon symbol="times-circle" />
						</ClayButton>
					</>
				)}
			</div>
		);
	}
}

export default CriteriaRowEditable;
