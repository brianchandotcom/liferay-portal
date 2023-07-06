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

import {VIEWPORT_SIZES, ViewportSize} from '../config/constants/viewportSizes';

import type {PermissionsState} from '../reducers/permissionsReducer';

export default function selectAvailablePanels(panels: string[][]) {
	return function ({
		permissions,
		selectedViewportSize,
	}: {
		permissions: PermissionsState;
		selectedViewportSize: ViewportSize;
	}) {
		const availablePanels = ['browser', 'comments', 'page_content'];

		if (
			permissions.LOCKED_SEGMENTS_EXPERIMENT ||
			(!permissions.UPDATE &&
				!permissions.UPDATE_LAYOUT_LIMITED &&
				!permissions.UPDATE_LAYOUT_BASIC)
		) {
			return panels
				.map((group) =>
					group.filter((panelId) => availablePanels.includes(panelId))
				)
				.filter((group) => group.length);
		}
		else if (selectedViewportSize !== VIEWPORT_SIZES.desktop) {
			return panels
				.map((group) =>
					group.filter((panelId) => availablePanels.includes(panelId))
				)
				.filter((group) => group.length);
		}

		return panels;
	};
}
