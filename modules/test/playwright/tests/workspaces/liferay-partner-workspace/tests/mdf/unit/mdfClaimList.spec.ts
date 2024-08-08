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
import {EAccountRoles} from '../../../utils/constants';
import {customFormatDate, getDateCustomFormat} from '../../../utils/date';
import {generatedDataFromClaim} from '../../../utils/mdf';

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

		const mdfClaimData = generatedDataFromClaim(accountPlatinum);

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
		
		const filterEndDate = new Date(mdfClaim.submitDate)
			.toISOString()
			.split('T')[0];

		const filterStartDate = new Date(new Date(mdfClaim.submitDate)
			.setDate(new Date(mdfClaim.submitDate)
			.getDate() - 1))
			.toISOString().split('T')[0];
		
		const fomartStartDate = new Date(new Date(mdfClaim.submitDate).setDate(new Date(mdfClaim.submitDate).getDate() - 1)).toISOString();
		
		const formatSubmitedDate = getDateCustomFormat(
			fomartStartDate,
			customFormatDate.SHORT_MONTH
		);
		
		const submitedDate =
			await mdfClaimListPage.getsubmitedDate(formatSubmitedDate);
		
		await mdfClaimListPage.filterMDFClaimByPeriod(
			filterStartDate,
			filterEndDate
		);

		await mdfClaimListPage.heading.click();

		await expect(submitedDate).toBeVisible();

		await mdfClaimListPage.clearAllFilters();
		await mdfClaimListPage.filterButton.click();

		await mdfClaimListPage.dateSubmittedAfterDateInput.fill('2024-08-09');
		await mdfClaimListPage.dateSubmittedBeforeDateInput.fill('2024-08-10');

		await mdfClaimListPage.applyFilterButton.click();

		await mdfClaimListPage.heading.click();

		await expect(mdfClaimListPage.noEntriesFoundMessage).toBeVisible();
		await expect(submitedDate).not.toBeVisible();
	});

	test('Filter data by Status', async ({mdfClaimListPage, page}) => {
		const generatedDataFromRequest =
		await mdfClaimListPage.getGeneratedDataFromRequest(
			mdfClaim.companyName
		);
		const status = generatedDataFromRequest.status;
		
		await mdfClaimListPage.filterMDFCLaimByStatus(status);

		await mdfClaimListPage.mdfClaimHeading.click();

		await expect(status).toBeTruthy();

		await mdfClaimListPage.clearAllFilters();
		await mdfClaimListPage.filterButton.click();

		await mdfClaimListPage.showMoreButton.click();

		await page.getByLabel('Draft').check();

		await mdfClaimListPage.applyFilterButton.click();
		await mdfClaimListPage.mdfClaimHeading.click();

		await expect(mdfClaimListPage.noEntriesFoundMessage).toBeVisible();
	});

	test('Filter data by Partner', async ({mdfClaimListPage}) => {
		const partnerName = await mdfClaimListPage.getPartnerName(
			mdfClaim.companyName
		);

		await mdfClaimListPage.filterMDFClaimByPartner(
			mdfClaim.companyName
		);

		await mdfClaimListPage.heading.click();

		await expect(partnerName).toBeVisible();
	});

	test('Clean date filter fields when click on Clear All Filters', async ({
		mdfClaimListPage,
	}) => {
		await mdfClaimListPage.filterMDFClaimByPeriod(
			'2024-06-01',
			'2024-06-08'
		);

		await mdfClaimListPage.heading.click();

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