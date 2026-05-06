/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ImportPreview} from '../types/portletDataHandlerSection';
import ApiHelper, {RequestResult} from './ApiHelper';

export interface ImportPreviewParams {
	file: File;
	onProgress: (percent: number) => void;
	signal?: AbortSignal;
	url: string;
}

export function postImportPreview({
	file,
	onProgress,
	signal,
	url,
}: ImportPreviewParams): Promise<RequestResult<ImportPreview>> {
	return ApiHelper.postFormDataWithProgress<ImportPreview>(
		url,
		Liferay.Util.objectToFormData({file}),
		onProgress,
		signal
	);
}
