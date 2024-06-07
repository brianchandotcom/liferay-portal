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

type Unit = (typeof UNITS)[number];
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
		const [, nextNumber, nextUnit] = match;

		if (!isUnit(nextUnit)) {
			throw new Error(`Invalid unit "${nextUnit}"`);
		}

		return {
			unit: nextUnit,
			value: nextNumber,
		};
	}

	return {
		unit: CUSTOM,
		value,
	};
};

const getNextValue = (value: number | string | undefined, unit: Unit) => {
	const [, nextNumber, nextUnit] =
		value?.toString().toLowerCase().match(REGEX) || [];

	return {
		nextNumber: nextNumber || value || '',
		nextUnit: isUnit(nextUnit) ? nextUnit : unit,
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
	value: currentValue,
}: Props) {
	const [active, setActive] = useState(false);
	const [error, setError] = useState(false);
	const inputId = useId();
	const inputRef = useRef<HTMLInputElement>(null);

	const initialValue = useMemo(
		() => getInitialValue(currentValue),
		[currentValue]
	);

	const [value, setValue] = useControlledState(initialValue.value);
	const [unit, setUnit] = useState(initialValue.unit);
	const triggerId = useId();

	const onSelectUnit = (selectedUnit: Unit) => {
		setActive(false);
		setUnit(selectedUnit);

		document.getElementById(triggerId)!.focus();

		if (!value || selectedUnit === unit) {
			return;
		}

		let valueWithUnits = `${value}${selectedUnit}`;

		if (selectedUnit === CUSTOM) {
			inputRef.current!.focus();

			setValue('');

			return;
		}
		else if (typeof value !== 'number' || isNaN(value)) {
			valueWithUnits = '';

			inputRef.current!.focus();

			if (field.typeOptions?.showLengthField) {
				setValue(valueWithUnits);

				return;
			}
		}

		if (valueWithUnits !== currentValue) {
			onValueSelect(field.name, valueWithUnits);
		}
	};

	const handleValueSelect = () => {
		if (value === currentValue && unit !== CUSTOM) {
			return;
		}

		const match = value.toString().toLowerCase().match(REGEX);
		let valueWithUnits = value;

		if (match) {
			const [, nextNumber, nextUnit] = match;

			valueWithUnits = `${nextNumber}${nextUnit}`;

			setValue(nextNumber);
			setUnit(nextUnit as Unit);
		}
		else if (unit !== CUSTOM && value) {
			valueWithUnits = `${value}${unit}`;
		}

		if (
			field.typeOptions?.showLengthField &&
			(!valueWithUnits ||
				!isValidStyleValue(
					field.cssProperty || '',
					valueWithUnits.toString()
				))
		) {
			const {nextNumber, nextUnit} = getNextValue(currentValue, unit);

			setValue(nextNumber);
			setUnit(nextUnit);
			setError(true);

			setTimeout(() => setError(false), 1000);

			return;
		}

		if (valueWithUnits !== currentValue) {
			onValueSelect(field.name, valueWithUnits.toString());
		}
	};

	const handleKeyUp = (event: KeyboardEvent) => {
		if (unit !== CUSTOM && KEYS_NOT_ALLOWED.has(event.key)) {
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
		if (!currentValue) {
			return;
		}

		setUnit((previousUnit) => {
			const {nextUnit} = getNextValue(currentValue, previousUnit);

			return nextUnit;
		});
	}, [currentValue]);

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
						onBlur={() => handleValueSelect()}
						onChange={(event) => {
							setValue(event.target.value);
						}}
						onKeyUp={handleKeyUp}
						ref={inputRef}
						sizing="sm"
						type={
							!defaultUnit && unit === CUSTOM ? 'text' : 'number'
						}
						value={value}
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
									unit
								)}
								className="layout__length-input__button p-1"
								disabled={Boolean(defaultUnit)}
								displayType="secondary"
								id={triggerId}
								size="sm"
								title={Liferay.Language.get('select-units')}
							>
								{defaultUnit ||
									(unit === CUSTOM ? (
										<ClayIcon symbol="code" />
									) : (
										unit.toUpperCase()
									))}
							</ClayButton>
						}
					>
						<ClayDropDown.ItemList aria-labelledby={triggerId}>
							{UNITS.map((unit) => (
								<ClayDropDown.Item
									key={unit}
									onClick={() => onSelectUnit(unit)}
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
