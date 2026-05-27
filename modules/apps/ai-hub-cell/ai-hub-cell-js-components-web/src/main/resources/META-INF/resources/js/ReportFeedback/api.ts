/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type ReportFeedbackReason =
	| 'AGENT_ERROR_OR_MALFUNCTION'
	| 'INAPPROPRIATE_OR_HARMFUL_CONTENT'
	| 'INCORRECT_OR_INACCURATE_RESPONSE'
	| 'OTHER'
	| 'PII_EXPOSURE';

export type ReportFeedbackSurface =
	| 'AI_ASSISTANT'
	| 'CLICK_TO_CHAT'
	| 'CMS_ASSISTANT';

export interface ReportFeedbackPayload {
	agentId: string;
	agentVersion?: string | null;
	comment?: string;
	reason: ReportFeedbackReason;
	surface: ReportFeedbackSurface;
	traceId: string;
}

// TODO LPD-91817 swap for a real POST once the backend endpoint lands
// (planned: POST /o/ai-hub-cell/v1.0/ai-issue-reports).

// eslint-disable-next-line @typescript-eslint/no-unused-vars
export async function postAIIssueReport(payload: ReportFeedbackPayload) {
	await new Promise((resolve) => setTimeout(resolve, 400));

	return {id: `mock-${Date.now()}`};
}
