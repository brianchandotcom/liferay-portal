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

import {
	getFragmentRowIndex,
	getRowFragmentEntryLinkIds
} from './FragmentsEditorGetUtils.es';

/**
 * Tells if a fragmentEntryLink is referenced in any (but the current one)
 * LayoutData inside LayoutDataList
 * @param {Array<{ segmentsExperienceId: string, layoutData: {structure: Array} }>} LayoutDataList
 * @param {string} fragmentEntryLinkId
 * @param {string} [skipSegmentsExperienceId] - allows to skip searching in layoutData by segmentsExperienceId
 * @returns {boolean}
 */
function containsFragmentEntryLinkId(
	LayoutDataList,
	fragmentEntryLinkId,
	skipSegmentsExperienceId
) {
	return LayoutDataList.filter(function _avoidCurrentExperienceLayoutDataItem(
		LayoutDataItem
	) {
		return LayoutDataItem.segmentsExperienceId !== skipSegmentsExperienceId;
	}).some(function _getFragmentRowIndexWrapper(LayoutDataItem) {
		const index = getFragmentRowIndex(
			LayoutDataItem.layoutData.structure,
			fragmentEntryLinkId
		);
		return index !== -1;
	});
}

/**
 * Utility to get a layoutData object
 *
 * @returns {object}
 */
function getEmptyLayoutData() {
	return {
		nextColumnId: 0,
		nextRowId: 0,
		structure: []
	};
}

/**
 * Utility to get a fragment entry links ids from layout data
 *
 * @param layoutData {LayoutDataShape}
 * @returns {Array}
 */
function getLayoutDataFragmentEntryLinkIds(layoutData) {
	let fragmentEntryLinkIds = [];

	layoutData.structure.forEach(row => {
		fragmentEntryLinkIds = fragmentEntryLinkIds.concat(
			getRowFragmentEntryLinkIds(row)
		);
	});

	return fragmentEntryLinkIds;
}

export {
	containsFragmentEntryLinkId,
	getEmptyLayoutData,
	getLayoutDataFragmentEntryLinkIds
};
