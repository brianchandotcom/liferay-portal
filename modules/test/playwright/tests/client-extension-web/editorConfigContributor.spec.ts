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

	await clientExtensionsPage.openItemActionsDropdown({text: sampleName1});

	await clientExtensionsPage.itemEditButton.click();

	const sampleName2 = 'Sample Name 2';

	await newEditorConfigContributorPage.nameInput.click();

	await newEditorConfigContributorPage.nameInput.fill(sampleName2);

	await newEditorConfigContributorPage.publishButton.click();

	await clientExtensionsPage.openItemActionsDropdown({text: sampleName2});

	clientExtensionsPage.page.on('dialog', (dialog) => dialog.accept());

	await clientExtensionsPage.itemDeleteButton.click();
});

/**
 * This test requires manual setup:
 *
 *     1. Run `gradle build` in /workspaces/liferay-sample-workspace/client-extensions/liferay-sample-editor-config-contributor
 *     2. Copy `liferay-sample-editor-config-contributor.zip` from /dist to @LIFERAY_HOME/osgi/client-extensions
 *
 * We are skipping the test until we automate these steps.
 */
test('Add a toolbar button to a CKEditor, by applying editor config contributor client extension', async ({
	apiHelpers,
	newEditorConfigContributorPage,
}) => {
	await apiHelpers.featureFlag.updateFeatureFlag('LPS-186870', true);

	await newEditorConfigContributorPage.goto();

	await expect(
		newEditorConfigContributorPage.descriptionEditable
	).toBeEditable();

	await expect(
		newEditorConfigContributorPage.aiCreatorEditorToolbarButton
	).toBeVisible();
});
