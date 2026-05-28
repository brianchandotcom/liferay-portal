/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {cleanup, fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import DetailsPanel from '../../../src/main/resources/META-INF/resources/js/agent_definition_form/DetailsPanel';

import type {AgentDefinition} from '../../../src/main/resources/META-INF/resources/js/agent_definition_form/types/AgentDefinition';

jest.mock('frontend-js-components-web', () => {
	const React = require('react');

	return {
		FieldBase: ({children, errorMessage, id, label, required}: any) =>
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
				errorMessage && React.createElement('div', null, errorMessage)
			),
		InputLocalized: ({error, id, label, onChange, translations}: any) =>
			React.createElement(
				React.Fragment,
				null,
				React.createElement('label', {htmlFor: id}, label),
				React.createElement('input', {
					id,
					onChange: (event: any) =>
						onChange({en_US: event.target.value}),
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

const baseValues: AgentDefinition = {
	active: false,
	description: '',
	externalReferenceCode: '',
	inputVariables: '',
	outputVariable: '',
	r_accountToAIHubAgentDefinitions_accountEntryERC: 'ACCOUNT',
	title_i18n: {},
	workflowDefinitionName: '',
};

function renderPanel(
	overrides: Partial<{
		errors: any;
		readOnly: boolean;
		touched: any;
		values: AgentDefinition;
	}> = {}
) {
	const handleBlur = jest.fn();
	const setField = jest.fn();
	const setFieldTouched = jest.fn();

	render(
		<DetailsPanel
			errors={overrides.errors || {}}
			handleBlur={handleBlur}
			readOnly={overrides.readOnly ?? false}
			setField={setField}
			setFieldTouched={setFieldTouched}
			touched={
				overrides.touched ?? {
					description: true,
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
				values: {...baseValues, active: false},
			});

			fireEvent.click(screen.getByRole('switch', {name: 'enable-agent'}));

			expect(setField).toHaveBeenCalledWith('active', true);
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

	describe('externalReferenceCode input', () => {
		it('calls setField with the typed value', () => {
			const {setField} = renderPanel();

			fireEvent.change(
				screen.getByLabelText(/^external-reference-code/i),
				{target: {value: 'AGENT-X'}}
			);

			expect(setField).toHaveBeenCalledWith(
				'externalReferenceCode',
				'AGENT-X'
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

		it('surfaces the validation error when present', () => {
			renderPanel({errors: {description: 'Description required'}});

			expect(
				screen.getByText('Description required')
			).toBeInTheDocument();
		});
	});

	describe('readOnly', () => {
		it('disables every editable input', () => {
			renderPanel({readOnly: true});

			expect(
				screen.getByLabelText(/^external-reference-code/i)
			).toBeDisabled();
			expect(screen.getByLabelText(/^description/i)).toBeDisabled();
		});
	});
});
