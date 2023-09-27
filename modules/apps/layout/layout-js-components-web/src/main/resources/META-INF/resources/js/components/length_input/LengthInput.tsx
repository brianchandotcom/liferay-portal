/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {
	KeyboardEvent,
	useEffect,
	useMemo,
	useRef,
	useState,
} from 'react';

import useControlledState from '../../hooks/useControlledState';
import isValidStyleValue from '../../utils/isValidStyleValue';
import {Field} from '../color_picker/ColorPicker';

import './LengthInput.scss';

const CUSTOM = 'custom' as const;

const KEYS_NOT_ALLOWED = new Set(['+', ',', 'e']);

// Try to parse a value
// 1st group: a number, a number with decimal and a decimal without integer part
// 2nd group: a specified unit (px, em, vh, vw, rem, %)

const REGEX = /^(-?(?:[\d]*\.?[\d]+))(px|em|vh|vw|rem|%)$/;

const UNITS = ['px', '%', 'em', 'rem', 'vw', 'vh', CUSTOM] as const;

type Unit = typeof UNITS[number];
type Value = {unit: Unit; value: number | string};

const isUnit = (unit: string): unit is Unit => {
	return UNITS.includes(unit as Unit);
};

const getInitialValue = (value: string | undefined): Value => {
	if (!value) {
		return {unit: UNITS[0], value: ''};
	}

	const match = value.toString().toLowerCase().match(REGEX);

	if (match) {
		const [, number, unit] = match;

		if (!isUnit(unit)) {
			throw new Error(`Invalid unit "${unit}"`);
		}

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

interface Props {
	className?: string;
	defaultUnit?: Unit;
	field: Field;
	onEnter?: () => {};
	onValueSelect: (fieldName: string, value: string) => void;
	showLabel?: boolean;
	value?: string;
}

export default function LengthInput({
	className,
	defaultUnit,
	field,
	onEnter,
	onValueSelect,
	showLabel = true,
	value,
}: Props) {
	const [active, setActive] = useState(false);
	const [error, setError] = useState(false);
	const inputId = useId();
	const inputRef = useRef<HTMLInputElement>(null);

	const initialValue = useMemo(() => getInitialValue(value), [value]);

	const [nextValue, setNextValue] = useControlledState(initialValue.value);
	const [nextUnit, setNextUnit] = useState(initialValue.unit);
	const triggerId = useId();

	const handleUnitSelect = (unit: Unit) => {
		setActive(false);
		setNextUnit(unit);

		document.getElementById(triggerId)!.focus();

		if (!nextValue || unit === nextUnit) {
			return;
		}

		let valueWithUnits = `${nextValue}${unit}`;

		if (unit === CUSTOM) {
			inputRef.current!.focus();

			setNextValue('');

			return;
		}
		else if (typeof nextValue !== 'number' || isNaN(nextValue)) {
			valueWithUnits = '';

			inputRef.current!.focus();

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
		const match = nextValue.toString().toLowerCase().match(REGEX);
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
				!isValidStyleValue(
					field.cssProperty || '',
					valueWithUnits.toString()
				))
		) {
			const [, number, unit] = value?.toLowerCase().match(REGEX) || [];

			setNextValue(number || value || '');
			setNextUnit(isUnit(unit) ? unit : CUSTOM);
			setError(true);

			setTimeout(() => setError(false), 1000);

			return;
		}

		if (valueWithUnits !== value) {
			onValueSelect(field.name, valueWithUnits.toString());
		}
	};

	const handleKeyUp = (event: KeyboardEvent) => {
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

		const [, , unit] = value.toString().toLowerCase().match(REGEX) || [];

		setNextUnit(isUnit(unit) ? unit : CUSTOM);
	}, [value]);

	return (
		<ClayForm.Group
			className={classNames(className, 'layout__length-input')}
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
						onBlur={() => {
							if (nextValue !== value) {
								handleValueSelect();
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
								className="layout__input-with-icon__label-icon mb-0 pl-1 pr-3 text-center"
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
							className: 'layout__length-input__dropdown',
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
								className="layout__length-input__button p-1"
								disabled={Boolean(defaultUnit)}
								displayType="secondary"
								id={triggerId}
								size="sm"
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
