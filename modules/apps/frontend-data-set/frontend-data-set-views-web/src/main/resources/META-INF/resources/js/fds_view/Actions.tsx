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

import {API_URL, OBJECT_RELATIONSHIP} from '../Constants';
import {IFDSViewSectionProps} from '../FDSView';
import openDefaultFailureToast from '../utils/openDefaultFailureToast';
import openDefaultSuccessToast from '../utils/openDefaultSuccessToast';
import ActionForm from './actions/ActionForm';
import ActionList from './actions/ActionList';

const SECTIONS = {
	CREATION_ACTIONS: 'creation-actions',
	EDIT_CREATION_ACTION: 'edit-creation-action',
	EDIT_ITEM_ACTION: 'edit-item-action',
	ITEM_ACTIONS: 'item-actions',
	NEW_CREATION_ACTION: 'new-creation-action',
	NEW_ITEM_ACTION: 'new-item-action',
};

interface IFDSAction {
	[OBJECT_RELATIONSHIP.FDS_VIEW_FDS_ACTION_CREATION]?: any;
	[OBJECT_RELATIONSHIP.FDS_VIEW_FDS_ACTION_ITEM]?: any;
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
	icon: string;
	id: number;
	label: string;
	label_i18n: {
		[key: string]: string;
	};
	modalSize?: string;
	permissionKey: string;
	title?: string;
	title_i18n?: {
		[key: string]: string;
	};
	type: string;
	url: string;
}

const Actions = ({fdsView, namespace, spritemap}: IFDSViewSectionProps) => {
	const [activeSection, setActiveSection] = useState(SECTIONS.ITEM_ACTIONS);
	const [activeTab, setActiveTab] = useState(0);
	const [fdsActions, setFDSActions] = useState<Array<IFDSAction>>([]);
	const [loading, setLoading] = useState(true);
	const [initialActionFormValues, setInitialActionFormValues] = useState<
		IFDSAction
	>();

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

	const loadFDSActions = async () => {
		setLoading(true);

		let url = '';
		if (activeTab === 0) {
			setActiveSection(SECTIONS.ITEM_ACTIONS);
			url = `${API_URL.FDS_ACTIONS}?filter=(${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_ACTION_ITEM_ID} eq '${fdsView.id}')&nestedFields=${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_ACTION_ITEM}&sort=dateCreated:desc`;
		}
		else if (activeTab === 1) {
			setActiveSection(SECTIONS.CREATION_ACTIONS);
			url = `${API_URL.FDS_ACTIONS}?filter=(${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_ACTION_CREATION_ID} eq '${fdsView.id}')&nestedFields=${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_ACTION_CREATION}&sort=dateCreated:desc`;
		}

		const response = await fetch(url);

		if (!response.ok) {
			setLoading(false);

			openDefaultFailureToast();

			return;
		}

		const responseJSON = await response.json();

		const storedFDSActions: IFDSAction[] = responseJSON.items;

		let ordered = storedFDSActions;
		let notOrdered: IFDSAction[] = [];

		const relationShip =
			activeTab === 0
				? OBJECT_RELATIONSHIP.FDS_VIEW_FDS_ACTION_ITEM
				: OBJECT_RELATIONSHIP.FDS_VIEW_FDS_ACTION_CREATION;

		const actionTypeOrder =
			activeTab === 0 ? 'fdsActionsItemOrder' : 'fdsActionsCreationOrder';

		const fdsActionsOrder =
			storedFDSActions?.[0]?.[relationShip]?.[actionTypeOrder];

		if (fdsActionsOrder) {
			const fdsActionsOrderArray = fdsActionsOrder.split(',') as string[];

			ordered = fdsActionsOrderArray
				.map((fdsActionId) =>
					storedFDSActions.find(
						(fdsAction) => fdsAction.id === Number(fdsActionId)
					)
				)
				.filter(Boolean) as IFDSAction[];

			notOrdered = storedFDSActions.filter(
				(fdsAction) =>
					!fdsActionsOrderArray.includes(String(fdsAction.id))
			);
		}

		setFDSActions([...notOrdered, ...ordered]);

		setLoading(false);
	};

	const createFDSAction = () => {
		const activeSection =
			activeTab === 0
				? SECTIONS.NEW_ITEM_ACTION
				: SECTIONS.NEW_CREATION_ACTION;

		setActiveSection(activeSection);
	};

	const deleteFDSAction = ({item}: {item: IFDSAction}) => {
		openModal({
			bodyHTML: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this-action'
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
							method: item.actions.delete.method,
						})
							.then(() => {
								openDefaultSuccessToast();

								loadFDSActions();
							})
							.catch(() => openDefaultFailureToast());
					},
				},
			],
			status: 'danger',
			title: Liferay.Language.get('delete-action'),
		});
	};

	const editFDSAction = ({item}: {item: IFDSAction}) => {
		setInitialActionFormValues(item);

		const actionType =
			activeTab === 0
				? SECTIONS.EDIT_ITEM_ACTION
				: SECTIONS.EDIT_CREATION_ACTION;

		setActiveSection(actionType);
	};

	const updateFDSActionsOrder = async ({order}: {order: string}) => {
		const actionTypeOrder =
			activeTab === 0 ? 'fdsActionsItemOrder' : 'fdsActionsCreationOrder';

		const response = await fetch(
			`${API_URL.FDS_VIEWS}/by-external-reference-code/${fdsView.externalReferenceCode}`,
			{
				body: JSON.stringify({
					[actionTypeOrder]: order,
				}),
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				},
				method: 'PATCH',
			}
		);

		if (!response.ok) {
			openDefaultFailureToast();

			return;
		}

		const responseJSON = await response.json();

		const storedFDSActionsOrder = responseJSON?.[actionTypeOrder];

		if (storedFDSActionsOrder && storedFDSActionsOrder === order) {
			openDefaultSuccessToast();
		}
		else {
			openDefaultFailureToast();
		}
	};

	useEffect(() => {
		loadFDSActions();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [activeTab]);

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
							active={activeTab}
							onActiveChange={setActiveTab}
						>
							<ClayTabs.Item>
								{Liferay.Language.get('item-actions')}
							</ClayTabs.Item>

							{Liferay.FeatureFlags['LPS-194395'] && (
								<ClayTabs.Item>
									{Liferay.Language.get('creation-actions')}
								</ClayTabs.Item>
							)}
						</ClayTabs>

						<ClayTabs.Content active={activeTab} fade>
							<ClayTabs.TabPane
								aria-labelledby={Liferay.Language.get(
									'actions'
								)}
							>
								<ActionList
									createFDSAction={createFDSAction}
									deleteFDSAction={deleteFDSAction}
									editFDSAction={editFDSAction}
									fdsActions={fdsActions}
									noItemsButtonLabel={Liferay.Language.get(
										'new-item-action'
									)}
									updateFDSActionsOrder={
										updateFDSActionsOrder
									}
								/>
							</ClayTabs.TabPane>

							<ClayTabs.TabPane
								aria-labelledby={Liferay.Language.get(
									'new-creation-action'
								)}
							>
								<ActionList
									createFDSAction={createFDSAction}
									deleteFDSAction={deleteFDSAction}
									editFDSAction={editFDSAction}
									fdsActions={fdsActions}
									noItemsButtonLabel={Liferay.Language.get(
										'new-creation-action'
									)}
									updateFDSActionsOrder={
										updateFDSActionsOrder
									}
								/>
							</ClayTabs.TabPane>
						</ClayTabs.Content>
					</>
				)}

				{(activeSection === SECTIONS.NEW_CREATION_ACTION ||
					activeSection === SECTIONS.NEW_ITEM_ACTION) && (
					<ActionForm
						activeTab={activeTab}
						fdsView={fdsView}
						loadFDSActions={loadFDSActions}
						namespace={namespace}
						sections={SECTIONS}
						setActiveSection={setActiveSection}
						spritemap={spritemap}
					/>
				)}

				{(activeSection === SECTIONS.EDIT_CREATION_ACTION ||
					activeSection === SECTIONS.EDIT_ITEM_ACTION) && (
					<ActionForm
						activeTab={activeTab}
						editing
						fdsView={fdsView}
						initialValues={initialActionFormValues}
						loadFDSActions={loadFDSActions}
						namespace={namespace}
						sections={SECTIONS}
						setActiveSection={setActiveSection}
						spritemap={spritemap}
					/>
				)}
			</ClayLayout.ContainerFluid>
		</ClayLayout.ContainerFluid>
	);
};

export {IFDSAction, SECTIONS};
export default Actions;
