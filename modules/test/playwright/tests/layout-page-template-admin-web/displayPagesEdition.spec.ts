/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {displayPageTemplatesPagesTest} from '../../fixtures/displayPageTemplatesPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pageManagementSiteTest} from '../../fixtures/pageManagementSiteTest';
import {ApiHelpers} from '../../helpers/ApiHelpers';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../utils/getRandomString';
import {getWebContentStructureId} from '../../utils/structured-content/getBasicWebContentStructureId';
import {pagesPagesTest} from '../layout-admin-web/fixtures/pagesPagesTest';
import {
	ANIMAL_01_FRIENDLY_URL,
	ANIMAL_DDM_STRUCTURE_KEY,
} from '../setup/page-management-site/constants/animals';

const test = mergeTests(
	apiHelpersTest,
	displayPageTemplatesPagesTest,
	pageEditorPagesTest,
	loginTest(),
	pageManagementSiteTest,
	pagesPagesTest
);

async function addDefaultAnimalDisplayPageTemplate(
	apiHelpers: ApiHelpers,
	displayPageTemplateName: string,
	site: Site
) {
	const className = await apiHelpers.jsonWebServicesClassName.fetchClassName(
		'com.liferay.journal.model.JournalArticle'
	);

	const animalWebContentStructureId = await getWebContentStructureId(
		apiHelpers,
		site.id,
		ANIMAL_DDM_STRUCTURE_KEY
	);

	const displayPage =
		await apiHelpers.jsonWebServicesLayoutPageTemplateEntry.addDisplayPageLayoutPageTemplateEntry(
			{
				classNameId: className.classNameId,
				classTypeId: String(animalWebContentStructureId),
				groupId: site.id,
				name: displayPageTemplateName,
			}
		);

	await apiHelpers.jsonWebServicesLayoutPageTemplateEntry.markAsDefaultDisplayPageLayoutPageTemplateEntry(
		{
			layoutPageTemplateEntryId: displayPage.layoutPageTemplateEntryId,
		}
	);
}

test.describe('General', () => {
	test('Allow mapping repeatable fields collection provider', async ({
		displayPageTemplatesPage,
		page,
		pageEditorPage,
		pageManagementSite,
	}) => {

		// Create DPT for Animal

		await displayPageTemplatesPage.goto(pageManagementSite.friendlyUrlPath);

		const displayPageTemplateName = getRandomString();

		await displayPageTemplatesPage.createTemplate({
			contentSubtype: 'Animal',
			contentType: 'Web Content Article',
			name: displayPageTemplateName,
		});

		await displayPageTemplatesPage.editTemplate(displayPageTemplateName);

		// Add collection and image fragment

		await pageEditorPage.addFragment(
			'Content Display',
			'Collection Display'
		);

		await page.locator('.lfr-layout-structure-item-collection').click();

		await pageEditorPage.chooseCollectionDisplayOption(
			'Repeatable Fields Collection Providers',
			'Species'
		);

		await pageEditorPage.waitForChangesSaved();

		await pageEditorPage.addFragment(
			'Basic Components',
			'Image',
			page.locator('.page-editor__collection-item.empty').last()
		);

		// Map editable to image field

		const imageFragmentId = await pageEditorPage.getFragmentId('Image');

		await pageEditorPage.selectEditable(imageFragmentId, 'image-square');

		await page.getByLabel('Source Selection').selectOption('Mapping');

		await page.getByLabel('Field').selectOption('Species Image');

		await pageEditorPage.waitForChangesSaved();

		// Change preview with item

		await displayPageTemplatesPage.changePreviewItem('Animal 01');

		// Check src of images

		const imageFragments = page.locator('.component-image img');

		expect(await imageFragments.first().getAttribute('src')).toContain(
			'poodle.jpg'
		);
		expect(await imageFragments.last().getAttribute('src')).toContain(
			'pug.jpg'
		);
	});

	test('Allow mapping editables to fields of related object', async ({
		displayPageTemplatesPage,
		pageEditorPage,
		pageManagementSite,
	}) => {

		// Create DPT for Lemon

		await displayPageTemplatesPage.goto(pageManagementSite.friendlyUrlPath);

		const displayPageTemplateName = getRandomString();

		await displayPageTemplatesPage.createTemplate({
			contentType: 'Lemon',
			name: displayPageTemplateName,
		});

		// Add fragment and select editable

		await displayPageTemplatesPage.editTemplate(displayPageTemplateName);

		await pageEditorPage.addFragment('Basic Components', 'Heading');

		const headingId = await pageEditorPage.getFragmentId('Heading');

		await pageEditorPage.selectEditable(headingId, 'element-text');

		// Map to field from related Lemon Basket object

		await pageEditorPage.setMappingConfiguration({
			mapping: {
				field: 'Lemon Basket Color',
			},
			relationship: 'Lemon Basket',
			source: 'relationship',
		});

		// Check editable is mapped

		const editable = pageEditorPage.getEditable({
			editableId: 'element-text',
			fragmentId: headingId,
		});

		await expect(editable).toHaveClass(/page-editor__editable--mapped/);
	});

	test(
		'Allow mapping background image',
		{
			tag: '@LPS-98030',
		},
		async ({
			apiHelpers,
			displayPageTemplatesPage,
			page,
			pageEditorPage,
			pageManagementSite,
		}) => {

			// Create display page template for Animal and mark as default

			const displayPageTemplateName = getRandomString();

			await addDefaultAnimalDisplayPageTemplate(
				apiHelpers,
				displayPageTemplateName,
				pageManagementSite
			);

			// Go to edit display page template

			await displayPageTemplatesPage.goto(
				pageManagementSite.friendlyUrlPath
			);

			await displayPageTemplatesPage.editTemplate(
				displayPageTemplateName
			);

			// Map background image

			await pageEditorPage.addFragment('Layout Elements', 'Container');

			const containerId = await pageEditorPage.getFragmentId('Container');

			await pageEditorPage.selectFragment(containerId);

			await page.getByRole('tab', {exact: true, name: 'Styles'}).click();

			await page
				.getByLabel('Image Source', {exact: true})
				.selectOption({label: 'Mapping'});

			await pageEditorPage.waitForChangesSaved();

			await page
				.getByLabel('Field', {exact: true})
				.selectOption({label: 'Main Image'});

			// Publish display page template

			await displayPageTemplatesPage.publishTemplate();

			// Assert background image in view mode

			await page.goto(
				`web${pageManagementSite.friendlyUrlPath}/w/${ANIMAL_01_FRIENDLY_URL}`
			);

			await expect(
				page.locator(
					'.lfr-layout-structure-item-container[style*="dogs.jpg"]'
				)
			).toBeAttached();

			// Delete default display page template

			await displayPageTemplatesPage.goto(
				pageManagementSite.friendlyUrlPath
			);

			await displayPageTemplatesPage.deleteTemplate(
				displayPageTemplateName
			);

			await expect(
				page.getByText(displayPageTemplateName, {exact: true})
			).not.toBeVisible();
		}
	);

	test(
		'Allow mapping link',
		{
			tag: '@LPS-98030',
		},
		async ({
			apiHelpers,
			displayPageTemplatesPage,
			page,
			pageEditorPage,
			pageManagementSite,
		}) => {

			// Create display page template for Animal and mark as default

			const displayPageTemplateName = getRandomString();

			await addDefaultAnimalDisplayPageTemplate(
				apiHelpers,
				displayPageTemplateName,
				pageManagementSite
			);

			// Go to edit display page template

			await displayPageTemplatesPage.goto(
				pageManagementSite.friendlyUrlPath
			);

			await displayPageTemplatesPage.editTemplate(
				displayPageTemplateName
			);

			// Map link to header fragment

			await pageEditorPage.addFragment('Basic Components', 'Heading');

			await pageEditorPage.mapEditableLink({
				editableId: 'element-text',
				fragmentName: 'Heading',
				linkConfiguration: {
					mappingConfiguration: {
						mapping: {
							field: 'More Info Link',
						},
						source: 'structure',
					},
					type: 'Mapped URL',
				},
			});

			// Map link to image fragment

			await pageEditorPage.addFragment('Basic Components', 'Image');

			await pageEditorPage.mapEditableLink({
				editableId: 'image-square',
				fragmentName: 'Image',
				linkConfiguration: {
					mappingConfiguration: {
						mapping: {
							field: 'More Info Link',
						},
						source: 'structure',
					},
					type: 'Mapped URL',
				},
			});

			// Map link to button fragment

			await pageEditorPage.addFragment('Basic Components', 'Button');

			await pageEditorPage.mapEditableLink({
				editableId: 'link',
				fragmentName: 'Button',
				linkConfiguration: {
					mappingConfiguration: {
						mapping: {
							field: 'More Info Link',
						},
						source: 'structure',
					},
					type: 'Mapped URL',
				},
			});

			// Publish display page template

			await displayPageTemplatesPage.publishTemplate();

			// Assert mapped link in view mode

			await page.goto(
				`web${pageManagementSite.friendlyUrlPath}/w/${ANIMAL_01_FRIENDLY_URL}`
			);

			expect(
				await page
					.locator('.component-heading')
					.getByRole('link')
					.getAttribute('href')
			).toContain('https://en.wikipedia.org/wiki/Dog');

			expect(
				await page
					.locator('.component-image')
					.getByRole('link')
					.getAttribute('href')
			).toContain('https://en.wikipedia.org/wiki/Dog');

			expect(
				await page
					.locator('.component-button')
					.getByRole('link')
					.getAttribute('href')
			).toContain('https://en.wikipedia.org/wiki/Dog');

			// Delete default display page template

			await displayPageTemplatesPage.goto(
				pageManagementSite.friendlyUrlPath
			);

			await displayPageTemplatesPage.deleteTemplate(
				displayPageTemplateName
			);

			await expect(
				page.getByText(displayPageTemplateName, {exact: true})
			).not.toBeVisible();
		}
	);

	test(
		'Allow mapping text fields and image fields',
		{
			tag: ['@LPS-86550', '@LPS-182999'],
		},
		async ({
			apiHelpers,
			displayPageTemplatesPage,
			page,
			pageEditorPage,
			pageManagementSite,
		}) => {

			// Create display page template for Animal and mark as default

			const displayPageTemplateName = getRandomString();

			await addDefaultAnimalDisplayPageTemplate(
				apiHelpers,
				displayPageTemplateName,
				pageManagementSite
			);

			// Go to edit display page template

			await displayPageTemplatesPage.goto(
				pageManagementSite.friendlyUrlPath
			);

			await displayPageTemplatesPage.editTemplate(
				displayPageTemplateName
			);

			// Map author name

			await pageEditorPage.addFragment('Basic Components', 'Heading');

			const headingFragmentId =
				await pageEditorPage.getFragmentId('Heading');

			await pageEditorPage.selectEditable(
				headingFragmentId,
				'element-text'
			);

			await page.getByLabel('Field').selectOption('Author Name');

			// Map author profile image

			await pageEditorPage.addFragment('Basic Components', 'Image');

			const imageFragmentId = await pageEditorPage.getFragmentId('Image');

			await pageEditorPage.selectEditable(
				imageFragmentId,
				'image-square'
			);

			await page
				.getByLabel('Source Selection', {exact: true})
				.selectOption('Mapping');

			await pageEditorPage.waitForChangesSaved();

			await page.getByLabel('Field').selectOption('Author Profile Image');

			// Publish display page template

			await displayPageTemplatesPage.publishTemplate();

			// Assert mapped fields in view mode

			await page.goto(
				`web${pageManagementSite.friendlyUrlPath}/w/${ANIMAL_01_FRIENDLY_URL}`
			);

			await expect(
				page.getByRole('heading', {name: 'Test Test'})
			).toBeVisible();

			await expect(
				page.getByRole('img', {name: 'Test Test'})
			).toBeVisible();

			// Delete default display page template

			await displayPageTemplatesPage.goto(
				pageManagementSite.friendlyUrlPath
			);

			await displayPageTemplatesPage.deleteTemplate(
				displayPageTemplateName
			);

			await expect(
				page.getByText(displayPageTemplateName, {exact: true})
			).not.toBeVisible();
		}
	);
});

test.describe('Image Resolution', () => {
	test(
		'Check image resolution in display page template',
		{
			tag: '@LPS-125191',
		},
		async ({
			apiHelpers,
			displayPageTemplatesPage,
			page,
			pageEditorPage,
			pageManagementSite,
			simulationMenuPage,
		}) => {

			// Create display page template

			const className =
				await apiHelpers.jsonWebServicesClassName.fetchClassName(
					'com.liferay.portal.kernel.repository.model.FileEntry'
				);

			const displayPageTemplateName = getRandomString();

			const displayPage =
				await apiHelpers.jsonWebServicesLayoutPageTemplateEntry.addDisplayPageLayoutPageTemplateEntry(
					{
						classNameId: className.classNameId,
						classTypeId: '0',
						groupId: pageManagementSite.id,
						name: displayPageTemplateName,
					}
				);

			await apiHelpers.jsonWebServicesLayoutPageTemplateEntry.markAsDefaultDisplayPageLayoutPageTemplateEntry(
				{
					layoutPageTemplateEntryId:
						displayPage.layoutPageTemplateEntryId,
				}
			);

			// Go to edit display page template

			await displayPageTemplatesPage.goto(
				pageManagementSite.friendlyUrlPath
			);

			await displayPageTemplatesPage.editTemplate(
				displayPageTemplateName
			);

			// Map Image

			await pageEditorPage.addFragment(
				'Featured Content',
				'Banner Center'
			);

			const containerId = await pageEditorPage.getFragmentId('Container');

			await pageEditorPage.selectFragment(containerId);

			await page.getByRole('tab', {exact: true, name: 'Styles'}).click();

			await page
				.getByLabel('Image Source', {exact: true})
				.selectOption({label: 'Mapping'});

			await pageEditorPage.waitForChangesSaved();

			await page
				.getByLabel('Field', {exact: true})
				.selectOption({label: 'File URL'});

			await displayPageTemplatesPage.publishTemplate();

			// Go to view mode

			await page.goto(
				`web${pageManagementSite.friendlyUrlPath}/d/high_resolution_photo-jpg`
			);

			// Open simulation panel

			await simulationMenuPage.openSimulationPanel();

			// Assert image in desktop

			const iframe = page.frameLocator(
				'iframe[title="Simulation Preview"]'
			);

			await expect(
				iframe.locator('.lfr-layout-structure-item-container').first()
			).toHaveCSS('background-image', /documents/);

			// Assert image in tablet

			await page.getByLabel('Tablet').click();

			await expect(
				iframe.locator('.lfr-layout-structure-item-container').first()
			).toHaveCSS('background-image', /Preview-1000x0/);
		}
	);
});

test.describe('Preview Item', () => {
	test(
		'View mapped content in page editor when select preview items',
		{
			tag: '@LPS-128904',
		},
		async ({
			context,
			displayPageTemplatesPage,
			page,
			pageEditorPage,
			pageManagementSite,
		}) => {

			// Create DPT for Animal

			await displayPageTemplatesPage.goto(
				pageManagementSite.friendlyUrlPath
			);

			const displayPageTemplateName = getRandomString();

			await displayPageTemplatesPage.createTemplate({
				contentSubtype: 'Animal',
				contentType: 'Web Content Article',
				name: displayPageTemplateName,
			});

			await displayPageTemplatesPage.editTemplate(
				displayPageTemplateName
			);

			// Add heading fragment

			await pageEditorPage.addFragment('Basic Components', 'Heading');

			const headingFragmentId =
				await pageEditorPage.getFragmentId('Heading');

			await pageEditorPage.selectEditable(
				headingFragmentId,
				'element-text'
			);

			await page.getByLabel('Field').selectOption('Title');

			await pageEditorPage.waitForChangesSaved();

			// Change preview with item

			await displayPageTemplatesPage.changePreviewItem('Animal 01');

			// Assert title

			await expect(
				page.locator('.component-heading').getByText('Animal 01')
			).toBeVisible();

			// Map author profile image

			await pageEditorPage.addFragment('Basic Components', 'Image');

			const imageFragmentId = await pageEditorPage.getFragmentId('Image');

			await pageEditorPage.changeEditableConfiguration({
				editableId: 'image-square',
				fieldLabel: 'Source Selection',
				fragmentId: imageFragmentId,
				tab: 'Image Source',
				value: 'Mapping',
			});

			await pageEditorPage.changeEditableConfiguration({
				editableId: 'image-square',
				fieldLabel: 'Field',
				fragmentId: imageFragmentId,
				tab: 'Image Source',
				value: 'Species Image',
			});

			// Assert image

			expect(
				await page.locator('.component-image img').getAttribute('src')
			).toContain('poodle.jpg');

			// Preview in a new tab

			const pagePromise = context.waitForEvent('page');

			const previewButton = page.getByRole('menuitem', {
				name: 'Preview in a New Tab',
			});

			await expect(async () => {
				await clickAndExpectToBeVisible({
					autoClick: true,
					target: previewButton,
					trigger: page
						.locator('.control-menu-nav-item')
						.getByLabel('Options', {exact: true}),
				});

				const newPage = await pagePromise;

				await expect(
					newPage.locator('.component-heading').getByText('Animal 01')
				).toBeVisible({
					timeout: 100,
				});
			}).toPass();
		}
	);
});
