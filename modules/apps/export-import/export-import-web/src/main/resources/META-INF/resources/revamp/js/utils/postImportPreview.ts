/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ApiHelper, {RequestResult} from '../services/ApiHelper';

export interface ImportPreview {
	additionCount: number;
	author: string;
	deletionCount: number;
	exportDate: string;
	fileEntryId: number;
	fileName: string;
	fileSize: number;
	portletDataHandlerSections: unknown[];
}

export async function postImportPreview({
	file,
	onProgress,
	signal,
	url,
}: {
	file: File;
	onProgress: (progressEvent: number) => void;
	signal?: AbortSignal;
	url: string;
}): Promise<RequestResult<ImportPreview>> {
	return await ApiHelper.postFormDataWithProgress<ImportPreview>(
		url,
		Liferay.Util.objectToFormData({file}),
		(progressEvent) => {
			onProgress(progressEvent);
		},
		signal
	);
}
