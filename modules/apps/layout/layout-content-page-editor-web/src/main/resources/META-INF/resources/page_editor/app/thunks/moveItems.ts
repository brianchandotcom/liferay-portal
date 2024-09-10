/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State} from '../../types/State';
import moveItemsAction from '../actions/moveItems';
import updateNetwork from '../actions/updateNetwork';
import LayoutService from '../services/LayoutService';
import sortItemIds from '../utils/sortItemIds';
import filterSelectedItems from './filterSelectedItems';

type Props = {
	itemIds: string[];
	parentItemIds: string[];
	positions: number[];
};

export default function moveItems({itemIds, parentItemIds, positions}: Props) {
	return (
		dispatch: (
			action: ReturnType<typeof updateNetwork | typeof moveItemsAction>
		) => void,
		getState: () => State
	) => {
		const {layoutData, segmentsExperienceId} = getState();

		const sortedItemIds = sortItemIds(
			filterSelectedItems(itemIds, layoutData.items),
			layoutData
		);

		return LayoutService.moveItems({
			itemIds: sortedItemIds,
			onNetworkStatus: dispatch,
			parentItemIds,
			positions,
			segmentsExperienceId,
		}).then((layoutData) => {
			dispatch(moveItemsAction({itemIds: sortedItemIds, layoutData}));
		});
	};
}
