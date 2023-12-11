/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
