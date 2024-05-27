/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';
import path from 'path';

import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';

export class PublisherSolutionPage {
	readonly addContentBlockButton: Locator;
	readonly categories: Locator;
	readonly continueButton: Locator;
	readonly createTemplate: Locator;
	readonly customizeSolutionDetails: Locator;
	readonly customizeSolutionHeader: Locator;
	readonly defineSolution: Locator;
	readonly descriptionInput: Locator;
	readonly headerDescription: Locator;
	readonly headerTitle: Locator;
	readonly nameInput: Locator;
	readonly newSolutionButton: Locator;
	readonly page: Page;
	readonly radioUploadImages: Locator;
	readonly selectFileButton: Locator;
	readonly solutionDescription: Locator;
	readonly tags: Locator;

	constructor(page: Page) {
		this.addContentBlockButton = page.getByRole('button', {
			name: 'Add Content Block',
		});
		this.categories = page.getByPlaceholder('Select categories');
		this.continueButton = page.getByRole('button', {
			name: 'Continue',
		});
		this.createTemplate = page.getByText('Create template', {exact: true});
		this.customizeSolutionDetails = page.getByText(
			'Customize storefront solutions details',
			{exact: true}
		);
		this.customizeSolutionHeader = page.getByText(
			'Customize solution header',
			{exact: true}
		);
		this.defineSolution = page.getByText('Define the solution profile', {
			exact: true,
		});
		this.descriptionInput = page.getByPlaceholder(
			'Enter solution description'
		);
		this.headerDescription = page.locator('.ql-editor');
		this.headerTitle = page.getByPlaceholder('Enter title header');
		this.nameInput = page.getByPlaceholder('Enter solution name');
		this.newSolutionButton = page.getByRole('button', {
			name: 'New Solution Template',
		});
		this.page = page;
		this.radioUploadImages = page.getByRole('radio', {
			name: 'Upload images',
		});
		this.selectFileButton = page.getByRole('button', {
			name: 'Select a file',
		});
		this.solutionDescription = page.getByText(
			'Manage and publish solutions on the Marketplace'
		);
		this.tags = page.getByPlaceholder('Select tags');
	}

	async fillCustomizeSolutionDetails() {
		await expect(this.addContentBlockButton).toBeVisible();
	}

	async fillCustomizeSolutionHeader(header) {
		await this.headerTitle.fill(header.title);
		await this.headerDescription.fill(header.description);
		await expect(this.continueButton).toBeDisabled();
		await this.radioUploadImages.isChecked();
		await expect(this.selectFileButton).toBeEnabled();

		await this.importFile(
			this.selectFileButton,
			path.join(__dirname, '../dependencies/marketplace-test-icon.png')
		);
	}

	async fillDefineSolutionProfile(profile) {
		await this.nameInput.fill(profile.name);
		await this.descriptionInput.fill(profile.description);
		await expect(this.continueButton).toBeDisabled();
		await this.categories.fill('Analytics and Optimization');
		await this.page
			.getByRole('option', {name: 'AnalyticsandOptimization'})
			.click();
		await this.tags.fill('Agent Portal');
		await this.page.getByRole('option', {name: 'AgentPortal'}).click();
	}

	async goToCustomizeSolutionDetails() {
		await clickAndExpectToBeVisible({
			target: this.customizeSolutionDetails,
			trigger: this.continueButton,
		});
	}

	async goToCustomizeSolutionHeader() {
		await clickAndExpectToBeVisible({
			target: this.customizeSolutionHeader,
			trigger: this.continueButton,
		});
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
		await expect(this.solutionDescription).toBeVisible();
	}

	async importFile(locator: Locator, filePath: string) {
		const fileChooserPromise = this.page.waitForEvent('filechooser');

		await locator.click();

		const fileChooser = await fileChooserPromise;

		await fileChooser.setFiles(filePath);
	}
}
