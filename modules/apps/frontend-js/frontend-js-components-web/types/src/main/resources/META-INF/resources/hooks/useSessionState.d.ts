/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {TYPE_VALUES as CookieType} from 'frontend-js-web';
export default function useSessionState<T>(
	key: string,
	defaultValue?: T | undefined,
	type?: CookieType
): readonly [
	T | undefined,
	import('react').Dispatch<import('react').SetStateAction<T | undefined>>
];
