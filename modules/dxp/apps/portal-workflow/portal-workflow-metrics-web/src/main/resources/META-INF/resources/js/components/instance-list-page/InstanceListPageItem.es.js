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
import React, {useContext, useState, useCallback, useEffect} from 'react';

import Icon from '../../shared/components/Icon.es';
import QuickActionKebab from '../../shared/components/quick-action-kebab/QuickActionKebab.es';
import moment from '../../shared/util/moment.es';
import {ModalContext} from './modal/ModalContext.es';
import {InstanceListContext} from './store/InstanceListPageStore.es';

const getSLAStatusIcon = slaStatus => {
	if (slaStatus === 'OnTime') {
		return {
			bgColor: 'bg-success-light',
			iconColor: 'text-success',
			iconName: 'check-circle'
		};
	}

	if (slaStatus === 'Overdue') {
		return {
			bgColor: 'bg-danger-light',
			iconColor: 'text-danger',
			iconName: 'exclamation-circle'
		};
	}

	return {
		bgColor: 'bg-info-light',
		iconColor: 'text-info',
		iconName: 'hr'
	};
};

const Item = taskItem => {
	const {selectedItems = [], setInstanceId, setSelectedItems} = useContext(
		InstanceListContext
	);
	const [checked, setChecked] = useState(false);

	useEffect(() => {
		setChecked(selectedItems.find(item => item.id === id) !== undefined);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedItems]);

	const {
		assetTitle,
		assetType,
		assigneeUsers,
		creatorUser,
		dateCreated,
		id,
		status,
		slaStatus,
		taskNames = []
	} = taskItem;

	const completed = status === 'Completed';
	const slaStatusIcon = getSLAStatusIcon(slaStatus);
	const formattedAssignees = !completed
		? assigneeUsers && assigneeUsers.length
			? assigneeUsers.map(assigneeUser => assigneeUser.name).join(', ')
			: Liferay.Language.get('unassigned')
		: Liferay.Language.get('not-available');

	const handleCheck = ({target}) => {
		setChecked(target.checked);

		if (target.checked) {
			setSelectedItems([...selectedItems, taskItem]);
		} else {
			setSelectedItems(selectedItems.filter(item => item.id !== id));
		}
	};

	const updateInstanceId = () => setInstanceId(id);

	return (
		<ClayTable.Row data-testid="instanceRow">
			<ClayTable.Cell>
				<div className="table-first-element-group">
					<ClayCheckbox
						checked={checked}
						data-testid="instanceCheckbox"
						onChange={handleCheck}
					/>

					<span
						className={`sticker sticker-sm ${slaStatusIcon.bgColor}`}
					>
						<span className="inline-item">
							<Icon
								elementClasses={slaStatusIcon.iconColor}
								iconName={slaStatusIcon.iconName}
							/>
						</span>
					</span>
				</div>
			</ClayTable.Cell>

			<ClayTable.Cell>
				<span
					className="link-text"
					data-target="#instanceDetailModal"
					data-testid="instanceIdLink"
					data-toggle="modal"
					onClick={updateInstanceId}
					tabIndex="-1"
				>
					<strong>{id}</strong>
				</span>
			</ClayTable.Cell>

			<ClayTable.Cell data-testid="assetInfoCell">
				{`${assetType}: ${assetTitle} `}
			</ClayTable.Cell>

			<ClayTable.Cell data-testid="taskNamesCell">
				{!completed
					? taskNames.join(', ')
					: Liferay.Language.get('completed')}
			</ClayTable.Cell>

			<ClayTable.Cell data-testid="assigneesCell">
				{formattedAssignees}
			</ClayTable.Cell>

			<ClayTable.Cell data-testid="creatorUserCell">
				{creatorUser ? creatorUser.name : ''}
			</ClayTable.Cell>

			<ClayTable.Cell data-testid="dateCreatedCell">
				{moment
					.utc(dateCreated)
					.format(Liferay.Language.get('mmm-dd-yyyy-lt'))}
			</ClayTable.Cell>

			<ClayTable.Cell style={{paddingRight: '0rem'}}>
				<QuickActionMenu taskItem={taskItem} />
			</ClayTable.Cell>
		</ClayTable.Row>
	);
};

const QuickActionMenu = ({taskItem}) => {
	const {setSingleModal} = useContext(ModalContext);
	const handleClickReassignTask = useCallback(
		() => {
			setSingleModal({
				selectedItem: taskItem,
				visible: true
			});
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[taskItem]
	);

	const kebabItems = [
		{
			action: handleClickReassignTask,
			icon: 'change',
			title: Liferay.Language.get('reassign-task')
		}
	];

	return (
		<div className="autofit-col">
			<QuickActionKebab items={kebabItems} />
		</div>
	);
};

Item.QuickActionMenu = QuickActionMenu;

export {Item};
