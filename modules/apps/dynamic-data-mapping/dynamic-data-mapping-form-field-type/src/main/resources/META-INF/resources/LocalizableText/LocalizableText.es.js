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
import React, {useRef, useState} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import InputComponent from './InputComponent.es';
import {
	convertValueToJSON,
	getEditingValue,
	getInitialInternalValue,
	normalizeLocaleId,
	transformAvailableLocalesAndValue,
} from './transform.es';

const INITIAL_DEFAULT_LOCALE = {
	icon: themeDisplay.getDefaultLanguageId(),
	localeId: themeDisplay.getDefaultLanguageId(),
};
const INITIAL_EDITING_LOCALE = {
	icon: normalizeLocaleId(themeDisplay.getDefaultLanguageId()),
	localeId: themeDisplay.getDefaultLanguageId(),
};

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
				onFieldChanged={(event) => {
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
	id,
	name,
	onBlur,
	onChange,
	onFocus,
	placeholder,
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
			id={id}
			name={name}
			onFieldBlurred={onBlur}
			onFieldChanged={({event, value}) => onChange(event, value)}
			onFieldFocused={onFocus}
			placeholder={placeholder}
			predefinedValue={predefinedValue}
			readOnly={readOnly}
		/>
	</FieldBase>
);

Main.displayName = 'LocalizableText';

export default Main;
