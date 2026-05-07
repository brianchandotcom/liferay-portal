/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import addApprovedStructuredContent from '../../../utils/structured-content/addApprovedStructuredContent';
import getBasicWebContentStructureId from '../../../utils/structured-content/getBasicWebContentStructureId';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import getWidgetDefinition from '../../layout-content-page-editor-web/main/utils/getWidgetDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest()
);

test(
	'Web Content Display exposes the resource primary key as analytics asset id',
	{tag: '@LPD-89267'},
	async ({apiHelpers, page, site}) => {
		const contentStructureId =
			await getBasicWebContentStructureId(apiHelpers);

		const webContent = await addApprovedStructuredContent({
			apiHelpers,
			contentStructureId,
			siteId: site.id,
			title: getRandomString(),
		});

		const resourcePrimKey = String(
			(webContent as StructuredContent & {id: number}).id
		);

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getWidgetDefinition({
					id: getRandomString(),
					widgetConfig: {
						articleId: webContent.key,
						groupId: String(site.id),
					},
					widgetName:
						'com_liferay_journal_content_web_portlet_JournalContentPortlet',
				}),
			]),
			siteId: site.id,
			title: getRandomString(),
		});

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

		const articleDiv = page.locator('.journal-content-article').first();

		await expect(articleDiv).toHaveAttribute(
			'data-analytics-asset-id',
			resourcePrimKey
		);
		await expect(articleDiv).toHaveAttribute(
			'data-analytics-web-content-resource-pk',
			resourcePrimKey
		);
	}
);
