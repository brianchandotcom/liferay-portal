/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';

export class PublisherSolutionPage {
	readonly categories: Locator;
	readonly continueButton: Locator;
	readonly createTemplate: Locator;
	readonly defineSolution: Locator;
	readonly descriptionInput: Locator;
	readonly nameInput: Locator;
	readonly newSolutionButton: Locator;
	readonly page: Page;
	readonly solutionTitle: Locator;
	readonly tags: Locator;

	constructor(page: Page) {
		this.categories = page.getByPlaceholder('Select categories');
		this.continueButton = page.getByRole('button', {
			name: 'Continue',
		});
		this.createTemplate = page.getByText('Create template', {exact: true});
		this.defineSolution = page.getByText('Define the solution profile', {
			exact: true,
		});
		this.descriptionInput = page.getByPlaceholder(
			'Enter solution description'
		);
		this.nameInput = page.getByPlaceholder('Enter solution name');
		this.newSolutionButton = page.getByRole('button', {
			name: 'New Solution Template',
		});
		this.page = page;
		this.solutionTitle = page.getByText('Solutions', {exact: true});
		this.tags = page.getByPlaceholder('Select tags');
	}

	async fillDefineSolutionProfile(name, description) {
		await this.nameInput.fill(name);
		await this.descriptionInput.fill(description);
		await this.categories.fill('Analytics and Optimization');
		await this.page
			.getByRole('option', {name: 'AnalyticsandOptimization'})
			.click();
		await this.tags.fill('Agent Portal');
		await this.page.getByRole('option', {name: 'AgentPortal'}).click();
	}

	async goToDefineSolutionProfile() {
		await clickAndExpectToBeVisible({
			target: this.defineSolution,
			trigger: this.continueButton,
		});
	}

	async goToNewSolution() {
		await clickAndExpectToBeVisible({
			target: this.createTemplate,
			trigger: this.newSolutionButton,
		});
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(siteUrl);
		await expect(this.solutionTitle).toBeVisible;
	}
}
