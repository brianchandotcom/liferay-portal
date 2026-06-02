/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';

export const test = mergeTests(
	featureFlagsTest({'LPD-57655': {enabled: true}}),
	loginTest()
);

const NAMESPACE = '_com_liferay_exportimport_web_portlet_ExportImportPortlet_';

test(
	'sanitizes a javascript: redirect parameter on the portlet export Cancel link',
	{tag: '@LPD-92456'},
	async ({page}) => {
		const searchParams = new URLSearchParams({
			p_p_id: 'com_liferay_exportimport_web_portlet_ExportImportPortlet',
			p_p_lifecycle: '0',
			[NAMESPACE + 'mvcPath']: '/export_portlet.jsp',
			[NAMESPACE + 'portletConfiguration']: 'true',
			[NAMESPACE + 'portletResource']:
				'com_liferay_journal_web_portlet_JournalPortlet',
			[NAMESPACE + 'redirect']: 'javascript:alert(document.domain)',
			[NAMESPACE + 'resourcePrimKey']:
				'1_LAYOUT_com_liferay_journal_web_portlet_JournalPortlet',
		});

		await page.goto(
			`/group/guest/~/control_panel/manage?${searchParams.toString()}`
		);

		const cancelLink = page.getByRole('button', {name: 'Cancel'});

		await expect(cancelLink).toBeVisible();

		await expect(cancelLink).toHaveAttribute(
			'href',
			/^(?!\s*javascript:)/i
		);
	}
);
