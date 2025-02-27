/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {PORTLET_URLS} from '../../../utils/portletUrls';
import {waitForAlert} from '../../../utils/waitForAlert';

type FieldType = 'Text';

export class StructureBuilderPage {
	readonly page: Page;

	private readonly labelInput: Locator;
	private readonly publishButton: Locator;
	private readonly saveButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.labelInput = this.page.getByLabel('Structure Label');
		this.publishButton = this.page.getByRole('button', {name: 'Publish'});
		this.saveButton = this.page.getByRole('button', {name: 'Save'});
	}

	async goto() {
		await this.page.goto(PORTLET_URLS.cmsStructureBuilder);

		await this.page.getByText('New Structure').waitFor();
	}

	async addField(type: FieldType) {
		const hasFields = !(await this.page
			.getByText('No Fields Yet')
			.isVisible());

		let trigger: Locator;

		if (hasFields) {
			trigger = this.page.getByLabel('Add Field');
		}
		else {
			trigger = this.page.getByText('Add Field');
		}

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {exact: true, name: type}),
			trigger,
		});
	}

	async changeStructureLabel(label: string) {
		await this.labelInput.fill(label);

		await this.page.locator('span.label-item').click();
	}

	async deleteField({label, nth = 0}: {label: string; nth?: number}) {
		const count = await this.page
			.locator('.treeview-link', {
				hasText: label,
			})
			.count();

		const treeItem = this.page
			.locator('.treeview-link', {
				hasText: label,
			})
			.nth(nth);

		if (treeItem) {
			await treeItem.click();

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: this.page.getByRole('menuitem', {name: 'Delete Field'}),
				trigger: treeItem.getByLabel('Field Options'),
			});

			await expect(
				this.page.locator('.treeview-link', {
					hasText: label,
				})
			).toHaveCount(count - 1);
		}
	}

	async deleteStructure(id: number) {
		const apiHelpers = new ApiHelpers(this.page);

		const APIClient = await apiHelpers.buildRestClient(ObjectDefinitionApi);

		const {
			response: {statusCode},
		} = await APIClient.deleteObjectDefinition(id);

		expect(statusCode).toBe(204);
	}

	async publishStructure() {
		await this.publishButton.click();

		await waitForAlert(this.page, 'published successfully', {
			timeout: 1000,
		});
	}

	async saveStructure() {
		const save = async () => {
			await this.saveButton.click();

			await waitForAlert(this.page, 'successfully', {timeout: 1000});
		};

		const [response] = await Promise.all([
			this.page.waitForResponse(
				(response) =>
					response.url().includes('object-definitions') &&
					response.status() === 200
			),
			await save(),
		]);

		const {id} = await response.json();

		return {id};
	}
}
