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
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useRef, useState} from 'react';

import {VIEWPORT_SIZES} from '../../app/config/constants/viewportSizes';
import {useSelector} from '../../app/contexts/StoreContext';
import {getResetLabelByViewport} from '../../app/utils/getResetLabelByViewport';
import isValidStyleValue from '../../app/utils/isValidStyleValue';
import useControlledState from '../../core/hooks/useControlledState';
import {useId} from '../../core/hooks/useId';
import {ConfigurationFieldPropTypes} from '../../prop-types/index';

import './LengthField.scss';

const CUSTOM = 'custom';

const KEYS_NOT_ALLOWED = new Set(['+', ',', 'e']);

const RESTORABLE_FIELDS = new Set(['opacity', 'borderWidth']);

// Try to parse a value
// 1st group: a number, a number with decimal and a decimal without integer part
// 2nd group: a specified unit (px, em, vh, vw, rem, %)

const REGEX = /^(-?(?:[\d]*\.?[\d]+))(px|em|vh|vw|rem|%)$/;

const UNITS = ['px', '%', 'em', 'rem', 'vw', 'vh', CUSTOM];

const getInitialValue = (value) => {
	if (!value) {
		return {unit: UNITS[0], value: ''};
	}

	const match = value.toLowerCase().match(REGEX);

	if (match) {
		const [, number, unit] = match;

		return {
			unit,
			value: number,
		};
	}

	return {
		unit: CUSTOM,
		value,
	};
};

export function LengthField({field, onEnter, onValueSelect, value}) {
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);
	const [showRestoreButton, setShowRestoreButton] = useState(
		RESTORABLE_FIELDS.has(field.name) && value !== field.defaultValue
	);

	return (
		<div className="align-items-center d-flex page-editor__length-field">
			<LengthInput
				className={field.icon ? 'mb-0' : null}
				defaultUnit={field.typeOptions?.defaultUnit}
				field={field}
				onEnter={onEnter}
				onValueSelect={(name, value) => {
					if (RESTORABLE_FIELDS.has(field.name)) {
						setShowRestoreButton(true);
					}

					onValueSelect(name, value);
				}}
				showLabel={!field.icon}
				value={value}
			/>

			{showRestoreButton ? (
				<ClayButtonWithIcon
					className="border-0 flex-shrink-0 mb-0 ml-2"
					displayType="secondary"
					onClick={() => {
						setShowRestoreButton(
							selectedViewportSize !== VIEWPORT_SIZES.desktop
						);
						onValueSelect(field.name, null);
					}}
					small
					symbol="restore"
					title={getResetLabelByViewport(selectedViewportSize)}
				/>
			) : null}
		</div>
	);
}

LengthField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onEnter: PropTypes.func,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
};

export function LengthInput({
	className,
	defaultUnit,
	field,
	onEnter,
	onValueSelect,
	showLabel = true,
	value,
}) {
	const [active, setActive] = useState(false);
	const [error, setError] = useState(false);
	const inputId = useId();
	const inputRef = useRef();

	const initialValue = useMemo(() => getInitialValue(value), [value]);

	const [nextValue, setNextValue] = useControlledState(initialValue.value);
	const [nextUnit, setNextUnit] = useState(initialValue.unit);
	const triggerId = useId();

	const handleUnitSelect = (unit) => {
		setActive(false);
		setNextUnit(unit);

		document.getElementById(triggerId)?.focus();

		if (!nextValue || unit === nextUnit) {
			return;
		}

		let valueWithUnits = `${nextValue}${unit}`;

		if (unit === CUSTOM) {
			inputRef.current.focus();

			setNextValue('');

			return;
		}
		else if (isNaN(nextValue)) {
			valueWithUnits = '';

			inputRef.current.focus();

			if (field.typeOptions?.showLengthField) {
				setNextValue(valueWithUnits);

				return;
			}
		}

		if (valueWithUnits !== value) {
			onValueSelect(field.name, valueWithUnits);
		}
	};

	const handleValueSelect = () => {
		const match = nextValue.toLowerCase().match(REGEX);
		let valueWithUnits = nextValue;

		if (match) {
			const [, number, unit] = match;

			valueWithUnits = `${number}${unit}`;

			setNextValue(number);
		}
		else if (nextUnit !== CUSTOM && nextValue) {
			valueWithUnits = `${nextValue}${nextUnit}`;
		}

		if (
			field.typeOptions?.showLengthField &&
			(!valueWithUnits ||
				!isValidStyleValue(field.cssProperty, valueWithUnits))
		) {
			const [, number, unit] = value.toLowerCase().match(REGEX) || [];

			setNextValue(number || value);
			setNextUnit(unit || CUSTOM);
			setError(true);

			setTimeout(() => setError(false), 1000);

			return;
		}

		if (valueWithUnits !== value) {
			onValueSelect(field.name, valueWithUnits);
		}
	};

	const handleKeyUp = (event) => {
		if (nextUnit !== CUSTOM && KEYS_NOT_ALLOWED.has(event.key)) {
			event.preventDefault();
		}

		if (event.key === 'Enter') {
			if (onEnter) {
				onEnter();
			}

			handleValueSelect();
		}
	};

	useEffect(() => {
		if (!value) {
			return;
		}

		const [, , unit] = value.toLowerCase().match(REGEX) || [];

		setNextUnit(unit || CUSTOM);
	}, [value]);

	return (
		<ClayForm.Group
			className={classNames(className, 'page-editor__length-input')}
		>
			<label
				className={classNames({'sr-only': !showLabel})}
				htmlFor={inputId}
			>
				{field.label}
			</label>

			<ClayInput.Group>
				<ClayInput.GroupItem prepend>
					<ClayInput
						aria-label={field.label}
						id={inputId}
						insetBefore={Boolean(field.icon)}
						onBlur={(event) => {
							if (nextValue !== value) {
								handleValueSelect(event);
							}
						}}
						onChange={(event) => {
							setNextValue(event.target.value);
						}}
						onKeyUp={handleKeyUp}
						ref={inputRef}
						sizing="sm"
						type={
							!defaultUnit && nextUnit === CUSTOM
								? 'text'
								: 'number'
						}
						value={nextValue}
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
									symbol={field.icon}
								/>

								<span className="sr-only">{field.label}</span>
							</label>
						</ClayInput.GroupInsetItem>
					) : null}
				</ClayInput.GroupItem>

				<ClayInput.GroupItem append shrink>
					<ClayDropDown
						active={active}
						alignmentPosition={Align.BottomRight}
						menuElementAttrs={{
							className: 'page-editor__length-input__dropdown',
							containerProps: {
								className: 'cadmin',
							},
						}}
						onActiveChange={setActive}
						renderMenuOnClick
						trigger={
							<ClayButton
								aria-expanded={active}
								aria-haspopup="true"
								aria-label={sub(
									Liferay.Language.get('select-a-unit'),
									nextUnit
								)}
								className="p-1 page-editor__length-input__button"
								disabled={defaultUnit}
								displayType="secondary"
								id={triggerId}
								small
								title={Liferay.Language.get('select-units')}
							>
								{defaultUnit ||
									(nextUnit === CUSTOM ? (
										<ClayIcon symbol="code" />
									) : (
										nextUnit.toUpperCase()
									))}
							</ClayButton>
						}
					>
						<ClayDropDown.ItemList aria-labelledby={triggerId}>
							{UNITS.map((unit) => (
								<ClayDropDown.Item
									key={unit}
									onClick={() => handleUnitSelect(unit)}
								>
									{unit.toUpperCase()}
								</ClayDropDown.Item>
							))}
						</ClayDropDown.ItemList>
					</ClayDropDown>
				</ClayInput.GroupItem>

				{error ? (
					<span aria-live="assertive" className="sr-only">
						{Liferay.Language.get(
							'this-field-requires-a-valid-style-value'
						)}
					</span>
				) : null}
			</ClayInput.Group>
		</ClayForm.Group>
	);
}

LengthInput.propTypes = {
	className: PropTypes.string,
	defaultUnit: PropTypes.string,
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onEnter: PropTypes.func,
	onValueSelect: PropTypes.func.isRequired,
	showLabel: PropTypes.bool,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
};
