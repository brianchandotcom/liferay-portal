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

import {useEffect} from 'react';
import {Link} from 'react-router-dom';

import {AvatarGroup} from '../../components/Avatar';
import Container from '../../components/Layout/Container';
import ProgressBar from '../../components/ProgressBar/';
import StatusBadge from '../../components/StatusBadge';
import Table from '../../components/Table';
import useHeader from '../../hooks/useHeader';
import {routines} from '../../util/mock';

const TestFlow = () => {
	const {setHeading} = useHeader();

	useEffect(() => {
		setHeading([{category: 'PROJECT', title: 'Testflow'}]);
	}, [setHeading]);

	return (
		<Container title="Tasks">
			<Table
				columns={[
					{
						clickable: true,
						key: 'status',
						render: (value: string) => {
							return (
								<Link
									className="text-decoration-none"
									to={`/testflow/${value
										.toLowerCase()
										.replace(' ', '_')}`}
								>
									<StatusBadge type="failed">
										Failed
									</StatusBadge>
								</Link>
							);
						},
						value: 'Status',
					},
					{
						clickable: true,
						key: 'startDate',
						value: 'Start Date',
					},
					{clickable: true, key: 'task', value: 'Task'},
					{
						clickable: true,
						key: 'projectName',
						value: 'Project Name',
					},
					{
						clickable: true,
						key: 'routineName',
						value: 'Routine Name',
					},
					{clickable: true, key: 'buildName', value: 'Build Name'},
					{
						key: 'score',
						render: ({incomplete, other, self}: any) => {
							const total = self + other + incomplete;
							const passed = self + other;

							return `${passed} / ${total}, ${Math.ceil(
								(passed * 100) / total
							)}%`;
						},
						value: 'Score',
					},
					{
						className: 'table-cell-expand',
						key: 'score',
						render: (score: any) => <ProgressBar items={score} />,
						value: 'Progress',
					},
					{
						key: 'assigned',
						render: (assigned: any) => (
							<AvatarGroup
								assignedUsers={assigned}
								groupSize={3}
							/>
						),
						value: 'Assigned',
					},
				]}
				items={routines}
				navigateTo={(item) => `/testflow/${item.id}`}
			/>
		</Container>
	);
};

export default TestFlow;
