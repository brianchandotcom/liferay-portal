/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ICreationActionItem} from '../../management_bar/components/CreationMenu';

const filterCreationActions = (
	actions: Array<ICreationActionItem>,
	itemData: any
): Array<ICreationActionItem> | null => {
	return actions
		? actions.reduce(
				(
					actions: Array<ICreationActionItem>,
					action: ICreationActionItem
				) => {
					if (action.data?.permissionKey) {
						if (itemData[action.data.permissionKey]) {
							return [...actions, action];
						}

						return actions;
					}

					return [...actions, action];
				},
				[]
		  )
		: null;
};

export default filterCreationActions;
