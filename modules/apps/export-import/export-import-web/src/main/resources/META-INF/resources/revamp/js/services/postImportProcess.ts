/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ImportProcess, ImportRequest} from '../types/exportImportProcess';
import ApiHelper, {RequestResult} from './ApiHelper';

export interface ImportProcessParams {
	importRequest: ImportRequest;
	url: string;
}

export function postImportProcess({
	importRequest,
	url,
}: ImportProcessParams): Promise<RequestResult<ImportProcess>> {
	return ApiHelper.post<ImportProcess>(url, importRequest);
}
