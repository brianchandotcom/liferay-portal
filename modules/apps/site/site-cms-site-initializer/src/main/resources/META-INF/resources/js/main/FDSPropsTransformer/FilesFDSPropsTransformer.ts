/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import createAssetAction from './actions/createAssetAction';
import createFolderAction from './actions/createFolderAction';
import addOnClickToCreationMenuItems from './utils/addOnClickToCreationMenuItems';

const ACTIONS = {
	createAsset: createAssetAction,
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
			primaryItems: addOnClickToCreationMenuItems(
				creationMenu.primaryItems,
				ACTIONS
			),
		},
	};
}
