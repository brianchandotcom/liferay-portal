/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function updateIn(
	objectOrArray,
	keyPathOrKey,
	updater,
	defaultValue = undefined
) {
	const keyPath =
		typeof keyPathOrKey === 'string' ? [keyPathOrKey] : keyPathOrKey;

	const [nextKey] = keyPath;

	let nextObjectOrArray = objectOrArray;

	if (keyPath.length > 1) {
		nextObjectOrArray = Array.isArray(nextObjectOrArray)
			? [...nextObjectOrArray]
			: {...nextObjectOrArray};

		nextObjectOrArray[nextKey] = updateIn(
			nextObjectOrArray[nextKey] || {},
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

		const updatedNextValue = updater(nextValue);

		if (updatedNextValue !== nextObjectOrArray[nextKey]) {
			nextObjectOrArray = Array.isArray(nextObjectOrArray)
				? [...nextObjectOrArray]
				: {...nextObjectOrArray};

			nextObjectOrArray[nextKey] = updatedNextValue;
		}
	}

	return nextObjectOrArray;
}
