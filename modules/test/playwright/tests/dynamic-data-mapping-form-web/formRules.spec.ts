/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinitionApi,
	ObjectField,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {formsPagesTest} from '../../fixtures/formsPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../fixtures/systemSettingsPageTest';
import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';
import {deleteItems} from './utils/deleteItems';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-31212': true,
	}),
	loginTest(),
	formsPagesTest,
	systemSettingsPageTest
);

test.afterEach(async ({formsPage, page, systemSettingsPage}) => {
	await formsPage.goTo();

	await deleteItems(formsPage, page);

	await formsPage.dataProvidersTab.click();

	await deleteItems(formsPage, page);

	await systemSettingsPage.goToSystemSetting(
		'Data Providers',
		'Data Providers'
	);

	await page.getByLabel('Access Local Network').uncheck();

	await page
		.getByRole('button', {name: 'Save'})
		.or(page.getByRole('button', {name: 'Update'}))
		.click();

	await expect(page.getByText('Success:Your request')).toBeVisible();
});

test('Select from list with multiple selections allowed is auto-filled by data provider defined in rule', async ({
	apiHelpers,
	dataProviderPage,
	formBuilderFieldSettingsSidePanelPage,
	formBuilderPage,
	formBuilderSidePanelPage,
	formsPage,
	page,
	rulesBuilderPage,
	systemSettingsPage,
}) => {
	test.slow();

	const baseObjectField: Partial<ObjectField> = {
		DBType: ObjectField.DBTypeEnum.String,
		businessType: ObjectField.BusinessTypeEnum.Text,
		indexed: true,
		indexedAsKeyword: false,
		indexedLanguageId: '',
		localized: false,
		required: false,
		system: false,
		type: ObjectField.TypeEnum.String,
	};

	const objectFields = [
		{
			externalReferenceCode: 'CountryERC',
			label: {en_US: 'Country'},
			name: 'country',
			...baseObjectField,
		},
		{
			externalReferenceCode: 'CityERC',
			label: {en_US: 'City'},
			name: 'city',
			...baseObjectField,
		},
	];

	const objectDefinitionExternalReferenceCode =
		'ObjectDefinition' + getRandomInt();

	const objectDefinitionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: objectDefinition} =
		await objectDefinitionAPIClient.postObjectDefinition({
			active: true,
			enableLocalization: false,
			externalReferenceCode: objectDefinitionExternalReferenceCode,
			label: {
				en_US: objectDefinitionExternalReferenceCode,
			},
			name: objectDefinitionExternalReferenceCode,
			objectFields,
			objectFolderExternalReferenceCode: 'default',
			pluralLabel: {
				en_US: objectDefinitionExternalReferenceCode,
			},
			portlet: true,
			scope: 'company',
			status: {code: 0},
			titleObjectFieldName: 'country',
		});

	apiHelpers.data.push({
		id: objectDefinition.id,
		type: 'objectDefinition',
	});

	const applicationName = 'c/' + objectDefinition.name.toLowerCase() + 's';

	await apiHelpers.objectEntry.postObjectEntry(
		{
			city: 'Recife',
			country: 'Brazil',
		},
		applicationName
	);

	await apiHelpers.objectEntry.postObjectEntry(
		{
			city: 'Paris',
			country: 'France',
		},
		applicationName
	);

	// configure data provider

	await systemSettingsPage.goToSystemSetting(
		'Data Providers',
		'Data Providers'
	);

	await page.getByLabel('Access Local Network').check();

	await page
		.getByRole('button', {name: 'Save'})
		.or(page.getByRole('button', {name: 'Update'}))
		.click();

	await expect(page.getByText('Success:Your request')).toBeVisible();

	await expect(page.getByLabel('Access Local Network')).toBeChecked();

	await formsPage.goTo();

	await formsPage.dataProvidersTab.click();

	await dataProviderPage.addNewDataProviderLink.click();

	const dataProviderName = getRandomString();

	await dataProviderPage.nameInputField.fill(dataProviderName);

	await dataProviderPage.urlInputField.fill(
		`${apiHelpers.baseUrl}${applicationName}/?aggregationTerms=country`
	);

	await dataProviderPage.userNameInputField.fill('test@liferay.com');

	await dataProviderPage.passwordInputField.fill('test');

	await dataProviderPage.timeoutInputField.fill('30000');

	await dataProviderPage.outputPathInputField.fill(
		'$.facets[0].facetValues[*].term'
	);

	await dataProviderPage.selectOutputType('List');

	await dataProviderPage.outputLabel.fill('Values found');

	await dataProviderPage.saveButton.click();

	await expect(page.getByText('Success:Your request')).toBeVisible();

	await formsPage.formsTab.click();

	// create form and configure two fields

	await formsPage.newFormButton.click();

	await formBuilderSidePanelPage.addSingleSelectionButton.dblclick();

	await formBuilderFieldSettingsSidePanelPage.addOptions(2);

	await formBuilderSidePanelPage.backButton.click();

	await formBuilderSidePanelPage.addSelectFromListButton.dblclick();

	await formBuilderFieldSettingsSidePanelPage.selectCreateListSetting(
		'From Autofill'
	);

	await formBuilderFieldSettingsSidePanelPage.advancedTabButton.click();

	await formBuilderFieldSettingsSidePanelPage.allowMultipleSelections();

	await page.waitForLoadState('networkidle');

	await formBuilderSidePanelPage.backButton.click();

	// configure rule

	await rulesBuilderPage.rulesTab.click();

	await rulesBuilderPage.addElementsButton.click();

	await rulesBuilderPage.selectConditionLeftFormField('Single Selection');

	await rulesBuilderPage.selectConditionOperator('Is Equal To');

	await rulesBuilderPage.selectConditionOperatorValueSource('Value');

	await rulesBuilderPage.selectConditionRightFormField('Option1');

	await rulesBuilderPage.selectAction('Autofill');

	await rulesBuilderPage.selectAutofillDataProvider(dataProviderName);

	await rulesBuilderPage.selectDataProviderOutput('Select from List');

	await rulesBuilderPage.saveButton.click();

	// assert the presence of auto-filled options in multiple selection field only when rule condition is met

	await formBuilderPage.formTab.click();

	const formPreviewPagePromise = page.waitForEvent('popup');

	await formBuilderPage.previewButton.click();

	const formPreviewPage = await formPreviewPagePromise;

	await formPreviewPage.getByLabel('Option0').check();

	await formPreviewPage.getByPlaceholder('Choose Options').click();

	await expect(
		formPreviewPage.getByRole('option', {name: 'No results found'})
	).toBeVisible();

	await formPreviewPage.getByLabel('Option1').check();

	await formPreviewPage.getByPlaceholder('Choose Options').click();

	await expect(
		formPreviewPage.getByRole('option', {name: 'Brazil'})
	).toBeVisible();

	await expect(
		formPreviewPage.getByRole('option', {name: 'France'})
	).toBeVisible();
});
