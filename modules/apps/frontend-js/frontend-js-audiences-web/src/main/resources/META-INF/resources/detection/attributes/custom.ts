/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CustomAttribute} from '../../index';

export async function getCustom<T>(url: string): Promise<T> {
	let symbol = 'default';

	const hashIndex = url.indexOf('#');

	if (hashIndex !== -1) {
		symbol = url.slice(hashIndex + 1);
		url = url.slice(0, hashIndex);
	}

	const module = await import(url);

	const customAttribute = module[symbol] as CustomAttribute<T>;

	if (typeof customAttribute !== 'function') {
		throw new Error(
			`Module '${url}' does not export any function named '${symbol}'`
		);
	}

	return customAttribute();
}
