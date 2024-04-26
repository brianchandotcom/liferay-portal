/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {APIResponse, expect as baseExpect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {workflowPagesTest} from '../../fixtures/workflowPagesTest';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import fillAndClickOutside from '../../utils/fillAndClickOutside';
import getRandomString from '../../utils/getRandomString';
import addApprovedStructuredContent from '../../utils/structured-content/addApprovedStructuredContent';
import getBasicWebContentStructureId from '../../utils/structured-content/getBasicWebContentStructureId';
import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';
import {journalPagesTest} from './fixtures/journalPagesTest';
import getDataStructureDefinition from './utils/getDataStructureDefinition';

const translateNameAndMetadataFields = async (
	page,
	structureName = 'Basic Web Content'
) => {
	await fillAndClickOutside(
		page,
		page.getByLabel('Friendly URL', {exact: true})
	);
	await fillAndClickOutside(
		page,
		page.getByPlaceholder(`Untitled ${structureName}`)
	);
	await fillAndClickOutside(
		page,
		page
			.frameLocator(':text("Description")+div iframe')
			.getByRole('textbox')
	);
};

const baseTest = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	isolatedSiteTest,
	journalPagesTest,
	loginTest(),
	workflowPagesTest
);

const bulkTest = mergeTests(
	baseTest,
	featureFlagsTest({
		'LPD-16469': true,
	})
);

const expect = baseExpect.extend({
	toBeSuccessful: (response: APIResponse) => ({
		message: () =>
			response.ok()
				? 'Response is successful'
				: 'Response is not successful',
		pass: response.ok(),
	}),
});

const keepTitlesUntranslated = mergeTests(baseTest);

const prefixUrlTest = mergeTests(
	baseTest,
	featureFlagsTest({
		'LPS-203351': true,
	})
);

const scheduleTest = mergeTests(
	baseTest,
	featureFlagsTest({
		'LPD-15596': true,
	})
);

const translationTest = mergeTests(
	baseTest,
	featureFlagsTest({
		'LPD-11253': true,
		'LPS-114700': true,
	})
);

const aiCreateImageTest = mergeTests(
	baseTest,
	featureFlagsTest({
		'LPD-10793': true,
	})
);

const privateContentIconTest = mergeTests(baseTest);

keepTitlesUntranslated(
	'LPD-20723: Clay link is translating asset titles/names by default in vertical card',
	async ({apiHelpers, journalPage, page, site}) => {
		const contentStructureId = await getBasicWebContentStructureId(
			apiHelpers
		);

		const title = 'add-web-content';

		await addApprovedStructuredContent({
			apiHelpers,
			contentStructureId,
			siteId: site.id,
			title,
		});

		await journalPage.goto(site.friendlyUrlPath);

		await journalPage.changeView('cards');

		await expect(page.getByRole('link', {name: title})).toBeVisible({
			timeout: 1000,
		});

		await journalPage.changeView('list');

		await expect(page.getByRole('link', {name: title})).toBeVisible({
			timeout: 1000,
		});

		await journalPage.changeView('table');

		await expect(page.getByRole('link', {name: title})).toBeVisible({
			timeout: 1000,
		});
	}
);

privateContentIconTest(
	'LPD-15807: Identify at a glance if a Web Content is visible for guests in content management',
	async ({apiHelpers, journalEditArticlePage, journalPage, site}) => {
		const contentStructureId = await getBasicWebContentStructureId(
			apiHelpers
		);

		const title = getRandomString();

		await addApprovedStructuredContent({
			apiHelpers,
			contentStructureId,
			siteId: site.id,
			title,
		});

		await journalPage.goto(site.friendlyUrlPath);

		await journalPage.assertPrivateContentIcon();

		await journalPage.changeView('table');

		await journalPage.assertPrivateContentIcon();

		await journalPage.changeView('list');

		await journalEditArticlePage.editArticle(title);

		await journalPage.assertPrivateContentIcon();
	}
);

privateContentIconTest(
	'LPD-15807: Identify at a glance if a Web Content is visible for guests in the item selector',
	async ({apiHelpers, journalEditArticlePage, journalPage, site}) => {
		const contentStructureId = await getBasicWebContentStructureId(
			apiHelpers
		);

		await addApprovedStructuredContent({
			apiHelpers,
			contentStructureId,
			siteId: site.id,
			title: getRandomString(),
		});

		const title = getRandomString();

		await addApprovedStructuredContent({
			apiHelpers,
			contentStructureId,
			siteId: site.id,
			title,
		});

		await journalPage.goto(site.friendlyUrlPath);

		await journalEditArticlePage.editArticle(title);

		await journalEditArticlePage.openRelatedAsset('Basic Web Content');

		await journalEditArticlePage.assertPrivateContentIconInRelatedAssetPopUp(
			'Basic Web Content'
		);

		await journalEditArticlePage.changeViewInRelatedAssetPopUp(
			'Basic Web Content',
			'table'
		);

		await journalEditArticlePage.assertPrivateContentIconInRelatedAssetPopUp(
			'Basic Web Content'
		);
	}
);

prefixUrlTest(
	'LPD-6813: Make prefix URLs configurable',
	async ({
		apiHelpers,
		displayPageTemplatesPage,
		friendlyUrlInstanceSettingsPage,
		page,
		site,
	}) => {
		const articleTitle = getRandomString();

		const contentStructureId = await getBasicWebContentStructureId(
			apiHelpers
		);

		await addApprovedStructuredContent({
			apiHelpers,
			contentStructureId,
			siteId: site.id,
			title: articleTitle,
		});

		await displayPageTemplatesPage.goto(site.friendlyUrlPath);

		const displayPageTemplateName = getRandomString();

		await displayPageTemplatesPage.publishNewTemplate(
			displayPageTemplateName
		);

		await displayPageTemplatesPage.markAsDefault(displayPageTemplateName);

		await friendlyUrlInstanceSettingsPage.goto();

		const urlSeparator = 'content';

		await friendlyUrlInstanceSettingsPage.modifySeparator(
			'_com_liferay_configuration_admin_web_portlet_InstanceSettingsPortlet_com.liferay.journal.model.JournalArticle',
			urlSeparator
		);

		expect(
			await page.request.get(
				'/group' +
					site.friendlyUrlPath +
					'/' +
					urlSeparator +
					'/' +
					articleTitle
			)
		).toBeSuccessful();

		await friendlyUrlInstanceSettingsPage.goto();

		await friendlyUrlInstanceSettingsPage.resetSeparator(
			'_com_liferay_configuration_admin_web_portlet_InstanceSettingsPortlet_com.liferay.journal.model.JournalArticle-reset-to-default-value'
		);

		expect(
			await page.request.get(
				'/group' + site.friendlyUrlPath + '/w/' + articleTitle
			)
		).toBeSuccessful();
	}
);

translationTest(
	'LPD-13732: This is a test for reset translations button in web content',
	async ({journalEditArticlePage, journalPage, page, site}) => {
		await journalPage.goto();

		await journalEditArticlePage.goto({siteUrl: site.friendlyUrlPath});

		await journalEditArticlePage.fillTitle(getRandomString());

		const translationButton = page.getByRole('combobox', {
			name: 'Select a language',
		});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('option', {
				name: 'Catalan Language: Not Translated',
			}),
			trigger: translationButton,
		});

		await translateNameAndMetadataFields(page);

		await translationButton.click();

		await expect(
			page.getByRole('option', {
				name: 'Catalan Language: Translating 3/4',
			})
		).toBeVisible({timeout: 1000});

		const translationOptionsButton = page.getByLabel('Translation Options');

		await translationOptionsButton.click();

		const resetTranslationButton = page.getByRole('button', {
			name: 'Reset Translation',
		});

		await expect(resetTranslationButton).toBeEnabled();

		await resetTranslationButton.click();

		const deleteButton = page.getByRole('button', {name: 'Delete'});

		await deleteButton.click();

		await translationButton.click();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('option', {
				name: 'Catalan Language: Not Translated',
			}),
			trigger: translationButton,
		});
	}
);

translationTest(
	'LPD-17245: Add error message in Translation for concurrent users',
	async ({
		apiHelpers,
		journalEditArticlePage,
		journalEditArticleTranslationsPage,
		journalPage,
		site,
	}) => {
		const contentStructureId = await getBasicWebContentStructureId(
			apiHelpers
		);

		const title = getRandomString();

		await addApprovedStructuredContent({
			apiHelpers,
			contentStructureId,
			siteId: site.id,
			title,
		});

		await journalPage.goto(site.friendlyUrlPath);

		const editBasicArticleTranslationUrl =
			await journalEditArticleTranslationsPage.editBasicArticleTranslations(
				title,
				''
			);

		await journalEditArticlePage.editAndPublishExistingBasicArticle(title);

		await journalEditArticleTranslationsPage.assertErrorInEditBasicArticleTranslations(
			editBasicArticleTranslationUrl
		);
	}
);

bulkTest(
	'LPD-17782: This is a test for bulk permissions of web content',
	async ({apiHelpers, journalPage, page, site}) => {
		const PERMISSIONS_LOCATORS = [
			{enabled: true, locator: '#guest_ACTION_DELETE'},
			{enabled: true, locator: '#guest_ACTION_PERMISSIONS'},
		];

		const contentStructureId = await getBasicWebContentStructureId(
			apiHelpers
		);

		const title1 = getRandomString();
		const title2 = getRandomString();

		await addApprovedStructuredContent({
			apiHelpers,
			contentStructureId,
			siteId: site.id,
			title: title1,
		});

		await addApprovedStructuredContent({
			apiHelpers,
			contentStructureId,
			siteId: site.id,
			title: title2,
		});

		await journalPage.goto(site.friendlyUrlPath);

		const article1 = page
			.locator(
				'#_com_liferay_journal_web_portlet_JournalPortlet_articlesSearchContainer .list-group-item'
			)
			.filter({hasText: title1});

		const article2 = page
			.locator(
				'#_com_liferay_journal_web_portlet_JournalPortlet_articlesSearchContainer .list-group-item'
			)
			.filter({hasText: title2});

		await article1.waitFor();
		await article2.waitFor();

		await journalPage.setJournalArticlePermissions(
			[article1, article2],
			['#guest_ACTION_DELETE', '#guest_ACTION_PERMISSIONS']
		);

		await journalPage.assertJournalArticlePermissions(
			title1,
			PERMISSIONS_LOCATORS
		);
		await journalPage.assertJournalArticlePermissions(
			title2,
			PERMISSIONS_LOCATORS
		);
	}
);

translationTest(
	'LPD-19627: Translate several fields in a Basic Web Content and check how many fields have been translated',
	async ({journalEditArticlePage, page, site}) => {
		await journalEditArticlePage.goto({siteUrl: site.friendlyUrlPath});

		await journalEditArticlePage.fillTitle(getRandomString());

		const translationButton = page.getByRole('combobox', {
			name: 'Select a language',
		});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('option', {
				name: 'Catalan Language: Not Translated',
			}),
			trigger: translationButton,
		});

		await translateNameAndMetadataFields(page);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('option', {
				name: 'Catalan Language: Translating 3/4',
			}),
			trigger: translationButton,
		});
	}
);

translationTest(
	'LPD-19627: Translate all fields of a Web Content based on a custom structure with repeatable fields',
	async ({apiHelpers, journalEditArticlePage, page, site}) => {
		const localizableFieldName = 'Text5678';
		const structureName = 'Structure 1';

		const dataDefinition = getDataStructureDefinition({
			defaultLanguageId: 'en_US',
			fields: [{name: localizableFieldName, repeatable: true}],
			name: structureName,
		});

		await apiHelpers.dataEngine.createStructure(site.id, dataDefinition);

		await journalEditArticlePage.goto({
			siteUrl: site.friendlyUrlPath,
			structureName,
		});

		const translationButton = page.getByRole('combobox', {
			name: 'Select a language',
		});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('option', {
				name: 'Catalan Language: Not Translated',
			}),
			trigger: translationButton,
		});

		await translateNameAndMetadataFields(page, structureName);

		const localizableField = page.getByRole('textbox', {
			name: localizableFieldName,
		});

		await fillAndClickOutside(page, localizableField);

		await clickAndExpectToBeVisible({
			target: page.getByRole('option', {
				name: 'Catalan Language: Translated',
			}),
			timeout: 1000,
			trigger: translationButton,
		});

		await page.getByLabel('Add Duplicate Field Text').click();

		await clickAndExpectToBeVisible({
			target: page.getByRole('option', {
				name: 'Catalan Language: Translating 4/5',
			}),
			timeout: 1000,
			trigger: translationButton,
		});

		await fillAndClickOutside(
			page,
			page.locator('input.ddm-field-text').nth(1)
		);

		await clickAndExpectToBeVisible({
			target: page.getByRole('option', {
				name: 'Catalan Language: Translated',
			}),
			timeout: 1000,
			trigger: translationButton,
		});
	}
);

translationTest(
	'LPD-19627: A non-localizabled field is disabled when another translation language is selected',
	async ({apiHelpers, journalEditArticlePage, page, site}) => {
		const nonLocalizableFieldName = 'Text1234';
		const structureName = 'Structure 1';

		const dataDefinition = getDataStructureDefinition({
			defaultLanguageId: 'en_US',
			fields: [{localizable: false, name: nonLocalizableFieldName}],
			name: structureName,
		});

		await apiHelpers.dataEngine.createStructure(site.id, dataDefinition);

		await journalEditArticlePage.goto({
			siteUrl: site.friendlyUrlPath,
			structureName,
		});

		const translationButton = page.getByRole('combobox', {
			name: 'Select a language',
		});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('option', {
				name: 'Catalan Language: Not Translated',
			}),
			trigger: translationButton,
		});

		await expect(
			page.getByRole('textbox', {
				name: nonLocalizableFieldName,
			})
		).toBeDisabled();
	}
);

scheduleTest(
	'Create a web content selecting permissions in the modal',
	async ({journalEditArticlePage, journalPage, page, site}) => {
		await journalEditArticlePage.goto({siteUrl: site.friendlyUrlPath});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {
				name: 'Publish With Permissions',
			}),
			trigger: page.getByRole('button', {
				name: 'Select and Confirm Publish Settings',
			}),
		});

		await expect(
			page.getByText(
				'Please enter a valid title for the default language'
			)
		).toBeVisible({timeout: 1000});

		const title = getRandomString();

		await journalEditArticlePage.titlePlaceholder.fill(title);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {
				name: 'Publish With Permissions',
			}),
			trigger: page.getByRole('button', {
				name: 'Select and Confirm Publish Settings',
			}),
		});

		await page.getByLabel('Viewable by').selectOption('Site Members');

		await page.getByRole('button', {exact: true, name: 'Publish'}).click();

		await page.getByText(title, {exact: true}).waitFor();

		await journalPage.assertJournalArticlePermissions(title, [
			{enabled: false, locator: '#guest_ACTION_VIEW'},
		]);
	}
);

scheduleTest(
	'Change permission of a web content in edition mode',
	async ({journalEditArticlePage, journalPage, page, site}) => {
		await journalEditArticlePage.goto({siteUrl: site.friendlyUrlPath});

		const title = getRandomString();

		await journalEditArticlePage.fillTitle(title);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {
				name: 'Publish With Permissions',
			}),
			trigger: page.getByRole('button', {
				name: 'Select and Confirm Publish Settings',
			}),
		});

		await page.getByRole('button', {exact: true, name: 'Publish'}).click();

		await waitForSuccessAlert(
			page,
			`Success:${title} was created successfully.`
		);

		await page.getByLabel(`Actions for ${title}`).waitFor();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {
				exact: true,
				name: 'Edit',
			}),
			trigger: page.getByLabel(`Actions for ${title}`, {
				exact: true,
			}),
		});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {
				name: 'Permissions',
			}),
			trigger: page.getByRole('button', {
				name: 'Options',
			}),
		});

		await journalPage.setPermissions(['#power-user_ACTION_DELETE']);

		await journalPage.goto(site.friendlyUrlPath);

		await journalPage.assertJournalArticlePermissions(title, [
			{enabled: true, locator: '#power-user_ACTION_DELETE'},
		]);
	}
);

aiCreateImageTest(
	'LPD-6800 Create AI Image option visible from Item Selector',
	async ({journalEditArticlePage, page, site}) => {
		await journalEditArticlePage.goto({siteUrl: site.friendlyUrlPath});

		await journalEditArticlePage.openDMItemSelectorForImages();

		const DMItemSelectorPage = page.frameLocator(
			'iframe[title="Select Item"]'
		);

		await DMItemSelectorPage.getByRole('button', {name: 'New'}).click();
		await expect(
			DMItemSelectorPage.getByRole('menuitem', {name: 'Create AI Image'})
		).toBeVisible();
	}
);

scheduleTest(
	'Create a web content scheduled',
	async ({journalEditArticlePage, site}) => {
		await journalEditArticlePage.goto({siteUrl: site.friendlyUrlPath});

		const articleTitle = getRandomString();
		const scheduleDate = '9987-11-26 13:00';

		await journalEditArticlePage.scheduleArticle(
			articleTitle,
			scheduleDate
		);

		await journalEditArticlePage.assertScheduleDate(
			articleTitle,
			scheduleDate
		);
	}
);

scheduleTest(
	'Create a web content scheduled with workflow activated',
	async ({
		journalEditArticlePage,
		journalPage,
		site,
		workflowPage,
		workflowTasksPage,
	}) => {
		await workflowPage.goto(site.friendlyUrlPath);

		await workflowPage.changeWorkflow(
			'Web Content Article',
			'Single Approver'
		);

		await journalEditArticlePage.goto({siteUrl: site.friendlyUrlPath});

		const articleTitle = getRandomString();
		const articleDate = '9987-11-26 13:00';

		await journalEditArticlePage.scheduleArticle(
			articleTitle,
			articleDate,
			{workflow: true}
		);

		await workflowTasksPage.goToAssignedToMyRoles(site.friendlyUrlPath);

		await workflowTasksPage.assignToMe(articleTitle);

		await workflowTasksPage.approve(articleTitle);

		await journalPage.goto(site.friendlyUrlPath);

		await journalEditArticlePage.assertScheduleDate(
			articleTitle,
			articleDate,
			{workflow: true}
		);
	}
);
