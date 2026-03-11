/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinitionAPI,
	ObjectValidationRuleAPI,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {editObjectDefinitionPagesTest} from '../../../fixtures/editObjectDefinitionPagesTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {scriptManagementPagesTest} from '../../../fixtures/scriptManagementPagesTest';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';

const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	editObjectDefinitionPagesTest,
	isolatedSiteTest,
	loginTest(),
	objectPagesTest,
	scriptManagementPagesTest
);

test(
	'LPD-78504 Can add valid entry when validation is set to full validation',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, scriptManagementPage, viewObjectEntriesPage}) => {
		await scriptManagementPage.enableScriptManagementConfiguration();
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Decimal',
						DBType: 'Double',
						label: {en_US: 'Decimal'},
						name: 'decimal',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectValidationRuleAPIClient =
			await apiHelpers.buildRestClient(ObjectValidationRuleAPI);

		await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
			objectDefinition.externalReferenceCode,
			{
				active: true,
				engine: 'groovy',
				errorLabel: {
					en_US: 'This entry is not valid for decimal entry.',
				},
				name: {en_US: 'Custom validation'},
				objectValidationRuleSettings: [],
				script: 'invalidFields = (decimal != 13.579)',
				system: false,
			}
		);

		await apiHelpers.objectEntry.postObjectEntry(
			{decimal: 13.579},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('13.579')).toBeVisible();
	}
);

test(
	'LPD-78504 Updated validation only affects entries added after update',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, scriptManagementPage, viewObjectEntriesPage}) => {
		await scriptManagementPage.enableScriptManagementConfiguration();
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Field'},
						name: 'customField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectValidationRuleAPIClient =
			await apiHelpers.buildRestClient(ObjectValidationRuleAPI);

		const {body: validationRule} =
			await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
				objectDefinition.externalReferenceCode,
				{
					active: true,
					engine: 'groovy',
					errorLabel: {en_US: 'Please enter a valid entry.'},
					name: {en_US: 'Custom validation'},
					objectValidationRuleSettings: [],
					script: "invalidFields = (customField != 'Allowed Entry')",
					system: false,
				}
			);

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Allowed Entry'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Allowed Entry')).toBeVisible();

		await objectValidationRuleAPIClient.putObjectValidationRule(
			validationRule.id,
			{
				...validationRule,
				script: "invalidFields = (customField == 'Allowed Entry')",
			}
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page
			.getByLabel('Custom Field', {exact: true})
			.fill('Allowed Entry');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(
			page.getByText('Please enter a valid entry.')
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can deactivate validation and add previously invalid entry',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, scriptManagementPage, viewObjectEntriesPage}) => {
		await scriptManagementPage.enableScriptManagementConfiguration();
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'LongText',
						DBType: 'Clob',
						label: {en_US: 'Long Text'},
						name: 'longText',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectValidationRuleAPIClient =
			await apiHelpers.buildRestClient(ObjectValidationRuleAPI);

		const {body: validationRule} =
			await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
				objectDefinition.externalReferenceCode,
				{
					active: true,
					engine: 'groovy',
					errorLabel: {
						en_US: 'This entry is not valid for long text field.',
					},
					name: {en_US: 'Custom validation'},
					objectValidationRuleSettings: [],
					script: "invalidFields = (longText == 'Build Incredible Digital Experiences with Liferay DXP')",
					system: false,
				}
			);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page
			.getByLabel('Long Text', {exact: true})
			.fill(
				'Build Incredible Digital Experiences with Liferay DXP'
			);

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(
			page.getByText('Build Incredible Digital Experiences')
		).not.toBeVisible();

		await objectValidationRuleAPIClient.putObjectValidationRule(
			validationRule.id,
			{
				...validationRule,
				active: false,
			}
		);

		await apiHelpers.objectEntry.postObjectEntry(
			{
				longText:
					'Build Incredible Digital Experiences with Liferay DXP',
			},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(
			page.getByText('Build Incredible Digital Experiences')
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can define expression with Concat for text fields',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Text A'},
						name: 'customTextA',
						required: false,
					},
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Text B'},
						name: 'customTextB',
						required: false,
					},
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Text C'},
						name: 'customTextC',
						required: false,
					},
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Text D'},
						name: 'customTextD',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectValidationRuleAPIClient =
			await apiHelpers.buildRestClient(ObjectValidationRuleAPI);

		await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
			objectDefinition.externalReferenceCode,
			{
				active: true,
				engine: 'ddm',
				errorLabel: {en_US: 'Please enter a valid entry.'},
				name: {en_US: 'Custom validation'},
				objectValidationRuleSettings: [],
				script: 'customTextA == concat(customTextB, customTextC, customTextD)',
				system: false,
			}
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page
			.getByLabel('Custom Text A', {exact: true})
			.fill('Liferay Experience Cloud');
		await page
			.getByLabel('Custom Text B', {exact: true})
			.fill('Cloud');
		await page
			.getByLabel('Custom Text C', {exact: true})
			.fill(' Liferay');
		await page
			.getByLabel('Custom Text D', {exact: true})
			.fill(' Experience');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(
			page.getByText('Please enter a valid entry.')
		).toBeVisible();

		await viewObjectEntriesPage.backButton.click();

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page
			.getByLabel('Custom Text A', {exact: true})
			.fill('Liferay Digital Experience');
		await page
			.getByLabel('Custom Text B', {exact: true})
			.fill('Liferay');
		await page
			.getByLabel('Custom Text C', {exact: true})
			.fill(' Digital');
		await page
			.getByLabel('Custom Text D', {exact: true})
			.fill(' Experience');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(viewObjectEntriesPage.successMessage).toBeVisible();

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(
			page.getByText('Liferay Digital Experience')
		).toBeVisible();

		await expect(
			page.getByText('Liferay Experience Cloud')
		).not.toBeVisible();
	}
);

test(
	'LPD-78504 Can define expression with conditional logic for text, boolean, and integer fields',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		test.slow();

		const objectValidationRuleAPIClient =
			await apiHelpers.buildRestClient(ObjectValidationRuleAPI);

		await test.step('Test conditional logic with text fields', async () => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							businessType: 'Text',
							DBType: 'String',
							label: {en_US: 'Food'},
							name: 'customFood',
							required: false,
						},
						{
							businessType: 'Text',
							DBType: 'String',
							label: {en_US: 'Category'},
							name: 'customCategory',
							required: false,
						},
					] as any,
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
				objectDefinition.externalReferenceCode,
				{
					active: true,
					engine: 'ddm',
					errorLabel: {en_US: 'Please enter a valid entry.'},
					name: {en_US: 'Custom validation'},
					objectValidationRuleSettings: [],
					script: "equals(customFood, condition(customCategory == 'fruit', 'banana', 'other'))",
					system: false,
				}
			);

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page
				.getByLabel('Category', {exact: true})
				.fill('fruit');
			await page.getByLabel('Food', {exact: true}).fill('apple');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(
				page.getByText('Please enter a valid entry.')
			).toBeVisible();

			await viewObjectEntriesPage.backButton.click();

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page
				.getByLabel('Category', {exact: true})
				.fill('fruit');
			await page
				.getByLabel('Food', {exact: true})
				.fill('banana');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(
				viewObjectEntriesPage.successMessage
			).toBeVisible();

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await expect(page.getByText('banana')).toBeVisible();

			await expect(page.getByText('apple')).not.toBeVisible();
		});

		await test.step('Test conditional logic with boolean fields', async () => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							businessType: 'Boolean',
							DBType: 'Boolean',
							label: {en_US: 'Shoes'},
							name: 'customShoes',
							required: false,
						},
						{
							businessType: 'Boolean',
							DBType: 'Boolean',
							label: {en_US: 'Mark'},
							name: 'customMark',
							required: false,
						},
					] as any,
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
				objectDefinition.externalReferenceCode,
				{
					active: true,
					engine: 'ddm',
					errorLabel: {en_US: 'Please enter a valid entry.'},
					name: {en_US: 'Custom validation'},
					objectValidationRuleSettings: [],
					script: "equals(customShoes, condition(customMark == 'true', 'false', 'true'))",
					system: false,
				}
			);

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page.getByLabel('Mark').check();

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(
				page.getByText('Please enter a valid entry.')
			).toBeVisible();

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await expect(page.getByText('Yes')).not.toBeVisible();

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page.getByLabel('Shoes').check();
			await page.getByLabel('Mark').check();

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(
				viewObjectEntriesPage.successMessage
			).toBeVisible();

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await expect(
				page.getByText('Yes').first()
			).toBeVisible();
		});

		await test.step('Test conditional logic with integer fields', async () => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							businessType: 'Integer',
							DBType: 'Integer',
							label: {en_US: 'Validate'},
							name: 'customValidate',
							required: false,
						},
						{
							businessType: 'Integer',
							DBType: 'Integer',
							label: {en_US: 'IsHundred'},
							name: 'customIsHundred',
							required: false,
						},
					] as any,
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
				objectDefinition.externalReferenceCode,
				{
					active: true,
					engine: 'ddm',
					errorLabel: {en_US: 'Please enter a valid entry.'},
					name: {en_US: 'Custom validation'},
					objectValidationRuleSettings: [],
					script: 'customIsHundred == condition(customValidate == 100, 1234, 4321)',
					system: false,
				}
			);

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page
				.getByLabel('IsHundred', {exact: true})
				.fill('4321');
			await page
				.getByLabel('Validate', {exact: true})
				.fill('100');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(
				page.getByText('Please enter a valid entry.')
			).toBeVisible();

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page
				.getByLabel('IsHundred', {exact: true})
				.fill('1234');
			await page
				.getByLabel('Validate', {exact: true})
				.fill('100');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(
				viewObjectEntriesPage.successMessage
			).toBeVisible();

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await expect(page.getByText('1234')).toBeVisible();

			await expect(page.getByText('4321')).not.toBeVisible();
		});
	}
);

test(
	'LPD-78504 Can define expression with Contains and Does Not Contain',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Text'},
						name: 'customText',
						required: false,
					},
					{
						businessType: 'Integer',
						DBType: 'Integer',
						label: {en_US: 'Custom Integer'},
						name: 'customInteger',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectValidationRuleAPIClient =
			await apiHelpers.buildRestClient(ObjectValidationRuleAPI);

		const {body: validationRule} =
			await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
				objectDefinition.externalReferenceCode,
				{
					active: true,
					engine: 'ddm',
					errorLabel: {en_US: 'Please enter a valid entry.'},
					name: {en_US: 'Custom validation'},
					objectValidationRuleSettings: [],
					script: "contains(customText, 'Allowed Entry')",
					system: false,
				}
			);

		await test.step('Assert that Contains validation is working', async () => {
			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page
				.getByLabel('Custom Text', {exact: true})
				.fill('Decline Entry');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(
				page.getByText('Please enter a valid entry.')
			).toBeVisible();

			await viewObjectEntriesPage.backButton.click();

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page
				.getByLabel('Custom Text', {exact: true})
				.fill('Allowed Entry');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(
				viewObjectEntriesPage.successMessage
			).toBeVisible();

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await expect(
				page.getByText('Allowed Entry')
			).toBeVisible();

			await expect(
				page.getByText('Decline Entry')
			).not.toBeVisible();
		});

		await test.step('Edit validation to Does Not Contain', async () => {
			await objectValidationRuleAPIClient.putObjectValidationRule(
				validationRule.id,
				{
					...validationRule,
					script: "NOT(contains(customInteger, '2004'))",
				}
			);
		});

		await test.step('Assert that Does Not Contain validation is working', async () => {
			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page
				.getByLabel('Custom Integer', {exact: true})
				.fill('2004');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(
				page.getByText('Please enter a valid entry.')
			).toBeVisible();

			await viewObjectEntriesPage.backButton.click();

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page
				.getByLabel('Custom Integer', {exact: true})
				.fill('2022');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(
				viewObjectEntriesPage.successMessage
			).toBeVisible();

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await expect(page.getByText('2022')).toBeVisible();

			await expect(page.getByText('2004')).not.toBeVisible();
		});
	}
);

test(
	'LPD-78504 Can define expression with Future Dates and Past Dates',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Date',
						DBType: 'Date',
						label: {en_US: 'Custom Date'},
						name: 'customDate',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectValidationRuleAPIClient =
			await apiHelpers.buildRestClient(ObjectValidationRuleAPI);

		const {body: validationRule} =
			await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
				objectDefinition.externalReferenceCode,
				{
					active: true,
					engine: 'ddm',
					errorLabel: {en_US: 'Please enter a valid entry.'},
					name: {en_US: 'Custom validation'},
					objectValidationRuleSettings: [],
					script: "pastDates(customDate, '2022-06-01')",
					system: false,
				}
			);

		await test.step('Assert that Custom Validation with Past Dates is working', async () => {
			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page
				.getByLabel('Custom Date', {exact: true})
				.fill('01/01/2023');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(
				page.getByText('Please enter a valid entry.')
			).toBeVisible();

			await viewObjectEntriesPage.backButton.click();

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page
				.getByLabel('Custom Date', {exact: true})
				.fill('01/01/2022');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(
				viewObjectEntriesPage.successMessage
			).toBeVisible();

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await expect(page.getByText('Jan 1, 2022')).toBeVisible();

			await expect(
				page.getByText('Jan 1, 2023')
			).not.toBeVisible();
		});

		await test.step('Edit Custom Validation to use Future Dates', async () => {
			await objectValidationRuleAPIClient.putObjectValidationRule(
				validationRule.id,
				{
					...validationRule,
					script: "futureDates(customDate, '2022-06-01')",
				}
			);
		});

		await test.step('Assert that Custom Validation with Future Dates is working', async () => {
			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page
				.getByLabel('Custom Date', {exact: true})
				.fill('02/02/2022');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(
				page.getByText('Please enter a valid entry.')
			).toBeVisible();

			await viewObjectEntriesPage.backButton.click();

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page
				.getByLabel('Custom Date', {exact: true})
				.fill('02/02/2023');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(
				viewObjectEntriesPage.successMessage
			).toBeVisible();

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await expect(page.getByText('Feb 2, 2023')).toBeVisible();

			await expect(
				page.getByText('Feb 2, 2022')
			).not.toBeVisible();
		});
	}
);

test(
	'LPD-78504 Can define expression with Is an Email',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Text'},
						name: 'customText',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectValidationRuleAPIClient =
			await apiHelpers.buildRestClient(ObjectValidationRuleAPI);

		await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
			objectDefinition.externalReferenceCode,
			{
				active: true,
				engine: 'ddm',
				errorLabel: {en_US: 'Please enter a valid entry.'},
				name: {en_US: 'Custom validation'},
				objectValidationRuleSettings: [],
				script: 'isEmailAddress(customText)',
				system: false,
			}
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page
			.getByLabel('Custom Text', {exact: true})
			.fill('It is not an email');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(
			page.getByText('Please enter a valid entry.')
		).toBeVisible();

		await viewObjectEntriesPage.backButton.click();

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page
			.getByLabel('Custom Text', {exact: true})
			.fill('test@liferay.com');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(viewObjectEntriesPage.successMessage).toBeVisible();

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('test@liferay.com')).toBeVisible();

		await expect(
			page.getByText('It is not an email')
		).not.toBeVisible();
	}
);

test(
	'LPD-78504 Can define expression with Is Decimal or Is Integer',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Text'},
						name: 'customText',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectValidationRuleAPIClient =
			await apiHelpers.buildRestClient(ObjectValidationRuleAPI);

		const {body: validationRule} =
			await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
				objectDefinition.externalReferenceCode,
				{
					active: true,
					engine: 'ddm',
					errorLabel: {en_US: 'Please enter a valid entry.'},
					name: {en_US: 'Custom validation'},
					objectValidationRuleSettings: [],
					script: 'isInteger(customText)',
					system: false,
				}
			);

		await test.step('Assert that isInteger validation is working', async () => {
			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page
				.getByLabel('Custom Text', {exact: true})
				.fill('1.23');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(
				page.getByText('Please enter a valid entry.')
			).toBeVisible();

			await viewObjectEntriesPage.backButton.click();

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page
				.getByLabel('Custom Text', {exact: true})
				.fill('123');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(
				viewObjectEntriesPage.successMessage
			).toBeVisible();

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await expect(page.getByText('123')).toBeVisible();

			await expect(page.getByText('1.23')).not.toBeVisible();
		});

		await test.step('Edit validation to isDecimal', async () => {
			await objectValidationRuleAPIClient.putObjectValidationRule(
				validationRule.id,
				{
					...validationRule,
					script: 'isDecimal(customText)',
				}
			);
		});

		await test.step('Assert that isDecimal validation is working', async () => {
			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page
				.getByLabel('Custom Text', {exact: true})
				.fill('9,87');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(
				page.getByText('Please enter a valid entry.')
			).toBeVisible();

			await viewObjectEntriesPage.backButton.click();

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page
				.getByLabel('Custom Text', {exact: true})
				.fill('9.87');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(
				viewObjectEntriesPage.successMessage
			).toBeVisible();

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await expect(page.getByText('9.87')).toBeVisible();

			await expect(page.getByText('9,87')).not.toBeVisible();
		});
	}
);

test(
	'LPD-78504 Can define expression with Is Empty',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Text'},
						name: 'customText',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectValidationRuleAPIClient =
			await apiHelpers.buildRestClient(ObjectValidationRuleAPI);

		await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
			objectDefinition.externalReferenceCode,
			{
				active: true,
				engine: 'ddm',
				errorLabel: {en_US: 'Please enter a valid entry.'},
				name: {en_US: 'Custom validation'},
				objectValidationRuleSettings: [],
				script: 'isEmpty(customText)',
				system: false,
			}
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page
			.getByLabel('Custom Text', {exact: true})
			.fill('Invalid entry');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(
			page.getByText('Please enter a valid entry.')
		).toBeVisible();

		await viewObjectEntriesPage.backButton.click();

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(viewObjectEntriesPage.successMessage).toBeVisible();
	}
);

test(
	'LPD-78504 Can define expression with Is Equal To and Is Not Equal To',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Integer',
						DBType: 'Integer',
						label: {en_US: 'Custom Integer'},
						name: 'customInteger',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectValidationRuleAPIClient =
			await apiHelpers.buildRestClient(ObjectValidationRuleAPI);

		const {body: validationRule} =
			await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
				objectDefinition.externalReferenceCode,
				{
					active: true,
					engine: 'ddm',
					errorLabel: {en_US: 'Please enter a valid entry.'},
					name: {en_US: 'Custom validation'},
					objectValidationRuleSettings: [],
					script: 'customInteger == 12345',
					system: false,
				}
			);

		await test.step('Assert that Is Equal To validation is working', async () => {
			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page
				.getByLabel('Custom Integer', {exact: true})
				.fill('54321');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(
				page.getByText('Please enter a valid entry.')
			).toBeVisible();

			await viewObjectEntriesPage.backButton.click();

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page
				.getByLabel('Custom Integer', {exact: true})
				.fill('12345');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(
				viewObjectEntriesPage.successMessage
			).toBeVisible();

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await expect(page.getByText('12345')).toBeVisible();

			await expect(page.getByText('54321')).not.toBeVisible();
		});

		await test.step('Edit validation to Is Not Equal To', async () => {
			await objectValidationRuleAPIClient.putObjectValidationRule(
				validationRule.id,
				{
					...validationRule,
					script: 'customInteger != 13579',
				}
			);
		});

		await test.step('Assert that Is Not Equal To validation is working', async () => {
			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page
				.getByLabel('Custom Integer', {exact: true})
				.fill('13579');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(
				page.getByText('Please enter a valid entry.')
			).toBeVisible();

			await viewObjectEntriesPage.backButton.click();

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page
				.getByLabel('Custom Integer', {exact: true})
				.fill('24680');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(
				viewObjectEntriesPage.successMessage
			).toBeVisible();

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await expect(page.getByText('24680')).toBeVisible();

			await expect(page.getByText('13579')).not.toBeVisible();
		});
	}
);

test(
	'LPD-78504 Can define expression with Is Greater Than Or Equal To',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Integer',
						DBType: 'Integer',
						label: {en_US: 'Custom Integer'},
						name: 'customInteger',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectValidationRuleAPIClient =
			await apiHelpers.buildRestClient(ObjectValidationRuleAPI);

		await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
			objectDefinition.externalReferenceCode,
			{
				active: true,
				engine: 'ddm',
				errorLabel: {en_US: 'Please enter a valid entry.'},
				name: {en_US: 'Custom validation'},
				objectValidationRuleSettings: [],
				script: 'customInteger >= 5000',
				system: false,
			}
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page
			.getByLabel('Custom Integer', {exact: true})
			.fill('4999');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(
			page.getByText('Please enter a valid entry.')
		).toBeVisible();

		await viewObjectEntriesPage.backButton.click();

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page
			.getByLabel('Custom Integer', {exact: true})
			.fill('5000');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(viewObjectEntriesPage.successMessage).toBeVisible();

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page
			.getByLabel('Custom Integer', {exact: true})
			.fill('5001');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(viewObjectEntriesPage.successMessage).toBeVisible();

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('5000')).toBeVisible();

		await expect(page.getByText('5001')).toBeVisible();

		await expect(page.getByText('4999')).not.toBeVisible();
	}
);

test(
	'LPD-78504 Can define expression with Is Less Than Or Equal To',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Integer',
						DBType: 'Integer',
						label: {en_US: 'Custom Integer'},
						name: 'customInteger',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectValidationRuleAPIClient =
			await apiHelpers.buildRestClient(ObjectValidationRuleAPI);

		await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
			objectDefinition.externalReferenceCode,
			{
				active: true,
				engine: 'ddm',
				errorLabel: {en_US: 'Please enter a valid entry.'},
				name: {en_US: 'Custom validation'},
				objectValidationRuleSettings: [],
				script: 'customInteger <= 5000',
				system: false,
			}
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page
			.getByLabel('Custom Integer', {exact: true})
			.fill('5001');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(
			page.getByText('Please enter a valid entry.')
		).toBeVisible();

		await viewObjectEntriesPage.backButton.click();

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page
			.getByLabel('Custom Integer', {exact: true})
			.fill('5000');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(viewObjectEntriesPage.successMessage).toBeVisible();

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page
			.getByLabel('Custom Integer', {exact: true})
			.fill('4999');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(viewObjectEntriesPage.successMessage).toBeVisible();

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('5000')).toBeVisible();

		await expect(page.getByText('4999')).toBeVisible();

		await expect(page.getByText('5001')).not.toBeVisible();
	}
);

test(
	'LPD-78504 Can define expression with Is a URL',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Text'},
						name: 'customText',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectValidationRuleAPIClient =
			await apiHelpers.buildRestClient(ObjectValidationRuleAPI);

		await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
			objectDefinition.externalReferenceCode,
			{
				active: true,
				engine: 'ddm',
				errorLabel: {en_US: 'Please enter a valid entry.'},
				name: {en_US: 'Custom validation'},
				objectValidationRuleSettings: [],
				script: 'isURL(customText)',
				system: false,
			}
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page
			.getByLabel('Custom Text', {exact: true})
			.fill('It is not a URL');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(
			page.getByText('Please enter a valid entry.')
		).toBeVisible();

		await viewObjectEntriesPage.backButton.click();

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page
			.getByLabel('Custom Text', {exact: true})
			.fill('https://www.liferay.com/');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(viewObjectEntriesPage.successMessage).toBeVisible();

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(
			page.getByText('https://www.liferay.com/')
		).toBeVisible();

		await expect(
			page.getByText('It is not a URL')
		).not.toBeVisible();
	}
);

test(
	'LPD-78504 Can define expression with Match regex',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Text'},
						name: 'customText',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectValidationRuleAPIClient =
			await apiHelpers.buildRestClient(ObjectValidationRuleAPI);

		await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
			objectDefinition.externalReferenceCode,
			{
				active: true,
				engine: 'ddm',
				errorLabel: {en_US: 'Please enter a valid entry.'},
				name: {en_US: 'Custom validation'},
				objectValidationRuleSettings: [],
				script: "match(customText, '[A-Z]')",
				system: false,
			}
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page.getByLabel('Custom Text', {exact: true}).fill('z');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(
			page.getByText('Please enter a valid entry.')
		).toBeVisible();

		await viewObjectEntriesPage.backButton.click();

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page.getByLabel('Custom Text', {exact: true}).fill('Z');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(viewObjectEntriesPage.successMessage).toBeVisible();

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(
			page.getByRole('cell', {name: 'Z', exact: true})
		).toBeVisible();

		await expect(
			page.getByRole('cell', {name: 'z', exact: true})
		).not.toBeVisible();
	}
);

test(
	'LPD-78504 Can define expression with math operators for integer fields',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		test.slow();

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Integer',
						DBType: 'Integer',
						label: {en_US: 'Custom Integer A'},
						name: 'customIntegerA',
						required: false,
					},
					{
						businessType: 'Integer',
						DBType: 'Integer',
						label: {en_US: 'Custom Integer B'},
						name: 'customIntegerB',
						required: false,
					},
					{
						businessType: 'Integer',
						DBType: 'Integer',
						label: {en_US: 'Custom Integer C'},
						name: 'customIntegerC',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectValidationRuleAPIClient =
			await apiHelpers.buildRestClient(ObjectValidationRuleAPI);

		const {body: validationRule} =
			await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
				objectDefinition.externalReferenceCode,
				{
					active: true,
					engine: 'ddm',
					errorLabel: {en_US: 'Please enter a valid entry.'},
					name: {en_US: 'Custom validation'},
					objectValidationRuleSettings: [],
					script: 'customIntegerA == customIntegerB + customIntegerC',
					system: false,
				}
			);

		await test.step('Assert plus operator is working', async () => {
			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page.getByLabel('Custom Integer A', {exact: true}).fill('1100');
			await page.getByLabel('Custom Integer B', {exact: true}).fill('800');
			await page.getByLabel('Custom Integer C', {exact: true}).fill('400');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(page.getByText('Please enter a valid entry.')).toBeVisible();

			await viewObjectEntriesPage.backButton.click();

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page.getByLabel('Custom Integer A', {exact: true}).fill('5500');
			await page.getByLabel('Custom Integer B', {exact: true}).fill('2250');
			await page.getByLabel('Custom Integer C', {exact: true}).fill('3250');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(viewObjectEntriesPage.successMessage).toBeVisible();

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await expect(page.getByText('5500')).toBeVisible();

			await expect(page.getByText('1100')).not.toBeVisible();
		});

		await test.step('Edit to minus operator and assert', async () => {
			await objectValidationRuleAPIClient.putObjectValidationRule(
				validationRule.id,
				{...validationRule, script: 'customIntegerA == customIntegerB - customIntegerC'}
			);

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page.getByLabel('Custom Integer A', {exact: true}).fill('2000');
			await page.getByLabel('Custom Integer B', {exact: true}).fill('2500');
			await page.getByLabel('Custom Integer C', {exact: true}).fill('1000');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(page.getByText('Please enter a valid entry.')).toBeVisible();

			await viewObjectEntriesPage.backButton.click();

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page.getByLabel('Custom Integer A', {exact: true}).fill('6500');
			await page.getByLabel('Custom Integer B', {exact: true}).fill('7250');
			await page.getByLabel('Custom Integer C', {exact: true}).fill('750');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(viewObjectEntriesPage.successMessage).toBeVisible();

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await expect(page.getByText('6500')).toBeVisible();

			await expect(page.getByText('2000')).not.toBeVisible();
		});

		await test.step('Edit to multiply operator and assert', async () => {
			await objectValidationRuleAPIClient.putObjectValidationRule(
				validationRule.id,
				{...validationRule, script: 'customIntegerA == customIntegerB * customIntegerC'}
			);

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page.getByLabel('Custom Integer A', {exact: true}).fill('3000');
			await page.getByLabel('Custom Integer B', {exact: true}).fill('2000');
			await page.getByLabel('Custom Integer C', {exact: true}).fill('2');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(page.getByText('Please enter a valid entry.')).toBeVisible();

			await viewObjectEntriesPage.backButton.click();

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page.getByLabel('Custom Integer A', {exact: true}).fill('7500');
			await page.getByLabel('Custom Integer B', {exact: true}).fill('3750');
			await page.getByLabel('Custom Integer C', {exact: true}).fill('2');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(viewObjectEntriesPage.successMessage).toBeVisible();

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await expect(page.getByText('7500')).toBeVisible();

			await expect(page.getByText('3000')).not.toBeVisible();
		});

		await test.step('Edit to divide operator and assert', async () => {
			await objectValidationRuleAPIClient.putObjectValidationRule(
				validationRule.id,
				{...validationRule, script: 'customIntegerA == customIntegerB / customIntegerC'}
			);

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page.getByLabel('Custom Integer A', {exact: true}).fill('4000');
			await page.getByLabel('Custom Integer B', {exact: true}).fill('5000');
			await page.getByLabel('Custom Integer C', {exact: true}).fill('2');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(page.getByText('Please enter a valid entry.')).toBeVisible();

			await viewObjectEntriesPage.backButton.click();

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page.getByLabel('Custom Integer A', {exact: true}).fill('8500');
			await page.getByLabel('Custom Integer B', {exact: true}).fill('17000');
			await page.getByLabel('Custom Integer C', {exact: true}).fill('2');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await expect(viewObjectEntriesPage.successMessage).toBeVisible();

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await expect(page.getByText('8500')).toBeVisible();

			await expect(page.getByText('4000')).not.toBeVisible();
		});
	}
);

test(
	'LPD-78504 Can define expression with Range using Future Dates and Past Dates',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Date',
						DBType: 'Date',
						label: {en_US: 'Custom Date'},
						name: 'customDate',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectValidationRuleAPIClient =
			await apiHelpers.buildRestClient(ObjectValidationRuleAPI);

		await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
			objectDefinition.externalReferenceCode,
			{
				active: true,
				engine: 'ddm',
				errorLabel: {en_US: 'Please enter a valid entry.'},
				name: {en_US: 'Custom validation'},
				objectValidationRuleSettings: [],
				script: "futureDates(customDate, '2022-06-01') AND pastDates(customDate, '2022-06-30')",
				system: false,
			}
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page
			.getByLabel('Custom Date', {exact: true})
			.fill('05/30/2022');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(
			page.getByText('Please enter a valid entry.')
		).toBeVisible();

		await viewObjectEntriesPage.backButton.click();

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page
			.getByLabel('Custom Date', {exact: true})
			.fill('07/01/2022');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(
			page.getByText('Please enter a valid entry.')
		).toBeVisible();

		await viewObjectEntriesPage.backButton.click();

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page
			.getByLabel('Custom Date', {exact: true})
			.fill('06/15/2022');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(viewObjectEntriesPage.successMessage).toBeVisible();

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Jun 15, 2022')).toBeVisible();

		await expect(page.getByText('May 30, 2022')).not.toBeVisible();

		await expect(page.getByText('Jul 1, 2022')).not.toBeVisible();
	}
);

test(
	'LPD-78504 Can define expression with Sum for integer fields',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Integer',
						DBType: 'Integer',
						label: {en_US: 'Custom Integer A'},
						name: 'customIntegerA',
						required: false,
					},
					{
						businessType: 'Integer',
						DBType: 'Integer',
						label: {en_US: 'Custom Integer B'},
						name: 'customIntegerB',
						required: false,
					},
					{
						businessType: 'Integer',
						DBType: 'Integer',
						label: {en_US: 'Custom Integer C'},
						name: 'customIntegerC',
						required: false,
					},
					{
						businessType: 'Integer',
						DBType: 'Integer',
						label: {en_US: 'Custom Integer D'},
						name: 'customIntegerD',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectValidationRuleAPIClient =
			await apiHelpers.buildRestClient(ObjectValidationRuleAPI);

		await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
			objectDefinition.externalReferenceCode,
			{
				active: true,
				engine: 'ddm',
				errorLabel: {en_US: 'Please enter a valid entry.'},
				name: {en_US: 'Custom validation'},
				objectValidationRuleSettings: [],
				script: 'customIntegerA >= sum(customIntegerB, customIntegerC, customIntegerD)',
				system: false,
			}
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page.getByLabel('Custom Integer A', {exact: true}).fill('10001');
		await page.getByLabel('Custom Integer B', {exact: true}).fill('3001');
		await page.getByLabel('Custom Integer C', {exact: true}).fill('4000');
		await page.getByLabel('Custom Integer D', {exact: true}).fill('5000');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(
			page.getByText('Please enter a valid entry.')
		).toBeVisible();

		await viewObjectEntriesPage.backButton.click();

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page.getByLabel('Custom Integer A', {exact: true}).fill('10002');
		await page.getByLabel('Custom Integer B', {exact: true}).fill('2002');
		await page.getByLabel('Custom Integer C', {exact: true}).fill('3000');
		await page.getByLabel('Custom Integer D', {exact: true}).fill('4000');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(viewObjectEntriesPage.successMessage).toBeVisible();

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('10002')).toBeVisible();

		await expect(page.getByText('10001')).not.toBeVisible();
	}
);

test(
	'LPD-78504 Groovy validation is not active by default',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		modalAddObjectValidationPage,
		objectValidationsPage,
		page,
		scriptManagementPage,
	}) => {
		await scriptManagementPage.enableScriptManagementConfiguration();
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectValidationsPage.goto(objectDefinition.label['en_US']);

		await objectValidationsPage.addObjectValidationButton.click();

		await modalAddObjectValidationPage.fillObjectValidationInputs(
			'Custom Validation',
			'Groovy'
		);

		await page.reload();

		await expect(
			page.getByRole('cell', {name: 'No'})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can prevent entry creation via Groovy validation',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, scriptManagementPage, viewObjectEntriesPage}) => {
		await scriptManagementPage.enableScriptManagementConfiguration();
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Field'},
						name: 'customField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectValidationRuleAPIClient =
			await apiHelpers.buildRestClient(ObjectValidationRuleAPI);

		await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
			objectDefinition.externalReferenceCode,
			{
				active: true,
				engine: 'groovy',
				errorLabel: {en_US: 'Please enter a valid entry.'},
				name: {en_US: 'Custom validation'},
				objectValidationRuleSettings: [],
				script: "invalidFields = (customField == 'Decline Entry')",
				system: false,
			}
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page
			.getByLabel('Custom Field', {exact: true})
			.fill('Decline Entry');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(
			page.getByText('Please enter a valid entry.')
		).toBeVisible();

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Decline Entry')).not.toBeVisible();
	}
);

test(
	'LPD-78504 Can update validation and affect only new entries',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Text'},
						name: 'text',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectValidationRuleAPIClient =
			await apiHelpers.buildRestClient(ObjectValidationRuleAPI);

		const {body: validationRule} =
			await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
				objectDefinition.externalReferenceCode,
				{
					active: true,
					engine: 'ddm',
					errorLabel: {en_US: 'Please enter a valid entry.'},
					name: {en_US: 'Custom validation'},
					objectValidationRuleSettings: [],
					script: "NOT(contains(text, 'lazy'))",
					system: false,
				}
			);

		await apiHelpers.objectEntry.postObjectEntry(
			{text: 'Quick brown fox'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await objectValidationRuleAPIClient.putObjectValidationRule(
			validationRule.id,
			{
				...validationRule,
				errorLabel: {
					en_US: 'This entry is not valid for text entry, the field contains the word: dog.',
				},
				name: {en_US: 'Custom validation updated'},
				script: "NOT(contains(text, 'dog'))",
			}
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page.getByLabel('Text', {exact: true}).fill('Lazy dog');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(
			page.getByText(
				'This entry is not valid for text entry, the field contains the word: dog.'
			)
		).toBeVisible();

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Lazy dog')).not.toBeVisible();

		await expect(page.getByText('Quick brown fox')).toBeVisible();
	}
);

test(
	'LPD-78504 Entry update succeeds only when passing all validations',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, scriptManagementPage, viewObjectEntriesPage}) => {
		await scriptManagementPage.enableScriptManagementConfiguration();
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Field'},
						name: 'customField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectValidationRuleAPIClient =
			await apiHelpers.buildRestClient(ObjectValidationRuleAPI);

		await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
			objectDefinition.externalReferenceCode,
			{
				active: true,
				engine: 'groovy',
				errorLabel: {en_US: 'Please enter a valid entry.'},
				name: {en_US: 'Custom validation'},
				objectValidationRuleSettings: [],
				script: "invalidFields = (customField == 'Decline Entry')",
				system: false,
			}
		);

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Entry Test'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.frontendDatasetActions.click();
		await viewObjectEntriesPage.frontendDatasetViewAction.click();

		await page
			.getByLabel('Custom Field', {exact: true})
			.fill('Decline Entry');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Decline Entry')).not.toBeVisible();

		await viewObjectEntriesPage.frontendDatasetActions.click();
		await viewObjectEntriesPage.frontendDatasetViewAction.click();

		await page
			.getByLabel('Custom Field', {exact: true})
			.fill('Update Entry');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(viewObjectEntriesPage.successMessage).toBeVisible();

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Update Entry')).toBeVisible();
	}
);

test(
	'LPD-78504 Can see Basic Info tab on validation management',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		modalAddObjectValidationPage,
		objectValidationsPage,
		page,
		scriptManagementPage,
	}) => {
		await scriptManagementPage.enableScriptManagementConfiguration();
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectValidationsPage.goto(objectDefinition.label['en_US']);

		await objectValidationsPage.addObjectValidationButton.click();

		await modalAddObjectValidationPage.fillObjectValidationInputs(
			'Custom Validation',
			'Groovy'
		);

		await page
			.getByRole('link', {name: 'Custom Validation'})
			.click();

		await expect(
			objectValidationsPage.iframe.getByRole('tab', {
				name: 'Basic Info',
			})
		).toBeVisible();

		await expect(
			objectValidationsPage.iframe.getByRole('heading', {
				name: 'Basic Info',
			})
		).toBeVisible();

		await expect(
			objectValidationsPage.iframe.getByRole('heading', {
				name: 'Trigger Event',
			})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can see Conditions tab on validation management',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		modalAddObjectValidationPage,
		objectValidationsPage,
		page,
		scriptManagementPage,
	}) => {
		await scriptManagementPage.enableScriptManagementConfiguration();
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectValidationsPage.goto(objectDefinition.label['en_US']);

		await objectValidationsPage.addObjectValidationButton.click();

		await modalAddObjectValidationPage.fillObjectValidationInputs(
			'Custom Validation',
			'Groovy'
		);

		await page
			.getByRole('link', {name: 'Custom Validation'})
			.click();

		await objectValidationsPage.iframe
			.getByRole('tab', {name: 'Conditions'})
			.click();

		await expect(
			objectValidationsPage.iframe.getByRole('heading', {
				name: 'Groovy',
			})
		).toBeVisible();

		await expect(
			objectValidationsPage.iframe.getByRole('heading', {
				name: 'Error Message',
			})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can validate Expression Builder syntax when creating actions',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectActionPage,
		page,
		viewObjectActionsPage,
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await viewObjectActionsPage.goto(
			objectDefinition.label['en_US']
		);

		await viewObjectActionsPage.openObjectActionSidePanel();

		await editObjectActionPage.actionLabelInput.fill('Custom Action');

		await editObjectActionPage.actionBuilderTab.click();

		await editObjectActionPage.inputWhenCombo.click();
		await editObjectActionPage.iframeLocator
			.getByRole('option', {name: 'On After Add'})
			.click();

		await editObjectActionPage.fillExpression('#');

		await editObjectActionPage.inputThenCombo.click();
		await editObjectActionPage.iframeLocator
			.getByRole('option', {name: 'Webhook'})
			.click();

		await editObjectActionPage.iframeLocator
			.locator('input[name="url"]')
			.fill('www.liferay.com');

		await editObjectActionPage.saveButton.click();

		await expect(page.getByText('Syntax Error')).toBeVisible();
	}
);

test(
	'LPD-78504 Can validate Expression Builder syntax when creating scheduled action',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectActionPage,
		page,
		viewObjectActionsPage,
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Field'},
						name: 'customField',
						required: true,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await viewObjectActionsPage.goto(
			objectDefinition.label['en_US']
		);

		await viewObjectActionsPage.openObjectActionSidePanel();

		await editObjectActionPage.actionLabelInput.fill('Custom Action');

		await editObjectActionPage.actionBuilderTab.click();

		await editObjectActionPage.inputWhenCombo.click();
		await editObjectActionPage.iframeLocator
			.getByRole('option', {name: 'On After Add'})
			.click();

		await editObjectActionPage.inputThenCombo.click();
		await editObjectActionPage.iframeLocator
			.getByRole('option', {name: 'Add an Object Entry'})
			.click();

		await editObjectActionPage.iframeLocator
			.getByRole('combobox')
			.getByText('Choose an Object')
			.click();
		await editObjectActionPage.iframeLocator
			.getByRole('option', {
				name: objectDefinition.label['en_US'],
			})
			.click();

		await editObjectActionPage.iframeLocator
			.getByPlaceholder('Input a value or create an expression.')
			.fill('#');

		await editObjectActionPage.saveButton.click();

		await expect(page.getByText('Syntax Error')).toBeVisible();
	}
);

test(
	'LPD-78504 Can validate Groovy syntax when creating actions',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectActionPage,
		page,
		scriptManagementPage,
		viewObjectActionsPage,
		viewObjectEntriesPage,
	}) => {
		await scriptManagementPage.enableScriptManagementConfiguration();

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Integer',
						DBType: 'Integer',
						label: {en_US: 'Custom Integer'},
						name: 'customInteger',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await viewObjectActionsPage.goto(
			objectDefinition.label['en_US']
		);

		await viewObjectActionsPage.openObjectActionSidePanel();

		await editObjectActionPage.actionLabelInput.fill('Custom Action');

		await editObjectActionPage.actionBuilderTab.click();

		await editObjectActionPage.inputWhenCombo.click();
		await editObjectActionPage.iframeLocator
			.getByRole('option', {name: 'On After Add'})
			.click();

		await editObjectActionPage.inputThenCombo.click();
		await editObjectActionPage.iframeLocator
			.getByRole('option', {name: 'Groovy Script'})
			.click();

		await editObjectActionPage.iframeLocator
			.locator('.CodeMirror-code')
			.click();

		await page.keyboard.type(
			'invalidFields = (customInteger < 18)'
		);

		await editObjectActionPage.saveButton.click();

		await page.waitForLoadState('networkidle');

		await expect(
			page.getByRole('cell', {name: 'Yes'})
		).toBeVisible();

		await expect(
			page.getByRole('cell', {name: 'Never Ran'})
		).toBeVisible();

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page
			.getByLabel('Custom Integer', {exact: true})
			.fill('123456789');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(viewObjectEntriesPage.successMessage).toBeVisible();

		await viewObjectActionsPage.goto(
			objectDefinition.label['en_US']
		);

		await expect(
			page.getByRole('cell', {name: 'Success'})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can validate Groovy syntax when creating scheduled action',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectActionPage,
		page,
		scriptManagementPage,
		viewObjectActionsPage,
	}) => {
		await scriptManagementPage.enableScriptManagementConfiguration();

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await viewObjectActionsPage.goto(
			objectDefinition.label['en_US']
		);

		await viewObjectActionsPage.openObjectActionSidePanel();

		await editObjectActionPage.actionLabelInput.fill('Custom Action');

		await editObjectActionPage.actionBuilderTab.click();

		await editObjectActionPage.inputWhenCombo.click();
		await editObjectActionPage.iframeLocator
			.getByRole('option', {name: 'On After Add'})
			.click();

		await editObjectActionPage.inputThenCombo.click();
		await editObjectActionPage.iframeLocator
			.getByRole('option', {name: 'Groovy Script'})
			.click();

		await editObjectActionPage.iframeLocator
			.locator('.CodeMirror-code')
			.click();

		await page.keyboard.type(
			"if = (customField == 'Syntax is incorrect')"
		);

		await editObjectActionPage.saveButton.click();

		await expect(page.getByText('Syntax Error')).toBeVisible();
	}
);

test(
	'LPD-78504 Can validate system object with Expression Builder',
	{tag: '@LPD-78504'},
	async ({apiHelpers, objectValidationsPage, page}) => {
		await objectValidationsPage.viewObjectDefinitionsPage.goto();

		await page.getByPlaceholder('Search').fill('User');
		await page.keyboard.press('Enter');

		await page
			.getByRole('link', {exact: true, name: 'User'})
			.click();

		await objectValidationsPage.validationTabItem.click();

		await expect(
			objectValidationsPage.validationTabItem
		).toBeVisible();
	}
);

test(
	'LPD-78504 Empty state is displayed when no validations are added',
	{tag: '@LPD-78504'},
	async ({apiHelpers, objectValidationsPage, page}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectValidationsPage.goto(objectDefinition.label['en_US']);

		await expect(page.getByText('No Results Found')).toBeVisible();
	}
);

test(
	'LPD-78504 Can view localized input changed on validation',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		modalAddObjectValidationPage,
		objectValidationsPage,
		page,
		scriptManagementPage,
	}) => {
		await scriptManagementPage.enableScriptManagementConfiguration();
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectValidationsPage.goto(objectDefinition.label['en_US']);

		await objectValidationsPage.addObjectValidationButton.click();

		await modalAddObjectValidationPage.fillObjectValidationInputs(
			'Custom Validation',
			'Groovy'
		);

		await page
			.getByRole('link', {name: 'Custom Validation'})
			.click();

		await expect(
			objectValidationsPage.iframe.getByRole('tab', {
				name: 'Basic Info',
			})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Error message is displayed when entry fails all validations',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, scriptManagementPage, viewObjectEntriesPage}) => {
		await scriptManagementPage.enableScriptManagementConfiguration();
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Field'},
						name: 'customField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectValidationRuleAPIClient =
			await apiHelpers.buildRestClient(ObjectValidationRuleAPI);

		await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
			objectDefinition.externalReferenceCode,
			{
				active: true,
				engine: 'groovy',
				errorLabel: {en_US: 'Please enter a valid entry.'},
				name: {en_US: 'Custom validation'},
				objectValidationRuleSettings: [],
				script: "invalidFields = (customField == 'Invalid Entry')",
				system: false,
			}
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page
			.getByLabel('Custom Field', {exact: true})
			.fill('Invalid Entry');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(
			page.getByText('Please enter a valid entry.')
		).toBeVisible();
	}
);

test(
	'LPD-78504 Specific error is shown when Groovy syntax is incorrect',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		modalAddObjectValidationPage,
		objectValidationsPage,
		page,
		scriptManagementPage,
	}) => {
		await scriptManagementPage.enableScriptManagementConfiguration();
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Field'},
						name: 'customField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectValidationsPage.goto(objectDefinition.label['en_US']);

		await objectValidationsPage.addObjectValidationButton.click();

		await modalAddObjectValidationPage.fillObjectValidationInputs(
			'Custom Validation',
			'Groovy'
		);

		await page
			.getByRole('link', {name: 'Custom Validation'})
			.click();

		await objectValidationsPage.iframe
			.getByRole('tab', {name: 'Conditions'})
			.click();

		await expect(
			objectValidationsPage.iframe.getByRole('heading', {
				name: 'Groovy',
			})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Validation tab is available on object definition',
	{tag: '@LPD-78504'},
	async ({apiHelpers, objectValidationsPage, page}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectValidationsPage.goto(objectDefinition.label['en_US']);

		await expect(
			objectValidationsPage.validationTabItem
		).toBeVisible();
	}
);

test(
	'LPD-78504 Error message field is required on validation',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		modalAddObjectValidationPage,
		objectValidationsPage,
		page,
		scriptManagementPage,
	}) => {
		await scriptManagementPage.enableScriptManagementConfiguration();
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectValidationsPage.goto(objectDefinition.label['en_US']);

		await objectValidationsPage.addObjectValidationButton.click();

		await modalAddObjectValidationPage.fillObjectValidationInputs(
			'Custom Validation',
			'Groovy'
		);

		await page
			.getByRole('link', {name: 'Custom Validation'})
			.click();

		await objectValidationsPage.iframe
			.getByRole('tab', {name: 'Conditions'})
			.click();

		await expect(
			objectValidationsPage.iframe.getByRole('heading', {
				name: 'Error Message',
			})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Label field is required when adding a new validation',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		modalAddObjectValidationPage,
		objectValidationsPage,
		page,
		scriptManagementPage,
	}) => {
		await scriptManagementPage.enableScriptManagementConfiguration();
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectValidationsPage.goto(objectDefinition.label['en_US']);

		await objectValidationsPage.addObjectValidationButton.click();

		await modalAddObjectValidationPage.fillObjectValidationInputs(
			'',
			'Groovy'
		);

		await expect(page.getByText('Required')).toBeVisible();
	}
);
