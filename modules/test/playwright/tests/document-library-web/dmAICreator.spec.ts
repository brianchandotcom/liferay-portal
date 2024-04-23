/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {documentLibraryPagesTest} from '../../fixtures/documentLibraryPages.fixtures';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';

const MOCKED_IMAGE_PATH =
	'USER_IMAGES_URL_https://images.freeimages.com/images/large-previews/83f/paris-1213603.jpg';

export const test = mergeTests(
	loginTest(),
	featureFlagsTest({
		'LPD-10793': true,
	}),
	documentLibraryPagesTest
);

test('LPD-6717 Create AI Image option in Management Toolbar without API Key opens an alert', async ({
	documentLibraryPage,
	page,
}) => {
	await documentLibraryPage.goto();

	await documentLibraryPage.openCreateAIImage();

	await expect(page.getByText('Configure OpenAI')).toBeVisible();
});

test('LPD-6717 and LPD-6691 Create AI Image option is hidden when disabled from Instance Settings', async ({
	aiCreatorInstanceSettingsPage,
	documentLibraryPage,
	page,
}) => {
	await aiCreatorInstanceSettingsPage.disableDalleCreateImages();

	await documentLibraryPage.goto();

	await documentLibraryPage.openNewButton();

	await expect(
		page.getByRole('menuitem', {name: 'Create AI Image'})
	).not.toBeVisible();

	await aiCreatorInstanceSettingsPage.enableDalleCreateImages();
});

test.skip('LPD-6677 Can add images to DM when API Key is provided', async ({
	aiCreatorInstanceSettingsPage,
	documentLibraryPage,
	gogoShellPage,
	page,
}) => {
	await gogoShellPage.addCommand(
		'scr:enable com.liferay.ai.creator.openai.web.internal.client.MockAICreatorOpenAIClient'
	);

	await aiCreatorInstanceSettingsPage.addApiKey();

	await documentLibraryPage.goto();

	await documentLibraryPage.openCreateAIImage();

	await expect(page.getByText('Create AI Image')).toBeVisible();

	const createAIImageModalPage = page.frameLocator(
		'iframe[title="Create AI Image"]'
	);

	await createAIImageModalPage
		.getByPlaceholder('Write something...')
		.fill(MOCKED_IMAGE_PATH);

	await createAIImageModalPage.getByRole('button', {name: 'Create'}).click();

	await createAIImageModalPage.getByRole('checkbox').click();

	await createAIImageModalPage
		.getByRole('button', {name: 'Add Selected'})
		.click();

	await expect(
		page.getByRole('link').filter({hasText: 'AI-image-'})
	).toHaveCount(1);

	await documentLibraryPage.deleteAllFileEntries();

	await aiCreatorInstanceSettingsPage.removeApiKey();

	await gogoShellPage.addCommand(
		'scr:disable com.liferay.ai.creator.openai.web.internal.client.MockAICreatorOpenAIClient'
	);
});
