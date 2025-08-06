/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropdown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClayTable from '@clayui/table';
import {createPortletURL, dateUtils} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import '../../../css/home/Home.scss';

import ClayTableCell from '@clayui/table/lib/Cell';

import ViewWorkflowTasksActions from './ViewWorkflowTasksActions';
import useFetchWorkflowTasks from './hooks/useFetchWorkflowTasks';
import {WorkflowTask} from './types/WorkflowTask';

export default function ViewWorkflowTasks({
	myWorkflowTasksURL,
}: {
	myWorkflowTasksURL: string;
}) {
	const filterItems = [
		{
			label: Liferay.Language.get('assigned-to-me'),
			value: 'assigned-to-me',
		},
		{
			label: Liferay.Language.get('assigned-to-my-roles'),
			value: 'assigned-to-my-roles',
		},
	];

	const [selectedItem, setSelectedItem] = useState({
		label: Liferay.Language.get('assigned-to-me'),
		value: 'assigned-to-me',
	});

	interface WorkflowTasksProps {
		items: WorkflowTask[];
		totalCount: number;
	}

	const [workflowTasks, setWorkflowTasks] = useState<WorkflowTasksProps>({
		items: [],
		totalCount: 0,
	});

	const initialPaginationValues = {
		delta: 8,
		page: 1,
	};

	const [delta, setDelta] = useState(initialPaginationValues.delta);
	const [page, setPage] = useState(initialPaginationValues.page);

	const handlePageChange = (newPage: number) => {
		setPage(newPage);
	};

	const handleDeltaChange = (newDelta: number) => {
		setDelta(newDelta);
		setPage(1);
	};

	const {fetchWorkflowTasks} = useFetchWorkflowTasks({
		delta,
		page,
		selectedItem,
		setWorkflowTasks,
	});

	useEffect(() => {
		fetchWorkflowTasks();

		return () => {
			setWorkflowTasks({items: [], totalCount: 0});
		};
	}, [delta, fetchWorkflowTasks, page, selectedItem.value]);

	return (
		<div className="d-flex flex-column home-fragment-boarder">
			<div className="align-items-center d-flex">
				<h3 className="mb-4 ml-3 mr-auto mt-3">
					{Liferay.Language.get('my-workflow-tasks')}
				</h3>

				<ClayDropdown
					className="filter-dropdown mb-4 mt-3"
					closeOnClick
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
					onClick={() => window.open(myWorkflowTasksURL, '_blank')}
				>
					<ClayIcon symbol="shortcut" />
				</ClayButton>
			</div>

			{workflowTasks.totalCount > 0 ? (
				<>
					<ClayTable
						borderedColumns={false}
						borderless
						tableVerticalAlignment="top"
					>
						<ClayTable.Body>
							{workflowTasks.items.map(
								(workflowTask: WorkflowTask, index) => {
									return (
										<ClayTable.Row key={index}>
											<ClayTableCell>
												<div className="c-mr-2 sticker sticker-circle sticker-lg sticker-secondary">
													<span className="inline-item">
														<img
															className="avatar img-fluid logo-img mw-100 rounded-circle"
															src={
																workflowTask.auditUserImageURL
																	? workflowTask.auditUserImageURL
																	: '/image/user_portrait?img_id=0'
															}
														/>
													</span>
												</div>
											</ClayTableCell>

											<ClayTable.Cell expanded>
												<p className="list-group-text text-3 text-dark">
													{`${workflowTask.auditUser} sent you `}

													<a
														className="home-link"
														href={createPortletURL(
															myWorkflowTasksURL,
															{
																mvcPath:
																	'/edit_workflow_task.jsp',
																workflowTaskId:
																	workflowTask.workflowTaskId,
															}
														).toString()}
													>
														{
															workflowTask.assetTitle
														}
													</a>

													{` for ${workflowTask.name} in the workflow.`}
												</p>

												<p className="text-3 text-secondary">
													{dateUtils.fromNow(
														new Date(
															workflowTask?.assignedDate
														)
													)}
												</p>
											</ClayTable.Cell>

											<ClayTable.Cell>
												<ViewWorkflowTasksActions
													filterType={
														selectedItem.value
													}
													loadData={
														fetchWorkflowTasks
													}
													workflowTask={workflowTask}
												/>
											</ClayTable.Cell>
										</ClayTable.Row>
									);
								}
							)}
						</ClayTable.Body>
					</ClayTable>

					<ClayPaginationBarWithBasicItems
						active={page}
						activeDelta={delta}
						ellipsisBuffer={3}
						onActiveChange={(newPage: number) =>
							handlePageChange(newPage)
						}
						onDeltaChange={(newDelta: number) =>
							handleDeltaChange(newDelta)
						}
						totalItems={workflowTasks.totalCount}
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
