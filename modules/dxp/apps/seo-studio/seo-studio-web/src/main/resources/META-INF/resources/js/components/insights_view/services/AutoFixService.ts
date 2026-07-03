/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {invokeAgent} from '../../../agent/invokeAgent';
import {AutoFixDefinition} from '../auto_fix_definitions/AutoFixDefinition';
import {AutoFixCandidate} from '../types/AutoFix';

// State is a Liferay workflow status: approved means fixed, pending means
// still open.

export const WORKFLOW_STATUS_APPROVED = 0;
export const WORKFLOW_STATUS_PENDING = 1;

export async function generateCandidates(
	definition: AutoFixDefinition,
	pageContent: string,
	signal?: AbortSignal
): Promise<AutoFixCandidate[]> {
	const response = await invokeAgent({
		agentExternalReferenceCode: definition.agentExternalReferenceCode,
		context: {pageContent},
		signal,
	});

	const candidates = definition.parseCandidates(response);

	if (!Array.isArray(candidates)) {

		// Surface the agent's raw text (a quota or guardrail rejection
		// message, for example) rather than a generic error.

		throw new Error(response.trim());
	}

	return candidates;
}

// The SEO Studio backend signs the request with the connection credentials;
// the connection secret never reaches the browser.

export async function applyFix({
	insightType,
	pageURL,
	value,
}: {
	insightType: string;
	pageURL: string;
	value: string;
}): Promise<void> {
	const body = new URLSearchParams();

	body.append('insightType', insightType);
	body.append('pageURL', pageURL);
	body.append('value', value);

	const response = await fetch('/o/seo-studio-auto-fix/auto-fix', {
		body,
		headers: new Headers({
			'Accept': 'application/json',
			'Content-Type': 'application/x-www-form-urlencoded',
		}),
		method: 'POST',
	});

	if (!response.ok) {
		throw new Error('Unable to apply the fix');
	}
}

export async function resolveInsight(scanInsightId: number): Promise<void> {
	const response = await fetch(
		`/o/seo-studio/scan-insights/${scanInsightId}`,
		{
			body: JSON.stringify({
				resolvedDate: new Date().toISOString(),
				state: WORKFLOW_STATUS_APPROVED,
			}),
			headers: new Headers({
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			}),
			method: 'PATCH',
		}
	);

	if (!response.ok) {
		throw new Error('Unable to mark the insight as resolved');
	}
}
