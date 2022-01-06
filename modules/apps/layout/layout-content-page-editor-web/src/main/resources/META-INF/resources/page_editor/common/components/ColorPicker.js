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
import ClayIcon from '@clayui/icon';
import {FocusScope} from '@clayui/shared';
import classNames from 'classnames';
import {debounce} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

import {getValidHexColor} from '../../app/utils/getValidHexColor';
import {useId} from '../../app/utils/useId';
import useControlledState from '../../core/hooks/useControlledState';
import {ConfigurationFieldPropTypes} from '../../prop-types/index';
import {DropdownColorPicker} from './DropdownColorPicker';

import './ColorPicker.scss';

const ERROR_MESSAGES = {
	mutuallyReferenced: Liferay.Language.get(
		'invalid-value.-tokens-cannot-be-mutually-referenced'
	),
	selfReferenced: Liferay.Language.get(
		'invalid-value.-tokens-cannot-reference-itself'
	),
	valueNotExist: Liferay.Language.get('this-token-does-not-exist'),
};

const debouncedOnValueSelect = debounce(
	(onValueSelect, fieldName, value) => onValueSelect(fieldName, value),
	300
);

export function ColorPicker({
	config,
	editedTokenValues,
	field,
	onValueSelect,
	tokenValues,
	value,
}) {
	const colors = {};
	const id = useId();

	const [activeAutocomplete, setActiveAutocomplete] = useState(false);
	const [activeColorPicker, setActiveColorPicker] = useState(false);
	const buttonsRef = useRef(null);
	const [color, setColor] = useControlledState(
		config.tokenReuseEnabled
			? tokenValues[value]?.value || value
			: tokenValues[value]?.value
	);
	const colorButtonRef = useRef(null);
	const [customColors, setCustomColors] = useState([value || '']);
	const [error, setError] = useState(null);
	const inputRef = useRef(null);
	const listboxRef = useRef(null);
	const [tokenLabel, setTokenLabel] = useControlledState(
		value ? tokenValues[value]?.label : Liferay.Language.get('default')
	);

	const tokenColorValues = Object.values(tokenValues).filter(
		(token) => token.editorType === 'ColorPicker'
	);

	const filteredTokenValues = tokenColorValues.filter((token) =>
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

	useEffect(() => {
		if (config.tokenReuseEnabled) {
			setError(null);
		}
	}, [value, config.tokenReuseEnabled]);

	const onSetValue = (value, label, name) => {
		setColor(value);
		setTokenLabel(label);
		onValueSelect(field.name, name ?? value);
	};

	const onBlurAutocompleteInput = ({target}) => {
		const colorButton = colorButtonRef.current.querySelector(
			'.clay-color-btn'
		);
		const isHexColor = target.value.startsWith('#');
		let nextValue = isHexColor
			? getValidHexColor(target.value)
			: target.value;

		if (!nextValue) {
			setColor(value);

			return;
		}
		else if (nextValue !== value) {
			const token = tokenColorValues.find(
				(token) => token.label.toLowerCase() === nextValue
			);

			if (token || isHexColor) {
				nextValue = token?.name || nextValue;

				if (nextValue === field.name) {
					setError(ERROR_MESSAGES.selfReferenced);

					return;
				}

				if (editedTokenValues?.[nextValue]?.name === field.name) {
					setError(ERROR_MESSAGES.mutuallyReferenced);

					return;
				}

				setTokenLabel(!isHexColor ? token.label : null);

				if (isHexColor) {
					setCustomColors([nextValue.replace('#', '')]);
				}
			}
			else if (colorButton.style.background !== nextValue) {
				setError(ERROR_MESSAGES.valueNotExist);

				return;
			}

			onValueSelect(field.name, nextValue);
		}

		setColor(nextValue);
	};

	const onChangeAutocompleteInput = ({target: {value}}) => {
		if (error) {
			setError(null);
		}

		setActiveAutocomplete(value.length > 1 && filteredTokenValues.length);
		setColor(value);
	};

	const onClickAutocompleteItem = ({label, name, value}) => {
		onSetValue(value, label, name);
	};

	const onKeydownAutocompleteItem = (event) => {
		if (event.key === 'Tab') {
			event.preventDefault();
			event.stopPropagation();

			setActiveAutocomplete(false);
			onBlurAutocompleteInput({target: inputRef.current});

			if (event.shiftKey) {
				inputRef.current.focus();
			}
			else {
				buttonsRef.current.querySelector('button').focus();
			}
		}
	};

	const onKeydownAutocompleteInput = (event) => {
		if (event.key === 'Tab') {
			setActiveAutocomplete(false);
			onBlurAutocompleteInput(event);

			if (!event.shiftKey) {
				event.preventDefault();
				event.stopPropagation();

				buttonsRef.current.querySelector('button').focus();
			}
		}
	};

	return (
		<ClayForm.Group small>
			<label>{field.label}</label>

			<ClayInput.Group
				className={classNames('page-editor__color-picker', {
					'has-warning': error,
					'hovered':
						!config.tokenReuseEnabled ||
						activeColorPicker ||
						activeAutocomplete,
				})}
			>
				{config.tokenReuseEnabled ? (
					tokenLabel ? (
						<ClayInput.GroupItem>
							<DropdownColorPicker
								active={activeColorPicker}
								colors={colors}
								config={config}
								fieldName={field.name}
								label={tokenLabel}
								onSetActive={setActiveColorPicker}
								onValueChange={({label, name, value}) =>
									onSetValue(value, label, name)
								}
								small
								value={color}
							/>
						</ClayInput.GroupItem>
					) : (
						<ClayInput.GroupItem>
							<ClayInput.Group>
								<ClayInput.GroupItem
									prepend
									ref={colorButtonRef}
									shrink
								>
									<ClayColorPicker
										active={activeColorPicker}
										colors={customColors}
										dropDownContainerProps={{
											className: 'cadmin',
										}}
										onChangeActive={setActiveColorPicker}
										onColorsChange={setCustomColors}
										onValueChange={(color) => {
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
									<FocusScope>
										<ClayAutocomplete>
											<ClayAutocomplete.Input
												aria-expanded={
													activeAutocomplete
												}
												aria-invalid={error}
												aria-owns={`${id}_listbox`}
												className="page-editor__color-picker__autocomplete__input"
												id={id}
												onBlur={(event) => {
													if (!activeAutocomplete) {
														onBlurAutocompleteInput(
															event
														);
													}
												}}
												onChange={
													onChangeAutocompleteInput
												}
												onKeyDown={
													onKeydownAutocompleteInput
												}
												ref={inputRef}
												role="combobox"
												value={
													color.startsWith('#')
														? color.toUpperCase()
														: color
												}
											/>

											<ClayAutocomplete.DropDown
												active={
													activeAutocomplete &&
													filteredTokenValues.length
												}
												closeOnClickOutside={true}
												onSetActive={
													setActiveAutocomplete
												}
											>
												<ul
													className="list-unstyled"
													id={`${id}_listbox`}
													ref={listboxRef}
													role="listbox"
												>
													{filteredTokenValues.map(
														(token, index) => (
															<ClayAutocomplete.Item
																aria-posinset={
																	index
																}
																disabled={
																	token.name ===
																		field.name ||
																	editedTokenValues?.[
																		token
																			.name
																	]?.name ===
																		field.name
																}
																key={token.name}
																onClick={() =>
																	onClickAutocompleteItem(
																		token
																	)
																}
																onKeyDown={
																	onKeydownAutocompleteItem
																}
																onMouseDown={(
																	event
																) =>
																	event.preventDefault()
																}
																role="option"
																value={
																	token.label
																}
															/>
														)
													)}
												</ul>
											</ClayAutocomplete.DropDown>
										</ClayAutocomplete>
									</FocusScope>
								</ClayInput.GroupItem>
							</ClayInput.Group>
						</ClayInput.GroupItem>
					)
				) : (
					<>
						<ClayInput.GroupItem prepend shrink>
							<DropdownColorPicker
								active={activeColorPicker}
								colors={colors}
								config={config}
								onSetActive={setActiveColorPicker}
								onValueChange={({name, value}) => {
									setColor(value);
									onValueSelect(field.name, name);
								}}
								showHex={false}
								small
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
								className="page-editor__color-picker__action-button"
								ref={buttonsRef}
								shrink
							>
								{tokenLabel ? (
									<ClayButtonWithIcon
										className="border-0"
										displayType="secondary"
										onClick={() => {
											setCustomColors([
												tokenValues[
													value
												].value.replace('#', ''),
											]);

											onSetValue(
												tokenValues[value].value,
												null
											);
										}}
										small
										symbol="chain-broken"
										title={Liferay.Language.get(
											'detach-token'
										)}
									/>
								) : (
									<DropdownColorPicker
										active={activeColorPicker}
										colors={colors}
										config={config}
										fieldName={field.name}
										onSetActive={setActiveColorPicker}
										onValueChange={({label, name, value}) =>
											onSetValue(value, label, name)
										}
										showSelector={false}
										small
										value={color}
									/>
								)}
							</ClayInput.GroupItem>
						)}

						<ClayInput.GroupItem
							className={classNames({
								'page-editor__color-picker__action-button':
									config.tokenReuseEnabled,
							})}
							shrink
						>
							<ClayButtonWithIcon
								className={classNames({
									'border-0': config.tokenReuseEnabled,
								})}
								displayType="secondary"
								onClick={() =>
									onSetValue(
										field.defaultValue ?? '',
										field.defaultValue
											? null
											: Liferay.Language.get('default')
									)
								}
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
						<div className="autofit-section">{error}</div>
					</div>
				</div>
			)}
		</ClayForm.Group>
	);
}

ColorPicker.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	tokenValues: PropTypes.shape({}).isRequired,
	value: PropTypes.string,
};
