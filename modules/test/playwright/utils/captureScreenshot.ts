/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';
import * as path from 'path';

import {getTempDir} from './temp';

type PropsWithLocator = {
	locator: Locator;
	page?: never;
};

type PropsWithPage = {
	locator?: never;
	page: Page;
};

type Props = {
	mask?: Locator[];
	name: string;
} & (PropsWithLocator | PropsWithPage);

export async function captureScreenshot({locator, mask, name, page}: Props) {
	if (locator) {
		return await locator.screenshot({
			mask,
			path: path.join(getTempDir(), `${name}.png`),
		});
	}
	else {
		return await page.screenshot({
			fullPage: true,
			mask,
			path: path.join(getTempDir(), `${name}.png`),
		});
	}
}
