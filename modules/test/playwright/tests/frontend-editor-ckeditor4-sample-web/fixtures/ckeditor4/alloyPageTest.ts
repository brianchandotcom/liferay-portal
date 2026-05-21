/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TabName} from '../../pages/CKEditorSamplePage';
import {AlloyPage} from '../../pages/ckeditor4/AlloyPage';
import {ckeditorSamplePageTest} from '../ckeditorSamplePageTest';

const alloyPageTest = ckeditorSamplePageTest.extend<{
	alloyPage: AlloyPage;
}>({
	alloyPage: async ({ckeditorSamplePage}, use) => {
		const alloyPage: AlloyPage = await ckeditorSamplePage.gotoTab(
			TabName.ALLOY
		);

		await use(alloyPage);
	},
});

export {alloyPageTest};
