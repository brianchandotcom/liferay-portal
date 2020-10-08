/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {ClayCheckbox} from '@clayui/form';
import ClayTable from '@clayui/table';
import React, {useContext} from 'react';

import {ModalContext} from '../../ModalProvider.es';

const Item = ({totalCount, ...task}) => {
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
				<ClayCheckbox checked={checked} onChange={handleCheck} />
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
};

const Table = ({items, totalCount}) => {
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
					items.length > 0 &&
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
};

Table.Item = Item;

export {Table};
