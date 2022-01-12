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

const fetchLicense = async (
	accountKey,
	dateEnd,
	dateStart,
	environment,
	licenseKeyDownloadURL,
	productName,
	sessionId
) => {
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
};

export {fetchLicense};
