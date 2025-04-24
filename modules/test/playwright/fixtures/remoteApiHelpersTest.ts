/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {ApiHelpers} from '../helpers/ApiHelpers';
import {liferayConfig} from '../liferay.config';

function remoteApiHelpersTest(port: string) {
	return test.extend<{remoteApiHelpers: ApiHelpers}>({
		remoteApiHelpers: async ({page}, use) => {
			const apiHelpers = new ApiHelpers(
				page,
				liferayConfig.environment.baseUrl.replace('8080', port)
			);
	
			await use(apiHelpers);
		}
	});
}

export {remoteApiHelpersTest};
