/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';

import {
	ReportFeedbackPayload,
	ReportFeedbackReason,
	ReportFeedbackSurface,
	postAIIssueReport,
} from './api';

interface UseReportFeedbackOptions {
	agentDefinitionExternalReferenceCodes: string[];
	surface: ReportFeedbackSurface;
}

export default function useReportFeedback({
	agentDefinitionExternalReferenceCodes,
	surface,
}: UseReportFeedbackOptions) {
	const [reason, setReason] = useState<ReportFeedbackReason | ''>('');
	const [userMessage, setUserMessage] = useState<string>('');
	const [submitting, setSubmitting] = useState<boolean>(false);
	const [error, setError] = useState<string>('');

	const canSubmit = Boolean(reason) && !submitting;

	async function submit(): Promise<boolean> {
		if (!reason) {
			return false;
		}

		const payload: ReportFeedbackPayload = {
			agentDefinitionExternalReferenceCodes,
			reason,
			surface,
			...(userMessage.trim() ? {userMessage: userMessage.trim()} : {}),
		};

		setError('');
		setSubmitting(true);

		try {
			await postAIIssueReport(payload);

			return true;
		}
		catch (caught) {
			setError(
				(caught as Error).message ||
					Liferay.Language.get(
						'unable-to-send-feedback-please-try-again'
					)
			);
			setSubmitting(false);

			return false;
		}
	}

	return {
		canSubmit,
		error,
		reason,
		setReason,
		setUserMessage,
		submit,
		submitting,
		userMessage,
	};
}
