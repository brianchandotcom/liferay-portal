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
import {useParams} from 'react-router-dom';

import Avatar from '../../../components/Avatar';
import Code from '../../../components/Code';
import Container from '../../../components/Layout/Container';
import Loading from '../../../components/Loading';
import StatusBadge from '../../../components/StatusBadge';
import QATable from '../../../components/Table/QATable';
import {useFetch} from '../../../hooks/useFetch';
import useHeader from '../../../hooks/useHeader';
import i18n from '../../../i18n';
import {
	TestraySubTask,
	TestrayTask,
	testrayTaskImpl,
} from '../../../services/rest';
import {testraySubtaskImpl} from '../../../services/rest/TestraySubtask';
import {SUBTASK_STATUS} from '../../../util/constants';
import SubtasksCaseResults from './SubtaskCaseResults';

const Subtasks = () => {
	const {setHeading} = useHeader();
	const {subtaskId, taskId} = useParams();

	const {data: testraySubtaskData} = useFetch<TestraySubTask>(
		testraySubtaskImpl.getResource(subtaskId as string),
		(response) => testraySubtaskImpl.transformData(response)
	);
	const {data: testrayTaskData, loading} = useFetch<TestrayTask>(
		testrayTaskImpl.getResource(taskId as string),
		(response) => testrayTaskImpl.transformData(response)
	);

	useEffect(() => {
		setTimeout(() => {
			setHeading([
				{
					category: i18n.translate('task'),
					path: `/testflow/${taskId}`,
					title: `${testrayTaskData?.name}`,
				},
				{
					category: i18n.translate('subtask'),
					title: `${testraySubtaskData?.name}`,
				},
			]);
		});
	}, [
		setHeading,
		testraySubtaskData?.name,
		subtaskId,
		testrayTaskData?.name,
		taskId,
	]);

	if (loading || !testraySubtaskData) {
		return <Loading />;
	}

	return (
		<>
			<Container className="pb-6" title="Subtasks">
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
													testraySubtaskData?.dueStatus as number
												]?.color
											}
										>
											{
												(SUBTASK_STATUS as any)[
													testraySubtaskData?.dueStatus as number
												]?.label
											}
										</StatusBadge>
									),
								},
								{
									title: i18n.translate('assignee'),
									value: (
										<Avatar
											displayName
											name={`${testraySubtaskData?.r_userToSubtasks_user.givenName} ${testraySubtaskData?.r_userToSubtasks_user.additionalName}`}
										/>
									),
								},
								{
									title: i18n.translate('updated'),
									value: '6 Hours ago',
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
									value: `${testraySubtaskData?.score}`,
								},
								{
									title: i18n.translate('error'),
									value: (
										<Code>
											{`java.lang.Exception: Cookie
											expiration date is not 6 months
											ahead. The expected expiration date
											is:'2022-10-08T09' while the actual
											cookie has 'ERROR: Cookie not found,
											or script not executed as
											expected.'.`}
										</Code>
									),
								},
								{
									title: i18n.translate('merged-with'),
									value: 'ST-5, ST-6',
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
