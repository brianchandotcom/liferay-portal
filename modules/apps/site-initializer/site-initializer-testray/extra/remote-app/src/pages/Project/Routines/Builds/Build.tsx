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

import {Avatar} from '../../../../components/Avatar';
import AssignToMe from '../../../../components/Avatar/AssigneToMe';
import Code from '../../../../components/Code';
import Container from '../../../../components/Layout/Container';
import ListView from '../../../../components/ListView/ListView';
import StatusBadge from '../../../../components/StatusBadge';
import {getTestrayCases} from '../../../../graphql/queries';
import {getStatusLabel} from '../../../../util/constants';

const Build = () => (
	<Container className="mt-4" title="Tests">
		<ListView
			query={getTestrayCases}
			tableProps={{
				columns: [
					{
						clickable: true,
						key: 'priority',
						value: 'Priority',
					},
					{
						key: 'component',
						value: 'Component',
					},
					{
						clickable: true,
						key: 'name',
						value: 'Case',
					},
					{
						key: 'run',
						render: () => '01',
						value: 'Run',
					},
					{
						key: 'assignee',
						render: (_: any, {testrayCaseResult}: any) =>
							testrayCaseResult?.assignedUserId ? (
								<Avatar />
							) : (
								<AssignToMe />
							),
						value: 'Assignee',
					},
					{
						key: 'status',
						render: (_: any, {testrayCaseResult}: any) =>
							testrayCaseResult?.dueStatus && (
								<StatusBadge
									type={getStatusLabel(
										testrayCaseResult?.dueStatus
									)?.toLowerCase()}
								>
									{getStatusLabel(
										testrayCaseResult?.dueStatus
									)}
								</StatusBadge>
							),
						value: 'Status',
					},
					{
						key: 'issues',
						value: 'Issues',
					},
					{
						key: 'error',
						render: (_: any, {testrayCaseResult}: any) =>
							testrayCaseResult?.errors && (
								<Code>{testrayCaseResult.errors}</Code>
							),
						value: 'Errors',
					},
				],
				navigateTo: (item) => `case-result/${item.testrayCaseId}`,
			}}
			transformData={(data) => data?.c?.testrayCases}
			variables={{}}
		/>
	</Container>
);

export default Build;
