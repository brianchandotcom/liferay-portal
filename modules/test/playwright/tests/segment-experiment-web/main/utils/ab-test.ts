/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

export async function assertTerminatedABTest(page: Page) {
	await expect(page.getByText('Terminated')).toBeVisible();

	await expect(
		page.getByText(
			'The test has not gathered sufficient data to confidently determine a winner.'
		)
	).toBeVisible();

	await expect(page.getByText('Create New Test')).toBeVisible();
}

export async function checkEmptyStateOnDXPSide(page: Page) {
	await expect(page.getByText('Create Test')).toBeVisible();
}

export async function clickOnABTestModalButton({
	buttonName,
	page,
}: {
	buttonName: string;
	page: Page;
}) {
	const modalFooter = await page.locator('.modal-footer');

	await modalFooter.getByText(buttonName).click();
}

export async function createABTest({name, page}: {name: string; page: Page}) {
	await page.getByText('Create Test').click();

	const modal = await page.locator('.modal-content');

	await modal.locator('input').fill(name);

	await modal.getByText('Save').click();

	await expect(page.locator('[data-testid="create-variant"]')).toBeVisible();
}

export async function createVariant({name, page}: {name: string; page: Page}) {
	const createVariantButton = await page.locator(
		'[data-testid="create-variant"]'
	);

	await expect(createVariantButton).toBeVisible();

	await createVariantButton.click();

	const variantModal = await page.locator('.modal-content');

	await variantModal.locator('input').fill(name);

	await variantModal.getByText('Save').click();

	await expect(page.locator(`[data-title="${name}"]`)).toBeVisible();
}

export async function openABTesSidebar(page: Page) {
	const sidebar = await page.locator('#segmentsExperimentSidebar');

	if (!(await sidebar.isVisible())) {
		await page
			.locator('button[data-qa-id=segmentsExperimentPanel]')
			.click();
	}

	await expect(page.locator('#segmentsExperimentSidebar')).toBeVisible();
}
