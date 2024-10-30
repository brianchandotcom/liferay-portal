/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBreadcrumb from '@clayui/breadcrumb';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayTabs from '@clayui/tabs';
import {fetch, openModal} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {
	API_URL,
	DEFAULT_FETCH_HEADERS,
	OBJECT_RELATIONSHIP,
} from '../../utils/constants';
import openDefaultFailureToast from '../../utils/openDefaultFailureToast';
import openDefaultSuccessToast from '../../utils/openDefaultSuccessToast';
import {IDataSetSectionProps} from '../DataSet';
import ActionForm from './components/ActionForm';
import ActionList from './components/ActionList';

import '../../../css/Actions.scss';
import sortItems from '../../utils/sortItems';
import {IOrderable} from '../../utils/types';

export enum EActionTarget {
	ASYNC = 'async',
	HEADLESS = 'headless',
	LINK = 'link',
	MODAL = 'modal',
	SIDEPANEL = 'sidePanel',
}

export enum EActionType {
	CREATION = 'creation',
	ITEM = 'item',
}

const SECTIONS = {
	CREATION_ACTIONS: 'creation-actions',
	EDIT_CREATION_ACTION: 'edit-creation-action',
	EDIT_ITEM_ACTION: 'edit-item-action',
	ITEM_ACTIONS: 'item-actions',
	NEW_CREATION_ACTION: 'new-creation-action',
	NEW_ITEM_ACTION: 'new-item-action',
};

interface IAction extends IOrderable {
	[OBJECT_RELATIONSHIP.DATA_SET_ACTIONS]?: any;
	actions: {
		delete: {
			href: string;
			method: string;
		};
	};
	confirmationMessage?: string;
	confirmationMessageType?: string;
	confirmationMessage_i18n?: {
		[key: string]: string;
	};
	errorMessage?: string;
	errorMessage_i18n?: {
		[key: string]: string;
	};
	icon: string;
	label: string;
	label_i18n: {
		[key: string]: string;
	};
	method?: string;
	modalSize?: string;
	permissionKey: string;
	requestBody?: string;
	successMessage?: string;
	successMessage_i18n?: {
		[key: string]: string;
	};
	target: EActionTarget;
	title?: string;
	title_i18n?: {
		[key: string]: string;
	};
	type: EActionType;
	url: string;
}

const Actions = ({dataSet, namespace, spritemap}: IDataSetSectionProps) => {
	const [actions, setActions] = useState<Array<IAction>>([]);
	const [activeSection, setActiveSection] = useState(SECTIONS.ITEM_ACTIONS);
	const [activeTab, setActiveTab] = useState(0);
	const [loading, setLoading] = useState(true);
	const [initialActionFormValues, setInitialActionFormValues] =
		useState<IAction>();

	const getBreadcrumbItems = () => {
		const breadcrumbItems: React.ComponentProps<
			typeof ClayBreadcrumb
		>['items'] = [
			{
				active:
					activeSection === SECTIONS.ITEM_ACTIONS ||
					activeSection === SECTIONS.CREATION_ACTIONS,
				label: Liferay.Language.get('actions'),
				onClick: () => {
					const activeSection =
						activeTab === 0
							? SECTIONS.ITEM_ACTIONS
							: SECTIONS.CREATION_ACTIONS;
					setActiveSection(activeSection);
				},
			},
		];

		if (activeSection === SECTIONS.NEW_ITEM_ACTION) {
			breadcrumbItems.push({
				active: true,
				label: Liferay.Language.get('new-item-action'),
				onClick: () => setActiveSection(SECTIONS.NEW_ITEM_ACTION),
			});
		}

		if (
			activeSection === SECTIONS.EDIT_ITEM_ACTION ||
			activeSection === SECTIONS.EDIT_CREATION_ACTION
		) {
			breadcrumbItems.push({
				active: true,
				label: initialActionFormValues?.label || '',
			});
		}

		if (activeSection === SECTIONS.NEW_CREATION_ACTION) {
			breadcrumbItems.push({
				active: true,
				label: Liferay.Language.get('new-creation-action'),
				onClick: () => setActiveSection(SECTIONS.NEW_CREATION_ACTION),
			});
		}

		return breadcrumbItems;
	};

	const loadActions = async ({activeTab}: {activeTab: number}) => {
		setLoading(true);

		const type = activeTab === 0 ? EActionType.ITEM : EActionType.CREATION;

		const url = `${API_URL.ACTIONS}?filter=(${OBJECT_RELATIONSHIP.DATA_SET_ACTIONS_ID} eq '${dataSet.id}') and (type eq '${type}')&nestedFields=${OBJECT_RELATIONSHIP.DATA_SET_ACTIONS}&sort=dateCreated:asc`;

		if (activeTab === 0) {
			setActiveSection(SECTIONS.ITEM_ACTIONS);
		}
		else if (activeTab === 1) {
			setActiveSection(SECTIONS.CREATION_ACTIONS);
		}

		const response = await fetch(url, {
			headers: DEFAULT_FETCH_HEADERS,
		});

		if (!response.ok) {
			setLoading(false);

			openDefaultFailureToast();

			return;
		}

		const responseJSON = await response.json();

		const storedActions: IAction[] = responseJSON.items;

		const actionTypeOrder =
			activeTab === 0 ? 'itemActionsOrder' : 'creationActionsOrder';

		const actionsOrder =
			storedActions?.[0]?.[OBJECT_RELATIONSHIP.DATA_SET_ACTIONS]?.[
				actionTypeOrder
			];

		setActions(sortItems(storedActions, actionsOrder) as IAction[]);

		setLoading(false);
	};

	const createAction = () => {
		const activeSection =
			activeTab === 0
				? SECTIONS.NEW_ITEM_ACTION
				: SECTIONS.NEW_CREATION_ACTION;

		setActiveSection(activeSection);
	};

	const deleteAction = ({item}: {item: IAction}) => {
		openModal({
			bodyHTML: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this-action?-fragments-using-it-will-be-affected'
			),
			buttons: [
				{
					autoFocus: true,
					displayType: 'secondary',
					label: Liferay.Language.get('cancel'),
					type: 'cancel',
				},
				{
					displayType: 'danger',
					label: Liferay.Language.get('delete'),
					onClick: ({processClose}: {processClose: Function}) => {
						processClose();

						fetch(item.actions.delete.href, {
							headers: DEFAULT_FETCH_HEADERS,
							method: item.actions.delete.method,
						})
							.then(() => {
								openDefaultSuccessToast();

								loadActions({activeTab});
							})
							.catch(() => openDefaultFailureToast());
					},
				},
			],
			status: 'danger',
			title: Liferay.Language.get('delete-action'),
		});
	};

	const editAction = ({item}: {item: IAction}) => {
		setInitialActionFormValues(item);

		const actionType =
			activeTab === 0
				? SECTIONS.EDIT_ITEM_ACTION
				: SECTIONS.EDIT_CREATION_ACTION;

		setActiveSection(actionType);
	};

	const updateActionsOrder = async ({order}: {order: string}) => {
		const actionTypeOrder =
			activeTab === 0 ? 'itemActionsOrder' : 'creationActionsOrder';

		const apiURL = API_URL.DATA_SETS;

		const response = await fetch(
			`${apiURL}/by-external-reference-code/${dataSet.externalReferenceCode}`,
			{
				body: JSON.stringify({
					[actionTypeOrder]: order,
				}),
				headers: DEFAULT_FETCH_HEADERS,
				method: 'PATCH',
			}
		);

		if (!response.ok) {
			openDefaultFailureToast();

			return;
		}

		const responseJSON = await response.json();

		const storedActionsOrder = responseJSON?.[actionTypeOrder];

		if (actions && storedActionsOrder && storedActionsOrder === order) {
			setActions(sortItems(actions, storedActionsOrder) as IAction[]);

			openDefaultSuccessToast();
		}
		else {
			openDefaultFailureToast();
		}
	};

	useEffect(() => {
		loadActions({activeTab: 0});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	if (loading) {
		return <ClayLoadingIndicator />;
	}

	return (
		<ClayLayout.ContainerFluid>
			<ClayBreadcrumb className="my-2" items={getBreadcrumbItems()} />

			<ClayLayout.ContainerFluid className="bg-white mb-4 p-0 rounded-sm">
				{(activeSection === SECTIONS.ITEM_ACTIONS ||
					activeSection === SECTIONS.CREATION_ACTIONS) && (
					<>
						<h2 className="mb-0 p-4">
							{Liferay.Language.get('actions')}
						</h2>

						<ClayTabs
							activation="automatic"
							active={activeTab}
							className="actions-tabs"
							onActiveChange={(tab: number) => {
								setActiveTab(tab);

								loadActions({activeTab: tab});
							}}
						>
							<ClayTabs.Item>
								{Liferay.Language.get('item-actions')}
							</ClayTabs.Item>

							<ClayTabs.Item>
								{Liferay.Language.get('creation-actions')}
							</ClayTabs.Item>
						</ClayTabs>

						<ClayTabs.Content active={activeTab} fade>
							<ClayTabs.TabPane
								aria-label={Liferay.Language.get(
									'item-actions'
								)}
								className="item-actions-tab-pane"
							>
								<ActionList
									actions={actions}
									createAction={createAction}
									creationMenuItemLabel={Liferay.Language.get(
										'new-item-action'
									)}
									deleteAction={deleteAction}
									editAction={editAction}
									noItemsButtonLabel={Liferay.Language.get(
										'new-item-action'
									)}
									updateActionsOrder={updateActionsOrder}
								/>
							</ClayTabs.TabPane>

							<ClayTabs.TabPane
								aria-label={Liferay.Language.get(
									'creation-actions'
								)}
								className="creation-actions-tab-pane"
							>
								<ActionList
									actions={actions}
									createAction={createAction}
									creationMenuItemLabel={Liferay.Language.get(
										'new-creation-action'
									)}
									deleteAction={deleteAction}
									editAction={editAction}
									noItemsButtonLabel={Liferay.Language.get(
										'new-creation-action'
									)}
									updateActionsOrder={updateActionsOrder}
								/>
							</ClayTabs.TabPane>
						</ClayTabs.Content>
					</>
				)}

				{(activeSection === SECTIONS.NEW_CREATION_ACTION ||
					activeSection === SECTIONS.NEW_ITEM_ACTION) && (
					<ActionForm
						activeTab={activeTab}
						dataSet={dataSet}
						namespace={namespace}
						onCancel={() => {
							setActiveSection(
								activeTab === 0
									? SECTIONS.ITEM_ACTIONS
									: SECTIONS.CREATION_ACTIONS
							);
						}}
						onSave={() => {
							setActiveSection(
								activeTab === 0
									? SECTIONS.ITEM_ACTIONS
									: SECTIONS.CREATION_ACTIONS
							);

							loadActions({activeTab});
						}}
						spritemap={spritemap}
					/>
				)}

				{(activeSection === SECTIONS.EDIT_CREATION_ACTION ||
					activeSection === SECTIONS.EDIT_ITEM_ACTION) && (
					<ActionForm
						activeTab={activeTab}
						dataSet={dataSet}
						editing
						initialValues={initialActionFormValues}
						namespace={namespace}
						onCancel={() => {
							setActiveSection(
								activeTab === 0
									? SECTIONS.ITEM_ACTIONS
									: SECTIONS.CREATION_ACTIONS
							);
						}}
						onSave={() => {
							setActiveSection(
								activeTab === 0
									? SECTIONS.ITEM_ACTIONS
									: SECTIONS.CREATION_ACTIONS
							);

							loadActions({activeTab});
						}}
						spritemap={spritemap}
					/>
				)}
			</ClayLayout.ContainerFluid>
		</ClayLayout.ContainerFluid>
	);
};

export {IAction, SECTIONS};
export default Actions;
