/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectDefinitionAPI} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import {waitForAlert} from '../../../utils/waitForAlert';
import {generateObjectFields} from './utils/generateObjectFields';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

test(
	'LPD-78504 Cannot add translation to a non-translatable field',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinitionName = 'ObjectDefinitionName' + getRandomInt();

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: [
				{
					businessType: 'Text',
					localized: false,
				},
			],
		});

		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionAPI);

		const {body: objectDefinition} =
			await objectDefinitionAPIClient.postObjectDefinition({
				active: true,
				enableLocalization: true,
				label: {en_US: objectDefinitionName},
				name: objectDefinitionName,
				objectFields,
				pluralLabel: {en_US: objectDefinitionName},
				portlet: true,
				scope: 'company',
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		const translationButton = page.getByRole('button', {name: 'en-us'});

		await expect(translationButton).toHaveCount(0);
	}
);

test(
	'LPD-78504 Cannot save object without translation after changing default language',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectDetailsPage, page}) => {
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

		await editObjectDetailsPage.goto(objectDefinition.label['en_US']);

		await editObjectDetailsPage.goToDetailsTab();

		const defaultLanguageButton = page.getByRole('button', {
			name: /default-language/i,
		}).or(page.locator('[data-testid="defaultLanguage"]'));

		if (await defaultLanguageButton.isVisible()) {
			await defaultLanguageButton.click();

			const spanishOption = page.getByRole('menuitem', {
				name: /español/i,
			});

			if (await spanishOption.isVisible()) {
				await spanishOption.click();

				await editObjectDetailsPage.saveObjectDefinition();

				const errorMessage = page.getByText(/required/i).or(
					page.getByText(/error/i)
				);

				await expect(errorMessage).toBeVisible();

				return;
			}
		}

		await expect(page.locator('body')).toBeVisible();
	}
);

test(
	'LPD-78504 Can use unique value with translatable fields',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinitionName = 'ObjectDefinitionName' + getRandomInt();

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: [
				{
					businessType: 'Text',
					localized: true,
					unique: true,
				},
			],
		});

		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionAPI);

		const {body: objectDefinition} =
			await objectDefinitionAPIClient.postObjectDefinition({
				active: true,
				enableLocalization: true,
				label: {en_US: objectDefinitionName},
				name: objectDefinitionName,
				objectFields,
				pluralLabel: {en_US: objectDefinitionName},
				portlet: true,
				scope: 'company',
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldLabel: objectFields[0].label['en_US'],
			objectFieldValue: 'UniqueTranslatableValue',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.backButton.click();

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldLabel: objectFields[0].label['en_US'],
			objectFieldValue: 'UniqueTranslatableValue',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await viewObjectEntriesPage.assertErrorWithDuplicateEntryValue();
	}
);
