/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-187854': true,
	}),
	loginTest(),
	objectPagesTest
);

let objectDefinition: ObjectDefinition;

test.beforeEach(async ({apiHelpers}) => {
	const newObjectDefinition =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			status: {code: 0},
		});

	objectDefinition = newObjectDefinition;
});

test.afterEach(async ({apiHelpers}) => {
	await apiHelpers.objectAdmin.deleteObjectDefinition(objectDefinition.id);
});

test('can create and use a object unique composite key validation', async ({
	apiHelpers,
	editObjectValidationPage,
	modalAddObjectValidationPage,
	objectValidationsFDSPage,
	page,
	viewObjectDefinitionsPage,
	viewObjectEntriesPage,
}) => {
	await apiHelpers.objectAdmin.postObjectFieldByExternalReferenceCode(
		objectDefinition.externalReferenceCode,
		{
			DBType: 'Integer',
			businessType: 'Integer',
			externalReferenceCode: 'integerField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'integerField'},
			listTypeDefinitionId: 0,
			localized: false,
			name: 'integerField',
			readOnly: 'false',
			required: false,
			state: false,
			system: false,
		}
	);

	viewObjectDefinitionsPage.goto();

	await expect(
		page.locator(`a:has-text("${objectDefinition.label['en_US']}")`)
	).toBeVisible();

	viewObjectDefinitionsPage.editObjectDefinitionFDSLink(
		objectDefinition.label['en_US']
	);

	objectValidationsFDSPage.goto();

	await objectValidationsFDSPage.addObjectValidationButton.click();

	const objectValidationLabel =
		'UniqueCompositeKeyValidation' + getRandomInt();

	await modalAddObjectValidationPage.fillObjectValidationInputs(
		objectValidationLabel,
		'Composite Key'
	);

	const newValidationLink = page.getByText(objectValidationLabel);

	await newValidationLink.click();

	await editObjectValidationPage.uniqueCompositeKeyTab.click();

	await editObjectValidationPage.addFieldsButton.click();

	await editObjectValidationPage.clickSelectAllFields();

	await editObjectValidationPage.saveObjectValidationButton.click();

	const applicationName = 'c/' + objectDefinition.name.toLowerCase() + 's';

	const textObjectEntry = {
		textField: 'entry',
	};

	await apiHelpers.objectEntry.postObjectEntry(
		textObjectEntry,
		applicationName
	);

	await viewObjectEntriesPage.goto(objectDefinition.id);

	await viewObjectEntriesPage.addObjectEntryButton.click();

	await viewObjectEntriesPage.fillObjectEntry('integerField', '0');
	await viewObjectEntriesPage.fillObjectEntry('textField', 'entry');

	await viewObjectEntriesPage.saveObjectEntryButton.click();
	await viewObjectEntriesPage.assertErrorWithDuplicateEntryValue();

	await viewObjectEntriesPage.backButton.click();

	await viewObjectEntriesPage.addObjectEntryButton.click();

	await viewObjectEntriesPage.fillObjectEntry('integerField', '123');
	await viewObjectEntriesPage.fillObjectEntry('textField', 'entry 2');

	await viewObjectEntriesPage.saveObjectEntryButton.click();
	await expect(viewObjectEntriesPage.successMessage).toBeVisible();
});
