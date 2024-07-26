/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {TMDFRequest} from '../../types/mdf';
import {MDFRequestFormActivitiesPage} from './MDFRequestFormActivitiesPage';
import {MDFRequestFormGoalsPage} from './MDFRequestFormGoalsPage';
import {MDFRequestFormReviewPage} from './MDFRequestFormReviewPage';

export class MDFRequestFormPage {
	readonly backButton: Locator;
	readonly cancelButton: Locator;
	readonly continueButton: Locator;
	readonly form: {
		activities: MDFRequestFormActivitiesPage;
		goals: MDFRequestFormGoalsPage;
		review: MDFRequestFormReviewPage;
	};
	readonly newRequestButton: Locator;
	readonly page: Page;
	readonly previousButton: Locator;
	readonly saveAsDraftButton: Locator;
	readonly site: Site;
	readonly seeMDFHomeButton: Locator;
	readonly statusDropdown: Locator;
	readonly submitButton: Locator;
	readonly successMessage: Locator;

	constructor(page: Page, site: Site) {
		this.backButton = page.getByText('← Back');
		this.cancelButton = page.getByRole('button', {name: 'Cancel'});
		this.continueButton = page.getByRole('button', {name: 'Continue'});
		this.form = {
			activities: new MDFRequestFormActivitiesPage(page),
			goals: new MDFRequestFormGoalsPage(page),
			review: new MDFRequestFormReviewPage(page),
		};
		this.page = page;
		this.previousButton = page.getByRole('button', {name: 'Previous'});
		this.saveAsDraftButton = page.getByRole('button', {
			name: 'Save as Draft',
		});
		this.seeMDFHomeButton = page.getByRole('button', {
			name: 'See MDF Home',
		});
		this.site = site;
		this.statusDropdown = page
			.locator('liferay-partner-custom-element div')
			.nth(2);
		this.submitButton = page.getByRole('button', {
			name: 'Submit',
		});
		this.successMessage = page.getByText('Success!');
	}

	async continue() {
		expect(this.continueButton).toBeEnabled();

		await this.continueButton.click();
	}

	async createNewRequest(form: TMDFRequest) {
		await this.form.goals.fillForm(form.goals);
		await this.continue();

		for (const [index, activity] of form.activities.entries()) {
			await this.form.activities.fillForm(index, activity);

			await this.continue();
		}

		await this.continue();
	}

	async goto() {
		await this.page.goto(
			`web/${this.site.friendlyUrlPath}/marketing/mdf-requests/new`,
			{
				waitUntil: 'commit',
			}
		);
	}

	async reviewMDFRequest(activityContent) {
		await this.form.review.reviewMDFContent(activityContent);
	}

	async statusDropDownOption(option: string) {
		await this.page
			.getByRole('menuitem', {
				name: option,
			})
			.click();
	}
}
