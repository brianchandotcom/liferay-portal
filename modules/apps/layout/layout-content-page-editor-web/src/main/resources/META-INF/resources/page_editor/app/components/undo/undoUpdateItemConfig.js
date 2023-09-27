/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateItemConfig from '../../actions/updateItemConfig';
import LayoutService from '../../services/LayoutService';
import {setIn} from '../../utils/setIn';

function undoAction({action, store}) {
	const {config, itemId, pageContents} = action;
	const {layoutData} = store;

	const nextLayoutData = setIn(
		layoutData,
		['items', itemId, 'config'],
		config
	);

	return (dispatch) => {
		return LayoutService.updateLayoutData({
			layoutData: nextLayoutData,
			onNetworkStatus: dispatch,
			segmentsExperienceId: store.segmentsExperienceId,
		}).then(() => {
			dispatch(
				updateItemConfig({
					itemId,
					layoutData: nextLayoutData,
					overridePreviousConfig: true,
					pageContents,
				})
			);
		});
	};
}

function getDerivedStateForUndo({action, state}) {
	const {itemId, pageContents} = action;
	const {layoutData} = state;

	const item = layoutData.items[itemId];

	return {
		config: item.config,
		itemId,
		pageContents,
	};
}

export {undoAction, getDerivedStateForUndo};
