/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {TMDFRequest} from '../../types/mdf';
import {PARTNER_SITE_FRIENLY_URL_PATH} from '../../utils/constants';
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
	readonly heading: Locator;
	readonly newRequestButton: Locator;
	readonly page: Page;
	readonly previousButton: Locator;
	readonly saveAsDraftButton: Locator;
	readonly seeMDFHomeButton: Locator;
	readonly statusDropdown: Locator;
	readonly submitButton: Locator;
	readonly successMessage: Locator;

	constructor(page: Page) {
		this.page = page;

		this.backButton = this.page.getByText('← Back');
		this.cancelButton = this.page.getByRole('button', {name: 'Cancel'});
		this.continueButton = this.page.getByRole('button', {name: 'Continue'});
		this.form = {
			activities: new MDFRequestFormActivitiesPage(this.page),
			goals: new MDFRequestFormGoalsPage(this.page),
			review: new MDFRequestFormReviewPage(this.page),
		};
		this.heading = this.page.getByRole('heading', {
			name: 'MDF Request',
		});
		this.previousButton = this.page.getByRole('button', {name: 'Previous'});
		this.saveAsDraftButton = this.page.getByRole('button', {
			name: 'Save as Draft',
		});
		this.seeMDFHomeButton = this.page.getByRole('button', {
			name: 'See MDF Home',
		});
		this.statusDropdown = this.page
			.locator('liferay-partner-custom-element div')
			.nth(2);
		this.submitButton = this.page.getByRole('button', {
			name: 'Submit',
		});
		this.successMessage = this.page.getByText('Success!');
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
			`${PARTNER_SITE_FRIENLY_URL_PATH}/marketing/mdf-requests/new`,
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
