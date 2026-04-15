/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {formsPagesTest} from '../../../fixtures/formsPagesTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import {generateObjectFields} from '../utils/generateObjectFields';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	formsPagesTest,
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

test(
	'cannot change required option when field is mapped to object field',
	{tag: '@LPS-136456'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
	}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: [{businessType: 'Text', required: true}],
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

		const fieldLabel = objectFields[0].label!['en_US'];

		await formBuilderPage.goToNew();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.selectObjectField(fieldLabel);

		await formBuilderSidePanelPage.clickBasicTab();

		await expect(
			formBuilderSidePanelPage.requiredFieldToggleSwitch
		).toBeVisible();

		await expect(
			formBuilderSidePanelPage.requiredFieldToggleSwitch
		).toBeDisabled();
	}
);

test(
	'cannot map fields with different types',
	{tag: '@LPS-133365'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await formBuilderPage.goToNew();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		await formBuilderSidePanelPage.addFieldByDoubleClick('Numeric');

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.objectFieldSelect.click();

		await expect(
			page.getByRole('option', {
				name: 'There are no compatible object fields to map.',
			})
		).toBeVisible();
	}
);

test(
	'cannot view forms entries when form is mapped to an object',
	{tag: '@LPS-136456'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		formsPage,
		page,
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await formBuilderPage.goToNew();

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

		await page.waitForTimeout(1000);

		await formBuilderPage.clickSaveButton();

		await expect(formBuilderPage.entriesTab).not.toBeVisible();

		await formsPage.goTo();

		await page
			.getByRole('row', {name: formTitle})
			.getByRole('button', {name: 'Show Actions'})
			.click();

		await expect(
			page.getByRole('menuitem', {name: 'View Entries'})
		).not.toBeVisible();
	}
);

test(
	'repeatable and searchable options are not available on form mapped to an object',
	{tag: '@LPS-136456'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await formBuilderPage.goToNew();

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

		await formBuilderSidePanelPage.clickAdvancedTab();

		await expect(
			formBuilderSidePanelPage.repeatableFieldToggleSwitch
		).not.toBeVisible();

		await expect(page.getByLabel('Searchable')).not.toBeVisible();
	}
);
