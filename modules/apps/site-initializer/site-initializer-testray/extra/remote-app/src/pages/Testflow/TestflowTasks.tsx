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

import {Avatar, AvatarGroup} from '../../components/Avatar';
import Code from '../../components/Code';
import Container from '../../components/Layout/Container';
import ProgressBar from '../../components/ProgressBar';
import StatusBadge from '../../components/StatusBadge';
import Table from '../../components/Table';
import QATable from '../../components/Table/QATable';
import useHeader from '../../hooks/useHeader';
import {progress, routines, subtask, tasks} from '../../util/mock';

const TestFlowTasks: React.FC = () => {
	const {assigned} = routines[0];

	const {setHeading, setTabs} = useHeader();

	useEffect(() => {
		setTimeout(() => {
			setHeading([
				{
					category: 'TASK',
					title:
						' [master] ci:test:analytics-cloud - 987 - 2022-02-07[16:07:08] ',
				},
			]);

			setTabs([]);
		}, 0);
	}, [setHeading, setTabs]);

	return (
		<>
			<Container className="pb-6" title="Task Details">
				<div className="d-flex flex-wrap">
					<div className="col-4 col-lg-4 col-md-12 pb-5">
						<QATable
							items={[
								{
									title: 'Status',
									value: (
										<StatusBadge type="passed">
											Passed
										</StatusBadge>
									),
								},
								{
									title: 'Assigned Users',
									value: (
										<AvatarGroup
											assignedUsers={assigned}
											groupSize={3}
										/>
									),
								},
								{
									title: 'Created',
									value: '8 Hours ago',
								},
							]}
						/>
					</div>

					<div className="col-8 col-lg-8 col-md-12">
						<QATable
							items={[
								{
									title: 'Project name',
									value: 'Liferay portal 7.4',
								},
								{
									title: 'Routine Name',
									value: 'Liferay portal 7.4',
								},
								{
									title: 'Build Name',
									value:
										'EE Package Tester - 7.4.13.u7 - 3102 - 2022-02-02[23:27:48]',
								},
							]}
						/>

						<ProgressBar
							displayTotalCompleted={false}
							items={tasks[1]}
							legend
						/>
					</div>
				</div>
			</Container>

			<Container className="mt-3" title="Progress (Score) ">
				<div className="my-4">
					<ProgressBar items={progress[1]} legend />
				</div>
			</Container>

			<Container className="mt-3" title="Subtasks">
				<Table
					columns={[
						{
							clickable: true,
							key: 'name',
							value: 'Name',
						},
						{
							clickable: true,
							key: 'status',
							render: () => (
								<StatusBadge type="blocked">
									Blocked
								</StatusBadge>
							),

							value: 'Status',
						},
						{clickable: true, key: 'score', value: 'Score'},
						{clickable: true, key: 'tests', value: 'Tests'},
						{
							clickable: true,
							key: 'error',
							render: (value) => <Code>{value}</Code>,
							size: 'xl',
							value: 'Errors',
						},
						{
							clickable: true,
							key: 'assignee',
							render: (assignee: any) => (
								<Avatar
									displayName
									name={assignee[0].name}
									url={assignee[0].url}
								/>
							),
							size: 'sm',
							value: 'Assignee',
						},
					]}
					items={subtask}
					navigateTo={() => '/testflow/subtasks'}
				/>
			</Container>
		</>
	);
};

export default TestFlowTasks;
