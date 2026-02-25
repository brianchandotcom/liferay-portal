/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useCallback, useEffect, useRef, useState} from 'react';

import '../../../css/components/BulkActionsMonitor.scss';

import Badge from '@clayui/badge';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import DropDown from '@clayui/drop-down';
import ClayLink from '@clayui/link';
import classnames from 'classnames';
import {openToast} from 'frontend-js-components-web';

import AssetBulkActionTaskService from '../../common/services/AssetBulkActionTaskService';
import {
	IBulkActionTask,
	IBulkActionTaskStarter,
	IBulkActionTaskStarterDTO,
	IBulkActionTaskType,
} from '../../common/types/BulkActionTask';
import {START_TASK} from '../../common/utils/events';
import {displaySystemErrorToast} from '../../common/utils/toastUtil';
import BulkActionsMonitorItemList from './components/BulkActionsMonitorItemList';
import {BulkActionTaskStarter} from './services/BulkActionTaskStarter';
import {
	INTERVAL_TASK_POLLING_MS,
	TASK_REPORT_FDS_ID,
	URL_TASKS_REPORT,
} from './util/constants';
import {getBulkActionTaskMessage} from './util/notifications';

const FDS_EVENT_UPDATE_DISPLAY = 'fds-update-display';

function BulkActionsMonitor() {
	const [active, setActive] = useState<boolean>(false);
	const [dataSetLoading, setDataSetLoading] = useState(
		new Set([TASK_REPORT_FDS_ID])
	);
	const [processingTasks, setProcessingTask] = useState(0);
	const [taskContext, setTaskContext] = useState<Record<string, any>>(() => {
		try {
			return JSON.parse(
				sessionStorage.getItem('cms_bulk_action_context') || '{}'
			);
		}
		catch (error) {
			return {};
		}
	});
	const [tasks, setTasks] = useState<IBulkActionTask[]>([]);
	const [tasksLoading, setTasksLoading] = useState<boolean>(false);

	const intervalRef = useRef<ReturnType<typeof setInterval> | null>(null);
	const previousTasksRef = useRef<IBulkActionTask[]>([]);
	const processingTasksRef = useRef(0);

	const isFetchingRef = useRef(false);

	useEffect(() => {
		sessionStorage.setItem(
			'cms_bulk_action_context',
			JSON.stringify(taskContext)
		);
	}, [taskContext]);

	const getTasks = useCallback(async () => {
		if (isFetchingRef.current) {
			return;
		}

		isFetchingRef.current = true;
		setTasksLoading(true);

		try {
			const {data} = await AssetBulkActionTaskService.getTasks({
				pageSize: 5,
				sort: 'dateCreated:desc',
			});

			const newTasks = data?.items || [];

			newTasks.forEach((newTask) => {
				const oldTask = previousTasksRef.current.find(
					(t) => t.id === newTask.id
				);

				if (
					oldTask &&
					oldTask.executionStatus.key !== 'completed' &&
					newTask.executionStatus.key === 'completed'
				) {
					const context = taskContext[newTask.id] || {};
					const itemsCount =
						context.itemCount ||
						newTask.numberOfSuccessfulItems ||
						0;

					const message = getBulkActionTaskMessage(
						newTask.type,
						'success',
						{
							items: new Array(itemsCount),
							selectAll: context.selectAll || false,
						},
						context
					);

					if (message) {
						openToast({message, type: 'success'});
					}

					setTaskContext((prevContext) => {
						const newContext = {...prevContext};
						delete newContext[newTask.id];

						return newContext;
					});
				}
			});

			previousTasksRef.current = newTasks;
			setTasks(newTasks);
		}
		catch (error: any) {
			console.error(error);
		}
		finally {
			isFetchingRef.current = false;
			setTasksLoading(false);
		}
	}, [taskContext]);

	const onActiveChange = useCallback(
		(active: boolean) => {
			setActive(active);

			if (active) {
				getTasks();
			}
		},
		[getTasks, setActive]
	);

	const stopPolling = useCallback(() => {
		if (intervalRef.current) {
			clearInterval(intervalRef.current);

			intervalRef.current = null;
		}
	}, []);

	const pollProcessingTasks = useCallback(() => {
		if (intervalRef.current) {
			return;
		}

		const getProcessingTasks = async () => {
			try {
				const {data} = await AssetBulkActionTaskService.getTasks({
					filter: "executionStatus eq 'initial' or executionStatus eq 'started'",
				});

				const dataTotalCount = data?.totalCount || 0;

				if (!dataTotalCount) {
					stopPolling();
					getTasks();
				}

				if (dataTotalCount < processingTasksRef.current) {
					dataSetLoading.forEach((dataSetId) => {
						Liferay.fire(FDS_EVENT_UPDATE_DISPLAY, {
							id: dataSetId,
						});
					});

					if (dataTotalCount === 0) {
						setDataSetLoading(new Set([TASK_REPORT_FDS_ID]));
					}
				}

				processingTasksRef.current = dataTotalCount;
				setProcessingTask(dataTotalCount);
			}
			catch {
				stopPolling();
			}
		};

		getProcessingTasks();

		intervalRef.current = setInterval(
			getProcessingTasks,
			INTERVAL_TASK_POLLING_MS
		);
	}, [dataSetLoading, getTasks, stopPolling]);

	const postBulkAction = useCallback(
		async (
			bulkActionDTO: IBulkActionTaskStarterDTO<keyof IBulkActionTaskType>
		) => {
			const bulkAction: IBulkActionTaskStarter =
				new BulkActionTaskStarter(bulkActionDTO);

			try {
				const response = await AssetBulkActionTaskService.createTask(
					bulkAction.payload,
					bulkAction.postURL
				);

				if (response.data) {
					bulkAction.onCreateSuccess(response);

					const newTask = response.data as any;

					if (newTask) {
						const {additionalData, selectedData} = bulkActionDTO;
						let assetName: string;

						if (
							selectedData.items &&
							selectedData.items.length === 1
						) {
							assetName = selectedData.items[0].title;
						}

						setTaskContext((prevContext) => ({
							...prevContext,
							[newTask.id]: {
								assetName,
								itemCount: selectedData.items
									? selectedData.items.length
									: 0,
								selectAll: selectedData.selectAll,
								targetName: additionalData?.targetName,
							},
						}));
					}

					getTasks();

					setDataSetLoading((prevState) => {
						if (bulkActionDTO.dataSetId) {
							const newDataSet = new Set(prevState);
							newDataSet.add(bulkActionDTO.dataSetId);

							return newDataSet;
						}

						return prevState;
					});

					pollProcessingTasks();
				}

				if (response.error) {
					bulkAction.onCreateError(response);
				}
			}
			catch (error) {
				bulkAction.onCreateError(error as unknown as any);

				if (!bulkAction.overrideDefaultErrorToast) {
					displaySystemErrorToast();
				}
			}
		},
		[getTasks, pollProcessingTasks]
	);

	useEffect(() => {
		Liferay.on(START_TASK, postBulkAction);

		return () => {
			Liferay.detach(START_TASK, postBulkAction);

			stopPolling();
		};
	}, [postBulkAction, stopPolling]);

	useEffect(() => {
		getTasks();

		pollProcessingTasks();
	}, [getTasks, pollProcessingTasks]);

	return processingTasks || tasks.length ? (
		<DropDown
			active={active}
			onActiveChange={onActiveChange}
			trigger={
				<div>
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('task-status-toggle')}
						borderless
						className={classnames('task-status-toggle', {
							'task-status-toggle-show': !processingTasks,
						})}
						displayType="secondary"
						symbol="forms"
					/>

					{processingTasks > 0 ? (
						<ClayButton
							className={classnames(
								'task-status-toggle',
								'task-status-toggle-processing',
								'task-status-toggle-show',
								{
									'border-info text-3 text-info': !active,
									'btn-info text-3': active,
								}
							)}
							displayType="secondary"
						>
							<Badge
								className={classnames({
									'badge-info mr-2': !active,
									'badge-light mr-2': active,
								})}
								label={processingTasks}
							/>

							{processingTasks === 1
								? Liferay.Language.get('processing-task')
								: Liferay.Language.get('processing-tasks')}
						</ClayButton>
					) : null}
				</div>
			}
		>
			<>
				<BulkActionsMonitorItemList items={tasks} />

				{tasksLoading ? (
					<div className="task-status-loading">
						<span className="loading-animation text-secondary" />
					</div>
				) : null}

				<div className="p-1">
					<ClayLink
						block
						className="btn btn-block btn-secondary task-status-view-all text-3"
						displayType="secondary"
						href={URL_TASKS_REPORT}
					>
						{Liferay.Language.get('view-all-tasks')}
					</ClayLink>
				</div>
			</>
		</DropDown>
	) : null;
}

export default BulkActionsMonitor;
