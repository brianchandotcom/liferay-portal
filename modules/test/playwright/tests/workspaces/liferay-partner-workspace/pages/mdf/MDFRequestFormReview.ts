/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { Page, expect } from "@playwright/test";

interface MDFRequestContent {
    activities: [{
        activityName: string;
        claimPercent: number;
        endDate: string;
        expenses: [{ value: number }];
        leadGenerated: boolean;
        marketingActivity: string;
        startDate: string;
        tactic: string;
    }];
    goals: {
        companyName: string;
        liferayBusinessSalesGoals: string[];
        overallCampaignDescription: string;
        overallCampaignName: string;
        targetAudienceRoles: string[];
        targetMarkets: string[];
    }
}

export class MDFRequestFormReview {
    readonly page: Page;

    constructor(page: Page) {
        this.page = page;
    }

    async reviewMDFContent(mdfRequestContent: MDFRequestContent) {
        const {
            activities: [{
                activityName,
                endDate,
                expenses: [{ value: expenses }],
                claimPercent,
                leadGenerated,
                marketingActivity,
                startDate,
                tactic
            }],
            goals: {
                overallCampaignName: campaignName,
                companyName,
                overallCampaignDescription: campaignDescription,
                liferayBusinessSalesGoals: [liferayBusinessSalesGoals],
                targetAudienceRoles: [targetAudienceRoles],
                targetMarkets: [targetMarkets]
            }
        } = mdfRequestContent;

        const expensePercentage = String(expenses * claimPercent);
        const leadGeneratedText = leadGenerated ? 'Yes' : 'No';

        await expect(this.page.getByRole('cell', { name: companyName })).toBeVisible();
        await expect(this.page.getByRole('cell', { name: campaignName })).toBeVisible();
        await expect(this.page.getByRole('cell', { name: campaignDescription })).toBeVisible();
        await expect(this.page.getByRole('cell', { name: liferayBusinessSalesGoals[0] })).toBeVisible();
        await expect(this.page.getByRole('cell', { name: targetMarkets[0] })).toBeVisible();
        await expect(this.page.getByRole('cell', { name: targetAudienceRoles[0] })).toBeVisible();
        await expect(this.page.getByText(expensePercentage).first()).toBeVisible();
        await expect(this.page.getByText(activityName).first()).toBeVisible();

        await this.page.getByRole('tab', {name: 'MDF Requested'}).click();

        await expect(this.page.getByText(activityName).first()).toBeVisible();
        await expect(this.page.getByRole('cell', {name: tactic})).toBeVisible();
        await expect(this.page.getByRole('cell', {name: marketingActivity})).toBeVisible();
        await expect(this.page.getByRole('cell', {name: startDate})).toBeVisible();
        await expect(this.page.getByRole('cell', {name: endDate})).toBeVisible();
        await expect(this.page.getByRole('cell', {name: String(expenses)})).toBeVisible();
        await expect(this.page.getByRole('cell', {name: leadGeneratedText})).toBeVisible();
        await expect(this.page.getByText(expensePercentage).first()).toBeVisible();

        await this.page.getByRole('tab', {name: 'MDF Requested'}).click();
    }
}