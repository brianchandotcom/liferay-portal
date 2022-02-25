/* eslint-disable no-console */
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

import {AvatarGroup} from '../../components/Avatar';
import Container from '../../components/Layout/Container';
import ListView from '../../components/ListView/ListView';
import ProgressBar from '../../components/ProgressBar/';
import StatusBadge from '../../components/StatusBadge';
import {getTestrayTasks} from '../../graphql/queries/testrayTask';
import {TEST_STATUS_LABEL} from '../../util/constants';

const TestFlow = () => (
	<Container title="Tasks">
		<ListView
			query={getTestrayTasks}
			tableProps={{
				columns: [
					{
						clickable: true,
						key: 'dueStatus',
						render: (status: number) => (
							<StatusBadge type="failed">
								{TEST_STATUS_LABEL[status]}
							</StatusBadge>
						),
						value: 'Status',
					},
					{
						clickable: true,
						key: 'dueDate',
						render: (_, testrayTask) =>
							testrayTask?.testrayBuild?.dueDate,
						value: 'Start Date',
					},
					{clickable: true, key: 'name', size: 'sm', value: 'Task'},
					{
						clickable: true,
						key: 'projectName',
						render: (_, testrayTask) => {
							return testrayTask?.testrayBuild?.testrayProject
								?.name;
						},
						value: 'Project Name',
					},
					{
						clickable: true,
						key: 'routineName',
						render: (_, testrayTask) => {
							return testrayTask?.testrayBuild?.testrayRoutine
								?.name;
						},
						value: 'Routine Name',
					},
					{
						clickable: true,
						key: 'buildName',
						render: (_, testrayTask) => {
							return testrayTask?.testrayBuild?.name;
						},
						value: 'Build Name',
					},
					{
						key: 'score',
						render: (score: any) => {
							if (!score) {
								return;
							}

							const {incomplete, other, self} = score || {};

							const total = self + other + incomplete;
							const passed = self + other;

							return `${passed} / ${total}, ${Math.ceil(
								(passed * 100) / total
							)}%`;
						},
						value: 'Score',
					},
					{
						key: 'score',
						render: (score: any) =>
							score && <ProgressBar items={score} />,
						size: 'sm',
						value: 'Progress',
					},
					{
						key: 'assigned',
						render: (assigned: any) =>
							assigned && (
								<AvatarGroup
									assignedUsers={assigned}
									groupSize={3}
								/>
							),
						value: 'Assigned',
					},
				],
				navigateTo: (item) => `/testflow/${item.id}`,
			}}
			transformData={(data) => data?.testrayTasks || {}}
		/>
	</Container>
);

export default TestFlow;
