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

import ClayAutocomplete from '@clayui/autocomplete';
import {ClayButtonWithIcon} from '@clayui/button';
import ClayColorPicker from '@clayui/color-picker';
import ClayForm, {ClayInput} from '@clayui/form';
import classNames from 'classnames';
import {debounce} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import ColorPicker from '../../../common/components/ColorPicker';
import useControlledState from '../../../core/hooks/useControlledState';
import {useStyleBook} from '../../../plugins/page-design-options/hooks/useStyleBook';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {config} from '../../config/index';
import {useId} from '../../utils/useId';
import {ColorPaletteField} from './ColorPaletteField';

const COLOR_PICKER_TYPE = 'ColorPicker';

const debouncedOnValueSelect = debounce(
	(onValueSelect, fieldName, value) => onValueSelect(fieldName, value),
	300
);

export function ColorPickerField({field, onValueSelect, value}) {
	const {tokenValues} = useStyleBook();
	const [color, setColor] = useControlledState(
		config.tokenReuseEnabled
			? tokenValues[value]?.value || value
			: tokenValues[value]?.value
	);
	const colors = {};
	const [customColors, setCustomColors] = useState([value || '']);
	const id = useId();
	const [isToken, setIsToken] = useState(!value || !!tokenValues[value]);

	Object.values(tokenValues)
		.filter((token) => token.editorType === COLOR_PICKER_TYPE)
		.forEach(
			({
				label,
				name,
				tokenCategoryLabel: category,
				tokenSetLabel: tokenSet,
				value,
			}) => {
				const color = {label, name, value};

				if (Object.keys(colors).includes(category)) {
					if (Object.keys(colors[category]).includes(tokenSet)) {
						colors[category][tokenSet].push(color);
					}
					else {
						colors[category][tokenSet] = [color];
					}
				}
				else {
					colors[category] = {[tokenSet]: [color]};
				}
			}
		);

	if (!Object.keys(colors).length) {
		return (
			<ColorPaletteField
				field={field}
				onValueSelect={(name, value) =>
					onValueSelect(name, value?.rgbValue ?? '')
				}
				value={value}
			/>
		);
	}

	return (
		<ClayForm.Group className="page-editor__color-picker-field" small>
			<label>{field.label}</label>

			<ClayInput.Group>
				{config.tokenReuseEnabled ? (
					isToken ? (
						<ClayInput.GroupItem>
							<ColorPicker
								colors={colors}
								label={
									value
										? tokenValues[value]?.label || ''
										: Liferay.Language.get('default')
								}
								onValueChange={({name, value}) => {
									setColor(value);
									onValueSelect(field.name, name);
								}}
								value={color}
							/>
						</ClayInput.GroupItem>
					) : (
						<ClayInput.GroupItem>
							<ClayInput.Group className="page-editor__color-picker-field__color-picker">
								<ClayInput.GroupItem prepend shrink>
									<ClayColorPicker
										colors={customColors}
										dropDownContainerProps={{
											className: 'cadmin',
										}}
										onColorsChange={setCustomColors}
										onValueChange={(color) => {
											setColor(`#${color}`);
											debouncedOnValueSelect(
												onValueSelect,
												field.name,
												`#${color}`
											);
										}}
										showHex={false}
										showPalette={false}
										value={color?.replace('#', '') ?? ''}
									/>
								</ClayInput.GroupItem>

								<ClayInput.GroupItem append>
									<ClayAutocomplete>
										<ClayAutocomplete.Input
											className="page-editor__color-picker-field__autocomplete__input"
											id={id}
											onChange={(event) => {
												setColor(event.target.value);
												debouncedOnValueSelect(
													onValueSelect,
													field.name,
													event.target.value
												);
											}}
											value={color}
										/>
									</ClayAutocomplete>
								</ClayInput.GroupItem>
							</ClayInput.Group>
						</ClayInput.GroupItem>
					)
				) : (
					<>
						<ClayInput.GroupItem prepend shrink>
							<ColorPicker
								colors={colors}
								onValueChange={({name, value}) => {
									setColor(value);
									onValueSelect(field.name, name);
								}}
								showHex={false}
								value={color}
							/>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem append>
							<ClayInput
								readOnly
								value={
									tokenValues[value]
										? tokenValues[value].label
										: Liferay.Language.get('default')
								}
							/>
						</ClayInput.GroupItem>
					</>
				)}

				{color && (
					<>
						{config.tokenReuseEnabled && (
							<ClayInput.GroupItem shrink>
								{isToken ? (
									<ClayButtonWithIcon
										className="border-0"
										displayType="secondary"
										onClick={() => {
											setIsToken(false);
											setColor(tokenValues[value].value);
											setCustomColors([
												tokenValues[value].value,
											]);
											onValueSelect(
												field.name,
												tokenValues[value].value
											);
										}}
										small
										symbol="link"
										title={Liferay.Language.get(
											'detach-token'
										)}
									/>
								) : (
									<ColorPicker
										colors={colors}
										onValueChange={({name, value}) => {
											setColor(value);
											setIsToken(true);
											onValueSelect(field.name, name);
										}}
										showSelector={false}
										value={color}
									/>
								)}
							</ClayInput.GroupItem>
						)}

						<ClayInput.GroupItem shrink>
							<ClayButtonWithIcon
								className={classNames({
									'border-0': config.tokenReuseEnabled,
								})}
								displayType="secondary"
								onClick={() => {
									setColor('');
									setIsToken(true);
									onValueSelect(field.name, '');
								}}
								small
								symbol="times-circle"
								title={Liferay.Language.get('clear-selection')}
							/>
						</ClayInput.GroupItem>
					</>
				)}
			</ClayInput.Group>
		</ClayForm.Group>
	);
}

ColorPickerField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.string,
};
