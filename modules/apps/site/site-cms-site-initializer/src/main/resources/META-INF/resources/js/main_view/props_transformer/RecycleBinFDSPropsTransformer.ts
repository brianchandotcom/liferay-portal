/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IInternalRenderer} from '@liferay/frontend-data-set-web';
import {openToast} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';

import {openGenericFDSDeleteConfirmationModal} from '../../common/utils/genericOpenModalUtil';
import {restoreItemAction} from './actions/restoreItemAction';
import AuthorRenderer from './cell_renderers/AuthorRenderer';
import SimpleActionLinkRenderer from './cell_renderers/SimpleActionLinkRenderer';
import SpaceRenderer from './cell_renderers/SpaceRenderer';

type Action = {
	href: string;
	method: string;
};
interface ItemData {
	actions?: {
		delete: Action;
		expire: Action;
		get: Action;
		replace: Action;
		restore: Action;
		update: Action;
	};
	embedded: {content: string; objectEntryFolderId: number; title: string};
}

const OBJECT_ENTRY_FOLDER_CLASSNAME =
	'com.liferay.object.model.ObjectEntryFolder';

export default function RecycleBinFDSPropsTransformer({
	itemsActions = [],
	...otherProps
}: {
	itemsActions?: any[];
	otherProps: any;
}) {
	return {
		...otherProps,
		customRenderers: {
			tableCell: [
				{
					component: AuthorRenderer,
					name: 'authorTableCellRenderer',
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
			],
		},
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

			return action;
		}),
		async onActionDropdownItemClick({
			action,
			itemData,
			loadData,
		}: {
			action: {data: {id: string}};
			itemData: ItemData;
			loadData: () => {};
		}) {
			if (action.data.id === 'delete') {
				const formattedItemLabel = `<strong>${Liferay.Util.escapeHTML(itemData.embedded.title)}</strong>`;
				const confirmationText = sub(
					Liferay.Language.get(
						'you-are-about-to-permanently-delete-x-this-action-cannot-be-undone'
					),
					formattedItemLabel
				);

				const displayDeleteItemSuccessToast = () => {
					openToast({
						message: sub(
							Liferay.Language.get(
								'x-has-been-permanently-deleted'
							),
							formattedItemLabel
						),
						type: 'success',
					});
				};
				openGenericFDSDeleteConfirmationModal(
					confirmationText,
					itemData.actions?.delete?.method,
					itemData.actions?.delete?.href,
					itemData.embedded.title,
					loadData,
					displayDeleteItemSuccessToast
				);
			}

			if (action.data.id === 'restore') {
				const formattedItemLabel = `<strong>${Liferay.Util.escapeHTML(itemData.embedded.title)}</strong>`;

				await restoreItemAction(
					formattedItemLabel,
					loadData,
					itemData.actions?.restore.method,
					itemData.actions?.restore.href
				);
			}
		},
	};
}
