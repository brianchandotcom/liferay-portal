/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateItemLocalConfig from '../../actions/updateItemLocalConfig';

function undoAction({action}) {
	const {config, itemId} = action;

	return (dispatch) =>
		dispatch(
			updateItemLocalConfig({
				itemConfig: config,
				itemId,
				overridePreviousConfig: true,
			})
		);
}

function getDerivedStateForUndo({action, state}) {
	const {itemId} = action;
	const {layoutData} = state;

	const item = layoutData.items[itemId];

	return {
		config: item.config,
		itemId,
	};
}

export {undoAction, getDerivedStateForUndo};
