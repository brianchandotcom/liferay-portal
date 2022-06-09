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

import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../config/constants/freemarkerFragmentEntryProcessor';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {getResponsiveConfig} from './getResponsiveConfig';
import {isFormRequiredField} from './isFormRequiredField';
import {isLayoutDataItemDeleted} from './isLayoutDataItemDeleted';

function getDescendantIds(layoutData, itemId) {
	const item = layoutData.items[itemId];

	const descendantIds = [...item.children];

	item.children.forEach((childId) => {
		if (!isLayoutDataItemDeleted(layoutData, childId)) {
			descendantIds.push(...getDescendantIds(layoutData, childId));
		}
	});

	return descendantIds;
}

function isItemHidden(layoutData, itemId, selectedViewportSize) {
	const item = layoutData?.items[itemId];

	if (!item) {
		return false;
	}

	const responsiveConfig = getResponsiveConfig(
		item.config,
		selectedViewportSize
	);

	return (
		responsiveConfig.styles.display === 'none' ||
		isItemHidden(layoutData, item.parentId, selectedViewportSize)
	);
}

export default function hasRequiredInputChild({
	checkHidden = false,
	formFields,
	fragmentEntryLinks,
	itemId,
	layoutData,
	selectedViewportSize = null,
}) {
	const descendantIds = getDescendantIds(layoutData, itemId);

	return descendantIds.some((descendantId) => {
		const item = layoutData.items[descendantId];

		if (item.type !== LAYOUT_DATA_ITEM_TYPES.fragment) {
			return false;
		}

		const {inputFieldId, inputRequired} =
			fragmentEntryLinks[item.config.fragmentEntryLinkId].editableValues[
				FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
			] || {};

		return (
			(!checkHidden ||
				isItemHidden(layoutData, descendantId, selectedViewportSize)) &&
			(inputRequired || isFormRequiredField(inputFieldId, formFields))
		);
	});
}
