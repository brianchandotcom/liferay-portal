/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {EditVirtualInstancePage} from '../pages/portal-instances-web/EditVirtualInstancePage';
import {VirtualInstancesPage} from '../pages/portal-instances-web/VirtualInstancesPage';

const virtualInstancesPagesTest = test.extend<{
	editVirtualInstancePage: EditVirtualInstancePage;
	virtualInstancesPage: VirtualInstancesPage;
}>({
	editVirtualInstancePage: async ({page}, use) => {
		await use(new EditVirtualInstancePage(page));
	},
	virtualInstancesPage: async ({page}, use) => {
		await use(new VirtualInstancesPage(page));
	},
});

export {virtualInstancesPagesTest};
