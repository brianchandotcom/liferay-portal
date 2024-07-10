/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, expect} from '@playwright/test';

export async function selectAndExpectToHaveValue({
	optionLabel,
	optionValue,
	select,
}: {
	optionLabel?: string;
	optionValue?: string;
	select: Locator;
}) {
	await expect(async () => {
		const value = optionValue || (await getValue(select, optionLabel));

		await select.selectOption(value);

		await expect(select).toHaveValue(value, {timeout: 100});
	}).toPass();
}

async function getValue(select: Locator, label: string) {
	return await select.evaluate(
		(element: HTMLSelectElement, label: string) =>
			Array.from(element.options).find((opt) => opt.text === label).value,
		label
	);
}
