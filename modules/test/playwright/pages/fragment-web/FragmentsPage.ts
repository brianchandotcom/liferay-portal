/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import fillAndClickOutside from '../../utils/fillAndClickOutside';
import {PORTLET_URLS} from '../../utils/portletUrls';
import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';

export class FragmentsPage {
	readonly page: Page;

	readonly selectFragmentIFrame: FrameLocator;

	constructor(page: Page) {
		this.page = page;
		this.selectFragmentIFrame = page.frameLocator(
			'iframe[title="Select Fragment"]'
		);
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.fragments}`
		);
	}

	async gotoFragmentSet(name: string) {
		await this.page
			.getByRole('menuitem', {
				exact: true,
				name,
			})
			.click();

		await this.page
			.locator('.sheet-title')
			.getByText(name, {exact: true})
			.waitFor();
	}

	async gotoSelectFragmentConfiguration(
		fragmentCollectionName: string,
		siteName: string,
		type: string
	) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: 'Configuration'}),
			trigger: this.page.getByLabel('Options'),
		});

		await this.page
			.getByRole('cell', {name: type})
			.getByRole('button')
			.click();

		await this.selectFragmentIFrame
			.locator('.nav-link')
			.filter({hasText: siteName})
			.click();

		await this.selectFragmentIFrame
			.getByRole('link', {exact: true, name: fragmentCollectionName})
			.click();
	}

	async copyFragment(title: string) {
		await this.clickAction('Make a Copy', title);

		await waitForSuccessAlert(
			this.page,
			'Success:The fragment was copied successfully.'
		);
	}

	async copyFragmentToSet(fragmentName: string, setName: string) {
		const setExists = await this.page
			.getByRole('menuitem', {name: setName})
			.isVisible();

		await this.clickAction('Copy To', fragmentName);

		await this.page.locator('.modal-body').waitFor();

		if (await this.page.getByText('Add Fragment Set').isVisible()) {
			await this.page.getByLabel('Name').fill(setName);
		}
		else if (setExists) {
			await this.page
				.getByLabel('Fragment Sets')
				.selectOption({label: setName});
		}
		else {
			await this.page.getByText('Save In New Set').click();

			await this.page.getByLabel('Name').fill(setName);
		}

		await this.page
			.getByRole('button', {exact: true, name: 'Save'})
			.click();

		await waitForSuccessAlert(
			this.page,
			'Success:The fragment was copied successfully.'
		);
	}

	async clickAction(action: string, title: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: action}),
			trigger: this.page
				.locator(`//p[@title="${title}"]/../..`)
				.getByLabel('More actions'),
		});
	}

	async createFragmentSet(name: string) {
		await this.page.getByTitle('Add Fragment Set').click();

		const nameInput = this.page.getByPlaceholder('Name');

		await nameInput.waitFor();

		await fillAndClickOutside(this.page, nameInput, name);

		await this.page.getByRole('button', {name: 'Save'}).click();

		await waitForSuccessAlert(this.page);
	}

	async createFragment(
		setName: string,
		name: string,
		fragmentType?: 'basic' | 'form',
		fieldTypes?: string[]
	) {
		await this.gotoFragmentSet(setName);

		await this.page.getByRole('button', {name: 'Add'}).click();

		await this.page.getByRole('heading', {name: 'Add Fragment'}).waitFor();

		if (fragmentType === 'form') {
			await this.page
				.locator('.fragment-type-card')
				.filter({hasText: 'Form Fragment'})
				.click();
		}

		await this.page.getByRole('button', {name: 'Next'}).click();

		await this.page.getByLabel('Name').fill(name);

		if (fragmentType === 'form') {
			for (const fieldType of fieldTypes) {
				await this.page.getByLabel(fieldType).check();
			}
		}

		await this.page.getByText('Add', {exact: true}).click();

		await waitForSuccessAlert(this.page);
	}

	async deleteFragment(title: string) {
		await this.clickAction('Delete', title);

		await this.page.getByRole('button', {name: 'Delete'}).click();

		await waitForSuccessAlert(this.page);
	}

	async deleteFragmentSet(setName: string) {
		await this.gotoFragmentSet(setName);

		await this.page
			.locator('.sheet-title')
			.getByLabel('Show Actions')
			.click();

		await this.page.getByRole('menuitem', {name: 'Delete'}).click();

		await this.page.getByRole('button', {name: 'Delete'}).click();

		await waitForSuccessAlert(this.page);
	}

	async markAsCacheable(title: string) {
		this.page.on('dialog', (dialog) => dialog.accept());

		await this.clickAction('Mark as Cacheable', title);

		await waitForSuccessAlert(this.page);
	}

	async renameFragment(newName: string, oldName: string) {
		await this.clickAction('Rename', oldName);

		await this.page.getByLabel('Name', {exact: true}).fill(newName);

		await this.page.getByRole('button', {name: 'Save'}).click();

		await waitForSuccessAlert(this.page);
	}
}
