/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, renderHook} from '@testing-library/react-hooks';

import {postAIIssueReport} from '../../../src/main/resources/META-INF/resources/js/ReportFeedback/api';
import useReportFeedback from '../../../src/main/resources/META-INF/resources/js/ReportFeedback/useReportFeedback';

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/ReportFeedback/api'
);

const mockPostAIIssueReport = postAIIssueReport as jest.MockedFunction<
	typeof postAIIssueReport
>;

describe('useReportFeedback', () => {
	beforeEach(() => {
		mockPostAIIssueReport.mockReset();
	});

	it('cannot submit until a reason is selected', () => {
		const {result} = renderHook(() =>
			useReportFeedback({
				agentDefinitionExternalReferenceCodes: ['agent-1'],
				surface: 'aiAssistant',
			})
		);

		expect(result.current.canSubmit).toBe(false);

		act(() => result.current.setReason('other'));

		expect(result.current.canSubmit).toBe(true);
	});

	it('does not post when no reason is selected', async () => {
		const {result} = renderHook(() =>
			useReportFeedback({
				agentDefinitionExternalReferenceCodes: ['agent-1'],
				surface: 'aiAssistant',
			})
		);

		let outcome: boolean | undefined;

		await act(async () => {
			outcome = await result.current.submit();
		});

		expect(outcome).toBe(false);
		expect(mockPostAIIssueReport).not.toHaveBeenCalled();
	});

	it('posts the trimmed payload and returns true on success', async () => {
		mockPostAIIssueReport.mockResolvedValue({id: 'report-1'});

		const {result} = renderHook(() =>
			useReportFeedback({
				agentDefinitionExternalReferenceCodes: ['agent-1', 'agent-2'],
				surface: 'aiAssistant',
			})
		);

		act(() => {
			result.current.setReason('piiExposure');
			result.current.setUserMessage('  leaked data  ');
		});

		let outcome: boolean | undefined;

		await act(async () => {
			outcome = await result.current.submit();
		});

		expect(outcome).toBe(true);
		expect(mockPostAIIssueReport).toHaveBeenCalledWith({
			agentDefinitionExternalReferenceCodes: ['agent-1', 'agent-2'],
			feedback: 'negative',
			reason: 'piiExposure',
			surface: 'aiAssistant',
			userMessage: 'leaked data',
		});
	});

	it('omits an empty userMessage from the payload', async () => {
		mockPostAIIssueReport.mockResolvedValue({id: 'report-1'});

		const {result} = renderHook(() =>
			useReportFeedback({
				agentDefinitionExternalReferenceCodes: ['agent-1'],
				surface: 'aiAssistant',
			})
		);

		act(() => result.current.setReason('other'));

		await act(async () => {
			await result.current.submit();
		});

		expect(mockPostAIIssueReport).toHaveBeenCalledWith({
			agentDefinitionExternalReferenceCodes: ['agent-1'],
			feedback: 'negative',
			reason: 'other',
			surface: 'aiAssistant',
		});
	});

	it('exposes the error message and resets submitting on failure', async () => {
		mockPostAIIssueReport.mockRejectedValue(new Error('network down'));

		const {result} = renderHook(() =>
			useReportFeedback({
				agentDefinitionExternalReferenceCodes: ['agent-1'],
				surface: 'aiAssistant',
			})
		);

		act(() => result.current.setReason('other'));

		let outcome: boolean | undefined;

		await act(async () => {
			outcome = await result.current.submit();
		});

		expect(outcome).toBe(false);
		expect(result.current.error).toBe('network down');
		expect(result.current.submitting).toBe(false);
	});
});
