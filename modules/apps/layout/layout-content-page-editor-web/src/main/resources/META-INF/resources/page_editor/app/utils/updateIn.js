/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

export const updateIn = (
	objectOrArray,
	keyPathOrKey,
	updater,
	defaultValue = undefined
) => {
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
};
