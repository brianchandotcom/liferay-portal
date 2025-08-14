/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IInternalRenderer, IView} from '@liferay/frontend-data-set-web';
import {openModal} from 'frontend-js-components-web';
import React from 'react';

import formatActionURL from '../../common/utils/formatActionURL';
import {ISearchAssetObjectEntry} from '../../structure_builder/types/AssetType';
import AssetTypeInfoPanel from '../info_panel/AssetTypeInfoPanelContent';
import FilePreviewerModalContent from '../modal/FilePreviewerModalContent';
import createAssetAction from './actions/createAssetAction';
import multipleFilesUploadAction from './actions/multipleFilesUploadAction';
import shareAction from './actions/shareAction';
import AuthorRenderer from './cell_renderers/AuthorRenderer';
import NameRenderer from './cell_renderers/NameRenderer';
import SpaceRenderer from './cell_renderers/SpaceRenderer';
import TypeRenderer from './cell_renderers/TypeRenderer';
import addOnClickToCreationMenuItems from './utils/addOnClickToCreationMenuItems';
import transformViewsItemsProps from './utils/transformViewsItemProps';

const ACTIONS = {
	createAsset: createAssetAction,
	uploadMultipleFiles: multipleFilesUploadAction,
};

export default function AllFDSPropsTransformer({
	additionalProps,
	creationMenu,
	itemsActions = [],
	views,
	...otherProps
}: {
	additionalProps: {
		autocompleteURL: string;
		cmsGroupId?: number;
		collaboratorURLs: Record<string, string>;
	};
	creationMenu: any;
	itemsActions?: any[];
	otherProps: any;
	views: IView[];
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
		infoPanelComponent: (items: {items: ISearchAssetObjectEntry[]}) => (
			<AssetTypeInfoPanel {...(otherProps as any)} {...items} />
		),
		itemsActions: itemsActions.map((action) => {
			if (action?.data?.id === 'download') {
				return {
					...action,
					isVisible: (item: any) =>
						Boolean(item?.embedded?.file?.link?.href),
				};
			}
			else if (action?.data?.id === 'view-content') {
				return {
					...action,
					isVisible: (item: any) => Boolean(!item?.embedded?.file),
				};
			}
			else if (action?.data?.id === 'view-file') {
				return {
					...action,
					isVisible: (item: any) => Boolean(item?.embedded?.file),
				};
			}

			return action;
		}),
		onActionDropdownItemClick: ({
			action,
			event,
			itemData,
		}: {
			action: any;
			event: Event;
			itemData: any;
		}) => {
			if (action?.data?.id === 'view-content') {
				event?.preventDefault();

				openModal({
					size: 'full-screen',
					title: itemData.embedded.title,
					url: formatActionURL(itemData, action.href),
				});
			}
			else if (action?.data?.id === 'view-file') {
				openModal({
					containerProps: {
						className: '',
					},
					contentComponent: () =>
						FilePreviewerModalContent({
							file: itemData.embedded.file,
							headerName: itemData.embedded.title,
						}),
					size: 'full-screen',
				});
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
		views: transformViewsItemsProps(views),
	};
}
