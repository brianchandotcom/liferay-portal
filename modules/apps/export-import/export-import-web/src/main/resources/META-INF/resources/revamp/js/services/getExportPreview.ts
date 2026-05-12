/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {NormalizedDateFilter} from '../components/date_filter';
import {ExportPreview} from '../types/exportImportPreview';
import ApiHelper, {RequestResult} from './ApiHelper';

export interface ExportPreviewParams {
	query?: NormalizedDateFilter;
	url: string;
}

export function getExportPreview({
	query = {},
	url,
}: ExportPreviewParams): Promise<RequestResult<ExportPreview>> {
	const searchParams = new URLSearchParams();

	Object.entries(query).forEach(([key, value]) => {
		if (value !== undefined && value !== null && value !== '') {
			searchParams.append(key, String(value));
		}
	});

	const queryString = searchParams.toString();

	return ApiHelper.get<ExportPreview>(
		queryString ? `${url}?${queryString}` : url
	);
}
