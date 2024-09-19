/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../layout-content-page-editor-web/utils/getWidgetDefinition';
import {clientExtensionsPageTest} from './fixtures/clientExtensionsPageTest';
import {editEditorConfigContributorPageTest} from './fixtures/editEditorConfigContributorPageTest';
import {editorSamplesPageTest} from './fixtures/editorSamplesPageTest';

export const test = mergeTests(
	apiHelpersTest,
	clientExtensionsPageTest,
	editEditorConfigContributorPageTest,
	editorSamplesPageTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	journalPagesTest
);

test('Create, edit and delete editor config contributor client extension @LPS-186870', async ({
	clientExtensionsPage,
	editEditorConfigContributorPage,
}) => {
	await editEditorConfigContributorPage.goto();

	const sampleName1 = getRandomString();

	await editEditorConfigContributorPage.nameInput.fill(sampleName1);

	await editEditorConfigContributorPage.descriptionContentEditable.isEditable();

	await editEditorConfigContributorPage.descriptionContentEditable.fill(
		'Sample Description'
	);

	await editEditorConfigContributorPage.urlInput.fill(
		'https://www.liferay.com'
	);

	await editEditorConfigContributorPage.portletNamesInput.fill(
		'Sample Portlet Name'
	);

	await editEditorConfigContributorPage.editorNamesInput.fill(
		'Sample Editor Names'
	);

	await editEditorConfigContributorPage.editorConfigKeysInput.fill(
		'Sample Editor Config Keys'
	);

	await editEditorConfigContributorPage.publish();

	await clientExtensionsPage.editClientExtension(sampleName1);

	// Synchronize test to avoid flakiness

	expect(editEditorConfigContributorPage.descriptionCKEditor).toBeVisible();

	const sampleName2 = getRandomString();

	await editEditorConfigContributorPage.nameInput.fill(sampleName2);

	await editEditorConfigContributorPage.publish();

	await editEditorConfigContributorPage.clientExtensionsPage.deleteClientExtension(
		sampleName2
	);
});

test('Add a toolbar button to a CKEditor, by applying editor config contributor client extension @LPS-186870', async ({
	editEditorConfigContributorPage: newEditorConfigContributorPage,
}) => {
	await newEditorConfigContributorPage.goto();

	await expect(
		newEditorConfigContributorPage.descriptionContentEditable
	).toBeEditable();

	await expect(
		newEditorConfigContributorPage.aiCreatorEditorToolbarButton
	).toBeVisible();
});

test('Add a toolbar button to an Alloy Editor @LPD-11056', async ({
	apiHelpers,
	editorSamplesPage,
	page,
	site,
}) => {
	let layout: Layout;

	await test.step('Create page with CKEditor sample widget', async () => {
		const widgetDefinition = getWidgetDefinition({
			id: getRandomString(),
			widgetName:
				'com_liferay_editor_ckeditor_sample_web_internal_portlet_CKEditorSamplePortlet',
		});

		layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([widgetDefinition]),
			siteId: site.id,
			title: getRandomString(),
		});
	});

	await test.step('Navigate to the page with Alloy Editor sample', async () => {
		await page.goto(
			`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
		);

		await expect(
			editorSamplesPage.balloonEditorContainer.getByText('Lorem ipsum')
		).toBeInViewport();

		await editorSamplesPage.selectTab({tabLabel: 'Alloy'});

		await expect(
			editorSamplesPage.alloyEditorContainer.getByText('Lorem ipsum')
		).toBeInViewport();
	});

	await test.step('Check if client extenstion is applied', async () => {
		await editorSamplesPage.alloyEditorContainer
			.getByText('Lorem ipsum')
			.selectText();

		await expect(
			editorSamplesPage.alloyEditorToolbarContainer.getByTitle(
				'Insert Video'
			)
		).toBeInViewport();
	});
});

test('CKEditor is still usable after deploying Client Extension @LPD-31017', async ({
	editEditorConfigContributorPage: newEditorConfigContributorPage,
	journalEditArticlePage,
	page,
}) => {
	await test.step('Create client extension for rich_text editors', async () => {
		await newEditorConfigContributorPage.goto();

		const nameField = page.getByLabel('Name Required', {exact: true});
		const urlField = page.getByLabel('JavaScript URL Required', {
			exact: true,
		});
		const editorConfigKeyField = page.getByLabel('Editor Config Key', {
			exact: true,
		});

		await nameField.click();
		await nameField.fill(getRandomString());

		await urlField.click();
		await urlField.fill(getRandomString());

		await editorConfigKeyField.click();
		await editorConfigKeyField.fill('rich_text');

		await page.getByRole('button', {name: 'Publish'}).click();
	});

	await test.step('Try to create a new Web Content Article after CX has been applied', async () => {
		await journalEditArticlePage.goto();

		const editorTextBox = page
			.frameLocator(
				'internal:role=textbox[name="Content"i] >> iframe[title="editor"]'
			)
			.getByRole('textbox');

		await editorTextBox.click();
		await editorTextBox.fill('LPD-31017');

		await expect(editorTextBox).toHaveText('LPD-31017');
	});
});
