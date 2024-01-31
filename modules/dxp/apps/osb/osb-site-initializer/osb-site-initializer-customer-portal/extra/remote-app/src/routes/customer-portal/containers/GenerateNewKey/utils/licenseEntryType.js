/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const getLicenseEntryTypeSelected = (selectedKeyData) => {
	const licenseEntryType = selectedKeyData?.licenseEntryType;

	if (licenseEntryType?.includes('Virtual Cluster')) {
		return 'virtual-cluster';
	}

	if (licenseEntryType?.includes('OEM')) {
		return 'oem';
	}

	if (licenseEntryType?.includes('Enterprise')) {
		return 'enterprise';
	}

	return 'production';
};

export {getLicenseEntryTypeSelected};
