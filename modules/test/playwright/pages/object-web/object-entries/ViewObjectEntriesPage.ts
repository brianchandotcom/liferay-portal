/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {PORTLET_URLS} from '../../../utils/portletUrls';

export class ViewObjectEntriesPage {
	readonly addObjectEntryButton: Locator;
	readonly backButton: Locator;
	readonly duplicateEntryErrorMessage: Locator;
	readonly page: Page;
	readonly saveObjectEntryButton: Locator;
	readonly successMessage: Locator;

	constructor(page: Page) {
		this.addObjectEntryButton = page
			.getByTestId('fdsCreationActionButton')
			.first();
		this.backButton = page.getByTitle('Back');
		this.duplicateEntryErrorMessage = page.getByText(
			'Error:The field values are already in use. Please choose unique values.'
		);
		this.page = page;
		this.saveObjectEntryButton = page.getByRole('button', {name: 'Save'});
		this.successMessage = page.getByText(
			'Your request completed successfully.'
		);
	}

	async assertErrorWithDuplicateEntryValue() {
		await this.duplicateEntryErrorMessage.waitFor();
		await expect(this.duplicateEntryErrorMessage).toBeVisible();
	}

	async fillObjectEntry(fieldName: string, fieldValue: string) {
		await this.page.getByLabel(fieldName).fill(fieldValue);
	}

	async goto(objectDefinitionId: number, siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl ?? '/guest'}${
				PORTLET_URLS.objects
			}_${objectDefinitionId}`,
			{waitUntil: 'load'}
		);
	}
}
