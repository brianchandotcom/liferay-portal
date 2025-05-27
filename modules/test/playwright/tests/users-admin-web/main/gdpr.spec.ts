/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import {createReadStream} from 'fs';
import path from 'node:path';

import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {contactsCenterPagesTest} from '../../../fixtures/contactsCenterPagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {formsPagesTest} from '../../../fixtures/formsPagesTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {messageBoardsPagesTest} from '../../../fixtures/messageBoardsTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import {passwordPoliciesAdminPageTest} from '../../../fixtures/passwordPoliciesAdminConfigPageTest';
import {productMenuPageTest} from '../../../fixtures/productMenuPageTest';
import {siteStagingPageTest} from '../../../fixtures/siteStagingPageTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {
	performLoginViaApi,
	performLogout,
	userData,
} from '../../../utils/performLogin';
import {PORTLET_URLS} from '../../../utils/portletUrls';
import getBasicWebContentStructureId from '../../../utils/structured-content/getBasicWebContentStructureId';
import {waitForAlert} from '../../../utils/waitForAlert';
import {blogsPagesTest} from '../../blogs-web/main/fixtures/blogsPagesTest';
import {journalPagesTest} from '../../journal-web/main/fixtures/journalPagesTest';

export const test = mergeTests(
	contactsCenterPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-35013': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	loginTest({screenName: 'demo.company.admin'}),
	usersAndOrganizationsPagesTest
);

export const testAdmin = mergeTests(
	applicationsMenuPageTest,
	blogsPagesTest,
	commercePagesTest,
	contactsCenterPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-35013': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	formsPagesTest,
	isolatedSiteTest,
	journalPagesTest,
	loginTest(),
	messageBoardsPagesTest,
	pagesAdminPagesTest,
	passwordPoliciesAdminPageTest,
	productMenuPageTest,
	siteStagingPageTest,
	usersAndOrganizationsPagesTest
);

test(
	'Can export multiple entries',
	{tag: '@LPD-25858'},
	async ({
		apiHelpers,
		contactsCenterPage,
		exportUserDataPage,
		page,
		usersAndOrganizationsPage,
	}) => {
		test.setTimeout(120000);

		const site = await apiHelpers.headlessSite.createSite({
			name: getRandomString(),
		});

		apiHelpers.data.push({id: site.id, type: 'site'});

		await contactsCenterPage.createPage(apiHelpers, site.id, {
			title: 'contact',
		});

		await page.goto(`/web/${site.name}/contact`);

		await contactsCenterPage.addContactButton.click();
		await contactsCenterPage.nameInput.fill(getRandomString());
		await contactsCenterPage.emailAddressInput.fill(
			`${getRandomString()}@liferay.com`
		);
		await contactsCenterPage.saveButton.click();

		await expect(contactsCenterPage.successMessage).toBeVisible();

		const announcement =
			await apiHelpers.jsonWebServicesAnnouncementsEntryApiHelper.addEntry();

		apiHelpers.data.push({id: announcement.entryId, type: 'announcement'});

		await apiHelpers.headlessDelivery.postBlog(site.id);

		const contentStructureId =
			await getBasicWebContentStructureId(apiHelpers);

		await apiHelpers.jsonWebServicesJournal.addWebContent({
			ddmStructureId: contentStructureId,
			groupId: site.id,
		});

		const folder = await apiHelpers.jsonWebServicesJournal.addFolder({
			groupId: site.id,
		});

		await apiHelpers.jsonWebServicesJournal.addWebContent({
			ddmStructureId: contentStructureId,
			folderId: folder.folderId,
			groupId: site.id,
		});

		await apiHelpers.jsonWebServicesMBApiHelper.addMessage({
			groupId: site.id,
		});

		const wikiNode = await apiHelpers.headlessDelivery.postWikiNode(
			site.id
		);

		await apiHelpers.headlessDelivery.postWikiPage(wikiNode.id);

		await apiHelpers.headlessDelivery.postDocument(
			site.id,
			createReadStream(
				path.join(__dirname, '/dependencies/attachment.txt')
			)
		);

		await performLogout(page);
		await performLoginViaApi({page, screenName: 'test'});

		await page.goto(`/web/${site.name}`);

		await usersAndOrganizationsPage.goToUsers(false);

		await (
			await usersAndOrganizationsPage.usersTableRowActions(
				'demo.company.admin'
			)
		).click();
		await usersAndOrganizationsPage.exportPersonalDataItem.click();

		await exportUserDataPage.addExportProcessesButton.click();

		await exportUserDataPage.announcementsCheckbox.check();
		await exportUserDataPage.blogsCheckbox.check();
		await exportUserDataPage.contactsCenterCheckbox.check();
		await exportUserDataPage.documentsAndMediaCheckbox.check();
		await exportUserDataPage.messageBoardsCheckbox.check();
		await exportUserDataPage.webContentCheckbox.check();
		await exportUserDataPage.wikiCheckbox.check();

		await exportUserDataPage.exportButton.click();

		await expect(exportUserDataPage.announcementsStatus).toBeVisible();
		await expect(exportUserDataPage.blogsStatus).toBeVisible();
		await expect(exportUserDataPage.contactsCenterStatus).toBeVisible();
		await expect(exportUserDataPage.documentsAndMediaStatus).toBeVisible();
		await expect(exportUserDataPage.messageBoardsStatus).toBeVisible();
		await expect(exportUserDataPage.webContentStatus).toBeVisible();
		await expect(exportUserDataPage.wikiStatus).toBeVisible();

		await exportUserDataPage.creationMenuNewButton.click();

		await expect(exportUserDataPage.announcementsCheckbox).toBeVisible();
	}
);

testAdmin(
	'Can delete a single staged and live blogs entry',
	{tag: '@LPD-31206'},
	async ({
		apiHelpers,
		blogsPage,
		page,
		personalDataErasurePage,
		productMenuPage,
		siteStagingPage,
		usersAndOrganizationsPage,
	}) => {
		testAdmin.setTimeout(120000);

		page.on('dialog', (dialog) => {
			dialog.accept();
		});

		const userAccount =
			await apiHelpers.headlessAdminUser.postUserAccount();

		userData[userAccount.alternateName] = {
			name: userAccount.givenName,
			password: 'test',
			surname: userAccount.familyName,
		};

		const site = await apiHelpers.headlessSite.createSite({
			name: 'Site' + getRandomInt(),
		});

		apiHelpers.data.push({id: site.id, type: 'site'});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			siteId: site.id,
			title: 'Page' + getRandomInt(),
		});

		const role =
			await apiHelpers.headlessAdminUser.getRoleByName('Administrator');

		await apiHelpers.headlessAdminUser.postRoleByExternalReferenceCodeUserAccountAssociation(
			role.externalReferenceCode,
			userAccount.id
		);

		await performLogout(page);
		await performLoginViaApi({page, screenName: userAccount.alternateName});

		const blog1Name = 'Blog1';
		const blog2Name = 'Blog2';
		const blog3Name = 'Blog3';

		const blog1 = await apiHelpers.headlessDelivery.postBlog(site.id, {
			headline: blog1Name,
		});
		const blog2 = await apiHelpers.headlessDelivery.postBlog(site.id, {
			headline: blog2Name,
		});
		await apiHelpers.headlessDelivery.postBlog(site.id, {
			headline: blog3Name,
		});

		await page.goto(`/group/${site.name}/${layout.friendlyUrlPath}`);

		await productMenuPage.openProductMenuIfClosed();
		await productMenuPage.publishingButton.click();
		await productMenuPage.stagingMenuItem.click();
		await siteStagingPage.localStagingCheckbox.check();
		await siteStagingPage.blogsCheckbox.check();
		await siteStagingPage.saveButton.click();

		await waitForAlert(page, 'Local staging is successfully enabled.');

		await performLogout(page);
		await performLoginViaApi({page, screenName: 'test'});

		await usersAndOrganizationsPage.goToUsers(false);
		await (
			await usersAndOrganizationsPage.usersTableRowActions(
				userAccount.alternateName
			)
		).click();

		await usersAndOrganizationsPage.deletePersonalDataMenuItem.click();

		await expect(
			personalDataErasurePage.selectAllItemsOnPageCheckbox
		).toBeVisible();

		await personalDataErasurePage.objectCountLink('6').click();

		await personalDataErasurePage
			.objectCheckBox(blog1.id, blog1Name, true)
			.check();
		await personalDataErasurePage
			.objectCheckBox(blog2.id, blog2Name, false)
			.check();

		await personalDataErasurePage.actionsButton.click();
		await personalDataErasurePage.menuItemDelete.click();

		await expect(
			personalDataErasurePage.selectAllItemsOnPageCheckbox
		).toBeVisible();

		await page.goto(`/group/${site.name}-staging${PORTLET_URLS.blogs}`);

		await expect(blogsPage.blogName(blog1Name)).toHaveCount(1);
		await expect(blogsPage.blogName(blog2Name)).toHaveCount(0);
		await expect(blogsPage.blogName(blog3Name)).toHaveCount(1);

		await page.goto(`/group/${site.name}${PORTLET_URLS.blogs}`);

		await expect(blogsPage.blogName(blog1Name)).toHaveCount(0);
		await expect(blogsPage.blogName(blog2Name)).toHaveCount(1);
		await expect(blogsPage.blogName(blog3Name)).toHaveCount(1);
	}
);

testAdmin(
	'Can delete multiple entries from an application',
	{tag: '@LPD-48828'},
	async ({
		apiHelpers,
		page,
		personalDataErasurePage,
		usersAndOrganizationsPage,
	}) => {
		testAdmin.setTimeout(120000);

		page.on('dialog', (dialog) => {
			dialog.accept().catch(() => {});
		});

		const userAccount =
			await apiHelpers.headlessAdminUser.postUserAccount();

		userData[userAccount.alternateName] = {
			name: userAccount.givenName,
			password: 'test',
			surname: userAccount.familyName,
		};

		const role =
			await apiHelpers.headlessAdminUser.getRoleByName('Administrator');

		await apiHelpers.headlessAdminUser.postRoleByExternalReferenceCodeUserAccountAssociation(
			role.externalReferenceCode,
			userAccount.id
		);

		await performLogout(page);
		await performLoginViaApi({page, screenName: userAccount.alternateName});

		const site = await apiHelpers.headlessSite.createSite({
			name: getRandomString(),
		});

		apiHelpers.data.push({id: site.id, type: 'site'});

		const folder = await apiHelpers.headlessDelivery.postDocumentFolder(
			site.id
		);

		const attachment1 = await apiHelpers.headlessDelivery.postDocument(
			site.id,
			createReadStream(
				path.join(__dirname, '/dependencies/attachment.docx')
			)
		);

		const attachment2 = await apiHelpers.headlessDelivery.postDocument(
			site.id,
			createReadStream(
				path.join(__dirname, '/dependencies/attachment.jpeg')
			)
		);

		await apiHelpers.headlessDelivery.postDocument(
			site.id,
			createReadStream(
				path.join(__dirname, '/dependencies/attachment.txt')
			)
		);

		await performLogout(page);
		await performLoginViaApi({page, screenName: 'test'});

		await usersAndOrganizationsPage.goToUsers(false);
		await (
			await usersAndOrganizationsPage.usersTableRowActions(
				userAccount.alternateName
			)
		).click();
		await usersAndOrganizationsPage.deletePersonalDataMenuItem.click();

		await expect(
			personalDataErasurePage.selectAllItemsOnPageCheckbox
		).toBeVisible();

		await personalDataErasurePage.documentsAndMediaRadioButton.check();
		await (
			await personalDataErasurePage.userAssociatedDataTableRowCheckBox(
				folder.name
			)
		).check();
		await (
			await personalDataErasurePage.userAssociatedDataTableRowCheckBox(
				attachment1.fileName
			)
		).check();
		await (
			await personalDataErasurePage.userAssociatedDataTableRowCheckBox(
				attachment2.fileName
			)
		).check();
		await personalDataErasurePage.actionsButton.click();
		await personalDataErasurePage.deleteMenuItem.click();

		await waitForAlert(page);

		await expect(
			personalDataErasurePage.objectLink(folder.name)
		).not.toBeVisible();
		await expect(
			personalDataErasurePage.objectLink(attachment1.fileName)
		).not.toBeVisible();
		await expect(
			personalDataErasurePage.objectLink(attachment2.fileName)
		).not.toBeVisible();

		await page.goto(`/group/${site.name}${PORTLET_URLS.documentLibrary}`);

		await expect(page.getByText(userAccount.name)).toHaveCount(1);
	}
);

testAdmin(
	'Applications without entries are visible but disabled in new data export',
	{tag: '@LPD-50594'},
	async ({
		apiHelpers,
		exportUserDataPage,
		page,
		usersAndOrganizationsPage,
	}) => {
		test.setTimeout(120000);

		page.on('dialog', (dialog) => {
			dialog.accept();
		});

		const userAccount =
			await apiHelpers.headlessAdminUser.postUserAccount();

		userData[userAccount.alternateName] = {
			name: userAccount.givenName,
			password: 'test',
			surname: userAccount.familyName,
		};

		const site = await apiHelpers.headlessSite.createSite({
			name: 'Site' + getRandomInt(),
		});

		apiHelpers.data.push({id: site.id, type: 'site'});

		const role =
			await apiHelpers.headlessAdminUser.getRoleByName('Administrator');

		await apiHelpers.headlessAdminUser.postRoleByExternalReferenceCodeUserAccountAssociation(
			role.externalReferenceCode,
			userAccount.id
		);

		await performLogout(page);
		await performLoginViaApi({page, screenName: userAccount.alternateName});

		await apiHelpers.headlessDelivery.postBlog(site.id, {
			headline: getRandomString(),
		});

		const contentStructureId =
			await getBasicWebContentStructureId(apiHelpers);

		await apiHelpers.jsonWebServicesJournal.addWebContent({
			ddmStructureId: contentStructureId,
			groupId: site.id,
		});

		await apiHelpers.jsonWebServicesMBApiHelper.addMessage({
			groupId: site.id,
		});

		await performLogout(page);
		await performLoginViaApi({page, screenName: 'test'});

		await usersAndOrganizationsPage.goToUsers(false);

		await (
			await usersAndOrganizationsPage.usersTableRowActions(
				userAccount.alternateName
			)
		).click();
		await usersAndOrganizationsPage.exportPersonalDataItem.click();
		await exportUserDataPage.addExportProcessesButton.click();

		await expect(exportUserDataPage.blogsCheckbox).toBeEnabled();
		await expect(exportUserDataPage.webContentCheckbox).toBeEnabled();
		await expect(exportUserDataPage.messageBoardsCheckbox).toBeEnabled();
		await expect(exportUserDataPage.announcementsCheckbox).toBeDisabled();
		await expect(exportUserDataPage.contactsCenterCheckbox).toBeDisabled();
		await expect(
			exportUserDataPage.documentsAndMediaCheckbox
		).toBeDisabled();
		await expect(exportUserDataPage.formsCheckbox).toBeDisabled();
		await expect(exportUserDataPage.wikiCheckbox).toBeDisabled();

		await exportUserDataPage.blogsCheckbox.check();
		await exportUserDataPage.webContentCheckbox.check();
		await exportUserDataPage.messageBoardsCheckbox.check();
		await exportUserDataPage.exportButton.click();

		await expect(exportUserDataPage.blogsStatus).toBeVisible();
		await expect(exportUserDataPage.webContentStatus).toBeVisible();
		await expect(exportUserDataPage.messageBoardsStatus).toBeVisible();
		await expect(exportUserDataPage.announcementsStatus).not.toBeVisible();
		await expect(exportUserDataPage.contactsCenterStatus).not.toBeVisible();
		await expect(
			exportUserDataPage.documentsAndMediaStatus
		).not.toBeVisible();
		await expect(exportUserDataPage.formsStatus).not.toBeVisible();
		await expect(exportUserDataPage.wikiStatus).not.toBeVisible();
	}
);

testAdmin(
	'Documents and Media entries display details in info panel during personal data deletion',
	{tag: '@LPD-50608'},
	async ({
		apiHelpers,
		page,
		personalDataErasurePage,
		usersAndOrganizationsPage,
	}) => {
		test.setTimeout(120000);

		page.on('dialog', (dialog) => {
			dialog.accept();
		});

		const userAccount =
			await apiHelpers.headlessAdminUser.postUserAccount();

		userData[userAccount.alternateName] = {
			name: userAccount.givenName,
			password: 'test',
			surname: userAccount.familyName,
		};

		const role =
			await apiHelpers.headlessAdminUser.getRoleByName('Administrator');

		await apiHelpers.headlessAdminUser.postRoleByExternalReferenceCodeUserAccountAssociation(
			role.externalReferenceCode,
			userAccount.id
		);

		await performLogout(page);
		await performLoginViaApi({page, screenName: userAccount.alternateName});

		const site = await apiHelpers.headlessSite.createSite({
			name: 'Site' + getRandomInt(),
		});

		apiHelpers.data.push({id: site.id, type: 'site'});

		const folder = await apiHelpers.headlessDelivery.postDocumentFolder(
			site.id
		);

		const document = await apiHelpers.headlessDelivery.postDocument(
			site.id,
			createReadStream(
				path.join(__dirname, '/dependencies/attachment.txt')
			),
			{
				description: getRandomString(),
				fileName: 'attachment.txt',
				title: getRandomString(),
			}
		);

		await performLogout(page);
		await performLoginViaApi({page, screenName: 'test'});

		await usersAndOrganizationsPage.goToUsers(false);

		await (
			await usersAndOrganizationsPage.usersTableRowActions(
				userAccount.alternateName
			)
		).click();
		await usersAndOrganizationsPage.deletePersonalDataMenuItem.click();

		await expect(
			personalDataErasurePage.selectAllItemsOnPageCheckbox
		).toBeVisible();

		await personalDataErasurePage.documentsAndMediaRadioButton.check();

		await expect(personalDataErasurePage.dlFileEntryText).toBeVisible();
		await expect(personalDataErasurePage.dlFolderText).toBeVisible();

		await (
			await personalDataErasurePage.userAssociatedDataTableRowCheckBox(
				folder.name
			)
		).check();
		await personalDataErasurePage.infoPanelButton.click();

		await expect(personalDataErasurePage.infoPanelSidebar).toContainText(
			folder.name
		);
		await expect(personalDataErasurePage.infoPanelSidebar).toContainText(
			folder.description
		);

		await (
			await personalDataErasurePage.userAssociatedDataTableRowCheckBox(
				folder.name
			)
		).uncheck();
		await (
			await personalDataErasurePage.userAssociatedDataTableRowCheckBox(
				document.fileName
			)
		).check();

		await expect(personalDataErasurePage.infoPanelSidebar).toContainText(
			document.title
		);
		await expect(personalDataErasurePage.infoPanelSidebar).toContainText(
			document.description
		);
		await expect(personalDataErasurePage.infoPanelSidebar).toContainText(
			'txt'
		);
	}
);

testAdmin(
	'Can delete all staged data from an application',
	{tag: '@LPD-51202'},
	async ({
		apiHelpers,
		page,
		personalDataErasurePage,
		productMenuPage,
		siteStagingPage,
		usersAndOrganizationsPage,
	}) => {
		testAdmin.setTimeout(120000);

		page.on('dialog', (dialog) => {
			dialog.accept().catch(() => {});
		});

		const userAccount =
			await apiHelpers.headlessAdminUser.postUserAccount();

		userData[userAccount.alternateName] = {
			name: userAccount.givenName,
			password: 'test',
			surname: userAccount.familyName,
		};

		const role =
			await apiHelpers.headlessAdminUser.getRoleByName('Administrator');

		await apiHelpers.headlessAdminUser.postRoleByExternalReferenceCodeUserAccountAssociation(
			role.externalReferenceCode,
			userAccount.id
		);

		await performLogout(page);
		await performLoginViaApi({page, screenName: userAccount.alternateName});

		const site = await apiHelpers.headlessSite.createSite({
			name: getRandomString(),
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			siteId: site.id,
			title: 'Page' + getRandomInt(),
		});

		apiHelpers.data.push({id: site.id, type: 'site'});

		const attachment1 = await apiHelpers.headlessDelivery.postDocument(
			site.id,
			createReadStream(
				path.join(__dirname, '/dependencies/attachment.docx')
			)
		);

		const attachment2 = await apiHelpers.headlessDelivery.postDocument(
			site.id,
			createReadStream(
				path.join(__dirname, '/dependencies/attachment.jpeg')
			)
		);

		const blog1 = await apiHelpers.headlessDelivery.postBlog(site.id, {
			headline: getRandomString(),
		});
		const blog2 = await apiHelpers.headlessDelivery.postBlog(site.id, {
			headline: getRandomString(),
		});

		await page.goto(`/group/${site.name}/${layout.friendlyUrlPath}`);

		await productMenuPage.openProductMenuIfClosed();
		await productMenuPage.publishingButton.click();
		await productMenuPage.stagingMenuItem.click();
		await siteStagingPage.localStagingCheckbox.check();
		await siteStagingPage.blogsCheckbox.check();
		await siteStagingPage.saveButton.click();

		await waitForAlert(page, 'Local staging is successfully enabled.');

		await performLogout(page);
		await performLoginViaApi({page, screenName: 'test'});

		await usersAndOrganizationsPage.goToUsers(false);
		await (
			await usersAndOrganizationsPage.usersTableRowActions(
				userAccount.alternateName
			)
		).click();
		await usersAndOrganizationsPage.deletePersonalDataMenuItem.click();

		await waitForAlert(page);

		await expect(
			personalDataErasurePage.selectAllItemsOnPageCheckbox
		).toBeVisible();

		await personalDataErasurePage.documentsAndMediaRadioButton.check();
		await personalDataErasurePage
			.objectCheckBox(attachment1.id, attachment1.fileName, false)
			.check();
		await personalDataErasurePage
			.objectCheckBox(attachment2.id, attachment2.fileName, false)
			.check();
		await personalDataErasurePage.actionsButton.click();
		await personalDataErasurePage.deleteMenuItem.click();

		await expect(
			personalDataErasurePage.objectCheckBox(
				attachment1.id,
				attachment1.fileName,
				false
			)
		).not.toBeVisible();
		await expect(
			personalDataErasurePage.objectCheckBox(
				attachment2.id,
				attachment2.fileName,
				false
			)
		).not.toBeVisible();

		await waitForAlert(page);

		await personalDataErasurePage.blogsRadioButton.check();
		await personalDataErasurePage
			.objectCheckBox(blog1.id, blog1.headline, false)
			.check();
		await personalDataErasurePage
			.objectCheckBox(blog2.id, blog2.headline, false)
			.check();
		await personalDataErasurePage.actionsButton.click();
		await personalDataErasurePage.deleteMenuItem.click();

		await expect(
			personalDataErasurePage.objectCheckBox(
				blog1.id,
				blog1.headline,
				false
			)
		).not.toBeVisible();
		await expect(
			personalDataErasurePage.objectCheckBox(
				blog2.id,
				blog2.headline,
				false
			)
		).not.toBeVisible();

		await waitForAlert(page);

		await page.goto(`/group/${site.name}${PORTLET_URLS.blogs}`);

		await expect(page.getByText(userAccount.name)).toHaveCount(2);
		await expect(page.getByText(blog1.headline)).toHaveCount(1);
		await expect(page.getByText(blog2.headline)).toHaveCount(1);

		await page.goto(`/group/${site.name}-staging${PORTLET_URLS.blogs}`);

		await expect(page.getByText(userAccount.name)).toHaveCount(0);
		await expect(page.getByText(blog1.headline)).toHaveCount(0);
		await expect(page.getByText(blog2.headline)).toHaveCount(0);

		await page.goto(`/group/${site.name}${PORTLET_URLS.documentLibrary}`);

		await expect(page.getByText(userAccount.name)).toHaveCount(2);
		await expect(page.getByText(attachment1.title)).toHaveCount(1);
		await expect(page.getByText(attachment2.title)).toHaveCount(1);

		await page.goto(
			`/group/${site.name}-staging${PORTLET_URLS.documentLibrary}`
		);

		await expect(page.getByText(userAccount.name)).toHaveCount(0);
		await expect(page.getByText(attachment1.title)).toHaveCount(0);
		await expect(page.getByText(attachment2.title)).toHaveCount(0);
	}
);

testAdmin(
	'Can delete a related asset',
	{tag: '@LPD-51202'},
	async ({
		apiHelpers,
		messageBoardsEditThreadPage,
		messageBoardsPage,
		page,
		personalDataErasurePage,
		userAssociatedDataEditMessageBoardThreadPage,
		userAssociatedDataMessageBoardPage,
		usersAndOrganizationsPage,
	}) => {
		test.setTimeout(120000);

		page.on('dialog', (dialog) => {
			dialog.accept().catch(() => {});
		});

		const userAccount =
			await apiHelpers.headlessAdminUser.postUserAccount();

		userData[userAccount.alternateName] = {
			name: userAccount.givenName,
			password: 'test',
			surname: userAccount.familyName,
		};

		const role =
			await apiHelpers.headlessAdminUser.getRoleByName('Administrator');

		await apiHelpers.headlessAdminUser.postRoleByExternalReferenceCodeUserAccountAssociation(
			role.externalReferenceCode,
			userAccount.id
		);

		await performLogout(page);
		await performLoginViaApi({page, screenName: userAccount.alternateName});

		const site = await apiHelpers.headlessSite.createSite({
			name: getRandomString(),
		});

		apiHelpers.data.push({id: site.id, type: 'site'});

		const blog = await apiHelpers.headlessDelivery.postBlog(site.id, {
			headline: 'Blog' + getRandomInt(),
		});

		const document = await apiHelpers.headlessDelivery.postDocument(
			site.id,
			createReadStream(
				path.join(__dirname, '/dependencies/attachment.txt')
			)
		);

		const threadSubject = 'Thread' + getRandomInt();

		await messageBoardsEditThreadPage.gotoAndPublishNewBasicThread(
			threadSubject,
			getRandomString(),
			site.friendlyUrlPath
		);

		await waitForAlert(page);

		await userAssociatedDataMessageBoardPage.actionButton.click();
		await userAssociatedDataMessageBoardPage.editMenuItem.click();

		await expect(
			userAssociatedDataEditMessageBoardThreadPage.relatedAssetsButton
		).toBeVisible();

		await userAssociatedDataEditMessageBoardThreadPage.relatedAssetsButton.click();

		await expect(async () => {
			await userAssociatedDataEditMessageBoardThreadPage.selectButton.click();
			await expect(
				userAssociatedDataEditMessageBoardThreadPage.blogEntryMenuItem
			).toBeVisible();
		}).toPass();

		await userAssociatedDataEditMessageBoardThreadPage.blogEntryMenuItem.click();

		await expect(
			await userAssociatedDataEditMessageBoardThreadPage.tableRowCheckBox(
				blog.headline
			)
		).toBeVisible();

		await (
			await userAssociatedDataEditMessageBoardThreadPage.tableRowCheckBox(
				blog.headline
			)
		).check();
		await userAssociatedDataEditMessageBoardThreadPage.doneButton.click();

		await expect(async () => {
			await userAssociatedDataEditMessageBoardThreadPage.selectButton.click();
			await expect(
				userAssociatedDataEditMessageBoardThreadPage.basicDocumentMenuItem
			).toBeVisible();
		}).toPass();

		await userAssociatedDataEditMessageBoardThreadPage.basicDocumentMenuItem.click();

		await expect(
			await userAssociatedDataEditMessageBoardThreadPage.tableRowCheckBox(
				document.title
			)
		).toBeVisible();

		await (
			await userAssociatedDataEditMessageBoardThreadPage.tableRowCheckBox(
				document.title
			)
		).check();
		await userAssociatedDataEditMessageBoardThreadPage.doneButton.click();
		await userAssociatedDataEditMessageBoardThreadPage.publishButton.click();

		await performLogout(page);
		await performLoginViaApi({page, screenName: 'test'});

		await usersAndOrganizationsPage.goToUsers(false);
		await (
			await usersAndOrganizationsPage.usersTableRowActions(
				userAccount.alternateName
			)
		).click();
		await usersAndOrganizationsPage.deletePersonalDataMenuItem.click();

		await waitForAlert(page);

		await personalDataErasurePage.documentsAndMediaRadioButton.check();
		await (
			await personalDataErasurePage.userAssociatedDataTableRowCheckBox(
				document.fileName
			)
		).check();
		await personalDataErasurePage.actionsButton.click();
		await personalDataErasurePage.deleteMenuItem.click();

		await waitForAlert(page);

		await messageBoardsPage.goto(site.friendlyUrlPath);

		await userAssociatedDataMessageBoardPage
			.threadSubjectLink(threadSubject)
			.click();

		await expect(page.getByText(blog.headline)).toBeVisible();
		await expect(page.getByText(document.title)).toHaveCount(0);
	}
);

testAdmin(
	'Can publish to live a deleted live entry',
	{tag: '@LPD-55588'},
	async ({
		apiHelpers,
		blogsPage,
		page,
		personalDataErasurePage,
		productMenuPage,
		siteStagingPage,
		usersAndOrganizationsPage,
	}) => {
		page.on('dialog', (dialog) => {
			dialog.accept();
		});

		const userAccount =
			await apiHelpers.headlessAdminUser.postUserAccount();

		userData[userAccount.alternateName] = {
			name: userAccount.givenName,
			password: 'test',
			surname: userAccount.familyName,
		};

		const role =
			await apiHelpers.headlessAdminUser.getRoleByName('Administrator');

		await apiHelpers.headlessAdminUser.postRoleByExternalReferenceCodeUserAccountAssociation(
			role.externalReferenceCode,
			userAccount.id
		);

		const site = await apiHelpers.headlessSite.createSite({
			name: 'Site' + getRandomInt(),
		});

		apiHelpers.data.push({id: site.id, type: 'site'});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			siteId: site.id,
			title: 'Page' + getRandomInt(),
		});

		await performLogout(page);
		await performLoginViaApi({page, screenName: userAccount.alternateName});

		const blog = await apiHelpers.headlessDelivery.postBlog(site.id, {
			headline: getRandomString(),
		});

		await page.goto(`/group/${site.name}/${layout.friendlyUrlPath}`);

		await productMenuPage.openProductMenuIfClosed();
		await productMenuPage.publishingButton.click();
		await productMenuPage.stagingMenuItem.click();
		await siteStagingPage.localStagingCheckbox.check();
		await siteStagingPage.blogsCheckbox.check();
		await siteStagingPage.saveButton.click();

		await waitForAlert(page, 'Local staging is successfully enabled.');

		await performLogout(page);
		await performLoginViaApi({page, screenName: 'test'});

		await usersAndOrganizationsPage.goToUsers(false);
		await (
			await usersAndOrganizationsPage.usersTableRowActions(
				userAccount.alternateName
			)
		).click();
		await usersAndOrganizationsPage.deletePersonalDataMenuItem.click();

		await expect(
			personalDataErasurePage.selectAllItemsOnPageCheckbox
		).toBeVisible();

		await personalDataErasurePage.blogsRadioButton.check();
		await personalDataErasurePage
			.objectCheckBox(blog.id, blog.headline, true)
			.check();
		await personalDataErasurePage.actionsButton.click();
		await personalDataErasurePage.menuItemDelete.click();

		await expect(
			personalDataErasurePage.selectAllItemsOnPageCheckbox
		).toBeVisible();

		await page.goto(`/group/${site.name}${PORTLET_URLS.blogs}`);

		await expect(blogsPage.blogName(blog.headline)).toHaveCount(0);

		await page.goto(`/group/${site.name}-staging${PORTLET_URLS.blogs}`);

		await expect(blogsPage.blogName(blog.headline)).toHaveCount(1);

		await blogsPage.goToBlogEntryAction('Publish to Live', blog.title);
		await blogsPage.successMessage.waitFor();

		await page.goto(`/group/${site.name}${PORTLET_URLS.blogs}`);

		await expect(blogsPage.blogName(blog.headline)).toHaveCount(1);
	}
);

testAdmin(
	'Can filter and view data',
	{tag: '@LPD-56386'},
	async ({
		apiHelpers,
		contactsCenterPage,
		formBuilderPage,
		formBuilderSidePanelPage,
		page,
		personalDataErasurePage,
		userAssociatedDataFormPage,
		usersAndOrganizationsPage,
	}) => {
		testAdmin.setTimeout(90000);

		page.on('dialog', (dialog) => {
			dialog.accept();
		});

		const userAccount =
			await apiHelpers.headlessAdminUser.postUserAccount();

		userData[userAccount.alternateName] = {
			name: userAccount.givenName,
			password: 'test',
			surname: userAccount.familyName,
		};

		const role =
			await apiHelpers.headlessAdminUser.getRoleByName('Administrator');

		await apiHelpers.headlessAdminUser.postRoleByExternalReferenceCodeUserAccountAssociation(
			role.externalReferenceCode,
			userAccount.id
		);

		await performLogout(page);
		await performLoginViaApi({page, screenName: userAccount.alternateName});

		const site = await apiHelpers.headlessSite.createSite({
			name: getRandomString(),
		});

		apiHelpers.data.push({id: site.id, type: 'site'});

		const formTitle = 'Form' + getRandomInt();
		const textFieldLabel = 'Text Field';

		await formBuilderPage.goToNew(site.friendlyUrlPath);
		await formBuilderPage.fillFormTitle(formTitle);
		await formBuilderPage.formDescription.fill(getRandomString());
		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');
		await formBuilderSidePanelPage.label.fill(textFieldLabel);
		await formBuilderPage.publishButton.click();

		await waitForAlert(page);

		const formPageName = 'form-page-' + getRandomInt();

		await userAssociatedDataFormPage.createFormPage(
			apiHelpers,
			formTitle,
			site,
			{
				title: formPageName,
			}
		);

		await page.goto(`/web/${site.name}/${formPageName}`);

		await expect(
			userAssociatedDataFormPage.formWidgetTextFieldLabel(textFieldLabel)
		).toBeVisible();

		await userAssociatedDataFormPage
			.formWidgetTextFieldLabel(textFieldLabel)
			.fill(`${textFieldLabel} value`);
		await userAssociatedDataFormPage.formWidgetSubmitButton.click();

		await waitForAlert(page);

		const contactsCenterPageName = 'contact-center-' + getRandomInt();

		await contactsCenterPage.createPage(apiHelpers, site.id, {
			title: contactsCenterPageName,
		});

		await page.goto(`/web/${site.name}/${contactsCenterPageName}`);

		await contactsCenterPage.addContactButton.click();

		const name = getRandomString();
		const email = `${getRandomString()}@liferay.com`;

		await contactsCenterPage.nameInput.fill(name);
		await contactsCenterPage.emailAddressInput.fill(email);
		await contactsCenterPage.saveButton.click();

		await expect(contactsCenterPage.successMessage).toBeVisible();

		const contentStructureId =
			await getBasicWebContentStructureId(apiHelpers);

		const webContent =
			await apiHelpers.jsonWebServicesJournal.addWebContent({
				ddmStructureId: contentStructureId,
				groupId: site.id,
			});

		await performLogout(page);
		await performLoginViaApi({page, screenName: 'test'});

		await usersAndOrganizationsPage.goToUsers(false);
		await (
			await usersAndOrganizationsPage.usersTableRowActions(
				userAccount.alternateName
			)
		).click();
		await usersAndOrganizationsPage.deletePersonalDataMenuItem.click();

		await expect(
			personalDataErasurePage.selectAllItemsOnPageCheckbox
		).toBeVisible();

		await personalDataErasurePage.contactsCenterRadioButton.check();

		await expect(page.getByText(name)).toHaveCount(1);
		await expect(page.getByText(email)).toHaveCount(1);

		await personalDataErasurePage.regularSitesRadioButton.check();

		await expect(personalDataErasurePage.formsRadioButton).toBeVisible();

		await personalDataErasurePage.formsRadioButton.check();

		await expect(page.getByText(formTitle)).toHaveCount(1);

		await personalDataErasurePage.webContentRadioButton.check();

		await expect(page.getByText(webContent.title)).toHaveCount(1);
	}
);

testAdmin(
	'Remaining items count is accurate',
	{tag: '@LPD-56386'},
	async ({
		apiHelpers,
		page,
		personalDataErasurePage,
		usersAndOrganizationsPage,
	}) => {
		page.on('dialog', (dialog) => {
			dialog.accept().catch(() => {});
		});

		const userAccount =
			await apiHelpers.headlessAdminUser.postUserAccount();

		userData[userAccount.alternateName] = {
			name: userAccount.givenName,
			password: 'test',
			surname: userAccount.familyName,
		};

		const role =
			await apiHelpers.headlessAdminUser.getRoleByName('Administrator');

		await apiHelpers.headlessAdminUser.postRoleByExternalReferenceCodeUserAccountAssociation(
			role.externalReferenceCode,
			userAccount.id
		);

		await performLogout(page);
		await performLoginViaApi({page, screenName: userAccount.alternateName});

		const site = await apiHelpers.headlessSite.createSite({
			name: getRandomString(),
		});

		apiHelpers.data.push({id: site.id, type: 'site'});

		const folder = await apiHelpers.headlessDelivery.postDocumentFolder(
			site.id
		);

		const attachment = await apiHelpers.headlessDelivery.postDocument(
			site.id,
			createReadStream(
				path.join(__dirname, '/dependencies/attachment.docx')
			)
		);

		const blog = await apiHelpers.headlessDelivery.postBlog(site.id, {
			headline: getRandomString(),
		});

		await performLogout(page);
		await performLoginViaApi({page, screenName: 'test'});

		await usersAndOrganizationsPage.goToUsers(false);
		await (
			await usersAndOrganizationsPage.usersTableRowActions(
				userAccount.alternateName
			)
		).click();
		await usersAndOrganizationsPage.deletePersonalDataMenuItem.click();

		await expect(
			personalDataErasurePage.selectAllItemsOnPageCheckbox
		).toBeVisible();
		await expect(
			personalDataErasurePage.remainingItemsCount('3')
		).toBeVisible();
		await expect(
			await personalDataErasurePage.allApplicationsDataTableRowCount(
				'1',
				'Blogs'
			)
		).toBeVisible();
		await expect(
			await personalDataErasurePage.allApplicationsDataTableRowCount(
				'2',
				'Documents and Media'
			)
		).toBeVisible();
		await expect(
			personalDataErasurePage.objectRadioButtonLabelCount('Blogs', '1')
		).toBeVisible();
		await expect(
			personalDataErasurePage.objectRadioButtonLabelCount(
				'Documents and Media',
				'2'
			)
		).toBeVisible();

		await personalDataErasurePage.documentsAndMediaRadioButton.check();

		await expect(
			personalDataErasurePage.selectAllItemsOnPageCheckbox
		).toBeVisible();

		await personalDataErasurePage
			.objectCheckBox(folder.id, folder.name, true)
			.check();
		await personalDataErasurePage
			.objectCheckBox(attachment.id, attachment.fileName, true)
			.check();
		await personalDataErasurePage.actionsButton.click();
		await personalDataErasurePage.deleteMenuItem.click();

		await waitForAlert(page);

		await expect(
			personalDataErasurePage.remainingItemsCount('1')
		).toBeVisible();

		await personalDataErasurePage.blogsRadioButton.check();

		await expect(
			personalDataErasurePage.selectAllItemsOnPageCheckbox
		).toBeVisible();

		await personalDataErasurePage
			.objectCheckBox(blog.id, blog.headline, true)
			.check();
		await personalDataErasurePage.actionsButton.click();
		await personalDataErasurePage.deleteMenuItem.click();

		await waitForAlert(page);

		await expect(personalDataErasurePage.anonymizeButton).toBeVisible();

		await personalDataErasurePage.reviewDataLink.click();

		await expect(personalDataErasurePage.emptyMessage).toBeVisible();
		await expect(
			personalDataErasurePage.objectRadioButtonLabelCount('Blogs', '0')
		).toBeVisible();
		await expect(
			personalDataErasurePage.objectRadioButtonLabelCount(
				'Documents and Media',
				'0'
			)
		).toBeVisible();
		await expect(
			personalDataErasurePage.remainingItemsCount('0')
		).toBeVisible();
	}
);

testAdmin(
	'Can delete an export process',
	{tag: '@LPD-56386'},
	async ({
		apiHelpers,
		exportUserDataPage,
		page,
		usersAndOrganizationsPage,
	}) => {
		test.setTimeout(120000);

		page.on('dialog', (dialog) => {
			dialog.accept().catch(() => {});
		});

		const userAccount =
			await apiHelpers.headlessAdminUser.postUserAccount();

		userData[userAccount.alternateName] = {
			name: userAccount.givenName,
			password: 'test',
			surname: userAccount.familyName,
		};

		const role =
			await apiHelpers.headlessAdminUser.getRoleByName('Administrator');

		await apiHelpers.headlessAdminUser.postRoleByExternalReferenceCodeUserAccountAssociation(
			role.externalReferenceCode,
			userAccount.id
		);

		await performLogout(page);
		await performLoginViaApi({page, screenName: userAccount.alternateName});

		const site = await apiHelpers.headlessSite.createSite({
			name: 'Site' + getRandomInt(),
		});

		apiHelpers.data.push({id: site.id, type: 'site'});

		await apiHelpers.headlessDelivery.postBlog(site.id, {
			headline: getRandomString(),
		});

		const contentStructureId =
			await getBasicWebContentStructureId(apiHelpers);

		await apiHelpers.jsonWebServicesJournal.addWebContent({
			ddmStructureId: contentStructureId,
			groupId: site.id,
		});

		await apiHelpers.jsonWebServicesMBApiHelper.addMessage({
			groupId: site.id,
		});

		await performLogout(page);
		await performLoginViaApi({page, screenName: 'test'});

		await usersAndOrganizationsPage.goToUsers(false);
		await (
			await usersAndOrganizationsPage.usersTableRowActions(
				userAccount.alternateName
			)
		).click();
		await usersAndOrganizationsPage.exportPersonalDataItem.click();
		await exportUserDataPage.addExportProcessesButton.click();

		await exportUserDataPage.blogsCheckbox.check();
		await exportUserDataPage.webContentCheckbox.check();
		await exportUserDataPage.messageBoardsCheckbox.check();
		await exportUserDataPage.exportButton.click();

		await waitForAlert(page);

		await expect(exportUserDataPage.blogsStatus).toBeVisible();
		await expect(exportUserDataPage.webContentStatus).toBeVisible();
		await expect(exportUserDataPage.messageBoardsStatus).toBeVisible();

		await expect(async () => {
			await (
				await exportUserDataPage.rowActions('Blogs', 0, false)
			).click();

			await expect(exportUserDataPage.deleteLink).toBeVisible();

			await exportUserDataPage.deleteLink.click();

			await expect(exportUserDataPage.blogsStatus).not.toBeVisible();
		}).toPass();

		await expect(async () => {
			await (
				await exportUserDataPage.rowActions('Message Boards', 0, false)
			).click();

			await expect(exportUserDataPage.deleteLink).toBeVisible();

			await exportUserDataPage.deleteLink.click();

			await expect(
				exportUserDataPage.messageBoardsStatus
			).not.toBeVisible();
		}).toPass();

		await expect(async () => {
			await (
				await exportUserDataPage.rowActions('Web Content', 0, false)
			).click();

			await expect(exportUserDataPage.deleteLink).toBeVisible();

			await exportUserDataPage.deleteLink.click();

			await expect(exportUserDataPage.webContentStatus).not.toBeVisible();
		}).toPass();

		await expect(
			exportUserDataPage.emptyExportProcessesMessage
		).toBeVisible();
	}
);
