/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dialog, Page} from '@playwright/test';

export function watchForDialog(page: Page): {
	assertNoDialog: () => void;
	dispose: () => void;
} {
	const dialogs: string[] = [];

	const listener = async (dialog: Dialog) => {
		dialogs.push(dialog.message());

		await dialog.dismiss();
	};

	page.on('dialog', listener);

	return {
		assertNoDialog: () => {
			if (dialogs.length) {
				throw new Error(
					`Expected no native dialog but got: ${dialogs.join(', ')}`
				);
			}
		},
		dispose: () => {
			page.off('dialog', listener);
		},
	};
}
