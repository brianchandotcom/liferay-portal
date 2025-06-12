/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HttpResponse, http} from 'msw';

import {
	getImportErrorDetailResponseJSON,
	getImportSingleErrorDetailResponseJSON,
} from './mockResponses';

export const handlers = [
	http.get('/group/__mocks__/get-import-error-detail', () => {
		return HttpResponse.json(getImportErrorDetailResponseJSON, 200, {
			'Content-Type': 'application/json',
		});
	}),

	http.get('/group/__mocks__/get-import-single-error-detail', () => {
		return HttpResponse.json(getImportSingleErrorDetailResponseJSON, 200, {
			'Content-Type': 'application/json',
		});
	}),
];
