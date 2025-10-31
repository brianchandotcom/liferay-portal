/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SubTabName, TabName} from '../../pages/CKEditorSamplePage';
import {ClassicPage} from '../../pages/ckeditor5/ClassicPage';
import {ckeditorSamplePageTest} from '../ckeditorSamplePageTest';

const classicPageTestFor = (subTabName: SubTabName) =>
	ckeditorSamplePageTest.extend<{
		classicPage: ClassicPage;
	}>({
		classicPage: async ({ckeditorSamplePage}, use) => {
			const classicPage: ClassicPage = await ckeditorSamplePage.gotoTab(
				TabName.CK_EDITOR_5,
				subTabName
			);

			await use(classicPage);
		},
	});

const advancedClassicPageTest = classicPageTestFor(SubTabName.ADVANCED_CLASSIC);
const basicClassicPageTest = classicPageTestFor(SubTabName.BASIC_CLASSIC);
const reactClassicPageTest = classicPageTestFor(SubTabName.REACT);
const reactPlusCETClassicPageTest = classicPageTestFor(
	SubTabName.REACT_PLUS_CET
);

export {
	advancedClassicPageTest,
	basicClassicPageTest,
	reactClassicPageTest,
	reactPlusCETClassicPageTest,
};
