/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function updateIn<T extends Record<string, any> | any[]>(
	objectOrArray: T,
	keyPathOrKey: Array<string | number>,
	updater: (value: unknown) => unknown,
	defaultValue: unknown = undefined
): T {
	const keyPath =
		typeof keyPathOrKey === 'string' ? [keyPathOrKey] : keyPathOrKey;

	const [nextKey] = keyPath as Array<keyof T>;

	let nextObjectOrArray = objectOrArray;

	if (keyPath.length > 1) {
		nextObjectOrArray = Array.isArray(nextObjectOrArray)
			? ([...nextObjectOrArray] as T)
			: ({...nextObjectOrArray} as T);

		nextObjectOrArray[nextKey] = updateIn<T[keyof T]>(
			(nextObjectOrArray[nextKey] || {}) as T[keyof T],
			keyPath.slice(1),
			updater,
			defaultValue
		);
	}
	else {
		const nextValue =
			typeof nextObjectOrArray[nextKey] === 'undefined'
				? defaultValue
				: nextObjectOrArray[nextKey];

		const updatedNextValue = updater(nextValue) as T[keyof T];

		if (updatedNextValue !== nextObjectOrArray[nextKey]) {
			nextObjectOrArray = Array.isArray(nextObjectOrArray)
				? ([...nextObjectOrArray] as T)
				: ({...nextObjectOrArray} as T);

			nextObjectOrArray[nextKey] = updatedNextValue;
		}
	}

	return nextObjectOrArray;
}
