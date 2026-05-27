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
	agentId: string;
	agentVersion?: string | null;
	surface: ReportFeedbackSurface;
	traceId: string;
}

export default function useReportFeedback({
	agentId,
	agentVersion,
	surface,
	traceId,
}: UseReportFeedbackOptions) {
	const [reason, setReason] = useState<ReportFeedbackReason | ''>('');
	const [comment, setComment] = useState<string>('');
	const [submitting, setSubmitting] = useState<boolean>(false);
	const [error, setError] = useState<string>('');

	const canSubmit = Boolean(reason) && !submitting;

	async function submit(): Promise<boolean> {
		if (!reason) {
			return false;
		}

		const payload: ReportFeedbackPayload = {
			agentId,
			agentVersion,
			reason,
			surface,
			traceId,
			...(comment.trim() ? {comment: comment.trim()} : {}),
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
		comment,
		error,
		reason,
		setComment,
		setReason,
		submit,
		submitting,
	};
}
