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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayForm, {
	ClayCheckbox,
	ClayInput,
	ClaySelectWithOption,
} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useState} from 'react';

import useControlledState from '../../../core/hooks/useControlledState';
import {useStyleBook} from '../../../plugins/page-design-options/hooks/useStyleBook';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {useActiveItemId} from '../../contexts/ControlsContext';
import {useGlobalContext} from '../../contexts/GlobalContext';
import {useSelector} from '../../contexts/StoreContext';
import selectCanDetachTokenValues from '../../selectors/selectCanDetachTokenValues';
import getLayoutDataItemUniqueClassName from '../../utils/getLayoutDataItemUniqueClassName';
import isNullOrUndefined from '../../utils/isNullOrUndefined';
import {useId} from '../../utils/useId';

export function SelectField({
	className,
	disabled,
	field,
	onValueSelect,
	value,
}) {
	const {tokenValues} = useStyleBook();

	const validValues = field.typeOptions
		? field.typeOptions.validValues
		: field.validValues;

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

const SingleSelectWithIcon = ({
	disabled,
	field,
	helpTextId,
	onChange,
	options,
	value,
}) => {
	const activeItemId = useActiveItemId();
	const globalContext = useGlobalContext();
	const inputId = useId();

	const [defaultOptionComputedValue, setDefaultComputedValue] = useState('');

	const defaultOptionLabel = useMemo(
		() =>
			options.find((option) => option.value === field.defaultValue).label,
		[field.defaultValue, options]
	);

	const selectedOptionLabel = useMemo(() => {
		if (value === field.defaultValue) {
			if (defaultOptionComputedValue) {
				return `${defaultOptionLabel} · ${defaultOptionComputedValue}`;
			}

			return defaultOptionLabel;
		}

		return (
			options.find((option) => option.value === value)?.label ||
			defaultOptionLabel
		);
	}, [
		defaultOptionComputedValue,
		defaultOptionLabel,
		field.defaultValue,
		options,
		value,
	]);

	useEffect(() => {
		if (!field.cssProperty) {
			return;
		}

		const element = globalContext.document.querySelector(
			`.${getLayoutDataItemUniqueClassName(activeItemId)}`
		);

		if (!element) {
			return;
		}

		setDefaultComputedValue(
			globalContext.window
				.getComputedStyle(element)
				.getPropertyValue(field.cssProperty)
		);
	}, [activeItemId, field.cssProperty, globalContext]);

	return (
		<div className="btn btn-unstyled m-0 p-0 page-editor__single-select-with-icon">
			<label
				className="mb-0 page-editor__single-select-with-icon__label-icon px-1 py-2 text-center"
				htmlFor={inputId}
			>
				<ClayIcon
					className="lfr-portal-tooltip"
					data-title={field.label}
					symbol={field.icon}
				/>

				<span className="sr-only">{field.label}</span>
			</label>

			<ClaySelectWithOption
				aria-describedby={helpTextId}
				className="page-editor__single-select-with-icon__select"
				disabled={Boolean(disabled)}
				id={inputId}
				onChange={onChange}
				options={options}
				value={value}
			/>

			<div
				className={classNames(
					'page-editor__single-select-with-icon__label pl-2 pr-3 py-2 text-truncate w-100',
					{disabled}
				)}
				role="presentation"
			>
				<span>{selectedOptionLabel}</span>
			</div>
		</div>
	);
};

const AdvancedSelectField = ({
	disabled,
	field,
	onValueSelect,
	options,
	tokenValues,
	value,
}) => {
	const canDetachTokenValues = useSelector(selectCanDetachTokenValues);
	const helpTextId = useId();
	const triggerId = useId();

	const [active, setActive] = useState(false);
	const [isTokenValue, setIsTokenValue] = useState(
		Boolean(tokenValues[value])
	);
	const [nextValue, setNextValue] = useControlledState(value);

	const handleSelectChange = (event) => {
		const nextValue =
			event.target.options[event.target.selectedIndex].value;

		setNextValue(nextValue);
		onValueSelect(field.name, nextValue);
	};

	return (
		<div
			className={classNames('page-editor__select-field', {
				'has-value': value,
			})}
		>
			{isTokenValue ? (
				<SingleSelectWithIcon
					disabled={disabled}
					field={field}
					helpTextId={helpTextId}
					onChange={handleSelectChange}
					options={options}
					value={nextValue}
				/>
			) : (
				<InputWithIcon
					field={field}
					onBlur={(event) => {
						if (!event.target.value) {
							return;
						}

						onValueSelect(field.name, event.target.value);
					}}
					onChange={(event) => {
						setNextValue(event.target.value);
					}}
					value={nextValue}
				/>
			)}

			{value ? (
				isTokenValue && canDetachTokenValues ? (
					<ClayButtonWithIcon
						className="border-0 ml-1"
						displayType="secondary"
						onClick={() => {
							setIsTokenValue(false);
							setNextValue(tokenValues[value].value);
							onValueSelect(field.name, tokenValues[value].value);
						}}
						small
						symbol="chain-broken"
						title={Liferay.Language.get('detach-token')}
					/>
				) : (
					<ClayDropDown
						active={active}
						alignmentPosition={Align.BottomRight}
						menuElementAttrs={{
							containerProps: {
								className: 'cadmin',
							},
						}}
						onActiveChange={setActive}
						trigger={
							<ClayButtonWithIcon
								aria-expanded={active}
								aria-haspopup="true"
								className="border-0 ml-1"
								displayType="secondary"
								id={triggerId}
								small
								symbol="theme"
								title={Liferay.Language.get(
									'value-from-stylebook'
								)}
							/>
						}
					>
						<ClayDropDown.ItemList aria-labelledby={triggerId}>
							{options.map(({label, value}) => {
								if (!value) {
									return;
								}

								return (
									<ClayDropDown.Item
										key={value}
										onClick={() => {
											setActive(false);
											setIsTokenValue(true);
											setNextValue(value);
											onValueSelect(field.name, value);
										}}
									>
										{label}
									</ClayDropDown.Item>
								);
							})}
						</ClayDropDown.ItemList>
					</ClayDropDown>
				)
			) : null}

			{field.description ? (
				<div className="mt-1 small text-secondary" id={helpTextId}>
					{field.description}
				</div>
			) : null}
		</div>
	);
};

const InputWithIcon = ({field, onBlur, onChange, value}) => {
	const inputId = useId();

	return (
		<ClayInput.Group>
			<ClayInput.GroupItem>
				<ClayInput
					aria-label={field.label}
					id={inputId}
					insetBefore={Boolean(field.icon)}
					onBlur={onBlur}
					onChange={onChange}
					sizing="sm"
					value={value}
				/>

				{field.icon ? (
					<ClayInput.GroupInsetItem before>
						<label
							className="mb-0 page-editor__input-with-icon__label-icon pl-1 pr-3 text-center"
							htmlFor={inputId}
						>
							<ClayIcon
								className="lfr-portal-tooltip"
								data-title={field.label}
								small
								symbol={field.icon}
							/>

							<span className="sr-only">{field.label}</span>
						</label>
					</ClayInput.GroupInsetItem>
				) : null}
			</ClayInput.GroupItem>
		</ClayInput.Group>
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
