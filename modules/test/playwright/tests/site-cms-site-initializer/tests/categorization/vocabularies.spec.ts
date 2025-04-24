/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';
import {getRandomInt} from '../../../../utils/getRandomInt';
import getRandomString from '../../../../utils/getRandomString';
import {cmsPagesTest} from '../../fixtures/cmsPagesTest';

const test = mergeTests(
	cmsPagesTest,
	featureFlagsTest({
		'LPD-17564': {enabled: true},
	}),
	loginTest()
);

test(
	'Assert can delete vocabulary from dropdown actions ',
	{tag: '@LPD-32750LPD-32750LPD-32750LPD-32750LPD-32750LPD-32750'},
	async ({editVocabularyPage, page, vocabulariesPage}) => {
		editVocabularyPage.goto();

		const name = `Vocabulary${getRandomInt()}`;

		await editVocabularyPage.changeGeneralInfo({
			description: getRandomString(),
			name,
		});

		await clickAndExpectToBeVisible({
			target: page.getByText(
				`Success:${name} was published successfully.`
			),
			trigger: editVocabularyPage.saveButton,
		});

		await vocabulariesPage.execItemAction({
			action: 'Delete',
			filter: name,
		});

		await expect(
			page.getByRole('heading', {name: `Delete "${name}"`})
		).toBeVisible();

		await clickAndExpectToBeVisible({
			target: page.getByText(
				'Success:Your request completed successfully.'
			),
			trigger: page.getByRole('button', {name: 'Delete'}),
		});

		await expect(vocabulariesPage.getItem(name)).toBeHidden();
	}
);

test(
	'Assert can edit vocabulary from dropdown actions ',
	{tag: '@LPD-32750'},
	async ({editVocabularyPage, page, vocabulariesPage}) => {
		await editVocabularyPage.goto();

		const name = `Vocabulary${getRandomInt()}`;

		await editVocabularyPage.changeGeneralInfo({
			description: getRandomString(),
			name,
		});

		await clickAndExpectToBeVisible({
			target: page.getByText(
				`Success:${name} was published successfully.`
			),
			trigger: editVocabularyPage.saveButton,
		});

		await vocabulariesPage.execItemAction({
			action: 'Edit',
			filter: name,
		});

		await expect(page.getByText(`Edit ${name}`)).toBeVisible();
	}
);

test(
	'Assert can edit vocabulary permissions from dropdown actions ',
	{tag: '@LPD-32750'},
	async ({editVocabularyPage, page, vocabulariesPage}) => {
		editVocabularyPage.goto();

		const name = `Vocabulary${getRandomInt()}`;

		await editVocabularyPage.changeGeneralInfo({
			description: getRandomString(),
			name,
		});

		await clickAndExpectToBeVisible({
			target: page.getByText(
				`Success:${name} was published successfully.`
			),
			trigger: editVocabularyPage.saveButton,
		});

		await vocabulariesPage.execItemAction({
			action: 'Permissions',
			filter: name,
		});

		await expect(
			page.getByRole('heading', {name: 'Permissions'})
		).toBeVisible();
	}
);

test(
	'Can create and update vocabulary',
	{tag: '@LPD-32750'},
	async ({editVocabularyPage, page, vocabulariesPage}) => {
		editVocabularyPage.goto();

		const name = `Vocabulary${getRandomInt()}`;

		await editVocabularyPage.changeGeneralInfo({
			description: getRandomString(),
			name,
		});

		await clickAndExpectToBeVisible({
			target: page.getByText(
				`Success:${name} was published successfully.`
			),
			trigger: editVocabularyPage.saveButton,
		});

		const newVocabRow = vocabulariesPage.getItem(name);
		await expect(newVocabRow).toBeVisible();

		const newVocabualry = page.getByRole('link', {name});

		await newVocabualry.click();

		await expect(page.getByText(`Edit ${name}`)).toBeVisible();

		const newName = `Vocabulary${getRandomInt()}`;

		await editVocabularyPage.changeGeneralInfo({
			description: getRandomString(),
			name: newName,
		});

		await clickAndExpectToBeVisible({
			target: page.getByText(
				`Success:${newName} was updated successfully.`
			),
			trigger: editVocabularyPage.saveButton,
		});

		await expect(vocabulariesPage.getItem(newName)).toBeVisible();
	}
);

test(
	'Validate vocabulary inputs when saving',
	{tag: '@LPD-32750'},
	async ({editVocabularyPage, page}) => {
		editVocabularyPage.goto();

		// Check we can't publish an empty name

		await clickAndExpectToBeVisible({
			target: page.getByText('The Name field is required'),
			trigger: editVocabularyPage.saveButton,
		});
	}
);
