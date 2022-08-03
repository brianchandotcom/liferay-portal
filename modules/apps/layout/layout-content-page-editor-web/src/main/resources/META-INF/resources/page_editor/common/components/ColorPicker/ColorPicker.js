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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayColorPicker from '@clayui/color-picker';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {debounce} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useRef, useState} from 'react';

import {useActiveItemId} from '../../../app/contexts/ControlsContext';
import {
	useDeleteStyleError,
	useSetStyleError,
	useStyleErrors,
} from '../../../app/contexts/StyleErrorsContext';
import {useId} from '../../../app/utils/useId';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {DropdownColorPicker} from './DropdownColorPicker';
import {parseColorValue} from './parseColorValue';

import './ColorPicker.scss';

export const DEFAULT_TOKEN_LABEL = Liferay.FeatureFlags['LPS-143206']
	? Liferay.Language.get('inherited')
	: Liferay.Language.get('default');

const debouncedOnValueSelect = debounce(
	(onValueSelect, fieldName, value) => onValueSelect(fieldName, value),
	300
);

function usePropsFirst(value, {forceProp = false}) {
	const [nextValue, setNextValue] = useState(value);
	const [previousValue, setPreviousValue] = useState(value);

	if (value !== previousValue || (forceProp && nextValue !== value)) {
		setNextValue(value);
		setPreviousValue(value);
	}

	return [nextValue, setNextValue];
}

export function ColorPicker({
	canDetachTokenValues = true,
	defaultTokenLabel = DEFAULT_TOKEN_LABEL,
	defaultTokenValue = undefined,
	editedTokenValues,
	field,
	onValueSelect,
	showLabel = true,
	tokenValues,
	value,
}) {
	const activeItemId = useActiveItemId();
	const colors = {};
	const inputId = useId();
	const labelId = useId();
	const deleteStyleError = useDeleteStyleError();
	const setStyleError = useSetStyleError();
	const styleErrors = useStyleErrors();

	const [activeDropdownColorPicker, setActiveDropdownColorPicker] = useState(
		false
	);
	const [activeColorPicker, setActiveColorPicker] = useState(false);
	const [clearedValue, setClearedValue] = useState(false);
	const buttonsRef = useRef(null);
	const [color, setColor] = usePropsFirst(
		tokenValues[value]?.value || value,
		{forceProp: clearedValue}
	);
	const colorButtonRef = useRef(null);
	const [customColors, setCustomColors] = useState([value || '']);
	const [error, setError] = useState({
		label: styleErrors[activeItemId]?.[field.name]?.error,
		value: styleErrors[activeItemId]?.[field.name]?.value,
	});
	const inputRef = useRef(null);
	const [tokenLabel, setTokenLabel] = usePropsFirst(
		value ? tokenValues[value]?.label : defaultTokenLabel,
		{forceProp: clearedValue}
	);

	const showButtons = (tokenLabel && color) || !tokenLabel;

	const tokenColorValues = Object.values(tokenValues)
		.filter((token) => token.editorType === 'ColorPicker')
		.map((token) => ({
			...token,
			disabled:
				token.name === field.name ||
				editedTokenValues?.[token.name]?.name === field.name,
		}));

	tokenColorValues.forEach(
		({
			disabled,
			label,
			name,
			tokenCategoryLabel: category,
			tokenSetLabel: tokenSet,
			value,
		}) => {
			const color = {disabled, label, name, value};

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

	const onSetValue = (value, label, name) => {
		setColor(value);
		setTokenLabel(label);
		onValueSelect(field.name, name ?? value);

		if (value === null) {
			setClearedValue(true);
		}
		else {
			setClearedValue(false);
		}
	};

	const onBlurInput = ({target}) => {
		if (value.toLowerCase() === target.value.toLowerCase()) {
			return;
		}

		if (!target.value) {
			setColor(value);

			return;
		}

		if (target.value !== value) {
			const token = tokenColorValues.find(
				(token) =>
					token.label.toLowerCase() === target.value.toLowerCase()
			);

			const nextValue = parseColorValue({
				editedTokenValues,
				field,
				token,
				value: target.value,
			});

			if (nextValue.error) {
				setError({label: nextValue.error, value: target.value});
				setCustomColors(['FFFFFF']);
				setStyleError(
					field.name,
					{
						error: nextValue.error,
						value: target.value,
					},
					activeItemId
				);

				return;
			}

			if (nextValue.value) {
				onValueSelect(field.name, nextValue.value);
			}

			if (nextValue.label) {
				setTokenLabel(nextValue.label);
			}

			if (nextValue.pickerColor) {
				setCustomColors([nextValue.pickerColor]);
			}

			setColor(nextValue.color || nextValue.value || value);
		}
	};

	const onChangeInput = ({target: {value}}) => {
		if (error.value) {
			setError({label: null, value: null});
			deleteStyleError(field.name, activeItemId);
		}

		setColor(value);
	};

	return (
		<ClayForm.Group small>
			<label className={classNames({'sr-only': !showLabel})} id={labelId}>
				{field.label}
			</label>

			<ClayInput.Group
				aria-labelledby={labelId}
				className={classNames('page-editor__color-picker', {
					'has-error': error.value,
					'hovered': activeColorPicker || activeDropdownColorPicker,
				})}
			>
				{tokenLabel ? (
					<ClayInput.GroupItem>
						<DropdownColorPicker
							active={activeDropdownColorPicker}
							colors={colors}
							fieldLabel={showLabel ? null : field.label}
							label={tokenLabel}
							onSetActive={setActiveDropdownColorPicker}
							onValueChange={({label, name, value}) =>
								onSetValue(value, label, name)
							}
							small
							value={color || defaultTokenValue}
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
									onActiveChange={setActiveColorPicker}
									onColorsChange={setCustomColors}
									onValueChange={(color) => {
										debouncedOnValueSelect(
											onValueSelect,
											field.name,
											`#${color}`
										);
										setColor(`#${color}`);

										if (error.value) {
											setError({
												label: null,
												value: null,
											});
											deleteStyleError(field.name);
										}
									}}
									showHex={false}
									showPalette={false}
									value={
										error.value
											? ''
											: color?.replace('#', '')
									}
								/>
							</ClayInput.GroupItem>

							<ClayInput.GroupItem append>
								<ClayInput
									aria-invalid={error.label}
									aria-label={field.label}
									className="page-editor__color-picker__autocomplete__input"
									id={inputId}
									onBlur={onBlurInput}
									onChange={onChangeInput}
									ref={inputRef}
									sizing="sm"
									value={
										error.value ||
										(color.startsWith('#')
											? color.toUpperCase()
											: color)
									}
								/>
							</ClayInput.GroupItem>
						</ClayInput.Group>
					</ClayInput.GroupItem>
				)}

				{showButtons && (
					<>
						<ClayInput.GroupItem
							className="page-editor__color-picker__action-button"
							ref={buttonsRef}
							shrink
						>
							{tokenLabel ? (
								canDetachTokenValues && (
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
								)
							) : (
								<DropdownColorPicker
									active={activeDropdownColorPicker}
									colors={colors}
									fieldLabel={showLabel ? null : field.label}
									onSetActive={setActiveDropdownColorPicker}
									onValueChange={({label, name, value}) => {
										onSetValue(value, label, name);

										if (error.value) {
											setError({
												label: null,
												value: null,
											});
											deleteStyleError(field.name);
										}
									}}
									showSelector={false}
									small
									value={color}
								/>
							)}
						</ClayInput.GroupItem>

						<ClayInput.GroupItem
							className="page-editor__color-picker__action-button"
							shrink
						>
							<ClayButtonWithIcon
								className="border-0"
								displayType="secondary"
								onClick={() => {
									if (
										value.toLowerCase() ===
										field.defaultValue?.toLowerCase()
									) {
										return;
									}

									setError({label: null, value: null});

									onSetValue(
										field.defaultValue ?? null,
										field.defaultValue
											? null
											: defaultTokenLabel
									);
								}}
								small
								symbol="times-circle"
								title={Liferay.Language.get('clear-selection')}
							/>
						</ClayInput.GroupItem>
					</>
				)}
			</ClayInput.Group>

			{error.label && (
				<div className="autofit-row mt-2 small text-danger">
					<div className="autofit-col">
						<div className="autofit-section mr-2">
							<ClayIcon symbol="exclamation-full" />
						</div>
					</div>

					<div className="autofit-col autofit-col-expand">
						<div className="autofit-section">{error.label}</div>
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
