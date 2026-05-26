/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {AlloyPage} from '../pages/AlloyPage';
import {TabName} from '../pages/CKEditor4SamplePage';
import {ckeditor4SamplePageTest} from './ckeditor4SamplePageTest';

const alloyPageTest = ckeditor4SamplePageTest.extend<{
	alloyPage: AlloyPage;
}>({
	alloyPage: async ({ckeditor4SamplePage}, use) => {
		const alloyPage: AlloyPage = await ckeditor4SamplePage.gotoTab(
			TabName.ALLOY
		);

		await use(alloyPage);
	},
});

export {alloyPageTest};
