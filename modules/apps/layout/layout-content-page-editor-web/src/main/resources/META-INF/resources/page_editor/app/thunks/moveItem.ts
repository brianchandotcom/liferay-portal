/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State} from '../../types/State';
import moveItemAction from '../actions/moveItem';
import updateNetwork from '../actions/updateNetwork';
import LayoutService from '../services/LayoutService';

type Props = {
	itemIds: string[];
	parentItemIds: string[];
	positions: number[];
};

export default function moveItem({itemIds, parentItemIds, positions}: Props) {
	return (
		dispatch: (
			action: ReturnType<typeof updateNetwork | typeof moveItemAction>
		) => void,
		getState: () => State
	) => {
		return LayoutService.moveItem({
			itemIds,
			onNetworkStatus: dispatch,
			parentItemIds,
			positions,
			segmentsExperienceId: getState().segmentsExperienceId,
		}).then((layoutData) => {
			dispatch(moveItemAction({itemIds, layoutData}));
		});
	};
}
