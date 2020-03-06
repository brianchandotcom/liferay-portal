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

import {ConfigurationFieldPropTypes} from '../../../prop-types/index';

export const TextField = ({field, onValueSelect, value}) => {
	const [currentValue, setCurrentValue] = useState(value);
	const [errorMessage, setErrorMessage] = useState('');

	const additionalAttributes = {
		...(field.typeOptions && field.typeOptions.validation
			? field.typeOptions.validation
			: {}),
		errorMessage: undefined,
		regexp: undefined,
	};

	const fieldType =
		field.typeOptions && field.typeOptions.validation
			? field.typeOptions.validation.type
			: 'text';

	if (fieldType === 'pattern') {
		additionalAttributes.type = 'text';
		additionalAttributes.pattern =
			field.typeOptions && field.typeOptions.validation
				? field.typeOptions.validation.regexp
				: '.*?';
	}

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

						onValueSelect(field.name, event.target.value);
					}
					else {
						setErrorMessage(
							field.typeOptions && field.typeOptions.validation
								? field.typeOptions.validation.errorMessage ||
										Liferay.Language.get(
											'you-have-entered-invalid-data'
										)
								: ''
						);
					}

					setCurrentValue(event.target.value);
				}}
				placeholder={
					field.typeOptions ? field.typeOptions.placeholder : ''
				}
				sizing="sm"
				type={fieldType}
				value={currentValue || value || field.defaultValue}
				{...additionalAttributes}
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

TextField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.string,
};
