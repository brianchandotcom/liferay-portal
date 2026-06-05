/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReportFeedbackSurface, postAIIssueReport} from './api';
import showThanksForFeedbackToast from './showThanksForFeedbackToast';

interface SubmitPositiveReportFeedbackOptions {
	agentDefinitionExternalReferenceCodes: string[];
	surface: ReportFeedbackSurface;
}

export default async function submitPositiveReportFeedback({
	agentDefinitionExternalReferenceCodes,
	surface,
}: SubmitPositiveReportFeedbackOptions) {
	try {
		await postAIIssueReport({
			agentDefinitionExternalReferenceCodes,
			feedback: 'positive',
			surface,
		});

		showThanksForFeedbackToast();
	}
	catch (error) {
		Liferay.Util.openToast({
			message: Liferay.Language.get(
				'unable-to-send-feedback-please-try-again'
			),
			type: 'danger',
		});
	}
}
