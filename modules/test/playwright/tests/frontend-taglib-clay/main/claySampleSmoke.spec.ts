/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {claySamplePageTest} from './fixtures/claySamplePageTest';
import {TabName} from './pages/ClaySamplePage';

const test = claySamplePageTest;

test(
	'Clay Sample portlet can render each tab UI',
	async ({claySamplePage}) => {
		const tabs = [
			TabName.BADGES,
			TabName.DROPDOWNS,
			TabName.LABELS,
			TabName.LINKS,
			TabName.NAVIGATION_BARS,
			TabName.PAGINATION_BARS,
			TabName.PROGRESS_BARS,
			TabName.STICKERS,
		];

		for (const tab of tabs) {
			await test.step(`Verify ${tab} tab renders`, async () => {
				await claySamplePage.selectTab(tab);
			});
		}
	}
);
