/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {memo, useEffect, useState} from 'react';
import {Link, useOutletContext, useSearchParams} from 'react-router-dom';
import Container from '~/components/Layout/Container';
import ListView from '~/components/ListView';
import StatusBadge from '~/components/StatusBadge';
import {StatusBadgeType} from '~/components/StatusBadge/StatusBadge';
import useSearchBuilder from '~/hooks/useSearchBuilder';
import i18n from '~/i18n';
import {TestrayCase, TestrayCaseResult, TestrayRun} from '~/services/rest';
import {testrayCaseImpl} from '~/services/rest/TestrayCase';
import {CaseResultStatuses} from '~/util/statuses';

type RunStatusProps = {
	caseResults: TestrayCaseResult[];
	dueStatusApplied?: string | null;
	run: TestrayRun;
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

const RunStatus: React.FC<RunStatusProps> = ({
	caseResults,
	dueStatusApplied,
	run,
}) => {
	let caseResult = caseResults.find(
		(caseResult) =>
			caseResult?.runId === run.id &&
			(dueStatusApplied
				? caseResult.dueStatus.key === dueStatusApplied
				: true)
	);

	if (!caseResult && dueStatusApplied) {
		caseResult = {
			dueStatus: {key: dueStatusApplied, name: dueStatusApplied + '?'},
		} as TestrayCaseResult;
	}

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
	const [mount, setMount] = useState(true);

	const [routeSearchParams] = useSearchParams();
	let caseResultFilter = useSearchBuilder({useURIEncode: false});

	const {
		runs: [runA, runB],
	} = useOutletContext<CompareRunsOutlet>();

	const dueStatusA = routeSearchParams.get('dueStatusA');
	const dueStatusB = routeSearchParams.get('dueStatusB');

	const compareRunFilterInitialContext = {
		entries: [
			{
				label: i18n.translate('status'),
				name: 'dueStatus',
				value: dueStatusA as string,
			},
			{
				label: i18n.translate('status'),
				name: 'dueStatus',
				value: dueStatusB as string,
			},
		],
		filter: {},
	};

	if (dueStatusA && dueStatusB) {
		caseResultFilter = caseResultFilter
			.group('OPEN')
			.eq('caseToCaseResult/r_runToCaseResult_c_runId', runA.id)
			.and()
			.eq('caseToCaseResult/dueStatus', dueStatusA)
			.group('CLOSE')
			.and()
			.group('OPEN')
			.eq('caseToCaseResult/r_runToCaseResult_c_runId', runB.id)
			.and()
			.eq('caseToCaseResult/dueStatus', dueStatusB)
			.group('CLOSE');
	}
	else {
		caseResultFilter = caseResultFilter.in(
			'caseToCaseResult/r_runToCaseResult_c_runId',
			[runA.id, runB.id]
		);
	}

	useEffect(() => {
		if (dueStatusA && dueStatusB) {
			setMount(false);

			setTimeout(() => {
				setMount(true);
			}, 200);
		}
	}, [dueStatusA, dueStatusB]);

	const filter = caseResultFilter.build();

	return (
		<Container>
			{mount && (
				<ListView
					initialContext={{
						filters: compareRunFilterInitialContext,
						pageSize: 100,
					}}
					managementToolbarProps={{
						display: {columns: false},
						filterSchema: 'compareRunsCases',
					}}
					resource={`/${
						testrayCaseImpl.uri
					}?${searchParams.toString()}`}
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
								render: (
									_,
									data: TestrayCase & {rowIndex: number}
								) => (
									<RunStatusMemoized
										caseResults={
											data.caseResults as TestrayCaseResult[]
										}
										dueStatusApplied={dueStatusA}
										run={runA}
									/>
								),
								size: 'md',
								sorteable: true,
								value: i18n.sub('status-in-x', 'run-a'),
							},
							{
								key: 'dueStatus',
								render: (
									_,
									data: TestrayCase & {rowIndex: number}
								) => (
									<RunStatusMemoized
										caseResults={
											data.caseResults as TestrayCaseResult[]
										}
										dueStatusApplied={dueStatusB}
										run={runB}
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
						testrayCaseImpl.transformDataFromList(response)
					}
					variables={{
						filter,
					}}
				/>
			)}
		</Container>
	);
};
export default CompareRunsCases;
