/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ApiHelper, {RequestResult} from '../services/ApiHelper';

export async function getValidateLarFileEndpoint({
	file,
	groupId,
	onProgress,
	signal,
}: {
	file: File;
	groupId: number;
	onProgress: (progressEvent: number) => void;
	signal?: AbortSignal;
}): Promise<
	RequestResult<{
		errorMessages: string[];
		success: boolean;
		tempFilePath: string;
	}>
> {
	return await ApiHelper.postFormDataWithProgress<{
		errorMessages: string[];
		success: boolean;
		tempFilePath: string;
	}>(
		`/o/export-import/v1.0/scopes/${groupId}/validate`,
		Liferay.Util.objectToFormData({file}),
		(progressEvent) => {
			onProgress(progressEvent);
		},
		signal
	);
}
