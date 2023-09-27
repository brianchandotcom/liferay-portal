/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Wrapper for `useState` that does an `isMounted()` check behind the scenes
 * before triggering side-effects.
 */
export default function useStateSafe<T = unknown>(
	initialValue: T | (() => T)
): readonly [T, (newValue: T | ((previousValue: T) => T)) => void];
