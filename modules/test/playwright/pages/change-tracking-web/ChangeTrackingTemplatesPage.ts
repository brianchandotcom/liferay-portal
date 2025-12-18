/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../utils/getRandomString';
import {ChangeTrackingPage} from './ChangeTrackingPage';

export class ChangeTrackingTemplatesPage {
	readonly page: Page;

	private readonly changeTrackingPage: ChangeTrackingPage;

	private readonly addUsersButton: Locator;
	private readonly createButton: Locator;
	private readonly defaultTemplateCheckbox: Locator;
	private readonly newTemplateButton: Locator;
	private readonly publicationCollaboratorsPanel: Locator;
	private readonly publicationNameField: Locator;
	private readonly templateNameField: Locator;

	constructor(page: Page) {
		this.page = page;
		this.changeTrackingPage = new ChangeTrackingPage(page);

		this.addUsersButton = page.getByRole('button', {name: 'Add Users'});
		this.createButton = page.getByRole('button', {name: 'Create'});
		this.defaultTemplateCheckbox = page.getByRole('checkbox', {
			name: 'Default Template',
		});
		this.newTemplateButton = page
			.getByTestId('creationMenuNewButton')
			.getByText('New');
		this.publicationCollaboratorsPanel = page.locator(
			'//button[descendant::text()="Publication Collaborators"]'
		);
		this.publicationNameField = page
			.getByRole('textbox', {name: 'Name'})
			.nth(1);
		this.templateNameField = page
			.getByRole('textbox', {name: 'Name'})
			.nth(0);
	}

	async addTemplate(
		name?: string,
		defaultTemplate?: boolean,
		publicationName?: string
	) {
		if (!name) {
			name = getRandomString();
		}

		await this.templateNameField.fill(name);

		if (defaultTemplate) {
			await this.defaultTemplateCheckbox.setChecked(true);
		}

		await this.page.getByText('Publication Information').click();

		if (!publicationName) {
			publicationName = '${RANDOM_HASH}';
		}

		await this.publicationNameField.fill(publicationName);

		await this.createButton.click();
	}

	async deleteTemplate(name: string) {
		await this.goto();

		await this.page.getByRole('row', {name}).getByRole('button').click();

		this.page.once('dialog', (dialog) => {
			dialog.accept();
		});

		await this.page.getByRole('menuitem', {name: 'Delete'}).click();

		await expect(this.page.getByText(name)).not.toBeVisible();
	}

	async goto() {
		await this.changeTrackingPage.goto();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: 'Templates'}),
			trigger: this.page.getByLabel('Options'),
		});

		await expect(
			this.page.getByText('Publication Templates')
		).toBeVisible();
	}

	async gotoCreateTemplate() {
		await this.goto();

		await this.newTemplateButton.click();

		await expect(
			this.page.getByRole('heading', {
				name: 'Create a New Publication Template',
			})
		).toBeVisible();

		await expect(this.templateNameField).toBeVisible();
	}

	async openManageCollaboratorsModal() {
		if (
			(await this.publicationCollaboratorsPanel.getAttribute(
				'aria-expanded'
			)) === 'false'
		) {
			await this.publicationCollaboratorsPanel.click();

			await expect(this.addUsersButton).toBeVisible();
		}

		await this.addUsersButton.click();
	}
}
