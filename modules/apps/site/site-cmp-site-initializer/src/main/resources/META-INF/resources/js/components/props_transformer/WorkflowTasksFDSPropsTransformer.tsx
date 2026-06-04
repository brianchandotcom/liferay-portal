/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	DateRenderer,
	IInternalRenderer,
	IView,
} from '@liferay/frontend-data-set-web';
import {addOnClickToCreationMenuItems} from '@liferay/site-cms-site-initializer';
import React from 'react';

import {styleActions} from '../../utils/actionStyles';
import {
	installCMPTabPersistence,
	registerTabFDS,
} from '../../utils/cmpTabPersistence';
import {WORKFLOW_TASK_ACTION_LINK_ID} from '../../utils/constants';
import {openCMPModal} from '../../utils/openCMPModal';
import {TaskAction, WorkflowTaskItemData} from '../../utils/types';
import WORKFLOW_TASK_MODALS from '../../utils/workflowTaskModals';
import ACTIONS from './actions/creationMenuActions';
import WorkflowStateRenderer from './cell_renderers/WorkflowStateRenderer';
import WorkflowTaskActionLinkRenderer from './cell_renderers/WorkflowTaskActionLinkRenderer';

export default function WorkflowTasksFDSPropsTransformer({
	creationMenu,
	id,
	itemsActions = [],
	views,
	...otherProps
}: {
	apiURL: string;
	creationMenu: any;
	id: string;
	itemsActions?: any[];
	otherProps: any;
	views: IView[];
}) {
	const nonDefaultViews = views.map((view) => ({
		...view,
		default: false,
		initialPaginationDelta: 20,
	}));

	registerTabFDS(id, 2);
	installCMPTabPersistence();

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
					component: ({actions, itemData}) => (
						<WorkflowTaskActionLinkRenderer
							actionId={WORKFLOW_TASK_ACTION_LINK_ID}
							actions={actions}
							itemData={itemData}
						/>
					),
					name: 'assetTitleTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
				{
					component: ({itemData}) =>
						itemData.embedded?.objectReviewed?.assetType ?? '-',
					name: 'assetTypeTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
				{
					component: ({itemData}) =>
						itemData.embedded?.title ?? itemData.embedded?.name,
					name: 'taskTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
				{
					component: ({itemData}) =>
						DateRenderer({value: itemData.embedded?.dateDue}),
					name: 'dueDateTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
				{
					component: ({itemData}) =>
						itemData.embedded?.objectReviewed?.projectTitle ?? '-',
					name: 'projectTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
				{
					component: ({itemData}) => (
						<WorkflowStateRenderer embedded={itemData.embedded} />
					),
					name: 'workflowStateTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
				{
					component: ({itemData}) =>
						DateRenderer({value: itemData.embedded?.modifiedDate}),
					name: 'lastActivityDateTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
			],
		},
		hideManagementBarInEmptyState: true,
		id,
		itemsActions: styleActions(itemsActions),
		async onActionDropdownItemClick({
			action,
			itemData,
			loadData,
		}: {
			action: TaskAction;
			itemData: WorkflowTaskItemData;
			loadData: () => Promise<void>;
		}) {
			const modal = WORKFLOW_TASK_MODALS[action?.data?.id];

			if (!modal) {
				return;
			}

			await openCMPModal({
				center: true,
				contentComponent: ({closeModal}: {closeModal: () => void}) =>
					modal({
						closeModal,
						dueDate: itemData.embedded?.dateDue,
						loadData,
						workflowTaskId: itemData.embedded?.id,
					}),
				size: 'md',
			});
		},
		views: nonDefaultViews,
	};
}
