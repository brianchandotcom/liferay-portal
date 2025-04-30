/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';
import moment from 'moment';

interface IMDFRequestPageContent {
	activities: [
		{
			activityName: string;
			claimPercent: number;
			endDate?: string;
			expenses: [{value: number}];
			leadGenerated: boolean;
			marketingActivity: string;
			startDate?: string;
			tactic?: string;
		},
	];
	goals: {
		companyName: string;
		liferayBusinessSalesGoals?: string[];
		overallCampaignDescription: string;
		overallCampaignName: string;
		targetAudienceRoles?: string[];
		targetMarkets?: string[];
	};
}

export class MDFRequestFormReviewPage {
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
	}

	async reviewMDFContent(mdfRequestPageContent: IMDFRequestPageContent) {
		const {
			activities: [
				{
					activityName,
					expenses: [{value: expenses}],
					claimPercent,
					leadGenerated,
					marketingActivity,
				},
			],
			goals: {
				companyName,
				overallCampaignDescription: campaignDescription,
				overallCampaignName: campaignName,
			},
		} = mdfRequestPageContent;

		const expensePercentage = String(expenses * claimPercent);
		const leadGeneratedText = leadGenerated ? 'Yes' : 'No';

		await expect(
			this.page.getByRole('cell', {name: companyName})
		).toBeVisible();
		await expect(
			this.page.getByRole('cell', {name: campaignName})
		).toBeVisible();
		await expect(
			this.page.getByRole('cell', {name: campaignDescription})
		).toBeVisible();
		await expect(
			this.page.getByRole('cell', {name: 'Lead generation'})
		).toBeVisible();
		await expect(
			this.page.getByRole('cell', {
				name: 'Aerospace & Defense; Agriculture',
			})
		).toBeVisible();
		await expect(
			this.page.getByRole('cell', {
				name: 'C-Level/Executive/VP; Administrator',
			})
		).toBeVisible();
		await expect(
			this.page.getByText(expensePercentage).first()
		).toBeVisible();
		await expect(this.page.getByText(activityName).first()).toBeVisible();

		await this.page
			.getByRole('button', {exact: false, name: campaignName})
			.click();

		await expect(this.page.getByText(activityName).first()).toBeVisible();
		await expect(
			this.page.getByRole('cell', {name: 'Other'})
		).toBeVisible();
		await expect(
			this.page.getByRole('cell', {name: marketingActivity})
		).toBeVisible();
		await expect(
			this.page.getByRole('cell', {
				name: moment().add(1, 'days').format('l'),
			})
		).toBeVisible();
		await expect(
			this.page.getByRole('cell', {
				name: moment().add(2, 'days').format('l'),
			})
		).toBeVisible();
		await expect(
			this.page.getByRole('cell', {name: String(expenses)})
		).toBeVisible();
		await expect(
			this.page.getByRole('cell', {name: leadGeneratedText})
		).toBeVisible();
		await expect(
			this.page.getByText(expensePercentage).first()
		).toBeVisible();
	}
}
