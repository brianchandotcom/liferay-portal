/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export default async function dragAndDropElement({
	dragTarget,
	dropTarget,
	page,
}: {
	dragTarget: Locator;
	dropTarget: Locator;
	page: Page;
}) {
	await dragTarget.hover();

	await page.mouse.down();

	await dropTarget.hover();

	const boundingClientRect = await dropTarget.evaluate((element) =>
		element.getBoundingClientRect()
	);

	await dropTarget.hover({
		position: {
			x: boundingClientRect.width / 2,
			y: boundingClientRect.height / 2,
		},
	});

	await page.mouse.up();
}
