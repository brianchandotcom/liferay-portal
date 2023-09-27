/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import ClayTable from '@clayui/table';
import {sub} from 'frontend-js-web';
import React, {useContext} from 'react';

import {ModalContext} from '../../ModalProvider.es';

function Item({totalCount, ...task}) {
	const {
		selectTasks: {tasks},
		setSelectTasks,
	} = useContext(ModalContext);
	const {assetTitle, assetType, assignee, id, instanceId, label} = task;

	const checked = !!tasks.find((item) => item.id === id);

	const handleCheck = ({target}) => {
		const updatedItems = target.checked
			? [...tasks, task]
			: tasks.filter((task) => task.id !== id);

		setSelectTasks({
			selectAll: totalCount > 0 && totalCount === updatedItems.length,
			tasks: updatedItems,
		});
	};

	return (
		<ClayTable.Row className={checked ? 'table-active' : ''}>
			<ClayTable.Cell>
				<ClayCheckbox
					aria-label={sub(
						Liferay.Language.get('select-x-x'),
						assetType,
						assetTitle
					)}
					checked={checked}
					onChange={handleCheck}
				/>
			</ClayTable.Cell>

			<ClayTable.Cell className="font-weight-bold">
				{instanceId}
			</ClayTable.Cell>

			<ClayTable.Cell>{`${assetType}: ${assetTitle}`}</ClayTable.Cell>

			<ClayTable.Cell>{label}</ClayTable.Cell>

			<ClayTable.Cell>
				{assignee ? assignee.name : Liferay.Language.get('unassigned')}
			</ClayTable.Cell>
		</ClayTable.Row>
	);
}

function Table({items, totalCount}) {
	return (
		<ClayTable>
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell
						headingCell
						style={{
							color: 'inherit',
							fontWeight: 'bold',
							width: '5%',
						}}
					></ClayTable.Cell>

					<ClayTable.Cell
						headingCell
						style={{
							color: 'inherit',
							fontWeight: 'bold',
							width: '10%',
						}}
					>
						{Liferay.Language.get('id')}
					</ClayTable.Cell>

					<ClayTable.Cell
						headingCell
						style={{
							color: 'inherit',
							fontWeight: 'bold',
							width: '30%',
						}}
					>
						{Liferay.Language.get('item-subject')}
					</ClayTable.Cell>

					<ClayTable.Cell
						headingCell
						style={{
							color: 'inherit',
							fontWeight: 'bold',
							width: '25%',
						}}
					>
						{Liferay.Language.get('process-step')}
					</ClayTable.Cell>

					<ClayTable.Cell
						style={{
							color: 'inherit',
							fontWeight: 'bold',
							width: '30%',
						}}
					>
						{Liferay.Language.get('current-assignee')}
					</ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body>
				{items &&
					!!items.length &&
					items.map((item, index) => (
						<Table.Item
							{...item}
							key={index}
							totalCount={totalCount}
						/>
					))}
			</ClayTable.Body>
		</ClayTable>
	);
}

Table.Item = Item;

export default Table;
