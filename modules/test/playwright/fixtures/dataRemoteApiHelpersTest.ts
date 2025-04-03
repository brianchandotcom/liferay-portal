/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {DataApiHelpers} from '../helpers/ApiHelpers';
import {liferayConfig} from '../liferay.config';
import {RemotePage, remotePageTest} from './remotePageTest';

const test = mergeTests(remotePageTest);

const dataRemoteApiHelpersTest = test.extend<{
	remoteApiHelpers: DataApiHelpers;
	remotePage: RemotePage;
}>({
	remoteApiHelpers: async ({remotePage}, use) => {
		const dataApiHelpers = new DataApiHelpers(
			remotePage,
			liferayConfig.environment.baseUrl.replace('8080', '9080')
		);

		try {
			await use(dataApiHelpers);
		}
		finally {

			// @ts-ignore

			const adminDataApiHelpers = new DataApiHelpers(
				remotePage,
				liferayConfig.environment.baseUrl.replace('8080', '9080')
			);

			adminDataApiHelpers.setData(dataApiHelpers.data);

			await adminDataApiHelpers.clearData();
		}
	},
});
export {dataRemoteApiHelpersTest};
