/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {formsPagesTest} from '../../fixtures/formsPagesTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {productMenuPageTest} from '../../fixtures/productMenuPageTest';
import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';
import getLoremIpsumText from '../../utils/lorem-ipsum-util/getLoremIpsumText';
import getGridDefinition from '../layout-content-page-editor-web/utils/getGridDefinition';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../layout-content-page-editor-web/utils/getWidgetDefinition';
import evaluateKeepCheckingAfterFound from '../object-web/utils/keepCheckingAfterFound';

const test = mergeTests(
	applicationsMenuPageTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	dataApiHelpersTest,
	formsPagesTest,
	isolatedSiteTest,
	loginTest(),
	productMenuPageTest
);

test.use({
	permissions: ['clipboard-write'],
});

test.describe('Forms widget', () => {
	test.beforeEach(({page}) => {
		page.setViewportSize({height: 1080, width: 1920});
	});

	test('renders in the page builder phone preview and automatically scrolls to the first field when navigating to the next form page', async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formWidgetPage,
		page,
		pageEditorPage,
		site,
	}) => {
		const gridId = getRandomString();

		const grid = getGridDefinition({
			columns: [{pageElements: [], size: 1}],
			id: gridId,
		});

		const formWidgetDefinition = getWidgetDefinition({
			id: getRandomString(),
			widgetName:
				'com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormPortlet',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([grid, formWidgetDefinition]),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await pageEditorPage.publishPage();

		await formBuilderPage.goToNew(site.friendlyUrlPath);

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		const formName = 'Form' + getRandomInt();

		await formBuilderPage.fillFormTitle(formName);

		const loremIpsumText = getLoremIpsumText([50, 100, 200]);

		for (let index = 0; index < 8; index++) {
			await formBuilderSidePanelPage.addFieldByDoubleClick('Paragraph');

			await formBuilderSidePanelPage.fillParagraphField({
				text: loremIpsumText,
			});

			await formBuilderSidePanelPage.paragraphFieldTitle.fill(
				`Paragraph ${index}`
			);

			await formBuilderSidePanelPage.clickBackButton();

			if (index === 3) {
				await formBuilderPage.newPageButton.click();
			}
		}

		await formBuilderPage.publishButton.click();

		await expect(
			page.getByText('Your request completed successfully')
		).toBeVisible();

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await formWidgetPage.setFormWidgetConfiguration(formName);

		await page.waitForLoadState();

		await page.reload();

		await expect(page.getByText(formName)).toBeVisible();

		await page.getByLabel('Portrait Phone').click();

		await page.waitForLoadState('networkidle');

		const keepsAttached = await evaluateKeepCheckingAfterFound({
			duration: 4000,
			iframeSelector: '.page-editor__global-context-iframe',
			page,
			selector:
				'form .lfr-ddm-form-pagination-controls button.lfr-ddm-form-pagination-next',
		});

		expect(keepsAttached).toBe(true);

		await pageEditorPage.publishPage();

		await page.goto('/web' + site.friendlyUrlPath);

		await expect(formWidgetPage.nextButton).toBeVisible();

		await formWidgetPage.nextButton.click();

		await expect(
			page.getByLabel('Paragraph 4<p>Nam tincidunt')
		).toBeInViewport();
	});
});
