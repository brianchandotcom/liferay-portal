/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {marketplacePagesTest} from './fixtures/marketplacePages';
import {marketplaceSiteFixture} from './fixtures/marketplaceSite';

export const test = mergeTests(
	dataApiHelpersTest,
	marketplaceSiteFixture,
	marketplacePagesTest
);

const describe = test.describe;

describe('LPD-26707 Can Publish and Manage Solutions', () => {
	test('LPD-26707 New Solution Template button should be visible for Suppliers', async ({
		apiHelpers,
		marketplace,
		publisherSolutionPage,
	}) => {
		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: 'Supplier account',
			type: 'supplier',
		});

		const user =
			await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
				'test@liferay.com'
			);

		await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
			account.id,
			['test@liferay.com']
		);

		const rolesResponse =
			await apiHelpers.headlessAdminUser.getAccountRoles(account.id);

		const accountSupplierRole = rolesResponse?.items?.filter((role) => {
			return role.name === 'Account Supplier';
		});

		await apiHelpers.headlessAdminUser.assingUserToAccountRole(
			account.id,
			accountSupplierRole[0].id,
			user.id
		);

		await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
			accountId: account.id,
		});

		await publisherSolutionPage.goto(
			`web${marketplace.friendlyUrlPath}/publisher-dashboard#/solutions`
		);

		await expect(publisherSolutionPage.newSolutionButton).toBeEnabled();
	});

	test('LPD-26707 Define the solution profile', async ({
		marketplace,
		publisherSolutionPage,
	}) => {
		await publisherSolutionPage.goto(
			`web${marketplace.friendlyUrlPath}/publisher-dashboard#/solutions`
		);
		await publisherSolutionPage.goToNewSolution();

		await publisherSolutionPage.goToDefineSolutionProfile();

		await publisherSolutionPage.fillDefineSolutionProfile(
			'Solution Test Name',
			'Solution Test Description'
		);

		await expect(publisherSolutionPage.continueButton).toBeEnabled();
	});
});
