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
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import useControlledState from '../../../core/hooks/useControlledState';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {useId} from '../../utils/useId';

const KEYS_NOT_ALLOWED = ['+', ',', 'e'];

const REGEX = /[-(0-9).]+|[a-zA-Z]+|%/g;

const UNITS = ['px', '%', 'em', 'rem', 'vw', 'vh'];

const isNumber = (value) => !isNaN(parseFloat(value));
export function LengthField({field, onValueSelect, value}) {
	const inputId = useId();
	const triggerId = useId();

	const [active, setActive] = useState(false);
	const [nextValue, setNextValue] = useControlledState(
		value ? value.match(REGEX)[0] : ''
	);
	const [nextUnit, setNextUnit] = useState(
		value ? value.match(REGEX)[1] : UNITS[0]
	);

	const handleUnitSelect = (unit) => {
		setActive(false);
		setNextUnit(unit);

		if (isNumber(nextValue)) {
			const valueWithUnits = `${nextValue}${unit}`;

			if (valueWithUnits !== value) {
				onValueSelect(field.name, valueWithUnits);
			}
		}
	};

	const handleValueSelect = () => {
		const valueWithUnits = isNumber(nextValue)
			? `${nextValue}${nextUnit}`
			: '';

		if (valueWithUnits !== value) {
			onValueSelect(field.name, valueWithUnits);
		}
	};

	const handleKeyDown = (event) => {
		if (KEYS_NOT_ALLOWED.includes(event.key)) {
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
		else {
			setNextUnit(value.match(REGEX)[1]);
		}
	}, [value]);

	return (
		<ClayForm.Group>
			<label htmlFor={inputId}>{field.label}</label>

			<ClayInput.Group>
				<ClayInput.GroupItem prepend>
					<ClayInput
						aria-label={field.label}
						id={inputId}
						onBlur={() => {
							handleValueSelect();
						}}
						onChange={(event) => {
							setNextValue(event.target.value);
						}}
						onKeyDown={handleKeyDown}
						sizing="sm"
						type="number"
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
								{nextUnit.toUpperCase()}
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
		</ClayForm.Group>
	);
}

LengthField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
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
