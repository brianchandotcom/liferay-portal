/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {partnerPagesTest} from '../../../fixtures/partnerPagesTest';
import {accountPlatinumMock} from '../../../mocks/accountMock';
import {userAdminMock} from '../../../mocks/userMock';
import {TAccount} from '../../../types/account';
import {TMDFRequestDataFromRequest} from '../../../types/mdf';
import {EAccountRoles} from '../../../utils/constants';
import {customFormatDate, getDateCustomFormat} from '../../../utils/date';
import {generatedDataFromRequest} from '../../../utils/mdf';

export const test = mergeTests(partnerPagesTest);

test.describe('MDF Request List', () => {
	const {emailAddress} = userAdminMock;
	let accountPlatinum: TAccount;
	let mdfRequest: TMDFRequestDataFromRequest;

	test.beforeEach(async ({apiHelpers, mdfRequestListPage, partnerHelper}) => {
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

		const mdfRequestData = generatedDataFromRequest(accountPlatinum);

		mdfRequest = await partnerHelper.createMDFRequest(mdfRequestData);

		await mdfRequestListPage.goto();
	});

	test.afterEach(async ({apiHelpers, partnerHelper}) => {
		if (accountPlatinum) {
			await apiHelpers.headlessAdminUser.deleteAccount(
				accountPlatinum.id
			);
		}

		if (mdfRequest) {
			await partnerHelper.deleteMDFRequest(mdfRequest.id);
		}
	});

	test('Open MDF Request List', async ({mdfRequestListPage}) => {
		await expect(mdfRequestListPage.heading).toBeTruthy();
	});

	test('Display MDF Resquest List', async ({mdfRequestListPage}) => {
		const generatedDataFromRequest =
			await mdfRequestListPage.getGeneratedDataFromRequest(
				mdfRequest.overallCampaignName
			);
		const campaignName = await mdfRequestListPage.getCampaignName();
		const formatEndDate = getDateCustomFormat(
			mdfRequest.maxDateActivity,
			customFormatDate.SHORT_MONTH
		);
		const formatStartDate = getDateCustomFormat(
			mdfRequest.minDateActivity,
			customFormatDate.SHORT_MONTH
		);

		const endActPeriod =
			await mdfRequestListPage.getEndActPeriod(formatEndDate);
		const partnerName = await mdfRequestListPage.getPartnerName(
			mdfRequest.companyName
		);
		const requested = await mdfRequestListPage.getRequested(
			String(mdfRequest.totalMDFRequestAmount)
		);
		const startActPeriod =
			await mdfRequestListPage.getStartActPeriod(formatStartDate);

		await expect(campaignName).toBeVisible();

		await expect(endActPeriod).toBeVisible();
		await expect(partnerName).toBeVisible();
		await expect(requested).toBeTruthy();
		await expect(generatedDataFromRequest.requestId).toBeTruthy();
		await expect(startActPeriod).toBeVisible();
		await expect(generatedDataFromRequest.status).toBeTruthy();
		await expect(generatedDataFromRequest.submitDate).toBeTruthy();
	});

	test('Filter data by Activity Period', async ({mdfRequestListPage}) => {
		const filterEndDate = new Date(mdfRequest.maxDateActivity)
			.toISOString()
			.split('T')[0];
		const filterStartDate = new Date(mdfRequest.minDateActivity)
			.toISOString()
			.split('T')[0];
		const formatEndDate = getDateCustomFormat(
			mdfRequest.maxDateActivity,
			customFormatDate.SHORT_MONTH
		);
		const formatStartDate = getDateCustomFormat(
			mdfRequest.minDateActivity,
			customFormatDate.SHORT_MONTH
		);
		const endActPeriod =
			await mdfRequestListPage.getEndActPeriod(formatEndDate);
		const startActPeriod =
			await mdfRequestListPage.getStartActPeriod(formatStartDate);

		await mdfRequestListPage.filterMDFRequestByPeriod(
			filterStartDate,
			filterEndDate
		);

		await mdfRequestListPage.heading.click();

		await expect(endActPeriod).toBeVisible();
		await expect(startActPeriod).toBeVisible();

		await mdfRequestListPage.clearAllFilters();
		await mdfRequestListPage.filterButton.click();

		await mdfRequestListPage.activityAfterDateInput.fill('2024-08-09');
		await mdfRequestListPage.activityBeforeDateInput.fill('2024-08-10');

		await mdfRequestListPage.applyFilterButton.click();

		await mdfRequestListPage.heading.click();

		await expect(mdfRequestListPage.noEntriesFoundMessage).toBeVisible();
		await expect(endActPeriod).not.toBeVisible();
		await expect(startActPeriod).not.toBeVisible();
	});

	test('Filter data by Status', async ({mdfRequestListPage, page}) => {
		const generatedDataFromRequest =
			await mdfRequestListPage.getGeneratedDataFromRequest(
				mdfRequest.overallCampaignName
			);
		const status = generatedDataFromRequest.status;

		await mdfRequestListPage.filterMDFRequestByStatus(status);

		await mdfRequestListPage.mdfRequestHeading.click();

		await expect(status).toBeTruthy();

		await mdfRequestListPage.clearAllFilters();
		await mdfRequestListPage.filterButton.click();

		await page.getByLabel('Draft').check();

		await mdfRequestListPage.applyFilterButton.click();
		await mdfRequestListPage.mdfRequestHeading.click();

		await expect(mdfRequestListPage.noEntriesFoundMessage).toBeVisible();
	});

	test('Filter data by Partner', async ({mdfRequestListPage}) => {
		const partnerName = await mdfRequestListPage.getPartnerName(
			mdfRequest.companyName
		);

		await mdfRequestListPage.filterMDFRequestByPartner(
			mdfRequest.companyName
		);

		await mdfRequestListPage.heading.click();

		await expect(partnerName).toBeVisible();
	});

	test('Clean date filter fields when click on Clear All Filters', async ({
		mdfRequestListPage,
	}) => {
		await mdfRequestListPage.filterMDFRequestByPeriod(
			'2024-06-01',
			'2024-06-08'
		);

		await mdfRequestListPage.heading.click();

		await mdfRequestListPage.clearAllFilters();

		await mdfRequestListPage.filterButton.click();

		await expect(mdfRequestListPage.activityAfterDateInput).toBeEmpty();
		await expect(mdfRequestListPage.activityBeforeDateInput).toBeEmpty();
	});

	test('Download MDF Report', async ({mdfRequestListPage, page}) => {
		const downloadPromise = page.waitForEvent('download');
		await mdfRequestListPage.exportRequestButton.click();

		const downloadMDFReport = await downloadPromise;

		await downloadMDFReport.saveAs(
			'~/' + downloadMDFReport.suggestedFilename()
		);

		expect(downloadMDFReport.suggestedFilename()).toBe('MDF Requests.csv');
	});

	test('Change MDF Request Status', async ({
		mdfRequestFormPage,
		mdfRequestListPage,
	}) => {
		const generatedDataFromRequest =
			await mdfRequestListPage.getGeneratedDataFromRequest(
				mdfRequest.overallCampaignName
			);
		const requestId = await mdfRequestListPage.getRequestId(
			generatedDataFromRequest.requestId
		);

		await requestId.click();

		await mdfRequestFormPage.statusDropdown.click();
		await mdfRequestFormPage.statusDropDownOption('Approved');
		await mdfRequestFormPage.backButton.click();

		await expect(mdfRequestListPage.getStatus('Approved')).toBeTruthy();
	});

	test('Find a Request using the search input', async ({
		mdfRequestListPage,
	}) => {
		const campaignName = await mdfRequestListPage.getCampaignName();
		const cleanSearch = await mdfRequestListPage.cleanSearch;

		await mdfRequestListPage.filterUsingSearchInput('Campaign Name');

		await expect(campaignName).toBeVisible();

		await cleanSearch.click();
		await mdfRequestListPage.filterUsingSearchInput('Test');

		await expect(mdfRequestListPage.noEntriesFoundMessage).toBeVisible();
	});

	test('Complete A MDF Request through action button', async ({
		mdfRequestFormPage,
		mdfRequestListPage,
		page,
	}) => {
		const actionButton = await mdfRequestListPage.actionButton;
		const generatedDataFromRequest =
			await mdfRequestListPage.getGeneratedDataFromRequest(
				mdfRequest.overallCampaignName
			);
		const completedTab = await mdfRequestListPage.completedTab;
		const completeMenuItem = await mdfRequestListPage.completeMenuItem;
		const requestId = await mdfRequestListPage.getRequestId(
			generatedDataFromRequest.requestId
		);
		const successTooltip = await page.getByText(
			'Success:MDF Request successfully completed!'
		);

		await requestId.click();

		await mdfRequestFormPage.statusDropdown.click();
		await mdfRequestFormPage.statusDropDownOption('Approved');
		await mdfRequestFormPage.backButton.click();

		await page.once('dialog', async (dialog) => {
			expect(dialog.message()).toContain(
				'Are you sure you want to complete the MDF request?'
			);
			await dialog.accept();
		});

		await actionButton.click();
		await completeMenuItem.click();

		await expect(successTooltip).toBeVisible();

		await completedTab.click();

		await expect(requestId).toBeVisible();
	});
});
