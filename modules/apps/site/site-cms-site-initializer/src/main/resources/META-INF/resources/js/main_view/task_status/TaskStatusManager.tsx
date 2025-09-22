/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useCallback, useEffect, useRef, useState} from 'react';

import '../../../css/components/AssetTaskStatus.scss';

import {sub} from 'frontend-js-web';

import {START_TASK} from '../../common/utils/events';
import {
	ActionId,
	BulkActionDataDTO,
	IBulkActionSelectedData,
	IBulkActionTaskItem,
} from './TaskStatusType';
import TaskStatusDropdown from './components/TaskStatusDropdown';
import BulkActionService from './services/BulkActionService';
import {TASK_ITEMS_REPORT_URL} from './util';
import generateUrlParams from './util/GenerateUrlParams';
import handleMessageAndName from './util/HandleMessageAndName';

function TaskStatusManager({
	bulkActionTaskClassNameId,
}: {
	bulkActionTaskClassNameId: number;
}) {
	const [active, setActive] = useState<boolean>(false);
	const [disable, setDisable] = useState(true);
	const intervalRef = React.useRef<ReturnType<typeof setInterval> | null>(
		null
	);
	const [processingTasks, setProcessingTask] = useState(0);
	const [taskItems, setTaskItems] = useState<IBulkActionTaskItem[]>([]);
	const updateOpenDropdownRef = useRef(false);

	const getTaskReportLink = (
		bulkActionTaskClassNameId: number,
		taskId: number
	) => {
		return `<a href="${TASK_ITEMS_REPORT_URL}${bulkActionTaskClassNameId}/${taskId}" class="alert-link lead"><strong>${Liferay.Language.get('task-report')}</strong></a>`;
	};

	const getTaskItems = useCallback(async () => {
		const tasks = await BulkActionService.getTasks({
			pageSize: 5,
			sort: 'dateCreated:desc',
		});

		if (tasks?.items?.length) {
			setDisable(false);
		}

		setTaskItems(tasks?.items || []);
	}, [setDisable, setTaskItems]);

	const stopPolling = useCallback(() => {
		if (intervalRef.current) {
			clearInterval(intervalRef.current);
			intervalRef.current = null;
		}
	}, []);

	const retryStrategy = useCallback(() => {
		const TIMEOUT = 10000;

		if (intervalRef.current) {
			return;
		}

		const pollingTasks = async () => {
			try {
				const tasks = await BulkActionService.getTasks({
					filter: `executionStatus eq 'STARTED'`,
				});

				if (tasks?.totalCount === 0) {
					stopPolling();
				}

				if (updateOpenDropdownRef.current) {
					getTaskItems();
				}
				setProcessingTask(tasks?.totalCount || 0);
			}
			catch {
				stopPolling();
			}
		};

		pollingTasks();
		intervalRef.current = setInterval(pollingTasks, TIMEOUT);
	}, [getTaskItems, setProcessingTask, stopPolling]);

	const postBulkAction = useCallback(
		async ({
			actionId,
			data,
			selectedData,
			...otherProps
		}: {
			actionId: ActionId;
			data: BulkActionDataDTO;
			otherProps: any;
			selectedData: IBulkActionSelectedData;
		}) => {
			try {
				const urlParams = generateUrlParams(selectedData, otherProps);

				const response = await BulkActionService.createTask(
					actionId,
					selectedData,
					data,
					urlParams
				);

				const {infoMessage} = handleMessageAndName(
					actionId,
					selectedData
				);

				if (response.data) {
					Liferay.Util.openToast({
						message: sub(infoMessage, [
							selectedData.items.length,
							getTaskReportLink(
								bulkActionTaskClassNameId,
								response.data.id
							),
						]),
						type: 'info',
					});

					setDisable(false);
					retryStrategy();
				}

				if (response.error) {
					Liferay.Util.openToast({
						message: Liferay.Language.get(
							'an-unexpected-system-error-occurred'
						),
						type: 'danger',
					});
				}
			}
			catch {
				Liferay.Util.openToast({
					message: Liferay.Language.get(
						'an-unexpected-system-error-occurred'
					),
					type: 'danger',
				});
			}
		},
		[bulkActionTaskClassNameId, retryStrategy, setDisable]
	);

	useEffect(() => {
		function post({
			actionId,
			data,
			selectedData,
			...otherProps
		}: {
			actionId: ActionId;
			data: BulkActionDataDTO;
			otherProps: any;
			selectedData: IBulkActionSelectedData;
		}) {
			if (actionId) {
				return postBulkAction({
					actionId,
					data,
					selectedData,
					...otherProps,
				});
			}
			else {
				setDisable(false);
			}
		}

		Liferay.on(START_TASK, post);

		return () => {
			Liferay.detach(START_TASK, post);
			stopPolling();
		};
	}, [postBulkAction, setDisable, stopPolling]);

	useEffect(() => {
		retryStrategy();
	}, [retryStrategy]);

	useEffect(() => {
		if (disable) {
			getTaskItems();
		}
	}, [disable, getTaskItems]);

	useEffect(() => {
		updateOpenDropdownRef.current = active;
	}, [active]);

	return (
		<TaskStatusDropdown
			active={active}
			disable={disable}
			getTaskItems={getTaskItems}
			processingTasks={processingTasks}
			setActive={setActive}
			taskClassNameId={bulkActionTaskClassNameId}
			taskItems={taskItems}
		/>
	);
}

export default TaskStatusManager;
