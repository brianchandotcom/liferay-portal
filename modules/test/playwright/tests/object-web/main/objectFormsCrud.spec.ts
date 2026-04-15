/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectDefinition} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {formsPagesTest} from '../../../fixtures/formsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {liferayConfig} from '../../../liferay.config';
import {getRandomInt} from '../../../utils/getRandomInt';
import {waitForAlert} from '../../../utils/waitForAlert';
import {deleteItems} from '../../dynamic-data-mapping-form-web/main/utils/deleteItems';
import {generateObjectFields} from '../utils/generateObjectFields';
import {postListTypeDefinitionListTypeEntries} from '../utils/postListTypeDefinitionListTypeEntries';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	formsPagesTest,
	loginTest(),
	objectPagesTest
);

test(
	'allow multiple selection option is not available for Select From List field when form is mapped to an object',
	{tag: '@LPS-139472'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
	}) => {
		const {listTypeDefinition} =
			await postListTypeDefinitionListTypeEntries({
				apiHelpers,
				listTypeEntriesLength: 2,
			});

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

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

		await formBuilderPage.goToNew();

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		await formBuilderSidePanelPage.addFieldByDoubleClick(
			'Select from List'
		);

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.selectObjectField(picklistFieldLabel);

		await expect(
			page.getByText('Allow Multiple Selections')
		).not.toBeVisible();
	}
);

test(
	'can delete a form mapped to an object after adding entry',
	{tag: '@LPS-139464'},
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

		const formTitle = 'Form' + getRandomInt();

		await formBuilderPage.goToNew();

		await expect(formBuilderPage.newFormHeading).toBeVisible();

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

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL = await formBuilderPage.getFormSubmissionURL();

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		await page.getByLabel('Text').fill('Entry Test');

		await page.getByRole('button', {name: 'Save'}).click();

		await expect(
			page.getByText('Your request completed successfully.')
		).toBeVisible();

		await formsPage.goTo();

		await deleteItems(formsPage);
		
		await expect(
			page.getByRole('link', {exact: true, name: formTitle})
		).not.toBeVisible();		
	}
);

test(
	'can map object field of RichText type to form and add entries',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
	}) => {
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

		await formBuilderPage.goToNew();

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		await formBuilderSidePanelPage.addFieldByDoubleClick('Rich Text');

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.selectObjectField(fieldLabel);

		await page.waitForTimeout(1000);

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL = await formBuilderPage.getFormSubmissionURL();

		await page.goto(formSubmissionURL, {waitUntil: 'load'});

		const richTextContent =
			'By building a vibrant business, making technology useful, and investing in communities, we make it possible for people to reach their full potential to serve others.';

		const richTextEditor = page.getByRole('paragraph');

		await richTextEditor.waitFor();

		await richTextEditor.click();

		await page.keyboard.type(richTextContent);

		await page.getByRole('button', {name: 'Save'}).click();

		await expect(
			page.getByText('Your request completed successfully.')
		).toBeVisible();

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);

		const fieldName = objectFields[0].name;

		expect(items[0][fieldName]).toContain(richTextContent);
	}
);

test('can map object field of LongText type to form and add entries', async ({
	apiHelpers,
	formBuilderPage,
	formBuilderSidePanelPage,
	formSettingsModalPage,
	page,
}) => {
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

	await formBuilderPage.goToNew();

	await expect(formBuilderPage.newFormHeading).toBeVisible();

	await formBuilderPage.fillFormTitle('Form' + getRandomInt());

	await formBuilderPage.formSettingsButton.click();

	await formSettingsModalPage.selectStorageType('Object');

	await formSettingsModalPage.selectObject(objectDefinition.label['en_US']);

	await formSettingsModalPage.clickDoneButton();

	await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

	await formBuilderSidePanelPage.clickAdvancedTab();

	await formBuilderSidePanelPage.selectObjectField(fieldLabel);

	await page.waitForTimeout(1000);

	await formBuilderPage.clickPublishFormButton();

	const formSubmissionURL = await formBuilderPage.getFormSubmissionURL();

	await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

	const entryText =
		'By building a vibrant business, making technology useful, and investing in communities, we make it possible for people to reach their full potential to serve others.';

	await page.getByRole('textbox').fill(entryText);

	await page.getByRole('button', {name: 'Save'}).click();

	await expect(
		page.getByText('Your request completed successfully.')
	).toBeVisible();

	const {items} =
		await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

	expect(items).toHaveLength(1);

	const fieldName = objectFields[0].name;

	expect(items[0][fieldName]).toBe(entryText);
});

test(
	'cannot edit picklist entries in forms sidebar',
	{tag: '@LPS-138495'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
	}) => {
		const {listTypeDefinition} =
			await postListTypeDefinitionListTypeEntries({
				apiHelpers,
				listTypeEntriesLength: 2,
			});
		
		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

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

		await formBuilderPage.goToNew();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		await formBuilderSidePanelPage.addFieldByDoubleClick(
			'Select from List'
		);

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.selectObjectField(picklistFieldLabel);

		await formBuilderSidePanelPage.clickBasicTab();

		await expect(page.getByText('Create List')).not.toBeVisible();
	}
);

test(
	'cannot select an unpublished Object in form settings',
	{tag: '@LPS-137316'},
	async ({apiHelpers, formBuilderPage, formSettingsModalPage}) => {
		const draftObjectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 2},
			});

		apiHelpers.data.push({
			id: draftObjectDefinition.id,
			type: 'objectDefinition',
		});

		await formBuilderPage.goToNew();

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.objectSelect.click();

		await expect(
			formSettingsModalPage.getSelectOptionLocator(
				draftObjectDefinition.label['en_US']
			)
		).not.toBeVisible();
	}
);

test(
	'can retrieve objects data from Data Providers and use on Select from List and Text fields',
	{tag: '@LPS-135429'},
	async ({
		apiHelpers,
		dataProviderPage,
		formBuilderFieldSettingsSidePanelPage,
		formBuilderPage,
		formBuilderSidePanelPage,
		formsPage,
		page,
		systemSettingsPage,
	}) => {
		try {
			let objectDefinition: ObjectDefinition;

			await test.step('create an object definition with a Text field', async () => {
				objectDefinition =
					await apiHelpers.objectAdmin.postRandomObjectDefinition({
						status: {code: 0},
					});

				apiHelpers.data.push({
					id: objectDefinition.id,
					type: 'objectDefinition',
				});
			});

			const objectEntry = 'Entry' + getRandomInt();

			await test.step('create an object entry', async () => {
				await apiHelpers.objectEntry.postObjectEntry(
					{textField: objectEntry},
					'c/' + objectDefinition.name.toLowerCase() + 's'
				);
			});

			await test.step('enable local network data provider access', async () => {
				await systemSettingsPage.goToSystemSetting(
					'Data Providers',
					'Data Providers'
				);

				await page.getByLabel('Access Local Network').check();

				await page
					.getByRole('button', {name: 'Save'})
					.or(page.getByRole('button', {name: 'Update'}))
					.click();

				await waitForAlert(page);

				await expect(
					page.getByLabel('Access Local Network')
				).toBeChecked();
			});

			const dataProviderName = 'ObjectEntries' + getRandomInt();

			await test.step('create a data provider pointing to the object definition API', async () => {
				await formsPage.goTo();

				await formsPage.dataProvidersTab.click();

				await dataProviderPage.addNewDataProviderLink.first().click();

				await dataProviderPage.nameInputField.fill(dataProviderName);

				await dataProviderPage.urlInputField.fill(
					`${liferayConfig.environment.baseUrl}/o/c/${objectDefinition.name.toLowerCase()}s`
				);

				await dataProviderPage.userNameInputField.fill(
					'test@liferay.com'
				);

				await dataProviderPage.passwordInputField.fill('test');

				await dataProviderPage.outputPathField.fill(
					'$.items..textField'
				);

				await dataProviderPage.selectOutputType('List');

				await dataProviderPage.outputLabelField.fill('Entry');

				await dataProviderPage.saveButton.click();

				await expect(
					page.getByText('Success:Your request')
				).toBeVisible();
			});

			await test.step('create a form with Select from List field configured with data provider', async () => {
				await formBuilderPage.goToNew();

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

				await page.waitForTimeout(1000);
			});

			await test.step('add a text field configured with autocomplete from Data Provider', async () => {
				await formBuilderSidePanelPage.backButton.click();

				await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

				await page.getByRole('tab', {name: 'Autocomplete'}).click();

				await page.getByRole('switch', {name: 'Autocomplete'}).click();

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

				await page.waitForTimeout(1000);
			});

			await test.step('Publish the form, navigate to it and assert entry value is available as an option', async () => {
				await formBuilderPage.clickPublishFormButton();

				const formSubmissionURL =
					await formBuilderPage.getFormSubmissionURL();

				await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

				await page.getByLabel('Data Provider Select Field').click();

				await expect(
					page.getByRole('option', {name: objectEntry})
				).toBeVisible();

				await page.getByRole('option', {name: objectEntry}).click();

				await expect(
					page.getByLabel('Data Provider Select Field')
				).toContainText(objectEntry);

				await page.getByLabel('Text').fill('Entry');

				await expect(
					page.getByRole('menuitem', {name: objectEntry})
				).toBeVisible();
			});
		}
		finally {
			await formsPage.goTo();

			await formsPage.dataProvidersTab.click();

			await deleteItems(formsPage);

			await systemSettingsPage.goToSystemSetting(
				'Data Providers',
				'Data Providers'
			);

			await page.getByLabel('Access Local Network').uncheck();

			await page
				.getByRole('button', {name: 'Save'})
				.or(page.getByRole('button', {name: 'Update'}))
				.click();

			await waitForAlert(page);
		}
	}
);

test.skip('can send form email when form is related with Object', async () => {

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

});

test(
	'mapping form to a object definition and fields to an object field is required when using Object as storage type',
	{tag: '@LPD-78504'},
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

		await formBuilderPage.goToNew();

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.clickDoneButton();

		await formBuilderPage.publishButton.click();

		await waitForAlert(
			page,
			'You must define an object for the selected storage type.',
			{type: 'danger'}
		);

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		await formBuilderPage.publishButton.click();

		await expect(
			page.getByRole('heading', {name: 'Unmapped Object Required'})
		).toBeVisible();

		await expect(
			page.getByText(
				'All fields in this form must be mapped to a field in the object.'
			)
		).toBeVisible();
	}
);
