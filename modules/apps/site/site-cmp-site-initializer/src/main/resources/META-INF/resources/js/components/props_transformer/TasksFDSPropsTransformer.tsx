/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DateRenderer, IInternalRenderer} from '@liferay/frontend-data-set-web';
import {AssigneeValue} from '@liferay/object-dynamic-data-mapping-form-field-type';
import {
	AssignToModalContent,
	SimpleActionLinkRenderer,
	UpdateDueDateModalContent,
	addOnClickToCreationMenuItems,
	deleteItemAction,
} from '@liferay/site-cms-site-initializer';
import React from 'react';

import {openCMPModal} from '../../utils/openCMPModal';
import StateLabel from '../StateLabel';
import EditAssigneeModalContent from '../modal/EditAssigneeModalContent';
import ACTIONS from './actions/creationMenuActions';
import {cmpTasksFDSAtom} from './atoms';

const _CLASS_NAME_KALEO_TASK_INSTANCE_TOKEN =
	'com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken';

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
		dateDue: string;
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

const WORKFLOW_TASK_MODALS: Record<
	string,
	(baseProps: {
		closeModal: () => void;
		dueDate: string;
		loadData: () => Promise<void>;
		workflowTaskId: number;
	}) => JSX.Element
> = {
	assignToMeWorkflowTask: (props) => (
		<AssignToModalContent {...props} assignable={false} />
	),
	assignToWorkflowTask: (props) => (
		<AssignToModalContent {...props} assignable={true} />
	),
	updateDueDateWorkflowTask: (props) => (
		<UpdateDueDateModalContent {...props} />
	),
};

export default function TasksFDSPropsTransformer({
	creationMenu,
	itemsActions = [],
	...otherProps
}: {
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
					component: ({itemData}) => {
						if (
							itemData.entryClassName ===
							_CLASS_NAME_KALEO_TASK_INSTANCE_TOKEN
						) {
							if (itemData.embedded?.assigneePerson) {
								return itemData.embedded.assigneePerson.name;
							}

							return itemData.embedded?.assigneeRoles
								?.map(({name}: {name: string}) => name)
								.join(', ');
						}

						return itemData.embedded?.assignTo?.name;
					},
					name: 'assigneeTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
				{
					component: ({itemData}) => {
						if (
							itemData.entryClassName ===
							_CLASS_NAME_KALEO_TASK_INSTANCE_TOKEN
						) {
							return DateRenderer({
								value: itemData.embedded?.dateDue,
							});
						}

						return DateRenderer({
							value: itemData.embedded?.dueDate,
						});
					},
					name: 'dueDateTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
				{
					component: ({itemData}) => {
						if (
							itemData.entryClassName ===
							_CLASS_NAME_KALEO_TASK_INSTANCE_TOKEN
						) {
							return '-';
						}

						return itemData.embedded
							?.r_cmpProjectToCMPTasks_c_cmpProject?.title;
					},
					name: 'projectTitleTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
				{
					component: ({actions, itemData, options}) => {
						if (
							itemData.entryClassName ===
							_CLASS_NAME_KALEO_TASK_INSTANCE_TOKEN
						) {
							return SimpleActionLinkRenderer({
								actions,
								itemData,
								options: {
									actionId: 'actionLinkWorkflowTask',
								},
								value: itemData.embedded?.objectReviewed
									?.assetTitle,
							});
						}

						return SimpleActionLinkRenderer({
							actions,
							itemData,
							options,
							value: itemData.embedded?.title,
						});
					},
					name: 'simpleActionLinkTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
				{
					component: ({itemData}) => {
						if (
							itemData.entryClassName ===
							_CLASS_NAME_KALEO_TASK_INSTANCE_TOKEN
						) {
							return '-';
						}

						return StateLabel(itemData.embedded?.state);
					},
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
			loadData: () => Promise<void>;
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

			if (
				itemData.entryClassName ===
				_CLASS_NAME_KALEO_TASK_INSTANCE_TOKEN
			) {
				await openCMPModal({
					center: true,
					contentComponent: ({
						closeModal,
					}: {
						closeModal: () => void;
					}) =>
						WORKFLOW_TASK_MODALS[action?.data?.id]({
							closeModal,
							dueDate: itemData.embedded?.dateDue,
							loadData,
							workflowTaskId: itemData.embedded?.id,
						}),
					size: 'md',
				});
			}
		},
	};
}
