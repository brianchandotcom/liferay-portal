/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {cleanup, fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import WorkflowPanel from '../../../src/main/resources/META-INF/resources/js/agent_definition_form/WorkflowPanel';

import type {AgentDefinition} from '../../../src/main/resources/META-INF/resources/js/agent_definition_form/types/AgentDefinition';
import type {WorkflowDefinition} from '../../../src/main/resources/META-INF/resources/js/agent_definition_form/types/WorkflowDefinition';

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

const workflowDefinitions: WorkflowDefinition[] = [
	{name: 'wf-1', title: 'Workflow 1'} as WorkflowDefinition,
	{name: 'wf-2', title: 'Workflow 2'} as WorkflowDefinition,
];

function renderPanel(
	overrides: Partial<{
		errors: any;
		readOnly: boolean;
		touched: any;
		values: AgentDefinition;
	}> = {}
) {
	const setField = jest.fn();
	const setFieldTouched = jest.fn();

	render(
		<WorkflowPanel
			errors={overrides.errors || {}}
			readOnly={overrides.readOnly ?? false}
			setField={setField}
			setFieldTouched={setFieldTouched}
			touched={overrides.touched ?? {workflowDefinitionName: true}}
			values={overrides.values || baseValues}
			workflowDefinitionURL="/workflow-url"
			workflowDefinitions={workflowDefinitions}
		/>
	);

	return {setField, setFieldTouched};
}

describe('WorkflowPanel', () => {
	afterEach(() => {
		cleanup();
	});

	describe('workflowDefinition picker', () => {
		it('forwards the selected key via setField', () => {
			const {setField} = renderPanel();

			fireEvent.change(screen.getByTestId('workflowDefinitionName'), {
				target: {value: 'wf-2'},
			});

			expect(setField).toHaveBeenCalledWith(
				'workflowDefinitionName',
				'wf-2'
			);
		});

		it('lists the workflow definitions as options', () => {
			renderPanel();

			expect(screen.getByText('Workflow 1')).toBeInTheDocument();
			expect(screen.getByText('Workflow 2')).toBeInTheDocument();
		});

		it('marks the field as touched on selection change', () => {
			const {setFieldTouched} = renderPanel();

			fireEvent.change(screen.getByTestId('workflowDefinitionName'), {
				target: {value: 'wf-1'},
			});

			expect(setFieldTouched).toHaveBeenCalledWith(
				'workflowDefinitionName',
				true
			);
		});

		it('reflects the current workflow name as the picker selection', () => {
			renderPanel({
				values: {...baseValues, workflowDefinitionName: 'wf-2'},
			});

			expect(screen.getByTestId('workflowDefinitionName')).toHaveValue(
				'wf-2'
			);
		});

		it('surfaces the validation error when present', () => {
			renderPanel({
				errors: {workflowDefinitionName: 'Workflow required'},
			});

			expect(screen.getByText('Workflow required')).toBeInTheDocument();
		});
	});

	describe('view-workflow button', () => {
		it('navigates to the workflow URL when clicked', () => {
			const originalLocation = window.location;

			Object.defineProperty(window, 'location', {
				configurable: true,
				value: {href: ''},
				writable: true,
			});

			renderPanel();

			fireEvent.click(
				screen.getByRole('button', {name: /view-workflow/i})
			);

			expect(window.location.href).toBe('/workflow-url');

			Object.defineProperty(window, 'location', {
				configurable: true,
				value: originalLocation,
				writable: true,
			});
		});
	});
});
