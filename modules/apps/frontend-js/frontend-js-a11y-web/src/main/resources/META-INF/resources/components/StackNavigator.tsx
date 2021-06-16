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

import React, {useState} from 'react';

type StackNavigatorProps = {
	children: Array<React.ReactElement>;
};

export function StackNavigator({children}: StackNavigatorProps) {
	const [activePageIndex, setActivePageIndex] = useState(0);

	const [navigationState, setNavigationState] = useState<unknown>();

	const childrenArray = React.Children.toArray(children);

	const child = childrenArray[activePageIndex];

	return React.cloneElement(child, {
		...child.props,
		index: activePageIndex,
		navigationState,
		next: (payload: unknown) => {
			const index = activePageIndex + 1;

			if (React.Children.count(children) > index) {
				setActivePageIndex(index);
				setNavigationState(payload);
			}
		},
		previous: (payload?: unknown) => {
			const index = activePageIndex - 1;

			if (index > 0) {
				setActivePageIndex(index);

				if (payload) {
					setNavigationState(payload);
				}
			}
		},
	});
}
