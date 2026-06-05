/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';
import fillAndClickOutside from '../../../../utils/fillAndClickOutside';
import {waitForAlert} from '../../../../utils/waitForAlert';

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
	const modalFooter = page.locator('.modal-footer');

	await modalFooter.getByText(buttonName).click();
}

export async function createABTest({
	goal = 'Bounce Rate',
	name,
	page,
}: {
	goal?: 'Bounce Rate' | 'Click';
	name: string;
	page: Page;
}) {
	const modal = page.locator('.modal-content');

	await clickAndExpectToBeVisible({
		target: modal.getByLabel('Test Name'),
		trigger: page.getByText('Create Test'),
	});

	await fillAndClickOutside(page, modal.getByLabel('Test Name'), name);

	if (goal !== 'Bounce Rate') {
		await modal.getByLabel('Select Goal').selectOption({label: goal});
	}

	await modal.getByText('Save').click();

	if (goal === 'Click') {
		await expect(page.locator('#clickableElement')).toBeVisible();
	}
	else {
		await expect(
			page.locator('[data-testid="create-variant"]')
		).toBeVisible();
	}

	await waitForAlert(page);
}

export async function createVariant({name, page}: {name: string; page: Page}) {
	const createVariantButton = page.locator('[data-testid="create-variant"]');

	await expect(createVariantButton).toBeVisible();

	await createVariantButton.click();

	const variantModal = page.locator('.modal-content');

	await variantModal.locator('input').fill(name);

	await variantModal.getByText('Save').click();

	await waitForAlert(page);

	await expect(page.locator(`[data-title="${name}"]`)).toBeVisible();
}

export async function getClickElementId({page}: {page: any}) {
	const title = await page
		.locator(
			'.lfr-segments-experiment-click-goal-target-popover .text-truncate'
		)
		.getAttribute('title');

	return (title ?? '').replace(/^#/, '');
}

export async function selectClickElement({
	index = 0,
	page,
}: {
	index?: number;
	page: Page;
}) {
	page.getByText('Change Clickable Element');

	await clickAndExpectToBeVisible({
		autoClick: true,
		target: page
			.locator('.lfr-segments-experiment-click-goal-target-overlay')
			.nth(index),
		trigger: page.getByText('Select Clickable Element'),
	});

	const title = getClickElementId({page});

	await page
		.locator('.lfr-segments-experiment-click-goal-target-popover')
		.getByRole('button')
		.click();

	await waitForAlert(page);

	return title;
}

export async function runTest(page: Page) {
	await page.getByText('Review and Run Test').click();

	await page
		.locator('.modal-item-last')
		.getByText('Run', {exact: true})
		.click();

	await page.locator('.modal-item-last').getByText('OK').click();
}

export async function terminateTest(page: Page) {
	await clickAndExpectToBeVisible({
		autoClick: true,
		target: page
			.locator('.modal-item-last')
			.getByText('Terminate', {exact: true}),
		trigger: page.getByText('Terminate Test'),
	});

	await waitForAlert(page);
}

export async function openABTesSidebar(page: Page) {
	const sidebar = page.locator('#segmentsExperimentSidebar');

	if (!(await sidebar.isVisible())) {
		await clickAndExpectToBeVisible({
			target: page.locator('#segmentsExperimentSidebar'),
			trigger: page.getByRole('button', {name: 'A/B Test'}),
		});
	}
}
