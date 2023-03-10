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

import {ClaySelectWithOption} from '@clayui/form';
import {PropTypes} from 'prop-types';
import React, {Component} from 'react';

import {
	HAS_OPERATORS,
	SUPPORTED_EVENT_OPERATORS,
	SUPPORTED_PROPERTY_TYPES,
} from '../../utils/constants';
import {getSupportedOperatorsFromEvent} from '../../utils/utils.es';
import IntegerInput from './IntegerInput';
import SelectEventEntityInput from './SelectEventEntityInput.es';

class EventInput extends Component {
	static propTypes = {
		criterion: PropTypes.object.isRequired,
		error: PropTypes.bool,
		onChange: PropTypes.func.isRequired,
		renderEmptyValuesErrors: PropTypes.bool,
		selectedOperator: PropTypes.object,
		selectedProperty: PropTypes.object.isRequired,
	};

	static defaultProps = {
		criterion: {},
	};

	render() {
		const {
			criterion,
			error,
			onChange,
			onInputChange,
			propertyLabel,
			renderEmptyValuesErrors,
			selectedProperty,
			value,
		} = this.props;

		const disabledInput = !!error;

		const notOperators = getSupportedOperatorsFromEvent(
			SUPPORTED_EVENT_OPERATORS,
			SUPPORTED_PROPERTY_TYPES,
			'NOT'
		);

		const notOperatorKey = criterion.operatorNot
			? HAS_OPERATORS.NOT_HAS
			: HAS_OPERATORS.HAS;

		const integerOperators = getSupportedOperatorsFromEvent(
			SUPPORTED_EVENT_OPERATORS,
			SUPPORTED_PROPERTY_TYPES,
			'INTEGER'
		);

		const integerOperatorLabel = integerOperators.find(
			(operator) => operator.name === criterion.operatorName
		)?.name;

		return (
			<div className="ml-2" style={{flexGrow: 1}}>
				<div className="align-items-center d-flex mb-2">
					<span className="mr-1 text-dark">
						{Liferay.Language.get('user')}
					</span>

					<ClaySelectWithOption
						aria-label={`${propertyLabel}: ${Liferay.Language.get(
							'select-option'
						)}`}
						className="criterion-input form-control operator-input"
						data-testid="select-has-operator"
						disabled={disabledInput}
						onChange={onInputChange('operatorNot')}
						options={notOperators.map(({label, name}) => ({
							label,
							value: name,
						}))}
						value={notOperatorKey}
					/>

					<span className="criterion-string">
						<b>{propertyLabel}</b>
					</span>

					<SelectEventEntityInput
						disabled={disabledInput}
						displayValue={criterion.assetId}
						onChange={onChange}
						propertyLabel={propertyLabel}
						renderEmptyValueErrors={renderEmptyValuesErrors}
						selectEntity={selectedProperty.selectEntity}
					/>
				</div>

				<div className="align-items-center d-flex">
					<ClaySelectWithOption
						aria-label={`${propertyLabel}: ${Liferay.Language.get(
							'select-option'
						)}`}
						className="criterion-input form-control operator-input"
						data-testid="integer-operator"
						disabled={disabledInput}
						onChange={onInputChange('operatorName')}
						options={integerOperators.map(({label, name}) => ({
							label,
							value: name,
						}))}
						value={integerOperatorLabel}
					/>

					<IntegerInput
						className="criterion-input form-control"
						data-testid="integer-number"
						disabled={disabledInput}
						onChange={onChange}
						propertyLabel={propertyLabel}
						type="number"
						value={value}
					/>
				</div>
			</div>
		);
	}
}

export default EventInput;
