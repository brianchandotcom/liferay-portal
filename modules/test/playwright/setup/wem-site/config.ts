/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const wemSiteSetup = {
	name: 'wem-site-setup',
	teardown: 'wem-site-teardown',
	testDir: 'setup/wem-site',
	testMatch: 'setup.spec.ts',
};

const wemSiteTeardown = {
	name: 'wem-site-teardown',
	testDir: 'setup/wem-site',
	testMatch: 'teardown.spec.ts',
};

export {wemSiteSetup, wemSiteTeardown};
