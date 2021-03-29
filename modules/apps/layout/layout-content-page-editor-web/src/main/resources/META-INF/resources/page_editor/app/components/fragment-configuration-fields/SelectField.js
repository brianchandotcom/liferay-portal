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

import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {useStyleBook} from '../../../plugins/page-design-options/hooks/useStyleBook';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {useId} from '../../utils/useId';

export const SelectField = ({
	className,
	disabled,
	field,
	onValueSelect,
	value,
}) => {
	const inputId = useId();
	const {tokenValues} = useStyleBook();

	const validValues = field.typeOptions
		? field.typeOptions.validValues
		: field.validValues;

	const [firstOption = {}] = validValues;

	const [nextValue, setNextValue] = useState(
		value || field.defaultValue || firstOption.value
	);

	const getFrontendTokenOption = (option) => {
		const token = tokenValues[option.frontendTokenName];

		if (!token) {
			return option;
		}

		return {
			label: token.label,
			value: option.frontendTokenName,
		};
	};

	useEffect(() => {
		setNextValue((prevValue) => value || prevValue);
	}, [value]);

	return (
		<ClayForm.Group className={className} small>
			<label htmlFor={inputId}>{field.label}</label>

			<ClaySelectWithOption
				aria-label={field.label}
				disabled={!!disabled}
				id={inputId}
				onChange={(event) => {
					const nextValue =
						event.target.options[event.target.selectedIndex].value;

					setNextValue(nextValue);
					onValueSelect(field.name, nextValue);
				}}
				options={validValues.map((validValue) =>
					validValue.frontendTokenName
						? getFrontendTokenOption(validValue)
						: validValue
				)}
				value={nextValue}
			/>
		</ClayForm.Group>
	);
};

SelectField.propTypes = {
	className: PropTypes.string,
	disabled: PropTypes.bool,

	field: PropTypes.shape({
		...ConfigurationFieldPropTypes,
		typeOptions: PropTypes.shape({
			validValues: PropTypes.arrayOf(
				PropTypes.shape({
					label: PropTypes.string.isRequired,
					value: PropTypes.string.isRequired,
				})
			).isRequired,
		}),
		validValues: PropTypes.arrayOf(
			PropTypes.shape({
				label: PropTypes.string.isRequired,
				value: PropTypes.string.isRequired,
			})
		),
	}),

	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
};
