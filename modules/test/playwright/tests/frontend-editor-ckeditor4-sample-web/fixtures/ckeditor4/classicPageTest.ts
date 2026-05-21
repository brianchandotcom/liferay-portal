/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TabName} from '../../pages/CKEditorSamplePage';
import {ClassicPage} from '../../pages/ckeditor4/ClassicPage';
import {ckeditorSamplePageTest} from '../ckeditorSamplePageTest';

const classicPageTest = ckeditorSamplePageTest.extend<{
	classicPage: ClassicPage;
}>({
	classicPage: async ({ckeditorSamplePage}, use) => {
		const classicPage: ClassicPage = await ckeditorSamplePage.gotoTab(
			TabName.CLASSIC
		);

		await use(classicPage);
	},
});

export {classicPageTest};
