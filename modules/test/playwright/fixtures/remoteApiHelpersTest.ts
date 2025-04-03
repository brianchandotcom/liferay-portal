/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {ApiHelpers} from '../helpers/ApiHelpers';
import {liferayConfig} from '../liferay.config';

const remoteApiHelpersTest = test.extend<{remoteApiHelpers: ApiHelpers}>({
	remoteApiHelpers: async ({page}, use) => {
		liferayConfig.environment.baseUrl =
			liferayConfig.environment.baseUrl.replace('8080', '9080');
		const apiHelpers = new ApiHelpers(page);
		liferayConfig.environment.baseUrl =
			liferayConfig.environment.baseUrl.replace('9080', '8080');

		await use(apiHelpers);
	},
});

export {remoteApiHelpersTest};
