/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinitionApi,
	ObjectField,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import {liferayConfig} from '../../liferay.config';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../utils/getRandomString';
import {
	disableSystemFeatureFlag,
	enableSystemFeatureFlag,
} from '../../utils/systemFeatureFlag';
import {waitForAlert} from '../../utils/waitForAlert';
import getFormContainerDefinition from '../layout-content-page-editor-web/utils/getFormContainerDefinition';
import getFragmentDefinition from '../layout-content-page-editor-web/utils/getFragmentDefinition';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../layout-content-page-editor-web/utils/getWidgetDefinition';
import {pagesPagesTest} from './fixtures/pagesPagesTest';

const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pagesAdminPagesTest,
	pagesPagesTest,
	pageEditorPagesTest
);

test(
	'Can configure and delete content page via header ellipsis icon at edit mode',
	{
		tag: '@LPS-137155',
	},
	async ({apiHelpers, page, pageEditorPage, site}) => {

		// Create a content page

		const pageName = getRandomString();

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			siteId: site.id,
			title: pageName,
		});

		// Configure page in edit mode

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page
				.locator('.dropdown-menu')
				.getByRole('menuitem', {name: 'Configure'}),
			trigger: page
				.locator('.control-menu-nav-item')
				.getByLabel('Options', {exact: true}),
		});

		await expect(page.getByText('Basic Info')).toBeVisible();

		// Delete page in edit mode

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page
				.locator('.dropdown-menu')
				.getByRole('menuitem', {name: 'Delete'}),
			trigger: page
				.locator('.control-menu-nav-item')
				.getByLabel('Options', {exact: true}),
		});

		await page.getByRole('button', {name: 'Delete'}).click();

		await expect(page.getByText('No Pages Yet.')).toBeVisible();
	}
);

test('Does not show widget topper on hover in view mode', async ({
	apiHelpers,
	page,
	site,
}) => {

	// Create a page with a Dropdown fragment and a Breadcrumb widget below
	// This case is specific to cover LPP-45872

	const fragmentId = getRandomString();

	const fragmentDefinition = getFragmentDefinition({
		id: fragmentId,
		key: 'BASIC_COMPONENT-dropdown',
	});

	const widgetId = getRandomString();

	const widgetDefinition = getWidgetDefinition({
		id: widgetId,
		widgetName:
			'com_liferay_site_navigation_breadcrumb_web_portlet_SiteNavigationBreadcrumbPortlet',
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			fragmentDefinition,
			widgetDefinition,
		]),
		siteId: site.id,
		title: getRandomString(),
	});

	// Go to view mode and check widget topper is not shown

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

	await page.locator('.portlet-breadcrumb').hover();

	await expect(
		page.locator('.portlet-topper').getByText('Breadcrumb')
	).not.toBeVisible();
});

test('Checks the correct label for restricted page in the page heading', async ({
	apiHelpers,
	page,
	site,
}) => {

	// Create a content page with only one permission

	const pageName = getRandomString();

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pagePermissions: [
			{
				actionKeys: ['VIEW'],
				roleKey: 'Owner',
			},
		],
		siteId: site.id,
		title: pageName,
	});

	// Go to the view mode and check the restricted page label

	await page.goto(
		`${liferayConfig.environment.baseUrl}/en/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
	);

	const header = page.getByRole('heading', {name: pageName});

	await header.waitFor({state: 'visible'});

	await expect(header.getByText('Restricted Page')).toBeVisible();
});

test(
	'Checks page title in view mode and in edit mode',
	{
		tag: '@LPS-146373',
	},
	async ({apiHelpers, page, site}) => {

		// Create a content page

		const pageName = getRandomString();

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			siteId: site.id,
			title: pageName,
		});

		// Check the page title in the view mode

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

		expect(await page.title()).toBe(
			`${pageName} - ${site.name} - Liferay DXP`
		);

		// Check the page title in the edit mode

		await page.getByTitle('Edit', {exact: true}).click();

		await page
			.getByText('Drag and drop fragments or widgets here.')
			.waitFor();

		expect(await page.title()).toBe(
			`${pageName} - ${site.name} - Liferay DXP (Editing)`
		);

		// Click back button

		await page.getByTitle(`Go to ${pageName}`).click();

		await page.getByTitle('Edit', {exact: true}).waitFor();

		expect(await page.title()).toBe(
			`${pageName} - ${site.name} - Liferay DXP`
		);
	}
);

test(
	'Discarding a draft will revert a content page back to its most recent published version',
	{
		tag: ['@LPS-78726', '@LPS-168168'],
	},
	async ({apiHelpers, page, pageEditorPage, pagesAdminPage, site}) => {

		// Create a page with a heading fragment

		const fragmentId = getRandomString();

		const fragmentDefinition = getFragmentDefinition({
			id: fragmentId,
			key: 'BASIC_COMPONENT-heading',
		});

		const layoutTitle = getRandomString();

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([fragmentDefinition]),
			siteId: site.id,
			title: layoutTitle,
		});

		// Go to edit mode and remove fragment

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await pageEditorPage.removeFragment(fragmentId);

		// Discard draft

		await pagesAdminPage.goto(site.friendlyUrlPath);

		page.on('dialog', (dialog) => dialog.accept());

		await pagesAdminPage.clickOnAction('Discard Draft', layoutTitle);

		await waitForAlert(page);

		// Go to edit mode and assert fragment is present

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await expect(page.getByText('Heading Example')).toBeVisible();
	}
);

test(
	'In edit mode the page should still show the elements of the page that are not from the page itself, like the header, footer, or elements defined by the theme',
	{
		tag: '@LPS-81870',
	},
	async ({apiHelpers, page, pageEditorPage, site}) => {

		// Create a page and go to edit mode

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition(),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// Assert header and footer

		await expect(page.getByTitle(`Go to ${site.name}`)).toBeVisible();

		await expect(
			page.getByText('This search bar is not visible to users yet.')
		).toBeVisible();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByText(
				'This area is defined by the theme. You can change the theme settings by clicking More in the Page Design Options panel on the sidebar.'
			),
			trigger: page.locator('#banner'),
		});

		await expect(page.getByText('Powered by ')).toBeAttached();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByText(
				'This area is defined by the theme. You can change the theme settings by clicking More in the Page Design Options panel on the sidebar.'
			),
			trigger: page.locator('#footer'),
		});
	}
);

test(
	'The deprecated label exist for the contributed Featured Content Fragment Set',
	{
		tag: '@LPD-42061',
	},
	async ({apiHelpers, page, pageEditorPage, site}) => {

		// Enable feature flag

		await enableSystemFeatureFlag({
			page,
			title: 'Featured Content Fragment Set',
			type: 'Deprecation',
		});

		// Create a content page

		const pageName = getRandomString();

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			siteId: site.id,
			title: pageName,
		});

		// Go to edit mode and check deprecated Feature Content

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await expect(
			page.getByRole('menuitem', {name: 'Featured Content Deprecated'})
		).toBeVisible();

		// Disable feature flag

		await disableSystemFeatureFlag({
			page,
			title: 'Featured Content Fragment Set',
			type: 'Deprecation',
		});
	}
);

test(
	'Having a rich text field named "Content" does not break the page in view mode',
	{
		tag: '@LPD-42061',
	},
	async ({apiHelpers, page, pageEditorPage, site}) => {

		// Create the object definition

		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionApi);

		const {body: objectDefinition} =
			await objectDefinitionAPIClient.postObjectDefinition({
				active: true,
				externalReferenceCode: 'papaERC',
				label: {
					en_US: 'Papa',
				},
				name: 'Papa',
				objectFields: [
					{
						DBType: ObjectField.DBTypeEnum.String,
						businessType: ObjectField.BusinessTypeEnum.Text,
						externalReferenceCode: 'nameERC',
						indexed: true,
						indexedAsKeyword: true,
						label: {
							en_US: 'Name',
						},
						name: 'name',
						required: false,
					},
					{
						DBType: ObjectField.DBTypeEnum.Clob,
						businessType: ObjectField.BusinessTypeEnum.RichText,
						externalReferenceCode: 'contentERC',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {
							en_US: 'content',
						},
						name: 'content',
						required: false,
					},
				],
				pluralLabel: {
					en_US: 'Papas',
				},
				portlet: true,
				scope: 'company',
				status: {
					code: 0,
				},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		// Create a content page with a form container and go to edit mode

		const formId = getRandomString();

		const formDefinition = getFormContainerDefinition({
			id: formId,
		});

		const pageName = getRandomString();

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([formDefinition]),
			siteId: site.id,
			title: pageName,
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// Go to edit mode and map the form container to the object

		await pageEditorPage.mapFormFragment(formId, 'Papa');

		await pageEditorPage.publishPage();

		// Go to view mode of page and check both fields are shown

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

		await expect(
			page.locator('.control-label', {hasText: 'Content'})
		).toBeVisible();

		await expect(page.locator('.cke_contents')).toBeVisible();

		await expect(page.getByLabel('Name')).toBeVisible();

		// Wait for five seconds and check Name field does not disappear

		await page.waitForTimeout(5000);

		await expect(page.getByLabel('Name')).toBeVisible();
	}
);
