/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DataApiHelpers} from '../helpers/ApiHelpers';
import {liferayConfig} from '../liferay.config';

import type {Page, TestType} from '@playwright/test';

function dataRemoteApiHelpersTest(
	test: TestType<{remotePage: Page}, any>,
	port: string
) {
	return test.extend<{
		remoteApiHelpers: DataApiHelpers;
	}>({
		remoteApiHelpers: async ({remotePage}, use) => {
			const remoteUrl = liferayConfig.environment.baseUrl.replace(
				'8080',
				port
			);

			const dataApiHelpers = new DataApiHelpers(remotePage, remoteUrl);

			try {
				await use(dataApiHelpers);
			}
			finally {

				// @ts-ignore

				const adminDataApiHelpers = new DataApiHelpers(
					remotePage,
					remoteUrl
				);
				adminDataApiHelpers.setData(dataApiHelpers.data);
				await adminDataApiHelpers.clearData();
			}
		},
	});
}

export {dataRemoteApiHelpersTest};
