/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {postAIIssueReport} from '../../../src/main/resources/META-INF/resources/js/ReportFeedback/api';
import showThanksForFeedbackToast from '../../../src/main/resources/META-INF/resources/js/ReportFeedback/showThanksForFeedbackToast';
import submitPositiveReportFeedback from '../../../src/main/resources/META-INF/resources/js/ReportFeedback/submitPositiveReportFeedback';

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/ReportFeedback/api'
);
jest.mock(
	'../../../src/main/resources/META-INF/resources/js/ReportFeedback/showThanksForFeedbackToast'
);

const mockPostAIIssueReport = postAIIssueReport as jest.MockedFunction<
	typeof postAIIssueReport
>;
const mockShowThanksForFeedbackToast =
	showThanksForFeedbackToast as jest.MockedFunction<
		typeof showThanksForFeedbackToast
	>;

describe('submitPositiveReportFeedback', () => {
	beforeEach(() => {
		mockPostAIIssueReport.mockReset();
		mockShowThanksForFeedbackToast.mockReset();
	});

	it('posts positive feedback and shows the thanks toast on success', async () => {
		mockPostAIIssueReport.mockResolvedValue({id: 'report-1'});

		await submitPositiveReportFeedback({
			agentDefinitionExternalReferenceCodes: ['agent-1'],
			surface: 'aiAssistant',
		});

		expect(mockPostAIIssueReport).toHaveBeenCalledWith({
			agentDefinitionExternalReferenceCodes: ['agent-1'],
			feedback: 'positive',
			surface: 'aiAssistant',
		});
		expect(mockShowThanksForFeedbackToast).toHaveBeenCalledTimes(1);
	});

	it('does not thank the user when the post fails', async () => {
		mockPostAIIssueReport.mockRejectedValue(new Error('network down'));

		await submitPositiveReportFeedback({
			agentDefinitionExternalReferenceCodes: ['agent-1'],
			surface: 'aiAssistant',
		});

		expect(mockShowThanksForFeedbackToast).not.toHaveBeenCalled();
	});
});
