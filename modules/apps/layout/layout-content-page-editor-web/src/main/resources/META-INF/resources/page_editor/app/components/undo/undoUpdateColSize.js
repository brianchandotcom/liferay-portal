/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
