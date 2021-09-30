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

import ClayForm, {ClayInput} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import useControlledState from '../../../core/hooks/useControlledState';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';

export const TextField = ({field, onValueSelect, value}) => {
	const [errorMessage, setErrorMessage] = useState('');

	const [nextValue, setNextValue] = useControlledState(value);

	const {additionalProps = {}, type = 'text'} = parseTypeOptions(
		field.typeOptions
	);

	return (
		<ClayForm.Group className={errorMessage ? 'has-error' : ''}>
			<label htmlFor={field.name}>{field.label}</label>

			<ClayInput
				id={field.name}
				onBlur={(event) => {
					if (event.target.checkValidity()) {
						setErrorMessage('');

						if (nextValue !== value) {
							onValueSelect(field.name, nextValue);
						}
					}
				}}
				onChange={(event) => {
					if (event.target.validity.valid) {
						setErrorMessage('');
					}
					else {
						const validationErrorMessage =
							(field.typeOptions &&
								field.typeOptions.validation &&
								field.typeOptions.validation.errorMessage) ||
							Liferay.Language.get(
								'you-have-entered-invalid-data'
							);

						setErrorMessage(validationErrorMessage);
					}

					setNextValue(event.target.value);
				}}
				placeholder={
					field.typeOptions ? field.typeOptions.placeholder : ''
				}
				sizing="sm"
				type={type}
				value={nextValue || ''}
				{...additionalProps}
			/>

			{errorMessage && (
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>
						<ClayForm.FeedbackIndicator symbol="exclamation-full" />
						{errorMessage}
					</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			)}
		</ClayForm.Group>
	);
};

function parseTypeOptions(typeOptions = {}) {
	if (!typeOptions.validation) {
		return {type: 'text'};
	}

	const {type: validationType, ...properties} = typeOptions.validation;

	const result = {
		additionalProps: {},
		type: 'text',
	};

	if (!validationType || validationType === 'text') {
		result.type = 'text';

		if (Number.isInteger(properties.minLength)) {
			result.additionalProps.minLength = properties.minLength;
		}

		if (Number.isInteger(properties.maxLength)) {
			result.additionalProps.maxLength = properties.maxLength;
		}
	}

	if (validationType === 'pattern') {
		result.additionalProps = {pattern: typeOptions.validation.regexp};
	}

	if (validationType === 'url' || validationType === 'email') {
		result.type = validationType;

		if (Number.isInteger(properties.minLength)) {
			result.additionalProps.minLength = properties.minLength;
		}

		if (Number.isInteger(properties.maxLength)) {
			result.additionalProps.maxLength = properties.maxLength;
		}
	}

	if (validationType === 'number') {
		result.type = validationType;

		if (Number.isInteger(properties.min)) {
			result.additionalProps.min = properties.min;
		}

		if (Number.isInteger(properties.max)) {
			result.additionalProps.max = properties.max;
		}
	}

	return result;
}

TextField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
};
