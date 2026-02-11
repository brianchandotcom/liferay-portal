/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React from 'react';

import OrderableTable from '../../../components/OrderableTable';
import Toggle from '../../../components/Toggle';
import {IAction} from '../Actions';

enum EItemImportPolicy {
	GROUP_PROXY = 'GROUP_PROXY',
	ITEM_PROXY = 'ITEM_PROXY',
}

const isVisible = ({item}: {item: any}) => {
	if (
		item.target === EItemImportPolicy.ITEM_PROXY ||
		item.target === EItemImportPolicy.GROUP_PROXY
	) {
		return false;
	}

	return true;
};

const ActionIconComponent = ({item}: any) => {
	return <>{item.icon ? <ClayIcon symbol={item.icon} /> : ''}</>;
};

const ActionTypeComponent = ({item}: any) => {
	return (
		<>
			{item.target === EItemImportPolicy.ITEM_PROXY ? (
				<span>{Liferay.Language.get('system-action')}</span>
			) : item.target === EItemImportPolicy.GROUP_PROXY ? (
				<span>{Liferay.Language.get('group-of-system-actions')}</span>
			) : (
				<span>{item.target}</span>
			)}
		</>
	);
};

const ActionList = ({
	actions,
	createAction,
	creationMenuItemLabel,
	deleteAction,
	editAction,
	noItemsButtonLabel,
	toogleActiveDisabled,
	updateActionsOrder,
	updateActive,
}: {
	actions: Array<IAction>;
	createAction: () => void;
	creationMenuItemLabel: string;
	deleteAction: ({item}: {item: IAction}) => void;
	editAction: ({item}: {item: IAction}) => void;
	noItemsButtonLabel: string;
	toogleActiveDisabled: boolean;
	updateActionsOrder: ({order}: {order: string}) => void;
	updateActive: (item: IAction) => Promise<void>;
}) => {
	return (
		<OrderableTable
			actions={[
				{
					icon: 'pencil',
					isVisible,
					label: Liferay.Language.get('edit'),
					onClick: editAction,
				},
				{
					icon: 'trash',
					isVisible,
					label: Liferay.Language.get('delete'),
					onClick: deleteAction,
				},
			]}
			className="mt-0 p-1"
			creationMenuItems={[
				{
					label: creationMenuItemLabel,
					onClick: createAction,
				},
			]}
			fields={[
				{
					contentRenderer: {
						component: ActionIconComponent,
					},
					label: Liferay.Language.get('icon'),
					name: 'icon',
				},
				{
					label: Liferay.Language.get('label'),
					name: 'label',
				},
				{
					contentRenderer: {
						component: ActionTypeComponent,
					},
					label: Liferay.Language.get('type'),
					name: 'target',
				},
				{
					contentRenderer: {
						component: ({item}: any) =>
							Toggle({
								disabled: toogleActiveDisabled,
								item,
								toggleChange: updateActive,
							}),
					},
					label: Liferay.Language.get('status'),
					name: 'active',
				},
			]}
			items={actions}
			noItemsButtonLabel={noItemsButtonLabel}
			noItemsDescription={Liferay.Language.get(
				'start-creating-an-action-to-interact-with-your-data'
			)}
			noItemsTitle={Liferay.Language.get('no-actions-were-created')}
			onOrderChange={({order}: {order: string}) => {
				updateActionsOrder({
					order,
				});
			}}
		/>
	);
};

export default ActionList;
