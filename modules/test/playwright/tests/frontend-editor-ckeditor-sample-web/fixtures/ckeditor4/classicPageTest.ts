/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClassicPage} from '../../pages/ckeditor4/ClassicPage';
import {ckeditorSamplePageTest} from "../ckeditorSamplePageTest";
import {SubTabName, TabName} from "../../pages/CKEditorSamplePage";

const classicPageTest = ckeditorSamplePageTest.extend<{
	classicPage: ClassicPage;
}>({
	classicPage: async ({ckeditorSamplePage}, use) => {
		const classicPage: ClassicPage =
			await ckeditorSamplePage.gotoTab(
				TabName.CK_EDITOR_4, SubTabName.CLASSIC
			);

		await use(classicPage);
	},
});

export {classicPageTest};
