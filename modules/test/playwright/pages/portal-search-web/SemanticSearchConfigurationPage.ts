/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {PORTLET_URLS} from '../../utils/portletUrls';

export class SemanticSearchConfigurationPage {
	readonly bringYourOwnLLMCapabilityAlert: Locator;
	readonly bringYourOwnLLMEnabledCheckbox: Locator;
	readonly page: Page;
	readonly textEmbeddingProviderSelect: Locator;

	constructor(page: Page) {
		this.page = page;

		this.bringYourOwnLLMCapabilityAlert = page.getByTestId(
			'bringYourOwnLLMCapabilityAlert'
		);
		this.bringYourOwnLLMEnabledCheckbox = page.getByTestId(
			'bringYourOwnLLMEnabledCheckbox'
		);
		this.textEmbeddingProviderSelect = page.getByLabel(
			'Text Embedding Provider'
		);
	}

	async goto() {
		await this.page.goto(PORTLET_URLS.semanticSearchConfiguration);

		await expect(this.textEmbeddingProviderSelect).toBeVisible();
	}
}
