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

import {updateColSize} from '../../actions/index';
import LayoutService from '../../services/LayoutService';

function undoAction({action, store}) {
	const {columnConfigs, rowItemId} = action;
	const {layoutData} = store;

	const newItems = columnConfigs.reduce((acc, columnConfig) => {
		const column = layoutData.items[columnConfig.itemId];

		acc[column.itemId] = {...column, config: columnConfig.config};

		return acc;
	}, {});

	const nextLayoutData = {
		...layoutData,
		items: {
			...layoutData.items,
			...newItems,
		},
	};

	return (dispatch) => {
		return LayoutService.updateLayoutData({
			layoutData: nextLayoutData,
			onNetworkStatus: dispatch,
			segmentsExperienceId: store.segmentsExperienceId,
		}).then(() => {
			dispatch(updateColSize({layoutData: nextLayoutData, rowItemId}));
		});
	};
}

function getDerivedStateForUndo({action, state}) {
	const {rowItemId} = action;
	const {layoutData} = state;

	const row = layoutData.items[rowItemId];

	const columnConfigs = row.children.map((columnId) => {
		const column = layoutData.items[columnId];

		return {config: column.config, itemId: columnId};
	});

	return {
		columnConfigs,
		rowItemId,
	};
}

export {undoAction, getDerivedStateForUndo};
