/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';
import {readFile} from 'fs/promises';
import path from 'path';

import {clickAndExpectToBeVisible} from './clickAndExpectToBeVisible';
import {getTempDir} from './temp';

export async function getExportedConfiguration(page: Page) {
	const downloadPromise = page.waitForEvent('download');

	await clickAndExpectToBeVisible({
		autoClick: true,
		target: page.getByRole('menuitem', {name: 'Export'}),
		trigger: page.getByRole('button', {name: 'Actions'}),
	});

	const download = await downloadPromise;

	const filePath = path.join(getTempDir(), download.suggestedFilename());

	await download.saveAs(filePath);

	return await readFile(filePath, 'utf-8');
}
