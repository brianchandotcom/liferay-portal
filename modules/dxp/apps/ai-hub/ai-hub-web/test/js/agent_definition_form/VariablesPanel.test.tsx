/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {cleanup, fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import VariablesPanel from '../../../src/main/resources/META-INF/resources/js/agent_definition_form/VariablesPanel';

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
	};
});

(global as any).Liferay = {
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

	render(
		<VariablesPanel
			errors={overrides.errors || {}}
			handleBlur={handleBlur}
			readOnly={overrides.readOnly ?? false}
			setField={setField}
			touched={
				overrides.touched ?? {
					inputVariables: true,
					outputVariable: true,
				}
			}
			values={overrides.values || baseValues}
		/>
	);

	return {handleBlur, setField};
}

describe('VariablesPanel', () => {
	afterEach(() => {
		cleanup();
	});

	describe('input-variables input', () => {
		it('calls setField with the typed value', () => {
			const {setField} = renderPanel();

			fireEvent.change(screen.getByLabelText(/^input-variables/i), {
				target: {value: 'a,b,c'},
			});

			expect(setField).toHaveBeenCalledWith('inputVariables', 'a,b,c');
		});

		it('surfaces the validation error when present', () => {
			renderPanel({errors: {inputVariables: 'Input required'}});

			expect(screen.getByText('Input required')).toBeInTheDocument();
		});
	});

	describe('output-variable input', () => {
		it('calls setField with the typed value', () => {
			const {setField} = renderPanel();

			fireEvent.change(screen.getByLabelText(/^output-variable/i), {
				target: {value: 'result'},
			});

			expect(setField).toHaveBeenCalledWith('outputVariable', 'result');
		});

		it('surfaces the validation error when present', () => {
			renderPanel({errors: {outputVariable: 'Output required'}});

			expect(screen.getByText('Output required')).toBeInTheDocument();
		});
	});

	describe('readOnly', () => {
		it('disables both inputs', () => {
			renderPanel({readOnly: true});

			expect(screen.getByLabelText(/^input-variables/i)).toBeDisabled();
			expect(screen.getByLabelText(/^output-variable/i)).toBeDisabled();
		});
	});
});
