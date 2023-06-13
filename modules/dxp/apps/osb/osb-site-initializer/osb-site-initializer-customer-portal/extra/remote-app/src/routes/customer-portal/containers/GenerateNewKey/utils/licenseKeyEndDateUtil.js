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

const getLicenseKeyEndDateSelected = (infoSelectedKey) => (licenseEntryType) =>
	infoSelectedKey?.selectedSubscription?.licenseKeyEndDates?.find(
		(licenseKey) => licenseKey?.licenseEntryType.includes(licenseEntryType)
	)?.endDate;

const getLicenseKeyEndDatesByLicenseType = (infoSelectedKey) => {
	const licenseEntryType = infoSelectedKey?.licenseEntryType;

	const _getLicenseKeyEndDateSelected = getLicenseKeyEndDateSelected(
		infoSelectedKey
	);

	if (licenseEntryType?.includes('Virtual Cluster')) {
		return _getLicenseKeyEndDateSelected('virtual-cluster');
	}

	if (licenseEntryType?.includes('OEM')) {
		return _getLicenseKeyEndDateSelected('oem');
	}

	if (licenseEntryType?.includes('Enterprise')) {
		return _getLicenseKeyEndDateSelected('enterprise');
	}

	return _getLicenseKeyEndDateSelected('production');
};

export {getLicenseKeyEndDatesByLicenseType};
