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
import {EAccountRoles, EMDFRequestStatuses} from '../../../utils/constants';
import {customFormatDate, getDateCustomFormat} from '../../../utils/date';
import {getGeneratedDataFromRequest} from '../../../utils/mdf';

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

		const mdfRequestData = getGeneratedDataFromRequest(accountPlatinum);

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
		const formattedEndDate = getDateCustomFormat(
			mdfRequest.maxDateActivity,
			customFormatDate.SHORT_MONTH
		);
		const formattedStartDate = getDateCustomFormat(
			mdfRequest.minDateActivity,
			customFormatDate.SHORT_MONTH
		);

		const campaignName = await mdfRequestListPage.getRenderedCampaignName(
			mdfRequest.overallCampaignName
		);
		const endActPeriod =
			await mdfRequestListPage.getRenderedEndActPeriod(formattedEndDate);
		const partnerName = await mdfRequestListPage.getRenderedPartnerName(
			mdfRequest.companyName
		);
		const requested = await mdfRequestListPage.getRenderedRequested(
			String(mdfRequest.totalMDFRequestAmount)
		);
		const startActPeriod =
			await mdfRequestListPage.getRenderedStartActPeriod(
				formattedStartDate
			);

		await expect(campaignName).toBeVisible();
		await expect(endActPeriod).toBeVisible();
		await expect(partnerName).toBeVisible();
		await expect(requested).toBeTruthy();
		await expect(startActPeriod).toBeVisible();
	});

	test('Filter data by Activity Period', async ({mdfRequestListPage}) => {
		const filterEndDate = new Date(mdfRequest.maxDateActivity)
			.toISOString()
			.split('T')[0];
		const filterStartDate = new Date(mdfRequest.minDateActivity)
			.toISOString()
			.split('T')[0];
		const formattedEndDate = getDateCustomFormat(
			mdfRequest.maxDateActivity,
			customFormatDate.SHORT_MONTH
		);
		const formattedStartDate = getDateCustomFormat(
			mdfRequest.minDateActivity,
			customFormatDate.SHORT_MONTH
		);

		const endActPeriod =
			await mdfRequestListPage.getRenderedEndActPeriod(formattedEndDate);
		const startActPeriod =
			await mdfRequestListPage.getRenderedStartActPeriod(
				formattedStartDate
			);

		await mdfRequestListPage.filterByActivityPeriod(
			filterStartDate,
			filterEndDate
		);

		await expect(endActPeriod).toBeVisible();
		await expect(startActPeriod).toBeVisible();

		await mdfRequestListPage.clearAllFilters();

		await mdfRequestListPage.filterButton.click();

		await mdfRequestListPage.activityAfterDateInput.fill('2024-08-09');
		await mdfRequestListPage.activityBeforeDateInput.fill('2024-08-10');

		await mdfRequestListPage.applyFilterButton.click();

		await expect(mdfRequestListPage.noEntriesFoundMessage).toBeVisible();
		await expect(endActPeriod).not.toBeVisible();
		await expect(startActPeriod).not.toBeVisible();
	});

	test('Filter data by Status', async ({mdfRequestListPage, page}) => {
		await mdfRequestListPage.filterByStatus(
			EMDFRequestStatuses.PENDING_MARKETING_REVIEW
		);

		await expect(
			mdfRequestListPage.getRenderedStatus(
				EMDFRequestStatuses.PENDING_MARKETING_REVIEW
			)
		).toBeTruthy();

		await mdfRequestListPage.clearAllFilters();

		await mdfRequestListPage.filterButton.click();

		await page.getByLabel(EMDFRequestStatuses.DRAFT).check();

		await mdfRequestListPage.applyFilterButton.click();

		await expect(mdfRequestListPage.noEntriesFoundMessage).toBeVisible();
	});

	test('Filter data by Partner', async ({mdfRequestListPage}) => {
		await mdfRequestListPage.filterByPartner(mdfRequest.companyName);

		const partnerName = await mdfRequestListPage.getRenderedPartnerName(
			mdfRequest.companyName
		);

		await expect(partnerName).toBeVisible();
	});

	test('Clean date filter fields when click on Clear All Filters', async ({
		mdfRequestListPage,
	}) => {
		await mdfRequestListPage.filterByActivityPeriod(
			'2024-06-01',
			'2024-06-08'
		);

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
		const renderedRow = await mdfRequestListPage.getRenderedRow(
			mdfRequest.overallCampaignName
		);

		const requestId = await mdfRequestListPage.getRenderedRequestId(
			renderedRow.requestId
		);

		await requestId.click();

		await mdfRequestFormPage.statusDropdown.click();

		await mdfRequestFormPage.statusDropDownOption(
			EMDFRequestStatuses.APPROVED
		);

		await mdfRequestFormPage.backButton.click();

		await expect(
			mdfRequestListPage.getRenderedStatus(EMDFRequestStatuses.APPROVED)
		).toBeTruthy();
	});

	test('Find a Request using the search input', async ({
		mdfRequestListPage,
	}) => {
		const campaignName = await mdfRequestListPage.getRenderedCampaignName(
			mdfRequest.overallCampaignName
		);
		const cleanSearch = await mdfRequestListPage.cleanSearch;

		await mdfRequestListPage.filterBySearchInput(
			mdfRequest.overallCampaignName
		);

		await expect(campaignName).toBeVisible();

		await cleanSearch.click();

		await mdfRequestListPage.filterBySearchInput('Test');

		await expect(mdfRequestListPage.noEntriesFoundMessage).toBeVisible();
	});

	test('Complete A MDF Request through action button', async ({
		mdfRequestFormPage,
		mdfRequestListPage,
		page,
	}) => {
		const actionButton = await mdfRequestListPage.actionButton;
		const completeMenuItem = await mdfRequestListPage.completeMenuItem;
		const completedTab = await mdfRequestListPage.completedTab;
		const renderedRow = await mdfRequestListPage.getRenderedRow(
			mdfRequest.overallCampaignName
		);

		const requestId = await mdfRequestListPage.getRenderedRequestId(
			renderedRow.requestId
		);

		await requestId.click();

		await mdfRequestFormPage.statusDropdown.click();

		await mdfRequestFormPage.statusDropDownOption(
			EMDFRequestStatuses.APPROVED
		);

		await mdfRequestFormPage.backButton.click();

		await page.once('dialog', async (dialog) => {
			expect(dialog.message()).toContain(
				'Are you sure you want to complete the MDF request?'
			);
			await dialog.accept();
		});

		await actionButton.click();

		await completeMenuItem.click();

		const successTooltip = await page.getByText(
			'Success:MDF Request successfully completed!'
		);

		await expect(successTooltip).toBeVisible();

		await completedTab.click();

		await expect(requestId).toBeVisible();
	});
});
