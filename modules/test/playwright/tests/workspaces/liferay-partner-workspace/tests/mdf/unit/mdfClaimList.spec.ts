/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {partnerPagesTest} from '../../../fixtures/partnerPagesTest';
import {accountPlatinumMock} from '../../../mocks/accountMock';
import {userAdminMock} from '../../../mocks/userMock';
import {TAccount} from '../../../types/account';
import {TMDFClaim} from '../../../types/mdf';
import {EAccountRoles, EMDFClaimStatuses} from '../../../utils/constants';
import {customFormatDate, getDateCustomFormat} from '../../../utils/date';
import {getGeneratedDataFromClaim} from '../../../utils/mdf';

export const test = mergeTests(partnerPagesTest);

test.describe('MDF Claim List', () => {
	const {emailAddress} = userAdminMock;
	let accountPlatinum: TAccount;
	let mdfClaim: TMDFClaim;

	test.beforeEach(async ({apiHelpers, mdfClaimListPage, partnerHelper}) => {
		accountPlatinum =
			await apiHelpers.headlessAdminUser.postAccount(accountPlatinumMock);

		await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
			accountPlatinum.id,
			[emailAddress]
		);

		await partnerHelper.assignUserToAccountRole(
			Number(accountPlatinum.id),
			EAccountRoles.PARTNER_MANAGER,
			emailAddress
		);

		const mdfClaimData = getGeneratedDataFromClaim(accountPlatinum);

		mdfClaim = await partnerHelper.createMDFCLaim(mdfClaimData);

		await mdfClaimListPage.goto();
	});

	test.afterEach(async ({apiHelpers, partnerHelper}) => {
		if (accountPlatinum) {
			await apiHelpers.headlessAdminUser.deleteAccount(
				accountPlatinum.id
			);
		}

		if (mdfClaim) {
			await partnerHelper.deleteMDFClaim(mdfClaim.id);
		}
	});

	test('Filter data by Date Submitted', async ({mdfClaimListPage}) => {
		const submitDate = new Date(mdfClaim.submitDate);

		const filterEndDate = new Date(
			submitDate.setDate(submitDate.getDate() + 1)
		)
			.toISOString()
			.split('T')[0];
		const filterStartDate = new Date(
			submitDate.setDate(submitDate.getDate() - 1)
		)
			.toISOString()
			.split('T')[0];

		const formattedSubmitDate = getDateCustomFormat(
			submitDate.toISOString(),
			customFormatDate.SHORT_MONTH
		);

		const submittedDate =
			await mdfClaimListPage.getRenderedDateSubmitted(
				formattedSubmitDate
			);

		await mdfClaimListPage.filterByDateSubmitted(
			filterStartDate,
			filterEndDate
		);

		await expect(submittedDate).toBeVisible();

		await mdfClaimListPage.clearAllFilters();

		await mdfClaimListPage.filterButton.click();

		await mdfClaimListPage.dateSubmittedAfterDateInput.fill('2023-01-01');
		await mdfClaimListPage.dateSubmittedBeforeDateInput.fill('2023-01-02');

		await mdfClaimListPage.applyFilterButton.click();

		await expect(mdfClaimListPage.noEntriesFoundMessage).toBeVisible();
		await expect(submittedDate).not.toBeVisible();
	});

	test('Filter data by Status', async ({mdfClaimListPage, page}) => {
		await mdfClaimListPage.filterByStatus(
			EMDFClaimStatuses.PENDING_MARKETING_REVIEW
		);

		await expect(
			mdfClaimListPage.getRenderedStatus(
				EMDFClaimStatuses.PENDING_MARKETING_REVIEW
			)
		).toBeTruthy();

		await mdfClaimListPage.clearAllFilters();
		await mdfClaimListPage.filterButton.click();

		await mdfClaimListPage.showMoreButton.click();

		await page.getByLabel(EMDFClaimStatuses.DRAFT).check();

		await mdfClaimListPage.applyFilterButton.click();
		await mdfClaimListPage.mdfClaimHeading.click();

		await expect(mdfClaimListPage.noEntriesFoundMessage).toBeVisible();
	});

	test('Filter data by Partner', async ({mdfClaimListPage}) => {
		const partnerName = await mdfClaimListPage.getRenderedPartnerName(
			mdfClaim.companyName
		);

		await mdfClaimListPage.filterByPartner(mdfClaim.companyName);

		await expect(partnerName).toBeVisible();
	});

	test('Clean date filter fields when click on Clear All Filters', async ({
		mdfClaimListPage,
	}) => {
		await mdfClaimListPage.filterByDateSubmitted(
			'2024-06-01',
			'2024-06-08'
		);

		await mdfClaimListPage.clearAllFilters();

		await mdfClaimListPage.filterButton.click();

		await expect(mdfClaimListPage.dateSubmittedAfterDateInput).toBeEmpty();
		await expect(mdfClaimListPage.dateSubmittedBeforeDateInput).toBeEmpty();
	});

	test('Download MDF Claim', async ({mdfClaimListPage, page}) => {
		const downloadPromise = page.waitForEvent('download');

		await mdfClaimListPage.exportClaimButton.click();

		const downloadMDFReport = await downloadPromise;

		await downloadMDFReport.saveAs(
			'~/' + downloadMDFReport.suggestedFilename()
		);

		expect(downloadMDFReport.suggestedFilename()).toBe('MDF Claim.csv');
	});
});
