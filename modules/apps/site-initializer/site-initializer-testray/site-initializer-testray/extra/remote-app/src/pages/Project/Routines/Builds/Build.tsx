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

import Avatar from '../../../../components/Avatar';
import AssignToMe from '../../../../components/Avatar/AssigneToMe';
import Code from '../../../../components/Code';
import Container from '../../../../components/Layout/Container';
import ListViewRest from '../../../../components/ListView';
import StatusBadge from '../../../../components/StatusBadge';
import {StatusBadgeType} from '../../../../components/StatusBadge/StatusBadge';
import useMutate from '../../../../hooks/useMutate';
import useRuns from '../../../../hooks/useRuns';
import i18n from '../../../../i18n';
import {filters} from '../../../../schema/filter';
import {
	PickList,
	TestrayCaseResult,
	testrayCaseResultImpl,
} from '../../../../services/rest';
import {SearchBuilder} from '../../../../util/search';
import useBuildTestActions from './useBuildTestActions';

const Build = () => {
	const {buildId} = useParams();
	const {updateItemFromList} = useMutate();
	const {actions, form} = useBuildTestActions();
	const {
		compareRuns: {runId},
		setRunId,
	} = useRuns();

	useEffect(() => {
		return () => setRunId(null);
	}, [setRunId]);

	const caseResultFilter = new SearchBuilder();

	const filter = runId
		? caseResultFilter
				.eq('buildId', buildId as string)
				.and()
				.eq('runId', runId)
				.build()
		: caseResultFilter.eq('buildId', buildId as string).build();

	return (
		<Container className="mt-4">
			<ListViewRest
				managementToolbarProps={{
					filterFields: filters.build.results as any,
					title: i18n.translate('tests'),
				}}
				resource={testrayCaseResultImpl.resource}
				tableProps={{
					actions,
					columns: [
						{
							clickable: true,
							key: 'priority',
							render: (
								_,
								{case: testrayCase}: TestrayCaseResult
							) => testrayCase?.priority,
							value: i18n.translate('priority'),
						},
						{
							key: 'component',
							render: (
								_,
								{case: testrayCase}: TestrayCaseResult
							) => testrayCase?.component?.name,
							value: i18n.translate('component'),
						},
						{
							clickable: true,
							key: 'name',
							render: (
								_,
								{case: testrayCase}: TestrayCaseResult
							) => testrayCase?.name,
							value: i18n.translate('case'),
						},
						{
							key: 'run',
							render: (_, caseResult: TestrayCaseResult) =>
								caseResult.run?.number
									?.toString()
									.padStart(2, '0'),
							value: i18n.translate('run'),
						},
						{
							key: 'user',
							render: (
								_: any,
								caseResult: TestrayCaseResult,
								mutate
							) => {
								if (caseResult?.user) {
									return (
										<Avatar
											className="text-capitalize"
											displayName
											name={`${caseResult.user.emailAddress
												.split('@')[0]
												.replace('.', ' ')}`}
											size="sm"
										/>
									);
								}

								return (
									<AssignToMe
										onClick={() =>
											testrayCaseResultImpl
												.assignToMe(caseResult)
												.then(() => {
													updateItemFromList(
														mutate,
														0,
														{},
														{
															revalidate: true,
														}
													);
												})
												.then(form.onSuccess)
												.catch(form.onError)
										}
									/>
								);
							},
							value: i18n.translate('assignee'),
						},
						{
							key: 'dueStatus',
							render: (dueStatus: PickList) => (
								<StatusBadge
									type={dueStatus.key as StatusBadgeType}
								>
									{dueStatus.name}
								</StatusBadge>
							),
							value: i18n.translate('status'),
						},
						{
							key: 'issues',
							size: 'lg',
							value: i18n.translate('issues'),
						},
						{
							key: 'errors',
							render: (errors: string) =>
								errors && (
									<Code>{errors.substring(0, 100)}...</Code>
								),
							size: 'xl',
							value: i18n.translate('errors'),
						},
						{
							key: 'comment',
							size: 'lg',
							value: i18n.translate('comment'),
						},
					],
					navigateTo: ({id}) => `case-result/${id}`,
					rowWrap: true,
				}}
				transformData={(response) =>
					testrayCaseResultImpl.transformDataFromList(response)
				}
				variables={{
					filter,
				}}
			/>
		</Container>
	);
};

export default Build;
