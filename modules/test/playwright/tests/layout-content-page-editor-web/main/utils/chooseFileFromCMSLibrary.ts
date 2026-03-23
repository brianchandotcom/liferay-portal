/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeHidden} from '../../../../utils/clickAndExpectToBeHidden';
import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';

export default async function chooseFileFromCMSLibrary({
	fileName,
	page,
	trigger,
}: {
	fileName?: string;
	filePath?: string;
	page: Page;
	trigger: Locator;
}) {
	const dialog = page.getByRole('dialog', {name: 'Select Files'});

	await clickAndExpectToBeVisible({
		target: dialog.locator('.visualization-mode-cards'),
		trigger,
	});

	await expect(() => {
		dialog.locator('.card', {hasText: fileName}).click();

		expect(dialog.getByRole('button', {name: 'Select'})).toBeEnabled();
	}).toPass();

	await clickAndExpectToBeHidden({
		target: dialog.locator('.card', {hasText: fileName}),
		trigger: dialog.getByRole('button', {name: 'Select'}),
	});
}
