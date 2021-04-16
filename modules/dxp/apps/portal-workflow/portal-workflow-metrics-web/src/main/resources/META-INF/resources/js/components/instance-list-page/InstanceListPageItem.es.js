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
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayTable from '@clayui/table';
import React, {useContext} from 'react';

import QuickActionKebab from '../../shared/components/quick-action-kebab/QuickActionKebab.es';
import moment from '../../shared/util/moment.es';
import {capitalize} from '../../shared/util/util.es';
import {AppContext} from '../AppContext.es';
import {InstanceListContext} from './InstanceListPageProvider.es';
import {ModalContext} from './modal/ModalProvider.es';

const getSLAStatusIconInfo = (slaStatus) => {
	const items = {
		OnTime: {
			bgColor: 'bg-success-light',
			name: 'check-circle',
			textColor: 'text-success',
		},
		Overdue: {
			bgColor: 'bg-danger-light',
			name: 'exclamation-circle',
			textColor: 'text-danger',
		},
		Untracked: {
			bgColor: 'bg-info-light',
			name: 'hr',
			textColor: 'text-info',
		},
	};

	return items[slaStatus] || items.Untracked;
};

const getDueDateFormatted = (dateOverdue) => {
	if (!dateOverdue) {
		return '';
	}

	const sameYear = dateOverdue.split('-')[0] == new Date().getFullYear();

	const format = sameYear
		? Liferay.Language.get('mmm-dd')
		: Liferay.Language.get('mmm-dd-yyyy');

	return moment.utc(dateOverdue).format(format);
};

const Item = ({totalCount, ...instance}) => {
	const {userId} = useContext(AppContext);
	const {
		selectedItems = [],
		setInstanceId,
		setSelectAll,
		setSelectedItems,
	} = useContext(InstanceListContext);
	const {openModal} = useContext(ModalContext);

	const {
		assetTitle,
		assetType,
		assignees = [],
		completed,
		creator,
		dateCreated,
		id,
		slaResults = [],
		slaStatus,
		taskNames = [Liferay.Language.get('not-available')],
	} = instance;

	const checked = !!selectedItems.find((item) => item.id === id);

	const assignedToUser = !!assignees.find(({id}) => id === Number(userId));
	const assigneeNames = assignees.map((user) => user.name).join(', ');
	const {reviewer} = assignees.find(({id}) => id === -1) || {};

	const disableCheckbox = (!assignedToUser && !reviewer) || completed;

	const formattedAssignees = !completed
		? assigneeNames
		: Liferay.Language.get('not-available');

	const formattedTaskNames = !completed
		? taskNames.join(', ')
		: Liferay.Language.get('completed');

	const handleCheck = ({target}) => {
		const updatedItems = target.checked
			? [...selectedItems, instance]
			: selectedItems.filter((item) => item.id !== id);

		setSelectAll(totalCount > 0 && totalCount === updatedItems.length);
		setSelectedItems(updatedItems);
	};

	const [slaResult] = slaResults;

	const dateOverdue = getDueDateFormatted(slaResult?.dateOverdue);
	const slaRunning = slaResult?.status === 'Running';

	const slaStatusIconInfo = getSLAStatusIconInfo(slaStatus);

	return (
		<ClayTable.Row className={checked ? 'table-active' : ''}>
			<ClayTable.Cell>
				<div className="table-first-element-group">
					<ClayCheckbox
						checked={checked}
						className="mr-2"
						disabled={disableCheckbox}
						onChange={handleCheck}
					/>

					<span
						className={`sticker sticker-sm ${slaStatusIconInfo.bgColor}`}
					>
						<span className="inline-item">
							<ClayIcon
								className={slaStatusIconInfo.textColor}
								symbol={slaStatusIconInfo.name}
							/>
						</span>
					</span>
				</div>
			</ClayTable.Cell>

			<ClayTable.Cell>
				<span
					className="link-text"
					onClick={() => {
						setInstanceId(id);

						openModal('instanceDetails');
					}}
					tabIndex="-1"
				>
					<strong>{id}</strong>
				</span>
			</ClayTable.Cell>

			<ClayTable.Cell>
				<div
					className={`due-date ${
						slaRunning ? slaStatusIconInfo.textColor : 'text-info'
					}`}
				>
					{!slaResult || !slaRunning ? (
						'-'
					) : (
						<>
							<span className="due-date-badge"></span>
							{dateOverdue}
						</>
					)}
				</div>
			</ClayTable.Cell>

			<ClayTable.Cell>{`${assetType}: ${assetTitle}`}</ClayTable.Cell>

			<ClayTable.Cell>{formattedTaskNames}</ClayTable.Cell>

			<ClayTable.Cell>{formattedAssignees}</ClayTable.Cell>

			<ClayTable.Cell>{creator ? creator.name : ''}</ClayTable.Cell>

			<ClayTable.Cell>
				{moment
					.utc(dateCreated)
					.format(Liferay.Language.get('mmm-dd-yyyy-lt'))}
			</ClayTable.Cell>

			<ClayTable.Cell style={{paddingRight: '0rem'}}>
				<QuickActionMenu
					disabled={disableCheckbox}
					instance={instance}
				/>
			</ClayTable.Cell>
		</ClayTable.Row>
	);
};

const QuickActionMenu = ({disabled, instance}) => {
	const {openModal, setSingleTransition} = useContext(ModalContext);
	const {setSelectedItems} = useContext(InstanceListContext);
	const {transitions = [], taskNames = []} = instance;

	const handleClick = (bulkModal, singleModal) => {
		openModal(taskNames.length > 1 ? bulkModal : singleModal);
		setSelectedItems([instance]);
	};

	const transitionLabel = capitalize(Liferay.Language.get('transition'));
	const updateDueDateItem = {
		icon: 'date',
		label: Liferay.Language.get('update-due-date'),
		onClick: () => handleClick('bulkUpdateDueDate', 'updateDueDate'),
	};

	const kebabItems = [
		{
			icon: 'change',
			label: Liferay.Language.get('reassign-task'),
			onClick: () => handleClick('bulkReassign', 'singleReassign'),
		},
		updateDueDateItem,
	];

	if (transitions.length > 0) {
		const transitionItems = [
			{
				type: 'divider',
			},
			{
				items: transitions.map(({label, name}) => ({
					label,
					name,
					onClick: () => {
						openModal('singleTransition');
						setSelectedItems([instance]);
						setSingleTransition({
							title: label,
							transitionName: name,
						});
					},
				})),
				label: transitionLabel,
				name: transitionLabel,
				type: 'group',
			},
		];

		kebabItems.push(...transitionItems);
	}
	else if (transitions.length === 0 && taskNames.length > 1) {
		kebabItems.splice(
			1,
			1,
			{
				label: transitionLabel,
				onClick: () => {
					setSelectedItems([instance]);
					openModal('bulkTransition');
				},
			},
			updateDueDateItem
		);
	}

	return (
		<ClayLayout.ContentCol>
			<QuickActionKebab disabled={disabled} items={kebabItems} />
		</ClayLayout.ContentCol>
	);
};

Item.QuickActionMenu = QuickActionMenu;

export {Item};
