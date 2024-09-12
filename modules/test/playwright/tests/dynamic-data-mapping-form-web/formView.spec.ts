/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {formsPagesTest} from '../../fixtures/formsPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import {getRandomInt} from '../../utils/getRandomInt';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	formsPagesTest,
	loginTest()
);

let objectDefinitions: ObjectDefinition[] = [];

test.afterEach(async ({apiHelpers}) => {
	if (objectDefinitions.length) {
		for (const objectDefinition of objectDefinitions) {
			await apiHelpers.objectAdmin.deleteObjectDefinition(
				objectDefinition.id
			);
		}

		objectDefinitions = [];
	}
});

test.describe('FormView when form storage type is object', () => {
	test.beforeEach(({page}) => {
		page.setViewportSize({height: 1080, width: 1920});
	});

	test('make sure the button submit label is Submit to workflow when the object definition has a linked workflow and Save when it does not', async ({
		apiHelpers,
		applicationsMenuPage,
		configurationTabPage,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		formsPage,
		page,
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 0},
			});

		objectDefinitions.push(objectDefinition);

		await apiHelpers.objectAdmin.postObjectDefinitionPublish(
			objectDefinition.id
		);

		await formBuilderPage.goToNew();

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		const formTitle = 'Form' + getRandomInt();

		await formBuilderPage.fillFormTitle(formTitle);

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.selectObjectField('textField');

		await expect(formBuilderSidePanelPage.objectFieldSelect).toBeVisible();

		await page.waitForTimeout(200);

		const newTabPagePromise = new Promise<Page>((resolve) =>
			formBuilderPage.page.once('popup', resolve)
		);

		await formBuilderPage.clickSaveButton();

		await formBuilderPage.publishButton.click();

		await formBuilderPage.clickOpenFormButton();

		const newTabPage = await newTabPagePromise;

		newTabPage.setViewportSize({height: 1080, width: 1920});

		await expect(
			newTabPage.getByRole('button', {
				name: 'Save',
			})
		).toBeVisible();

		await newTabPage.close();

		await applicationsMenuPage.goToProcessBuilder();

		await configurationTabPage.configurationTabLink.click();

		await configurationTabPage.assignWorkflowToAssetType(
			'Single Approver',
			objectDefinition.label['en_US']
		);

		await page.goto('/');

		await formsPage.goTo();

		await formsPage.clickFormTitle(formTitle);

		await formBuilderPage.unpublishButton.click();

		await page.waitForTimeout(200);

		const newTabPagePromise2 = new Promise<Page>((resolve) =>
			formBuilderPage.page.once('popup', resolve)
		);

		await formBuilderPage.publishButton.click();

		await formBuilderPage.clickOpenFormButton();

		const newTabPage2 = await newTabPagePromise2;

		await page.waitForTimeout(200);

		await expect(
			newTabPage2.getByRole('button', {
				name: 'Submit for Workflow',
			})
		).toBeVisible();
	});
});
