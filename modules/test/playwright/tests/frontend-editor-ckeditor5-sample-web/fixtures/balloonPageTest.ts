/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {BalloonPage} from '../pages/BalloonPage';
import {TabName} from '../pages/CKEditor5SamplePage';
import {ckeditor5SamplePageTest} from './ckeditor5SamplePageTest';

const balloonPageTest = ckeditor5SamplePageTest.extend<{
	balloonPage: BalloonPage;
}>({
	balloonPage: async ({ckeditor5SamplePage}, use) => {
		const balloonPage: BalloonPage = await ckeditor5SamplePage.gotoTab(
			TabName.BALLOON
		);

		await use(balloonPage);
	},
});

export {balloonPageTest};
