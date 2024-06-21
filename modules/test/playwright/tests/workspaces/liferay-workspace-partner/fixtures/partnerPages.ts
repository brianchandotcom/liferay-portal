/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {MDFRequestFormPage} from '../pages/MDFRequestFormPage';
import {PartnerHomePage} from '../pages/PartnerHomePage';

const partnerPagesTest = test.extend<{
	partnerHomePage: PartnerHomePage;
	partnerMDFRequestForm: MDFRequestFormPage;
}>({
	partnerHomePage: async ({page}, use) => {
		await use(new PartnerHomePage(page));
	},
	partnerMDFRequestForm: async ({page}, use) => {
		await use(new MDFRequestFormPage(page));
	},
});

export {partnerPagesTest};
