/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {changeTrackingPagesTest} from '../../../fixtures/changeTrackingPagesTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';

const test = mergeTests(
	apiHelpersTest,
	changeTrackingPagesTest,
	usersAndOrganizationsPagesTest
);

test(
	'Does not list a publication scoped role in the regular roles selector',
	{tag: '@LPD-64610'},
	async ({
		apiHelpers,
		changeTrackingPage,
		ctCollection,
		editUserPage,
		usersAndOrganizationsPage,
	}) => {
		const productionRole = await apiHelpers.headlessAdminUser.postRole({
			name: getRandomString(),
		});

		try {
			await changeTrackingPage.workOnPublication(ctCollection);

			const publicationRole = await apiHelpers.headlessAdminUser.postRole(
				{
					name: getRandomString(),
				}
			);

			await usersAndOrganizationsPage.goToUsers();
			await usersAndOrganizationsPage.goToUser('Test Test');

			await editUserPage.rolesLink.click();

			await clickAndExpectToBeVisible({
				target: editUserPage.selectRegularRolesSearchInput,
				trigger: editUserPage.selectRegularRolesButton,
			});

			await editUserPage.selectRegularRolesSearchInput.fill(
				productionRole.name
			);
			await editUserPage.selectRegularRolesSearchInput.press('Enter');

			await expect(
				editUserPage.selectRegularRolesFrame.getByText(
					productionRole.name,
					{exact: true}
				)
			).toBeVisible();

			await editUserPage.selectRegularRolesSearchInput.fill(
				publicationRole.name
			);
			await editUserPage.selectRegularRolesSearchInput.press('Enter');

			await expect(
				editUserPage.selectRegularRolesFrame.getByText(
					publicationRole.name,
					{exact: true}
				)
			).toHaveCount(0);
		}
		finally {
			await apiHelpers.headlessChangeTracking.checkoutCTCollection(0);

			await apiHelpers.headlessAdminUser.deleteRole(productionRole.id);
		}
	}
);

test(
	'Does not list a publication scoped site in the sites selector',
	{tag: '@LPD-64610'},
	async ({
		apiHelpers,
		changeTrackingPage,
		ctCollection,
		editUserPage,
		usersAndOrganizationsPage,
	}) => {
		const productionSite = await apiHelpers.headlessAdminSite.postSite({
			name: getRandomString(),
		});

		try {
			await changeTrackingPage.workOnPublication(ctCollection);

			const publicationSite = await apiHelpers.headlessAdminSite.postSite(
				{
					name: getRandomString(),
				}
			);

			await usersAndOrganizationsPage.goToUsers();
			await usersAndOrganizationsPage.goToUser('Test Test');

			await editUserPage.membershipsLink.click();

			await clickAndExpectToBeVisible({
				target: editUserPage.selectSiteSearchBar,
				trigger: editUserPage.selectSiteButton,
			});

			await editUserPage.selectSiteSearchBar.fill(productionSite.name);
			await editUserPage.selectSiteSearchBarButton.click();

			await expect(
				editUserPage.selectSiteFrameSiteLink(productionSite.name)
			).toBeVisible();

			await editUserPage.selectSiteSearchBar.fill(publicationSite.name);
			await editUserPage.selectSiteSearchBarButton.click();

			await expect(
				editUserPage.selectSiteFrameSiteLink(publicationSite.name)
			).toHaveCount(0);
		}
		finally {
			await apiHelpers.headlessChangeTracking.checkoutCTCollection(0);

			await apiHelpers.headlessAdminSite.deleteSite(
				productionSite.externalReferenceCode
			);
		}
	}
);

test(
	'Does not list a publication scoped user group in the user groups selector',
	{tag: '@LPD-64610'},
	async ({
		apiHelpers,
		changeTrackingPage,
		ctCollection,
		editUserPage,
		usersAndOrganizationsPage,
	}) => {
		const productionUserGroup =
			await apiHelpers.headlessAdminUser.postUserGroup({
				name: getRandomString(),
			});

		try {
			await changeTrackingPage.workOnPublication(ctCollection);

			const publicationUserGroup =
				await apiHelpers.headlessAdminUser.postUserGroup({
					name: getRandomString(),
				});

			await usersAndOrganizationsPage.goToUsers();
			await usersAndOrganizationsPage.goToUser('Test Test');

			await editUserPage.membershipsLink.click();

			await clickAndExpectToBeVisible({
				target: editUserPage.selectUserGroupSearchBar,
				trigger: editUserPage.selectUserGroupsButton,
			});

			await editUserPage.selectUserGroupSearchBar.fill(
				productionUserGroup.name
			);
			await editUserPage.selectUserGroupSearchBar.press('Enter');

			await expect(
				editUserPage.selectUserGroupFrameEntry(productionUserGroup.name)
			).toBeVisible();

			await editUserPage.selectUserGroupSearchBar.fill(
				publicationUserGroup.name
			);
			await editUserPage.selectUserGroupSearchBar.press('Enter');

			await expect(
				editUserPage.selectUserGroupFrameEntry(
					publicationUserGroup.name
				)
			).toHaveCount(0);
		}
		finally {
			await apiHelpers.headlessChangeTracking.checkoutCTCollection(0);

			await apiHelpers.headlessAdminUser.deleteUserGroup(
				productionUserGroup.id
			);
		}
	}
);

test(
	'Does not list a publication scoped asset library in the asset libraries selector',
	{tag: '@LPD-64610'},
	async ({
		apiHelpers,
		changeTrackingPage,
		ctCollection,
		editUserPage,
		usersAndOrganizationsPage,
	}) => {
		const productionAssetLibraryName = getRandomString();

		const productionDepotEntry =
			await apiHelpers.jsonWebServicesDepot.addDepotEntry(
				productionAssetLibraryName
			);

		try {
			await changeTrackingPage.workOnPublication(ctCollection);

			const publicationAssetLibraryName = getRandomString();

			await apiHelpers.jsonWebServicesDepot.addDepotEntry(
				publicationAssetLibraryName
			);

			await usersAndOrganizationsPage.goToUsers();
			await usersAndOrganizationsPage.goToUser('Test Test');

			await editUserPage.membershipsLink.click();

			await clickAndExpectToBeVisible({
				target: editUserPage.selectAssetLibrariesSearchBar,
				trigger: editUserPage.selectAssetLibrariesButton,
			});

			await editUserPage.selectAssetLibrariesSearchBar.fill(
				productionAssetLibraryName
			);
			await editUserPage.selectAssetLibrariesSearchBar.press('Enter');

			await expect(
				editUserPage.selectAssetLibrariesFrameEntry(
					productionAssetLibraryName
				)
			).toBeVisible();

			await editUserPage.selectAssetLibrariesSearchBar.fill(
				publicationAssetLibraryName
			);
			await editUserPage.selectAssetLibrariesSearchBar.press('Enter');

			await expect(
				editUserPage.selectAssetLibrariesFrameEntry(
					publicationAssetLibraryName
				)
			).toHaveCount(0);
		}
		finally {
			await apiHelpers.headlessChangeTracking.checkoutCTCollection(0);

			await apiHelpers.jsonWebServicesDepot.deleteDepotEntry(
				productionDepotEntry.depotEntryId
			);
		}
	}
);
