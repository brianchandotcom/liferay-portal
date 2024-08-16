/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';
import path from 'path';

import {zipFolder} from '../../../../../utils/zip';
import {PublishProductPayload, Steps} from '../../types';

export class PublisherAppPage {
	readonly addPackagesButton: Locator;
	readonly backButton: Locator;
	readonly cloudCompatibleRadio: Locator;
	readonly confirmButton: Locator;
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
		support: {
			publisherWebsiteUrl: Locator;
			supportEmail: Locator;
			supportPhone: Locator;
		};
		version: {
			notes: Locator;
			version: Locator;
		};
	};
	protected publishProductPayload: PublishProductPayload;
	readonly logoUploadButton: Locator;
	readonly page: Page;
	readonly paidPriceModel: Locator;
	readonly selectFileButton: Locator;
	readonly standardLicenses: Locator;
	readonly submissionCheckbox: Locator;
	readonly submitButton: Locator;
	readonly zipFilesContainer: Locator;

	constructor(page: Page) {
		this.addPackagesButton = page.getByRole('button', {
			name: 'Add Package(s)',
		});
		this.backButton = page.getByRole('button', {name: 'Back'});
		this.cloudCompatibleRadio = page.locator('.radio-card-button-icon');

		this.confirmButton = page.getByRole('button', {name: 'Confirm'});
		this.continueButton = page.getByRole('button', {name: 'Continue'});
		this.form = {
			build: {
				cpu: page.getByPlaceholder('Enter the number of CPUs'),
				ram: page.getByPlaceholder('Enter the required RAM'),
			},
			profile: {
				categories: page.getByPlaceholder('Select categories'),
				description: page.getByPlaceholder('Enter app description'),
				name: page.getByPlaceholder('Enter app name'),
				tags: page.getByPlaceholder('Select tags'),
			},
			support: {
				publisherWebsiteUrl: page
					.getByPlaceholder('http:// Enter app name')
					.nth(1),
				supportEmail: page.getByPlaceholder(
					'Enter Support Email Address'
				),
				supportPhone: page.getByPlaceholder('Enter Support Phone'),
			},
			version: {
				notes: page.getByPlaceholder('Enter app description'),
				version: page.getByPlaceholder('0.0.0'),
			},
		};
		this.logoUploadButton = page.getByText('Upload Image');
		this.page = page;
		this.paidPriceModel = page
			.locator('div')
			.filter({hasText: /^Paid$/})
			.first();
		this.selectFileButton = page.getByRole('button', {
			name: 'Select a file',
		});
		this.standardLicenses = page.getByText('Standard License prices');
		this.submissionCheckbox = page.getByRole('checkbox');
		this.submitButton = page.getByRole('button', {
			name: 'Submit App',
		});

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

		await this.continue();
		await this.waitForStep('build');
	}

	async fillBuild() {
		expect(this.continueButton).toBeDisabled();

		if (this.publishProductPayload.cloudCompatible) {
			await this.cloudCompatibleRadio.first().click();

			await this.form.build.cpu.fill(
				this.publishProductPayload.resourceRequirements.cpus.toString()
			);
			await this.form.build.ram.fill(
				this.publishProductPayload.resourceRequirements.ram.toString()
			);
		}
		else {
			await this.cloudCompatibleRadio.last().click();
		}

		for (const compatibleOffering of this.publishProductPayload
			.compatibleOfferings) {
			await this.page
				.getByText(compatibleOffering, {exact: true})
				.click();
		}

		await this.addPackagesButton.click();

		await this.page
			.getByRole('heading', {
				name: 'Select Compatible Versions',
			})
			.waitFor({state: 'visible'});

		for (const dxpVersion of this.publishProductPayload.dxpVersions) {
			await this.page.getByText(dxpVersion, {exact: true}).click();
		}

		await this.confirmButton.click();

		let i = 0;

		for (const _ of this.publishProductPayload.dxpVersions) {
			await this.importFile(
				this.selectFileButton.nth(i),
				await zipFolder(
					path.join(
						__dirname,
						'../../dependencies/folder.marketplace.jar'
					)
				)
			);

			i++;
		}

		await this.continue();

		expect(this.continueButton).toBeDisabled();

		await this.waitForStep('storefront');
	}

	async fillLicensing() {
		await this.continue();

		await this.waitForStep('support');
	}

	async fillStoreFront() {
		expect(this.continueButton).toBeDisabled();

		await this.importFile(
			this.selectFileButton,
			this.publishProductPayload.logo
		);

		expect(this.continueButton).toBeEnabled();

		await this.continue();

		expect(this.continueButton).toBeDisabled();

		await this.waitForStep('version');
	}

	async fillVersion() {
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
		await this.waitForStep('pricing');
	}

	async fillPricing() {
		if (this.publishProductPayload.priceModel === 'paid') {
			await this.paidPriceModel.click();

			await this.continue(); // Select the App License
			await this.waitForStep('licensing');
			await this.continue();

			await expect(this.standardLicenses).toBeVisible();

			await this.continue();
			await this.waitForStep('support');
		}
		else {
			await this.continue();
			await this.waitForStep('licensing');
			await this.continue();
			await this.waitForStep('support');
		}
	}

	async fillSupport() {
		if (this.publishProductPayload.priceModel === 'free') {
			expect(this.continueButton).toBeEnabled();
			await this.continue();
		}
		else {
			expect(this.continueButton).toBeDisabled();
			await this.form.support.publisherWebsiteUrl.fill(
				this.publishProductPayload.support.publisherWebsiteUrl
			);
			await this.form.support.supportEmail.fill(
				this.publishProductPayload.support.supportEmail
			);
			await this.form.support.supportPhone.fill(
				this.publishProductPayload.support.supportPhone
			);
			await this.continue();
		}
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
