/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export enum CONSENT_TYPE {
	FUNCTIONAL = 'CONSENT_TYPE_FUNCTIONAL',
	NECESSARY = 'CONSENT_TYPE_NECESSARY',
	PERFORMANCE = 'CONSENT_TYPE_PERFORMANCE',
	PERSONALIZATION = 'CONSENT_TYPE_PERSONALIZATION',
}

export const enum DISPATCH_TRIGGER_TYPE {
	AUTO_FILL = 'testray-autofill',
	CREATE_TASK_SUBTASK = 'testray-testflow',
	IMPORT_RESULTS = 'testray-import-results',
}
