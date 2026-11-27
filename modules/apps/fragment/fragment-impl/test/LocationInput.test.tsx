/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {act, render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {LocationInput} from '../src/main/resources/META-INF/resources/js/api/LocationInput';

const BARCELONA =
	'{"address":"Barcelona","coordinates":{"latitude":41.38,"longitude":2.17}}';

const MADRID =
	'{"address":"Madrid","coordinates":{"latitude":40.41,"longitude":-3.7}}';

// The map needs a real browser, so the picker is replaced with a button that
// picks Barcelona.

jest.mock('@liferay/object-js-components-web', () => ({
	LocationInput: ({
		children,
		disabled,
		id,
		mapProviderKey,
		onLocationChange,
		placeholder,
		serializedValue,
	}: {
		children?: React.ReactNode;
		disabled?: boolean;
		id: string;
		mapProviderKey?: string;
		onLocationChange: (locationValue: object) => void;
		placeholder?: string;
		serializedValue?: string;
	}) => (
		<div data-testid="location-picker">
			<button
				data-map-provider-key={mapProviderKey}
				data-placeholder={placeholder}
				data-serialized-value={serializedValue}
				disabled={disabled}
				id={id}
				onClick={() =>
					onLocationChange({
						address: 'Barcelona',
						coordinates: {latitude: 41.38, longitude: 2.17},
					})
				}
			>
				pick
			</button>

			{children}
		</div>
	),
	stringifyLocationValue: (locationValue: object) =>
		JSON.stringify(locationValue),
}));

// Only `LocalizationSelect` reaches CKEditor, which Jest cannot parse.

jest.mock(
	'../src/main/resources/META-INF/resources/js/api/LocalizationSelect',
	() => ({
		EVENT_INPUT_REGISTERED: 'localizedInput:registered',
		EVENT_TRANSLATION_STATUS: 'localizedInput:translationStatus',
		getSelectedLanguageId: () => null,
	})
);

const NAME = 'ObjectField_location';
const NAMESPACE = 'fragment-1';

function setUpFragment() {
	document.body.innerHTML = `
		<div class="location-input">
			<span class="d-none" id="${NAMESPACE}-location-read-only">(read-only)</span>

			<div id="${NAMESPACE}-location-input-container"></div>

			<input class="sr-only" id="${NAMESPACE}-location-input-value" name="${NAME}" type="text" />

			<span id="${NAMESPACE}-location-input-translations"></span>

			<div class="d-none" id="${NAMESPACE}-unlocalized-info"></div>
		</div>`;

	return document.getElementById(`${NAMESPACE}-location-input-container`)!;
}

function renderLocationInput(
	props: Partial<React.ComponentProps<typeof LocationInput>> = {}
) {
	const container = setUpFragment();

	render(
		<LocationInput
			defaultLanguageId="en_US"
			name={NAME}
			namespace={NAMESPACE}
			readOnlyLabelId={`${NAMESPACE}-location-read-only`}
			{...props}
		/>,
		{container}
	);
}

function getContent() {
	return document.querySelector('.location-input-content')!;
}

function getPicker() {
	return screen.getByRole('button', {name: 'pick'});
}

function getValueInput() {
	return document.getElementById(
		`${NAMESPACE}-location-input-value`
	) as HTMLInputElement;
}

/**
 * `Liferay.on` is a stub, so hand the event to the registered listener.
 */

function fireLocalizationEvent(
	event: string,
	payload: Record<string, unknown>
) {
	const call = (Liferay.on as jest.Mock).mock.calls.find(
		([name]) => name === `localizationSelect:${event}`
	);

	act(() => call![1](payload));
}

/**
 * A missing `value` attribute is how the portal marks a language untranslated.
 */

function getTranslation(languageId: string) {
	return document.getElementById(
		`${NAMESPACE}${NAMESPACE}-location-input-value_${languageId}`
	) as HTMLInputElement | null;
}

function escapeQuotes(value: string) {
	return value.replace(/"/g, '&quot;');
}

describe('LocationInput', () => {

	// Dropped so each test reaches the listener its own render made.

	beforeEach(() => {
		(Liferay.on as jest.Mock).mockClear();
	});

	describe('Value', () => {
		it('submits the picked location as the Objects JSON string', async () => {
			renderLocationInput({mapProviderKey: 'OpenStreetMap'});

			expect(getPicker()).toHaveAttribute(
				'data-map-provider-key',
				'OpenStreetMap'
			);
			expect(getPicker()).toHaveAttribute('data-serialized-value', '');

			await userEvent.click(getPicker());

			expect(getValueInput()).toHaveValue(BARCELONA);
			expect(getPicker()).toHaveAttribute(
				'data-serialized-value',
				BARCELONA
			);
		});

		it('notifies the form when a location is picked', async () => {
			renderLocationInput();

			const listener = jest.fn();

			document.body.addEventListener('change', listener);

			await userEvent.click(getPicker());

			document.body.removeEventListener('change', listener);

			expect(listener).toHaveBeenCalledTimes(1);
			expect(listener.mock.calls[0][0].target).toBe(getValueInput());
		});

		it('shows the saved location that the server escaped', () => {
			renderLocationInput({value: escapeQuotes(MADRID)});

			expect(getPicker()).toHaveAttribute(
				'data-serialized-value',
				MADRID
			);
			expect(getValueInput()).toHaveValue(MADRID);
		});

		it('submits nothing when the server sends a null value', () => {
			renderLocationInput({value: 'null'});

			expect(getPicker()).toHaveAttribute('data-serialized-value', '');
			expect(getValueInput()).toHaveValue('');
		});

		it('passes the placeholder to the address input', () => {
			renderLocationInput({placeholder: 'Search an address'});

			expect(getPicker()).toHaveAttribute(
				'data-placeholder',
				'Search an address'
			);
		});

		it('moves the unlocalized icon next to the address input', () => {
			renderLocationInput();

			expect(
				screen
					.getByTestId('location-picker')
					.contains(
						document.getElementById(`${NAMESPACE}-unlocalized-info`)
					)
			).toBe(true);
		});
	});

	describe('States', () => {
		it('disables and fades the picker', () => {
			renderLocationInput({disabled: true});

			expect(getPicker()).toBeDisabled();
			expect(getContent()).toHaveClass(
				'location-input--disabled',
				'location-input--inert',
				'location-input--locked'
			);
		});

		it('locks the picker without fading it in edit mode', () => {
			renderLocationInput({editMode: true});

			expect(getPicker()).toBeDisabled();
			expect(getContent()).toHaveClass(
				'location-input--inert',
				'location-input--locked'
			);
			expect(getContent()).not.toHaveClass('location-input--disabled');
		});

		it('locks the picker without fading it when read only', () => {
			renderLocationInput({readOnly: true});

			expect(getPicker()).toBeDisabled();
			expect(getContent()).toHaveClass('location-input--locked');
			expect(getContent()).not.toHaveClass('location-input--disabled');
			expect(getContent()).not.toHaveClass('location-input--inert');
		});

		it('leaves the picker editable by default', () => {
			renderLocationInput();

			expect(getPicker()).toBeEnabled();
			expect(getContent()).not.toHaveClass('location-input--disabled');
			expect(getContent()).not.toHaveClass('location-input--inert');
			expect(getContent()).not.toHaveClass('location-input--locked');
		});
	});

	describe('Unlocalized', () => {
		it('disables and fades the picker on a language it cannot translate', () => {
			renderLocationInput({unlocalizedFieldsState: 'disabled'});

			fireLocalizationEvent('localeChanged', {languageId: 'es_ES'});

			expect(getPicker()).toBeDisabled();
			expect(getContent()).toHaveClass(
				'location-input--disabled',
				'location-input--inert',
				'location-input--locked'
			);
		});

		it('locks the picker without fading it on a read only language', () => {
			renderLocationInput({unlocalizedFieldsState: 'read-only'});

			fireLocalizationEvent('localeChanged', {languageId: 'es_ES'});

			expect(getPicker()).toBeDisabled();
			expect(getContent()).toHaveClass('location-input--locked');
			expect(getContent()).not.toHaveClass('location-input--disabled');
		});

		it('enables the picker again on the default language', () => {
			renderLocationInput();

			fireLocalizationEvent('localeChanged', {languageId: 'es_ES'});
			fireLocalizationEvent('localeChanged', {languageId: 'en_US'});

			expect(getPicker()).toBeEnabled();
			expect(getContent()).not.toHaveClass('location-input--locked');
		});
	});

	describe('Localization', () => {
		const renderLocalized = (valueI18n: Record<string, string>) =>
			renderLocationInput({
				availableLanguageIds: ['en_US', 'es_ES'],
				localizable: true,
				value: valueI18n.en_US ?? '',
				valueI18n,
			});

		it('falls back to the default language in an untranslated language', () => {
			renderLocalized({en_US: escapeQuotes(MADRID)});

			fireLocalizationEvent('localeChanged', {languageId: 'es_ES'});

			expect(getPicker()).toHaveAttribute(
				'data-serialized-value',
				MADRID
			);
			expect(getValueInput()).toHaveValue(MADRID);
		});

		it('shows the translation of a translated language', () => {
			renderLocalized({
				en_US: escapeQuotes(MADRID),
				es_ES: escapeQuotes(BARCELONA),
			});

			fireLocalizationEvent('localeChanged', {languageId: 'es_ES'});

			expect(getPicker()).toHaveAttribute(
				'data-serialized-value',
				BARCELONA
			);
			expect(getValueInput()).toHaveValue(BARCELONA);
		});

		it('keeps a location picked in one language on that language', async () => {
			renderLocalized({en_US: escapeQuotes(MADRID)});

			fireLocalizationEvent('localeChanged', {languageId: 'es_ES'});

			await userEvent.click(getPicker());

			expect(getTranslation('es_ES')).toHaveValue(BARCELONA);

			fireLocalizationEvent('localeChanged', {languageId: 'en_US'});

			expect(getPicker()).toHaveAttribute(
				'data-serialized-value',
				MADRID
			);
		});

		it('does not translate a language that was only visited', () => {
			renderLocalized({en_US: escapeQuotes(MADRID)});

			fireLocalizationEvent('localeChanged', {languageId: 'es_ES'});

			expect(getTranslation('es_ES')).toBeNull();
		});

		it('shows an auto translation for the language on screen', () => {
			renderLocalized({en_US: escapeQuotes(MADRID)});

			fireLocalizationEvent('localeChanged', {languageId: 'es_ES'});
			fireLocalizationEvent('autoTranslate', {
				fields: {[NAME]: BARCELONA},
				languageId: 'es_ES',
			});

			expect(getPicker()).toHaveAttribute(
				'data-serialized-value',
				BARCELONA
			);
			expect(getTranslation('es_ES')).toHaveValue(BARCELONA);
		});
	});
});
