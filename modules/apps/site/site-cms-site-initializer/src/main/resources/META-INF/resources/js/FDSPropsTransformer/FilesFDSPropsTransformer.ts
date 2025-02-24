/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import createFolderAction from './actions/createFolderAction';

const ACTIONS: Record<string, (data: any) => void> = {
	createFolder: createFolderAction,
};

export default function FilesFDSPropsTransformer({
	creationMenu,
	...otherProps
}: {
	creationMenu: any;
	otherProps: any;
}) {
	return {
		...otherProps,
		creationMenu: {
			...creationMenu,
			primaryItems: creationMenu.primaryItems.map(
				(item: {data: {action: string}}) => {
					return {
						...item,
						onClick() {
							const action = item.data.action;

							if (action) {
								ACTIONS[action](item.data);
							}
						},
					};
				}
			),
		},
	};
}
