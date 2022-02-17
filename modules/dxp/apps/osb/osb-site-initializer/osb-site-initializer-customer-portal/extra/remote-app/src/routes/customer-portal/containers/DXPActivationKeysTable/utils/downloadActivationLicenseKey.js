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
	getExportedLicenseKeys,
} from '../../../../../common/services/liferay/rest/raysource/LicenseKeys';
import downloadFromBlob from '../../../../../common/utils/downloadFromBlob';
import {EXTENSION_FILE_TYPES, STATUS_CODE} from '../../../utils/constants';

export async function downloadActivationLicenseKey(
	licenseKey,
	licenseKeyDownloadURL,
	sessionId,
	activationKeyName,
	activationKeyVersion,
	projectName
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

		const projectFileName = projectName.replaceAll(' ', '').toLowerCase();
		const productNameFormated = activationKeyName
			.replaceAll(' ', '')
			.toLowerCase();

		return downloadFromBlob(
			licenseBlob,
			`activation-key-${productNameFormated}-${activationKeyVersion}-${projectFileName}${extensionFile}`
		);
	}
}

export async function downloadAggregatedActivationKey(
	selectedKeysIDs,
	licenseKeyDownloadURL,
	sessionId,
	aggregatedKeysProductNames,
	aggregatedProductFileVersions,
	projectName
) {
	const license = await getAggregatedActivationDownloadKey(
		selectedKeysIDs,
		licenseKeyDownloadURL,
		sessionId
	);

	const DIFFERENT_AGGREGATED_NAMES = 'multiple-products';
	const DIFFERENT_AGGREGATED_VERSIONS = 'multiple-versions';

	const areAggregatedNamesEqual = aggregatedKeysProductNames.every(
		(name) => name === aggregatedKeysProductNames[0]
	);

	const areAggregatedVersionsEqual = aggregatedProductFileVersions.every(
		(version) => version === aggregatedProductFileVersions[0]
	);

	const aggregatedProductFileName = areAggregatedNamesEqual
		? aggregatedKeysProductNames[0].replaceAll(' ', '').toLowerCase()
		: DIFFERENT_AGGREGATED_NAMES;

	const aggregatedProductFileVersion = areAggregatedVersionsEqual
		? aggregatedProductFileVersions[0]
		: DIFFERENT_AGGREGATED_VERSIONS;

	const projectFileName = projectName.replaceAll(' ', '').toLowerCase();

	if (license.status === STATUS_CODE.success) {
		const contentType = license.headers.get('content-type');
		const extensionFile = EXTENSION_FILE_TYPES[contentType] || '.txt';
		const licenseBlob = await license.blob();

		return downloadFromBlob(
			licenseBlob,
			`activation-key-${aggregatedProductFileName}-${aggregatedProductFileVersion}-${projectFileName}${extensionFile}`
		);
	}
}

export async function downloadAllKeysDetails(
	accountKey,
	licenseKeyDownloadURL,
	sessionId
) {
	const license = await getExportedLicenseKeys(
		accountKey,
		licenseKeyDownloadURL,
		sessionId
	);

	if (license.status === STATUS_CODE.success) {
		const contentType = license.headers.get('content-type');
		const extensionFile = EXTENSION_FILE_TYPES[contentType] || '.txt';
		const licenseBlob = await license.blob();

		return downloadFromBlob(licenseBlob, `activation-keys${extensionFile}`);
	}
}
