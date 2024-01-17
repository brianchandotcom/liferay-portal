/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {clientExtensionsPageTest} from './fixtures/clientExtensionsPageTest';
import {newEditorConfigContributorPageTest} from './fixtures/newEditorConfigContributorPageTest';

export const test = mergeTests(
	apiHelpersTest,
	clientExtensionsPageTest,
	newEditorConfigContributorPageTest
);

test('Editor config contributor client extension is disabled if FF is set to false', async ({
	apiHelpers,
	clientExtensionsPage,
}) => {
	await apiHelpers.featureFlag.updateFeatureFlag('LPS-186870', false);

	await clientExtensionsPage.goto();

	await clientExtensionsPage.newClientExtensionButton.click();

	await expect(
		clientExtensionsPage.editorConfigContributorMenuItem
	).not.toBeAttached();
});

test('Create, edit and delete editor config contributor client extension', async ({
	apiHelpers,
	clientExtensionsPage,
	newEditorConfigContributorPage,
}) => {
	await apiHelpers.featureFlag.updateFeatureFlag('LPS-186870', true);

	await clientExtensionsPage.goto();

	await clientExtensionsPage.newClientExtensionButton.click();

	await clientExtensionsPage.editorConfigContributorMenuItem.click();

	const sampleName1 = 'Sample Name 1';

	await newEditorConfigContributorPage.nameInput.fill(sampleName1);

	await newEditorConfigContributorPage.descriptionEditable.isEditable();

	await newEditorConfigContributorPage.descriptionEditable.fill(
		'Sample Description'
	);

	await newEditorConfigContributorPage.urlInput.fill(
		'https://www.liferay.com'
	);

	await newEditorConfigContributorPage.portletNamesInput.fill(
		'Sample Portlet Name'
	);

	await newEditorConfigContributorPage.editorNamesInput.fill(
		'Sample Editor Names'
	);

	await newEditorConfigContributorPage.editorConfigKeysInput.fill(
		'Sample Editor Config Keys'
	);

	await newEditorConfigContributorPage.publishButton.click();

	await expect(
		clientExtensionsPage.page.getByText(sampleName1)
	).toBeVisible();

	await clientExtensionsPage.itemActionsToggleButton.click();

	await clientExtensionsPage.itemEditButton.click();

	const sampleName2 = 'Sample Name 2';

	await newEditorConfigContributorPage.nameInput.click();

	await newEditorConfigContributorPage.nameInput.fill(sampleName2);

	await newEditorConfigContributorPage.publishButton.click();

	await expect(
		clientExtensionsPage.page.getByText(sampleName2)
	).toBeVisible();

	await clientExtensionsPage.itemActionsToggleButton.click();

	clientExtensionsPage.page.on('dialog', (dialog) => dialog.accept());

	await clientExtensionsPage.itemDeleteButton.click();
});
