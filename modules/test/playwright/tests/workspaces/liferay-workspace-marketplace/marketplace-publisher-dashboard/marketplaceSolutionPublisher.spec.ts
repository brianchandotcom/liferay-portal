/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';
import {getRandomInt} from '../../../../utils/getRandomInt';
import {marketplacePagesTest} from '../fixtures/marketplacePages';
import {marketplaceSiteFixture} from '../fixtures/marketplaceSite';
import {solutions} from '../utils/constants';

export const test = mergeTests(
	dataApiHelpersTest,
	marketplaceSiteFixture,
	marketplacePagesTest
);

const ACCOUNT_NAME = {
	PERSON: `Person Account${getRandomInt()}`,
	SUPPLIER: `Supplier Account${getRandomInt()}`,
};
const SOLUTION_PUBLISHER_ROLE = 'Solution Publisher';

test.describe('Can Publish and Manage Solutions', () => {
	let _account;
	let _catalog;
	let _productId;

	test.beforeEach(
		async ({apiHelpers, marketplace, publisherSolutionPage}) => {
			const account = await apiHelpers.headlessAdminUser.postAccount({
				name: ACCOUNT_NAME.SUPPLIER,
				type: 'supplier',
			});

			_account = account;

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
				return role.name === SOLUTION_PUBLISHER_ROLE;
			});

			await apiHelpers.headlessAdminUser.assignUserToAccountRole(
				account.id,
				accountSupplierRole[0].id,
				user.id
			);

			const catalog =
				await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
					accountId: account.id,
				});

			_catalog = catalog;

			await publisherSolutionPage.goto(
				`web${marketplace.friendlyUrlPath}/publisher-dashboard#/solutions`
			);
		}
	);

	test.afterEach(async ({apiHelpers}) => {
		await apiHelpers.headlessAdminUser.deleteAccount(_account.id);

		await apiHelpers.headlessCommerceAdminCatalog.deleteProduct(_productId);

		await apiHelpers.headlessCommerceAdminCatalog.deleteCatalog(
			_catalog.id
		);
	});

	test('LPD-26707 New Solution Template button should be visible for Suppliers', async ({
		publisherSolutionPage,
	}) => {
		await publisherSolutionPage.selectAccount(ACCOUNT_NAME.SUPPLIER);
		await expect(publisherSolutionPage.newSolutionButton).toBeEnabled();
	});

	for (const key of Object.keys(solutions)) {
		const solution = solutions[key as keyof typeof solutions];

		test(`LPD-26707 can publish solution "${solution.profile.name}" template`, async ({
			apiHelpers,
			marketplace,
			page,
			publisherSolutionPage,
		}) => {
			await publisherSolutionPage.goto(
				`web${marketplace.friendlyUrlPath}/publisher-dashboard#/solutions`
			);

			await publisherSolutionPage.goToNewSolution();
			await publisherSolutionPage.goToDefineSolutionProfile();
			await publisherSolutionPage.fillDefineSolutionProfile(
				solution.profile
			);

			await expect(publisherSolutionPage.continueButton).toBeEnabled();

			await publisherSolutionPage.goToCustomizeSolutionHeader();
			await publisherSolutionPage.fillCustomizeSolutionHeader(
				solution.header
			);

			await expect(publisherSolutionPage.continueButton).toBeEnabled();

			await publisherSolutionPage.goToCustomizeSolutionDetails();
			await publisherSolutionPage.fillCustomizeSolutionDetails(
				solution.details
			);

			await expect(publisherSolutionPage.continueButton).toBeEnabled();
			await publisherSolutionPage.goToCompanyProfile();
			await publisherSolutionPage.fillCompanyProfile(
				solution.companyProfile
			);

			await expect(publisherSolutionPage.continueButton).toBeEnabled();
			await publisherSolutionPage.goToContactUs();
			await publisherSolutionPage.emailInput.fill('test@example.com');

			await expect(publisherSolutionPage.continueButton).toBeEnabled();

			await clickAndExpectToBeVisible({
				target: publisherSolutionPage.reviewAndSubmitTitle,
				trigger: publisherSolutionPage.continueButton,
			});

			await publisherSolutionPage.reviewAndSubmit();

			await page
				.getByText(`Solution ${solution.profile.name} submitted`)
				.waitFor({state: 'visible'});

			const createdProduct =
				await apiHelpers.headlessCommerceAdminCatalog.getProducts(
					new URLSearchParams({
						filter: `name eq '${solution.profile.name}'`,
					})
				);

			const productId = createdProduct.items[0].productId;

			_productId = productId;

			await expect(
				page.getByText(solution.profile.name).last()
			).toBeVisible();

			await expect(
				publisherSolutionPage.underReviewStatus.last()
			).toBeVisible();
		});
	}
});

test.describe(`Supplier Accounts without ${SOLUTION_PUBLISHER_ROLE} role can not be a solution publisher`, () => {
	let _account;
	let _catalog;

	test.beforeEach(
		async ({apiHelpers, marketplace, publisherSolutionPage}) => {
			const account = await apiHelpers.headlessAdminUser.postAccount({
				name: ACCOUNT_NAME.SUPPLIER,
				type: 'supplier',
			});

			_account = account;

			await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
				account.id,
				['test@liferay.com']
			);

			const catalog =
				await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
					accountId: account.id,
				});

			_catalog = catalog;

			await publisherSolutionPage.goto(
				`web${marketplace.friendlyUrlPath}/publisher-dashboard#/solutions`
			);
		}
	);

	test.afterEach(async ({apiHelpers}) => {
		await apiHelpers.headlessAdminUser.deleteAccount(_account.id);
		await apiHelpers.headlessCommerceAdminCatalog.deleteCatalog(
			_catalog.id
		);
	});

	test('LPD-28486 New Solution Template button should NOT be visible', async ({
		publisherSolutionPage,
	}) => {
		await publisherSolutionPage.selectAccount(ACCOUNT_NAME.SUPPLIER);

		await expect(publisherSolutionPage.newSolutionButton).toBeHidden();

		await expect(publisherSolutionPage.notAvailableAlert).toBeVisible();

		await expect(publisherSolutionPage.becomePublisherForm).toBeVisible();
	});
});
