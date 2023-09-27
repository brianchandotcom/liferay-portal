/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';
import {useParams, useSearchParams} from 'react-router-dom';
import Avatar from '~/components/Avatar';
import AssignToMe from '~/components/Avatar/AssignToMe/AssignToMe';
import Code from '~/components/Code';
import JiraLink from '~/components/JiraLink';
import Container from '~/components/Layout/Container';
import ListView from '~/components/ListView';
import StatusBadge from '~/components/StatusBadge';
import {StatusBadgeType} from '~/components/StatusBadge/StatusBadge';
import useMutate from '~/hooks/useMutate';
import useSearchBuilder from '~/hooks/useSearchBuilder';
import i18n from '~/i18n';
import {
	PickList,
	TestrayCaseResult,
	TestrayCaseResultIssue,
	testrayCaseResultImpl,
} from '~/services/rest';

import useBuildTestActions from './useBuildTestActions';

const Build = () => {
	const [searchParams] = useSearchParams();
	const {actions, form} = useBuildTestActions();
	const {buildId} = useParams();
	const {updateItemFromList} = useMutate();

	const runId = searchParams.get('runId');

	const filterInitialContext = useMemo(
		() => ({
			entries: [
				{
					label: i18n.translate('run'),
					name: 'runToCaseResult/number',
					value: runId as string,
				},
			],
			filter: {'runToCaseResult/id': runId as string},
		}),
		[runId]
	);

	const caseResultFilter = useSearchBuilder({useURIEncode: false});

	const filter = runId
		? caseResultFilter.eq('buildId', buildId as string).build()
		: caseResultFilter.eq('buildId', buildId as string).build();

	return (
		<Container className="mt-4">
			<ListView
				initialContext={{
					columns: {environment: false},
					filters: filterInitialContext,
				}}
				managementToolbarProps={{
					filterSchema: 'buildResults',
					title: i18n.translate('tests'),
				}}
				resource={testrayCaseResultImpl.resource}
				tableProps={{
					actions,
					columns: [
						{
							clickable: true,
							key: 'caseType',
							render: (
								_,
								{case: testrayCase}: TestrayCaseResult
							) => testrayCase?.caseType?.name,
							value: i18n.translate('case-type'),
						},
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
							clickable: true,
							key: 'team',
							render: (_, testrayCaseResult: TestrayCaseResult) =>
								testrayCaseResult.case?.component?.team?.name,
							value: i18n.translate('team'),
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
							size: 'xl',
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
							clickable: true,
							key: 'environment',
							render: (_, item: TestrayCaseResult) =>
								item?.run?.name,
							value: i18n.translate('environment'),
							width: '250',
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
											name={caseResult.user.name}
											size="sm"
											url={caseResult.user.image}
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
							truncate: false,
							value: i18n.translate('assignee'),
							width: '200',
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
							render: (issues: TestrayCaseResultIssue[]) =>
								issues.map((caseResultIssue, index) => (
									<JiraLink
										issue={caseResultIssue}
										key={index}
									/>
								)),
							value: i18n.translate('issues'),
						},
						{
							key: 'errors',
							render: (errors: string) =>
								errors && <Code>{errors}</Code>,
							size: 'xl',
							truncate: true,
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
