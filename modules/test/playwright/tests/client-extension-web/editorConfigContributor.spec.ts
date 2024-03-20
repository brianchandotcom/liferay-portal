/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../layout-content-page-editor-web/utils/getWidgetDefinition';
import {clientExtensionsPageTest} from './fixtures/clientExtensionsPageTest';
import {editorSamplesPageTest} from './fixtures/editorSamplesPageTest';
import {newEditorConfigContributorPageTest} from './fixtures/newEditorConfigContributorPageTest';

export const test = mergeTests(
	apiHelpersTest,
	clientExtensionsPageTest,
	editorSamplesPageTest,
	featureFlagsTest({
		'LPS-178052': true,
		'LPS-186870': true,
	}),
	isolatedSiteTest,
	newEditorConfigContributorPageTest
);

test('Create, edit and delete editor config contributor client extension @LPS-186870', async ({
	clientExtensionsPage,
	newEditorConfigContributorPage,
}) => {
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

	await clientExtensionsPage.editClientExtension(sampleName1);

	const sampleName2 = 'Sample Name 2';

	await newEditorConfigContributorPage.nameInput.click();

	await newEditorConfigContributorPage.nameInput.fill(sampleName2);

	await newEditorConfigContributorPage.publishButton.click();

	await clientExtensionsPage.deleteClientExtension(sampleName2);
});

test('Add a toolbar button to a CKEditor, by applying editor config contributor client extension @LPS-186870', async ({
	newEditorConfigContributorPage,
}) => {
	await newEditorConfigContributorPage.goto();

	await expect(
		newEditorConfigContributorPage.descriptionEditable
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

		layout = await apiHelpers.headlessDelivery.createSitePage(
			site.id,
			getRandomString(),
			getPageDefinition([widgetDefinition])
		);
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
