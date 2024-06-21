/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {
	MDFRequestFormActivities,
	MDFRequestFormActivitiesContent,
} from './MDFRequestFormActivities';
import {
	MDFRequestFormGoals,
	MDFRequestFormGoalsContent,
} from './MDFRequestFormGoals';

type FormContent = {
	activities: MDFRequestFormActivitiesContent[];
	goals: MDFRequestFormGoalsContent;
};

export class MDFRequestFormPage {
	readonly cancelButton: Locator;
	readonly continueButton: Locator;
	readonly form: {
		activities: MDFRequestFormActivities;
		goals: MDFRequestFormGoals;
		review: {};
	};
	readonly newRequestButton: Locator;
	readonly page: Page;
	readonly previousButton: Locator;
	readonly saveAsDraftButton: Locator;
	readonly submitButton: Locator;
	readonly seeMDFHomeButton: Locator;
	readonly successMessage: Locator;

	constructor(page: Page) {
		this.page = page;
		this.cancelButton = page.getByRole('button', {name: 'Cancel'});
		this.continueButton = page.getByRole('button', {name: 'Continue'});
		this.form = {
			activities: new MDFRequestFormActivities(page),
			goals: new MDFRequestFormGoals(page),
			review: {},
		};
		this.previousButton = page.getByRole('button', {name: 'Previous'});
		this.saveAsDraftButton = page.getByRole('button', {
			name: 'Save as Draft',
		});
		this.submitButton = page.getByRole('button', {
			name: 'Submit',
		});
		this.newRequestButton = page.getByRole('button', {
			name: 'New Request',
		});
		this.seeMDFHomeButton = page.getByRole('button', {
			name: 'See MDF Home',
		});
		this.successMessage = page.getByText('Success!');
	}

	async createNewRequest(form: FormContent) {
		await this.newRequestButton.click();

		await this.form.goals.fillForm(form.goals);

		await this.continueButton.click();

		for (const [index, activity] of form.activities.entries()) {
			await this.form.activities.fillForm(index, activity);

			await this.continueButton.click();
		}

		await this.continueButton.click();
	}
}
