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
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayCheckbox, ClaySelectWithOption} from '@clayui/form';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import useControlledState from '../../../core/hooks/useControlledState';
import {useStyleBook} from '../../../plugins/page-design-options/hooks/useStyleBook';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import isNullOrUndefined from '../../utils/isNullOrUndefined';
import {useId} from '../../utils/useId';
import {AdvancedSelectField} from './AdvancedSelectField';

export function SelectField({
	className,
	disabled,
	field,
	onValueSelect,
	value,
}) {
	const {tokenValues} = useStyleBook();

	const validValues = field.typeOptions?.validValues || field.validValues;

	const multiSelect = field.typeOptions?.multiSelect ?? false;

	const defaultValue = isNullOrUndefined(value) ? field.defaultValue : value;

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

	const getOptions = (options) => {
		return options.map((option) =>
			option.frontendTokenName ? getFrontendTokenOption(option) : option
		);
	};

	return (
		<ClayForm.Group className={className} small>
			{multiSelect ? (
				<MultiSelect
					disabled={disabled}
					field={field}
					onValueSelect={onValueSelect}
					options={getOptions(validValues)}
					value={
						defaultValue
							? Array.isArray(value)
								? defaultValue
								: [defaultValue]
							: []
					}
				/>
			) : field.icon && Liferay.FeatureFlags['LPS-143206'] ? (
				<AdvancedSelectField
					disabled={disabled}
					field={field}
					onValueSelect={onValueSelect}
					options={getOptions(validValues)}
					tokenValues={tokenValues}
					value={
						isNullOrUndefined(value) ? field.defaultValue : value
					}
				/>
			) : (
				<SingleSelect
					disabled={disabled}
					field={field}
					onValueSelect={onValueSelect}
					options={getOptions(validValues)}
					value={
						isNullOrUndefined(value) ? field.defaultValue : value
					}
				/>
			)}
		</ClayForm.Group>
	);
}

const MultiSelect = ({
	disabled,
	field,
	inputId,
	onValueSelect,
	options,
	value,
}) => {
	const helpTextId = useId();
	const labelId = useId();

	const [nextValue, setNextValue] = useControlledState(value);

	let label = Liferay.Language.get('select');

	if (nextValue.length === 1) {
		const [selectedValue] = nextValue;

		label =
			options.find((option) => selectedValue === option.value)?.label ||
			label;
	}
	else if (nextValue.length > 1) {
		label = Liferay.Util.sub(
			Liferay.Language.get('x-selected'),
			nextValue.length
		);
	}

	const items = options.map((option) => {
		return {
			...option,
			checked:
				Array.isArray(value) &&
				value.some((item) => item === option.value),
			onChange: (selected) => {
				const changedValue = selected
					? [...nextValue, option.value]
					: nextValue.filter((item) => item !== option.value);

				setNextValue(changedValue);
				onValueSelect(
					field.name,
					changedValue.length ? changedValue : null
				);
			},
			type: 'checkbox',
		};
	});

	const [active, setActive] = useState(false);

	return (
		<>
			<label
				className={classNames({'sr-only': field.hideLabel})}
				id={labelId}
			>
				{field.label}
			</label>

			<ClayDropDown
				active={active}
				disabled={!!disabled}
				id={inputId}
				onActiveChange={setActive}
				trigger={
					<ClayButton
						aria-describedby={helpTextId}
						aria-labelledby={labelId}
						className="form-control-select form-control-sm text-left w-100"
						displayType="secondary"
						small
					>
						{label}
					</ClayButton>
				}
			>
				{items.map(({checked, label, onChange}) => (
					<ClayDropDown.Section key={label}>
						<ClayCheckbox
							checked={checked}
							label={label}
							onChange={() => onChange(!checked)}
						/>
					</ClayDropDown.Section>
				))}
			</ClayDropDown>

			{field.description ? (
				<div className="mt-1 small text-secondary" id={helpTextId}>
					{field.description}
				</div>
			) : null}
		</>
	);
};

const SingleSelect = ({disabled, field, onValueSelect, options, value}) => {
	const helpTextId = useId();
	const inputId = useId();

	const [nextValue, setNextValue] = useControlledState(value);

	return (
		<>
			<label
				className={classNames({'sr-only': field.hideLabel})}
				htmlFor={inputId}
			>
				{field.label}
			</label>

			<ClaySelectWithOption
				aria-describedby={helpTextId}
				disabled={!!disabled}
				id={inputId}
				onChange={(event) => {
					const nextValue =
						event.target.options[event.target.selectedIndex].value;

					setNextValue(nextValue);
					onValueSelect(field.name, nextValue);
				}}
				options={options}
				value={nextValue}
			/>

			{field.description ? (
				<div className="mt-1 small text-secondary" id={helpTextId}>
					{field.description}
				</div>
			) : null}
		</>
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
	value: PropTypes.oneOfType([
		PropTypes.number,
		PropTypes.string,
		PropTypes.arrayOf(PropTypes.string),
	]),
};
