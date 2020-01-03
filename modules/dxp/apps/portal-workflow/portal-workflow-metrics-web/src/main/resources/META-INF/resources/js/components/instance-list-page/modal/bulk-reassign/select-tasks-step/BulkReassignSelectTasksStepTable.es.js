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
import React, {useContext, useEffect, useState, useCallback} from 'react';

import {ModalContext} from '../../ModalContext.es';

const Item = task => {
	const {bulkModal, setBulkModal} = useContext(ModalContext);

	const {assetTitle, assetType, assigneePerson, id, instanceId, name} = task;
	const {selectedTasks} = bulkModal;

	const [checked, setChecked] = useState(false);

	useEffect(() => {
		setChecked(!!selectedTasks.find(item => item.id === id));
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedTasks]);

	const handleCheck = useCallback(
		({target}) => {
			setChecked(target.checked);

			if (target.checked) {
				setBulkModal({
					...bulkModal,
					selectedTasks: [...selectedTasks, task]
				});
			} else {
				setBulkModal({
					...bulkModal,
					selectedTasks: selectedTasks.filter(task => task.id !== id)
				});
			}
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[selectedTasks]
	);

	return (
		<ClayTable.Row>
			<ClayTable.Cell>
				<ClayCheckbox
					checked={checked}
					data-testid="itemCheckbox"
					onChange={handleCheck}
				/>
			</ClayTable.Cell>

			<ClayTable.Cell className="font-weight-bold">
				{instanceId}
			</ClayTable.Cell>

			<ClayTable.Cell>{`${assetType}: ${assetTitle}`}</ClayTable.Cell>

			<ClayTable.Cell>{name}</ClayTable.Cell>

			<ClayTable.Cell>
				{assigneePerson
					? assigneePerson.name
					: Liferay.Language.get('unassigned')}
			</ClayTable.Cell>
		</ClayTable.Row>
	);
};

const Table = ({items}) => {
	return (
		<ClayTable borderless={true} data-testid="bulkReassignModalTable">
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell
						headingCell
						style={{
							color: 'inherit',
							fontWeight: 'bold',
							width: '5%'
						}}
					></ClayTable.Cell>

					<ClayTable.Cell
						headingCell
						style={{
							color: 'inherit',
							fontWeight: 'bold',
							width: '10%'
						}}
					>
						{Liferay.Language.get('id')}
					</ClayTable.Cell>

					<ClayTable.Cell
						headingCell
						style={{
							color: 'inherit',
							fontWeight: 'bold',
							width: '30%'
						}}
					>
						{Liferay.Language.get('item-subject')}
					</ClayTable.Cell>

					<ClayTable.Cell
						headingCell
						style={{
							color: 'inherit',
							fontWeight: 'bold',
							width: '25%'
						}}
					>
						{Liferay.Language.get('process-step')}
					</ClayTable.Cell>

					<ClayTable.Cell
						style={{
							color: 'inherit',
							fontWeight: 'bold',
							width: '30%'
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
						<Table.Item {...item} key={index} />
					))}
			</ClayTable.Body>
		</ClayTable>
	);
};

Table.Item = Item;

export {Table};
