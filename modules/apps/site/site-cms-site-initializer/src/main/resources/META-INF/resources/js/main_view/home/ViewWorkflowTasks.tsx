/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropdown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {useModal} from '@clayui/modal';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {dateUtils} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {transitionWorkflowState} from '../../common/services/WorkflowService';

import '../../../css/workflow_tasks/WorkflowTasks.scss';
import {WorkflowTaskResponse} from '../../common/types/WorkflowTaskResponse';
import AssignToModal from './AssignToModal';
import UpdateDueDateModal from './UpdateDueDateModal';
import useFetchWorkflowTasks from './hooks/useFetchWorkflowTasks';

export default function ViewWorkflowTasks(workflowProps) {
	const filterItems = [
		{
			label: 'Assigned To Me',
			value: 'assigned-to-me',
		},
		{
			label: 'Assigned To My Roles',
			value: 'assigned-to-my-roles',
		},
	];

	const initialPaginationValues = {
		delta: 10,
		page: 1,
	};

	const [selectedItem, setSelectedItem] = useState({
		label: 'Assigned To Me',
		value: 'assigned-to-me',
	});

	const [workflowTasks, setWorkflowTasks] = useState<WorkflowTaskResponse[]>(
		[]
	);
	const [delta, setDelta] = useState(initialPaginationValues.delta);
	const [page, setPage] = useState(initialPaginationValues.page);
	const [totalCount, setTotalCount] = useState(0);
	const [selectedWorkflowTask, setSelectedWorkflowTask] =
		useState<WorkflowTaskResponse | null>(null);

	const handlePageChange = (newPage: number) => {
		setPage(newPage);
	};

	const handleDeltaChange = (newDelta: number) => {
		setDelta(newDelta);
		setPage(1);
	};

	const assignedToModalProps = useModal({
		onClose: () => setSelectedWorkflowTask(null),
	});

	const updateDueDateModalProps = useModal({
		onClose: () => setSelectedWorkflowTask(null),
	});

	const {fetchWorkflowTasks} = useFetchWorkflowTasks({
		delta,
		page,
		selectedItem,
		setTotalCount,
		setWorkflowTasks,
		workflowProps,
	});

	useEffect(() => {
		fetchWorkflowTasks();

		return () => {
			setTotalCount(0);
			setWorkflowTasks([]);
		};
	}, [delta, fetchWorkflowTasks, page, selectedItem.value]);

	return (
		<div className="d-flex flex-column workflow-tasks-view">
			<div className="align-items-center d-flex">
				<h3 className="mb-4 ml-3 mr-auto mt-3">
					{Liferay.Language.get('my-workflow-tasks')}
				</h3>

				<ClayDropdown
					className="filter-dropdown mb-4 mt-3"
					closeOnClickOutside
					hasLeftSymbols
					trigger={
						<ClayButton displayType="secondary" size="sm">
							<span>
								{selectedItem.label}

								<ClayIcon
									className="ml-2"
									symbol="caret-bottom"
								/>
							</span>
						</ClayButton>
					}
				>
					{filterItems.map((item) => (
						<ClayDropdown.Item
							active={item.value === selectedItem.value}
							key={item.value}
							onClick={() => {
								setSelectedItem(item);
							}}
							symbolLeft={
								item.value === selectedItem.value ? 'check' : ''
							}
						>
							{item.label}
						</ClayDropdown.Item>
					))}
				</ClayDropdown>

				<ClayButton
					borderless
					className="mb-4 mt-3"
					onClick={() =>
						window.open(workflowProps.myWorkflowTasksURL, '_blank')
					}
				>
					<ClayIcon symbol="shortcut" />
				</ClayButton>
			</div>

			{totalCount > 0 ? (
				<>
					{workflowTasks.map(
						(workflowTask: WorkflowTaskResponse, index) => (
							<li
								className={`${index % 2 === 0 ? 'bg-light' : ''} border-0 list-group-item list-group-item-flex`}
								key={workflowTask.id}
							>
								<div className="autofit-col">
									<div className="rounded-circle sticker sticker-secondary">
										<span className="inline-item">
											<img
												className="avatar img-fluid logo-img mw-100 rounded-circle"
												src="/image/user_portrait?img_id=0"
											/>
										</span>
									</div>
								</div>

								<div className="autofit-col autofit-col-expand mt-1">
									<p className="list-group-text text-3 text-dark">
										{`${workflowTask?.auditUser} sent you ${workflowTask?.objectReviewed?.assetTitle} 
									for ${workflowTask?.name} in the workflow.`}
									</p>

									<p className="text-3 text-secondary">
										{dateUtils.fromNow(
											new Date(workflowTask?.dateCreated)
										)}
									</p>
								</div>

								<div className="autofit-col">
									<ClayDropdown
										trigger={
											<ClayButton
												className="m-0"
												displayType="unstyled"
												size="sm"
											>
												<span>
													<ClayIcon symbol="ellipsis-v" />
												</span>
											</ClayButton>
										}
									>
										<ClayDropdown.Item
											onClick={async () => {
												try {
													const res =
														await transitionWorkflowState(
															{
																transitionName:
																	'approve',
																workflowTaskId:
																	Number(
																		workflowTask.id
																	),
															}
														);

													if (res.error) {
														throw res.error;
													}
													else {
														Liferay.Util.openToast({
															message:
																'Workflow task approved successfully.',
															type: 'success',
														});
														await fetchWorkflowTasks();
													}
												}
												catch (error) {
													Liferay.Util.openToast({
														message:
															'Error when approving workflow task.',
														type: 'danger',
													});
													console.error(error);
												}
											}}
										>
											{Liferay.Language.get('approve')}
										</ClayDropdown.Item>

										<ClayDropdown.Item
											onClick={async () => {
												try {
													const res =
														await transitionWorkflowState(
															{
																transitionName:
																	'reject',
																workflowTaskId:
																	Number(
																		workflowTask.id
																	),
															}
														);

													if (res.error) {
														throw res.error;
													}
													else {
														Liferay.Util.openToast({
															message:
																'Workflow task rejected successfully.',
															type: 'success',
														});
														await fetchWorkflowTasks();
													}
												}
												catch (error) {
													Liferay.Util.openToast({
														message:
															'Error when rejecting workflow task.',
														type: 'danger',
													});
													console.error(error);
												}
											}}
										>
											{Liferay.Language.get('reject')}
										</ClayDropdown.Item>

										<ClayDropdown.Item
											onClick={() => {
												try {
													setSelectedWorkflowTask(
														workflowTask
													);
													assignedToModalProps.onOpenChange(
														true
													);
												}
												catch (error) {
													console.error(error);
												}
											}}
										>
											{Liferay.Language.get(
												'assign-to-...'
											)}
										</ClayDropdown.Item>

										<ClayDropdown.Item
											onClick={() => {
												try {
													setSelectedWorkflowTask(
														workflowTask
													);
													updateDueDateModalProps.onOpenChange(
														true
													);
												}
												catch (error) {
													console.error(error);
												}
											}}
										>
											{Liferay.Language.get(
												'update-due-date'
											)}
										</ClayDropdown.Item>
									</ClayDropdown>
								</div>
							</li>
						)
					)}

					{selectedWorkflowTask && (
						<>
							<AssignToModal
								fetchWorkflowTasks={fetchWorkflowTasks}
								modalProps={{
									observer: assignedToModalProps.observer,
									onOpenChange:
										assignedToModalProps.onOpenChange,
									open: assignedToModalProps.open,
								}}
								workflowTaskId={Number(selectedWorkflowTask.id)}
							/>
							<UpdateDueDateModal
								dateDue={selectedWorkflowTask.dateDue}
								fetchWorkflowTasks={fetchWorkflowTasks}
								modalProps={{
									observer: updateDueDateModalProps.observer,
									onOpenChange:
										updateDueDateModalProps.onOpenChange,
									open: updateDueDateModalProps.open,
								}}
								workflowTaskId={Number(selectedWorkflowTask.id)}
							/>
						</>
					)}
					<ClayPaginationBarWithBasicItems
						active={page}
						activeDelta={delta}
						className="mt-3"
						ellipsisBuffer={3}
						ellipsisProps={{'aria-label': 'More', 'title': 'More'}}
						labels={{
							paginationResults: Liferay.Language.get(
								'showing-x-to-x-of-x-results'
							),
							perPageItems: Liferay.Language.get('x-items'),
							selectPerPageItems: Liferay.Language.get('x-items'),
						}}
						onActiveChange={(newPage: number) =>
							handlePageChange(newPage)
						}
						onDeltaChange={(newDelta: number) =>
							handleDeltaChange(newDelta)
						}
						totalItems={totalCount}
					/>
				</>
			) : (
				<>
					<div className="align-items-center d-flex flex-column my-8">
						<h3>{Liferay.Language.get('no-tasks')}</h3>

						<span>
							{Liferay.Language.get(
								'there-are-no-tasks-assigned-to-you'
							)}
						</span>
					</div>
				</>
			)}
		</div>
	);
}
