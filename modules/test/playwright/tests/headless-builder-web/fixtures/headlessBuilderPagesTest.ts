/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApplicationPage} from '../pages/applicationPage';
import {HeadlessBuilderPage} from '../pages/headlessBuilderPage';
import {headlessBuilderTest} from './headlessBuilderTest';

const headlessBuilderPagesTest = headlessBuilderTest.extend<{
	applicationPage: ApplicationPage;
	headlessBuilderPage: HeadlessBuilderPage;
}>({
	applicationPage: async ({page}, use) => {
		await use(new ApplicationPage(page));
	},
	headlessBuilderPage: async ({page}, use) => {
		await use(new HeadlessBuilderPage(page));
	},
});

export {headlessBuilderPagesTest};
