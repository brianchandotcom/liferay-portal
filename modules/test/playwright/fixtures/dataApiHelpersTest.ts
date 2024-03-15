/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {DataApiHelpers} from '../helpers/ApiHelpers';

const dataApiHelpersTest = test.extend<{apiHelpers: DataApiHelpers}>({
	apiHelpers: async ({page}, use) => {
		const dataApiHelpers = new DataApiHelpers(page);

		try {
			await use(dataApiHelpers);
		}
		finally {
			await dataApiHelpers.clearData();
		}
	},
});

export {dataApiHelpersTest};
