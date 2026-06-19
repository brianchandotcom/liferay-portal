/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {
	cleanup,
	fireEvent,
	render,
	screen,
	waitFor,
} from '@testing-library/react';
import React from 'react';

import AgentDefinitionForm from '../../../src/main/resources/META-INF/resources/js/agent_definition_form/AgentDefinitionForm';

const mockDisassociateAgentDefinitionFromContentRetriever = jest.fn();
const mockDisassociateAgentDefinitionFromGuardrail = jest.fn();
const mockGetAgentDefinition = jest.fn();
const mockGetContentRetrievers = jest.fn();
const mockGetGuardrails = jest.fn();
const mockGetWorkflowDefinition = jest.fn();
const mockGetWorkflowDefinitions = jest.fn();
const mockOpenToast = jest.fn();
const mockPostAgentDefinition = jest.fn();
const mockPutAgentDefinition = jest.fn();
const mockPutAgentDefinitionToContentRetrievers = jest.fn();
const mockPutAgentDefinitionToGuardrails = jest.fn();

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/agent_definition_form/services/AgentDefinitionService',
	() => ({
		disassociateAgentDefinitionFromContentRetriever: (...args: any[]) =>
			mockDisassociateAgentDefinitionFromContentRetriever(...args),
		disassociateAgentDefinitionFromGuardrail: (...args: any[]) =>
			mockDisassociateAgentDefinitionFromGuardrail(...args),
		getAgentDefinition: (...args: any[]) => mockGetAgentDefinition(...args),
		postAgentDefinition: (...args: any[]) =>
			mockPostAgentDefinition(...args),
		putAgentDefinition: (...args: any[]) => mockPutAgentDefinition(...args),
		putAgentDefinitionToContentRetrievers: (...args: any[]) =>
			mockPutAgentDefinitionToContentRetrievers(...args),
		putAgentDefinitionToGuardrails: (...args: any[]) =>
			mockPutAgentDefinitionToGuardrails(...args),
	})
);

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/agent_definition_form/services/ContentRetrieverService',
	() => ({
		getContentRetrievers: (...args: any[]) =>
			mockGetContentRetrievers(...args),
	})
);

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/agent_definition_form/services/GuardrailService',
	() => ({
		getGuardrails: (...args: any[]) => mockGetGuardrails(...args),
	})
);

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/agent_definition_form/services/WorkflowDefinitionService',
	() => ({
		getWorkflowDefinition: (...args: any[]) =>
			mockGetWorkflowDefinition(...args),
		getWorkflowDefinitions: (...args: any[]) =>
			mockGetWorkflowDefinitions(...args),
	})
);

jest.mock('@liferay/object-js-components-web', () => ({
	openToast: (...args: any[]) => mockOpenToast(...args),
}));

jest.mock('uuid', () => ({v4: () => 'GENERATED_UUID'}));

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

jest.mock('@clayui/multi-select', () => {
	const React = require('react');

	return {
		__esModule: true,
		default: ({inputName}: any) =>
			React.createElement('div', {
				'data-testid': `multi-select-${inputName}`,
			}),
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

const defaultProps = {
	accountEntryExternalReferenceCode: 'ACCOUNT',
	backURL: '/back',
	externalReferenceCode: '',
	readOnly: false,
	workflowDefinitionURL: '/workflow-url',
};

describe('AgentDefinitionForm', () => {
	beforeEach(() => {
		mockDisassociateAgentDefinitionFromContentRetriever.mockReset();
		mockDisassociateAgentDefinitionFromGuardrail.mockReset();
		mockGetAgentDefinition.mockReset();
		mockGetContentRetrievers.mockReset();
		mockGetGuardrails.mockReset();
		mockGetWorkflowDefinition.mockReset();
		mockGetWorkflowDefinitions.mockReset();
		mockOpenToast.mockReset();
		mockPostAgentDefinition.mockReset();
		mockPutAgentDefinition.mockReset();
		mockPutAgentDefinitionToContentRetrievers.mockReset();
		mockPutAgentDefinitionToGuardrails.mockReset();

		mockGetContentRetrievers.mockResolvedValue({items: []});
		mockGetGuardrails.mockResolvedValue({items: []});
		mockGetWorkflowDefinitions.mockResolvedValue({items: []});
	});

	afterEach(() => {
		cleanup();
	});

	describe('panels', () => {
		it('hydrates panel inputs after the fetch resolves', async () => {
			mockGetAgentDefinition.mockResolvedValueOnce({
				active: true,
				agentDefinitionsToContentRetrievers: [],
				aiHubAgentDefinitionsToAIHubGuardrails: [],
				description: 'Loaded',
				externalReferenceCode: 'AGENT_X',
				inputVariables: 'in',
				outputVariable: 'out',
				title_i18n: {en_US: 'Loaded From API'},
				workflowDefinitionName: '',
			});

			render(
				<AgentDefinitionForm
					{...defaultProps}
					externalReferenceCode="AGENT_X"
				/>
			);

			await waitFor(() => {
				expect(
					screen.getByDisplayValue('Loaded From API')
				).toBeInTheDocument();
			});
		});

		it('renders every panel for a fresh form', () => {
			render(<AgentDefinitionForm {...defaultProps} />);

			expect(screen.getByText('details')).toBeInTheDocument();
			expect(screen.getByText('workflow')).toBeInTheDocument();
			expect(screen.getByText('variables')).toBeInTheDocument();
			expect(screen.getByText('data-sources')).toBeInTheDocument();
			expect(screen.getByText('guardrails')).toBeInTheDocument();
		});
	});

	describe('save', () => {
		it('blocks the submit and surfaces required-field errors when all fields are empty', async () => {
			render(<AgentDefinitionForm {...defaultProps} />);

			fireEvent.click(screen.getByRole('button', {name: 'save'}));

			await waitFor(() => {
				expect(screen.getAllByText('required').length).toBeGreaterThan(
					0
				);
			});

			expect(mockPostAgentDefinition).not.toHaveBeenCalled();
		});

		it('submits filled values and shows the success toast', async () => {
			mockGetWorkflowDefinitions.mockResolvedValue({
				items: [
					{
						name: 'wf-1',
						title: 'Workflow 1',
					},
				],
			});
			mockPostAgentDefinition.mockResolvedValueOnce({
				externalReferenceCode: 'AGENT_X',
				status: {label: 'approved'},
			});

			render(<AgentDefinitionForm {...defaultProps} />);

			fireEvent.change(screen.getByLabelText(/^title/i), {
				target: {value: 'My Agent'},
			});
			fireEvent.change(
				screen.getByLabelText(/^external-reference-code/i),
				{target: {value: 'AGENT_X'}}
			);
			fireEvent.change(screen.getByLabelText(/^description/i), {
				target: {value: 'A description'},
			});
			fireEvent.change(screen.getByLabelText(/^input-variables/i), {
				target: {value: 'a,b'},
			});
			fireEvent.change(screen.getByLabelText(/^output-variable/i), {
				target: {value: 'out'},
			});

			await waitFor(() => {
				expect(screen.getByText('Workflow 1')).toBeInTheDocument();
			});

			fireEvent.change(screen.getByTestId('workflowDefinitionName'), {
				target: {value: 'wf-1'},
			});

			fireEvent.click(screen.getByRole('button', {name: 'save'}));

			await waitFor(() => {
				expect(mockPostAgentDefinition).toHaveBeenCalledWith(
					expect.objectContaining({
						description: 'A description',
						externalReferenceCode: 'AGENT_X',
						inputVariables: 'a,b',
						outputVariable: 'out',
						title_i18n: {en_US: 'My Agent'},
						workflowDefinitionName: 'wf-1',
					})
				);
			});

			await waitFor(() => {
				expect(mockOpenToast).toHaveBeenCalledWith(
					expect.objectContaining({type: 'success'})
				);
			});
		});
	});

	describe('toolbar', () => {
		it('disables the save button when readOnly is true', () => {
			render(<AgentDefinitionForm {...defaultProps} readOnly={true} />);

			expect(screen.getByRole('button', {name: 'save'})).toBeDisabled();
		});

		it('exposes a Cancel link that points at backURL', () => {
			render(
				<AgentDefinitionForm {...defaultProps} backURL="/back-here" />
			);

			const cancel = screen.getByRole('link', {name: 'cancel'});

			expect(cancel).toHaveAttribute('href', '/back-here');
		});
	});

	describe('workflow fetching', () => {
		it('fetches the full workflow list when readOnly is false', async () => {
			render(<AgentDefinitionForm {...defaultProps} readOnly={false} />);

			await waitFor(() => {
				expect(mockGetWorkflowDefinitions).toHaveBeenCalled();
			});

			expect(mockGetWorkflowDefinition).not.toHaveBeenCalled();
		});

		it('fetches the single workflow by name when readOnly is true and the name is set', async () => {
			mockGetAgentDefinition.mockResolvedValueOnce({
				agentDefinitionsToContentRetrievers: [],
				aiHubAgentDefinitionsToAIHubGuardrails: [],
				externalReferenceCode: 'AGENT_X',
				title_i18n: {en_US: 'Loaded'},
				workflowDefinitionName: 'wf-1',
			});
			mockGetWorkflowDefinition.mockResolvedValueOnce({
				name: 'wf-1',
				title: 'Workflow 1',
			});

			render(
				<AgentDefinitionForm
					{...defaultProps}
					externalReferenceCode="AGENT_X"
					readOnly={true}
				/>
			);

			await waitFor(() => {
				expect(mockGetWorkflowDefinition).toHaveBeenCalledWith('wf-1');
			});

			expect(mockGetWorkflowDefinitions).not.toHaveBeenCalled();
		});
	});
});
