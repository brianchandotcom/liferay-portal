/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export enum EEditorType {
	CKEDITOR4 = 'ckeditor4',
	CKEDITOR5 = 'ckeditor5',
}

export async function waitForEditor({
	container: containerProp,
	editorType = EEditorType.CKEDITOR5,
	page,
}: {
	container?: Locator;
	editorType?: EEditorType;
	page: Page;
}) {
	if (editorType === EEditorType.CKEDITOR5) {
		const container = containerProp ?? page.locator('.lfr-ck');

		await container.locator('.ck-content').waitFor({state: 'visible'});
	}
	else {
		const container = containerProp ?? page.locator('.cke');

		await container
			.frameLocator('iframe')
			.locator('.cke_editable')
			.waitFor({state: 'visible'});
	}
}

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
