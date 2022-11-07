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

import updateRowColumns from '../../actions/updateRowColumns';
import LayoutService from '../../services/LayoutService';
import {setIn} from '../../utils/setIn';

function undoAction({action}) {
	const {deletedColumnIds, layoutDataItem, previousNumberOfColumns} = action;

	return async (dispatch, getState) => {
		const {segmentsExperienceId} = getState();

		if (deletedColumnIds.length) {

			// LPS-164654 We need to restore all deleted columns in reversed orders
			// so the backend can recover each column children correctly.

			await LayoutService.unmarkItemsForDeletion({
				itemIds: deletedColumnIds.reverse(),
				onNetworkStatus: dispatch,
				segmentsExperienceId,
			});

			const {
				layoutData,
				pageContents,
			} = await LayoutService.updateItemConfig({
				itemConfig: setIn(
					layoutDataItem.config,
					'numberOfColumns',
					previousNumberOfColumns
				),
				itemId: layoutDataItem.itemId,
				onNetworkStatus: dispatch,
				segmentsExperienceId,
			});

			dispatch(
				updateRowColumns({
					itemId: layoutDataItem.itemId,
					layoutData,
					numberOfColumns: previousNumberOfColumns,
					pageContents,
				})
			);
		}
		else {
			const {
				layoutData,
				pageContents,
			} = await LayoutService.updateRowColumns({
				itemId: layoutDataItem.itemId,
				numberOfColumns: previousNumberOfColumns,
				onNetworkStatus: dispatch,
				segmentsExperienceId,
			});

			dispatch(
				updateRowColumns({
					itemId: layoutDataItem.itemId,
					layoutData,
					numberOfColumns: previousNumberOfColumns,
					pageContents,
				})
			);
		}
	};
}

function getDerivedStateForUndo({action, state}) {
	const {itemId} = action;
	const {layoutData} = state;

	const layoutDataItem = layoutData.items[itemId];

	const nextNumberOfColumns = action.numberOfColumns;
	const previousNumberOfColumns = layoutDataItem.config.numberOfColumns;

	const deletedColumnIds = layoutDataItem.children.slice(
		nextNumberOfColumns,
		previousNumberOfColumns
	);

	return {
		deletedColumnIds,
		layoutDataItem,
		previousNumberOfColumns,
	};
}

export {undoAction, getDerivedStateForUndo};
