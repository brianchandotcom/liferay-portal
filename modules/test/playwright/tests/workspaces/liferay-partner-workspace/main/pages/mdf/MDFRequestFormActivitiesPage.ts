/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {TMDFRequestActivity, TMDFRequestActivityExpense} from '../../types/mdf';
import {
	EMDFRequestActivityTactics,
	EMDFRequestActivityTypes,
} from '../../utils/constants';

export class MDFRequestFormActivitiesPage {
	readonly addActivity: Locator;
	readonly addExpense: Locator;
	readonly page: Page;
	readonly totalMDFRequestAmount: Locator;

	constructor(page: Page) {
		this.addActivity = page.getByRole('button', {name: 'Add Activity'});
		this.addExpense = page.getByRole('button', {name: 'Add Expense'});
		this.page = page;
		this.totalMDFRequestAmount = page.getByText(
			'Total MDF Requested Amount:'
		);
	}

	activityName(index: number) {
		return this.page.locator(`input[name="activities[${index}].name"]`);
	}

	typeOfActivity(index: number) {
		return this.page.locator(
			`select[name="activities[${index}].typeActivity"]`
		);
	}

	tactic(index: number) {
		return this.page.locator(`select[name="activities[${index}].tactic"]`);
	}

	marketingActivity(index: number) {
		return this.page.locator(
			`input[name="activities[${index}].activityDescription.marketingActivity"]`
		);
	}

	leadGenerated(index: number) {
		return this.page.locator(
			`input[name="activities[${index}].activityDescription.leadGenerated"]`
		);
	}

	startDate(index: number) {
		return this.page.locator(
			`input[name="activities[${index}].startDate"]`
		);
	}

	endDate(index: number) {
		return this.page.locator(`input[name="activities[${index}].endDate"]`);
	}

	expense(activityIndex: number, budgetIndex: number) {
		return this.page.locator(
			`select[name="activities[${activityIndex}].budgets[${budgetIndex}].expense"]`
		);
	}

	budget(activityIndex: number, budgetIndex: number) {
		return this.page.locator(
			`input[name="activities[${activityIndex}].budgets[${budgetIndex}].cost"]`
		);
	}

	mdfRequestAmount(index: number) {
		return this.page.locator(
			`input[name="activities[${index}].mdfRequestAmount].cost"]`
		);
	}

	async selectTypeOfActivity(
		index: number,
		option: EMDFRequestActivityTypes
	) {
		await this.typeOfActivity(index).selectOption(option);
	}

	async selectTactic(index: number, option: EMDFRequestActivityTactics) {
		await this.tactic(index).selectOption(option);
	}

	async selectLeadGenerated(index: number, option: boolean) {
		await this.leadGenerated(index)
			.nth(option ? 0 : 1)
			.check();
	}

	async addActivityBudget(
		activityIndex: number,
		expenseIndex: number,
		expense: TMDFRequestActivityExpense
	) {
		await this.addExpense.click();

		await this.expense(activityIndex, expenseIndex).selectOption(
			expense.type
		);

		await this.budget(activityIndex, expenseIndex).fill(
			expense.value.toString()
		);
	}

	async fillForm(
		activityIndex: number,
		{
			activityName,
			claimPercent,
			endDate,
			expenses,
			leadGenerated,
			marketingActivity,
			startDate,
			tactic,
			typeOfActivity,
		}: TMDFRequestActivity
	) {
		const activityButton = await this.addActivity;
		await activityButton.click();

		await this.activityName(activityIndex).fill(activityName);

		await this.selectTypeOfActivity(activityIndex, typeOfActivity);

		await this.selectTactic(activityIndex, tactic);

		if (
			typeOfActivity === EMDFRequestActivityTypes.MISCELLANEOUS_MARKETING
		) {
			await this.marketingActivity(activityIndex).fill(marketingActivity);
		}

		await this.selectLeadGenerated(activityIndex, leadGenerated);

		await this.startDate(activityIndex).fill(startDate);

		await this.endDate(activityIndex).fill(endDate);

		let totalValue = 0;

		for (const [expenseIndex, expense] of expenses.entries()) {
			await this.addActivityBudget(activityIndex, expenseIndex, expense);

			totalValue += expense.value;
		}

		await expect(this.totalMDFRequestAmount).toHaveText(
			`Total MDF Requested Amount: ${totalValue * claimPercent}`
		);
	}
}
