/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {documentLibraryPages} from '../../fixtures/documentLibraryPages';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';

const MOCKED_IMAGE_PATH =
	'USER_IMAGES_URL_https://images.freeimages.com/images/large-previews/83f/paris-1213603.jpg';

export const test = mergeTests(
	loginTest,
	featureFlagsTest({
		'LPD-10793': true,
	}),
	documentLibraryPages
);

test('Create AI Image option in Management Toolbar without API Key opens an alert', async ({
	documentLibraryPage,
	page,
}) => {
	await documentLibraryPage.goto();

	await documentLibraryPage.openCreateAIImage();

	await expect(page.getByText('Configure OpenAI')).toBeVisible();
});

test('Create AI Image option is hidden when disabled from Instance Settings', async ({
	documentLibraryPage,
	page,
}) => {
	await documentLibraryPage.disableAICreator();

	await documentLibraryPage.goto();

	await documentLibraryPage.openNewButton();

	await expect(
		page.getByRole('menuitem', {name: 'Create AI Image'})
	).not.toBeVisible();

	await documentLibraryPage.enableAICreator();
});

test('Can add images to DM when API Key is provided', async ({
	documentLibraryPage,
	page,
}) => {
	await documentLibraryPage.addGogoShellCommand(
		'scr:enable com.liferay.ai.creator.openai.web.internal.client.MockAICreatorOpenAIClient'
	);

	await documentLibraryPage.addApiKey();

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

	// TODO remove that generated image

	await documentLibraryPage.removeApiKey();

	await documentLibraryPage.addGogoShellCommand(
		'scr:disable com.liferay.ai.creator.openai.web.internal.client.MockAICreatorOpenAIClient'
	);
});
