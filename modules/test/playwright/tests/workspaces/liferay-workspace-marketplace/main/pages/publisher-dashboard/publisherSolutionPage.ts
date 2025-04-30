/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';
import path from 'path';

import {clickAndExpectToBeVisible} from '../../../../../utils/clickAndExpectToBeVisible';

export class PublisherSolutionPage {
	readonly accountSearchDropdown: Locator;
	readonly addContentBlockButton: Locator;
	readonly becomePublisherForm: Locator;
	readonly blocksTitle: Locator;
	readonly categories: Locator;
	readonly chooseBlockSelect: Locator;
	readonly companyProfile: Locator;
	readonly contactUs: Locator;
	readonly continueButton: Locator;
	readonly createTemplate: Locator;
	readonly customizeSolutionDetails: Locator;
	readonly customizeSolutionHeader: Locator;
	readonly defineSolution: Locator;
	readonly descriptionInput: Locator;
	readonly emailInput: Locator;
	readonly headerTitle: Locator;
	readonly newSolutionButton: Locator;
	readonly notAvailableAlert: Locator;
	readonly page: Page;
	readonly phoneInput: Locator;
	readonly radioUploadImages: Locator;
	readonly reviewAndSubmitTitle: Locator;
	readonly reviewCheckbox: Locator;
	readonly richTextEditor: Locator;
	readonly saveButton: Locator;
	readonly selectContentBlock: Locator;
	readonly selectFileButton: Locator;
	readonly solutionDescription: Locator;
	readonly solutionName: Locator;
	readonly solutionNameInput: Locator;
	readonly submitSolutionButton: Locator;
	readonly tags: Locator;
	readonly underReviewStatus: Locator;
	readonly website: Locator;

	constructor(page: Page) {
		this.accountSearchDropdown = page.locator('#account-search.dropdown');
		this.addContentBlockButton = page.getByRole('button', {
			name: 'Add Content Block',
		});
		this.becomePublisherForm = page.getByRole('link', {
			name: 'please complete this form.',
		});
		this.blocksTitle = page.getByPlaceholder('Enter title');
		this.categories = page.getByPlaceholder('Select categories');
		this.chooseBlockSelect = page.getByRole('option', {
			name: 'Continue',
		});
		this.companyProfile = page.getByText('Provide company profile details');
		this.contactUs = page.getByText('Provide contact us details');
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
		this.emailInput = page.getByPlaceholder('name@yourdomain.com');
		this.headerTitle = page.getByPlaceholder('Enter title header');
		this.newSolutionButton = page.getByRole('button', {
			name: 'New Solution Template',
		});
		this.notAvailableAlert = page.getByRole('alert');
		this.page = page;
		this.phoneInput = page.getByPlaceholder('+1 (123) 456-7890');
		this.richTextEditor = page.locator('.ql-editor');
		this.saveButton = page.getByRole('button', {exact: true, name: 'Save'});
		this.solutionNameInput = page.getByPlaceholder('Enter solution name');
		this.radioUploadImages = page.getByRole('radio', {
			name: 'Upload images',
		});
		this.reviewAndSubmitTitle = page.getByText(
			'Review and submit solution'
		);
		this.reviewCheckbox = page.getByRole('checkbox');
		this.selectContentBlock = page.getByText('Select Content Block');
		this.selectFileButton = page.getByRole('button', {
			name: 'Select a file',
		});
		this.solutionDescription = page.getByText(
			'Manage and publish solutions on the Marketplace'
		);
		this.submitSolutionButton = page.getByRole('button', {
			name: 'Submit Solution',
		});
		this.tags = page.getByPlaceholder('Select tags');
		this.underReviewStatus = page
			.getByRole('cell', {name: 'Under Review'})
			.last();

		this.website = page.getByPlaceholder('http://www.yourdomain.com');
	}

	async fillCompanyProfile(companyProfile) {
		await this.richTextEditor.fill(companyProfile.description);

		await expect(this.continueButton).toBeDisabled();

		await this.website.fill(companyProfile.website);
		await this.emailInput.fill(companyProfile.email);
		await this.phoneInput.fill(companyProfile.phone);
	}

	async fillCustomizeSolutionDetails(details) {
		await expect(this.addContentBlockButton).toBeVisible();

		await this.addContentBlockButton.click();

		await expect(this.saveButton).toBeDisabled();

		await this.selectContentBlock.waitFor({state: 'visible'});
		await this.page.selectOption(
			'select[aria-label="Select Label"]',
			'text-block'
		);
		await expect(this.saveButton).toBeEnabled();

		await this.saveButton.click();
		await this.blocksTitle.first().fill(details['text-block'].title);
		await this.richTextEditor
			.first()
			.fill(details['text-block'].description);
		await this.addContentBlockButton.click();
		await this.selectContentBlock.waitFor({state: 'visible'});
		await this.page.selectOption(
			'select[aria-label="Select Label"]',
			'text-images-block'
		);
		await this.saveButton.click();

		await expect(this.selectFileButton).toBeEnabled();

		await this.blocksTitle.last().fill(details['text-images'].title);
		await this.richTextEditor
			.last()
			.fill(details['text-images'].description);
		await this.importFile(
			this.selectFileButton,
			path.join(__dirname, '../../dependencies/marketplace-icon.png')
		);
	}

	async fillCustomizeSolutionHeader(header) {
		await this.headerTitle.fill(header.title);
		await this.richTextEditor.fill(header.description);

		await expect(this.continueButton).toBeDisabled();

		await this.radioUploadImages.isChecked();

		await expect(this.selectFileButton).toBeEnabled();

		await this.importFile(
			this.selectFileButton,
			path.join(__dirname, '../../dependencies/marketplace-icon.png')
		);
	}

	async fillDefineSolutionProfile(profile) {
		await this.solutionNameInput.fill(profile.name);
		await this.descriptionInput.fill(profile.description);

		await expect(this.continueButton).toBeDisabled();

		await this.categories.fill('Analytics');
		await this.page
			.getByRole('option', {name: 'Analytics and Optimization'})
			.click();
		await this.tags.fill('Agent');
		await this.page.getByRole('option', {name: 'Agent Portal'}).click();
	}

	async goToContactUs() {
		await clickAndExpectToBeVisible({
			target: this.contactUs,
			trigger: this.continueButton,
		});
	}

	async goToCompanyProfile() {
		await clickAndExpectToBeVisible({
			target: this.companyProfile,
			trigger: this.continueButton,
		});
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
		await this.newSolutionButton.click();
		await this.createTemplate.waitFor({state: 'visible'});
	}

	async selectAccount(accountName: string) {
		const accountOption = this.page.getByRole('menuitem', {
			name: accountName,
		});

		await clickAndExpectToBeVisible({
			target: accountOption,
			trigger: this.accountSearchDropdown,
		});

		await accountOption.click();

		// Necessary to wait few seconds because the page forces a full reload
		// using window.reload()

		await this.page.waitForTimeout(2000);
	}

	async reviewAndSubmit() {
		await this.reviewCheckbox.check();

		await expect(this.submitSolutionButton).toBeEnabled();

		await this.submitSolutionButton.click();
		await this.page.waitForLoadState('networkidle');
	}

	async goto(url: string) {
		await this.page.goto(url, {
			waitUntil: 'networkidle',
		});

		await expect(this.solutionDescription).toBeVisible();
	}

	async importFile(locator: Locator, filePath: string) {
		const fileChooserPromise = this.page.waitForEvent('filechooser');

		await locator.click();

		const fileChooser = await fileChooserPromise;

		await fileChooser.setFiles(filePath);
	}
}
