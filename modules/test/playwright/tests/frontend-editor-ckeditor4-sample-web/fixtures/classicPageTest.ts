/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TabName} from '../pages/CKEditor4SamplePage';
import {ClassicPage} from '../pages/ClassicPage';
import {ckeditor4SamplePageTest} from './ckeditor4SamplePageTest';

const classicPageTest = ckeditor4SamplePageTest.extend<{
	classicPage: ClassicPage;
}>({
	classicPage: async ({ckeditor4SamplePage}, use) => {
		const classicPage: ClassicPage = await ckeditor4SamplePage.gotoTab(
			TabName.CLASSIC
		);

		await use(classicPage);
	},
});

export {classicPageTest};
