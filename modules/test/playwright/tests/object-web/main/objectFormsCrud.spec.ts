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
import {liferayConfig} from '../../../liferay.config';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {generateObjectFields} from './utils/generateObjectFields';
import {postListTypeDefinitionListTypeEntries} from './utils/postListTypeDefinitionListTypeEntries';

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
	'LPD-78504 Allow Multiple Selections option is not available for Select From List field when form is mapped to an object',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
		site,
	}) => {
		// Create a picklist with entries

		const {listTypeDefinition} =
			await postListTypeDefinitionListTypeEntries({
				apiHelpers,
				listTypeEntriesLength: 2,
			});

		// Create an object definition with a Picklist field

		const objectFields = generateObjectFields({
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			objectFieldBusinessTypes: ['Picklist'],
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

		const picklistFieldLabel = objectFields[0].label!['en_US'];

		// Create a new form mapped to the object

		await formBuilderPage.goToNew(site.friendlyUrlPath);

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		// Add a Select from List field and map it to the picklist object field

		await formBuilderSidePanelPage.addFieldByDoubleClick(
			'Select from List'
		);

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.selectObjectField(picklistFieldLabel);

		// Verify Allow Multiple Selections toggle is not visible

		await expect(
			page.getByLabel('Allow Multiple Selections')
		).toBeHidden();
	}
);

test(
	'LPD-78504 Can delete a form mapped to an object after adding entries',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		formsPage,
		page,
		site,
	}) => {
		// Create an object definition with a Text field

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

		const fieldLabel = objectFields[0].label!['en_US'];
		const formTitle = 'Form' + getRandomInt();

		// Create a new form mapped to the object

		await formBuilderPage.goToNew(site.friendlyUrlPath);

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		await formBuilderPage.fillFormTitle(formTitle);

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		// Add a Text field and map it to the object field

		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.objectFieldSelect.click();

		await page
			.getByRole('option', {name: fieldLabel})
			.dispatchEvent('click');

		await page.waitForTimeout(2000);

		// Save and publish the form

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL =
			await formBuilderPage.getFormSubmissionURL();

		// Navigate to the published form and submit an entry

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		await page.getByLabel('Text').fill('Entry Test');

		await page.getByRole('button', {name: 'Save'}).click();

		await expect(
			page.getByText('Your request completed successfully.')
		).toBeVisible();

		// Go to Forms admin page and delete the form

		await formsPage.goTo(site.friendlyUrlPath);

		await formsPage.managementToolbarSelectAllItems.click();

		formsPage.page.once('dialog', async (dialog) => {
			await dialog.accept();
		});

		await formsPage.managementToolbarDeleteButton.click();

		// Verify the form is no longer listed

		await expect(
			page.getByRole('link', {exact: true, name: formTitle})
		).toBeHidden();
	}
);

test(
	'LPD-78504 Can map and view entries for Rich Text field',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
		site,
	}) => {
		// Create an object definition with a RichText field

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['RichText'],
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

		// Create a new form mapped to the object

		await formBuilderPage.goToNew(site.friendlyUrlPath);

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		// Add a Rich Text field and map it to the object field

		await formBuilderSidePanelPage.addFieldByDoubleClick('Rich Text');

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.objectFieldSelect.click();

		await page
			.getByRole('option', {name: fieldLabel})
			.dispatchEvent('click');

		await page.waitForTimeout(2000);

		// Save and publish the form

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL =
			await formBuilderPage.getFormSubmissionURL();

		// Navigate to the published form and submit a rich text entry

		await page.goto(formSubmissionURL, {waitUntil: 'load'});

		const richTextContent =
			'By building a vibrant business, making technology useful, and investing in communities, we make it possible for people to reach their full potential to serve others.';

		const richTextEditor = page.locator(
			'.ck-editor__editable[contenteditable="true"]'
		);

		await richTextEditor.waitFor();

		await richTextEditor.click();

		await page.keyboard.type(richTextContent);

		await page.getByRole('button', {name: 'Save'}).click();

		await expect(
			page.getByText('Your request completed successfully.')
		).toBeVisible();

		// Verify the entry was created in the object

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);

		const item = items[0];
		const fieldName = objectFields[0].name;

		expect(item[fieldName]).toContain(richTextContent);
	}
);

test(
	'LPD-78504 Can map Clob type and view entries with Multiple Lines',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
		site,
	}) => {
		// Create an object definition with a LongText (Clob) field

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['LongText'],
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

		// Create a new form mapped to the object

		await formBuilderPage.goToNew(site.friendlyUrlPath);

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		// Add a Text field and switch it to Multiple Lines

		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

		await page.getByLabel('Multiple Lines').click();

		// Map to the object field

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.objectFieldSelect.click();

		await page
			.getByRole('option', {name: fieldLabel})
			.dispatchEvent('click');

		await page.waitForTimeout(2000);

		// Save and publish the form

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL =
			await formBuilderPage.getFormSubmissionURL();

		// Navigate to the published form and submit an entry

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		const entryText =
			'By building a vibrant business, making technology useful, and investing in communities, we make it possible for people to reach their full potential to serve others.';

		await page.getByRole('textbox').fill(entryText);

		await page.getByRole('button', {name: 'Save'}).click();

		await expect(
			page.getByText('Your request completed successfully.')
		).toBeVisible();

		// Verify the entry was created in the object

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);

		const item = items[0];
		const fieldName = objectFields[0].name;

		expect(item[fieldName]).toBe(entryText);
	}
);

test(
	'LPD-78504 Can map Clob type and view entries with Single Line',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
		site,
	}) => {
		// Create an object definition with a LongText (Clob) field

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['LongText'],
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

		// Create a new form mapped to the object

		await formBuilderPage.goToNew(site.friendlyUrlPath);

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		// Add a Text field (Single Line is the default)

		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

		// Map to the object field

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.objectFieldSelect.click();

		await page
			.getByRole('option', {name: fieldLabel})
			.dispatchEvent('click');

		await page.waitForTimeout(2000);

		// Save and publish the form

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL =
			await formBuilderPage.getFormSubmissionURL();

		// Navigate to the published form and submit an entry

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		const entryText =
			'By building a vibrant business, making technology useful, and investing in communities, we make it possible for people to reach their full potential to serve others.';

		await page.getByLabel('Text').fill(entryText);

		await page.getByRole('button', {name: 'Save'}).click();

		await expect(
			page.getByText('Your request completed successfully.')
		).toBeVisible();

		// Verify the entry was created in the object

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);

		const item = items[0];
		const fieldName = objectFields[0].name;

		expect(item[fieldName]).toBe(entryText);
	}
);

test(
	'LPD-78504 Cannot edit Picklist entries in Forms Sidebar',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
		site,
	}) => {
		// Create a picklist with entries

		const {listTypeDefinition} =
			await postListTypeDefinitionListTypeEntries({
				apiHelpers,
				listTypeEntriesLength: 2,
			});

		// Create an object definition with a Picklist field

		const objectFields = generateObjectFields({
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			objectFieldBusinessTypes: ['Picklist'],
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

		const picklistFieldLabel = objectFields[0].label!['en_US'];

		// Create a new form mapped to the object

		await formBuilderPage.goToNew(site.friendlyUrlPath);

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		// Add a Select from List field and map it to the picklist object field

		await formBuilderSidePanelPage.addFieldByDoubleClick(
			'Select from List'
		);

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.selectObjectField(picklistFieldLabel);

		// Go to Basic tab and verify Create List option is not visible

		await formBuilderSidePanelPage.clickBasicTab();

		await expect(page.getByLabel('Create List')).toBeHidden();
	}
);

test(
	'LPD-78504 Cannot select an unpublished Object in form settings',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formSettingsModalPage,
		page,
		site,
	}) => {
		// Create an unpublished (draft) object definition

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 2},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		// Create a new form

		await formBuilderPage.goToNew(site.friendlyUrlPath);

		await formBuilderPage.formSettingsButton.click();

		// Select Object storage type

		await formSettingsModalPage.selectStorageType('Object');

		// Click the Select Object dropdown and verify the unpublished object is not listed

		await formSettingsModalPage.objectSelect.click();

		await expect(
			formSettingsModalPage.getSelectOptionLocator(
				objectDefinition.label['en_US']
			)
		).toBeHidden();
	}
);

test(
	'LPD-78504 Can retrieve Data Providers on Select from List field',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		dataProviderPage,
		formBuilderFieldSettingsSidePanelPage,
		formBuilderPage,
		formBuilderSidePanelPage,
		formsPage,
		page,
		site,
		systemSettingsPage,
	}) => {
		test.slow();

		// Create an object definition with a Text field

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

		// Create an object entry

		const fieldName = objectFields[0].name!;
		const entryValue = 'Entry Test';

		await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName]: entryValue},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		// Enable local network data provider access

		await systemSettingsPage.goToSystemSetting(
			'Data Providers',
			'Data Providers'
		);

		await page.getByLabel('Access Local Network').check();

		await page
			.getByRole('button', {name: 'Save'})
			.or(page.getByRole('button', {name: 'Update'}))
			.click();

		await expect(
			page.getByText('Success:Your request')
		).toBeVisible();

		// Create a REST data provider pointing to the object's API

		await formsPage.goTo(site.friendlyUrlPath);

		await formsPage.dataProvidersTab.click();

		await dataProviderPage.addNewDataProviderLink.first().click();

		const dataProviderName = 'ObjectEntries' + getRandomInt();

		await dataProviderPage.nameInputField.fill(dataProviderName);

		await dataProviderPage.urlInputField.fill(
			`${liferayConfig.environment.baseUrl}/o/c/${objectDefinition.name.toLowerCase()}s`
		);

		await dataProviderPage.userNameInputField.fill('test@liferay.com');

		await dataProviderPage.passwordInputField.fill('test');

		await dataProviderPage.outputPathField.fill(
			`$.items..${fieldName}`
		);

		await dataProviderPage.selectOutputType('List');

		await dataProviderPage.outputLabelField.fill('Entry');

		await dataProviderPage.saveButton.click();

		await expect(
			page.getByText('Success:Your request')
		).toBeVisible();

		// Create a form with Select from List field configured with data provider

		await formBuilderPage.goToNew(site.friendlyUrlPath);

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderSidePanelPage.addFieldByDoubleClick(
			'Select from List'
		);

		await formBuilderSidePanelPage.label.fill(
			'Data Provider Select Field'
		);

		await formBuilderFieldSettingsSidePanelPage.selectCreateListSetting(
			'From Data Provider'
		);

		await expect(
			formBuilderFieldSettingsSidePanelPage.dataProviderSelect
		).toBeVisible();

		await formBuilderFieldSettingsSidePanelPage.selectDataProviderSetting(
			dataProviderName
		);

		await expect(
			formBuilderFieldSettingsSidePanelPage.outputParameterSelect
		).toBeVisible();

		await formBuilderFieldSettingsSidePanelPage.selectOutputParameterSetting(
			'Entry'
		);

		await page.waitForTimeout(2000);

		// Publish the form

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL =
			await formBuilderPage.getFormSubmissionURL();

		// Navigate to published form and verify data provider option is available

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		await page.getByLabel('Data Provider Select Field').click();

		await expect(
			page.getByRole('option', {name: entryValue})
		).toBeVisible();

		await page.getByRole('option', {name: entryValue}).click();

		await expect(
			page.getByLabel('Data Provider Select Field')
		).toContainText(entryValue);

		// Cleanup: disable local network access

		await systemSettingsPage.goToSystemSetting(
			'Data Providers',
			'Data Providers'
		);

		await page.getByLabel('Access Local Network').uncheck();

		await page
			.getByRole('button', {name: 'Save'})
			.or(page.getByRole('button', {name: 'Update'}))
			.click();
	}
);

test(
	'LPD-78504 Can retrieve Data Providers on Text field',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		dataProviderPage,
		formBuilderFieldSettingsSidePanelPage,
		formBuilderPage,
		formBuilderSidePanelPage,
		formsPage,
		page,
		site,
		systemSettingsPage,
	}) => {
		test.slow();

		// Create an object definition with a Text field

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

		// Create an object entry

		const fieldName = objectFields[0].name!;
		const entryValue = 'Entry Test';

		await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName]: entryValue},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		// Enable local network data provider access

		await systemSettingsPage.goToSystemSetting(
			'Data Providers',
			'Data Providers'
		);

		await page.getByLabel('Access Local Network').check();

		await page
			.getByRole('button', {name: 'Save'})
			.or(page.getByRole('button', {name: 'Update'}))
			.click();

		await expect(
			page.getByText('Success:Your request')
		).toBeVisible();

		// Create a REST data provider pointing to the object's API

		await formsPage.goTo(site.friendlyUrlPath);

		await formsPage.dataProvidersTab.click();

		await dataProviderPage.addNewDataProviderLink.first().click();

		const dataProviderName = 'ObjectEntries' + getRandomInt();

		await dataProviderPage.nameInputField.fill(dataProviderName);

		await dataProviderPage.urlInputField.fill(
			`${liferayConfig.environment.baseUrl}/o/c/${objectDefinition.name.toLowerCase()}s`
		);

		await dataProviderPage.userNameInputField.fill('test@liferay.com');

		await dataProviderPage.passwordInputField.fill('test');

		await dataProviderPage.outputPathField.fill(
			`$.items..${fieldName}`
		);

		await dataProviderPage.selectOutputType('List');

		await dataProviderPage.outputLabelField.fill('Entry');

		await dataProviderPage.saveButton.click();

		await expect(
			page.getByText('Success:Your request')
		).toBeVisible();

		// Create a form with a Text field configured with autocomplete

		await formBuilderPage.goToNew(site.friendlyUrlPath);

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

		// Navigate to Autocomplete tab and enable autocomplete

		await page.getByRole('tab', {name: 'Autocomplete'}).click();

		await page.getByRole('switch', {name: 'Autocomplete'}).click();

		// Select "From Data Provider" and configure

		await page.getByLabel('From Data Provider').click();

		await expect(
			formBuilderFieldSettingsSidePanelPage.dataProviderSelect
		).toBeVisible();

		await formBuilderFieldSettingsSidePanelPage.selectDataProviderSetting(
			dataProviderName
		);

		await expect(
			formBuilderFieldSettingsSidePanelPage.dataProviderSelect
		).toHaveText(dataProviderName);

		await expect(
			formBuilderFieldSettingsSidePanelPage.outputParameterSelect
		).toBeVisible();

		await formBuilderFieldSettingsSidePanelPage.selectOutputParameterSetting(
			'Entry'
		);

		await expect(
			formBuilderFieldSettingsSidePanelPage.outputParameterSelect
		).toHaveText('Entry');

		await page.waitForTimeout(2000);

		// Publish the form

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL =
			await formBuilderPage.getFormSubmissionURL();

		// Navigate to published form and submit an entry

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		await page.getByLabel('Text').fill('Entry');

		// Dismiss autocomplete dropdown before clicking Submit

		await page.getByLabel('Text').press('Escape');

		await page.getByRole('button', {name: 'Submit'}).click();

		await expect(
			page.getByText(
				'Your information was successfully received. Thank you for filling out the form.'
			)
		).toBeVisible();

		// Cleanup: disable local network access

		await systemSettingsPage.goToSystemSetting(
			'Data Providers',
			'Data Providers'
		);

		await page.getByLabel('Access Local Network').uncheck();

		await page
			.getByRole('button', {name: 'Save'})
			.or(page.getByRole('button', {name: 'Update'}))
			.click();
	}
);

test(
	'LPD-78504 Can send form email when form is related with Object',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Original Poshi test (CanSendFormEmailWhenItIsRelatedWithObject):
		// 1. Creates an object with a Text field and publishes it
		// 2. Creates a form mapped to the object's storage type with a Text field
		// 3. Configures email notifications (from: test@liferay.com, to: formreviewer@liferay.com,
		//    subject: "Form Subject", sender: "Sender Name")
		// 4. Publishes the form
		// 5. Creates a widget page with a Form widget displaying the form
		// 6. Submits an entry ("Entry test") through the widget page
		// 7. Verifies the email was sent via MockMock SMTP server
		//
		// Requirements not yet available in this test project:
		// - MockMock SMTP server (requires env/set_up.sh, see users-admin-web/email/ for reference)
		// - Form email notification configuration (no page object exists)
		// - Widget page creation with Form widget portlet configuration

		test.skip(
			true,
			'Requires MockMock SMTP server infrastructure and form email notification page objects'
		);
	}
);

test(
	'LPD-78504 Can submit form entries using object storage type',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
		site,
	}) => {
		// Create an object definition with a Text field

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

		const fieldLabel = objectFields[0].label!['en_US'];

		// Create a new form mapped to the object

		await formBuilderPage.goToNew(site.friendlyUrlPath);

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		// Add a Text field and map it to the object field

		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.objectFieldSelect.click();

		await page
			.getByRole('option', {name: fieldLabel})
			.dispatchEvent('click');

		await page.waitForTimeout(2000);

		// Save and publish the form

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL =
			await formBuilderPage.getFormSubmissionURL();

		// Navigate to the published form and submit an entry

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		const textValue = getRandomString();

		await page.getByLabel('Text').fill(textValue);

		await page.getByRole('button', {name: 'Save'}).click();

		await expect(
			page.getByText('Your request completed successfully.')
		).toBeVisible();

		// Verify the entry was created in the object

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);

		const item = items[0];
		const fieldName = objectFields[0].name;

		expect(item[fieldName]).toBe(textValue);
	}
);

test(
	'LPD-78504 Select Object field is required when using Object storage type',
	{tag: '@LPD-78504'},
	async ({
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
		site,
	}) => {
		// Create a new form

		await formBuilderPage.goToNew(site.friendlyUrlPath);

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		// Add a Text field

		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

		// Open form settings and select Object storage type without selecting an object

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.clickDoneButton();

		// Try to publish the form

		await formBuilderPage.publishButton.click();

		// Verify error message is displayed

		await expect(
			page.getByText(
				'You must define an object for the selected storage type.'
			)
		).toBeVisible();
	}
);
