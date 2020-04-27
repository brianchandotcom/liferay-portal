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
import ClayTable from '@clayui/table';
import React, {useContext, useEffect, useState} from 'react';

import QuickActionKebab from '../../shared/components/quick-action-kebab/QuickActionKebab.es';
import moment from '../../shared/util/moment.es';
import {capitalize} from '../../shared/util/util.es';
import {AppContext} from '../AppContext.es';
import {InstanceListContext} from './InstanceListPageProvider.es';
import {ModalContext} from './modal/ModalProvider.es';

const getSLAStatusIcon = (slaStatus) => {
	const items = {
		OnTime: {
			bgColor: 'bg-success-light',
			iconColor: 'text-success',
			iconName: 'check-circle',
		},
		Overdue: {
			bgColor: 'bg-danger-light',
			iconColor: 'text-danger',
			iconName: 'exclamation-circle',
		},
		Untracked: {
			bgColor: 'bg-info-light',
			iconColor: 'text-info',
			iconName: 'hr',
		},
	};

	return items[slaStatus] || items.Untracked;
};

const Item = ({totalCount, ...instance}) => {
	const {userId} = useContext(AppContext);
	const {
		selectedItems = [],
		setInstanceId,
		setSelectAll,
		setSelectedItems,
	} = useContext(InstanceListContext);
	const {setVisibleModal} = useContext(ModalContext);

	const [checked, setChecked] = useState(false);

	const {
		assetTitle,
		assetType,
		assignees = [],
		completed,
		creator,
		dateCreated,
		id,
		slaStatus,
		taskNames = [Liferay.Language.get('not-available')],
	} = instance;

	useEffect(() => {
		setChecked(!!selectedItems.find((item) => item.id === id));
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedItems]);

	const assignedToUser = !!assignees.find(({id}) => id === Number(userId));
	const assigneeNames = assignees.map((user) => user.name).join(', ');
	const {reviewer} = assignees.find(({id}) => id === -1) || {};

	const disableCheckbox = (!assignedToUser && !reviewer) || completed;
	const slaStatusIcon = getSLAStatusIcon(slaStatus);

	const formattedAssignees = !completed
		? assigneeNames
		: Liferay.Language.get('not-available');

	const formattedTaskNames = !completed
		? taskNames.join(', ')
		: Liferay.Language.get('completed');

	const handleCheck = ({target}) => {
		setChecked(target.checked);

		const updatedItems = target.checked
			? [...selectedItems, instance]
			: selectedItems.filter((item) => item.id !== id);

		setSelectAll(totalCount > 0 && totalCount === updatedItems.length);
		setSelectedItems(updatedItems);
	};

	return (
		<ClayTable.Row
			className={checked ? 'table-active' : ''}
			data-testid="instanceRow"
		>
			<ClayTable.Cell>
				<div className="table-first-element-group">
					<ClayCheckbox
						checked={checked}
						data-testid="instanceCheckbox"
						disabled={disableCheckbox}
						onChange={handleCheck}
					/>

					<span
						className={`sticker sticker-sm ${slaStatusIcon.bgColor}`}
					>
						<span className="inline-item">
							<ClayIcon
								className={slaStatusIcon.iconColor}
								data-testid="statusIcon"
								symbol={slaStatusIcon.iconName}
							/>
						</span>
					</span>
				</div>
			</ClayTable.Cell>

			<ClayTable.Cell>
				<span
					className="link-text"
					data-testid="instanceIdLink"
					onClick={() => {
						setInstanceId(id);

						setVisibleModal('instanceDetails');
					}}
					tabIndex="-1"
				>
					<strong>{id}</strong>
				</span>
			</ClayTable.Cell>

			<ClayTable.Cell data-testid="assetInfoCell">
				{`${assetType}: ${assetTitle}`}
			</ClayTable.Cell>

			<ClayTable.Cell data-testid="taskNamesCell">
				{formattedTaskNames}
			</ClayTable.Cell>

			<ClayTable.Cell data-testid="assigneesCell">
				{formattedAssignees}
			</ClayTable.Cell>

			<ClayTable.Cell data-testid="creatorCell">
				{creator ? creator.name : ''}
			</ClayTable.Cell>

			<ClayTable.Cell data-testid="dateCreatedCell">
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
	const {setSingleTransition, setVisibleModal} = useContext(ModalContext);
	const {setSelectedItem, setSelectedItems} = useContext(InstanceListContext);
	const {transitions = [], taskNames = []} = instance;

	const handleClick = (bulkModal, singleModal) => {
		if (taskNames.length > 1) {
			setSelectedItems([instance]);
			setVisibleModal(bulkModal);
		}
		else {
			setSelectedItem(instance);
			setVisibleModal(singleModal);
		}
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
						setVisibleModal('singleTransition');
						setSelectedItem(instance);
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
					setVisibleModal('bulkTransition');
				},
			},
			updateDueDateItem
		);
	}

	return (
		<div className="autofit-col">
			<QuickActionKebab disabled={disabled} items={kebabItems} />
		</div>
	);
};

Item.QuickActionMenu = QuickActionMenu;

export {Item};
