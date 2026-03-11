/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import {performLoginViaApi, performLogout, userData} from '../../../utils/performLogin';
import {generateObjectFields} from './utils/generateObjectFields';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest,
	usersAndOrganizationsPagesTest
);

test(
	'LPD-78504 Can anonymize object entries',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		page,
		personalDataErasurePage,
		usersAndOrganizationsPage,
		viewObjectEntriesPage,
	}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';
		const textFieldName = objectFields[0].name;

		await apiHelpers.objectEntry.postObjectEntry(
			{[textFieldName]: 'Entry A'},
			applicationName
		);

		const userAccount =
			await apiHelpers.headlessAdminUser.postUserAccount();

		apiHelpers.data.push({id: userAccount.id, type: 'userAccount'});

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
		await performLoginViaApi({
			page,
			screenName: userAccount.alternateName,
		});

		for (const entryName of ['Entry B', 'Entry C', 'Entry D', 'Entry E']) {
			await apiHelpers.objectEntry.postObjectEntry(
				{[textFieldName]: entryName},
				applicationName
			);
		}

		await performLogout(page);
		await performLoginViaApi({page, screenName: 'test'});

		await usersAndOrganizationsPage.goToUsers(false);

		page.on('dialog', (dialog) => {
			dialog.accept().catch(() => {});
		});

		await expect(async () => {
			await (
				await usersAndOrganizationsPage.usersTableRowActions(
					userAccount.alternateName
				)
			).click();

			await expect(
				usersAndOrganizationsPage.deletePersonalDataMenuItem
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await usersAndOrganizationsPage.deletePersonalDataMenuItem.click();

		await personalDataErasurePage.selectAllItemsOnPageCheckbox.check();
		await personalDataErasurePage.allSelectedButton.click();

		await personalDataErasurePage.anonymizeMenuItem.click();

		await personalDataErasurePage.anonymizeButton.click();

		await usersAndOrganizationsPage.goToUsers(false);

		await expect(async () => {
			await (
				await usersAndOrganizationsPage.usersTableRowActions(
					userAccount.alternateName
				)
			).click();

			await expect(
				usersAndOrganizationsPage.activateUserMenuItem
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await usersAndOrganizationsPage.activateUserMenuItem.click();

		await viewObjectEntriesPage.goto(objectDefinition.className);

		for (let i = 0; i < 4; i++) {
			await expect(
				page.getByRole('cell', {name: 'Anonymous Anonymous'}).nth(i)
			).toBeVisible();
		}
	}
);

test(
	'LPD-78504 Can delete object entries via personal data management',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		page,
		personalDataErasurePage,
		usersAndOrganizationsPage,
		viewObjectEntriesPage,
	}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';
		const textFieldName = objectFields[0].name;

		await apiHelpers.objectEntry.postObjectEntry(
			{[textFieldName]: 'Entry A'},
			applicationName
		);

		const userAccount =
			await apiHelpers.headlessAdminUser.postUserAccount();

		apiHelpers.data.push({id: userAccount.id, type: 'userAccount'});

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
		await performLoginViaApi({
			page,
			screenName: userAccount.alternateName,
		});

		for (const entryName of ['Entry B', 'Entry C', 'Entry D', 'Entry E']) {
			await apiHelpers.objectEntry.postObjectEntry(
				{[textFieldName]: entryName},
				applicationName
			);
		}

		await performLogout(page);
		await performLoginViaApi({page, screenName: 'test'});

		await usersAndOrganizationsPage.goToUsers(false);

		page.on('dialog', (dialog) => {
			dialog.accept().catch(() => {});
		});

		await expect(async () => {
			await (
				await usersAndOrganizationsPage.usersTableRowActions(
					userAccount.alternateName
				)
			).click();

			await expect(
				usersAndOrganizationsPage.deletePersonalDataMenuItem
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await usersAndOrganizationsPage.deletePersonalDataMenuItem.click();

		await personalDataErasurePage.selectAllItemsOnPageCheckbox.check();
		await personalDataErasurePage.allSelectedButton.click();

		await personalDataErasurePage.deleteMenuItem.click();

		await personalDataErasurePage.deleteLink.click();

		await usersAndOrganizationsPage.goToUsers(false);

		await expect(async () => {
			await (
				await usersAndOrganizationsPage.usersTableRowActions(
					userAccount.alternateName
				)
			).click();

			await expect(
				usersAndOrganizationsPage.activateUserMenuItem
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await usersAndOrganizationsPage.activateUserMenuItem.click();

		await viewObjectEntriesPage.goto(objectDefinition.className);

		for (const entryName of [
			'Entry B',
			'Entry C',
			'Entry D',
			'Entry E',
		]) {
			await expect(
				page.getByRole('cell', {name: entryName})
			).not.toBeVisible();
		}

		await expect(
			page.getByRole('cell', {name: 'Entry A'})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can export object entries via personal data management',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		exportUserDataPage: _exportUserDataPage,
		page,
		usersAndOrganizationsPage,
	}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';
		const textFieldName = objectFields[0].name;

		const userAccount =
			await apiHelpers.headlessAdminUser.postUserAccount();

		apiHelpers.data.push({id: userAccount.id, type: 'userAccount'});

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
		await performLoginViaApi({
			page,
			screenName: userAccount.alternateName,
		});

		for (const entryNumber of ['1', '2', '3']) {
			await apiHelpers.objectEntry.postObjectEntry(
				{[textFieldName]: `Entry ${entryNumber}`},
				applicationName
			);
		}

		await performLogout(page);
		await performLoginViaApi({page, screenName: 'test'});

		await usersAndOrganizationsPage.goToUsers(false);

		await expect(async () => {
			await (
				await usersAndOrganizationsPage.usersTableRowActions(
					userAccount.alternateName
				)
			).click();

			await expect(
				usersAndOrganizationsPage.exportPersonalDataItem
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await usersAndOrganizationsPage.exportPersonalDataItem.click();

		await page.getByRole('button', {name: 'Add'}).click();

		await page.getByRole('menuitem', {name: 'Export'}).click();

		await page.getByRole('checkbox', {name: 'Objects'}).check();

		await page.getByRole('button', {name: 'Export'}).click();

		await expect(
			page.getByText('Your request completed successfully.')
		).toBeVisible();
	}
);
