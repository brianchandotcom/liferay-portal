/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {pageViewModePagesTest} from '../../../fixtures/pageViewModePagesTest';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
import { generateObjectFields } from '../utils/generateObjectFields';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest,
	pageViewModePagesTest
);

test(
	'Can add object portlet as a widget on a page',
	{tag: '@LPS-143122'},
	async ({
		apiHelpers,
		editObjectDetailsPage,
		page,
		site,
		widgetPagePage,
	}) => {
		// Corresponds to Poshi test: CanAddObjectPortletWidget

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await test.step('Enable "Show Widget in Page Builder" and save', async () => {
			await editObjectDetailsPage.goto(objectDefinition.label['en_US']);

			await editObjectDetailsPage.goToDetailsTab();

			await expect(
				editObjectDetailsPage.showWidgetToggle
			).toBeChecked();

			await editObjectDetailsPage.saveObjectDefinition();

			await waitForAlert(page, 'Success:');
		});

		await test.step('Create a widget page and add the object portlet', async () => {
			const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
				groupId: site.id,
				title: getRandomString(),
			});

			await page.goto(
				`/web${site.friendlyUrlPath}${layout.friendlyURL}`
			);

			await widgetPagePage.addPortlet(
				objectDefinition.pluralLabel['en_US']
			);
		});

		await test.step('Verify the portlet is displayed with empty state', async () => {
			await expect(
				page.getByText(objectDefinition.pluralLabel['en_US']).first()
			).toBeVisible();

			await expect(page.getByText('No Results Found')).toBeVisible();
		});
	}
);

test(
	'Cannot add object portlet as widget when widget button is disabled',
	{tag: '@LPS-143122'},
	async ({
		apiHelpers,
		editObjectDetailsPage,
		page,
		site,
		widgetPagePage,
	}) => {
		// Corresponds to Poshi test: CannotAddObjectPortletWhenWidgetDisabled

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await test.step('Navigate to object details and untoggle show widget', async () => {
			await editObjectDetailsPage.goto(objectDefinition.label['en_US']);

			await editObjectDetailsPage.goToDetailsTab();

			await editObjectDetailsPage.showWidgetToggle.uncheck();

			await editObjectDetailsPage.saveObjectDefinition();

			await waitForAlert(page, 'Success:');

		});

		await test.step('Search for the object portlet on a widget page and verify it is not available', async () => {
			const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
				groupId: site.id,
				title: getRandomString(),
			});

			await page.goto(
				`/web${site.friendlyUrlPath}${layout.friendlyURL}`
			);

			await widgetPagePage.openAddPanel();

			await page.getByLabel('Widgets', {exact: true}).click();

			await page
				.getByRole('textbox', {name: 'Search Form'})
				.fill(objectDefinition.pluralLabel['en_US']);
			
			await expect(page.getByRole('alert').getByText('There are no widgets on this page')).toBeVisible();		});
    }
);

test(
	'Object portlet widget disappears from page when widget button is disabled',
	{tag: '@LPS-143122'},
	async ({
		apiHelpers,
		editObjectDetailsPage,
		page,
		site,
		widgetPagePage,
	}) => {
		// Corresponds to Poshi test: ObjectPortletWidgetDisappearsWhenWidgetDisabled

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		let layout: Layout;

		await test.step('Create a widget page and add the object portlet', async () => {
			layout = await apiHelpers.jsonWebServicesLayout.addLayout({
				groupId: site.id,
				title: getRandomString(),
			});

			await page.goto(
				`/web${site.friendlyUrlPath}${layout.friendlyURL}`
			);

			await widgetPagePage.addPortlet(
				objectDefinition.pluralLabel['en_US']
			);
		});

		await test.step('Disable "Show Widget in Page Builder" and save', async () => {
			await editObjectDetailsPage.goto(objectDefinition.label['en_US']);

			await editObjectDetailsPage.goToDetailsTab();

			await editObjectDetailsPage.showWidgetToggle.uncheck();

			await editObjectDetailsPage.saveObjectDefinition();

			await waitForAlert(page, 'Success:');
		});

		await test.step('Navigate back to widget page and verify warning message', async () => {
			await page.goto(
				`/web${site.friendlyUrlPath}${layout.friendlyURL}`
			);

			await expect(
				page.getByText('This object is not available.')
			).toBeVisible();
		});
	}
);

test(
	'Widget button is enabled by default on object details',
	{tag: '@LPS-143122'},
	async ({apiHelpers, editObjectDetailsPage, page}) => {
		// Corresponds to Poshi test: WidgetButtonEnabledByDefault

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 2},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await test.step('Navigate to object details and verify widget toggle is checked by default', async () => {
			await editObjectDetailsPage.goto(objectDefinition.label['en_US']);

			await editObjectDetailsPage.goToDetailsTab();

			await expect(
				page.getByRole('switch', {
					name: 'Show Widget in Page Builder',
				})
			).toBeChecked();
		});
	}
);
