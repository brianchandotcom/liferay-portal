/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, renderHook, waitFor} from '@testing-library/react';

import {useAgentDefinitionForm} from '../../../../src/main/resources/META-INF/resources/js/agent_definition_form/hooks/useAgentDefinitionForm';

const mockDeleteAgentDefinitionToContentRetrievers = jest.fn();
const mockDeleteAgentDefinitionToModelArmorTemplates = jest.fn();
const mockGetAgentDefinition = jest.fn();
const mockGetContentRetrievers = jest.fn();
const mockGetModelArmorTemplates = jest.fn();
const mockOpenToast = jest.fn();
const mockPostAgentDefinition = jest.fn();
const mockPutAgentDefinition = jest.fn();
const mockPutAgentDefinitionToContentRetrievers = jest.fn();
const mockPutAgentDefinitionToModelArmorTemplates = jest.fn();

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/agent_definition_form/services/AgentDefinitionService',
	() => ({
		deleteAgentDefinitionToContentRetrievers: (...args: any[]) =>
			mockDeleteAgentDefinitionToContentRetrievers(...args),
		deleteAgentDefinitionToModelArmorTemplates: (...args: any[]) =>
			mockDeleteAgentDefinitionToModelArmorTemplates(...args),
		getAgentDefinition: (...args: any[]) => mockGetAgentDefinition(...args),
		postAgentDefinition: (...args: any[]) =>
			mockPostAgentDefinition(...args),
		putAgentDefinition: (...args: any[]) => mockPutAgentDefinition(...args),
		putAgentDefinitionToContentRetrievers: (...args: any[]) =>
			mockPutAgentDefinitionToContentRetrievers(...args),
		putAgentDefinitionToModelArmorTemplates: (...args: any[]) =>
			mockPutAgentDefinitionToModelArmorTemplates(...args),
	})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/agent_definition_form/services/ContentRetrieverService',
	() => ({
		getContentRetrievers: (...args: any[]) =>
			mockGetContentRetrievers(...args),
	})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/agent_definition_form/services/ModelArmorTemplateService',
	() => ({
		getModelArmorTemplates: (...args: any[]) =>
			mockGetModelArmorTemplates(...args),
	})
);

jest.mock('@liferay/object-js-components-web', () => ({
	openToast: (...args: any[]) => mockOpenToast(...args),
}));

jest.mock('uuid', () => ({v4: () => 'GENERATED_UUID'}));

(global as any).Liferay = {
	Language: {
		get: (key: string) => key,
	},
};

function renderAgentHook({
	accountEntryExternalReferenceCode = 'ACCOUNT',
	externalReferenceCode = '',
	readOnly = false,
}: {
	accountEntryExternalReferenceCode?: string;
	externalReferenceCode?: string;
	readOnly?: boolean;
} = {}) {
	return renderHook(() =>
		useAgentDefinitionForm({
			accountEntryExternalReferenceCode,
			externalReferenceCode,
			readOnly,
		})
	);
}

async function fillRequiredFields(result: any) {
	await act(async () => {
		result.current.setField('title_i18n', {en_US: 'My Agent'});
		result.current.setField('externalReferenceCode', 'AGENT_X');
		result.current.setField('description', 'A description');
		result.current.setField('inputVariables', 'a,b');
		result.current.setField('outputVariable', 'out');
		result.current.setField('workflowDefinitionName', 'wf-1');
	});
}

describe('useAgentDefinitionForm', () => {
	beforeEach(() => {
		mockDeleteAgentDefinitionToContentRetrievers.mockReset();
		mockDeleteAgentDefinitionToModelArmorTemplates.mockReset();
		mockGetAgentDefinition.mockReset();
		mockGetContentRetrievers.mockReset();
		mockGetModelArmorTemplates.mockReset();
		mockOpenToast.mockReset();
		mockPostAgentDefinition.mockReset();
		mockPutAgentDefinition.mockReset();
		mockPutAgentDefinitionToContentRetrievers.mockReset();
		mockPutAgentDefinitionToModelArmorTemplates.mockReset();

		mockGetContentRetrievers.mockResolvedValue({items: []});
		mockGetModelArmorTemplates.mockResolvedValue({items: []});
	});

	describe('initial values', () => {
		it('applies the empty defaults', () => {
			const {result} = renderAgentHook();

			expect(result.current.values.active).toBe(false);
			expect(result.current.values.description).toBe('');
			expect(result.current.values.externalReferenceCode).toBe(
				'GENERATED_UUID'
			);
			expect(result.current.values.inputVariables).toBe('');
			expect(result.current.values.outputVariable).toBe('');
			expect(result.current.values.title_i18n).toEqual({});
			expect(result.current.values.workflowDefinitionName).toBe('');
		});

		it('seeds the account-relationship field from the prop', () => {
			const {result} = renderAgentHook({
				accountEntryExternalReferenceCode: 'ACCOUNT_42',
			});

			expect(
				result.current.values
					.r_accountToAIHubAgentDefinitions_accountEntryERC
			).toBe('ACCOUNT_42');
		});
	});

	describe('fetch lifecycle', () => {
		it('hydrates the relationship pickers from the nested fields', async () => {
			mockGetAgentDefinition.mockResolvedValueOnce({
				agentDefinitionsToContentRetrievers: [
					{externalReferenceCode: 'CR_1', title: 'First'},
					{externalReferenceCode: 'CR_2', title: 'Second'},
				],
				aiHubAgentDefinitionsToAIHubMATemplates: [
					{externalReferenceCode: 'MAT_1', title: 'Guard'},
				],
				externalReferenceCode: 'AGENT_X',
				title_i18n: {en_US: 'Loaded Title'},
			});

			const {result} = renderAgentHook({
				externalReferenceCode: 'AGENT_X',
			});

			await waitFor(() => {
				expect(result.current.contentRetrievers.selected).toHaveLength(
					2
				);
			});

			expect(result.current.modelArmorTemplates.selected).toHaveLength(1);

			await act(async () => {
				await result.current.contentRetrievers.sync('AGENT_X');
				await result.current.modelArmorTemplates.sync('AGENT_X');
			});

			expect(
				mockPutAgentDefinitionToContentRetrievers
			).not.toHaveBeenCalled();
			expect(
				mockDeleteAgentDefinitionToContentRetrievers
			).not.toHaveBeenCalled();
		});

		it('loads the agent definition and hydrates values on mount in edit mode', async () => {
			mockGetAgentDefinition.mockResolvedValueOnce({
				active: true,
				agentDefinitionsToContentRetrievers: [],
				aiHubAgentDefinitionsToAIHubMATemplates: [],
				description: 'Loaded from API',
				externalReferenceCode: 'AGENT_X',
				inputVariables: 'in1,in2',
				outputVariable: 'out',
				r_accountToAIHubAgentDefinitions_accountEntryERC: 'ACCOUNT',
				title_i18n: {en_US: 'Loaded Title'},
				workflowDefinitionName: 'wf-1',
			});

			const {result} = renderAgentHook({
				externalReferenceCode: 'AGENT_X',
			});

			await waitFor(() => {
				expect(result.current.values.title_i18n).toEqual({
					en_US: 'Loaded Title',
				});
			});

			expect(result.current.values.active).toBe(true);
			expect(result.current.values.description).toBe('Loaded from API');
			expect(result.current.values.inputVariables).toBe('in1,in2');
			expect(result.current.values.workflowDefinitionName).toBe('wf-1');
		});

		it('populates the source lists for both relationship pickers on mount', async () => {
			mockGetContentRetrievers.mockResolvedValueOnce({
				items: [
					{externalReferenceCode: 'CR_1', title: 'First'},
					{externalReferenceCode: 'CR_2', title: 'Second'},
				],
			});
			mockGetModelArmorTemplates.mockResolvedValueOnce({
				items: [{externalReferenceCode: 'MAT_1', title: 'Guard'}],
			});

			const {result} = renderAgentHook();

			await waitFor(() => {
				expect(
					result.current.contentRetrievers.sourceList
				).toHaveLength(2);
			});

			expect(result.current.modelArmorTemplates.sourceList).toHaveLength(
				1
			);
		});

		it('shows an error toast when the agent fetch rejects', async () => {
			mockGetAgentDefinition.mockRejectedValueOnce(new Error('Boom'));

			renderAgentHook({externalReferenceCode: 'AGENT_X'});

			await waitFor(() => {
				expect(mockOpenToast).toHaveBeenCalledWith(
					expect.objectContaining({
						message: 'failed-to-load-agent-data',
						type: 'danger',
					})
				);
			});
		});

		it('skips the agent fetch when externalReferenceCode is empty', async () => {
			renderAgentHook({externalReferenceCode: ''});

			await waitFor(() => {
				expect(mockGetContentRetrievers).toHaveBeenCalled();
			});

			expect(mockGetAgentDefinition).not.toHaveBeenCalled();
		});
	});

	describe('setField', () => {
		it('preserves type safety across boolean fields', async () => {
			const {result} = renderAgentHook();

			await act(async () => {
				result.current.setField('active', true);
			});

			expect(result.current.values.active).toBe(true);
		});

		it('updates one field without touching the rest', async () => {
			const {result} = renderAgentHook();

			await act(async () => {
				result.current.setField('title_i18n', {en_US: 'New Title'});
			});

			expect(result.current.values.title_i18n).toEqual({
				en_US: 'New Title',
			});
			expect(result.current.values.description).toBe('');
			expect(result.current.values.workflowDefinitionName).toBe('');
		});
	});

	describe('submit', () => {
		it('issues DELETE requests for removed relationships', async () => {
			mockGetAgentDefinition.mockResolvedValueOnce({
				agentDefinitionsToContentRetrievers: [
					{externalReferenceCode: 'CR_1'},
					{externalReferenceCode: 'CR_2'},
				],
				aiHubAgentDefinitionsToAIHubMATemplates: [
					{externalReferenceCode: 'MAT_1'},
				],
				description: 'desc',
				externalReferenceCode: 'AGENT_X',
				inputVariables: 'a',
				outputVariable: 'b',
				title_i18n: {en_US: 'My Agent'},
				workflowDefinitionName: 'wf-1',
			});
			mockPutAgentDefinition.mockResolvedValueOnce({
				externalReferenceCode: 'AGENT_X',
				status: {label: 'approved'},
			});
			mockDeleteAgentDefinitionToContentRetrievers.mockResolvedValue({});
			mockDeleteAgentDefinitionToModelArmorTemplates.mockResolvedValue(
				{}
			);

			const {result} = renderAgentHook({
				externalReferenceCode: 'AGENT_X',
			});

			await waitFor(() => {
				expect(result.current.contentRetrievers.selected).toHaveLength(
					2
				);
			});

			await act(async () => {
				result.current.contentRetrievers.setSelected([
					{externalReferenceCode: 'CR_1'},
				]);
				result.current.modelArmorTemplates.setSelected([]);
			});

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(
					mockDeleteAgentDefinitionToContentRetrievers
				).toHaveBeenCalledWith('AGENT_X', 'CR_2');
			});

			expect(
				mockDeleteAgentDefinitionToModelArmorTemplates
			).toHaveBeenCalledWith('AGENT_X', 'MAT_1');
		});

		it('issues PUT requests for added relationships only', async () => {
			mockGetAgentDefinition.mockResolvedValueOnce({
				agentDefinitionsToContentRetrievers: [
					{externalReferenceCode: 'CR_1'},
				],
				aiHubAgentDefinitionsToAIHubMATemplates: [],
				description: 'desc',
				externalReferenceCode: 'AGENT_X',
				inputVariables: 'a',
				outputVariable: 'b',
				title_i18n: {en_US: 'My Agent'},
				workflowDefinitionName: 'wf-1',
			});
			mockPutAgentDefinition.mockResolvedValueOnce({
				externalReferenceCode: 'AGENT_X',
				status: {label: 'approved'},
			});
			mockPutAgentDefinitionToContentRetrievers.mockResolvedValue({});
			mockPutAgentDefinitionToModelArmorTemplates.mockResolvedValue({});

			const {result} = renderAgentHook({
				externalReferenceCode: 'AGENT_X',
			});

			await waitFor(() => {
				expect(result.current.contentRetrievers.selected).toHaveLength(
					1
				);
			});

			await act(async () => {
				result.current.contentRetrievers.setSelected([
					{externalReferenceCode: 'CR_1'},
					{externalReferenceCode: 'CR_2'},
				]);
				result.current.modelArmorTemplates.setSelected([
					{externalReferenceCode: 'MAT_1'},
				]);
			});

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(
					mockPutAgentDefinitionToContentRetrievers
				).toHaveBeenCalledWith('AGENT_X', 'CR_2');
			});

			expect(
				mockPutAgentDefinitionToContentRetrievers
			).not.toHaveBeenCalledWith('AGENT_X', 'CR_1');
			expect(
				mockPutAgentDefinitionToModelArmorTemplates
			).toHaveBeenCalledWith('AGENT_X', 'MAT_1');
			expect(
				mockDeleteAgentDefinitionToContentRetrievers
			).not.toHaveBeenCalled();
		});

		it('shows a danger toast when the response status is not approved', async () => {
			mockPostAgentDefinition.mockResolvedValueOnce({
				externalReferenceCode: 'AGENT_X',
				status: {label: 'rejected'},
			});

			const {result} = renderAgentHook();

			await fillRequiredFields(result);

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(mockOpenToast).toHaveBeenCalledWith(
					expect.objectContaining({
						message: 'failed-to-save-agent',
						type: 'danger',
					})
				);
			});
		});

		it('shows the success toast when the API echoes back an approved status', async () => {
			mockPostAgentDefinition.mockResolvedValueOnce({
				externalReferenceCode: 'AGENT_X',
				status: {label: 'approved'},
			});

			const {result} = renderAgentHook();

			await fillRequiredFields(result);

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(mockOpenToast).toHaveBeenCalledWith(
					expect.objectContaining({
						message: 'agent-saved-successfully',
						type: 'success',
					})
				);
			});
		});

		it('shows the error message in a danger toast when the API call rejects', async () => {
			mockPostAgentDefinition.mockRejectedValueOnce(new Error('Boom'));

			const {result} = renderAgentHook();

			await fillRequiredFields(result);

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(mockOpenToast).toHaveBeenCalledWith(
					expect.objectContaining({
						message: 'Boom',
						type: 'danger',
					})
				);
			});
		});

		it('shows the unexpected-error toast when the API call rejects without a message', async () => {
			mockPostAgentDefinition.mockRejectedValueOnce({});

			const {result} = renderAgentHook();

			await fillRequiredFields(result);

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(mockOpenToast).toHaveBeenCalledWith(
					expect.objectContaining({
						message: 'an-unexpected-error-occurred',
						type: 'danger',
					})
				);
			});
		});

		it('skips relationship PUTs and DELETEs when neither picker has changed', async () => {
			mockPostAgentDefinition.mockResolvedValueOnce({
				externalReferenceCode: 'AGENT_X',
				status: {label: 'approved'},
			});

			const {result} = renderAgentHook();

			await fillRequiredFields(result);

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(mockPostAgentDefinition).toHaveBeenCalled();
			});

			expect(
				mockPutAgentDefinitionToContentRetrievers
			).not.toHaveBeenCalled();
			expect(
				mockPutAgentDefinitionToModelArmorTemplates
			).not.toHaveBeenCalled();
			expect(
				mockDeleteAgentDefinitionToContentRetrievers
			).not.toHaveBeenCalled();
			expect(
				mockDeleteAgentDefinitionToModelArmorTemplates
			).not.toHaveBeenCalled();
		});
	});

	describe('validate', () => {
		it('clears errors once required fields are filled', async () => {
			mockPostAgentDefinition.mockResolvedValueOnce({
				externalReferenceCode: 'AGENT_X',
				status: {label: 'approved'},
			});

			const {result} = renderAgentHook();

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(result.current.errors.title_i18n).toBe('required');
			});

			await fillRequiredFields(result);

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(result.current.errors.title_i18n).toBeUndefined();
			});

			expect(result.current.errors.externalReferenceCode).toBeUndefined();
			expect(result.current.errors.description).toBeUndefined();
			expect(result.current.errors.inputVariables).toBeUndefined();
			expect(result.current.errors.outputVariable).toBeUndefined();
			expect(
				result.current.errors.workflowDefinitionName
			).toBeUndefined();
		});

		it('flags every required field on a blank submit', async () => {
			const {result} = renderAgentHook();

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(result.current.errors.title_i18n).toBe('required');
			});

			expect(result.current.errors.description).toBe('required');
			expect(result.current.errors.inputVariables).toBe('required');
			expect(result.current.errors.outputVariable).toBe('required');
			expect(result.current.errors.workflowDefinitionName).toBe(
				'required'
			);
			expect(mockPostAgentDefinition).not.toHaveBeenCalled();
		});
	});
});
