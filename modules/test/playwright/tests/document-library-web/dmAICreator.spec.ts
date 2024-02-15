/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {documentLibraryPages} from '../../fixtures/documentLibraryPages';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';

const testFeatureFlagsEnabled = mergeTests(
	loginTest,
	featureFlagsTest({
		'LPD-10793': true,
	}),
	documentLibraryPages
);

testFeatureFlagsEnabled(
	'Create AI Image option in Management Toolbar without API Key opens an alert',
	async ({documentLibraryPage, page}) => {
		await documentLibraryPage.goto();

		await documentLibraryPage.new();

		await page
			.getByRole('menuitem', {
				name: 'Create AI Image',
			})
			.click();

		await expect(page.getByText('Configure OpenAI')).toBeVisible();
	}
);

testFeatureFlagsEnabled(
	'Create AI Image option is hidden when disabled from Instance Settings',
	async ({documentLibraryPage, page}) => {
		await documentLibraryPage.disableAICreator();

		await documentLibraryPage.goto();

		await documentLibraryPage.new();

		await expect(
			page.getByRole('menuitem', {name: 'Create AI Image'})
		).not.toBeVisible();

		await documentLibraryPage.enableAICreator();
	}
);

testFeatureFlagsEnabled(
	'Create AI Image opens a modal when API Key is provided',
	async ({documentLibraryPage, page}) => {
		await documentLibraryPage.addGogoShellCommand('scr:enable com.liferay.ai.creator.openai.web.internal.client.MockAICreatorOpenAIClient');

		await documentLibraryPage.addApiKey();

		await documentLibraryPage.goto();

		await documentLibraryPage.new();

		await page
			.getByRole('menuitem', {
				name: 'Create AI Image',
			})
			.click();

		await expect(page.getByText('Create AI Image')).toBeVisible();

		await documentLibraryPage.addGogoShellCommand('scr:disable com.liferay.ai.creator.openai.web.internal.client.MockAICreatorOpenAIClient');
	}
);
