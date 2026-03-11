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
import {generateObjectFields} from './utils/generateObjectFields';

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
	'LPD-78504 Cannot change required option when field is mapped to object field',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
	}) => {
		// Corresponds to Poshi test: CannotChangeRequiredOption

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

		const fieldLabel = objectDefinition.objectFields.find(
			(field) => field.businessType === 'Text'
		).label['en_US'];

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
			page.getByRole('switch', {name: 'Required Field'})
		).toBeDisabled();
	}
);

test(
	'LPD-78504 Cannot map with different field types',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
	}) => {
		// Corresponds to Poshi test: CannotMapWithDifferentFieldTypes

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

		const textFieldLabel = objectDefinition.objectFields.find(
			(field) => field.businessType === 'Text'
		).label['en_US'];

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
			formBuilderSidePanelPage.getSelectOptionLocator(textFieldLabel)
		).toBeHidden();
	}
);

test(
	'LPD-78504 Cannot map with different field types for field group',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
	}) => {
		// Corresponds to Poshi test: CannotMapWithDifferentFieldTypesForFieldGroup

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

		const textFieldLabel = objectDefinition.objectFields.find(
			(field) => field.businessType === 'Text'
		).label['en_US'];

		await formBuilderPage.goToNew();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

		await formBuilderSidePanelPage.clickBackButton();

		await formBuilderSidePanelPage.addFieldToFieldGroup('Numeric', 0);

		await expect(
			page.getByLabel('Fields Group', {exact: true})
		).toBeVisible();

		await formBuilderPage.openFieldSettings('Numeric');

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.objectFieldSelect.click();

		await expect(
			formBuilderSidePanelPage.getSelectOptionLocator(textFieldLabel)
		).toBeHidden();
	}
);

test(
	'LPD-78504 Cannot view forms entries when form is mapped to an object',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
	}) => {
		// Corresponds to Poshi test: CannotViewFormsEntries

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

		await formBuilderPage.goToNew();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

		await expect(formBuilderPage.formTab).toBeVisible();

		await expect(formBuilderPage.entriesTab).toBeHidden();
	}
);

test(
	'LPD-78504 Repeatable option is not available on form mapped to object',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
	}) => {
		// Corresponds to Poshi test: RepeatableOptionNotAvailable

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

		await expect(
			formBuilderSidePanelPage.repeatableFieldToggleSwitch
		).toBeHidden();
	}
);

test(
	'LPD-78504 Searchable option is not available on form mapped to object',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
	}) => {
		// Corresponds to Poshi test: SearchableOptionNotAvailable

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

		await expect(page.getByLabel('Searchable')).toBeHidden();
	}
);
