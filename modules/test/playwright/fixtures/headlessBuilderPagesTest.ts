/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {ApiApplicationPage} from '../pages/headless-builder-web/apiApplicationPage';
import {HeadlessBuilderPage} from '../pages/headless-builder-web/headlessBuilderPage';

const headlessBuilderPagesTest = test.extend<{
	apiApplicationPage: ApiApplicationPage;
	headlessBuilderPage: HeadlessBuilderPage;
}>({
	apiApplicationPage: async ({page}, use) => {
		await use(new ApiApplicationPage(page));
	},
	headlessBuilderPage: async ({page}, use) => {
		await use(new HeadlessBuilderPage(page));
	},
});

export {headlessBuilderPagesTest};
