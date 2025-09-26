/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useCallback, useEffect, useRef, useState} from 'react';

import '../../../css/components/AssetTaskStatus.scss';

import Badge from '@clayui/badge';
import Button, {ClayButtonWithIcon} from '@clayui/button';
import DropDown from '@clayui/drop-down';
import ClayLink from '@clayui/link';
import classnames from 'classnames';
import {debounce} from 'frontend-js-web';

import AssetBulkActionTaskService from '../../common/services/AssetBulkActionTaskService';
import {
	IBulkActionTask,
	IBulkActionTaskStarter,
	IBulkActionTaskStarterDTO,
	IBulkActionTaskType,
} from '../../common/types/BulkActionTask';
import {START_TASK} from '../../common/utils/events';
import {displaySystemErrorToast} from '../../common/utils/toastUtil';
import TaskStatusDropdownItemList from './components/TaskStatusDropdownItemList';
import {BulkActionTaskStarter} from './services/BulkActionTaskStarter';
import {INTERVAL_TASK_POLLING_MS, URL_TASKS_REPORT} from './util/constants';

function TaskStatusManager({
	bulkActionTaskClassNameId: classNameId,
}: {
	bulkActionTaskClassNameId: number;
}) {
	const [active, setActive] = useState<boolean>(false);
	const [disabled, setDisabled] = useState(true);
	const [processingTasks, setProcessingTask] = useState(0);
	const [taskItems, setTaskItems] = useState<IBulkActionTask[]>([]);

	const intervalRef = useRef<ReturnType<typeof setInterval> | null>(null);

	const getTaskItems = useCallback(async () => {
		const {data} = await AssetBulkActionTaskService.getTasks({
			pageSize: 5,
			sort: 'dateCreated:desc',
		});

		if (data?.items?.length) {
			setDisabled(false);
		}

		setTaskItems(data?.items || []);
	}, [setDisabled, setTaskItems]);

	const stopPolling = useCallback(() => {
		if (intervalRef.current) {
			clearInterval(intervalRef.current);

			intervalRef.current = null;
		}
	}, [intervalRef]);

	const pollProcessingTasks = useCallback(() => {
		if (intervalRef.current) {
			return;
		}

		const getProcessingTasks = async () => {
			try {
				const {data} = await AssetBulkActionTaskService.getTasks({
					filter: `executionStatus eq 'STARTED'`,
				});

				if (!data?.totalCount) {
					stopPolling();
				}

				setProcessingTask(data?.totalCount || 0);
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
	}, [setProcessingTask, stopPolling]);

	const postBulkAction = useCallback(
		async (
			bulkActionDTO: IBulkActionTaskStarterDTO<keyof IBulkActionTaskType>
		) => {
			const bulkAction: IBulkActionTaskStarter =
				new BulkActionTaskStarter({
					classNameId,
					...bulkActionDTO,
				});

			try {
				const response = await AssetBulkActionTaskService.createTask(
					bulkAction.payload,
					bulkAction.postURL
				);

				if (response.data) {
					bulkAction.onCreateSuccess(response);

					setDisabled(false);

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
		[classNameId, pollProcessingTasks, setDisabled]
	);

	useEffect(() => {
		Liferay.on(START_TASK, postBulkAction);

		return () => {
			Liferay.detach(START_TASK, postBulkAction);

			stopPolling();
		};
	}, [postBulkAction, setDisabled, stopPolling]);

	useEffect(() => {
		pollProcessingTasks();
	}, [pollProcessingTasks]);

	useEffect(() => {
		if (disabled) {
			getTaskItems();
		}
	}, [disabled, getTaskItems]);

	const onActiveChange = useCallback(
		(active: boolean) => {
			setActive(active);

			if (active) {
				debounce(async () => await getTaskItems(), 500);
			}
		},
		[getTaskItems, setActive]
	);

	return (
		<div className="p-2">
			<DropDown
				active={active}
				onActiveChange={onActiveChange}
				trigger={
					processingTasks > 0 ? (
						<Button
							className={classnames({
								'btn-sm border-info text-info pb-1': !active,
								'btn-sm btn-info pb-1': active,
							})}
							displayType="secondary"
						>
							<Badge
								className={classnames({
									'mr-2 badge-info': !active,
									'mr-2 badge-light': active,
								})}
								label={processingTasks}
							/>

							{processingTasks === 1
								? Liferay.Language.get('processing-task')
								: Liferay.Language.get('processing-tasks')}
						</Button>
					) : (
						<ClayButtonWithIcon
							borderless
							disabled={disabled}
							displayType="unstyled"
							symbol="forms"
						/>
					)
				}
			>
				<>
					<TaskStatusDropdownItemList
						classNameId={classNameId}
						items={taskItems}
					/>

					<ClayLink
						block
						className="border-0 btn btn-block btn-secondary task-status-view-all text-3"
						displayType="secondary"
						href={URL_TASKS_REPORT}
					>
						{Liferay.Language.get('view-all-tasks')}
					</ClayLink>
				</>
			</DropDown>
		</div>
	);
}

export default TaskStatusManager;
