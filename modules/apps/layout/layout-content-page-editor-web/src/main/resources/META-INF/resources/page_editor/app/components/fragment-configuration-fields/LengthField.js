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
import React, {useState} from 'react';

import useControlledState from '../../../core/hooks/useControlledState';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {useId} from '../../utils/useId';

const KEYS_NOT_ALLOWED = ['+', ',', 'e'];

const REGEX = /[-(0-9).]+|[a-zA-Z]+|%/g;

const UNITS = ['px', '%', 'em', 'rem', 'vw', 'vh'];

export function LengthField({field, onValueSelect, value}) {
	const inputId = useId();
	const dropdownId = useId();

	const [active, setActive] = useState(false);
	const [nextValue, setNextValue] = useControlledState(
		value ? value.match(REGEX)[0] : ''
	);
	const [nextUnit, setNextUnit] = useControlledState(
		value ? value.match(REGEX)[1] : UNITS[0]
	);

	return (
		<ClayForm.Group>
			<label htmlFor={inputId}>{field.label}</label>

			<ClayInput.Group>
				<ClayInput.GroupItem prepend>
					<ClayInput
						id={inputId}
						onBlur={() => {
							const valueWithUnits = Number(nextValue)
								? `${nextValue}${nextUnit}`
								: '';

							if (valueWithUnits !== value) {
								onValueSelect(field.name, valueWithUnits);
							}
						}}
						onChange={(event) => {
							setNextValue(event.target.value);
						}}
						onKeyDown={(event) => {
							if (KEYS_NOT_ALLOWED.includes(event.key)) {
								event.preventDefault();
							}
						}}
						sizing="sm"
						type="number"
						value={nextValue}
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem append shrink>
					<ClayDropDown
						active={active}
						alignmentPosition={Align.BottomRight}
						aria-labelledby={dropdownId}
						menuElementAttrs={{
							className: 'page-editor__length-field__dropdown',
							containerProps: {
								className: 'cadmin',
							},
						}}
						onActiveChange={setActive}
						trigger={
							<ClayButton
								className="p-1 page-editor__length-field__button"
								displayType="secondary"
								id={dropdownId}
								small
							>
								{nextUnit.toUpperCase()}
							</ClayButton>
						}
					>
						<ClayDropDown.ItemList>
							{UNITS.map((unit) => (
								<ClayDropDown.Item
									key={unit}
									onClick={() => {
										setActive(false);
										setNextUnit(unit);

										if (Number(nextValue)) {
											const valueWithUnits = `${nextValue}${unit}`;

											if (valueWithUnits !== value) {
												onValueSelect(
													field.name,
													valueWithUnits
												);
											}
										}
									}}
								>
									{unit.toUpperCase()}
								</ClayDropDown.Item>
							))}
						</ClayDropDown.ItemList>
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
