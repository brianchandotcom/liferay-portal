/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import {siteSettingsPagesTest} from '../../../fixtures/siteSettingsPagesTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import {
	performLoginViaApi,
	performLogout,
	userData,
} from '../../../utils/performLogin';
import {waitForAlert} from '../../../utils/waitForAlert';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	loginTest(),
	isolatedSiteTest,
	pageEditorPagesTest,
	pagesAdminPagesTest,
	siteSettingsPagesTest,
	usersAndOrganizationsPagesTest
);

let layout;

test.beforeEach(async ({apiHelpers, pageEditorPage, pagesAdminPage, site}) => {
	await pagesAdminPage.goto(site.friendlyUrlPath);

	layout = await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		options: {type: 'content'},
		title: 'Site Page',
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	await pageEditorPage.addWidget('Community', 'My Sites');

	await pageEditorPage.publishPage();
});

test('Assert a user can navigate to a Site public page via My Sites widget', async ({
	context,
	page,
	site,
}) => {
	await page.goto(`/web${site.friendlyUrlPath}`);

	await clickAndExpectToBeVisible({
		autoClick: true,
		target: page.getByRole('menuitem', {name: 'Go to Pages'}),
		trigger: page
			.locator(
				`[id="_com_liferay_site_my_sites_web_portlet_MySitesPortlet_ocerSearchContainer_-${site.name}"]`
			)
			.getByLabel('Show Actions'),
	});

	const [page2] = await Promise.all([context.waitForEvent('page')]);

	await page2.waitForLoadState();

	await expect(
		page2.getByRole('menuitem', {name: 'Site Page'})
	).toBeVisible();
});

test('Assert a user cannot join or leave a site with manual membership management disabled in My Sites widget', async ({
	apiHelpers,
	page,
	site,
	siteSettingsPage,
}) => {
	const user = await apiHelpers.headlessAdminUser.postUserAccount();

	userData[user.alternateName] = {
		name: user.givenName,
		password: 'test',
		surname: user.familyName,
	};

	const role =
		await apiHelpers.headlessAdminUser.getRoleByName('Administrator');

	await apiHelpers.headlessAdminUser.assignUserToRole(
		role.externalReferenceCode,
		user.id
	);

	await siteSettingsPage.goToSiteSetting(
		'Site Configuration',
		'Details',
		site.friendlyUrlPath
	);

	await page.getByLabel('Allow Manual Membership').click();

	await siteSettingsPage.saveButton.click();

	await waitForAlert(page, 'Success');

	await performLogout(page);

	await performLoginViaApi({page, screenName: user.alternateName});

	await page.goto(`/web${site.friendlyUrlPath}`);

	await page.waitForTimeout(500);

	const availableSitesLink = page.getByRole('link', {
		name: 'Available Sites',
	});

	await availableSitesLink.click();

	const showActionsLabel = page
		.locator(
			`[id="_com_liferay_site_my_sites_web_portlet_MySitesPortlet_ocerSearchContainer_-${site.name}"]`
		)
		.getByLabel('Show Actions');

	await expect(showActionsLabel).toBeHidden();

	await performLogout(page);
	await performLoginViaApi({page, screenName: 'test'});

	await page.goto(`/web${site.friendlyUrlPath}`);

	await page.waitForTimeout(500);

	await availableSitesLink.click();

	await expect(showActionsLabel).toBeHidden();
});

test('Assert joining sites with different membership types using My Sites portlet', async ({
	apiHelpers,
	page,
}) => {
	const privateSite = await apiHelpers.headlessAdminSite.postSite({
		membershipType: 'private',
		name: getRandomString(),
	});

	const openSite = await apiHelpers.headlessAdminSite.postSite({
		membershipType: 'open',
		name: getRandomString(),
	});

	const restrictedSite = await apiHelpers.headlessAdminSite.postSite({
		membershipType: 'restricted',
		name: getRandomString(),
	});

	const user = await apiHelpers.headlessAdminUser.postUserAccount();

	userData[user.alternateName] = {
		name: user.givenName,
		password: 'test',
		surname: user.familyName,
	};

	await performLogout(page);

	await performLoginViaApi({page, screenName: user.alternateName});

	await page.getByLabel(user.name).click();

	// Join open site and assert private site is hidden

	await page.getByRole('menuitem', {name: 'My Dashboard'}).click();

	await page.getByRole('link', {name: 'Available Sites'}).click();

	await page.waitForTimeout(500);

	await expect(page.getByText(privateSite.name)).toBeHidden();
	await expect(page.getByText(openSite.name)).toBeVisible();

	await page
		.locator(
			`[id="_com_liferay_site_my_sites_web_portlet_MySitesPortlet_ocerSearchContainer_-${openSite.name}"]`
		)
		.getByLabel('Show Actions')
		.click();

	await page.getByRole('menuitem', {name: 'Join'}).click();

	await waitForAlert(page, 'Success');

	// Join restricted site

	const restrictedSiteShowActionsLabel = page
		.locator(
			`[id="_com_liferay_site_my_sites_web_portlet_MySitesPortlet_ocerSearchContainer_-${restrictedSite.name}"]`
		)
		.getByLabel('Show Actions');

	await restrictedSiteShowActionsLabel.click();

	await page.getByRole('menuitem', {name: 'Request Membership'}).click();

	await page
		.getByLabel('Characters Maximum:')
		.fill('Please, allow me to join Restricted Site');

	await page.getByRole('button', {name: 'Save'}).click();

	await waitForAlert(page, 'Success');

	await performLogout(page);

	await performLoginViaApi({page, screenName: 'test'});

	await page.waitForTimeout(300);

	await page.goto(
		`/group/${restrictedSite.name}/~/control_panel/manage/-/site_memberships`
	);

	await page.waitForTimeout(300);

	await page.getByLabel('Options').click();

	await page
		.getByRole('menuitem', {name: 'View Membership Requests'})
		.click();

	await expect(
		page.getByRole('heading', {name: 'Membership Requests'})
	).toBeVisible();

	await page.getByLabel('More actions').click();

	await page.getByRole('menuitem', {name: 'Reply'}).click();

	await page
		.getByLabel('Characters Maximum:')
		.fill('You may join the Restricted Site');

	await page.getByRole('button', {name: 'Save'}).click();

	await waitForAlert(page, 'Success');

	await page.getByRole('link', {name: 'Approved'}).click();

	await expect(page.getByText(user.name)).toBeVisible();

	await performLogout(page);

	await performLoginViaApi({page, screenName: user.alternateName});

	// Assert user joined restricted and open sites

	await page.getByLabel(user.name).click();

	await page.getByRole('menuitem', {name: 'My Dashboard'}).click();

	await expect(page.getByText(openSite.name)).toBeVisible();
	await expect(page.getByText(restrictedSite.name)).toBeVisible();

	await restrictedSiteShowActionsLabel.click();

	await page.getByRole('menuitem', {name: 'Leave Site'}).click();

	await waitForAlert(page, 'Success');

	await page.reload();

	await expect(page.getByText(restrictedSite.name)).toBeHidden();

	await performLogout(page);

	await performLoginViaApi({page, screenName: 'test'});
});

test('Assert My Sites widget displays user memberships assigned in multiple ways', async ({
	apiHelpers,
	editUserPage,
	page,
	site,
	usersAndOrganizationsPage,
}) => {
	const user = await apiHelpers.headlessAdminUser.postUserAccount();

	userData[user.alternateName] = {
		name: user.givenName,
		password: 'test',
		surname: user.familyName,
	};

	const site2 = await apiHelpers.headlessAdminSite.postSite({
		name: getRandomString(),
	});

	// Assign site membership via Site Settings

	await page.goto(
		`/group/${site.name}/~/control_panel/manage/-/site_memberships`
	);

	await page.getByRole('button', {name: 'Add'}).click();

	await page
		.frameLocator('iframe[title="Assign Users to This Site"]')
		.getByLabel(user.alternateName)
		.check();

	await page.getByRole('button', {name: 'Done'}).click();

	await waitForAlert(page, 'Success');

	await expect(page.getByText(user.name)).toBeVisible();

	// Assign site membership via User Admin

	const role = await apiHelpers.headlessAdminUser.getRoleByName('Power User');

	await apiHelpers.headlessAdminUser.assignUserToRole(
		role.externalReferenceCode,
		user.id
	);

	await usersAndOrganizationsPage.goToUsers();

	await (
		await usersAndOrganizationsPage.usersTableRowLink(user.alternateName)
	).click();

	await editUserPage.membershipsLink.click();

	await editUserPage.selectSiteButton.click();

	await page.waitForTimeout(300);

	await page
		.frameLocator('iframe[title="Select Site"]')
		.getByRole('link', {exact: true, name: site2.name})
		.click();

	await usersAndOrganizationsPage.saveUserButton.click();

	await waitForAlert(page, 'Success');

	// Verify that both site memberships are displayed in My Sites widget

	await page.goto(`/web${site.friendlyUrlPath}`);

	await performLogout(page);

	await performLoginViaApi({page, screenName: user.alternateName});

	await page.getByLabel(user.name).click();

	await page.goto(`/web${site.friendlyUrlPath}`);

	await expect(
		page
			.locator(
				`[id="_com_liferay_site_my_sites_web_portlet_MySitesPortlet_ocerSearchContainer_-${site.name}"]`
			)
			.getByRole('link', {name: site.name})
	).toBeVisible();
	await expect(page.getByText(site2.name)).toBeVisible();

	await performLogout(page);
	await performLoginViaApi({page, screenName: 'test'});
});

test('Assert My Sites widget search shows active sites only', async ({
	apiHelpers,
	page,
	site,
}) => {
	const inactiveSite = await apiHelpers.headlessAdminSite.postSite({
		active: false,
		name: getRandomString(),
	});

	await page.goto(`/web${site.friendlyUrlPath}`);

	await page.waitForTimeout(500);

	const searchBar = page.getByPlaceholder('Search for');

	await searchBar.fill(site.name);
	await searchBar.press('Enter');

	await expect(
		page.getByRole('link', {exact: true, name: site.name})
	).toBeVisible();
	await expect(
		page
			.locator(
				`[id="_com_liferay_site_my_sites_web_portlet_MySitesPortlet_ocerSearchContainer_-${site.name}"]`
			)
			.getByText('User')
	).toHaveText('1 User');
	await expect(page.getByText('1 Result Found')).toBeVisible();

	await searchBar.fill(inactiveSite.name);
	await searchBar.press('Enter');

	await expect(
		page.getByRole('link', {exact: true, name: inactiveSite.name})
	).toBeHidden();
	await expect(page.getByText('0 Result Found')).toBeHidden();
});

test('Assert that pagination is working for My Sites widget', async ({
	apiHelpers,
	page,
	site,
}) => {
	for (let i = 1; i < 20; i++) {
		await apiHelpers.headlessAdminSite.postSite({
			externalReferenceCode: `Site${i}`,
			name: getRandomString(),
		});
	}

	await page.goto(`/web${site.friendlyUrlPath}`);

	await page.waitForTimeout(500);

	await expect(
		page.getByText('Showing 1 to 20 of 21 entries.')
	).toBeVisible();

	await page.getByLabel('Items per Page').click();

	await page.getByRole('option', {name: '40 Entries per Page'}).click();

	await expect(
		page.getByText('Showing 1 to 21 of 21 entries.')
	).toBeVisible();
});

test('Assert that site tags appear in My Sites widget', async ({
	page,
	site,
	siteSettingsPage,
}) => {
	await siteSettingsPage.goto(site.friendlyUrlPath);

	await page.getByRole('link', {name: 'Assets'}).click();

	await page.getByRole('menuitem', {name: 'Categorization'}).click();

	const tagInputField = page.getByLabel('Tags', {exact: true});

	const tags = ['apple', 'banana', 'mango'];

	for (const tag of tags) {
		await tagInputField.click();
		await tagInputField.fill(tag);
		await tagInputField.press('Enter');
	}

	await page.getByLabel('Topic', {exact: true}).click();

	await page.getByText('Save').click();

	await waitForAlert(page, `Success`);

	await page.goto(`/web${site.friendlyUrlPath}`);

	await page.waitForTimeout(500);

	for (const tag of tags) {
		await expect(page.getByText(tag)).toBeVisible();
	}
});
