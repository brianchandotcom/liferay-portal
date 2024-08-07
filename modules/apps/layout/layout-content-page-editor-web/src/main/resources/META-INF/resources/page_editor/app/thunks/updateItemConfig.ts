/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State} from '../../types/State';
import updateItemConfigAction from '../actions/updateItemConfig';
import updateNetwork from '../actions/updateNetwork';
import LayoutService from '../services/LayoutService';
import {clearPageContents} from '../utils/usePageContents';

import type {LayoutDataItem} from '../../types/layout_data/LayoutData';

export default function updateItemConfig({
	itemConfig,
	itemIds,
}: {
	itemConfig: LayoutDataItem['config'];
	itemIds: string[];
}) {
	return (
		dispatch: (
			action: ReturnType<
				typeof updateNetwork | typeof updateItemConfigAction
			>
		) => void,
		getState: () => State
	) => {
		const {segmentsExperienceId} = getState();

		return LayoutService.updateItemConfig({
			itemConfig,
			itemIds,
			onNetworkStatus: dispatch,
			segmentsExperienceId,
		}).then(({layoutData}) => {
			dispatch(
				updateItemConfigAction({
					itemIds,
					layoutData,
				})
			);
			clearPageContents();
		});
	};
}
