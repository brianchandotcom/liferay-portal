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
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
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

const debouncedOnValueSelect = debounce(
	(onValueSelect, fieldName, value) => onValueSelect(fieldName, value),
	300
);

export function ColorPickerField({field, onValueSelect, value}) {
	const colors = {};
	const id = useId();
	const {tokenValues} = useStyleBook();

	const [activeAutocomplete, setActiveAutocomplete] = useState(false);
	const [activeColorPicker, setActiveColorPicker] = useState(false);
	const [color, setColor] = useControlledState(
		config.tokenReuseEnabled
			? tokenValues[value]?.value || value
			: tokenValues[value]?.value
	);
	const [customColors, setCustomColors] = useState([value || '']);
	const [error, setError] = useState(false);
	const [isHexadecimal, setIsHexadecimal] = useState(false);
	const [isToken, setIsToken] = useState(!value || !!tokenValues[value]);

	const tokenColorValues = Object.values(tokenValues).filter(
		(token) => token.editorType === 'ColorPicker'
	);

	const filteredTokenColorValues = tokenColorValues.filter((token) =>
		token.label.toLowerCase().includes(color)
	);

	tokenColorValues.forEach(
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
		<ClayForm.Group small>
			<label>{field.label}</label>

			<ClayInput.Group
				className={classNames('page-editor__color-picker-field', {
					'has-warning': error,
					'hovered': !config.tokenReuseEnabled || activeColorPicker,
				})}
			>
				{config.tokenReuseEnabled ? (
					isToken ? (
						<ClayInput.GroupItem>
							<ColorPicker
								active={activeColorPicker}
								colors={colors}
								label={
									value
										? tokenValues[value]?.label || ''
										: Liferay.Language.get('default')
								}
								onSetActive={setActiveColorPicker}
								onValueChange={({name, value}) => {
									onValueSelect(field.name, name);
									setColor(value);
								}}
								value={color}
							/>
						</ClayInput.GroupItem>
					) : (
						<ClayInput.GroupItem>
							<ClayInput.Group className="page-editor__color-picker-field__color-picker">
								<ClayInput.GroupItem prepend shrink>
									<ClayColorPicker
										active={activeColorPicker}
										colors={customColors}
										dropDownContainerProps={{
											className: 'cadmin',
										}}
										onChangeActive={setActiveColorPicker}
										onColorsChange={setCustomColors}
										onValueChange={(color) => {
											if (error) {
												setError(false);
											}

											debouncedOnValueSelect(
												onValueSelect,
												field.name,
												`#${color}`
											);
											setColor(`#${color}`);
										}}
										showHex={false}
										showPalette={false}
										value={
											error ? '' : color?.replace('#', '')
										}
									/>
								</ClayInput.GroupItem>

								<ClayInput.GroupItem append>
									<ClayAutocomplete>
										<ClayAutocomplete.Input
											className="page-editor__color-picker-field__autocomplete__input"
											id={id}
											onBlur={({target}) => {
												let nextValue = isHexadecimal
													? target.value.substring(
															0,
															7
													  )
													: target.value;

												if (nextValue !== value) {
													const token = tokenColorValues.find(
														(token) =>
															token.label.toLowerCase() ===
															nextValue
													);

													if (
														token ||
														isHexadecimal
													) {
														nextValue =
															token?.name ||
															nextValue;

														setIsToken(
															!isHexadecimal
														);
														onValueSelect(
															field.name,
															nextValue
														);

														if (isHexadecimal) {
															setCustomColors([
																nextValue.replace(
																	'#',
																	''
																),
															]);
														}
													}
													else {
														setError(true);
													}
												}

												setColor(nextValue);

												if (activeAutocomplete) {
													setActiveAutocomplete(
														false
													);
												}
											}}
											onChange={({target: {value}}) => {
												if (error) {
													setError(false);
												}

												setActiveAutocomplete(true);
												setColor(value);
												setIsHexadecimal(
													value.startsWith('#')
												);
											}}
											value={color}
										/>

										<ClayAutocomplete.DropDown
											active={
												activeAutocomplete &&
												filteredTokenColorValues.length
											}
											closeOnClickOutside={true}
											onSetActive={setActiveAutocomplete}
										>
											<ClayDropDown.ItemList>
												{filteredTokenColorValues.map(
													(token) => (
														<ClayAutocomplete.Item
															key={token.name}
															match={color}
															onClick={() => {
																setColor(
																	token.value
																);
																setIsToken(
																	true
																);
																onValueSelect(
																	field.name,
																	token.name
																);
															}}
															onMouseDown={(
																event
															) =>
																event.preventDefault()
															}
															value={token.label}
														/>
													)
												)}
											</ClayDropDown.ItemList>
										</ClayAutocomplete.DropDown>
									</ClayAutocomplete>
								</ClayInput.GroupItem>
							</ClayInput.Group>
						</ClayInput.GroupItem>
					)
				) : (
					<>
						<ClayInput.GroupItem prepend shrink>
							<ColorPicker
								active={activeColorPicker}
								colors={colors}
								onSetActive={setActiveColorPicker}
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
							<ClayInput.GroupItem
								className="page-editor__color-picker-field__action-button"
								shrink
							>
								{isToken ? (
									<ClayButtonWithIcon
										className="border-0"
										displayType="secondary"
										onClick={() => {
											setColor(tokenValues[value].value);
											setCustomColors([
												tokenValues[
													value
												].value.replace('#', ''),
											]);
											setIsToken(false);
											onValueSelect(
												field.name,
												tokenValues[value].value
											);
										}}
										small
										symbol="chain-broken"
										title={Liferay.Language.get(
											'detach-token'
										)}
									/>
								) : (
									<ColorPicker
										active={activeColorPicker}
										colors={colors}
										onSetActive={setActiveColorPicker}
										onValueChange={({name, value}) => {
											if (error) {
												setError(false);
											}

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

						<ClayInput.GroupItem
							className={classNames({
								'page-editor__color-picker-field__action-button':
									config.tokenReuseEnabled,
							})}
							shrink
						>
							<ClayButtonWithIcon
								className={classNames({
									'border-0': config.tokenReuseEnabled,
								})}
								displayType="secondary"
								onClick={() => {
									if (config.tokenReuseEnabled && error) {
										setError(false);
									}

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

			{config.tokenReuseEnabled && error && (
				<div className="autofit-row mt-2 small text-warning">
					<div className="autofit-col">
						<div className="autofit-section mr-2">
							<ClayIcon symbol="warning-full" />
						</div>
					</div>

					<div className="autofit-col autofit-col-expand">
						<div className="autofit-section">
							{Liferay.Language.get('this-token-does-not-exist')}
						</div>
					</div>
				</div>
			)}
		</ClayForm.Group>
	);
}

ColorPickerField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.string,
};
