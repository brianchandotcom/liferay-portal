/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReportFeedbackReason} from './api';

export const REASON_OPTIONS: {
	label: string;
	value: ReportFeedbackReason | '';
}[] = [
	{label: Liferay.Language.get('select-reason'), value: ''},
	{
		label: Liferay.Language.get('incorrect-or-inaccurate-response'),
		value: 'INCORRECT_OR_INACCURATE_RESPONSE',
	},
	{
		label: Liferay.Language.get('inappropriate-or-harmful-content'),
		value: 'INAPPROPRIATE_OR_HARMFUL_CONTENT',
	},
	{
		label: Liferay.Language.get('exposure-of-personal-sensitive-data-pii'),
		value: 'PII_EXPOSURE',
	},
	{
		label: Liferay.Language.get('agent-error-or-malfunction'),
		value: 'AGENT_ERROR_OR_MALFUNCTION',
	},
	{
		label: Liferay.Language.get('other'),
		value: 'OTHER',
	},
];
