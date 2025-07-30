/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IInternalRenderer} from '@liferay/frontend-data-set-web';

import AssetTypeInfoPanel from '../info_panel/AssetTypeInfoPanelContent';
import {EVENTS} from '../info_panel/util/constants';
import createAssetAction from './actions/createAssetAction';
import createFolderAction from './actions/createFolderAction';
import shareAction from './actions/shareAction';
import AuthorRenderer from './cell_renderers/AuthorRenderer';
import NameRenderer from './cell_renderers/NameRenderer';
import SimpleActionLinkRenderer from './cell_renderers/SimpleActionLinkRenderer';
import SpaceRenderer from './cell_renderers/SpaceRenderer';
import TypeRenderer from './cell_renderers/TypeRenderer';
import addOnClickToCreationMenuItems from './utils/addOnClickToCreationMenuItems';

const ACTIONS = {
	createAsset: createAssetAction,
	createFolder: createFolderAction,
};

const OBJECT_ENTRY_FOLDER_CLASSNAME =
	'com.liferay.object.model.ObjectEntryFolder';

export default function ContentFDSPropsTransformer({
	additionalProps,
	creationMenu,
	itemsActions = [],
	...otherProps
}: {
	additionalProps: {
		autocompleteURL: string;
		collaboratorURLs: Record<string, string>;
	};
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
					component: AuthorRenderer,
					name: 'authorTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
				{
					component: NameRenderer,
					name: 'nameTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
				{
					component: SimpleActionLinkRenderer,
					name: 'simpleActionLinkTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
				{
					component: SpaceRenderer,
					name: 'spaceTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
				{
					component: TypeRenderer,
					name: 'typeTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
			],
		},
		infoPanelComponent: () => AssetTypeInfoPanel(otherProps),
		itemsActions: itemsActions.map((action) => {
			if (action?.data?.id === 'actionLink') {
				return {
					...action,
					isVisible: (item: any) =>
						Boolean(
							item?.entryClassName !==
								OBJECT_ENTRY_FOLDER_CLASSNAME
						),
				};
			}

			if (action?.data?.id === 'viewContent') {
				return {
					...action,
					data: {
						...action.data,
						disableHeader: false,
						size: 'full-screen',
						title: 'View',
					},
					isVisible: (item: any) =>
						Boolean(
							item?.entryClassName !==
								OBJECT_ENTRY_FOLDER_CLASSNAME
						),
				};
			}

			return action;
		}),
		onActionDropdownItemClick: ({
			action,
			itemData,
		}: {
			action: any;
			itemData: any;
		}) => {
			if (action?.data?.id === 'show-details') {
				Liferay.fire(EVENTS.ASSET_DATA, {items: [{...itemData}]});
			}
			else if (action?.data?.id === 'share') {
				const {autocompleteURL, collaboratorURLs} = additionalProps;

				shareAction({
					autocompleteURL,
					collaboratorURL: collaboratorURLs[itemData.entryClassName],
					creator: itemData.embedded.creator,
					itemId: itemData.embedded.id,
					title: itemData.embedded?.title,
				});
			}
		},
		onSelectedItemsChange: (selectedItems: any[]) => {
			Liferay.fire(EVENTS.ASSET_DATA, {items: selectedItems});
		},
	};
}
