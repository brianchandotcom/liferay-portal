/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';
import {createReadStream} from 'fs';
import path from 'node:path';

import {contactsCenterPagesTest} from '../../fixtures/contactsCenterPagesTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';
import getRandomString from '../../utils/getRandomString';
import performLogin, {performLogout, userData} from '../../utils/performLogin';
import {PORTLET_URLS} from '../../utils/portletUrls';
import getBasicWebContentStructureId from '../../utils/structured-content/getBasicWebContentStructureId';

export const test = mergeTests(
	contactsCenterPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest({screenName: 'demo.company.admin'}),
	usersAndOrganizationsPagesTest
);

export const testAdmin = mergeTests(
	contactsCenterPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest(),
	usersAndOrganizationsPagesTest
);

async function checkUsernameAssociatedWithObjects(
	counters: {
		blog: number;
		document: number;
		message: number;
		webContent: number;
		wiki: number;
	},
	page: Page,
	userAccountName: string,
	siteName: string,
	wikiNodeName: string
) {
	const portletBaseURL = `/group/${siteName}`;

	await page.goto(`${portletBaseURL}${PORTLET_URLS.journal}`);
	await expect(page.getByText(userAccountName)).toHaveCount(
		counters.webContent
	);

	await page.goto(`${portletBaseURL}${PORTLET_URLS.blogs}`);
	await expect(page.getByText(userAccountName)).toHaveCount(counters.blog);

	await page.goto(`${portletBaseURL}${PORTLET_URLS.documentLibrary}`);
	await expect(page.getByText(userAccountName)).toHaveCount(
		counters.document
	);

	await page.goto(`${portletBaseURL}${PORTLET_URLS.messageBoardsAdmin}`);
	await expect(page.getByText(userAccountName)).toHaveCount(counters.message);

	await page.goto(`${portletBaseURL}${PORTLET_URLS.wikiAdmin}`);
	await page.getByRole('link', {name: wikiNodeName}).click();
	await expect(page.getByText(userAccountName)).toHaveCount(counters.wiki);
}

test.describe('LPD-25858 Refactor of GDPR#CanExportMultipleEntries', () => {
	test('Exports multiple entries from different applications', async ({
		apiHelpers,
		contactsCenterPage,
		exportUserDataPage,
		page,
		usersAndOrganizationsPage,
	}) => {
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

		await performLogin(page, 'test');

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
	});
});

testAdmin.describe('LPD-27068 Refactor of GDPR#CanAnonymizeAllEntries', () => {
	testAdmin(
		'Adds entries from different applications and anonymizes all entries via Select All',
		async ({
			apiHelpers,
			page,
			personalDataErasurePage,
			usersAndOrganizationsPage,
		}) => {
			const userAccount =
				await apiHelpers.headlessAdminUser.postUserAccount();

			userData[userAccount.alternateName] = {
				name: userAccount.givenName,
				password: 'test',
				surname: userAccount.familyName,
			};

			await apiHelpers.headlessAdminUser.postRoleByExternalReferenceCodeUserAccountAssociation(
				'Administrator',
				userAccount.id
			);

			await performLogout(page);

			await performLogin(page, userAccount.alternateName);

			const site = await apiHelpers.headlessSite.createSite({
				name: getRandomString(),
			});

			apiHelpers.data.push({id: site.id, type: 'site'});

			await apiHelpers.headlessDelivery.postBlog(site.id);

			const contentStructureId =
				await getBasicWebContentStructureId(apiHelpers);

			const folder = await apiHelpers.jsonWebServicesJournal.addFolder({
				groupId: site.id,
			});

			await apiHelpers.jsonWebServicesJournal.addWebContent({
				ddmStructureId: contentStructureId,
				groupId: site.id,
			});

			await apiHelpers.jsonWebServicesJournal.addWebContent({
				ddmStructureId: contentStructureId,
				folderId: folder.folderId,
				groupId: site.id,
			});

			await apiHelpers.headlessDelivery.postDocument(
				site.id,
				createReadStream(
					path.join(__dirname, '/dependencies/attachment.txt')
				)
			);

			await apiHelpers.jsonWebServicesMBApiHelper.addMessage({
				groupId: site.id,
			});

			const wikiNode = await apiHelpers.headlessDelivery.postWikiNode(
				site.id
			);

			await apiHelpers.headlessDelivery.postWikiPage(wikiNode.id);

			await performLogout(page);

			await performLogin(page, 'test');

			await page.goto(`/web/${site.name}`);

			await checkUsernameAssociatedWithObjects(
				{blog: 1, document: 1, message: 1, webContent: 2, wiki: 1},
				page,
				userAccount.name,
				site.name,
				wikiNode.name
			);

			await usersAndOrganizationsPage.goToUsers(false);
			await (
				await usersAndOrganizationsPage.usersTableRowActions(
					userAccount.alternateName
				)
			).click();

			page.on('dialog', (dialog) => {
				dialog.accept().catch(() => {});
			});

			await usersAndOrganizationsPage.deletePersonalDataMenuItem.click();

			await personalDataErasurePage.selectAllItemsOnPageCheckbox.check();
			await personalDataErasurePage.allSelectedButton.click();

			await personalDataErasurePage.anonymizeMenuItem.click();

			await personalDataErasurePage.anonymizeButton.click();

			await checkUsernameAssociatedWithObjects(
				{blog: 0, document: 0, message: 0, webContent: 0, wiki: 0},
				page,
				userAccount.name,
				site.name,
				wikiNode.name
			);
		}
	);
});
