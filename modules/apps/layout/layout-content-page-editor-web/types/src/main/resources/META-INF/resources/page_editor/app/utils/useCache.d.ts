/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CacheKey} from './cache';
export declare type Fetcher<T> = () => Promise<
	T & {
		error?: string;
	}
>;
export default function useCache<T>({
	fetcher,
	key,
}: {
	fetcher: Fetcher<T>;
	key: CacheKey | [CacheKey, ...string[]];
}): T | null;
