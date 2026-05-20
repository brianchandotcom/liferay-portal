/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IItemsActions} from './types';

type ItemActionsMap = Record<string, IItemsActions>;

export default function transformDataSetItems<
	T extends {
		embedded?: {actions?: ItemActionsMap} & Record<string, unknown>;
	},
>(items: T[]): Array<T & {actions?: ItemActionsMap}> {
	return items.map((item) => {
		if (item.embedded && item.embedded.actions) {
			return {
				...item,
				actions: item.embedded.actions,
			};
		}

		return {...item};
	});
}
