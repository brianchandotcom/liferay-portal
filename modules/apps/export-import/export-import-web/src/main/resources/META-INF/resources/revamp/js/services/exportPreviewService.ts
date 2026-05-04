/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ExportPreview} from '../types/portletDataHandlerSection';
import ApiHelper, {RequestResult} from './ApiHelper';

export type ExportPreviewRange = 'all' | 'dateRange' | 'last';

export interface ExportPreviewParams {
	endDate?: string;
	last?: number;
	range?: ExportPreviewRange;
	startDate?: string;
}

export function fetchExportPreview(
	baseURL: string,
	params: ExportPreviewParams = {}
): Promise<RequestResult<ExportPreview>> {
	const searchParams = new URLSearchParams();

	Object.entries(params).forEach(([key, value]) => {
		if (value !== undefined && value !== null && value !== '') {
			searchParams.append(key, String(value));
		}
	});

	const queryString = searchParams.toString();
	const url = queryString ? `${baseURL}?${queryString}` : baseURL;

	return ApiHelper.get<ExportPreview>(url);
}
