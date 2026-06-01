/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

const AI_HUB_ENDPOINT = '/o/ai-hub/v1.0';

export type ReportFeedbackReason =
	| 'agentError'
	| 'harmfulContent'
	| 'incorrect'
	| 'other'
	| 'piiExposure';

export type ReportFeedbackSurface =
	| 'aiAssistant'
	| 'clickToChat'
	| 'writingAssistant';

export interface ReportFeedbackPayload {
	agentDefinitionExternalReferenceCodes: string[];
	reason: ReportFeedbackReason;
	surface: ReportFeedbackSurface;
	userMessage?: string;
}

export async function postAIIssueReport(payload: ReportFeedbackPayload) {
	const response = await fetch(`${AI_HUB_ENDPOINT}/agent-issue-reports`, {
		body: JSON.stringify(payload),
		headers: new Headers({
			'Accept': 'application/json',
			'Content-Type': 'application/json',
		}),
		method: 'POST',
	});

	if (!response.ok) {
		throw new Error(
			`Unable to send feedback (${response.status} ${response.statusText})`
		);
	}

	return response.json();
}
