/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {MDFRequestFormPage} from '../pages/MDFRequesListPage/MDFRequestFormPage';
import {MDFRequestListPage} from '../pages/MDFRequesListPage/mdfRequestListPage';
import {PartnerHomePage} from '../pages/partnerHomePage';

const partnerPagesTest = test.extend<{
	partnerHomePage: PartnerHomePage;
	partnerMDFListPage: MDFRequestListPage;
	partnerMDFRequestForm: MDFRequestFormPage;
}>({
	partnerHomePage: async ({page}, use) => {
		await use(new PartnerHomePage(page));
	},
	partnerMDFListPage: async ({page}, use) => {
		await use(new MDFRequestListPage(page));
	},
	partnerMDFRequestForm: async ({page}, use) => {
		await use(new MDFRequestFormPage(page));
	},
});

export {partnerPagesTest};
