/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InputLocalizedPage} from '../../pages/ckeditor5/InputLocalizedPage';
import {ckeditorSamplePageTest} from "../ckeditorSamplePageTest";
import {SubTabName, TabName} from "../../pages/CKEditorSamplePage";

const inputLocalizedPageTest = ckeditorSamplePageTest.extend<{
	inputLocalizedPage: InputLocalizedPage;
}>({
	inputLocalizedPage: async ({ckeditorSamplePage}, use) => {
		const inputLocalizedPage: InputLocalizedPage =
			await ckeditorSamplePage.gotoTab(
				TabName.CK_EDITOR_5, SubTabName.INPUT_LOCALIZED
			);

		await use(inputLocalizedPage);
	},
});

export {inputLocalizedPageTest};
