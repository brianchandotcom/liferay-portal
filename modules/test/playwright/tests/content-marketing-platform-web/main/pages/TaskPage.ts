/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApiHelpers} from '../../../../helpers/ApiHelpers';
import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';

export class TaskPage {
	readonly apiHelpers: ApiHelpers;
	readonly breadcrumb: Locator;
	readonly deleteMenuItem: Locator;
	readonly moreActionsButton: Locator;
	readonly newSubtaskButton: Locator;
	readonly page: Page;
	readonly subtasksSection: Locator;
	readonly subtasksTitle: Locator;

	constructor(page: Page) {
		this.apiHelpers = new ApiHelpers(page);
		this.breadcrumb = page.getByRole('navigation', {name: 'Breadcrumb'});
		this.deleteMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Delete',
		});
		this.moreActionsButton = page.getByRole('button', {
			name: 'More Actions',
		});
		this.page = page;
		this.subtasksTitle = page.getByRole('heading', {name: /^Subtasks/});

		this.subtasksSection = page
			.locator('.cms-section')
			.filter({has: this.subtasksTitle});

		this.newSubtaskButton = this.subtasksSection.getByRole('button', {
			name: 'New Subtask',
		});
	}

	getSubtaskRow(title: string) {
		return this.subtasksSection.getByRole('row').filter({hasText: title});
	}

	async goto(taskId: number) {
		const cmpTaskObjectDefinition =
			await this.apiHelpers.objectAdmin.getObjectDefinitionByName(
				'CMPTask'
			);

		const {classNameId} =
			await this.apiHelpers.jsonWebServicesClassName.fetchClassName(
				cmpTaskObjectDefinition.className
			);

		await this.page.goto(`/web/cms/e/task/${classNameId}/${taskId}`);
	}

	async openMoreActions() {
		await clickAndExpectToBeVisible({
			target: this.deleteMenuItem,
			trigger: this.moreActionsButton,
		});
	}

	async openSubtaskActions(title: string) {
		await clickAndExpectToBeVisible({
			target: this.deleteMenuItem,
			trigger: this.getSubtaskRow(title).getByRole('button', {
				name: `${title} Actions`,
			}),
		});
	}
}
