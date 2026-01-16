/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IInternalRenderer} from '@liferay/frontend-data-set-web';
import {AssigneeValue} from '@liferay/object-dynamic-data-mapping-form-field-type';
import {
	ACTIONS,
	AdditionalProps,
	SimpleActionLinkRenderer,
	addOnClickToCreationMenuItems,
	deleteItemAction,
} from '@liferay/site-cms-site-initializer';
import React from 'react';

import {openCMPModal} from '../../utils/openCMPModal';
import StateLabel from '../StateLabel';
import EditAssigneeModalContent from '../modal/EditAssigneeModalContent';
import {cmpTasksFDSAtom} from './atoms';

type action = {
	data: {
		id: string;
	};
};

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
		assignTo: AssigneeValue | null | {};
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

export default function TasksFDSPropsTransformer({
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
		atom: cmpTasksFDSAtom,
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
			action: action;
			itemData: ItemData;
			loadData: () => {};
		}) {
			if (action?.data?.id === 'delete') {
				await deleteItemAction(itemData, loadData);
			}
			else if (action?.data?.id === 'assign-to') {
				await openCMPModal({
					center: true,
					contentComponent: ({
						closeModal,
					}: {
						closeModal: () => void;
					}) => (
						<EditAssigneeModalContent
							closeModal={closeModal}
							loadData={loadData}
							taskId={String(itemData.embedded.id)}
							taskTitle={itemData.embedded.title}
							value={itemData.embedded.assignTo}
						/>
					),
					size: 'md',
				});
			}
		},
	};
}
