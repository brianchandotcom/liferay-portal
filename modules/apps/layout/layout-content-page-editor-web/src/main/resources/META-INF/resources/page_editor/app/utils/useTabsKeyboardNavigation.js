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

import {useRef} from 'react';

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
export default function useTabsKeyboardNavigation({
	numberOfPanels,
	selectPanel,
}) {
	const tabsRef = useRef({});

	const onTabItemKeyDown = (event, index) => {
		let nextIndex = null;

		if (event.key === 'ArrowRight') {
			nextIndex = index + 1;

			if (nextIndex >= numberOfPanels) {
				nextIndex = 0;
			}
		}
		else if (event.key === 'ArrowLeft') {
			nextIndex = index - 1;

			if (nextIndex < 0) {
				nextIndex = numberOfPanels - 1;
			}
		}

		if (nextIndex !== null) {
			selectPanel(nextIndex);

			tabsRef.current[nextIndex]?.focus();
		}
	};

	return {
		onTabItemKeyDown,
		tabItemRef: (ref, index) => {
			tabsRef.current[index] = ref;
		},
	};
}
