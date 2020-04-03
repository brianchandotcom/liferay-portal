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
import {useIsMounted} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {useDebounceCallback} from '../../../core/hooks/useDebounceCallback';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';

export const TextField = ({field, onValueSelect, value}) => {
	const [currentValue, setCurrentValue] = useState(
		value || field.defaultValue || ''
	);
	const [errorMessage, setErrorMessage] = useState('');

	const isMounted = useIsMounted();

	useEffect(() => {
		setCurrentValue(currentValue => {
			if (!currentValue || !value) {
				return value || field.defaultValue || '';
			}

			return currentValue;
		});
	}, [field.defaultValue, value]);

	const {additionalProps = {}, type = 'text'} = parseTypeOptions(
		field.typeOptions
	);

	const selectValue = (target, name, isMounted, onValueSelect) => {
		if (isMounted() && target.validity.valid) {
			onValueSelect(name, target.value);
		}
	};

	const [debouncedOnValueSelect] = useDebounceCallback(selectValue, 500);

	return (
		<ClayForm.Group className={errorMessage ? 'has-error' : ''}>
			<label htmlFor={field.name}>{field.label}</label>

			<ClayInput
				id={field.name}
				onBlur={event => {
					if (event.target.checkValidity()) {
						setErrorMessage('');
					}
				}}
				onChange={event => {
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

					debouncedOnValueSelect(
						event.target,
						field.name,
						isMounted,
						onValueSelect
					);

					setCurrentValue(event.target.value);
				}}
				placeholder={
					field.typeOptions ? field.typeOptions.placeholder : ''
				}
				sizing="sm"
				type={type}
				value={currentValue}
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
	value: PropTypes.string,
};
