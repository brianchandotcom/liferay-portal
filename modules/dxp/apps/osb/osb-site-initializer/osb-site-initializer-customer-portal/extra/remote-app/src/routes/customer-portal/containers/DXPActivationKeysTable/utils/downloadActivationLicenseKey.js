/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {
	getActivationDownloadKey,
	getAggregatedActivationDownloadKey,
} from '../../../../../common/services/liferay/rest/raysource/LicenseKeys';
import downloadFromBlob from '../../../../../common/utils/downloadFromBlob';
import {EXTENSION_FILE_TYPES, STATUS_CODE} from '../../../utils/constants';

export async function downloadActivationLicenseKey(
	licenseKey,
	licenseKeyDownloadURL,
	sessionId
) {
	const license = await getActivationDownloadKey(
		licenseKey,
		licenseKeyDownloadURL,
		sessionId
	);

	if (license.status === STATUS_CODE.success) {
		const contentType = license.headers.get('content-type');
		const extensionFile = EXTENSION_FILE_TYPES[contentType] || '.txt';
		const licenseBlob = await license.blob();

		return downloadFromBlob(licenseBlob, `license${extensionFile}`);
	}
}

export async function downloadAggregatedActivationDownloadKey(
	selectedKeysIDs,
	licenseKeyDownloadURL,
	sessionId
) {
	const license = await getAggregatedActivationDownloadKey(
		selectedKeysIDs,
		licenseKeyDownloadURL,
		sessionId
	);
	// eslint-disable-next-line no-console

	if (license.status === STATUS_CODE.success) {
		const contentType = license.headers.get('content-type');
		const extensionFile = EXTENSION_FILE_TYPES[contentType] || '.txt';
		const licenseBlob = await license.blob();

		return downloadFromBlob(licenseBlob, `license${extensionFile}`);
	}
}
