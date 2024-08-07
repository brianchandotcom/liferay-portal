/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {VIEWPORT_SIZES, ViewportSize} from '../config/constants/viewportSizes';
import updateItemConfig from '../thunks/updateItemConfig';

import type {LayoutDataItem} from '../../types/layout_data/LayoutData';

interface Params {
	dispatch: (action: ReturnType<typeof updateItemConfig>) => void;
	itemIds: string[];
	selectedViewportSize: ViewportSize;
	styleName: 'display';
	styleValue: 'block' | 'none';
}

export default function updateItemStyle({
	dispatch,
	itemIds,
	selectedViewportSize,
	styleName,
	styleValue,
}: Params) {
	let itemConfig = {
		styles: {
			[styleName]: styleValue,
		},
	} as LayoutDataItem['config'];

	if (selectedViewportSize !== VIEWPORT_SIZES.desktop) {
		itemConfig = {
			[selectedViewportSize]: {
				styles: {
					[styleName]: styleValue,
				},
			},
		} as LayoutDataItem['config'];
	}

	dispatch(
		updateItemConfig({
			itemConfig,
			itemIds,
		})
	);
}
