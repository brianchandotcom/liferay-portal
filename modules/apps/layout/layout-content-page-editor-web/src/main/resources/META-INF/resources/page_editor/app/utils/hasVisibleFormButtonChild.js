/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../config/constants/freemarkerFragmentEntryProcessor';
import {isItemHidden} from './isItemHidden';

export function hasVisibleFormButtonChild({
	fragmentEntryLinks,
	itemId,
	layoutData,
	type,
	viewportSize,
}) {
	const item = layoutData.items[itemId];

	for (const childId of item.children) {
		const child = layoutData.items[childId];

		if (
			!child ||
			isItemHidden(layoutData, childId, viewportSize, {recursive: true})
		) {
			continue;
		}

		if ('fragmentEntryLinkId' in child.config) {
			const fragment =
				fragmentEntryLinks[child.config.fragmentEntryLinkId];

			if (
				fragment.editableValues[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]
					?.type === type
			) {
				return true;
			}
		}
		else {
			const hasVisibleFormButton = hasVisibleFormButtonChild({
				fragmentEntryLinks,
				itemId: childId,
				layoutData,
				type,
				viewportSize,
			});

			if (hasVisibleFormButton) {
				return true;
			}
		}
	}

	return false;
}
