/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import OrderableTable from '../../../components/OrderableTable';
import {IAction} from '../Actions';

const ActionList = ({
	actions,
	createAction,
	creationMenuItemLabel,
	deleteAction,
	editAction,
	noItemsButtonLabel,
	updateActionsOrder,
}: {
	actions: Array<IAction>;
	createAction: () => void;
	creationMenuItemLabel: string;
	deleteAction: ({item}: {item: IAction}) => void;
	editAction: ({item}: {item: IAction}) => void;
	noItemsButtonLabel: string;
	updateActionsOrder: ({order}: {order: string}) => void;
}) => {
	return (
		<OrderableTable
			actions={[
				{
					icon: 'pencil',
					label: Liferay.Language.get('edit'),
					onClick: editAction,
				},
				{
					icon: 'trash',
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
					label: Liferay.Language.get('icon'),
					name: 'icon',
				},
				{
					label: Liferay.Language.get('label'),
					name: 'label',
				},
				{
					label: Liferay.Language.get('type'),
					name: 'target',
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
