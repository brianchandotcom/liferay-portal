/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	FDS_EVENT,
	IBulkActionItem,
	IView,
} from '@liferay/frontend-data-set-web';
import {openToast} from 'frontend-js-components-web';

import ApiHelper from '../../common/services/ApiHelper';
import AssetsFDSPropsTransformer, {
	AdditionalProps,
} from './AssetsFDSPropsTransformer';
import fileDropAction from './actions/fileDropAction';
import {MultipleFileUploaderData} from './actions/multipleFilesUploadAction';
import filterFDSBulkActions from './utils/filterFDSBulkActions';

export default function RelatedAssetsFDSPropsTransformer({
	additionalProps,
	bulkActions = [],
	creationMenu,
	id,
	itemsActions = [],
	views,
	...otherProps
}: {
	additionalProps: AdditionalProps & MultipleFileUploaderData;
	bulkActions: Array<IBulkActionItem>;
	creationMenu: any;
	id: string;
	itemsActions?: any[];
	otherProps: any;
	views: IView[];
}) {
	const assetsData = AssetsFDSPropsTransformer({
		additionalProps,
		creationMenu,
		itemsActions,
		...otherProps,
		views,
	});

	return {
		...assetsData,
		bulkActions: filterFDSBulkActions(bulkActions),
		fileDropSettings: {
			enabled: true,
			isDropTarget: () => true,
			onFileDrop: (droppedFiles: any, dropTarget?: any) => {
				fileDropAction(
					{
						...additionalProps,
						loadData: () =>
							Liferay.fire(FDS_EVENT.UPDATE_DISPLAY, {id}),
					},
					droppedFiles,
					dropTarget
				);
			},
		},
		id,
		infoPanelComponent: null,
		async onActionDropdownItemClick({
			action,
			event,
			itemData,
			items,
			loadData,
		}: {
			action: any;
			event: Event;
			itemData: ItemData;
			items: any;
			loadData: () => {};
		}) {
			if (action.data.id === 'unlink-asset') {
				const {actions, embedded} = itemData;

				await ApiHelper.patch(
					{
						keywords: embedded.keywords?.filter(
							(keyword) =>
								!additionalProps.keywords
									?.split(',')
									.includes(keyword)
						),
					},
					actions.update.href
				);

				openToast({
					message: Liferay.Language.get(
						'your-request-completed-successfully'
					),
					type: 'success',
				});

				loadData();
			}
			else {
				assetsData.onActionDropdownItemClick({
					action,
					event,
					itemData,
					items,
					loadData,
				});
			}
		},
	};
}
