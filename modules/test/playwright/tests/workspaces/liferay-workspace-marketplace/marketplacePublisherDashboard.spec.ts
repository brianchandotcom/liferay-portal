/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {marketplacePagesTest} from './fixtures/marketplacePages';
import {marketplaceSiteFixture} from './fixtures/marketplaceSite';
import {PUBLISH_SOLUTION} from './types';

export const test = mergeTests(
	dataApiHelpersTest,
	marketplaceSiteFixture,
	marketplacePagesTest
);

test.describe('Can Publish and Manage Solutions', () => {
	test.beforeEach(
		async ({apiHelpers, marketplace, publisherSolutionPage}) => {
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
		}
	);

	test('LPD-26707 New Solution Template button should be visible for Suppliers', async ({
		publisherSolutionPage,
	}) => {
		await expect(publisherSolutionPage.newSolutionButton).toBeEnabled();
	});

	test('LPD-26707 Add new solution template', async ({
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
			PUBLISH_SOLUTION.profile
		);

		await expect(publisherSolutionPage.continueButton).toBeEnabled();

		await publisherSolutionPage.goToCustomizeSolutionHeader();
		await publisherSolutionPage.fillCustomizeSolutionHeader(
			PUBLISH_SOLUTION.header
		);

		await expect(publisherSolutionPage.continueButton).toBeEnabled();

		await publisherSolutionPage.goToCustomizeSolutionDetails();
		await publisherSolutionPage.fillCustomizeSolutionDetails(
			PUBLISH_SOLUTION.details
		);

		await expect(publisherSolutionPage.continueButton).toBeEnabled();
		await publisherSolutionPage.goToCompanyProfile();
		await publisherSolutionPage.fillCompanyProfile(
			PUBLISH_SOLUTION.companyProfile
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
			.getByText(`Solution ${PUBLISH_SOLUTION.profile.name} submitted`)
			.waitFor({state: 'visible'});

		await expect(
			page.getByText(PUBLISH_SOLUTION.profile.name).last()
		).toBeVisible();

		await expect(
			publisherSolutionPage.underReviewStatus.last()
		).toBeVisible();
	});
});
