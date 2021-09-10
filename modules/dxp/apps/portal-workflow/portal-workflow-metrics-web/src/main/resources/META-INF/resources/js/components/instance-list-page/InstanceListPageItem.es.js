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
import ClayModal, {useModal} from '@clayui/modal';
import ClayPopover from '@clayui/popover';
import ClayTable from '@clayui/table';
import WorkflowInstanceTracker from '@liferay/portal-workflow-instance-tracker-web/js/components/WorkflowInstanceTracker';
import React, {useContext, useState} from 'react';

import useDebounceCallback from '../../hooks/useDebounceCallback.es';
import QuickActionKebab from '../../shared/components/quick-action-kebab/QuickActionKebab.es';
import {remainingTimeFormat} from '../../shared/util/duration.es';
import moment from '../../shared/util/moment.es';
import {capitalize, getSLAStatusIconInfo} from '../../shared/util/util.es';
import {AppContext} from '../AppContext.es';
import {InstanceListContext} from './InstanceListPageProvider.es';
import {ModalContext} from './modal/ModalProvider.es';
function Item({totalCount, ...instance}) {
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

	const [showInstanceTrackerModal, setShowInstanceTrackerModal] = useState(
		false
	);

	const {observer} = useModal({
		onClose: () => {
			setShowInstanceTrackerModal(false);
		},
	});

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

	const slaStatusIconInfo = getSLAStatusIconInfo(slaStatus);

	return (
		<ClayTable.Row className={checked ? 'table-active' : ''}>
			<ClayTable.Cell>
				<div className="table-first-element-group">
					<ClayCheckbox
						checked={checked}
						disabled={disableCheckbox}
						onChange={handleCheck}
					/>

					<span
						className={`ml-2 sticker sticker-sm ${slaStatusIconInfo?.bgColor}`}
					>
						<span className="inline-item">
							<ClayIcon
								className={slaStatusIconInfo?.textColor}
								symbol={slaStatusIconInfo?.name}
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
				<DueDateSLAResults
					slaResults={slaResults}
					slaStatusIconInfo={slaStatusIconInfo}
				/>
			</ClayTable.Cell>

			<ClayTable.Cell
				className="bounded-column"
				data-tooltip-align="bottom"
				title={`${assetType}: ${assetTitle}`}
			>{`${assetType}: ${assetTitle}`}</ClayTable.Cell>

			<ClayTable.Cell
				className="bounded-column"
				data-tooltip-align="bottom"
				title={formattedTaskNames}
			>
				{formattedTaskNames}
			</ClayTable.Cell>

			<ClayTable.Cell
				className="bounded-column"
				data-tooltip-align="bottom"
				title={formattedAssignees}
			>
				{formattedAssignees}
			</ClayTable.Cell>

			<ClayTable.Cell
				className="bounded-column"
				data-tooltip-align="bottom"
				title={creator ? creator.name : ''}
			>
				{creator ? creator.name : ''}
			</ClayTable.Cell>

			<ClayTable.Cell>
				{moment
					.utc(dateCreated)
					.format(Liferay.Language.get('mmm-dd-yyyy-lt'))}
			</ClayTable.Cell>

			<ClayTable.Cell style={{paddingRight: '0rem'}}>
				<QuickActionMenu
					disabled={disableCheckbox}
					instance={instance}
					setShowInstanceTrackerModal={() =>
						setShowInstanceTrackerModal(true)
					}
				/>
			</ClayTable.Cell>

			{showInstanceTrackerModal && (
				<ClayModal observer={observer} size="full-screen">
					<ClayModal.Header>
						{Liferay.Language.get('track-workflow')}
					</ClayModal.Header>

					<ClayModal.Body>
						<WorkflowInstanceTracker workflowInstanceId={id} />
					</ClayModal.Body>
				</ClayModal>
			)}
		</ClayTable.Row>
	);
}

function QuickActionMenu({disabled, instance, setShowInstanceTrackerModal}) {
	const {openModal, setSingleTransition} = useContext(ModalContext);
	const {workflowInstanceTrackerEnabled} = useContext(AppContext);
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

	let kebabItems = [
		{
			icon: 'change',
			label: Liferay.Language.get('reassign-task'),
			onClick: () => handleClick('bulkReassign', 'singleReassign'),
		},
		updateDueDateItem,
	];

	if (workflowInstanceTrackerEnabled) {
		kebabItems = [
			...kebabItems,
			{
				label: Liferay.Language.get('track-workflow'),
				onClick: setShowInstanceTrackerModal,
			},
		];
	}

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
}

function DueDateSLAResults({slaResults, slaStatusIconInfo}) {
	const [popover, setPopover] = useState(false);

	const [showPopover, cancelShowPopover] = useDebounceCallback(
		() => setPopover(true),
		1000
	);

	const getDueDateFormatted = (dateOverdue, fullDatetime = false) => {
		if (!dateOverdue) {
			return '';
		}

		let format = '';

		const sameYear = dateOverdue.split('-')[0] == new Date().getFullYear();

		if (sameYear) {
			format = fullDatetime
				? Liferay.Language.get('mmm-dd-lt')
				: Liferay.Language.get('mmm-dd');
		}
		else {
			format = fullDatetime
				? Liferay.Language.get('mmm-dd-yyyy-lt')
				: Liferay.Language.get('mmm-dd-yyyy');
		}

		return moment.utc(dateOverdue).format(format);
	};

	const instanceSlaResults = slaResults.slice(0, 2).map((slaResult) => {
		const datetimeOverdueFormatted = getDueDateFormatted(
			slaResult.dateOverdue,
			true
		);

		const [durationText, onTimeText] = remainingTimeFormat(
			slaResult.onTime,
			slaResult.remainingTime,
			true
		);

		const textClass = slaResult.onTime ? 'text-success' : 'text-danger';

		return {
			...slaResult,
			datetimeOverdueFormatted,
			durationText,
			onTimeText,
			textClass,
		};
	});

	const slaResultDateOverdue = instanceSlaResults?.length
		? getDueDateFormatted(instanceSlaResults[0].dateOverdue)
		: '';

	return (
		<div
			className={`due-date ${
				instanceSlaResults?.length
					? slaStatusIconInfo?.textColor
					: 'text-info'
			}`}
		>
			{!instanceSlaResults?.length ? (
				'-'
			) : (
				<ClayPopover
					alignPosition="bottom-left"
					className="due-date-popover"
					header={Liferay.Language.get('due-date')}
					onMouseEnter={() => setPopover(true)}
					onMouseLeave={() => setPopover(false)}
					show={popover}
					trigger={
						<div
							onMouseOut={() => {
								cancelShowPopover();

								setPopover(false);
							}}
							onMouseOver={() => showPopover()}
						>
							<span className="due-date-badge"></span>
							{slaResultDateOverdue}
						</div>
					}
				>
					{instanceSlaResults.map((slaResult) => (
						<div key={`critical-sla-${slaResult.id}`}>
							<div>{slaResult.name}:</div>
							<div className={slaResult.textClass}>
								{slaResult.datetimeOverdueFormatted} (
								{slaResult.durationText} {slaResult.onTimeText})
							</div>
						</div>
					))}
				</ClayPopover>
			)}
		</div>
	);
}

Item.QuickActionMenu = QuickActionMenu;

export default Item;
