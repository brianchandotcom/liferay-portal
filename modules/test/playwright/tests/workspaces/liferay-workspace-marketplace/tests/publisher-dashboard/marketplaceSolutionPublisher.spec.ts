/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../../../utils/clickAndExpectToBeVisible';
import {getRandomInt} from '../../../../../utils/getRandomInt';
import {marketplaceHelper} from '../../fixtures/marketplaceHelper';
import {marketplacePagesTest} from '../../fixtures/marketplacePages';
import {marketplaceSiteFixture} from '../../fixtures/marketplaceSite';
import {SOLUTION_PUBLISHER_ROLE, solutions} from '../../utils/constants';

export const test = mergeTests(
	marketplaceSiteFixture,
	marketplaceHelper,
	marketplacePagesTest
);

test.describe('Can Publish and Manage Solutions', () => {
	let _account;
	let _catalog;
	let _productId;
	const accountName = `Supplier Account${getRandomInt()}`;

	test.beforeEach(
		async ({marketplace, marketplaceHelper, publisherSolutionPage}) => {
			const {account, catalog} =
				await marketplaceHelper.createAccountUserCatalog({
					accountName,
					accountType: 'supplier',
				});

			_account = account;
			_catalog = catalog;

			await marketplaceHelper.assignUserToAccountRole({
				accountId: account.id,
				accountRole: SOLUTION_PUBLISHER_ROLE,
			});

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
		await publisherSolutionPage.selectAccount(accountName);
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
	const accountName = `Supplier Account${getRandomInt()}`;

	test.beforeEach(
		async ({marketplace, marketplaceHelper, publisherSolutionPage}) => {
			const {account, catalog} =
				await marketplaceHelper.createAccountUserCatalog({
					accountName,
					accountType: 'supplier',
				});

			_account = account;
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
		await publisherSolutionPage.selectAccount(accountName);

		await expect(publisherSolutionPage.newSolutionButton).toBeHidden();

		await expect(publisherSolutionPage.notAvailableAlert).toBeVisible();

		await expect(publisherSolutionPage.becomePublisherForm).toBeVisible();
	});
});
