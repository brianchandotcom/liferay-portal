/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinitionApi,
	ObjectField,
	ObjectValidationRule,
	ObjectValidationRuleApi,
} from '@liferay/object-admin-rest-client-js';
import {Page, expect, mergeTests} from '@playwright/test';
import path from 'path';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {displayPageTemplatesPagesTest} from '../../fixtures/displayPageTemplatesPagesTest';
import {documentLibraryPagesTest} from '../../fixtures/documentLibraryPages.fixtures';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pageManagementSiteTest} from '../../fixtures/pageManagementSiteTest';
import {PageEditorPage} from '../../pages/layout-content-page-editor-web/PageEditorPage';
import {clickAndExpectToBeHidden} from '../../utils/clickAndExpectToBeHidden';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import fillAndClickOutside from '../../utils/fillAndClickOutside';
import getRandomString from '../../utils/getRandomString';
import {waitForAlert} from '../../utils/waitForAlert';
import {getObjectERC} from '../setup/page-management-site/utils/getObjectERC';
import {goToObjectEntity} from '../setup/page-management-site/utils/goToObjectEntity';
import getContainerDefinition from './utils/getContainerDefinition';
import getFormContainerDefinition from './utils/getFormContainerDefinition';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';
import getWidgetDefinition from './utils/getWidgetDefinition';

const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	displayPageTemplatesPagesTest,
	documentLibraryPagesTest,
	featureFlagsTest({
		'LPD-10727': true,
		'LPD-37927': true,
		'LPS-178052': true,
	}),
	loginTest(),
	objectPagesTest,
	pageEditorPagesTest,
	pageManagementSiteTest
);

test.describe('Form Configuration', () => {
	test(
		'Can add custom translations for success message',
		{
			tag: ['@LPS-155529', '@LPS-188036'],
		},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Create a page with a Form fragment and a widget

			const formId = getRandomString();

			const formDefinition = getFormContainerDefinition({
				id: formId,
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode and change form configuration

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			await pageEditorPage.mapFormFragment(formId, 'Lemon', [
				'Lemon Size',
				'Lemon Basket to Lemons',
			]);

			await pageEditorPage.selectFragment(formId);

			await page
				.getByLabel('Success Action', {exact: true})
				.selectOption({label: 'Show Embedded Message'});

			await pageEditorPage.switchLanguage('es-ES');

			await page
				.getByLabel('Embedded Message', {exact: true})
				.fill('Estamos muy agradecidos de recibir su formulario.');

			// Preview message

			await page
				.getByLabel('Preview Success Message', {exact: true})
				.click();

			await expect(
				page.getByText(
					'Estamos muy agradecidos de recibir su formulario.'
				)
			).toBeVisible();

			await pageEditorPage.publishPage();

			// Go to view mode in spanish language

			await page.goto(
				`/es/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			// Submit form

			await page.getByRole('button', {name: 'Submit'}).click();

			// Assert spanish success message

			await page
				.getByText('Estamos muy agradecidos de recibir su formulario.')
				.waitFor();

			// Go to view mode in english language

			await page.goto(
				`/en/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			// Submit form

			await page.getByRole('button', {name: 'Submit'}).click();

			// Assert english success message

			await page
				.getByText(
					'Thank you. Your information was successfully received.'
				)
				.waitFor();
		}
	);

	test(
		'Show success message only one time',
		{
			tag: '@LPD-37435',
		},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Create a page with a Form fragment and a widget

			const formId = getRandomString();

			const formDefinition = getFormContainerDefinition({
				id: formId,
			});

			const widgetId = getRandomString();

			const widgetDefinition = getWidgetDefinition({
				id: widgetId,
				widgetName:
					'com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet',
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([
					formDefinition,
					widgetDefinition,
				]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode and change form configuration

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			await pageEditorPage.mapFormFragment(formId, 'Lemon', [
				'Lemon Size',
				'Lemon Basket to Lemons',
			]);

			await pageEditorPage.selectFragment(formId);

			await page
				.getByLabel('Success Action', {exact: true})
				.selectOption({label: 'Stay in Page'});

			await page
				.getByLabel('Show Notification After Submit', {exact: true})
				.check();

			await page
				.getByLabel('Success Notification Text', {exact: true})
				.fill('Request received correctly');

			// add a Tabs fragment that includes drop-zone

			await pageEditorPage.addFragment('Basic Components', 'Tabs');

			await pageEditorPage.publishPage();

			// Go to view mode and check picklist values

			await page.goto(
				`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			// Submit form

			await page.getByRole('button', {name: 'Submit'}).click();

			// Wait for the first alert

			await page.getByText('Request received correctly').waitFor();

			// Verify that the first alert disappears without any more alerts being displayed

			let moreAlertsAppear = false;
			let firstAlertDisappears = false;

			await expect(async () => {
				const alerts = await page
					.getByText('Request received correctly')
					.all();

				if (alerts.length > 1) {
					moreAlertsAppear = true;
				}
				else if (!alerts.length) {
					firstAlertDisappears = true;
				}

				expect(firstAlertDisappears).toBe(true);
				expect(moreAlertsAppear).toBe(false);
			}).toPass();
		}
	);
});

test.describe('Captcha Fragment', () => {
	test(
		'The user could see an error message when submit a form with wrong captcha verification code',
		{
			tag: ['@LPS-151402', '@LPS-155168'],
		},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Create a page with a form fragment with a captcha fragment

			const objectDefinitionApiClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {className: objectDefinitionClassName} = (
				await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
					getObjectERC('Lemon')
				)
			).body;

			const captchaDefinition = getFragmentDefinition({
				id: getRandomString(),
				key: 'INPUTS-captcha',
			});

			const submitFragmentDefinition = getFragmentDefinition({
				id: getRandomString(),
				key: 'INPUTS-submit-button',
			});

			const formDefinition = getFormContainerDefinition({
				id: getRandomString(),
				objectDefinitionClassName,
				pageElements: [captchaDefinition, submitFragmentDefinition],
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode and assert captcha is disabled

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			await expect(page.locator('.form-input-captcha')).toHaveAttribute(
				'disabled'
			);

			// Go to view mode and assert error message with wrong captcha verification code when submit the form

			await page.goto(
				`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			await page.getByText('Submit', {exact: true}).click();

			await expect(
				page.getByText('CAPTCHA verification failed. Please try again.')
			).toBeVisible();
		}
	);
});

test.describe('Checkbox Fragment', () => {
	test(
		'The page designer can configure checkbox fragment',
		{
			tag: '@LPS-151157',
		},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Create a page with a form fragment with a checkbox fragment

			const objectDefinitionApiClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {className: objectDefinitionClassName} = (
				await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
					getObjectERC('All Fields')
				)
			).body;

			const checkboxId = getRandomString();

			const checkboxDefinition = getFragmentDefinition({
				fragmentConfig: {
					inputFieldId: 'ObjectField_boolean',
				},
				id: checkboxId,
				key: 'INPUTS-checkbox',
			});

			const formDefinition = getFormContainerDefinition({
				id: getRandomString(),
				objectDefinitionClassName,
				pageElements: [checkboxDefinition],
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			// Change label

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Label',
				fragmentId: checkboxId,
				tab: 'General',
				value: 'Are you a fun of Stephen Curry?',
			});

			const checkboxInput = page.locator('.forms-checkbox');

			await expect(
				checkboxInput.getByText('Are you a fun of Stephen Curry?')
			).not.toHaveClass(/sr-only/);

			// Hide label

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Show Label',
				fragmentId: checkboxId,
				tab: 'General',
				value: false,
			});

			await expect(
				checkboxInput.getByText('Are you a fun of Stephen Curry?')
			).toHaveClass(/sr-only/);

			// Show help text

			await expect(checkboxInput).not.toContainText(
				/Add your help text here./
			);

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Show Help Text',
				fragmentId: checkboxId,
				tab: 'General',
				value: true,
			});

			await expect(checkboxInput).toContainText(
				/Add your help text here./
			);
		}
	);

	test(
		'User should see error message below checkbox fragment',
		{
			tag: '@LPS-182728',
		},
		async ({apiHelpers, page, pageManagementSite}) => {

			// Adds checkbox validation

			const objectValidationRuleApiClient =
				await apiHelpers.buildRestClient(ObjectValidationRuleApi);

			const {body: objectValidationRule} =
				await objectValidationRuleApiClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
					getObjectERC('All Fields'),
					{
						active: true,
						engine: 'ddm',
						engineLabel: 'Expression Builder',
						errorLabel: {
							en_US: 'Please accept the terms of use and Privacy Policy.',
						},
						name: {
							en_US: 'Checkbox Validation',
						},
						objectValidationRuleSettings: [
							{
								name: 'outputObjectFieldExternalReferenceCode',
								value: 'boolean-erc',
							} as any,
						],
						outputType:
							ObjectValidationRule.OutputTypeEnum
								.PartialValidation,
						script: 'boolean == true',
						system: false,
					}
				);

			// Create a page with a form fragment with a checkbox fragment

			const objectDefinitionApiClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {className: objectDefinitionClassName} = (
				await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
					getObjectERC('All Fields')
				)
			).body;

			const checkboxId = getRandomString();

			const checkboxDefinition = getFragmentDefinition({
				fragmentConfig: {
					inputFieldId: 'ObjectField_boolean',
				},
				id: checkboxId,
				key: 'INPUTS-checkbox',
			});

			const submitFragmentDefinition = getFragmentDefinition({
				id: getRandomString(),
				key: 'INPUTS-submit-button',
			});

			const formDefinition = getFormContainerDefinition({
				id: getRandomString(),
				objectDefinitionClassName,
				pageElements: [checkboxDefinition, submitFragmentDefinition],
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to view mode

			await page.goto(
				`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			// Assert error message

			await page.getByText('Submit', {exact: true}).click();

			await expect(page.locator('.forms-checkbox')).toContainText(
				'Please accept the terms of use and Privacy Policy.'
			);

			// Delete validation

			await objectValidationRuleApiClient.deleteObjectValidationRule(
				objectValidationRule.id
			);
		}
	);
});

test.describe('Date Fragment', () => {
	test(
		'The page designer could map date field to date fragment',
		{
			tag: ['@LPS-151158', '@LPS-155502'],
		},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Create a page with a form fragment with a date fragment

			const objectDefinitionApiClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {className: objectDefinitionClassName} = (
				await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
					getObjectERC('All Fields')
				)
			).body;

			const dateId = getRandomString();

			const dateDefinition = getFragmentDefinition({
				fragmentConfig: {
					inputFieldId: 'ObjectField_date',
				},
				id: dateId,
				key: 'INPUTS-date-input',
			});

			const formDefinition = getFormContainerDefinition({
				id: getRandomString(),
				objectDefinitionClassName,
				pageElements: [dateDefinition],
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			// Change label

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Label',
				fragmentId: dateId,
				tab: 'General',
				value: 'Expiration Date',
			});

			const dateInput = page.locator('.date-input');

			await expect(
				dateInput.getByText('Expiration Date')
			).not.toHaveClass('sr-only');

			// Hide label

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Show Label',
				fragmentId: dateId,
				tab: 'General',
				value: false,
			});

			await expect(dateInput.getByText('Expiration Date')).toHaveClass(
				'sr-only'
			);

			// Show help text

			await expect(dateInput).not.toContainText(
				/Add your help text here./
			);

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Show Help Text',
				fragmentId: dateId,
				tab: 'General',
				value: true,
			});

			await expect(dateInput).toContainText(/Add your help text here./);
		}
	);

	test(
		'User should see error message below date fragment',
		{
			tag: '@LPS-182728',
		},
		async ({apiHelpers, page, pageManagementSite}) => {

			// Adds date validation

			const objectValidationRuleApiClient =
				await apiHelpers.buildRestClient(ObjectValidationRuleApi);

			const {body: objectValidationRule} =
				await objectValidationRuleApiClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
					getObjectERC('All Fields'),
					{
						active: true,
						engine: 'ddm',
						engineLabel: 'Expression Builder',
						errorLabel: {
							en_US: 'Please enter a valid date.',
						},
						name: {
							en_US: 'Date Validation',
						},
						objectValidationRuleSettings: [
							{
								name: 'outputObjectFieldExternalReferenceCode',
								value: 'date-erc',
							} as any,
						],
						outputType:
							ObjectValidationRule.OutputTypeEnum
								.PartialValidation,
						script: "futureDates(date, '2022-06-01')",
						system: false,
					}
				);

			// Create a page with a form fragment with a date fragment

			const objectDefinitionApiClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {className: objectDefinitionClassName} = (
				await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
					getObjectERC('All Fields')
				)
			).body;

			const dateId = getRandomString();

			const dateDefinition = getFragmentDefinition({
				fragmentConfig: {
					inputFieldId: 'ObjectField_date',
				},
				id: dateId,
				key: 'INPUTS-date-input',
			});

			const submitFragmentDefinition = getFragmentDefinition({
				id: getRandomString(),
				key: 'INPUTS-submit-button',
			});

			const formDefinition = getFormContainerDefinition({
				id: getRandomString(),
				objectDefinitionClassName,
				pageElements: [dateDefinition, submitFragmentDefinition],
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to view mode

			await page.goto(
				`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			// Edit date

			await page.locator('input[name="date"]').click();

			await page.keyboard.type('07/11/2020');

			await page.locator('body').click();

			await page.getByText('Submit', {exact: true}).click();

			// Assert error message

			await expect(page.locator('.date-input')).toContainText(
				'Please enter a valid date.'
			);

			// Delete validation

			const objectvalidationRuleApiClient =
				await apiHelpers.buildRestClient(ObjectValidationRuleApi);

			await objectvalidationRuleApiClient.deleteObjectValidationRule(
				objectValidationRule.id
			);
		}
	);
});

test.describe('Date and Time Fragment', () => {
	test(
		'The page designer could map date and time field to date and time fragment',
		{
			tag: '@LPS-191312',
		},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Create a page with a form fragment with a date and time fragment

			const objectDefinitionApiClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {className: objectDefinitionClassName} = (
				await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
					getObjectERC('All Fields')
				)
			).body;

			const dateId = getRandomString();

			const dateDefinition = getFragmentDefinition({
				fragmentConfig: {
					inputFieldId: 'ObjectField_date',
				},
				id: dateId,
				key: 'INPUTS-date-input',
			});

			const dateTimeId = getRandomString();

			const dateTimeDefinition = getFragmentDefinition({
				fragmentConfig: {
					inputFieldId: 'ObjectField_dateAndTime',
				},
				id: dateTimeId,
				key: 'INPUTS-date-time-input',
			});

			const textDefinition = getFragmentDefinition({
				fragmentConfig: {
					inputFieldId: 'ObjectField_text',
				},
				id: getRandomString(),
				key: 'INPUTS-text-input',
			});

			const submitFragmentDefinition = getFragmentDefinition({
				id: getRandomString(),
				key: 'INPUTS-submit-button',
			});

			const formDefinition = getFormContainerDefinition({
				id: getRandomString(),
				objectDefinitionClassName,
				pageElements: [
					dateDefinition,
					dateTimeDefinition,
					textDefinition,
					submitFragmentDefinition,
				],
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode and change label

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Label',
				fragmentId: dateTimeId,
				tab: 'General',
				value: 'Clock',
			});

			await pageEditorPage.publishPage();

			// Go to view mode

			await page.goto(
				`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			// Edit date

			await page.locator('input[name="date"]').click();

			await page.keyboard.type('01/07/2023');

			// Edit date and time

			await page.locator('input[name="dateAndTime"]').click();

			await page.keyboard.type('10/10/2022');
			await page.keyboard.press('ArrowRight');
			await page.keyboard.type('10:10');
			await page.keyboard.press('ArrowRight');
			await page.keyboard.type('AM');

			await fillAndClickOutside(
				page,
				page.getByLabel('Text'),
				'Date And Time'
			);

			await page.getByText('Submit', {exact: true}).click();

			// Assert success message

			await expect(
				page.getByText(
					'Thank you. Your information was successfully received.'
				)
			).toBeVisible();

			// Go to custom object admin

			await goToObjectEntity({
				entityName: 'All Fields',
				page,
			});

			// Check the date and time of the object entry

			const row = page.locator('.dnd-tbody .dnd-tr').first();

			await expect(row).toContainText('Oct 10, 2022, 10:10 AM');
		}
	);
});

test.describe('File Upload Fragment', () => {
	test(
		"Cannot clear object entry's mandatory attached file via associated display page",
		{
			tag: '@LPS-191357',
		},
		async ({
			apiHelpers,
			displayPageTemplatesPage,
			page,
			pageEditorPage,
			pageManagementSite,
		}) => {

			// Create a Display page for the all fields object

			await displayPageTemplatesPage.goto(
				pageManagementSite.friendlyUrlPath
			);

			const displayPageTemplateName = getRandomString();

			await displayPageTemplatesPage.createTemplate({
				contentType: 'All Fields',
				name: displayPageTemplateName,
			});

			await displayPageTemplatesPage.editTemplate(
				displayPageTemplateName
			);

			// Add a Form Container and map it to file upload field

			await pageEditorPage.addFragment(
				'Form Components',
				'Form Container'
			);

			const fragmentId =
				await pageEditorPage.getFragmentId('Form Container');

			await pageEditorPage.mapFormFragment(
				fragmentId,
				'All Fields (Default)',
				['Computer File']
			);

			// Mark upload file as required

			const dptFileUploadId =
				await pageEditorPage.getFragmentId('File Upload');

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Mark as Required',
				fragmentId: dptFileUploadId,
				tab: 'General',
				value: true,
			});

			await displayPageTemplatesPage.publishTemplate();

			// Mark display page as default

			await displayPageTemplatesPage.goto(
				pageManagementSite.friendlyUrlPath
			);

			await displayPageTemplatesPage.markAsDefault(
				displayPageTemplateName
			);

			// Create a page with a form fragment with a file upload fragment

			const objectDefinitionApiClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {className: objectDefinitionClassName} = (
				await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
					getObjectERC('All Fields')
				)
			).body;

			const fileUploadId = getRandomString();

			const fileUploadDefinition = getFragmentDefinition({
				fragmentConfig: {
					inputFieldId: 'ObjectField_fileUpload',
				},
				id: fileUploadId,
				key: 'INPUTS-file-upload',
			});

			const submitFragmentDefinition = getFragmentDefinition({
				id: getRandomString(),
				key: 'INPUTS-submit-button',
			});

			const formId = getRandomString();

			const formDefinition = getFormContainerDefinition({
				id: formId,
				objectDefinitionClassName,
				pageElements: [fileUploadDefinition, submitFragmentDefinition],
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			// Mark upload file as required

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Mark as Required',
				fragmentId: fileUploadId,
				tab: 'General',
				value: true,
			});

			// Change redirect to display page after submit

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Success Action',
				fragmentId: formId,
				tab: 'General',
				value: 'Go to Entry Display Page',
			});

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Display Page',
				fragmentId: formId,
				tab: 'General',
				value: 'Default',
			});

			await pageEditorPage.publishPage();

			// Go to view mode

			await page.goto(
				`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			// Select file from computer

			const fileChooserPromise = page.waitForEvent('filechooser');

			const fileUploadInput = page.locator('.file-upload');

			await fileUploadInput
				.getByText('Select File', {exact: true})
				.click();

			const fileChooser = await fileChooserPromise;

			await fileChooser.setFiles(
				path.join(__dirname, '/dependencies/file_upload_image_1.jpg')
			);

			await expect(
				fileUploadInput.getByText('file_upload_image_1')
			).toBeVisible();

			// Submit form

			await page.getByRole('button', {name: 'Submit'}).click();

			// Assert form is submitted and user is redirected to display page

			await expect(page.getByText('Computer File')).toBeVisible();

			await expect(
				fileUploadInput.getByText('file_upload_image_1')
			).toBeVisible();

			// Assert form is not submitted if mandatory field is cleared

			await page.locator('[id*="file-upload-remove-button"]').click();

			await page.getByRole('button', {name: 'Submit'}).click();

			await expect(page.getByText('Computer File')).toBeVisible();

			await expect(
				page.getByText(
					'Thank you. Your information was successfully received.'
				)
			).not.toBeVisible();
		}
	);

	test(
		'Configure fragment mapped to File Upload field',
		{
			tag: '@LPS-157806',
		},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Create a page with a form fragment with a file upload fragment

			const objectDefinitionApiClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {className: objectDefinitionClassName} = (
				await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
					getObjectERC('All Fields')
				)
			).body;

			const fileUploadId = getRandomString();

			const fileUploadDefinition = getFragmentDefinition({
				fragmentConfig: {
					inputFieldId: 'ObjectField_fileUpload',
				},
				id: fileUploadId,
				key: 'INPUTS-file-upload',
			});

			const submitFragmentDefinition = getFragmentDefinition({
				id: getRandomString(),
				key: 'INPUTS-submit-button',
			});

			const formDefinition = getFormContainerDefinition({
				id: getRandomString(),
				objectDefinitionClassName,
				pageElements: [fileUploadDefinition, submitFragmentDefinition],
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			// Assert default configuration values

			await pageEditorPage.selectFragment(fileUploadId);

			await expect(
				page.getByLabel('Show Label', {exact: true})
			).toBeChecked();

			await expect(
				page.getByLabel('Show Help Text', {exact: true})
			).not.toBeChecked();

			await expect(
				page.getByLabel('Help Text', {exact: true})
			).toHaveValue('Add your help text here.');

			await expect(
				page.getByLabel('Show Supported File Info', {exact: true})
			).toBeChecked();

			const fileUploadInput = page.locator('.file-upload');

			await expect(
				fileUploadInput.getByText(
					'Upload a .jpeg,.jpg,.pdf,.png no larger than 2 MB.',
					{exact: true}
				)
			).toBeVisible();

			// Change button text

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Button Text',
				fragmentId: fileUploadId,
				tab: 'General',
				value: 'Upload',
			});

			await expect(
				fileUploadInput.getByText('Upload', {exact: true})
			).toBeVisible();

			await pageEditorPage.publishPage();

			// Go to view mode

			await page.goto(
				`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			await expect(
				fileUploadInput.getByRole('button', {name: 'Upload'})
			).toBeVisible();
		}
	);

	test(
		'Upload file from computer',
		{
			tag: '@LPS-155170',
		},
		async ({apiHelpers, documentLibraryPage, page, pageManagementSite}) => {

			// Create a page with a form fragment with a file upload fragment

			const objectDefinitionApiClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {className: objectDefinitionClassName} = (
				await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
					getObjectERC('All Fields')
				)
			).body;

			const fileUploadId = getRandomString();

			const fileUploadDefinition = getFragmentDefinition({
				fragmentConfig: {
					inputFieldId: 'ObjectField_fileUpload',
				},
				id: fileUploadId,
				key: 'INPUTS-file-upload',
			});

			const submitFragmentDefinition = getFragmentDefinition({
				id: getRandomString(),
				key: 'INPUTS-submit-button',
			});

			const formDefinition = getFormContainerDefinition({
				id: getRandomString(),
				objectDefinitionClassName,
				pageElements: [fileUploadDefinition, submitFragmentDefinition],
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to view mode

			await page.goto(
				`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			// Select file from computer

			const fileChooserPromise = page.waitForEvent('filechooser');

			const fileUploadInput = page.locator('.file-upload');

			await fileUploadInput
				.getByText('Select File', {exact: true})
				.click();

			const fileChooser = await fileChooserPromise;

			await fileChooser.setFiles(
				path.join(__dirname, '/dependencies/file_upload_image_2.jpg')
			);

			await expect(
				fileUploadInput.getByText('file_upload_image_2')
			).toBeVisible();

			// Submit form

			await page.getByRole('button', {name: 'Submit'}).click();

			// Assert document is added to document library

			await documentLibraryPage.goto(pageManagementSite.friendlyUrlPath);

			await page.getByRole('link', {name: 'FileUpload'}).click();

			await expect(
				page.getByRole('link', {name: 'file_upload_image_2'})
			).toBeVisible();
		}
	);

	test(
		'Upload file from document library',
		{
			tag: '@LPS-194129',
		},
		async ({apiHelpers, page, pageManagementSite}) => {

			// Create a page with a form fragment with a file upload fragment

			const objectDefinitionApiClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {className: objectDefinitionClassName} = (
				await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
					getObjectERC('All Fields')
				)
			).body;

			const fileUploadId = getRandomString();

			const fileUploadDefinition = getFragmentDefinition({
				fragmentConfig: {
					inputFieldId: 'ObjectField_dlFileUpload',
				},
				id: fileUploadId,
				key: 'INPUTS-file-upload',
			});

			const submitFragmentDefinition = getFragmentDefinition({
				id: getRandomString(),
				key: 'INPUTS-submit-button',
			});

			const formDefinition = getFormContainerDefinition({
				id: getRandomString(),
				objectDefinitionClassName,
				pageElements: [fileUploadDefinition, submitFragmentDefinition],
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to view mode

			await page.goto(
				`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			// Select file from document library

			const fileUploadInput = page.locator('.file-upload');

			await fileUploadInput
				.getByText('Select File', {exact: true})
				.click();

			// Assert jpg files are not present

			const dialogIFrame = page.frameLocator('iframe');

			await expect(
				dialogIFrame.getByText(
					'Drag & Drop Your Files or Browse to Upload'
				)
			).toBeVisible();

			await expect(
				dialogIFrame.getByText('balinese.jpg')
			).not.toBeVisible();
		}
	);

	test(
		'View error messages from File Upload field',
		{
			tag: '@LPS-151402',
		},
		async ({apiHelpers, page, pageManagementSite}) => {

			// Create a page with a form fragment with a file upload fragment

			const objectDefinitionApiClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {className: objectDefinitionClassName} = (
				await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
					getObjectERC('All Fields')
				)
			).body;

			const fileUploadId = getRandomString();

			const fileUploadDefinition = getFragmentDefinition({
				fragmentConfig: {
					inputFieldId: 'ObjectField_fileUpload',
				},
				id: fileUploadId,
				key: 'INPUTS-file-upload',
			});

			const submitFragmentDefinition = getFragmentDefinition({
				id: getRandomString(),
				key: 'INPUTS-submit-button',
			});

			const formDefinition = getFormContainerDefinition({
				id: getRandomString(),
				objectDefinitionClassName,
				pageElements: [fileUploadDefinition, submitFragmentDefinition],
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to view mode

			await page.goto(
				`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			// Select file from computer

			const fileChooserPromise = page.waitForEvent('filechooser');

			const fileUploadInput = page.locator('.file-upload');

			await fileUploadInput
				.getByText('Select File', {exact: true})
				.click();

			const fileChooser = await fileChooserPromise;

			await fileChooser.setFiles(
				path.join(__dirname, '/dependencies/high_resolution_image.jpg')
			);

			await expect(
				fileUploadInput.getByText('high_resolution_photo')
			).toBeVisible();

			// Submit form

			await page.getByRole('button', {name: 'Submit'}).click();

			// Assert error message

			await expect(
				page.getByText(
					'File size is larger than the allowed maximum upload size (2 MB).'
				)
			).toBeVisible();
		}
	);
});

test.describe('Form Localization', () => {
	test('Can translate form fields', async ({
		apiHelpers,
		page,
		pageEditorPage,
		pageManagementSite,
	}) => {

		// Create a page with a Form fragment and a Localization Select fragment

		const fragmentDefinition = getFragmentDefinition({
			id: getRandomString(),
			key: 'localization-select',
		});

		const formId = getRandomString();

		const formDefinition = getFormContainerDefinition({
			id: formId,
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				fragmentDefinition,
				formDefinition,
			]),
			siteId: pageManagementSite.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

		// Map the form to the All Fields object and publish the page

		await pageEditorPage.mapFormFragment(formId, 'All Fields');

		await pageEditorPage.publishPage();

		// Go to view mode and fill the form

		await page.goto(
			`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
		);

		await page.locator('iframe[title="editor"]').waitFor();

		await page.getByLabel('Long Text').fill('long text english');

		await page.getByLabel('Text', {exact: true}).fill('text english');
		await page.evaluate(() =>
			(window as any).CKEDITOR.instances['richText'].setData(
				'rich text english'
			)
		);

		// Add translations and check translation status

		const translationSelector = page.getByLabel(
			'Select a language, current language:'
		);

		await translationSelector.click();

		const option = page.getByRole('option', {
			name: 'Spanish (Spain) Language',
		});

		await expect(option).toContainText(/Not Translated/);

		await option.click();

		await page.getByLabel('Long Text').fill('long text español');

		await page.getByLabel('Text', {exact: true}).fill('text español');

		await translationSelector.click();

		await expect(option).toContainText(/Translating 2\/3/);

		await option.click();

		await page.evaluate(() =>
			(window as any).CKEDITOR.instances['richText'].setData(
				'rich text español'
			)
		);

		await translationSelector.click();

		await expect(option).toContainText(/Translated/);

		await option.click();

		// Publish the form

		await page.getByRole('button', {name: 'Submit'}).click();

		await expect(
			page.getByText(
				'Thank you. Your information was successfully received.'
			)
		).toBeVisible();

		// Go to custom object admin an check the values

		await goToObjectEntity({
			entityName: 'All Fields',
			page,
		});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {
				exact: true,
				name: 'View',
			}),
			trigger: page.locator('.dnd-tbody .item-actions').last(),
		});

		await page.getByRole('textbox', {name: 'Long Text'}).waitFor();

		await expect(page.getByText('long text english')).toBeVisible();
		await expect(
			page
				.frameLocator('iframe[title="editor"]')
				.getByText('rich text english')
		).toBeVisible();
		await expect(page.getByText('text english')).toBeVisible();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {
				name: 'Español',
			}),
			trigger: page.getByTestId('triggerButton').first(),
		});

		await expect(page.getByText('long text español')).toBeVisible();
		await expect(
			page
				.frameLocator('iframe[title="editor"]')
				.getByText('rich text español')
		).toBeVisible();
		await expect(page.getByText('text español')).toBeVisible();
	});
});

test.describe('Numeric input field', () => {
	test('Check the numeric input configuration', async ({
		apiHelpers,
		page,
		pageEditorPage,
		pageManagementSite,
	}) => {

		// Create a page with a Form fragment

		const formId = getRandomString();

		const formDefinition = getFormContainerDefinition({
			id: formId,
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([formDefinition]),
			siteId: pageManagementSite.id,
			title: getRandomString(),
		});

		// Go to edit mode and map the form to Lemon object, specifically to the "Lemon Weight" field

		await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

		await pageEditorPage.mapFormFragment(formId, 'Lemon', ['Lemon Weight']);

		// Check Mark as Required field

		const numericInputId = await pageEditorPage.getFragmentId('Numeric');

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Mark as Required',
			fragmentId: numericInputId,
			tab: 'General',
			value: true,
		});

		const requireIcon = page
			.locator('label')
			.filter({hasText: 'Lemon Weight'})
			.locator('svg.reference-mark');

		await expect(requireIcon).toBeAttached();

		// Check Label and Show Label fields

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Label',
			fragmentId: numericInputId,
			tab: 'General',
			value: 'Lemon weight in grams',
		});

		const label = page
			.locator('label')
			.filter({hasText: 'Lemon weight in grams'});

		await expect(label).not.toHaveClass(/sr-only/);

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Show Label',
			fragmentId: numericInputId,
			tab: 'General',
			value: false,
		});

		await expect(label).toHaveClass(/sr-only/);

		// Check Help Text and Show Help Text fields

		const helpText = page.getByText('Add your help text here.', {
			exact: true,
		});

		await expect(helpText).not.toBeAttached();

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Show Help Text',
			fragmentId: numericInputId,
			tab: 'General',
			value: true,
		});

		await expect(helpText).toBeVisible();

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Help Text',
			fragmentId: numericInputId,
			tab: 'General',
			value: 'The lemon weight must be in grams',
		});

		await expect(
			page.getByText('The lemon weight must be in grams')
		).toBeVisible();

		// Check Placeholder field

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Placeholder',
			fragmentId: numericInputId,
			tab: 'General',
			value: 'Lemon weight in grams',
		});

		await expect(
			page.getByPlaceholder('Lemon weight in grams')
		).toBeVisible();
	});

	test('Check the numeric input error', async ({
		apiHelpers,
		page,
		pageEditorPage,
		pageManagementSite,
	}) => {

		// Create a page with a Form fragment

		const formId = getRandomString();

		const formDefinition = getFormContainerDefinition({
			id: formId,
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([formDefinition]),
			siteId: pageManagementSite.id,
			title: getRandomString(),
		});

		// Go to edit mode and map the form to Lemon object, specifically to the "Lemon Weight" field

		await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

		await pageEditorPage.mapFormFragment(formId, 'Lemon', ['Lemon Weight']);

		await pageEditorPage.publishPage();

		// Go to view mode and check that the input type is numeric and has the attributes max and min

		await page.goto(
			`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
		);

		const lemonWeightInput = page.getByLabel('Lemon Weight');

		await expect(lemonWeightInput).toHaveAttribute('type', 'number');
		await expect(lemonWeightInput).toHaveAttribute('max');
		await expect(lemonWeightInput).toHaveAttribute('min');

		// Submit the form with a wrong value

		await lemonWeightInput.fill('-1');

		await page.getByText('Submit', {exact: true}).click();

		await expect(
			page.getByText('The lemon weight must be greater than 0')
		).toBeVisible();

		// Submit the form with a correct value

		await lemonWeightInput.fill('10');

		await page.getByText('Submit', {exact: true}).click();

		await expect(
			page.getByText(
				'Thank you. Your information was successfully received.'
			)
		).toBeVisible();
	});
});

test.describe('Text input field', () => {
	test(
		'Check the Text input configuration',
		{tag: '@LPS-149725'},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Create a page with a Form fragment

			const formId = getRandomString();

			const formDefinition = getFormContainerDefinition({
				id: formId,
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode and map the form to Lemon object, specifically to the "Lemon Size" field

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			await pageEditorPage.mapFormFragment(formId, 'Lemon', [
				'Lemon Size',
			]);

			// Check Mark as Required field

			const inputId = await pageEditorPage.getFragmentId('Text');

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Mark as Required',
				fragmentId: inputId,
				tab: 'General',
				value: true,
			});

			const requireIcon = page
				.locator('label')
				.filter({hasText: 'Lemon Size'})
				.locator('svg.reference-mark');

			await expect(requireIcon).toBeAttached();

			// Check Label and Show Label fields

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Label',
				fragmentId: inputId,
				tab: 'General',
				value: 'Lemon size in cm',
			});

			const label = page
				.locator('label')
				.filter({hasText: 'Lemon size in cm'});

			await expect(label).not.toHaveClass(/sr-only/);

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Show Label',
				fragmentId: inputId,
				tab: 'General',
				value: false,
			});

			await expect(label).toHaveClass(/sr-only/);

			// Check Help Text and Show Help Text fields

			const helpText = page.getByText('Add your help text here.', {
				exact: true,
			});

			await expect(helpText).not.toBeAttached();

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Show Help Text',
				fragmentId: inputId,
				tab: 'General',
				value: true,
			});

			await expect(helpText).toBeVisible();

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Help Text',
				fragmentId: inputId,
				tab: 'General',
				value: 'The lemon size must be in cm',
			});

			await expect(
				page.getByText('The lemon size must be in cm')
			).toBeVisible();

			// Check Placeholder field

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Placeholder',
				fragmentId: inputId,
				tab: 'General',
				value: 'Type the lemon size',
			});

			await expect(
				page.getByPlaceholder('Type the lemon size')
			).toBeVisible();

			// Show characters count

			const characterText = page.getByText('0 / 280');

			await expect(characterText).toHaveClass(/sr-only/);

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Show Characters Count',
				fragmentId: inputId,
				tab: 'General',
				value: true,
			});

			await expect(characterText).not.toHaveClass(/sr-only/);
		}
	);

	test(
		'An error is shown when the number of input characters is exceeded',
		{tag: ['@LPS-149725', '@LPS-173849']},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Create a page with a Form fragment

			const formId = getRandomString();

			const formDefinition = getFormContainerDefinition({
				id: formId,
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode and map the form to Lemon object, specifically to the "Lemon Size" field

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			await pageEditorPage.mapFormFragment(formId, 'Lemon', [
				'Lemon Size',
			]);

			// Publish and go to view mode

			await pageEditorPage.publishPage();

			await page.goto(
				`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			// Type 290 characters and check that the input error is shown

			const inputError = page.getByText(
				'Maximum Number of Characters Exceeded: 290 / 280'
			);

			const formError = page.getByText(
				'Value exceeds maximum length of 280 for field Lemon Size.'
			);

			await page.getByLabel('Lemon Size').click();

			await page.keyboard.type('a'.repeat(290));

			await expect(inputError).toBeVisible();

			await expect(formError).not.toBeVisible();

			// Submit the form and check that the form error is shown as an alert

			await page.getByText('Submit', {exact: true}).click();

			await expect(formError).toBeVisible();

			await expect(formError).toHaveClass(/alert/);
		}
	);

	test(
		'Check that the input is a required field by default',
		{tag: '@LPS-151400'},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Create a page with a Form fragment

			const formId = getRandomString();

			const formDefinition = getFormContainerDefinition({
				id: formId,
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode and map the form to Potato object, specifically to the "Potato Origin" field

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			await pageEditorPage.mapFormFragment(formId, 'Potato', [
				'Potato Origin',
			]);

			// Select the input fragment and check that it is a required field

			const inputId = await pageEditorPage.getFragmentId('Text');

			await pageEditorPage.selectFragment(inputId);

			const selectedOption = page
				.getByLabel('Field', {exact: true})
				.getByRole('option', {selected: true});

			await expect(selectedOption).toContainText('Potato Origin*');

			await expect(
				page.getByLabel('Mark as Required', {exact: true})
			).toBeDisabled();

			// Publish and check that the input has the attribute required

			await pageEditorPage.publishPage();

			await page.goto(
				`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			await expect(page.getByLabel('Potato Origin')).toHaveAttribute(
				'required'
			);
		}
	);
});

test.describe('Submit button', () => {
	test(
		"Cannot save a value as draft in the object when 'Allow Users to Save Entries as Draft' option is not enabled",
		{tag: '@LPS-191474'},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Create a Content page with a form

			const formId = getRandomString();

			const formDefinition = getFormContainerDefinition({
				id: formId,
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode and map the form to Lemon Weight field

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			await pageEditorPage.mapFormFragment(formId, 'Lemon', [
				'Lemon Weight',
			]);

			// Change the "Submitted Entry Status" configuration to Draft

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Submitted Entry Status',
				fragmentId: await pageEditorPage.getFragmentId('Form Button'),
				tab: 'General',
				value: 'Draft',
			});

			// Publish with a draft submit button

			await page.getByLabel('Publish', {exact: true}).click();

			await expect(
				page.getByText(
					'form does not allow creating entries as draft. Review the button configuration and set it to approved to generate valid entries.'
				)
			).toBeVisible();

			await page
				.locator('.modal')
				.getByText('Publish', {exact: true})
				.click();

			await waitForAlert(
				page,
				'Success:The page was published successfully.'
			);

			// Go to view mode and check that the value cannot be saved

			await page.goto(
				`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			await page.getByLabel('Lemon Weight').fill('200');

			await page.getByText('Submit', {exact: true}).click();

			await expect(
				page.getByText(
					'An error occurred while sending the form information.'
				)
			).toBeVisible();
		}
	);

	test(
		'It is not possible to change an object from approved status to draft status',
		{tag: '@LPS-191474'},
		async ({
			apiHelpers,
			displayPageTemplatesPage,
			objectDetailsPage,
			page,
			pageEditorPage,
			pageManagementSite,
		}) => {
			const checkObjectEntryStatus = async (
				value: string,
				status: string
			) => {

				// Go to entity

				await goToObjectEntity({
					entityName: 'Lemon',
					page,
				});

				// Check the status of the object entry

				const row = page.locator('.dnd-tr').filter({hasText: value});

				await expect(row).toContainText(status);
			};

			await test.step('Set the "Allow Users to Save Entries as Draft" configuration of the Lemon object to true', async () => {
				await objectDetailsPage.goto('Lemon');

				await objectDetailsPage.updateConfiguration({
					fieldLabel: 'Allow Users to Save Entries as Draft',
					value: true,
				});
			});

			const displayPageTemplateName = getRandomString();

			await test.step('Create a Display Page Template with a Form container mapped to Lemon object and two buttons, one to save as Draft and other to save as Approved', async () => {

				// Create a Display page for the Lemon object

				await displayPageTemplatesPage.goto(
					pageManagementSite.friendlyUrlPath
				);

				await displayPageTemplatesPage.createTemplate({
					contentType: 'Lemon',
					name: displayPageTemplateName,
				});

				await displayPageTemplatesPage.editTemplate(
					displayPageTemplateName
				);

				// Add a Form Container and map it to Lemon Weight field

				await pageEditorPage.addFragment(
					'Form Components',
					'Form Container'
				);

				const fragmentId =
					await pageEditorPage.getFragmentId('Form Container');

				await pageEditorPage.mapFormFragment(
					fragmentId,
					'Lemon (Default)',
					['Lemon Weight']
				);

				// Add another submit button with the "Submitted Entry Status" configuration as Draft

				const dptSubmitButtonId =
					await pageEditorPage.getFragmentId('Form Button');

				await pageEditorPage.clickFragmentOption(
					dptSubmitButtonId,
					'Duplicate'
				);

				await pageEditorPage.editTextEditable(
					dptSubmitButtonId,
					'submit-button-text',
					'Submit as draft'
				);

				await pageEditorPage.changeFragmentConfiguration({
					fieldLabel: 'Submitted Entry Status',
					fragmentId: dptSubmitButtonId,
					tab: 'General',
					value: 'Draft',
				});

				await displayPageTemplatesPage.publishTemplate();
			});

			const headingId = getRandomString();
			const formId = getRandomString();
			let layout = null;

			await test.step('Create a Content page with a Form fragment mapped to Lemon object with a draft Submit Button and a Heading fragment', async () => {

				// Create a Content page

				const formDefinition = getFormContainerDefinition({
					id: formId,
				});

				const headingDefinition = getFragmentDefinition({
					id: headingId,
					key: 'BASIC_COMPONENT-heading',
				});

				layout = await apiHelpers.headlessDelivery.createSitePage({
					pageDefinition: getPageDefinition([
						formDefinition,
						headingDefinition,
					]),
					siteId: pageManagementSite.id,
					title: getRandomString(),
				});

				// Go to edit mode

				await pageEditorPage.goto(
					layout,
					pageManagementSite.friendlyUrlPath
				);

				// Map the form to Lemon Weight field

				await pageEditorPage.mapFormFragment(formId, 'Lemon', [
					'Lemon Weight',
				]);

				// Change the "Submitted Entry Status" configuration to Draft

				const submitButtonId =
					await pageEditorPage.getFragmentId('Form Button');

				await pageEditorPage.editTextEditable(
					submitButtonId,
					'submit-button-text',
					'Submit as draft'
				);

				await pageEditorPage.changeFragmentConfiguration({
					fieldLabel: 'Submitted Entry Status',
					fragmentId: submitButtonId,
					tab: 'General',
					value: 'Draft',
				});

				await pageEditorPage.publishPage();
			});

			const input = page.getByLabel('Lemon Weight');
			const submitDraftButton = page.getByText('Submit as draft', {
				exact: true,
			});

			await test.step('Go to view mode and save the Lemon Weight field value as draft', async () => {
				await page.goto(
					`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
				);

				await fillAndClickOutside(page, input, '100');

				await submitDraftButton.click();

				await page
					.getByText(
						'Thank you. Your information was successfully received.'
					)
					.waitFor();

				// Check the saved value

				await checkObjectEntryStatus('100', 'Draft');
			});

			await test.step('Go to edit mode and map the Heading to the draft entry number and select the DPT created before as Field', async () => {
				await pageEditorPage.goto(
					layout,
					pageManagementSite.friendlyUrlPath
				);

				await pageEditorPage.changeEditableConfiguration({
					editableId: 'element-text',
					fieldLabel: 'Link',
					fragmentId: headingId,
					tab: 'Link',
					value: 'Mapped URL',
				});

				await pageEditorPage.openMappingSelector();

				const iframe = page.frameLocator('iframe[title="Select"]');

				await iframe.getByPlaceholder('Search').waitFor();

				await iframe.getByText('Lemons', {exact: true}).click();

				await clickAndExpectToBeHidden({
					target: iframe.locator('.lfr-item-viewer'),
					trigger: iframe
						.locator('.item-selector-list-row .entry')
						.first(),
				});

				await pageEditorPage.changeConfiguration({
					fieldLabel: 'Field',
					tab: 'Link',
					value: displayPageTemplateName,
				});

				await pageEditorPage.publishPage();
			});

			const headingFragment = page.getByText('Heading Example', {
				exact: true,
			});

			await test.step('Go to view mode, click in the Heading and save the field value as Draft', async () => {
				await page.goto(
					`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
				);

				await headingFragment.click();

				await expect(headingFragment).not.toBeAttached();

				// Set new value and submit as draft

				await fillAndClickOutside(page, input, '200');

				await submitDraftButton.click();

				await page
					.getByText(
						'Thank you. Your information was successfully received.'
					)
					.waitFor();

				// Check the saved value

				await checkObjectEntryStatus('200', 'Draft');
			});

			await test.step('Go to view mode, click in the Heading and save the field value as Approved', async () => {
				await page.goto(
					`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
				);

				await headingFragment.click();

				await expect(headingFragment).not.toBeAttached();

				// Set new value and submit as approved

				await fillAndClickOutside(page, input, '300');

				await page.getByText('Submit', {exact: true}).click();

				await page
					.getByText(
						'Thank you. Your information was successfully received.'
					)
					.waitFor();

				// Check the saved value

				await checkObjectEntryStatus('300', 'Approved');
			});

			await test.step('Go to view mode, click in the Heading and try to save the field value as Draft again', async () => {
				await page.goto(
					`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
				);

				await headingFragment.click();

				await expect(headingFragment).not.toBeAttached();

				// Set new value and submit as draft

				await fillAndClickOutside(page, input, '400');

				await submitDraftButton.click();

				await expect(
					page.getByText(
						'An error occurred while sending the form information.'
					)
				).toBeVisible();

				// Check the saved value

				await checkObjectEntryStatus('300', 'Approved');
			});

			await test.step('Restore default value', async () => {
				await objectDetailsPage.goto('Lemon');

				await objectDetailsPage.updateConfiguration({
					fieldLabel: 'Allow Users to Save Entries as Draft',
					value: false,
				});
			});
		}
	);
});

test.describe('Textarea input field', () => {
	test(
		'Check the Textarea input configuration',
		{tag: '@LPS-170206'},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Create a page with a Form fragment

			const formId = getRandomString();

			const formDefinition = getFormContainerDefinition({
				id: formId,
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode and map the form to Lemon object, specifically to the "Lemon History" field

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			await pageEditorPage.mapFormFragment(formId, 'Lemon', [
				'Lemon History',
			]);

			const textareaInput = page.getByLabel('Lemon History', {
				exact: true,
			});

			// Check the role of the input is textbox

			await expect(textareaInput).toHaveRole('textbox');

			// Check Number of Lines config

			await expect(textareaInput).toHaveAttribute('rows', '5');

			await pageEditorPage.selectFragment(formId);

			const textareaInputId =
				await pageEditorPage.getFragmentId('Textarea');

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Number of Lines',
				fragmentId: textareaInputId,
				tab: 'General',
				value: '2',
			});

			await expect(textareaInput).toHaveAttribute('rows', '2');

			// Check Mark as Required field

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Mark as Required',
				fragmentId: textareaInputId,
				tab: 'General',
				value: true,
			});

			const requireIcon = page
				.locator('label')
				.filter({hasText: 'Lemon History'})
				.locator('svg.reference-mark');

			await expect(requireIcon).toBeAttached();

			// Check Label and Show Label fields

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Label',
				fragmentId: textareaInputId,
				tab: 'General',
				value: 'Describe the history of the lemon',
			});

			const label = page
				.locator('label')
				.filter({hasText: 'Describe the history of the lemon'});

			await expect(label).not.toHaveClass(/sr-only/);

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Show Label',
				fragmentId: textareaInputId,
				tab: 'General',
				value: false,
			});

			await expect(label).toHaveClass(/sr-only/);

			// Check Help Text and Show Help Text fields

			const helpText = page.getByText('Add your help text here.', {
				exact: true,
			});

			await expect(helpText).not.toBeAttached();

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Show Help Text',
				fragmentId: textareaInputId,
				tab: 'General',
				value: true,
			});

			await expect(helpText).toBeVisible();

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Help Text',
				fragmentId: textareaInputId,
				tab: 'General',
				value: 'Brief description of the lemon history',
			});

			await expect(
				page.getByText('Brief description of the lemon history')
			).toBeVisible();

			// Check Placeholder field

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Placeholder',
				fragmentId: textareaInputId,
				tab: 'General',
				value: 'Type the lemon history',
			});

			await expect(
				page.getByPlaceholder('Type the lemon history')
			).toBeVisible();

			// Show characters count

			const characterText = page.getByText('0 / 300');

			await expect(characterText).toHaveClass(/sr-only/);

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Show Characters Count',
				fragmentId: textareaInputId,
				tab: 'General',
				value: true,
			});

			await expect(characterText).not.toHaveClass(/sr-only/);
		}
	);

	test(
		'Check the Textarea input errors',
		{tag: ['@LPS-170206', '@LPS-173849', '@LPS-182728']},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Create a page with a Form fragment

			const formId = getRandomString();

			const formDefinition = getFormContainerDefinition({
				id: formId,
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode and map the form to Lemon object, specifically to the "Lemon History" field

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			await pageEditorPage.mapFormFragment(formId, 'Lemon', [
				'Lemon History',
			]);

			// Publish and go to view mode

			await pageEditorPage.publishPage();

			await page.goto(
				`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			// Type 310 characters and check that the input error is shown

			const inputError = page.getByText(
				'Maximum Number of Characters Exceeded: 310 / 300'
			);

			await page.getByLabel('Lemon History').click();

			await page.keyboard.type('a'.repeat(310));

			await expect(inputError).toBeVisible();

			// Submit the form and check that the error is still visible

			await page.getByText('Submit', {exact: true}).click();

			await expect(inputError).toBeVisible();
		}
	);
});

test.describe('Picklist input field', () => {
	test('Shows correct options in picklist field selected as title in related object', async ({
		apiHelpers,
		page,
		pageEditorPage,
		pageManagementSite,
	}) => {

		// Create a page with a Form fragment

		const formId = getRandomString();

		const formDefinition = getFormContainerDefinition({
			id: formId,
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([formDefinition]),
			siteId: pageManagementSite.id,
			title: getRandomString(),
		});

		// Go to edit mode

		await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

		// Map the form to Lemon Basket object, select fields and publish

		await pageEditorPage.mapFormFragment(formId, 'Lemon', [
			'Lemon Size',
			'Lemon Basket to Lemons',
		]);

		await pageEditorPage.publishPage();

		// Go to view mode and check picklist values

		await page.goto(
			`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
		);

		await page.getByLabel('Lemon Basket to Lemons').click();

		await expect(page.getByText('Plastic', {exact: true})).toBeVisible();
		await expect(page.getByText('Carton', {exact: true})).toBeVisible();
	});

	test(
		'Checks changing the option when one default is selected set the value correctly',
		{
			tag: '@LPD-31856',
		},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Create a page with a Form fragment

			const formId = getRandomString();

			const formDefinition = getFormContainerDefinition({
				id: formId,
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode and map it to the object

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			await pageEditorPage.mapFormFragment(formId, 'Lemon Basket', [
				'Lemon Dimensions',
				'Material',
			]);

			// Publish and go to view mode

			await pageEditorPage.publishPage();

			await page.goto(
				`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			// Check that the value after selecting the item is correct

			await page.getByLabel('Material').click();

			await page.getByText('carton').click();

			await expect(page.getByLabel('Material')).toHaveValue('Carton');
		}
	);
});

test.describe('Rich Text Fragment', () => {
	test(
		'The page designer can configure rich text fragment',
		{
			tag: '@LPS-170205',
		},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Create a page with a form fragment with a rich text fragment

			const objectDefinitionApiClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {className: objectDefinitionClassName} = (
				await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
					getObjectERC('All Fields')
				)
			).body;

			const richTextId = getRandomString();

			const richTextDefinition = getFragmentDefinition({
				fragmentConfig: {
					inputFieldId: 'ObjectField_richText',
				},
				id: richTextId,
				key: 'INPUTS-rich-text-input',
			});

			const formDefinition = getFormContainerDefinition({
				id: getRandomString(),
				objectDefinitionClassName,
				pageElements: [richTextDefinition],
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			// Change label

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Label',
				fragmentId: richTextId,
				tab: 'General',
				value: 'Description',
			});

			const richTexInput = page.locator('.rich-text-input');

			await expect(richTexInput.getByText('Description')).not.toHaveClass(
				/sr-only/
			);

			// Hide label

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Show Label',
				fragmentId: richTextId,
				tab: 'General',
				value: false,
			});

			await expect(richTexInput.getByText('Description')).toHaveClass(
				/sr-only/
			);

			// Show help text

			await expect(richTexInput).not.toContainText(
				/Add your help text here./
			);

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Show Help Text',
				fragmentId: richTextId,
				tab: 'General',
				value: true,
			});

			await expect(richTexInput).toContainText(
				/Add your help text here./
			);
		}
	);

	test(
		'User should see error message below rich text fragment',
		{
			tag: '@LPS-182728',
		},
		async ({apiHelpers, page, pageManagementSite}) => {

			// Adds rich text validation

			const objectValidationRuleApiClient =
				await apiHelpers.buildRestClient(ObjectValidationRuleApi);

			const {body: objectValidationRule} =
				await objectValidationRuleApiClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
					getObjectERC('All Fields'),
					{
						active: true,
						engine: 'ddm',
						engineLabel: 'Expression Builder',
						errorLabel: {
							en_US: 'Please enter a valid description.',
						},
						name: {
							en_US: 'Rich Text Validation',
						},
						objectValidationRuleSettings: [
							{
								name: 'outputObjectFieldExternalReferenceCode',
								value: 'rich-text-erc',
							} as any,
						],
						outputType:
							ObjectValidationRule.OutputTypeEnum
								.PartialValidation,
						script: 'NOT(isEmpty(richText))',
						system: false,
					}
				);

			// Create a page with a form fragment with a rich text fragment

			const objectDefinitionApiClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {className: objectDefinitionClassName} = (
				await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
					getObjectERC('All Fields')
				)
			).body;

			const richTextId = getRandomString();

			const richTextDefinition = getFragmentDefinition({
				fragmentConfig: {
					inputFieldId: 'ObjectField_richText',
				},
				id: richTextId,
				key: 'INPUTS-rich-text-input',
			});

			const submitFragmentDefinition = getFragmentDefinition({
				id: getRandomString(),
				key: 'INPUTS-submit-button',
			});

			const formDefinition = getFormContainerDefinition({
				id: getRandomString(),
				objectDefinitionClassName,
				pageElements: [richTextDefinition, submitFragmentDefinition],
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to view mode

			await page.goto(
				`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			// Assert error message

			await page.getByText('Submit', {exact: true}).click();

			await expect(page.locator('.rich-text-input')).toContainText(
				'Please enter a valid description.'
			);

			// Delete validation

			await objectValidationRuleApiClient.deleteObjectValidationRule(
				objectValidationRule.id
			);
		}
	);
});

test.describe('Relationships', () => {
	test('Allow selecting fields from main object and relationships in fields modal', async ({
		apiHelpers,
		pageEditorPage,
		pageManagementSite,
	}) => {

		// Create a page with a Form fragment

		const formId = getRandomString();

		const formDefinition = getFormContainerDefinition({
			id: formId,
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([formDefinition]),
			siteId: pageManagementSite.id,
			title: getRandomString(),
		});

		// Go to edit mode

		await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

		// Map the form to Lemon object and select fields

		await pageEditorPage.mapFormFragment(formId, 'Lemon', [
			'Lemon Size',
			'Lemon Basket Color',
		]);

		const form = pageEditorPage.getFragment(formId);

		await expect(form.getByText('Lemon Size')).toBeVisible();
		await expect(form.getByText('Lemon Basket Color')).toBeVisible();
	});
});

test.describe('Multiselect', () => {
	test(
		'Page designer can define number of options of multiselect fragment',
		{tag: ['@LPS-169936', '@LPS-182728']},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Create a page with a Form fragment

			const formId = getRandomString();

			const formDefinition = getFormContainerDefinition({
				id: formId,
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			// Map the form to object and select fields

			await pageEditorPage.mapFormFragment(formId, 'Lemon Basket', [
				'Lemon Dimensions',
			]);

			// Assert help text

			await expect(page.getByText('Lemon Dimensions')).toBeVisible();

			const inputId = await pageEditorPage.getFragmentId('Multiselect');

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Show Help Text',
				fragmentId: inputId,
				tab: 'General',
				value: true,
			});

			await expect(
				page.getByText('Add your help text here.')
			).toBeVisible();

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Help Text',
				fragmentId: inputId,
				tab: 'General',
				value: 'Preferences',
			});

			await expect(page.getByText('Preferences')).toBeVisible();

			// Assert mandatory symbol

			await expect(
				page.locator('.custom-checkbox .lexicon-icon-asterisk')
			).toBeVisible();

			// Change number of options configuration

			await pageEditorPage.selectFragment(inputId);

			await expect(
				page.getByLabel('Number of Options', {exact: true})
			).toHaveValue('5');

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Number of Options',
				fragmentId: inputId,
				tab: 'General',
				value: '2',
			});

			// Assert options in edit mode

			const multiselect = page.locator(
				'.custom-checkbox .custom-control-label'
			);

			await expect(multiselect.nth(0).getByText('Large')).toBeVisible();
			await expect(multiselect.nth(1).getByText('Medium')).toBeVisible();
			await expect(
				multiselect.nth(2).getByText('Small')
			).not.toBeVisible();

			await pageEditorPage.publishPage();

			// Assert options in view mode

			await page.goto(
				`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			await expect(multiselect.nth(0).getByText('Large')).toBeVisible();
			await expect(multiselect.nth(1).getByText('Medium')).toBeVisible();
			await expect(
				multiselect.nth(2).getByText('Small')
			).not.toBeVisible();

			// Click show all

			await page.getByRole('button', {name: 'Show All'}).click();

			await expect(multiselect.nth(0).getByText('Large')).toBeVisible();
			await expect(multiselect.nth(1).getByText('Medium')).toBeVisible();
			await expect(multiselect.nth(2).getByText('Small')).toBeVisible();

			// Go to edit mode and update show all options configuration

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Show All Options',
				fragmentId: inputId,
				tab: 'General',
				value: true,
			});

			// Assert options in edit mode

			await expect(multiselect.nth(0).getByText('Large')).toBeVisible();
			await expect(multiselect.nth(1).getByText('Medium')).toBeVisible();
			await expect(multiselect.nth(2).getByText('Small')).toBeVisible();

			await pageEditorPage.publishPage();

			// Assert options in view mode

			await page.goto(
				`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			await expect(multiselect.nth(0).getByText('Large')).toBeVisible();
			await expect(multiselect.nth(1).getByText('Medium')).toBeVisible();
			await expect(multiselect.nth(2).getByText('Small')).toBeVisible();
		}
	);
});

test.describe('Multistep', () => {
	test(
		'Change to multistep when adding a stepper fragment and remove it when changing to simple',
		{tag: '@LPD-10727'},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Create a page with a Form fragment

			const formId = getRandomString();

			const formDefinition = getFormContainerDefinition({
				id: formId,
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			// Map the form to Lemon object and select fields

			await pageEditorPage.mapFormFragment(formId, 'Lemon', [
				'Lemon Size',
				'Lemon Basket Color',
			]);

			// Add stepper and check multistep modal is shown

			await pageEditorPage.addFragment('Form Components', 'Stepper');

			await expect(
				page.getByText(
					'Adding a stepper fragment inside a simple form will turn it into a multistep form. Are you sure you want to continue?'
				)
			).toBeVisible();

			await page.getByRole('button', {name: 'Continue'}).click();

			// Check that the form is now multistep

			await pageEditorPage.selectFragment(
				await pageEditorPage.getFragmentId('Form Container')
			);

			await expect(
				page.getByLabel('Form Type', {exact: true})
			).toHaveValue('multistep');

			// Change to simple and check stepper is removed

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Form Type',
				fragmentId: formId,
				tab: 'General',
				value: 'simple',
			});

			await page.getByRole('button', {name: 'Continue'}).click();

			await expect(
				page.locator('[data-name="Stepper"]')
			).not.toBeVisible();
		}
	);

	test(
		'Can add and configure a Stepper fragment',
		{tag: '@LPD-10727'},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Get the id of Lemon object from the site initializer

			const objectDefinitionApiClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {className: objectDefinitionClassName} = (
				await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
					getObjectERC('Lemon')
				)
			).body;

			// Create a page with a Form fragment with a Stepper fragment

			const stepperId = getRandomString();

			const stepperFragment = getFragmentDefinition({
				id: stepperId,
				key: 'INPUTS-stepper',
			});

			const formId = getRandomString();

			const formDefinition = getFormContainerDefinition({
				id: formId,
				objectDefinitionClassName,
				pageElements: [stepperFragment],
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			// Check steps titles and bullets numbers are displayed

			await page.locator('.multi-step-nav').getByText('Step 1').waitFor();

			await page
				.locator('.multi-step-icon[data-multi-step-icon="1"]')
				.waitFor();

			// Hide both and check they are not displayed

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Show Bullets Numbers',
				fragmentId: stepperId,
				tab: 'General',
				value: false,
			});

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Show Step Titles',
				fragmentId: stepperId,
				tab: 'General',
				value: false,
			});

			await expect(
				page.locator('.multi-step-nav').getByText('Step 1')
			).not.toBeVisible();

			await expect(
				page.locator('.multi-step-icon[data-multi-step-icon="1"]')
			).not.toBeVisible();
		}
	);

	test(
		'Can configure multistep options for a form container',
		{tag: '@LPD-10727'},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Get the id of Lemon object from the site initializer

			const objectDefinitionApiClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {className: objectDefinitionClassName} = (
				await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
					getObjectERC('Lemon')
				)
			).body;

			// Create a page with a form container

			const formId = getRandomString();

			const formDefinition = getFormContainerDefinition({
				id: formId,
				objectDefinitionClassName,
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			// Check steps are not displayed

			await expect(page.locator('.page-editor__form-step')).toHaveCount(
				0
			);

			// Change to Multistep and check first step is displayed

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Form Type',
				fragmentId: formId,
				tab: 'General',
				value: 'Multistep',
			});

			await expect(page.locator('.page-editor__form-step')).toHaveCount(
				2
			);

			await expect(
				page.locator('.page-editor__form-step').nth(0)
			).toBeVisible();

			await expect(
				page.locator('.page-editor__form-step').nth(1)
			).not.toBeVisible();

			// Check option to display all steps and check it works

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Display All Steps in Edit Mode',
				fragmentId: formId,
				tab: 'General',
				value: true,
			});

			await expect(
				page.locator('.page-editor__form-step').nth(0)
			).toBeVisible();

			await expect(
				page.locator('.page-editor__form-step').nth(1)
			).toBeVisible();

			// Change number of steps and check it works

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Number of Steps',
				fragmentId: formId,
				tab: 'General',
				value: '3',
			});

			await expect(page.locator('.page-editor__form-step')).toHaveCount(
				3
			);
		}
	);

	test(
		'Can change step with the stepper fragment',
		{tag: '@LPD-10727'},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Get the id of Lemon object from the site initializer

			const objectDefinitionApiClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {className: objectDefinitionClassName} = (
				await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
					getObjectERC('Lemon')
				)
			).body;

			// Definition for the Stepper fragment

			const stepperFragment = getFragmentDefinition({
				id: getRandomString(),
				key: 'INPUTS-stepper',
			});

			// Create a form with two steps and the Stepper

			const headingDefinition = getFragmentDefinition({
				id: getRandomString(),
				key: 'BASIC_COMPONENT-heading',
			});

			const buttonDefinition = getFragmentDefinition({
				id: getRandomString(),
				key: 'BASIC_COMPONENT-button',
			});

			const formDefinition = getFormContainerDefinition({
				id: getRandomString(),
				objectDefinitionClassName,
				pageElements: [stepperFragment],
				steps: [[headingDefinition], [buttonDefinition]],
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			// Check step change works properly

			const button = page.locator(
				'.lfr-layout-structure-item-basic-component-button'
			);

			const heading = page.locator(
				'.lfr-layout-structure-item-basic-component-heading'
			);

			await expect(button).not.toBeVisible();
			await expect(heading).toBeVisible();

			const stepButtons = await page.locator('.multi-step-icon').all();
			await stepButtons[1].click();

			await expect(button).toBeVisible();
			await expect(heading).not.toBeVisible();
		}
	);

	test(
		'Can change step with the form button fragment',
		{tag: '@LPD-10727'},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Get the id of Lemon object from the site initializer

			const objectDefinitionApiClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {className: objectDefinitionClassName} = (
				await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
					getObjectERC('Lemon')
				)
			).body;

			// Create a form with two steps and two form buttons

			const headingDefinition = getFragmentDefinition({
				id: getRandomString(),
				key: 'BASIC_COMPONENT-heading',
			});

			const formButtonNextId = getRandomString();

			const formButtonNext = getFragmentDefinition({
				id: formButtonNextId,
				key: 'INPUTS-submit-button',
			});

			const buttonDefinition = getFragmentDefinition({
				id: getRandomString(),
				key: 'BASIC_COMPONENT-button',
			});

			const formButtonPrevious = getFragmentDefinition({
				fragmentConfig: {
					type: 'previous',
				},
				id: getRandomString(),
				key: 'INPUTS-submit-button',
			});

			const formButtonSubmit = getFragmentDefinition({
				id: getRandomString(),
				key: 'INPUTS-submit-button',
			});

			const formDefinition = getFormContainerDefinition({
				id: getRandomString(),
				objectDefinitionClassName,
				steps: [
					[headingDefinition, formButtonNext],
					[buttonDefinition, formButtonPrevious, formButtonSubmit],
				],
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Create function that check form buttons behavior

			const checkFormButtonsBehavior = async () => {

				// Check initial state

				const button = page.locator(
					'.lfr-layout-structure-item-basic-component-button'
				);

				const heading = page.locator(
					'.lfr-layout-structure-item-basic-component-heading'
				);

				await expect(heading).toBeVisible();
				await expect(button).not.toBeVisible();

				// Check Next button works

				await page
					.locator('.btn', {hasText: 'Next'})
					.click({position: {x: 10, y: 10}});

				await expect(heading).not.toBeVisible();
				await expect(button).toBeVisible();

				// Check Previous button works

				await page
					.locator('.btn', {hasText: 'Previous'})
					.click({position: {x: 10, y: 10}});

				await expect(heading).toBeVisible();
				await expect(button).not.toBeVisible();
			};

			// Check in edit mode

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Type',
				fragmentId: formButtonNextId,
				tab: 'General',
				value: 'Next',
			});

			await expect(
				page.locator('.component-button').getByText('Next')
			).toBeVisible();

			await checkFormButtonsBehavior();

			await pageEditorPage.publishPage();

			// Check in view mode

			await page.goto(
				`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			await checkFormButtonsBehavior();
		}
	);

	test(
		'Step change affects only desired form',
		{tag: '@LPD-10727'},
		async ({apiHelpers, page, pageManagementSite}) => {

			// Get the id of Lemon object from the site initializer

			const objectDefinitionApiClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {className: objectDefinitionClassName} = (
				await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
					getObjectERC('Lemon')
				)
			).body;

			// Definition for the Steppers fragment

			const stepperFragment1 = getFragmentDefinition({
				id: getRandomString(),
				key: 'INPUTS-stepper',
			});

			const stepperFragment2 = getFragmentDefinition({
				id: getRandomString(),
				key: 'INPUTS-stepper',
			});

			// Create two forms with two steps and the Stepper

			const headingDefinition = getFragmentDefinition({
				id: getRandomString(),
				key: 'BASIC_COMPONENT-heading',
			});

			const buttonDefinition = getFragmentDefinition({
				id: getRandomString(),
				key: 'BASIC_COMPONENT-button',
			});

			const form1Definition = getFormContainerDefinition({
				id: getRandomString(),
				objectDefinitionClassName,
				pageElements: [stepperFragment1],
				steps: [[headingDefinition], []],
			});

			const form2Definition = getFormContainerDefinition({
				id: getRandomString(),
				objectDefinitionClassName,
				pageElements: [stepperFragment2],
				steps: [[buttonDefinition], []],
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([
					form1Definition,
					form2Definition,
				]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to view mode of page

			await page.goto(
				`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			// Check step change affects only desired form

			const button = page.locator(
				'.lfr-layout-structure-item-basic-component-button'
			);

			const heading = page.locator(
				'.lfr-layout-structure-item-basic-component-heading'
			);

			await expect(button).toBeVisible();
			await expect(heading).toBeVisible();

			const firstForm = page
				.locator('.lfr-layout-structure-item-form')
				.first();

			const firstFormSteps = await firstForm
				.locator('.multi-step-icon')
				.all();
			await firstFormSteps[1].click();

			await expect(heading).not.toBeVisible();
			await expect(button).toBeVisible();
		}
	);

	test(
		'Stepper gets number of steps from parent form',
		{tag: '@LPD-10727'},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Get the id of Lemon object from the site initializer

			const objectDefinitionApiClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {className: objectDefinitionClassName} = (
				await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
					getObjectERC('Lemon')
				)
			).body;

			// Create a form with a Stepper

			const stepperId = getRandomString();

			const stepperFragment = getFragmentDefinition({
				id: stepperId,
				key: 'INPUTS-stepper',
			});

			const formId = getRandomString();

			const formDefinition = getFormContainerDefinition({
				id: formId,
				objectDefinitionClassName,
				pageElements: [stepperFragment],
				steps: [[]],
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode of page

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			// Check changing number of steps in Form affects the Stepper

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Number of Steps',
				fragmentId: formId,
				tab: 'General',
				value: '4',
			});

			await expect(page.locator('.multi-step-indicator')).toHaveCount(4);

			// Delete the Stepper and check that when adding it again, it takes the correct number of steps

			await pageEditorPage.deleteFragment(stepperId);

			await pageEditorPage.addFragment('Form Components', 'Stepper');

			await expect(page.locator('.multi-step-indicator')).toHaveCount(4);
		}
	);

	test(
		'Undoing the action of adding a stepper to a simple form changes the form type to simple again',
		{tag: '@LPD-10727'},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Get the id of Lemon object from the site initializer

			const objectDefinitionApiClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {className: objectDefinitionClassName} = (
				await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
					getObjectERC('Lemon')
				)
			).body;

			// Create a page with a Form fragment

			const formId = getRandomString();

			const formDefinition = getFormContainerDefinition({
				id: formId,
				objectDefinitionClassName,
				pageElements: [],
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			// Add a Stepper

			await pageEditorPage.addFragment(
				'Form Components',
				'Stepper',
				page.locator('.page-editor__form .page-editor__container')
			);

			await page
				.locator('.modal-title', {hasText: 'Convert to Multistep Form'})
				.waitFor();

			await page.locator('.modal-footer').getByText('Continue').click();

			await pageEditorPage.waitForChangesSaved();

			// Check type changed to Multistep

			await pageEditorPage.selectFragment(formId);

			await expect(
				page.getByLabel('Form Type', {exact: true})
			).toHaveValue('multistep');

			// Undo the action

			await pageEditorPage.undoButton.click();

			await pageEditorPage.waitForChangesSaved();

			// Check Stepper disappeared and type changed to Simple again

			await expect(
				page.getByLabel('Form Type', {exact: true})
			).toHaveValue('simple');

			await expect(page.locator('.multi-step-nav')).not.toBeVisible();
		}
	);

	test(
		'Undoing the action of moving a stepper from a multistep to a simple form changes the form type to simple again',
		{tag: '@LPD-10727'},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Get the id of Lemon object from the site initializer

			const objectDefinitionApiClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {className: objectDefinitionClassName} = (
				await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
					getObjectERC('Lemon')
				)
			).body;

			// Create a page containing a multistep form with a stepper and a simple form

			const stepperId = getRandomString();

			const stepperFragment = getFragmentDefinition({
				id: stepperId,
				key: 'INPUTS-stepper',
			});

			const firstFormId = getRandomString();

			const firstFormDefinition = getFormContainerDefinition({
				id: firstFormId,
				objectDefinitionClassName,
				pageElements: [stepperFragment],
				steps: [[]],
			});

			const secondFormId = getRandomString();

			const secondFormDefinition = getFormContainerDefinition({
				id: secondFormId,
				objectDefinitionClassName,
				pageElements: [],
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([
					firstFormDefinition,
					secondFormDefinition,
				]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			// Move the stepper to the second form

			await pageEditorPage.goToSidebarTab('Browser');

			await pageEditorPage.selectFragment(stepperId);

			await pageEditorPage.dragTreeNode({
				source: {label: 'Stepper'},
				target: {label: 'Form Container', nth: 1},
			});

			await page
				.locator('.modal-title', {hasText: 'Convert to Multistep Form'})
				.waitFor();

			await page.locator('.modal-footer').getByText('Continue').click();

			await pageEditorPage.waitForChangesSaved();

			// Check type changed to Multistep

			await pageEditorPage.selectFragment(secondFormId);

			await expect(
				page.getByLabel('Form Type', {exact: true})
			).toHaveValue('multistep');

			// Undo the action

			await pageEditorPage.undoButton.click();

			await pageEditorPage.waitForChangesSaved();

			// Check Stepper disappeared and type changed to Simple again

			await expect(
				page.getByLabel('Form Type', {exact: true})
			).toHaveValue('simple');

			const secondForm = page
				.locator('.page-editor__form .page-editor__container')
				.last();

			await expect(
				secondForm.locator('.multi-step-nav')
			).not.toBeVisible();
		}
	);

	test(
		'Correctly handle multistep form errors in view mode',
		{tag: '@LPD-10727'},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Get the id of Potato object from the site initializer

			const objectDefinitionApiClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {className: objectDefinitionClassName} = (
				await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
					getObjectERC('Potato')
				)
			).body;

			// Create a form with three steps and a stepper

			const stepperId = getRandomString();

			const stepperFragment = getFragmentDefinition({
				fragmentConfig: {
					numberOfSteps: 3,
				},
				id: stepperId,
				key: 'INPUTS-stepper',
			});

			const textInputId = getRandomString();

			const textInputFragment = getFragmentDefinition({
				id: textInputId,
				key: 'INPUTS-text-input',
			});

			const submitButtonFragment = getFragmentDefinition({
				fragmentConfig: {
					type: 'submit',
				},
				id: getRandomString(),
				key: 'INPUTS-submit-button',
			});

			const formId = getRandomString();

			const formDefinition = getFormContainerDefinition({
				id: formId,
				objectDefinitionClassName,
				pageElements: [stepperFragment],
				steps: [[], [textInputFragment], [submitButtonFragment]],
			});

			// Create page and go to edit mode

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			// Map text input fragment to Potato Origin field

			await page.locator('[data-multi-step-icon="2"]').click();

			await pageEditorPage.selectFragment(textInputId);

			await page.getByLabel('Field', {exact: true}).waitFor();

			await page
				.getByLabel('Field', {exact: true})
				.selectOption('Potato Origin*');

			// Publish

			await clickAndExpectToBeVisible({
				target: page.locator('.modal-title', {hasText: 'Form Errors'}),
				timeout: 3000,
				trigger: pageEditorPage.publishButton,
			});

			await page.locator('.modal-footer').getByText('Publish').click();

			await waitForAlert(
				page,
				'Success:The page was published successfully.'
			);

			// Go to view mode

			await page.goto(
				`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			// Create function to submit form

			const submitForm = async () => {
				await expect(async () => {
					await page.locator('[data-multi-step-icon="2"]').click();

					const submitButton = page.getByRole('button', {
						name: 'Submit',
					});

					await page.locator('[data-multi-step-icon="3"]').click();

					await expect(submitButton).toBeVisible({timeout: 100});

					await submitButton.click();
				}).toPass();
			};

			// Try to submit and check it takes to step 2 because field is required

			const field = page.getByLabel('Potato Origin');

			await submitForm();

			await field.waitFor();

			// Fill field with incorrect value, submit and check it shows error

			await field.fill('Madrid');

			await submitForm();

			await page
				.getByText('Potato Origin should be Canary Islands')
				.waitFor();

			// Fill field with correct value, submit and check it submits

			await page.getByLabel('Potato Origin').fill('Canary Islands');

			await submitForm();

			await expect(
				page.getByText('Your information was successfully received')
			).toBeVisible();
		}
	);

	test(
		'The last step is selected when the active one is removed',
		{tag: '@LPD-38514'},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Get the id of Lemon object from the site initializer

			const objectDefinitionApiClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {className: objectDefinitionClassName} = (
				await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
					getObjectERC('Lemon')
				)
			).body;

			// Create a form with a Stepper

			const stepperId = getRandomString();

			const stepperFragment = getFragmentDefinition({
				fragmentConfig: {
					numberOfSteps: 3,
				},
				id: stepperId,
				key: 'INPUTS-stepper',
			});

			const headingDefinition = getFragmentDefinition({
				id: getRandomString(),
				key: 'BASIC_COMPONENT-heading',
			});

			const formId = getRandomString();

			const formDefinition = getFormContainerDefinition({
				id: formId,
				objectDefinitionClassName,
				pageElements: [stepperFragment],
				steps: [[], [headingDefinition], []],
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode of page and select third step

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			await page.locator('.multi-step-indicator').nth(2).click();

			await expect(page.getByText('Heading Example')).not.toBeVisible();

			// Change the number of steps to 2 and check step 2 is selected

			await pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'Number of Steps',
				fragmentId: formId,
				tab: 'General',
				value: '2',
			});

			await expect(page.getByText('Heading Example')).toBeVisible();
		}
	);
});

test.describe('Edit mode language changes', () => {
	test('Input fragments show correct label, help text and placeholder when switching language', async ({
		apiHelpers,
		page,
		pageEditorPage,
		pageManagementSite,
	}) => {

		// Create a page with a Form fragment

		const formId = getRandomString();

		const formDefinition = getFormContainerDefinition({
			id: formId,
		});

		const languageSelectorDefinition = getWidgetDefinition({
			id: getRandomString(),
			widgetName:
				'com_liferay_site_navigation_language_web_portlet_SiteNavigationLanguagePortlet',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				formDefinition,
				languageSelectorDefinition,
			]),
			siteId: pageManagementSite.id,
			title: getRandomString(),
		});

		// Go to edit mode

		await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

		await pageEditorPage.mapFormFragment(formId, 'Lemon', ['Lemon Size']);

		const fragmentId = await pageEditorPage.getFragmentId('Text');

		// Add translations to label, help text and placeholder

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Label',
			fragmentId,
			tab: 'General',
			value: 'English Label',
		});

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Help Text',
			fragmentId,
			tab: 'General',
			value: 'English Help Text',
		});

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Placeholder',
			fragmentId,
			tab: 'General',
			value: 'English Placeholder',
		});

		await pageEditorPage.switchLanguage('es-ES');

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Label',
			fragmentId,
			tab: 'General',
			value: 'Spanish Label',
		});

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Show Help Text',
			fragmentId,
			tab: 'General',
			value: true,
		});

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Help Text',
			fragmentId,
			tab: 'General',
			value: 'Spanish Help Text',
		});

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Placeholder',
			fragmentId,
			tab: 'General',
			value: 'Spanish Placeholder',
		});

		// Check that the translations are correctly displayed

		await pageEditorPage.switchLanguage('en-US');

		const englishLabel = page.getByLabel('English Label');
		const englishHelpText = page.getByText('English Help Text');
		const englishPlaceholder = page.getByPlaceholder('English Placeholder');

		const spanishLabel = page.getByLabel('Spanish Label');
		const spanishHelpText = page.getByText('Spanish Help Text');
		const spanishPlaceholder = page.getByPlaceholder('Spanish Placeholder');

		await expect(englishLabel).toBeVisible();
		await expect(englishHelpText).toBeVisible();
		await expect(englishPlaceholder).toBeVisible();

		await pageEditorPage.switchLanguage('es-ES');

		await expect(spanishLabel).toBeVisible();
		await expect(spanishHelpText).toBeVisible();
		await expect(spanishPlaceholder).toBeVisible();

		// Check the translations in the view mode

		await pageEditorPage.publishPage();

		await page.goto(
			`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
		);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {name: 'español-España'}),
			trigger: page.getByTitle('Select a Language', {exact: true}),
		});

		await expect(spanishLabel).toBeVisible();
		await expect(spanishHelpText).toBeVisible();
		await expect(spanishPlaceholder).toBeVisible();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {name: 'english-United States'}),
			trigger: page.getByTitle('Seleccionar un idioma', {exact: true}),
		});

		await expect(englishLabel).toBeVisible();
		await expect(englishHelpText).toBeVisible();
		await expect(englishPlaceholder).toBeVisible();
	});
});

test.describe('Edit mode form errors', () => {
	async function assertWarningMessage(
		headingMessage: string,
		page: Page,
		pageEditorPage: PageEditorPage,
		warningMessage: string
	) {
		await clickAndExpectToBeVisible({
			target: page.getByRole('heading', {name: headingMessage}),
			timeout: 2000,
			trigger: pageEditorPage.publishButton,
		});

		await expect(page.getByText(warningMessage)).toBeVisible();

		await clickAndExpectToBeHidden({
			target: page.getByRole('heading', {name: headingMessage}),
			timeout: 2000,
			trigger: page.getByRole('button', {name: 'Cancel'}),
		});
	}

	test(
		'Show a warning message when there is a form with unmapped input fragments, hidden required input fragments, missing required input fragments, hidden submit button or missing submit button',
		{tag: ['@LPS-150278', '@LPS-157998']},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Create a new object definition with a required field

			const objectDefinitionAPIClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {body: objectDefinition} =
				await objectDefinitionAPIClient.postObjectDefinition({
					active: true,
					externalReferenceCode: 'studentERC',
					label: {
						en_US: 'Student',
					},
					name: 'Student',
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
							required: true,
						},
						{
							DBType: ObjectField.DBTypeEnum.Integer,
							businessType: ObjectField.BusinessTypeEnum.Integer,
							externalReferenceCode: 'ageERC',
							indexed: true,
							indexedAsKeyword: false,
							indexedLanguageId: '',
							label: {
								en_US: 'Age',
							},
							name: 'age',
							required: false,
						},
					],
					pluralLabel: {
						en_US: 'Students',
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

			// Create a page with a Form fragment

			const textContainerId = getRandomString();
			const textContainerDefinition = getContainerDefinition({
				id: textContainerId,
			});

			const numericContainerId = getRandomString();
			const numericContainerDefinition = getContainerDefinition({
				id: numericContainerId,
			});

			const submitContainerId = getRandomString();
			const submitContainerDefinition = getContainerDefinition({
				id: submitContainerId,
			});

			const formId = getRandomString();
			const formDefinition = getFormContainerDefinition({
				id: formId,
				objectDefinitionClassName: objectDefinition.className,
				pageElements: [
					textContainerDefinition,
					numericContainerDefinition,
					submitContainerDefinition,
				],
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			// Publish and assert multiple wargning messages

			await assertWarningMessage(
				'Form Errors',
				page,
				pageEditorPage,
				'Submit button is hidden or missing. Your users may not be able to submit the form.'
			);

			await assertWarningMessage(
				'Form Errors',
				page,
				pageEditorPage,
				'One or more required fields are not mapped from the form. A form with missing required fields will not generate a valid entry.'
			);

			// Add text input fragment and map to required field

			await pageEditorPage.addFragment(
				'Form Components',
				'Textarea',
				page.locator(`.lfr-layout-structure-item-${textContainerId}`)
			);

			const textareaId = await pageEditorPage.getFragmentId('Textarea');

			await pageEditorPage.selectFragment(textareaId);

			await page.getByLabel('Field', {exact: true}).selectOption('Name*');

			await pageEditorPage.waitForChangesSaved();

			// Publish and check warning message for missing submit button

			await assertWarningMessage(
				'Submit Button Missing',
				page,
				pageEditorPage,
				'Student form has a hidden or missing submit button. If you continue, your users may not be able to submit the form. Are you sure you want to publish it?'
			);

			// Add submit button

			await pageEditorPage.addFragment(
				'Form Components',
				'Form Button',
				page.locator(`.lfr-layout-structure-item-${submitContainerId}`)
			);

			// Hide submit button container

			await pageEditorPage.hideFragment(submitContainerId);

			// Publish and check warning message for hide submit button

			await assertWarningMessage(
				'Submit Button Missing',
				page,
				pageEditorPage,
				'Student form has a hidden or missing submit button. If you continue, your users may not be able to submit the form. Are you sure you want to publish it?'
			);

			await waitForAlert(
				page,
				'The hidden fragment contained required fields. A form with missing required fields will not generate a valid entry.',
				{type: 'warning'}
			);

			// Show submit button container

			await pageEditorPage.goToSidebarTab('Browser');

			await page.locator(`[data-item-id="${submitContainerId}"]`).click();

			await page.getByLabel('Show Container').click();

			// Add unmapped numeric input fragment

			await pageEditorPage.addFragment(
				'Form Components',
				'Numeric',
				page.locator(`.lfr-layout-structure-item-${numericContainerId}`)
			);

			// Publish and check warning message for unmapped numeric input fragment

			await assertWarningMessage(
				'Fragment Mapping Missing',
				page,
				pageEditorPage,
				'Student form has some fragments not mapped to object fields. Unmapped fragments data will not be stored. Are you sure you want to publish?'
			);

			// Map numeric input fragment

			const numericId = await pageEditorPage.getFragmentId('Numeric');

			await pageEditorPage.selectFragment(numericId);

			await page.getByLabel('Field', {exact: true}).selectOption('Age');

			await pageEditorPage.waitForChangesSaved();

			// Hide text container

			await pageEditorPage.hideFragment(textContainerId);

			// Publish and check warning message for hidden required input fragment

			await assertWarningMessage(
				'Required Fields Hidden',
				page,
				pageEditorPage,
				'Student form contains one or more hidden fragments mapped to required fields. A form with missing required fields will not generate a valid entry. Are you sure you want to publish it?'
			);

			await waitForAlert(
				page,
				'The hidden fragment contained required fields. A form with missing required fields will not generate a valid entry.',
				{type: 'warning'}
			);
		}
	);

	test(
		'Show an error when there is no Submit Button',
		{tag: '@LPS-151754'},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Get the id of Lemon object from the site initializer

			const objectDefinitionApiClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {className: objectDefinitionClassName} = (
				await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
					getObjectERC('Lemon')
				)
			).body;

			// Create a page with a Form fragment

			const formId = getRandomString();

			const formDefinition = getFormContainerDefinition({
				id: formId,
				objectDefinitionClassName,
				pageElements: [],
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			// Publish and check it shows Submit Button error

			await clickAndExpectToBeVisible({
				target: page.getByText('Submit Button Missing'),
				timeout: 3000,
				trigger: pageEditorPage.publishButton,
			});
		}
	);

	test(
		'Show errors for empty steps and missing Next/Previous buttons',
		{tag: '@LPD-10727'},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Get the id of Lemon object from the site initializer

			const objectDefinitionApiClient =
				await apiHelpers.buildRestClient(ObjectDefinitionApi);

			const {className: objectDefinitionClassName} = (
				await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
					getObjectERC('Lemon')
				)
			).body;

			// Create a forms with three steps, forcing errors

			const nextButton = getFragmentDefinition({
				fragmentConfig: {
					type: 'next',
				},
				id: getRandomString(),
				key: 'INPUTS-submit-button',
			});

			const previousButton = getFragmentDefinition({
				fragmentConfig: {
					type: 'previous',
				},
				id: getRandomString(),
				key: 'INPUTS-submit-button',
			});

			const formDefinition = getFormContainerDefinition({
				id: getRandomString(),
				objectDefinitionClassName,
				steps: [[nextButton], [previousButton], [], [nextButton]],
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			// Publish and check errors

			await clickAndExpectToBeVisible({
				target: page.locator('.modal-title', {hasText: 'Form Errors'}),
				timeout: 3000,
				trigger: pageEditorPage.publishButton,
			});

			await expect(
				page.getByText('Next button is hidden or missing in Step 2')
			).toBeVisible();

			await expect(
				page.getByText('Previous button is hidden or missing in Step 4')
			).toBeVisible();

			await expect(page.getByText('Step 3 is empty')).toBeVisible();
		}
	);
});
