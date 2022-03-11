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

export async function getCommonLicenseKey(
	accountKey,
	dateEnd,
	dateStart,
	environment,
	licenseKeyDownloadURL,
	productName,
	sessionId
) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${licenseKeyDownloadURL}/accounts/${accountKey}/product-groups/${productName}/product-environment/${environment}/common-license-key?dateEnd=${dateEnd}&dateStart=${dateStart}`,
		{
			headers: {
				'Okta-Session-ID': sessionId,
			},
		}
	);

	return response;
}

export async function getDevelopmentLicenseKey(
	accountKey,
	licenseKeyDownloadURL,
	sessionId,
	selectedVersion
) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${licenseKeyDownloadURL}/accounts/${accountKey}/product-groups/DXP/product-version/${selectedVersion}/development-license-key`,

		{
			headers: {
				'Okta-Session-ID': sessionId,
			},
		}
	);

	return response;
}

export async function getActivationLicenseKey(
	accountKey,
	licenseKeyDownloadURL,
	filter,
	page,
	pageSize,
	sessionId
) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${licenseKeyDownloadURL}/accounts/${accountKey}/license-keys?filter=${filter}&page=${page}&pageSize=${pageSize}`,
		{
			headers: {
				'Okta-Session-ID': sessionId,
			},
		}
	);

	return response.json();
}

export async function getActivationDownloadKey(
	licenseKey,
	licenseKeyDownloadURL,
	sessionId
) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${licenseKeyDownloadURL}/license-keys/${licenseKey}/download`,

		{
			headers: {
				'Okta-Session-ID': sessionId,
			},
		}
	);

	return response;
}

export async function getAggregatedActivationDownloadKey(
	selectedKeysIDs,
	licenseKeyDownloadURL,
	sessionId
) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${licenseKeyDownloadURL}/license-keys/download?${selectedKeysIDs}`,

		{
			headers: {
				'Okta-Session-ID': sessionId,
			},
		}
	);

	return response;
}

export async function getExportedLicenseKeys(
	accountKey,
	licenseKeyDownloadURL,
	sessionId
) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${licenseKeyDownloadURL}/accounts/${accountKey}/license-keys/export`,

		{
			headers: {
				'Okta-Session-ID': sessionId,
			},
		}
	);

	return response;
}

export async function associateContactRoleNameByEmailByProject(
	accountKey,
	licenseKeyDownloadURL,
	sessionId,
	emailURI,
	roleName
) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${licenseKeyDownloadURL}/accounts/${accountKey}/contacts/by-email-address/${emailURI}/roles?contactRoleNames=${roleName}`,
		{
			headers: {
				'Okta-Session-ID': sessionId,
			},
			method: 'PUT',
		}
	);

	return response;
}

export async function deleteContactRoleNameByEmailByProject(
	accountKey,
	licenseKeyDownloadURL,
	sessionId,
	emailURI,
	rolesToDelete
) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${licenseKeyDownloadURL}/accounts/${accountKey}/contacts/by-email-address/${emailURI}/roles?${rolesToDelete}`,
		{
			headers: {
				'Okta-Session-ID': sessionId,
			},
			method: 'DELETE',
		}
	);

	return response;
}

export async function putDeactivateKeys(
	licenseKeyDownloadURL,
	licenseKeyIds,
	sessionId
) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${licenseKeyDownloadURL}/license-keys/deactivate?${licenseKeyIds}`,

		{
			headers: {
				'Okta-Session-ID': sessionId,
			},
			method: 'PUT',
		}
	);

	return response;
}

export async function getNewGenerateKeyFormValues(
	accountKey,
	licenseKeyDownloadURL,
	productGroupName,
	sessionId
) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${licenseKeyDownloadURL}/accounts/${accountKey}/product-groups/${productGroupName}/generate-form`,
		{
			headers: {
				'Okta-Session-ID': sessionId,
			},
		}
	);

	return response.json();
}
