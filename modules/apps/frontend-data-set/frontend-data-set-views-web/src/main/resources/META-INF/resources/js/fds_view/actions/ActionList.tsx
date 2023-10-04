/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import OrderableTable from '../../components/OrderableTable';
import {IFDSAction} from '../Actions';

interface IFDSActionListProps {
	createFDSAction: () => void;
	deleteFDSAction: ({item}: {item: IFDSAction}) => void;
	editFDSAction: ({item}: {item: IFDSAction}) => void;
	fdsActions: Array<IFDSAction>;
	noItemsButtonLabel: string;
	updateFDSActionsOrder: ({order}: {order: string}) => void;
}

const ActionList = ({
	createFDSAction,
	deleteFDSAction,
	editFDSAction,
	fdsActions,
	noItemsButtonLabel,
	updateFDSActionsOrder,
}: IFDSActionListProps) => {
	return (
		<OrderableTable
			actions={[
				{
					icon: 'pencil',
					label: Liferay.Language.get('edit'),
					onClick: editFDSAction,
				},
				{
					icon: 'trash',
					label: Liferay.Language.get('delete'),
					onClick: deleteFDSAction,
				},
			]}
			className="mt-0 p-1"
			creationMenuItems={[
				{
					label: Liferay.Language.get('add-action'),
					onClick: createFDSAction,
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
					name: 'type',
				},
			]}
			items={fdsActions}
			noItemsButtonLabel={noItemsButtonLabel}
			noItemsDescription={Liferay.Language.get(
				'start-creating-an-action-to-interact-with-your-data'
			)}
			noItemsTitle={Liferay.Language.get('no-actions-were-created')}
			onOrderChange={({order}: {order: string}) => {
				updateFDSActionsOrder({
					order,
				});
			}}
		/>
	);
};

export default ActionList;
