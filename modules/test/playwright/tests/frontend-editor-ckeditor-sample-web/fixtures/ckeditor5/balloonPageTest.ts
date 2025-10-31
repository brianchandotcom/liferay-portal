/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SubTabName, TabName} from '../../pages/CKEditorSamplePage';
import {BalloonPage} from '../../pages/ckeditor5/BalloonPage';
import {ckeditorSamplePageTest} from '../ckeditorSamplePageTest';

const balloonPageTest = ckeditorSamplePageTest.extend<{
	balloonPage: BalloonPage;
}>({
	balloonPage: async ({ckeditorSamplePage}, use) => {
		const ballonPage: BalloonPage = await ckeditorSamplePage.gotoTab(
			TabName.CK_EDITOR_5,
			SubTabName.BALLOON
		);

		await use(ballonPage);
	},
});

export {balloonPageTest};
