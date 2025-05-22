/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IInternalRenderer} from '@liferay/frontend-data-set-web';

import createSpaceAction from './actions/createSpaceAction';
import SpaceRenderer from './cell_renderers/SpaceRenderer';
import addOnClickToCreationMenuItems from './utils/addOnClickToCreationMenuItems';

const ACTIONS = {
	createSpace: createSpaceAction,
};

export default function AllSpacesFDSPropsTransformer({
	additionalProps,
	creationMenu,
	itemsActions = [],
	...otherProps
}: {
	additionalProps: any;
	creationMenu: any;
	itemsActions?: any[];
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
		customRenderers: {
			tableCell: [
				{
					component: SpaceRenderer,
					name: 'spaceTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
			],
		},
		itemsActions: itemsActions.map((action) => {
			const pinnedAssetLibraryIds = additionalProps.pinnedAssetLibraryIds;

			if (action?.data?.id === 'pin') {
				return {
					...action,
					isVisible: (item: any) =>
						!pinnedAssetLibraryIds?.includes(item.id.toString()),
				};
			}

			if (action?.data?.id === 'unpin') {
				return {
					...action,
					isVisible: (item: any) =>
						!!pinnedAssetLibraryIds?.includes(item.id.toString()),
				};
			}

			return action;
		}),
		onActionDropdownItemClick: ({
			action,
		}: {
			action: {data: {id: string}};
		}) => {
			if (action.data.id === 'pin' || action.data.id === 'unpin') {
				window.location.reload();
			}
		},
	};
}
