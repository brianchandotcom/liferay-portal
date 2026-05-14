/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SubTabName} from '../../frontend-editor-ckeditor-sample-web/pages/CKEditorSamplePage';
import {ClassicPage} from '../pages/ClassicPage';
import {ckeditor5SamplePageTest} from './ckeditor5SamplePageTest';

const classicPageTestFor = (subTabName: SubTabName) =>
	ckeditor5SamplePageTest.extend<{
		classicPage: ClassicPage;
	}>({
		classicPage: async ({ckeditor5SamplePage}, use) => {
			const classicPage: ClassicPage =
				await ckeditor5SamplePage.gotoTab(subTabName);

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
