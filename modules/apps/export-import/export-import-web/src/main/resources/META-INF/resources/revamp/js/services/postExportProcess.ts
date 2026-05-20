/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ExportProcess, ExportRequest} from '../types/exportImportProcess';
import ApiHelper, {RequestResult} from './ApiHelper';

export interface ExportProcessParams {
	exportRequest: ExportRequest;
	url: string;
}

export function postExportProcess({
	exportRequest,
	url,
}: ExportProcessParams): Promise<RequestResult<ExportProcess>> {
	return ApiHelper.post<ExportProcess>(url, exportRequest);
}
