/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {expect, test} from '@playwright/test';
import * as path from 'path';

import {zipFolder} from '../../utils/util';

test('can import a folder with document type restrictions and workflow', async ({
	page,
}) => {
	await page.goto('/');
	const openProductMenu = page.getByLabel('Open Product Menu');
	if (await openProductMenu.isVisible()) {
		await openProductMenu.click();
	}
	await page.getByRole('menuitem', {name: 'Content & Data'}).click();
	await page.getByRole('menuitem', {name: 'Documents and Media'}).click();
	await page.getByTestId('headerOptions').getByLabel('Options').click();
	await page.getByRole('menuitem', {name: 'Export / Import'}).click();

	const exportImportFrame = page.frameLocator(
		'iframe[title="Export \\/ Import"]'
	);

	await exportImportFrame.getByRole('link', {name: 'Import'}).click();

	const fileChooserPromise = page.waitForEvent('filechooser');

	await exportImportFrame.getByRole('button', {name: 'Select File'}).click();

	const fileChooser = await fileChooserPromise;

	await fileChooser.setFiles(
		await zipFolder(
			path.join(__dirname, '/dependencies/folder.portlet.lar')
		)
	);

	await exportImportFrame.getByRole('button', {name: 'Continue'}).click();
	await exportImportFrame.getByRole('button', {name: 'Import'}).click();

	await page.getByLabel('close', {exact: true}).click();
	await page
		.locator(
			'[id="_com_liferay_document_library_web_portlet_DLAdminPortlet_entries_1"]'
		)
		.getByLabel('More actions')
		.click();
	await page.getByRole('menuitem', {name: 'Edit'}).click();

	expect(
		await page
			.getByTitle('Workflow Definition')
			.evaluate(
				(select: HTMLSelectElement) =>
					select.options[select.selectedIndex].value
			)
	).toBe('Single Approver@1');
});
