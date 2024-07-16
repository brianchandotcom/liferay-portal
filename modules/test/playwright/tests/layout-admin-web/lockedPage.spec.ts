/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {checkAccessibility} from '../../utils/checkAccessibility';

const test = mergeTests(loginTest());

const LOCKED_PAGE_URL =
	'/~/control_panel/manage?p_p_id=com_liferay_layout_admin_web_portlet_GroupPagesPortlet&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_mvcRenderCommandName=%2Flayout_admin%2Flocked_layout';

test('Checks accessibility of locked page', async ({page}) => {
	await page.goto(`/group/guest${LOCKED_PAGE_URL}`);

	await checkAccessibility({
		page,
	});
});
