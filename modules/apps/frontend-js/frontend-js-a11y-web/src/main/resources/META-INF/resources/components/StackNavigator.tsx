/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

type StackNavigatorProps<T> = {
	activePage: number;
	children: Array<React.ReactElement>;
	onActiveChange: (index: number) => void;
	onParamsChange: (payload: T) => void;
	params: T | undefined;
};

export function StackNavigator<T>({
	activePage,
	children,
	onActiveChange,
	onParamsChange,
	params,
}: StackNavigatorProps<T>) {
	const childrenArray = React.Children.toArray(children);

	const child = childrenArray[activePage];

	return React.cloneElement(child, {
		...child.props,
		index: activePage,
		next: (payload: T) => {
			const index = activePage + 1;

			if (React.Children.count(children) > index) {
				onActiveChange(index);
				onParamsChange(payload);
			}
		},
		params,
		previous: (payload: T) => {
			const index = activePage - 1;

			if (index >= 0) {
				onActiveChange(index);
				onParamsChange(payload);
			}
		},
	});
}
