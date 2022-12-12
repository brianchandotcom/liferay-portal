/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {Dispatch} from 'react';
import {useNavigate, useOutletContext, useParams} from 'react-router-dom';
import {KeyedMutator} from 'swr';

import FloatingBox from '../../../components/FloatingBox';
import ListView from '../../../components/ListView';
import StatusBadge from '../../../components/StatusBadge';
import {StatusBadgeType} from '../../../components/StatusBadge/StatusBadge';
import {ListViewTypes} from '../../../context/ListViewContext';
import useMutate from '../../../hooks/useMutate';
import i18n from '../../../i18n';
import {Liferay} from '../../../services/liferay';
import {
	TestraySubTask,
	TestraySubTaskCaseResult,
	testraySubTaskImpl,
} from '../../../services/rest';
import {testraySubtaskCaseResultImpl} from '../../../services/rest/TestraySubtaskCaseResults';
import {searchUtil} from '../../../util/search';
import {SubTaskStatuses} from '../../../util/statuses';

type OutletContext = {
	mutateSubtask: KeyedMutator<TestraySubTask>;
};

const SubtasksCaseResults = () => {
	const navigate = useNavigate();
	const {subtaskId, taskId} = useParams();
	const {updateItemFromList} = useMutate();
	const {mutateSubtask}: OutletContext = useOutletContext();

	const getFloatingBoxAlerts = (
		subtasksCaseResults: TestraySubTaskCaseResult[],
		selectRows: number[]
	) => {
		const alerts = [];
		const selectedRows = selectRows.map((rowId) =>
			subtasksCaseResults.find(({id}) => rowId === id)
		) as TestraySubTaskCaseResult[];

		if (subtasksCaseResults.length === selectRows.length) {
			alerts.push({
				text: i18n.translate(
					'you-cannot-split-all-case-results-from-a-subtask'
				),
			});
		}

		const subtaskStatusCheck = () => {
			const subtasksOpenStatus = selectedRows.filter(
				({subTask}) => subTask?.dueStatus?.key === SubTaskStatuses.OPEN
			);

			if (subtasksOpenStatus.length) {
				return [
					{
						text: i18n.sub(
							'subtask-x-must-be-in-analysis-to-be-used-in-a-split',
							subtasksOpenStatus[0].subTask?.name as string
						),
					},
				];
			}
		};

		const subtaskUserCheck = () => {
			const subtasksWithDifferentAssignedUsers = selectedRows.some(
				({subTask}) =>
					subTask?.user?.id?.toString() !==
					Liferay.ThemeDisplay.getUserId()
			);

			if (subtasksWithDifferentAssignedUsers) {
				const [{subTask}] = selectedRows;

				return [
					{
						text: i18n.sub(
							'subtask-x-must-be-assigned-to-you-to-be-user-in-a-split',
							subTask?.name ?? ''
						),
					},
				];
			}
		};

		const alreadyAssigned = subtaskUserCheck() || [];
		const statusOpen = subtaskStatusCheck() || [];

		return [...alerts, ...alreadyAssigned, ...statusOpen];
	};

	const onSplitSubtasks = async (
		dispatch: Dispatch<any>,
		mutate: KeyedMutator<TestraySubTaskCaseResult>,
		selectedCaseResults: TestraySubTaskCaseResult[]
	) => {
		const {currentSubtask, newSubtask} = await testraySubTaskImpl.split(
			selectedCaseResults,
			Number(subtaskId),
			Number(taskId)
		);

		mutateSubtask(currentSubtask);

		updateItemFromList(
			mutate,
			0,
			{},
			{
				revalidate: true,
			}
		);

		Liferay.Util.openToast({
			message: i18n.sub('x-tests-were-split-into-x-successfully-view-x', [
				selectedCaseResults.length.toString(),
				newSubtask.name,
				newSubtask.name,
			]),
			onClick: ({event}) => {
				const {target} = event;

				if (target?.id === 'testray-link') {
					navigate(`../../subtasks/${newSubtask.id}`);
				}
			},
		});

		dispatch({
			payload: [],
			type: ListViewTypes.SET_CHECKED_ROW,
		});
	};

	return (
		<ListView
			managementToolbarProps={{
				visible: false,
			}}
			resource={testraySubtaskCaseResultImpl.resource}
			tableProps={{
				columns: [
					{
						clickable: true,
						key: 'run',
						render: (
							_,
							testraySubTaskCaseResult: TestraySubTaskCaseResult
						) => testraySubTaskCaseResult?.caseResult?.run?.number,

						value: i18n.translate('run'),
					},
					{
						clickable: true,
						key: 'priority',
						render: (
							_,
							testraySubTaskCaseResult: TestraySubTaskCaseResult
						) =>
							testraySubTaskCaseResult.caseResult?.case?.priority,

						value: i18n.translate('priority'),
					},
					{
						clickable: true,
						key: 'component',
						render: (
							_,
							testraySubTaskCaseResult: TestraySubTaskCaseResult
						) =>
							testraySubTaskCaseResult.caseResult?.component?.team
								?.name,
						value: i18n.translate('team'),
					},
					{
						clickable: true,
						key: 'component',
						render: (
							_,
							testraySubTaskCaseResult: TestraySubTaskCaseResult
						) =>
							testraySubTaskCaseResult.caseResult?.component
								?.name,

						value: i18n.translate('component'),
					},
					{
						clickable: true,
						key: 'case',
						render: (
							_,
							testraySubTaskCaseResult: TestraySubTaskCaseResult
						) => testraySubTaskCaseResult.caseResult?.case?.name,

						size: 'md',
						value: i18n.translate('case'),
					},
					{key: 'issues', value: i18n.translate('issues')},
					{
						key: 'dueStatus',
						render: (
							_,
							testraySubTaskCaseResult: TestraySubTaskCaseResult
						) => (
							<StatusBadge
								type={
									testraySubTaskCaseResult.caseResult
										?.dueStatus.key as StatusBadgeType
								}
							>
								{
									testraySubTaskCaseResult.caseResult
										?.dueStatus.name
								}
							</StatusBadge>
						),

						value: i18n.translate('status'),
					},
				],
				navigateTo: ({caseResult}) =>
					`/project/${caseResult.build?.project.id}/routines/${caseResult.build?.routine.id}/build/${caseResult.build?.id}/case-result/${caseResult.id}`,
				rowSelectable: true,
				rowWrap: true,
			}}
			transformData={(response) =>
				testraySubtaskCaseResultImpl.transformDataFromList(response)
			}
			variables={{
				filter: searchUtil.eq('subtaskId', subtaskId as string),
			}}
		>
			{({items}, {dispatch, listViewContext: {selectedRows}, mutate}) => {
				const alerts = getFloatingBoxAlerts(items, selectedRows);

				const selectedCaseResults: TestraySubTaskCaseResult[] = selectedRows.map(
					(rowId) => items.find(({id}) => rowId === id)
				);

				return (
					<FloatingBox
						alerts={alerts}
						clearList={() =>
							dispatch({
								payload: [],
								type: ListViewTypes.SET_CHECKED_ROW,
							})
						}
						isVisible={!!selectedRows.length}
						onSubmit={() =>
							onSplitSubtasks(
								dispatch,
								mutate,
								selectedCaseResults
							)
						}
						primaryButtonProps={{
							disabled: !!alerts.length,
							title: i18n.translate('split-tests'),
						}}
						selectedCount={selectedRows.length}
						tooltipText={i18n.translate(
							'move-selected-tests-to-a-new-subtask'
						)}
					/>
				);
			}}
		</ListView>
	);
};

export default SubtasksCaseResults;
