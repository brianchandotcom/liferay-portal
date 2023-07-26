/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayCheckbox, ClaySelectWithOption} from '@clayui/form';
import {
	isNullOrUndefined,
	useControlledState,
} from '@liferay/layout-js-components-web';
import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {useStyleBook} from '../../../plugins/page_design_options/hooks/useStyleBook';
import {ConfigurationFieldPropTypes} from '../../../prop_types/index';
import {AdvancedSelectField} from './AdvancedSelectField';

export function SelectField({
	className,
	disabled,
	field,
	item,
	onValueSelect,
	value,
}) {
	const {tokenValues} = useStyleBook();

	const validValues = field.typeOptions?.validValues || [];

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
			) : field.icon ? (
				<AdvancedSelectField
					disabled={disabled}
					field={field}
					item={item}
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
		label = sub(Liferay.Language.get('x-selected'), nextValue.length);
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
						aria-describedby={field.description ? helpTextId : null}
						aria-labelledby={labelId}
						className="form-control-select form-control-sm text-left w-100"
						displayType="secondary"
						size="sm"
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
				<p className="m-0 mt-1 small text-secondary" id={helpTextId}>
					{field.description}
				</p>
			) : null}
		</>
	);
};

const SingleSelect = ({disabled, field, onValueSelect, options, value}) => {
	const helpTextId = useId();
	const inputId = useId();

	const [nextValue, setNextValue] = useControlledState(value);

	const isInline = field.typeOptions.inline;

	return (
		<div
			className={classNames({
				'c-gap-2 d-flex align-items-center': isInline,
			})}
		>
			<label
				className={classNames({
					'flex-shrink-0': isInline,
					'sr-only': field.hideLabel,
				})}
				htmlFor={inputId}
			>
				{field.label}
			</label>

			<ClaySelectWithOption
				aria-describedby={field.description ? helpTextId : null}
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
				<p className="m-0 mt-1 small text-secondary" id={helpTextId}>
					{field.description}
				</p>
			) : null}
		</div>
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
			),
		}),
	}),

	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([
		PropTypes.number,
		PropTypes.string,
		PropTypes.arrayOf(PropTypes.string),
	]),
};
