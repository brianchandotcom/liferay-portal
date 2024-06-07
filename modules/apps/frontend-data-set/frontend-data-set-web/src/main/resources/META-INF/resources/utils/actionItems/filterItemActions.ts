/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IItemsActions} from '../..';

const filterItemActions = (
	actions: Array<IItemsActions>,
	itemData: any
): Array<IItemsActions> => {
	return actions
		? actions.reduce(
				(actions: Array<IItemsActions>, action: IItemsActions) => {
					if (action.data?.permissionKey) {
						const itemDataActionKeys = Object.keys(
							itemData.actions
						);

						if (
							itemData.actions &&
							itemDataActionKeys.some(
								(itemAction) =>
									itemAction.toLowerCase() ===
									action.data?.permissionKey?.toLowerCase()
							)
						) {
							if (action.target === 'headless') {
								const matchedPermissionKey =
									itemDataActionKeys.filter(
										(itemAction) =>
											itemAction.toLowerCase() ===
											action.data?.permissionKey?.toLowerCase()
									);

								return [
									...actions,
									{
										...action,
										...itemData.actions[
											matchedPermissionKey[0]
										],
									},
								];
							}
							else {
								return [...actions, action];
							}
						}

						return actions;
					}

					return [...actions, action];
				},
				[]
			)
		: [];
};

export default filterItemActions;
