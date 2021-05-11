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

import {State} from '@liferay/frontend-js-state-web';

// Shared state (atoms and selectors);

export const userAtom = State.atom('clay-sample-atom', {
	name: Liferay.ThemeDisplay.getUserName(),
});

export const userSelector = State.selector('clay-sample-selector', (get) => {
	const user = get(userAtom);

	return `${user.name} (${user.name.length})`;
});

export const counterAtomReact = State.atom('sample-counter', 0);
