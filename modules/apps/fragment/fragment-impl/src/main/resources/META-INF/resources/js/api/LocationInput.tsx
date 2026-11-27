/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	LocationInput as ObjectLocationInput,
	stringifyLocationValue,
} from '@liferay/object-js-components-web';
import classNames from 'classnames';
import {unescapeHTML} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import {focusInput} from './focusInput';
import {getTranslationInput} from './getTranslationInput';
import {registerLocalizedInput} from './registerLocalizedInput';
import {registerUnlocalizedInput} from './registerUnlocalizedInput';

import type {
	LocationValue,
	MapProviderKey,
} from '@liferay/object-js-components-web';

type OnChange = ReturnType<typeof registerLocalizedInput>['onChange'];

type Props = {
	availableLanguageIds?: string[];
	defaultLanguageId: Liferay.Language.Locale;
	disabled?: boolean;
	editMode?: boolean;
	focus?: boolean;
	googleMapsAPIKey?: string;
	localizable?: boolean;
	mapProviderKey?: MapProviderKey;
	name: string;
	namespace: string;
	placeholder?: string;
	readOnly?: boolean;
	readOnlyLabelId: string;
	unlocalizedFieldsState?: 'disabled' | 'read-only';
	value?: string;
	valueI18n?: Record<string, string>;
};

// The server escapes the value, which holds JSON with quotes.

function unescape(value?: string | null) {
	if (!value || value === 'null') {
		return '';
	}

	return unescapeHTML(value);
}

export function LocationInput({
	availableLanguageIds,
	defaultLanguageId,
	disabled,
	editMode,
	focus = false,
	googleMapsAPIKey,
	localizable = false,
	mapProviderKey,
	name,
	namespace,
	placeholder,
	readOnly,
	readOnlyLabelId,
	unlocalizedFieldsState = 'disabled',
	value,
	valueI18n,
}: Props) {
	const id = `${namespace}-location-input`;

	const [serializedValue, setSerializedValue] = useState(() =>
		unescape(value)
	);

	const [unlocalized, setUnlocalized] = useState(false);

	const onChangeRef = useRef<OnChange>();

	const registeredRef = useRef(false);

	const tooltipSlotRef = useRef<HTMLDivElement>(null);

	useEffect(() => {

		// Registering twice would attach a second set of Liferay listeners.

		if (registeredRef.current) {
			return;
		}

		registeredRef.current = true;

		const valueInput = document.getElementById(
			`${id}-value`
		) as HTMLInputElement;

		valueInput.value = unescape(value);

		if (!localizable) {
			registerUnlocalizedInput({
				changeTextDirection: false,
				customLocaleChangeHandler: true,
				defaultLanguageId,
				inputElement: valueInput,
				onLocaleChange: (languageId) =>
					setUnlocalized(languageId !== defaultLanguageId),
				readOnlyInputLabel: document.getElementById(
					readOnlyLabelId
				) as HTMLSpanElement,
				unlocalizedFieldsState,
				unlocalizedMessageContainer: document.getElementById(
					`${namespace}-unlocalized-info`
				)!,
			});

			return;
		}

		const localizationInputsContainer = document.getElementById(
			`${id}-translations`
		)!;

		let currentLanguageId: string = defaultLanguageId;

		const translationInput = (languageId: string) =>
			getTranslationInput({
				inputId: valueInput.id,
				inputName: name,
				languageId,
				localizationInputsContainer,
				namespace,
			});

		const setValue = (value = '') => {
			valueInput.value = value;

			setSerializedValue(value);
		};

		const initialValues = valueI18n
			? Object.fromEntries(
					Object.entries(valueI18n).map(([languageId, value]) => [
						languageId,
						unescape(value),
					])
				)
			: undefined;

		const {onChange} = registerLocalizedInput({
			availableLanguageIds,
			changeTextDirection: false,
			defaultLanguageId,
			initialValues,
			inputElement: valueInput,
			inputName: name,
			localizationInputsContainer,
			namespace,
			onAutoTranslate: ({languageId, value}) => {
				translationInput(languageId).value = value ?? '';

				if (languageId === currentLanguageId) {
					setValue(value);
				}
			},
			onLocaleChange: ({languageId, value}) => {
				currentLanguageId = languageId;

				setValue(value);
			},
			onMarkAsTranslated: () => {
				const value = translationInput(defaultLanguageId).value;

				translationInput(currentLanguageId).value = value;

				setValue(value);
			},
			onResetTranslation: () => {
				translationInput(currentLanguageId).removeAttribute('value');

				setValue(translationInput(defaultLanguageId).value);
			},
		});

		onChangeRef.current = onChange;
	}, [
		availableLanguageIds,
		defaultLanguageId,
		id,
		localizable,
		name,
		namespace,
		readOnlyLabelId,
		unlocalizedFieldsState,
		value,
		valueI18n,
	]);

	useEffect(() => {
		if (!focus) {
			return;
		}

		const addressInput = document.getElementById(id);

		if (addressInput) {
			focusInput(addressInput as HTMLInputElement);
		}
		else {
			document
				.getElementById(`${id}-value`)
				?.closest('.form-group')
				?.scrollIntoView({block: 'center'});
		}
	}, [focus, id]);

	const handleLocationChange = (locationValue: LocationValue | null) => {
		const newValue = stringifyLocationValue(locationValue);

		const valueInput = document.getElementById(
			`${id}-value`
		) as HTMLInputElement;

		valueInput.value = newValue;

		valueInput.dispatchEvent(new Event('change', {bubbles: true}));

		setSerializedValue(newValue);

		onChangeRef.current?.(newValue);
	};

	// The unlocalized field icon is rendered by the fragment template, and is
	// moved next to the address input, or next to the map without it.

	useEffect(() => {
		const tooltip = document.getElementById(
			`${namespace}-unlocalized-info`
		);

		if (tooltip && tooltipSlotRef.current) {
			tooltipSlotRef.current.appendChild(tooltip);
		}
	}, [namespace]);

	const faded =
		disabled || (unlocalized && unlocalizedFieldsState === 'disabled');

	const locked = faded || editMode || readOnly || unlocalized;

	return (
		<div
			className={classNames(
				'd-flex flex-column flex-grow-1 location-input-content',
				{
					'location-input--disabled': faded,
					'location-input--inert': faded || editMode,
					'location-input--locked': locked,
				}
			)}
		>
			<ObjectLocationInput
				disabled={locked}
				googleMapsAPIKey={googleMapsAPIKey}
				id={id}
				mapProviderKey={mapProviderKey}
				onLocationChange={handleLocationChange}
				placeholder={placeholder}
				serializedValue={serializedValue}
			>
				<div
					className="input-group-item input-group-item-shrink pr-3"
					ref={tooltipSlotRef}
				/>
			</ObjectLocationInput>
		</div>
	);
}
