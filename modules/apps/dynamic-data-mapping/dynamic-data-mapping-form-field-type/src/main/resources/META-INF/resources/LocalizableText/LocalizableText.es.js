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

import './LocalizableTextRegister.soy';

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import classNames from 'classnames';
import React, {forwardRef, useState} from 'react';

import {FieldBaseProxy} from '../FieldBase/ReactFieldBase.es';
import getConnectedReactComponentAdapter from '../util/ReactComponentAdapter.es';
import {connectStore} from '../util/connectStore.es';
import InputComponent from './InputComponent.es';
import templates from './LocalizableTextAdapter.soy';

function getEditingValue({defaultLocale, editingLocale, value}) {
	const valueJSON = convertValueToJSON(value);

	if (valueJSON) {
		return (
			valueJSON[editingLocale.localeId] ||
			valueJSON[defaultLocale.localeId] ||
			''
		);
	}

	return editingLocale;
}

function convertValueToString(value) {
	if (value && typeof value === 'object') {
		return JSON.stringify(value);
	}

	return value;
}

function convertValueToJSON(value) {
	if (value && typeof value === 'string') {
		try {
			return JSON.parse(value);
		}
		catch (e) {
			console.warn('Unable to parse JSON', value);
		}
	}

	return value;
}

function isDefaultLocale({defaultLocale, localeId}) {
	return defaultLocale.localeId === localeId;
}

function isTranslated({localeId, value}) {
	const valueJSON = convertValueToJSON(value);

	if (valueJSON) {
		return !!valueJSON[localeId];
	}

	return false;
}

function getInitialInternalValue({editingLocale, value}) {
	const valueJSON = convertValueToJSON(value);

	return valueJSON[editingLocale.localeId] || '';
}

function normalizeLocaleId(localeId) {
	if (!localeId || localeId === '') {
		throw new Error(`localeId ${localeId} is invalid`);
	}

	return localeId.replace('_', '-').toLowerCase();
}

function transformDataFromServer(props) {
	const {defaultLocale, value} = props;

	return {
		...props,
		availableLocales: props.availableLocales.map(availableLocale => {
			return {
				...availableLocale,
				icon: normalizeLocaleId(availableLocale.localeId),
				isDefault: isDefaultLocale({
					defaultLocale,
					localeId: availableLocale.localeId,
				}),
				isTranslated: isTranslated({
					localeId: availableLocale.localeId,
					value,
				}),
			};
		}),
		value: convertValueToString(value),
	};
}

const INITIAL_DEFAULT_LOCALE = {
	icon: themeDisplay.getDefaultLanguageId(),
	localeId: themeDisplay.getDefaultLanguageId(),
};
const INITIAL_EDITING_LOCALE = {
	icon: normalizeLocaleId(themeDisplay.getDefaultLanguageId()),
	localeId: themeDisplay.getDefaultLanguageId(),
};

const DropdownTrigger = forwardRef(
	({editingLocale, spritemap, ...otherProps}, ref) => {
		return (
			<ClayButton
				aria-expanded="false"
				aria-haspopup="true"
				className="dropdown-toggle"
				data-testid="triggerButton"
				displayType="secondary"
				monospaced
				ref={ref}
				{...otherProps}
			>
				<span className="inline-item">
					<ClayIcon
						spritemap={spritemap}
						symbol={editingLocale.icon}
					/>
				</span>
				<span className="btn-section" data-testid="triggerText">
					{editingLocale.icon}
				</span>
			</ClayButton>
		);
	}
);

const AvailableLocaleLabel = ({isDefault, isTranslated}) => {
	const labelText = isDefault
		? 'default'
		: isTranslated
		? 'translated'
		: 'not-translated';

	return (
		<ClayLabel
			displayType={classNames({
				info: isDefault,
				success: isTranslated,
				warning: !isDefault && !isTranslated,
			})}
			large
		>
			{Liferay.Language.get(labelText)}
		</ClayLabel>
	);
};

const LocalesDropdown = ({
	availableLocales,
	editingLocale,
	onLanguageClicked = () => {},
}) => {
	const [dropdownActive, setDropdownActive] = useState(false);

	const spritemap = `${themeDisplay.getPathThemeImages()}/lexicon/icons.svg`;

	return (
		<div>
			<ClayDropDown
				active={dropdownActive}
				hasRightSymbols
				onActiveChange={setDropdownActive}
				trigger={
					<DropdownTrigger
						editingLocale={editingLocale}
						spritemap={spritemap}
					/>
				}
			>
				<ClayDropDown.ItemList>
					{availableLocales.map(
						({
							displayName,
							icon,
							isDefault,
							isTranslated,
							localeId,
						}) => (
							<ClayDropDown.Item
								className="autofit-row custom-dropdown-item-row"
								data-testid={`availableLocalesDropdown${localeId}`}
								key={localeId}
								onClick={event => {
									onLanguageClicked({event, localeId});
									setDropdownActive(false);
								}}
							>
								<span className="autofit-col autofit-col-expand">
									<span className="autofit-section">
										<span className="inline-item inline-item-before">
											<ClayIcon
												spritemap={spritemap}
												symbol={icon}
											/>
										</span>
										{displayName}
									</span>
								</span>

								<span className="autofit-col">
									<AvailableLocaleLabel
										isDefault={isDefault}
										isTranslated={isTranslated}
									/>
								</span>
							</ClayDropDown.Item>
						)
					)}
				</ClayDropDown.ItemList>
			</ClayDropDown>
		</div>
	);
};

const LocalizableText = ({
	availableLocales = [],
	defaultLocale = INITIAL_DEFAULT_LOCALE,
	displayStyle = 'singleline',
	editingLocale = INITIAL_EDITING_LOCALE,
	id,
	name,
	onFieldBlurred,
	onFieldChanged = () => {},
	onFieldFocused,
	placeholder = '',
	predefinedValue = '',
	readOnly,
	value,
}) => {
	const [currentEditingLocale, setCurrentEditingLocale] = useState(
		editingLocale
	);

	const [currentValue, setCurrentValue] = useState(value);

	const [currentInternalValue, setCurrentInternalValue] = useState(
		getInitialInternalValue({editingLocale: currentEditingLocale, value})
	);

	const inputValue = currentInternalValue
		? currentInternalValue
		: predefinedValue;

	return (
		<ClayInput.Group>
			<InputComponent
				displayStyle={displayStyle}
				id={id}
				inputValue={inputValue}
				name={name}
				onFieldBlurred={onFieldBlurred}
				onFieldChanged={event => {
					const {target} = event;
					const valueJSON = convertValueToJSON(currentValue);

					const newValue = JSON.stringify({
						...valueJSON,
						[currentEditingLocale.localeId]: target.value,
					});

					setCurrentValue(newValue);
					setCurrentInternalValue(target.value);

					onFieldChanged({event, value: newValue});
				}}
				onFieldFocused={onFieldFocused}
				placeholder={placeholder}
				readOnly={readOnly}
			/>

			<input
				id={id}
				name={name}
				type="hidden"
				value={currentValue || ''}
			/>

			<ClayInput.GroupItem
				className="liferay-ddm-form-field-localizable-text"
				shrink
			>
				<LocalesDropdown
					availableLocales={availableLocales}
					editingLocale={currentEditingLocale}
					onLanguageClicked={({localeId}) => {
						const newEditingLocale = availableLocales.find(
							availableLocale =>
								availableLocale.localeId === localeId
						);

						setCurrentEditingLocale({
							...newEditingLocale,
							icon: normalizeLocaleId(newEditingLocale.localeId),
						});

						setCurrentInternalValue(
							getEditingValue({
								defaultLocale,
								editingLocale: newEditingLocale,
								value: currentValue,
							})
						);
					}}
				/>
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
};

const LocalizableTextWithFieldBase = props => {
	const {
		availableLocales,
		displayStyle,
		editingLocale,
		id,
		name,
		onFieldBlurred,
		onFieldChanged,
		onFieldFocused,
		placeholder,
		predefinedValue,
		readOnly,
		value = {},
		...otherProps
	} = transformDataFromServer(props);

	return (
		<FieldBaseProxy {...otherProps} id={id} name={name} readOnly={readOnly}>
			<LocalizableText
				availableLocales={availableLocales}
				displayStyle={displayStyle}
				editingLocale={editingLocale}
				id={id}
				name={name}
				onFieldBlurred={onFieldBlurred}
				onFieldChanged={onFieldChanged}
				onFieldFocused={onFieldFocused}
				placeholder={placeholder}
				predefinedValue={predefinedValue}
				readOnly={readOnly}
				value={value}
			/>
		</FieldBaseProxy>
	);
};

const LocalizableTextProxy = connectStore(({emit, ...otherProps}) => (
	<LocalizableTextWithFieldBase
		{...otherProps}
		onFieldBlurred={event =>
			emit('fieldBlurred', event, event.target.value)
		}
		onFieldChanged={({event, value}) => emit('fieldEdited', event, value)}
		onFieldFocused={event =>
			emit('fieldFocused', event, event.target.value)
		}
	/>
));

const ReactLocalizableTextAdapter = getConnectedReactComponentAdapter(
	LocalizableTextProxy,
	templates
);

export {ReactLocalizableTextAdapter, LocalizableTextWithFieldBase};
export default ReactLocalizableTextAdapter;
