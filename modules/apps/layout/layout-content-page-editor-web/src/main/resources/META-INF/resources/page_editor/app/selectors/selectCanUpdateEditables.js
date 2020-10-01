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

import {EDITABLE_TYPES} from '../config/constants/editableTypes';
import {VIEWPORT_SIZES} from '../config/constants/viewportSizes';

/**
 * @param {{ permissions: import("../../types/ActionKeys").ActionKeysMap, selectedViewportSize: string }} state
 */
export default function selectCanUpdateEditables(
	{permissions, selectedViewportSize},
	activeItem
) {
	return (
		(!permissions.LOCKED_SEGMENTS_EXPERIMENT &&
			(permissions.UPDATE || permissions.UPDATE_LAYOUT_CONTENT) &&
			selectedViewportSize === VIEWPORT_SIZES.desktop) ||
		(activeItem &&
			activeItem.type === EDITABLE_TYPES.image &&
			selectedViewportSize !== VIEWPORT_SIZES.desktop)
	);
}
