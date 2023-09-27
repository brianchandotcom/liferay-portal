/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function createReducer(reducers, config) {
	return (state, action) =>
		reducers.reduce((prevState, reducer) => {
			const nextProperties = reducer(prevState, action, config);

			if (prevState === nextProperties) {
				return nextProperties;
			}

			return {
				...prevState,
				...nextProperties,
			};
		}, state);
}
