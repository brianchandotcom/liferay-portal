/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {EditSXPBlueprintPage} from '../pages/search-experiences-web/EditSXPBlueprintPage';
import {SXPBlueprintsAndElementsViewPage} from '../pages/search-experiences-web/SXPBlueprintsAndElementsViewPage';

const searchExperiencesPagesTest = test.extend<{
	editSXPBlueprintPage: EditSXPBlueprintPage;
	sxpBlueprintsAndElementsViewPage: SXPBlueprintsAndElementsViewPage;
}>({
	editSXPBlueprintPage: async ({page}, use) => {
		await use(new EditSXPBlueprintPage(page));
	},
	sxpBlueprintsAndElementsViewPage: async ({page}, use) => {
		await use(new SXPBlueprintsAndElementsViewPage(page));
	},
});

export {searchExperiencesPagesTest};
