/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayProgressBar from '@clayui/progress-bar';
import {IInternalRenderer} from '@liferay/frontend-data-set-web';
import {
	ACTIONS,
	AdditionalProps,
	SimpleActionLinkRenderer,
	addOnClickToCreationMenuItems,
	deleteItemAction,
	manageMembersAction,
} from '@liferay/site-cms-site-initializer';
import {fetch} from 'frontend-js-web';

import StateLabel from '../../common/components/StateLabel';
import UserRelationshipRenderer from './cell_renderers/UserRelationshipRenderer';

type Action = {
	href: string;
	method: string;
};

interface ItemData {
	actions: {
		delete: Action;
		get: Action;
		update: Action;
	};
	embedded: {
		content: string;
		content_i18n: {[locale: string]: string};
		creator: {
			contentType: string;
			id: number;
			image?: string;
			name: string;
		};
		defaultLanguageId: string;
		externalReferenceCode: string;
		file?: any;
		id: number;
		objectEntryFolderExternalReferenceCode?: string;
		objectEntryFolderId: number;
		parentObjectEntryFolderExternalReferenceCode?: string;
		scopeId: number;
		systemProperties?: any;
		title: string;
		title_i18n: {[locale: string]: string};
	};
	entryClassName: string;
	id: number;
	title: string;
}

export default function ProjectsFDSPropsTransformer({
	additionalProps,
	creationMenu,
	itemsActions = [],
	...otherProps
}: {
	additionalProps: AdditionalProps;
	creationMenu: any;
	itemsActions?: any[];
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
					component: ({value}) => ClayProgressBar({value}),
					name: 'progressBarTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
				{
					component: ({actions, itemData, options, value}) =>
						SimpleActionLinkRenderer({
							actions,
							additionalProps,
							itemData,
							options,
							value,
						}),
					name: 'simpleActionLinkTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
				{
					component: ({value}) => StateLabel(value),
					name: 'stateTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
				{
					component: UserRelationshipRenderer,
					name: 'userRelationshipTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
			],
		},
		itemsActions: itemsActions.map((action) => {
			if (action?.data?.id === 'delete') {
				return {
					...action,
					className: 'text-danger',
				};
			}

			return action;
		}),
		async onActionDropdownItemClick({
			action,
			itemData,
			loadData,
		}: {
			action: any;
			itemData: ItemData;
			loadData: () => {};
		}) {
			if (action?.data?.id === 'delete') {
				await deleteItemAction(itemData, loadData);
			}
			else if (action?.data?.id === 'view-members') {
				const scopeExternalReferenceCode =
					itemData.embedded.systemProperties?.scope
						?.externalReferenceCode;

				const response = await fetch(
					`/o/headless-asset-library/v1.0/asset-libraries/${scopeExternalReferenceCode}`,
					{
						method: 'GET',
					}
				);

				const {actions, creatorUserId} = await response.json();

				manageMembersAction({
					assetLibraryCreatorUserId: creatorUserId,
					externalReferenceCode: scopeExternalReferenceCode,
					hasAssignMembersPermission: 'assign-members' in actions,
					title: Liferay.Language.get('all-members'),
				});
			}
		},
	};
}
