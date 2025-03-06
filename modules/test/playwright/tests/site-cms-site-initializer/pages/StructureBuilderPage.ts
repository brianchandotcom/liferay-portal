/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectDefinitionApi} from '@liferay/object-admin-rest-client-js';
import {Locator, Page, expect} from '@playwright/test';

import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {PORTLET_URLS} from '../../../utils/portletUrls';
import {waitForAlert} from '../../../utils/waitForAlert';

export const FIELD_TYPES = [
	'Text',
	'Long Text',
	'Rich Text',
	'Integer',
	'Decimal',
	'Single Select',
	'Multiselect',
	'Date',
	'Date and Time',
	'Boolean',
	'Upload',
] as const;

type FieldType = (typeof FIELD_TYPES)[number];

export class StructureBuilderPage {
	readonly page: Page;

	private readonly labelInput: Locator;
	private readonly nameInput: Locator;
	private readonly publishButton: Locator;
	private readonly saveButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.labelInput = this.page.getByLabel('Structure Label');
		this.nameInput = this.page.getByLabel('Structure Name');
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

	async changeFieldSettings({
		erc,
		label,
		localizable,
		mandatory,
		name,
	}: {
		erc?: string;
		label?: string;
		localizable?: boolean;
		mandatory?: boolean;
		name?: string;
	}) {
		if (erc) {
			const ercInput = this.page.getByLabel('ERC');

			await ercInput.fill(erc);
			await ercInput.blur();
		}

		if (name) {
			const fieldNameInput = this.page.getByLabel('Field Name');

			await fieldNameInput.fill(name);
			await fieldNameInput.blur();
		}

		if (label) {
			const labelInput = this.page.getByLabel('Label');

			await labelInput.fill(label);
			await labelInput.blur();
		}

		if (localizable) {
			await this.page.getByLabel('Localizable').click();
		}

		if (mandatory) {
			await this.page.getByLabel('Mandatory').click();
		}
	}

	async changeStructureSettings({
		erc,
		label,
		name,
	}: {
		erc?: string;
		label?: string;
		name?: string;
	}) {
		if (erc) {
			const ercInput = this.page.getByLabel('ERC');
			await ercInput.fill(erc);
			await ercInput.blur();
		}

		if (label) {
			await this.labelInput.fill(label);
			await this.labelInput.blur();
		}

		if (name) {
			await this.nameInput.fill(name);
			await this.nameInput.blur();
		}
	}

	async deleteField({label, nth = 0}: {label: string; nth?: number}) {
		const count = await this.page
			.locator('.treeview-item')
			.getByLabel(label, {exact: true})
			.count();

		const treeItem = this.page
			.locator('.treeview-item')
			.getByLabel(label, {exact: true})
			.nth(nth);

		if (treeItem) {
			await treeItem.click();

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: this.page.getByRole('menuitem', {name: 'Delete'}),
				trigger: treeItem.getByLabel('Field Options'),
			});

			await expect(
				this.page
					.locator('.treeview-item')
					.getByLabel(label, {exact: true})
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
		const publish = async () => {
			await this.publishButton.click();

			await waitForAlert(this.page, 'published successfully', {
				timeout: 1000,
			});
		};

		const [response] = await Promise.all([
			this.page.waitForResponse(
				(response) =>
					response.url().includes('object-definitions') &&
					response.status() === 200,
				{timeout: 5000}
			),
			await publish(),
		]);

		return await response.json();
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
					response.status() === 200,
				{timeout: 5000}
			),
			await save(),
		]);

		return await response.json();
	}

	async selectField({label, nth = 0}: {label: string; nth?: number}) {
		await clickAndExpectToBeVisible({
			target: this.page.locator('.breadcrumb-link', {hasText: label}),
			trigger: this.page
				.locator('.treeview-item')
				.getByLabel(label, {exact: true})
				.nth(nth),
		});
	}
}
