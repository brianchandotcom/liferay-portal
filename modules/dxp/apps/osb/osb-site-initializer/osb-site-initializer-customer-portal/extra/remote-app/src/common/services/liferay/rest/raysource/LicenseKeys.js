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
	provisioningServerAPI,
	productName,
	sessionId
) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/accounts/${accountKey}/product-groups/${productName}/product-environment/${environment}/common-license-key?dateEnd=${dateEnd}&dateStart=${dateStart}`,
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
	provisioningServerAPI,
	sessionId,
	selectedVersion,
	productName
) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/accounts/${accountKey}/product-groups/${productName}/product-version/${selectedVersion}/development-license-key`,

		{
			headers: {
				'Okta-Session-ID': sessionId,
			},
		}
	);

	return response;
}

export async function getActivationDownloadKey(
	licenseKey,
	provisioningServerAPI,
	sessionId
) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/license-keys/${licenseKey}/download`,

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
	provisioningServerAPI,
	sessionId
) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/license-keys/download?${selectedKeysIDs}`,

		{
			headers: {
				'Okta-Session-ID': sessionId,
			},
		}
	);

	return response;
}

export async function getMultipleActivationDownloadKey(
	selectedKeysIDs,
	provisioningServerAPI,
	sessionId
) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/license-keys/download-zip?${selectedKeysIDs}`,

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
	provisioningServerAPI,
	sessionId,
	productName
) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/accounts/${accountKey}/license-keys/export?filter=active+eq+true+and+startswith(productName,'${productName}')`,

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
	provisioningServerAPI,
	sessionId,
	emailURI,
	roleName
) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/accounts/${accountKey}/contacts/by-email-address/${emailURI}/roles?contactRoleNames=${roleName}`,
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
	provisioningServerAPI,
	sessionId,
	emailURI,
	rolesToDelete
) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/accounts/${accountKey}/contacts/by-email-address/${emailURI}/roles?${rolesToDelete}`,
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
	provisioningServerAPI,
	licenseKeyIds,
	sessionId
) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/license-keys/deactivate?${licenseKeyIds}`,

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
	provisioningServerAPI,
	productGroupName,
	sessionId
) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/accounts/${accountKey}/product-groups/${productGroupName}/generate-form`,
		{
			headers: {
				'Okta-Session-ID': sessionId,
			},
		}
	);

	return response.json();
}

export async function createNewGenerateKey(
	accountKey,
	provisioningServerAPI,
	sessionId,
	licenseKey
) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/accounts/${accountKey}/license-keys`,
		{
			body: JSON.stringify([licenseKey]),
			headers: {
				'Content-Type': 'application/json',
				'Okta-Session-ID': sessionId,
			},
			method: 'POST',
		}
	);

	return response.json();
}

export async function putSubscriptionInKey(
	provisioningServerAPI,
	licenseKeyIds,
	sessionId
) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/license-keys/subscriptions?licenseKeyIds=${licenseKeyIds}`,

		{
			headers: {
				'Okta-Session-ID': sessionId,
			},
			method: 'PUT',
		}
	);

	return response;
}

export async function deleteSubscriptionInKey(
	provisioningServerAPI,
	licenseKeyIds,
	sessionId
) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/license-keys/subscriptions?licenseKeyIds=${licenseKeyIds}`,

		{
			headers: {
				'Okta-Session-ID': sessionId,
			},
			method: 'DELETE',
		}
	);

	return response;
}

export async function getSubscriptionInKey(
	provisioningServerAPI,
	licenseKeyIds,
	sessionId
) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/license-keys/subscriptions?licenseKeyId=${licenseKeyIds}`,
		{
			headers: {
				'Okta-Session-ID': sessionId,
			},
			method: 'GET',
		}
	);

	return response.json();
}
