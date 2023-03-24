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

import {memo} from 'react';
import {Link, useOutletContext, useParams} from 'react-router-dom';
import Container from '~/components/Layout/Container';
import ListView from '~/components/ListView';
import StatusBadge from '~/components/StatusBadge';
import {StatusBadgeType} from '~/components/StatusBadge/StatusBadge';
import useSearchBuilder from '~/hooks/useSearchBuilder';
import i18n from '~/i18n';
import {TestrayCase, TestrayCaseResult, TestrayRun} from '~/services/rest';
import {testrayCaseRest} from '~/services/rest/TestrayCase';
import {CaseResultStatuses} from '~/util/statuses';

type RunStatusProps = {
	caseResults: TestrayCaseResult[];
	runId: Number;
};

type CompareRunsOutlet = {
	runs: TestrayRun[];
};

const searchParams = new URLSearchParams();

searchParams.set(
	'fields',
	'priority,caseToCaseResult.r_runToCaseResult_c_runId,name,r_componentToCases_c_component.name,caseToCaseResult.dueStatus,caseToCaseResult.id'
);
searchParams.set(
	'nestedFields',
	'componentToCases,caseToCaseResult,runToCaseResult'
);
searchParams.set('nestedFieldsDepth', '2');

const RunStatus: React.FC<RunStatusProps> = ({caseResults, runId}) => {
	const {runs} = useOutletContext<CompareRunsOutlet>();

	const caseResult = caseResults.find(
		(caseResult) => caseResult?.runId === runId
	);

	const run = runs.find(({id}) => id === runId);

	const didNotRunStatusKey = caseResult
		? caseResult?.dueStatus.key
		: CaseResultStatuses.DID_NOT_RUN;

	const didNotRunStatusName = caseResult
		? caseResult?.dueStatus.name
		: CaseResultStatuses.DID_NOT_RUN;

	const LinkWrapper =
		didNotRunStatusKey === CaseResultStatuses.DID_NOT_RUN
			? ({children}: {children: React.ReactNode}) => <>{children}</>
			: Link;

	return (
		<StatusBadge type={didNotRunStatusKey as StatusBadgeType}>
			<LinkWrapper
				to={`/project/${run?.build?.project?.id}/routines/${run?.build?.routine?.id}/build/${run?.build?.id}/case-result/${caseResult?.id}`}
			>
				{didNotRunStatusName === CaseResultStatuses.DID_NOT_RUN
					? i18n.translate('dnr')
					: didNotRunStatusName}
			</LinkWrapper>
		</StatusBadge>
	);
};

const RunStatusMemoized = memo(RunStatus);

const CompareRunsCases = () => {
	const {runA, runB} = useParams();

	const caseResultFilter = useSearchBuilder({useURIEncode: false});

	const filter = caseResultFilter
		.in('caseToCaseResult/r_runToCaseResult_c_runId', [
			runA as string,
			runB as string,
		])
		.build();

	return (
		<Container>
			<ListView
				initialContext={{pageSize: 100}}
				managementToolbarProps={{
					display: {columns: false},
					filterSchema: 'compareRunsCases',
					title: i18n.translate('cases'),
				}}
				resource={`/${testrayCaseRest.uri}?${searchParams.toString()}`}
				tableProps={{
					columns: [
						{
							key: 'priority',
							sorteable: true,
							value: i18n.translate('priority'),
							width: '100',
						},
						{
							key: 'component',
							render: (component) => component?.name,
							value: i18n.translate('component'),
						},
						{
							key: 'name',
							size: 'xl',
							sorteable: true,
							value: i18n.translate('case'),
						},
						{
							key: 'dueStatus',
							render: (_, data: TestrayCase) => (
								<RunStatusMemoized
									caseResults={
										data.caseResults as TestrayCaseResult[]
									}
									runId={Number(runA)}
								/>
							),
							size: 'md',
							sorteable: true,
							value: i18n.sub('status-in-x', 'run-a'),
						},
						{
							key: 'dueStatus',
							render: (_, data: TestrayCase) => (
								<RunStatusMemoized
									caseResults={
										data.caseResults as TestrayCaseResult[]
									}
									runId={Number(runB)}
								/>
							),
							size: 'md',
							sorteable: true,
							value: i18n.sub('status-in-x', 'run-b'),
						},
					],
					rowWrap: true,
				}}
				transformData={(response) =>
					testrayCaseRest.transformDataFromList(response)
				}
				variables={{
					filter,
				}}
			/>
		</Container>
	);
};
export default CompareRunsCases;
