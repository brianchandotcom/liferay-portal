/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {DataApiHelpers} from '../helpers/ApiHelpers';
import {BackendPage, backendPageTest} from './backendPageTest';
import { liferayConfig } from '../liferay.config';

const test = mergeTests(backendPageTest);

const dataRemoteApiHelpersTest = test.extend<{
	remoteApiHelpers: DataApiHelpers;
	backendPage: BackendPage;
}>({
	remoteApiHelpers: async ({backendPage, page}, use) => {
		liferayConfig.environment.baseUrl = 
			liferayConfig.environment.baseUrl.replace('8080', '9080')
		const dataApiHelpers = new DataApiHelpers(page);
		liferayConfig.environment.baseUrl = 
			liferayConfig.environment.baseUrl.replace('9080', '8080')
		
		try {
			await use(dataApiHelpers);
		}
		finally {

			// @ts-ignore
			liferayConfig.environment.baseUrl = 
				liferayConfig.environment.baseUrl.replace('8080', '9080')
			const adminDataApiHelpers = new DataApiHelpers(backendPage);
			liferayConfig.environment.baseUrl = 
				liferayConfig.environment.baseUrl.replace('9080', '8080')

			adminDataApiHelpers.setData(dataApiHelpers.data);

			await adminDataApiHelpers.clearData();
		}
	},
});
export {dataRemoteApiHelpersTest};