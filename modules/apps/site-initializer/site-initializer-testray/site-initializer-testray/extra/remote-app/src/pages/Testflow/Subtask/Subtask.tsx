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

import {useEffect} from 'react';
import {useOutletContext, useParams} from 'react-router-dom';

import Avatar from '../../../components/Avatar';
import AssignToMe from '../../../components/Avatar/AssigneToMe';
import Code from '../../../components/Code';
import Container from '../../../components/Layout/Container';
import Loading from '../../../components/Loading';
import StatusBadge from '../../../components/StatusBadge';
import QATable from '../../../components/Table/QATable';
import {useFetch} from '../../../hooks/useFetch';
import useHeader from '../../../hooks/useHeader';
import i18n from '../../../i18n';
import {
	APIResponse,
	TestraySubTask,
	TestraySubTaskCasesResult,
	TestrayTask,
} from '../../../services/rest';
import {testraySubtaskImpl} from '../../../services/rest/TestraySubtask';
import {testraySubtaskCaseResultImpl} from '../../../services/rest/TestraySubtaskCaseResults';
import {SUBTASK_STATUS} from '../../../util/constants';
import {getTimeFromNow} from '../../../util/date';
import {searchUtil} from '../../../util/search';
import SubtasksCaseResults from './SubtaskCaseResults';
import SubtaskHeaderActions from './SubtaskHeaderActions';

type OutletContext = {
	testrayTask: TestrayTask;
};

const Subtasks = () => {
	const {setHeading} = useHeader();
	const {subtaskId} = useParams();
	const {testrayTask} = useOutletContext<OutletContext>();

	const {data: testraySubtask, mutate: mutateSubtask} = useFetch<
		TestraySubTask
	>(testraySubtaskImpl.getResource(subtaskId as string), (response) =>
		testraySubtaskImpl.transformData(response)
	);

	const {data: testrayCaseResultData, mutate: mutateCaseResult} = useFetch<
		APIResponse<TestraySubTaskCasesResult>
	>(
		`${testraySubtaskCaseResultImpl.resource}&filter=${searchUtil.eq(
			'subtaskId',
			subtaskId as string
		)}&pageSize=100`,
		(response) =>
			testraySubtaskCaseResultImpl.transformDataFromList(response)
	);

	const testrayCaseResults = testrayCaseResultData?.items || [];

	useEffect(() => {
		setTimeout(() => {
			setHeading([
				{
					category: i18n.translate('task'),
					path: `/testflow/${testrayTask.id}`,
					title: `${testrayTask?.name}`,
				},
				{
					category: i18n.translate('subtask'),
					title: `${testraySubtask?.name}`,
				},
			]);
		});
	}, [
		setHeading,
		testraySubtask?.name,
		subtaskId,
		testrayTask.id,
		testrayTask?.name,
	]);

	if (!testraySubtask) {
		return <Loading />;
	}

	return (
		<>
			<SubtaskHeaderActions
				caseResult={testrayCaseResults.map((caseResult) =>
					Number(caseResult.caseResult?.id)
				)}
				dueStatus={Number(testrayCaseResults[0]?.caseResult?.dueStatus)}
				mutateCaseResult={mutateCaseResult}
				mutateSubtask={mutateSubtask}
				subtask={testraySubtask}
			/>

			<Container className="pb-6" title={i18n.translate('subtasks')}>
				<div className="d-flex flex-wrap">
					<div className="col-4 col-lg-4 col-md-12">
						<QATable
							items={[
								{
									title: i18n.translate('status'),
									value: (
										<StatusBadge
											type={
												(SUBTASK_STATUS as any)[
													testraySubtask?.dueStatus as number
												]?.label
											}
										>
											{
												(SUBTASK_STATUS as any)[
													testraySubtask?.dueStatus as number
												]?.label
											}
										</StatusBadge>
									),
								},
								{
									title: i18n.translate('assignee'),
									value: testraySubtask.user ? (
										<Avatar
											displayName
											name={`${testraySubtask.user?.givenName} ${testraySubtask?.user?.additionalName}`}
										/>
									) : (
										<AssignToMe
											onClick={() =>
												testraySubtaskImpl
													.assignToMe(testraySubtask)
													.then(mutateSubtask)
											}
										/>
									),
								},
								{
									title: i18n.translate('updated'),
									value: getTimeFromNow(
										testraySubtask?.statusUpdateDate
									),
								},
								{
									title: i18n.translate('issue'),
									value: '-',
								},
								{
									title: i18n.translate('comment'),
									value: 'None',
								},
							]}
						/>
					</div>

					<div className="col-8 col-lg-8 col-md-12 pb-5">
						<QATable
							items={[
								{
									title: i18n.translate('score'),
									value: `${testraySubtask?.score}`,
								},
								{
									title: i18n.translate('error'),
									value: (
										<Code>
											{testrayCaseResults.length
												? testrayCaseResults[0]
														.caseResult?.errors
												: null}
										</Code>
									),
								},
							]}
						/>
					</div>
				</div>
			</Container>

			<Container className="mt-5" title={i18n.translate('tests')}>
				<SubtasksCaseResults />
			</Container>
		</>
	);
};

export default Subtasks;
