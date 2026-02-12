/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EItemActionsType, IItemsActions} from '../types';

const findAction = (
	actions: IItemsActions[],
	actionId: string
): IItemsActions | undefined => {
	for (const action of actions) {
		if (action.data?.id === actionId) {
			return action;
		}

		if (action.type === EItemActionsType.GROUP && action.items) {
			const foundAction = findAction(action.items, actionId);

			if (foundAction) {
				return foundAction;
			}
		}
	}
};

export default findAction;
