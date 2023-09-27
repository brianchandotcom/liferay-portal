/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {VIEWPORT_SIZES} from '../config/constants/viewportSizes';
import updateItemConfig from '../thunks/updateItemConfig';

export default function updateItemStyle({
	dispatch,
	itemId,
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
		})
	);
}
