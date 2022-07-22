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
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useRef, useState} from 'react';

import {useId} from '../../app/utils/useId';
import useControlledState from '../../core/hooks/useControlledState';
import {ConfigurationFieldPropTypes} from '../../prop-types/index';

import './LengthField.scss';

const CUSTOM = 'custom';

const KEYS_NOT_ALLOWED = ['+', ',', 'e'];

// Try to parse a value
// 1st group: a number, a number with decimal and a decimal without integer part
// 2nd group: a specified unit (px, em, vh, vw, rem, %)

const REGEX = /^(-?(?:[\d]*\.?[\d]+))(px|em|vh|vw|rem|%)$/;

const UNITS = ['px', '%', 'em', 'rem', 'vw', 'vh', CUSTOM];

export function LengthField({field, onValueSelect, value}) {
	const inputId = useId();
	const isCustomRef = useRef(false);

	const initialValue = useMemo(() => {
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
	}, [value]);

	return (
		<ClayForm.Group>
			<label htmlFor={inputId}>{field.label}</label>

			<Field
				field={field}
				id={inputId}
				initialValue={initialValue}
				isCustomRef={isCustomRef}
				onValueSelect={onValueSelect}
				value={value}
			/>
		</ClayForm.Group>
	);
}

LengthField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
};

const Field = ({
	field,
	id,
	initialValue,
	isCustomRef,
	onValueSelect,
	value,
}) => {
	const [active, setActive] = useState(false);
	const [nextValue, setNextValue] = useControlledState(initialValue.value);
	const [nextUnit, setNextUnit] = useState(initialValue.unit);
	const triggerId = useId();

	const handleUnitSelect = (unit) => {
		setActive(false);
		setNextUnit(unit);

		isCustomRef.current = unit === CUSTOM;

		if (!nextValue) {
			return;
		}

		let valueWithUnits = `${nextValue}${unit}`;

		if (unit === CUSTOM) {
			valueWithUnits = nextValue;
		}
		else if (isNaN(nextValue)) {
			valueWithUnits = '';
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

		if (valueWithUnits !== value) {
			onValueSelect(field.name, valueWithUnits);
		}
	};

	const handleKeyDown = (event) => {
		if (nextUnit !== CUSTOM && KEYS_NOT_ALLOWED.includes(event.key)) {
			event.preventDefault();
		}

		if (event.key === 'Enter') {
			handleValueSelect();
		}
	};

	useEffect(() => {
		if (!value) {
			return;
		}

		const match = value.toLowerCase().match(REGEX);

		setNextUnit(match ? match[2] : CUSTOM);
	}, [value]);

	return (
		<ClayInput.Group>
			<ClayInput.GroupItem prepend>
				<ClayInput
					aria-label={field.label}
					id={id}
					onBlur={() => {
						handleValueSelect();
					}}
					onChange={(event) => {
						setNextValue(event.target.value);
					}}
					onKeyDown={handleKeyDown}
					sizing="sm"
					type={nextUnit === CUSTOM ? 'text' : 'number'}
					value={nextValue}
				/>
			</ClayInput.GroupItem>

			<ClayInput.GroupItem append shrink>
				<ClayDropDown
					active={active}
					alignmentPosition={Align.BottomRight}
					menuElementAttrs={{
						className: 'page-editor__length-field__dropdown',
						containerProps: {
							className: 'cadmin',
						},
					}}
					onActiveChange={setActive}
					role="listbox"
					trigger={
						<ClayButton
							aria-expanded={active}
							aria-haspopup="true"
							aria-label={Liferay.Util.sub(
								Liferay.Language.get('select-a-unit'),
								nextUnit
							)}
							className="p-1 page-editor__length-field__button"
							displayType="secondary"
							id={triggerId}
							small
						>
							{nextUnit === CUSTOM ? (
								<ClayIcon symbol="code" />
							) : (
								nextUnit.toUpperCase()
							)}
						</ClayButton>
					}
				>
					<DropDownList
						aria-labelledby={triggerId}
						field={field}
						onClick={handleUnitSelect}
					/>
				</ClayDropDown>
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
};

const DropDownList = ({onClick, ...otherProps}) => (
	<ClayDropDown.ItemList {...otherProps}>
		{UNITS.map((unit) => (
			<ClayDropDown.Item
				aria-label={unit}
				key={unit}
				onClick={() => onClick(unit)}
			>
				{unit.toUpperCase()}
			</ClayDropDown.Item>
		))}
	</ClayDropDown.ItemList>
);
