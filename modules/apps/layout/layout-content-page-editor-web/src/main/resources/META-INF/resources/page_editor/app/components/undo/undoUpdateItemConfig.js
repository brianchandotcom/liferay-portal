/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateItemConfig from '../../actions/updateItemConfig';
import LayoutService from '../../services/LayoutService';
import {setIn} from '../../utils/setIn';

function undoAction({action, store}) {
	const {config, itemIds} = action;
	const {layoutData} = store;

	let nextLayoutData = layoutData;

	itemIds.forEach((itemId) => {
		nextLayoutData = setIn(
			nextLayoutData,
			['items', itemId, 'config'],
			config[itemId]
		);
	});

	return (dispatch) => {
		return LayoutService.updateLayoutData({
			layoutData: nextLayoutData,
			onNetworkStatus: dispatch,
			segmentsExperienceId: store.segmentsExperienceId,
		}).then(() => {
			dispatch(
				updateItemConfig({
					itemIds,
					layoutData: nextLayoutData,
				})
			);
		});
	};
}

function getDerivedStateForUndo({action, state}) {
	const {itemIds} = action;
	const {layoutData} = state;
	let config = {};

	for (const itemId of itemIds) {
		const item = layoutData.items[itemId];

		config = {...config, [itemId]: item.config};
	}

	return {
		config,
		itemIds,
	};
}

export {undoAction, getDerivedStateForUndo};
