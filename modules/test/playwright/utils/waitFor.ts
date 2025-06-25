/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

export enum EFDSVisualizationMode {
	CARDS = 'cards',
	LIST = 'list',
	TABLE = 'table',
}

export async function waitForFDS({
	page,
	visualizationMode,
}: {
	page: Page;
	visualizationMode: EFDSVisualizationMode;
}) {
	if (visualizationMode === EFDSVisualizationMode.CARDS) {
		await page.locator('.fds .cards-container').waitFor({state: 'visible'});
	}
	else if (visualizationMode === EFDSVisualizationMode.LIST) {
		await page.locator('.fds .list-sheet').waitFor({state: 'visible'});
	}
	else if (visualizationMode === EFDSVisualizationMode.TABLE) {
		await page.locator('.fds .table').waitFor({state: 'visible'});
	}
}

export async function waitForInputLocalized(page: Page, id: string) {
	await page.evaluate(async (id) => await Liferay.componentReady(id), id);
}
