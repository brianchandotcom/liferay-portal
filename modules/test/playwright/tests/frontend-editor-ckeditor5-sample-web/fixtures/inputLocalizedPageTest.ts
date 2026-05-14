/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SubTabName} from '../../frontend-editor-ckeditor-sample-web/pages/CKEditorSamplePage';
import {InputLocalizedPage} from '../pages/InputLocalizedPage';
import {ckeditor5SamplePageTest} from './ckeditor5SamplePageTest';

const inputLocalizedPageTest = ckeditor5SamplePageTest.extend<{
	inputLocalizedPage: InputLocalizedPage;
}>({
	inputLocalizedPage: async ({ckeditor5SamplePage}, use) => {
		const inputLocalizedPage: InputLocalizedPage =
			await ckeditor5SamplePage.gotoTab(SubTabName.INPUT_LOCALIZED);

		await use(inputLocalizedPage);
	},
});

export {inputLocalizedPageTest};
