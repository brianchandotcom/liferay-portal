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
import OrderableTable from '../components/OrderableTable';
import openDefaultFailureToast from '../utils/openDefaultFailureToast';
import openDefaultSuccessToast from '../utils/openDefaultSuccessToast';
import ItemActionForm from './actions/ItemActionForm';

const SECTIONS = {
	ACTIONS: 'actions',
	EDIT_ITEM_ACTION: 'edit-item-action',
	NEW_ITEM_ACTION: 'new-item-action',
};

interface IFDSAction {
	[OBJECT_RELATIONSHIP.FDS_VIEW_FDS_ACTION]: any;
	actions: {
		delete: {
			href: string;
			method: string;
		};
	};
	confirmationMessage: string;
	confirmationMessage_i18n: {
		[key: string]: string;
	};
	icon: string;
	id: number;
	label: string;
	label_i18n: {
		[key: string]: string;
	};
	type: string;
	url: string;
}

const Actions = ({fdsView, namespace, spritemap}: IFDSViewSectionProps) => {
	const [activeSection, setActiveSection] = useState(SECTIONS.ACTIONS);
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
				active: activeSection === SECTIONS.ACTIONS,
				label: Liferay.Language.get('actions'),
				onClick: () => setActiveSection(SECTIONS.ACTIONS),
			},
		];

		if (activeSection === SECTIONS.NEW_ITEM_ACTION) {
			breadcrumbItems.push({
				active: true,
				label: Liferay.Language.get('new-item-action'),
				onClick: () => setActiveSection(SECTIONS.NEW_ITEM_ACTION),
			});
		}

		if (activeSection === SECTIONS.EDIT_ITEM_ACTION) {
			breadcrumbItems.push({
				active: true,
				label: initialActionFormValues?.label || '',
			});
		}

		return breadcrumbItems;
	};

	const loadFDSActions = async () => {
		setLoading(true);

		const response = await fetch(
			`${API_URL.FDS_ACTIONS}?filter=(${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_ACTION_ID} eq '${fdsView.id}')&nestedFields=${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_ACTION}&sort=dateCreated:desc`
		);

		if (!response.ok) {
			setLoading(false);

			openDefaultFailureToast();

			return;
		}

		const responseJSON = await response.json();

		const storedFDSActions: IFDSAction[] = responseJSON.items;

		let ordered = storedFDSActions;
		let notOrdered: IFDSAction[] = [];

		const fdsActionsOrder =
			storedFDSActions?.[0]?.[OBJECT_RELATIONSHIP.FDS_VIEW_FDS_ACTION]
				?.fdsActionsOrder;

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

	const handleEdit = ({item}: {item: IFDSAction}) => {
		setInitialActionFormValues(item);

		setActiveSection(SECTIONS.EDIT_ITEM_ACTION);
	};

	const updateFDSActionsOrder = async ({
		fdsActionsOrder,
	}: {
		fdsActionsOrder: string;
	}) => {
		const response = await fetch(
			`${API_URL.FDS_VIEWS}/by-external-reference-code/${fdsView.externalReferenceCode}`,
			{
				body: JSON.stringify({
					fdsActionsOrder,
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

		const storedFDSActionsOrder = responseJSON?.fdsActionsOrder;

		if (
			storedFDSActionsOrder &&
			storedFDSActionsOrder === fdsActionsOrder
		) {
			openDefaultSuccessToast();
		}
		else {
			openDefaultFailureToast();
		}
	};

	useEffect(() => {
		loadFDSActions();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	if (loading) {
		return <ClayLoadingIndicator />;
	}

	return (
		<ClayLayout.ContainerFluid>
			<ClayBreadcrumb className="my-2" items={getBreadcrumbItems()} />

			<ClayLayout.ContainerFluid className="bg-white mb-4 p-0 rounded-sm">
				{activeSection === SECTIONS.ACTIONS && (
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

							<ClayTabs.Item>
								{Liferay.Language.get('creation-actions')}
							</ClayTabs.Item>
						</ClayTabs>

						<ClayTabs.Content active={activeTab} fade>
							<ClayTabs.TabPane
								aria-labelledby={Liferay.Language.get(
									'actions'
								)}
							>
								<OrderableTable
									actions={[
										{
											icon: 'pencil',
											label: Liferay.Language.get('edit'),
											onClick: handleEdit,
										},
										{
											icon: 'trash',
											label: Liferay.Language.get(
												'delete'
											),
											onClick: deleteFDSAction,
										},
									]}
									className="mt-0 p-1"
									creationMenuItems={[
										{
											label: Liferay.Language.get(
												'add-action'
											),
											onClick: () =>
												setActiveSection(
													SECTIONS.NEW_ITEM_ACTION
												),
										},
									]}
									fields={[
										{
											label: Liferay.Language.get('icon'),
											name: 'icon',
										},
										{
											label: Liferay.Language.get(
												'label'
											),
											name: 'label',
										},
										{
											label: Liferay.Language.get('type'),
											name: 'type',
										},
									]}
									items={fdsActions}
									noItemsButtonLabel={Liferay.Language.get(
										'create-item-action'
									)}
									noItemsDescription={Liferay.Language.get(
										'start-creating-an-action-to-interact-with-your-data'
									)}
									noItemsTitle={Liferay.Language.get(
										'no-actions-were-created'
									)}
									onOrderChange={({
										order,
									}: {
										order: string;
									}) => {
										updateFDSActionsOrder({
											fdsActionsOrder: order,
										});
									}}
								/>
							</ClayTabs.TabPane>

							<ClayTabs.TabPane
								aria-labelledby={Liferay.Language.get(
									'new-item-action'
								)}
							>
								2. Proin efficitur imperdiet dolor, a iaculis
								orci lacinia eu.
							</ClayTabs.TabPane>
						</ClayTabs.Content>
					</>
				)}

				{activeSection === SECTIONS.NEW_ITEM_ACTION && (
					<ItemActionForm
						fdsView={fdsView}
						loadFDSActions={loadFDSActions}
						namespace={namespace}
						sections={SECTIONS}
						setActiveSection={setActiveSection}
						spritemap={spritemap}
					/>
				)}

				{activeSection === SECTIONS.EDIT_ITEM_ACTION && (
					<ItemActionForm
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

export {IFDSAction};
export default Actions;
