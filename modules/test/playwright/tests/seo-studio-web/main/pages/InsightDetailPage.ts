/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class InsightDetailPage {
	readonly page: Page;

	readonly affectedPagesHeading: Locator;
	readonly descriptionSectionTitle: Locator;
	readonly onPageBreadcrumbLink: Locator;
	readonly suggestionSectionTitle: Locator;
	readonly table: Locator;

	constructor(page: Page) {
		this.page = page;

		this.affectedPagesHeading = this.page.getByRole('heading', {
			level: 4,
			name: /^Affected Pages/,
		});
		this.descriptionSectionTitle = this.page.getByText('Description', {
			exact: true,
		});
		this.onPageBreadcrumbLink = this.page
			.locator('.breadcrumb-item')
			.getByRole('link', {name: 'On Page'});
		this.suggestionSectionTitle = this.page.getByText('Suggestion', {
			exact: true,
		});
		this.table = this.page.locator('.insight-detail-affected-pages table');
	}

	getAffectedPageRow(title: string): Locator {
		return this.table.getByRole('row', {name: new RegExp(title)});
	}

	getTitleHeader(): Locator {
		return this.table.getByRole('columnheader', {name: 'Title'});
	}

	getTypeHeader(): Locator {
		return this.table.getByRole('columnheader', {name: 'Type'});
	}
}
