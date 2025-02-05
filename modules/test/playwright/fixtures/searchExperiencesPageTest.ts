/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {EditResultRankingPage} from '../pages/search-experiences-web/EditResultRankingPage';
import {EditSXPBlueprintPage} from '../pages/search-experiences-web/EditSXPBlueprintPage';
import {ResultRankingsViewPage} from '../pages/search-experiences-web/ResultRankingsViewPage';
import {SXPBlueprintsAndElementsViewPage} from '../pages/search-experiences-web/SXPBlueprintsAndElementsViewPage';

const searchExperiencesPagesTest = test.extend<{
	editResultRankingPage: EditResultRankingPage;
	editSXPBlueprintPage: EditSXPBlueprintPage;
	resultRankingsViewPage: ResultRankingsViewPage;
	sxpBlueprintsAndElementsViewPage: SXPBlueprintsAndElementsViewPage;
}>({
	editResultRankingPage: async ({page}, use) => {
		await use(new EditResultRankingPage(page));
	},
	editSXPBlueprintPage: async ({page}, use) => {
		await use(new EditSXPBlueprintPage(page));
	},
	resultRankingsViewPage: async ({page}, use) => {
		await use(new ResultRankingsViewPage(page));
	},
	sxpBlueprintsAndElementsViewPage: async ({page}, use) => {
		await use(new SXPBlueprintsAndElementsViewPage(page));
	},
});

export {searchExperiencesPagesTest};
