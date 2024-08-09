/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const pageManagementSiteSetup = {
	name: 'page-management-site-setup',
	teardown: 'page-management-site-teardown',
	testDir: 'tests/setup/page-management-site',
	testMatch: 'setup.spec.ts',
};

const pageManagementSiteTeardown = {
	name: 'page-management-site-teardown',
	testDir: 'tests/setup/page-management-site',
	testMatch: 'teardown.spec.ts',
};

export {pageManagementSiteSetup, pageManagementSiteTeardown};
