/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SiteTemplate} from '../types/SiteTemplate';
import ApiHelper from './ApiHelper';

const SITE_TEMPLATES_URL = '/o/headless-admin-site/v1.0/site-templates';

async function getActiveSiteTemplates() {
	return await ApiHelper.get<{items: SiteTemplate[]}>(
		`${SITE_TEMPLATES_URL}?active=true`
	);
}

export default {
	getActiveSiteTemplates,
};
