/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {cleanup, fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import DetailsPanel from '../../../src/main/resources/META-INF/resources/js/guardrail_form/DetailsPanel';

import type {Guardrail} from '../../../src/main/resources/META-INF/resources/js/guardrail_form/types/Guardrail';

jest.mock('@clayui/core', () => {
	const React = require('react');

	return {
		Option: ({children}: any) =>
			React.createElement(React.Fragment, null, children),
		Picker: ({id, items, onSelectionChange, selectedKey}: any) =>
			React.createElement(
				'select',
				{
					'data-testid': id,
					id,
					'onChange': (event: any) =>
						onSelectionChange(event.target.value),
					'value': selectedKey || '',
				},
				items.map((item: any) =>
					React.createElement(
						'option',
						{key: item.value, value: item.value},
						item.label
					)
				)
			),
	};
});

jest.mock('frontend-js-components-web', () => {
	const React = require('react');

	return {
		FieldBase: ({
			children,
			errorMessage,
			helpMessage,
			id,
			label,
			required,
		}: any) =>
			React.createElement(
				'div',
				null,
				label &&
					React.createElement(
						'label',
						{htmlFor: id},
						label,
						required && '*'
					),
				children,
				errorMessage && React.createElement('div', null, errorMessage),
				helpMessage && React.createElement('small', null, helpMessage)
			),
		InputLocalized: ({
			error,
			id,
			label,
			onChange,
			placeholder,
			translations,
		}: any) =>
			React.createElement(
				React.Fragment,
				null,
				React.createElement('label', {htmlFor: id}, label),
				React.createElement('input', {
					id,
					onChange: (event: any) =>
						onChange({en_US: event.target.value}),
					placeholder,
					value: translations?.en_US || '',
				}),
				error && React.createElement('div', null, error)
			),
	};
});

(global as any).Liferay = {
	Icons: {spritemap: 'icons.svg'},
	Language: {
		get: (key: string) => key,
	},
};

const baseValues: Guardrail = {
	active: true,
	description: '',
	externalReferenceCode: '',
	guardrailType: 'input',
	maliciousUriFilterEnabled: false,
	multilanguageDetectionEnabled: false,
	piAndJailbreakConfidenceLevel: 'mediumAndAbove',
	piAndJailbreakFilterEnabled: false,
	raiDangerousLevel: 'none',
	raiHarassmentLevel: 'none',
	raiHateSpeechLevel: 'none',
	raiSexuallyExplicitLevel: 'none',
	sdpFilterEnabled: false,
	title_i18n: {},
};

function renderPanel(
	overrides: Partial<{
		errors: any;
		touched: any;
		values: Guardrail;
	}> = {}
) {
	const handleBlur = jest.fn();
	const setField = jest.fn();
	const setFieldTouched = jest.fn();

	render(
		<DetailsPanel
			errors={overrides.errors || {}}
			handleBlur={handleBlur}
			readOnly={false}
			setField={setField}
			setFieldTouched={setFieldTouched}
			touched={
				overrides.touched ?? {
					externalReferenceCode: true,
					title_i18n: true,
				}
			}
			values={overrides.values || baseValues}
		/>
	);

	return {handleBlur, setField, setFieldTouched};
}

describe('DetailsPanel', () => {
	afterEach(() => {
		cleanup();
	});

	describe('active toggle', () => {
		it('flips the active value via setField when toggled', () => {
			const {setField} = renderPanel({
				values: {...baseValues, active: true},
			});

			fireEvent.click(screen.getByRole('switch', {name: 'active'}));

			expect(setField).toHaveBeenCalledWith('active', false);
		});
	});

	describe('description textarea', () => {
		it('calls setField on change', () => {
			const {setField} = renderPanel();

			fireEvent.change(screen.getByLabelText(/^description/i), {
				target: {value: 'A description'},
			});

			expect(setField).toHaveBeenCalledWith(
				'description',
				'A description'
			);
		});
	});

	describe('externalReferenceCode input', () => {
		it('calls setField with the typed value', () => {
			const {setField} = renderPanel();

			fireEvent.change(
				screen.getByLabelText(/^external-reference-code/i),
				{target: {value: 'TEMPLATE_X'}}
			);

			expect(setField).toHaveBeenCalledWith(
				'externalReferenceCode',
				'TEMPLATE_X'
			);
		});

		it('masks out a leading hyphen, spaces, and special characters', () => {
			const {setField} = renderPanel();

			fireEvent.change(
				screen.getByLabelText(/^external-reference-code/i),
				{target: {value: '-a b_c!1-2'}}
			);

			expect(setField).toHaveBeenCalledWith(
				'externalReferenceCode',
				'ab_c1-2'
			);
		});

		it('surfaces the validation error when present', () => {
			renderPanel({errors: {externalReferenceCode: 'ERC required'}});

			expect(screen.getByText('ERC required')).toBeInTheDocument();
		});
	});

	describe('guardrailType picker', () => {
		it('forwards the selected key via setField', () => {
			const {setField} = renderPanel();

			fireEvent.change(screen.getByTestId('guardrailType'), {
				target: {value: 'output'},
			});

			expect(setField).toHaveBeenCalledWith('guardrailType', 'output');
		});

		it('reflects the current guardrailType as the picker selection', () => {
			renderPanel({values: {...baseValues, guardrailType: 'output'}});

			expect(screen.getByTestId('guardrailType')).toHaveValue('output');
		});
	});

	describe('multi-language detection checkbox', () => {
		it('toggles the value via setField', () => {
			const {setField} = renderPanel({
				values: {...baseValues, multilanguageDetectionEnabled: false},
			});

			fireEvent.click(
				screen.getByRole('checkbox', {name: 'multilanguage-detection'})
			);

			expect(setField).toHaveBeenCalledWith(
				'multilanguageDetectionEnabled',
				true
			);
		});
	});

	describe('title input', () => {
		it('calls setField on each keystroke', () => {
			const {setField} = renderPanel();

			fireEvent.change(screen.getByLabelText(/^title/i), {
				target: {value: 'New Title'},
			});

			expect(setField).toHaveBeenCalledWith('title_i18n', {
				en_US: 'New Title',
			});
		});

		it('renders the current title value', () => {
			renderPanel({
				values: {
					...baseValues,
					title_i18n: {en_US: 'Existing Title'},
				},
			});

			expect(
				screen.getByDisplayValue('Existing Title')
			).toBeInTheDocument();
		});

		it('surfaces the validation error when present', () => {
			renderPanel({errors: {title_i18n: 'Required field'}});

			expect(screen.getByText('Required field')).toBeInTheDocument();
		});
	});
});
