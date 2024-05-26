/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {PublishProductPayload, Steps} from '../types';

export class PublisherAppPage {
	readonly backButton: Locator;
	readonly cloudCompatibleRadio: Locator;
	readonly continueButton: Locator;
	readonly form: {
		build: {
			cpu: Locator;
			ram: Locator;
		};
		profile: {
			categories: Locator;
			description: Locator;
			name: Locator;
			tags: Locator;
		};
		version: {
			notes: Locator;
			version: Locator;
		};
	};
	protected publishProductPayload: PublishProductPayload;
	readonly logoUploadButton: Locator;
	readonly page: Page;
	readonly selectFileButton: Locator;
	readonly submissionCheckbox: Locator;
	readonly submitButton: Locator;
	readonly zipFilesContainer: Locator;

	constructor(page: Page) {
		this.backButton = page.getByRole('button', {name: 'Back'});
		this.cloudCompatibleRadio = page.locator('.radio-card-button-icon');
		this.continueButton = page.getByRole('button', {name: 'Continue'});
		this.form = {
			build: {
				cpu: page.getByPlaceholder('Enter the number of CPUs'),
				ram: page.getByPlaceholder('Enter the required RAM'),
			},
			profile: {
				categories: page
					.locator('div')
					.filter({hasText: /^Select categories$/})
					.nth(2),
				description: page.getByPlaceholder('Enter app description'),
				name: page.getByPlaceholder('Enter app name'),
				tags: page
					.locator('div')
					.filter({hasText: /^Select tags$/})
					.nth(2),
			},
			version: {
				notes: page.getByPlaceholder('Enter app description'),
				version: page.getByPlaceholder('0.0.0'),
			},
		};
		this.logoUploadButton = page.getByText('Upload Image');
		this.selectFileButton = page.getByRole('button', {
			name: 'Select a file',
		});
		this.submitButton = page.getByRole('button', {
			name: 'Submit App',
		});
		this.submissionCheckbox = page.getByRole('checkbox');

		this.page = page;
		this.zipFilesContainer = page.locator(
			'.document-file-list-item-container'
		);
	}

	setPublishProduct(publishProductPayload: PublishProductPayload) {
		this.publishProductPayload = publishProductPayload;
	}

	async importFile(locator: Locator, filePath: string) {
		const fileChooserPromise = this.page.waitForEvent('filechooser');

		await locator.click();

		const fileChooser = await fileChooserPromise;

		await fileChooser.setFiles(filePath);
	}

	async back() {
		expect(this.backButton).toBeEnabled();

		await this.backButton.click();
	}

	async continue() {
		expect(this.continueButton).toBeEnabled();

		await this.continueButton.click();
	}

	async checkHeader({accountName, appName}) {
		expect(await this.page.getByText(accountName)).toBeTruthy();
		expect(await this.page.getByText(appName)).toBeTruthy();
	}

	async fillProfile() {
		await this.waitForStep('profile');

		expect(this.continueButton).toBeDisabled();

		await this.importFile(
			this.logoUploadButton,
			this.publishProductPayload.logo
		);

		await this.form.profile.name.fill(this.publishProductPayload.name);
		await this.form.profile.description.fill(
			this.publishProductPayload.description
		);

		for (const category of this.publishProductPayload.categories ?? []) {
			await this.form.profile.categories.click();
			await this.page.getByText(category, {exact: true}).click();
		}

		for (const tag of this.publishProductPayload.tags ?? []) {
			await this.form.profile.tags.click();
			await this.page.getByText(tag, {exact: true}).click();
		}

		expect(this.continueButton).toBeEnabled();
	}

	async fillBuild() {
		await this.waitForStep('build');

		expect(this.continueButton).toBeDisabled();

		if (this.publishProductPayload.cloudCompatible) {
			await this.cloudCompatibleRadio.first().click();
			await this.form.build.cpu.fill(
				this.publishProductPayload.resourceRequirements.cpus.toString()
			);
			await this.form.build.ram.fill(
				this.publishProductPayload.resourceRequirements.ram.toString()
			);

			await this.importFile(
				this.selectFileButton,
				this.publishProductPayload.zipFiles[0]
			);

			expect(await this.zipFilesContainer).toHaveCount(1);
			expect(await this.zipFilesContainer).toContainText(
				this.publishProductPayload.zipFiles[0].split('/').at(-1)
			);
		} else {
			await this.cloudCompatibleRadio.last().click();
		}

		for (const compatibleOffering of this.publishProductPayload
			.compatibleOfferings) {
			await this.page
				.getByText(compatibleOffering, {exact: true})
				.click();
		}

		await this.continue();

		expect(this.continueButton).toBeDisabled();
	}

	async fillStoreFront() {
		await this.waitForStep('storefront');

		expect(this.continueButton).toBeDisabled();

		await this.importFile(
			this.selectFileButton,
			this.publishProductPayload.logo
		);

		expect(this.continueButton).toBeEnabled();

		await this.continue();

		expect(this.continueButton).toBeDisabled();
	}

	async fillVersion() {
		await this.waitForStep('version');

		expect(this.continueButton).toBeDisabled();
		expect(this.form.version.notes).toHaveValue('');
		expect(this.form.version.version).toHaveValue('1.0');

		await this.form.version.version.clear();
		await this.form.version.version.fill(
			this.publishProductPayload.version.version
		);
		await this.form.version.notes.fill(
			this.publishProductPayload.version.notes
		);

		await this.continue();
	}

	async fillPricing() {
		await this.waitForStep('pricing');

		await this.continue(); // Select the App Price
		await this.continue(); // Select Trial Condition
	}

	async fillSupport() {
		await this.waitForStep('support');

		await this.continue();
	}

	async reviewAndSubmit() {
		await this.waitForStep('submit');
		await this.submissionCheckbox.click();
		await this.submitButton.click();
		await this.page.waitForLoadState('networkidle');
	}

	async waitForStep(step: Steps) {
		await this.page.waitForSelector(`.list-item-selected-${step}`);
	}

	async goto() {
		await this.page.goto('/web/marketplace/publisher-dashboard', {
			waitUntil: 'networkidle',
		});
	}
}
