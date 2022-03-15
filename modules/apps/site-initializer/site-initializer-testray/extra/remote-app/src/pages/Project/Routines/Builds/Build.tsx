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
import {getCases} from '../../../../graphql/queries';
import i18n from '../../../../i18n';
import {getStatusLabel} from '../../../../util/constants';

const Build = () => (
	<Container className="mt-4" title={i18n.translate('tests')}>
		<ListView
			query={getCases}
			tableProps={{
				columns: [
					{
						clickable: true,
						key: 'priority',
						value: i18n.translate('priority'),
					},
					{
						key: 'component',
						value: i18n.translate('component'),
					},
					{
						clickable: true,
						key: 'name',
						value: i18n.translate('case'),
					},
					{
						key: 'run',
						render: () => '01',
						value: i18n.translate('run'),
					},
					{
						key: 'assignee',
						render: (_: any, {caseResult}: any) =>
							caseResult?.assignedUserId ? (
								<Avatar />
							) : (
								<AssignToMe />
							),
						value: i18n.translate('assignee'),
					},
					{
						key: 'status',
						render: (_: any, {caseResult}: any) =>
							caseResult?.dueStatus && (
								<StatusBadge
									type={getStatusLabel(
										caseResult?.dueStatus
									)?.toLowerCase()}
								>
									{getStatusLabel(caseResult?.dueStatus)}
								</StatusBadge>
							),
						value: i18n.translate('status'),
					},
					{
						key: 'issues',
						value: i18n.translate('issues'),
					},
					{
						key: 'error',
						render: (_: any, {caseResult}: any) =>
							caseResult?.errors && (
								<Code>{caseResult.errors}</Code>
							),
						value: i18n.translate('errors'),
					},
				],
				navigateTo: ({id}) => `case-result/${id}`,
			}}
			transformData={(data) => data?.c?.cases}
		/>
	</Container>
);

export default Build;
