/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ApiHelper from '../../common/services/ApiHelper';
import {ENDPOINTS} from '../constants/endpoints';

const getEndpointByScope = (
	isCompanyGroup: boolean,
	groupId?: number
) => {
	return isCompanyGroup
		? ENDPOINTS.VALIDATE_IMPORT_FILE
		: ENDPOINTS.VALIDATE_SITE_IMPORT_FILE(groupId ?? 0);
};

export async function getValidateLarFileEndpoint({
	file,
	groupId,
	isCompanyGroup,
	onProgress,
}: {
	file: File;
	groupId?: number;
	isCompanyGroup: boolean;
	onProgress: (progressEvent: number) => void;
}) {
	const formData = new FormData();

	formData.append('file', file);

	return await ApiHelper.postFormDataWithProgress<{
		errorMessages: string[];
		success: boolean;
		tempFilePath: string;
	}>(
		getEndpointByScope(isCompanyGroup, groupId),
		formData,
		(progressEvent) => {
			onProgress(progressEvent);
		}
	);
}
