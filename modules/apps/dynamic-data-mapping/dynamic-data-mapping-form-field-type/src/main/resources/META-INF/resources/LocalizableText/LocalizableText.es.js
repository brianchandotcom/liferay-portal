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
import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import {usePage} from 'data-engine-js-components-web';
import {delegate} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import InputComponent from './InputComponent.es';
import {
	convertValueToJSON,
	getEditingValue,
	getInitialInternalValue,
	normalizeLocaleId,
	transformAvailableLocales,
	transformAvailableLocalesAndValue,
	transformEditingLocale,
} from './transform.es';

const INITIAL_DEFAULT_LOCALE = {
	icon: themeDisplay.getDefaultLanguageId(),
	localeId: themeDisplay.getDefaultLanguageId(),
};
const INITIAL_EDITING_LOCALE = {
	icon: normalizeLocaleId(themeDisplay.getDefaultLanguageId()),
	localeId: themeDisplay.getDefaultLanguageId(),
};

const AvailableLocaleLabel = ({isDefault, isSubmitLabel, isTranslated}) => {
	let labelText = '';

	if (isSubmitLabel) {
		labelText = isTranslated ? 'customized' : 'not-customized';
	}
	else {
		labelText = isDefault
			? 'default'
			: isTranslated
			? 'translated'
			: 'not-translated';
	}

	return (
		<ClayLabel
			displayType={classNames({
				info: isDefault && !isSubmitLabel,
				success: isTranslated,
				warning:
					(!isDefault && !isTranslated) ||
					(!isTranslated && isSubmitLabel),
			})}
		>
			{Liferay.Language.get(labelText)}
		</ClayLabel>
	);
};

const LocalesDropdown = ({
	availableLocales,
	editingLocale,
	fieldName,
	onLanguageClicked = () => {},
}) => {
	const alignElementRef = useRef(null);
	const dropdownMenuRef = useRef(null);

	const [dropdownActive, setDropdownActive] = useState(false);

	return (
		<div>
			<ClayButton
				aria-expanded="false"
				aria-haspopup="true"
				className="dropdown-toggle"
				data-testid="triggerButton"
				displayType="secondary"
				monospaced
				onClick={() => setDropdownActive(!dropdownActive)}
				ref={alignElementRef}
			>
				<span className="inline-item">
					<ClayIcon symbol={editingLocale.icon} />
				</span>
				<span className="btn-section" data-testid="triggerText">
					{editingLocale.icon}
				</span>
			</ClayButton>
			<ClayDropDown.Menu
				active={dropdownActive}
				alignElementRef={alignElementRef}
				onSetActive={setDropdownActive}
				ref={dropdownMenuRef}
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
								className="custom-dropdown-item-row"
								data-testid={`availableLocalesDropdown${localeId}`}
								key={localeId}
								onClick={(event) => {
									onLanguageClicked({event, localeId});
									setDropdownActive(false);
								}}
							>
								<ClayLayout.ContentRow containerElement="span">
									<ClayLayout.ContentCol
										containerElement="span"
										expand
									>
										<ClayLayout.ContentSection containerElement="span">
											<span className="inline-item inline-item-before">
												<ClayIcon symbol={icon} />
											</span>
											{displayName}
										</ClayLayout.ContentSection>
									</ClayLayout.ContentCol>

									<ClayLayout.ContentCol containerElement="span">
										<AvailableLocaleLabel
											isDefault={isDefault}
											isSubmitLabel={
												fieldName === 'submitLabel'
											}
											isTranslated={isTranslated}
										/>
									</ClayLayout.ContentCol>
								</ClayLayout.ContentRow>
							</ClayDropDown.Item>
						)
					)}
				</ClayDropDown.ItemList>
			</ClayDropDown.Menu>
		</div>
	);
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

	const {portletNamespace} = usePage();

	useEffect(() => {
		const onClickDDMFormSettingsButton = function () {
			const translationManager = Liferay.component('translationManager');

			const newAvailableLocales = translationManager.get(
				'availableLocales'
			);

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
		};

		const clickDDMFormSettingsButton = delegate(
			document.body,
			'click',
			`#${portletNamespace}ddmFormInstanceSettingsIcon`,
			onClickDDMFormSettingsButton
		);

		return () => clickDDMFormSettingsButton.dispose();
	}, [
		currentAvailableLocales,
		currentValue,
		defaultLocale,
		fieldName,
		portletNamespace,
	]);

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
					onLanguageClicked={({localeId}) => {
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
