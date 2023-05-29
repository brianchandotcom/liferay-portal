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

import {ClayInput} from '@clayui/form';
import React, {useEffect, useState} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import LocalesDropdown from '../util/localizable/LocalesDropdown';
import {
	convertValueToJSON,
	getEditingValue,
	getInitialInternalValue,
	normalizeLocaleId,
	transformAvailableLocales,
	transformAvailableLocalesAndValue,
	transformEditingLocale,
} from '../util/localizable/transform.es';
import InputComponent from './InputComponent.es';

const INITIAL_DEFAULT_LOCALE = {
	icon: themeDisplay.getDefaultLanguageId(),
	localeId: themeDisplay.getDefaultLanguageId(),
};
const INITIAL_EDITING_LOCALE = {
	icon: normalizeLocaleId(themeDisplay.getDefaultLanguageId()),
	localeId: themeDisplay.getDefaultLanguageId(),
};

const LocalizableText = ({
	availableLocales = [],
	defaultLocale = INITIAL_DEFAULT_LOCALE,
	displayStyle = 'singleline',
	editingLocale = INITIAL_EDITING_LOCALE,
	fieldName,
	id,
	name,
	onFieldBlurred,
	onFieldChanged = () => {},
	onFieldFocused,
	placeholder = '',
	placeholdersSubmitLabel = [],
	predefinedValue = '',
	readOnly,
	value,
}) => {
	const [currentAvailableLocales, setCurrentAvailableLocales] = useState(
		availableLocales
	);

	const [currentEditingLocale, setCurrentEditingLocale] = useState(
		editingLocale
	);

	const [currentValue, setCurrentValue] = useState(value);

	const [currentInternalValue, setCurrentInternalValue] = useState(
		getInitialInternalValue({editingLocale: currentEditingLocale, value})
	);

	const getPlaceholder = (currentEditingLocale) => {
		if (fieldName !== 'submitLabel') {
			return placeholder;
		}

		return placeholdersSubmitLabel.find(
			({localeId}) => localeId === currentEditingLocale.localeId
		).placeholderSubmitLabel;
	};

	const inputValue = currentInternalValue
		? currentInternalValue
		: predefinedValue;

	useEffect(() => {
		const translationManager = Liferay.component('translationManager');

		if (!translationManager) {
			return;
		}

		const newAvailableLocales = translationManager.get('availableLocales');

		const {availableLocales} = {
			...transformAvailableLocales(
				[...newAvailableLocales],
				defaultLocale,
				currentValue
			),
		};

		const newEditingLocale = transformEditingLocale({
			defaultLocale,
			editingLocale: newAvailableLocales.get(
				translationManager.get('editingLocale')
			),
			value: currentValue,
		});

		setCurrentAvailableLocales(availableLocales);

		setCurrentEditingLocale(newEditingLocale);

		setCurrentInternalValue(
			getEditingValue({
				defaultLocale,
				editingLocale: newEditingLocale,
				fieldName,
				value: currentValue,
			})
		);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [defaultLocale, fieldName]);

	return (
		<ClayInput.Group>
			<InputComponent
				displayStyle={displayStyle}
				fieldName={fieldName}
				id={id}
				inputValue={inputValue}
				name={name}
				onFieldBlurred={onFieldBlurred}
				onFieldChanged={(event) => {
					const {target} = event;
					const valueJSON = convertValueToJSON(currentValue);

					const newValue = JSON.stringify({
						...valueJSON,
						[currentEditingLocale.localeId]: target.value,
					});

					setCurrentValue(newValue);
					setCurrentInternalValue(target.value);

					const {availableLocales} = {
						...transformAvailableLocalesAndValue({
							availableLocales: currentAvailableLocales,
							defaultLocale,
							value: newValue,
						}),
					};

					setCurrentAvailableLocales(availableLocales);

					onFieldChanged({event, value: newValue});
				}}
				onFieldFocused={onFieldFocused}
				placeholder={getPlaceholder(currentEditingLocale)}
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
					availableLocales={currentAvailableLocales}
					editingLocale={currentEditingLocale}
					fieldName={fieldName}
					onLanguageClicked={(localeId) => {
						const newEditingLocale = currentAvailableLocales.find(
							(availableLocale) =>
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
								fieldName,
								value: currentValue,
							})
						);
					}}
				/>
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
};

const Main = ({
	availableLocales,
	defaultLocale,
	displayStyle,
	editingLocale,
	fieldName,
	id,
	name,
	onBlur,
	onChange,
	onFocus,
	placeholder,
	placeholdersSubmitLabel,
	predefinedValue,
	readOnly,
	value = {},
	...otherProps
}) => (
	<FieldBase {...otherProps} id={id} name={name} readOnly={readOnly}>
		<LocalizableText
			{...transformAvailableLocalesAndValue({
				availableLocales,
				defaultLocale,
				value,
			})}
			displayStyle={displayStyle}
			editingLocale={editingLocale}
			fieldName={fieldName}
			id={id}
			name={name}
			onFieldBlurred={onBlur}
			onFieldChanged={({event, value}) => onChange(event, value)}
			onFieldFocused={onFocus}
			placeholder={placeholder}
			placeholdersSubmitLabel={placeholdersSubmitLabel}
			predefinedValue={predefinedValue}
			readOnly={readOnly}
		/>
	</FieldBase>
);

Main.displayName = 'LocalizableText';

export default Main;
