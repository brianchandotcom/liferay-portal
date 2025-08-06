/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {ConfigStagingPage} from '../pages/ConfigStagingPage';
import {PortletStagingPage} from '../pages/PortletStagingPage';
import {RemoteStagingPage} from '../pages/RemoteStagingPage';

const remoteStagingPagesTest = test.extend<{
	configStagingPage: ConfigStagingPage;
	portletStagingPage: PortletStagingPage;
	remoteStagingPage: RemoteStagingPage;
}>({
	configStagingPage: async ({page}, use) => {
		await use(new ConfigStagingPage(page));
	},
	portletStagingPage: async ({page}, use) => {
		await use(new PortletStagingPage(page));
	},
	remoteStagingPage: async ({page}, use) => {
		await use(new RemoteStagingPage(page));
	},
});

export {remoteStagingPagesTest};
