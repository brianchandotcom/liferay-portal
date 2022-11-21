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

import {useParams} from 'react-router-dom';

import FloatingBox from '../../../components/FloatingBox';
import ListView from '../../../components/ListView';
import StatusBadge from '../../../components/StatusBadge';
import {StatusBadgeType} from '../../../components/StatusBadge/StatusBadge';
import {ListViewTypes} from '../../../context/ListViewContext';
import i18n from '../../../i18n';
import {TestraySubTaskCaseResult} from '../../../services/rest';
import {testraySubtaskCaseResultImpl} from '../../../services/rest/TestraySubtaskCaseResults';
import {searchUtil} from '../../../util/search';

const SubtasksCaseResults = () => {
	const {subtaskId} = useParams();

	const getFloatingBoxAlerts = (
		subtasksCaseResults: TestraySubTaskCaseResult[],
		selectRows: number[]
	) => {
		const alerts = [];

		if (subtasksCaseResults.length === selectRows.length) {
			alerts.push({
				text: i18n.translate(
					'you-cannot-split-all-case-results-from-a-subtask'
				),
			});
		}

		return [...alerts];
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
			{({items}, {dispatch, listViewContext: {selectedRows}}) => {
				const alerts = getFloatingBoxAlerts(items, selectedRows);

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
