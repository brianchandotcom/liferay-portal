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

import {VIEWPORT_SIZES} from '../config/constants/viewportSizes';
import updateItemConfig from '../thunks/updateItemConfig';

export default function updateItemStyle({
	dispatch,
	itemId,
	segmentsExperienceId,
	selectedViewportSize,
	styleName,
	styleValue,
}) {
	let itemConfig = {
		styles: {
			[styleName]: styleValue,
		},
	};

	if (selectedViewportSize !== VIEWPORT_SIZES.desktop) {
		itemConfig = {
			[selectedViewportSize]: {
				styles: {
					[styleName]: styleValue,
				},
			},
		};
	}

	dispatch(
		updateItemConfig({
			itemConfig,
			itemId,
			segmentsExperienceId,
		})
	);
}
