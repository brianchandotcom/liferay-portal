/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useParams} from 'react-router-dom';

import Container from '../../../components/Layout/Container';
import ListViewRest from '../../../components/ListView';
import ProgressBar from '../../../components/ProgressBar';
import SearchBuilder from '../../../core/SearchBuilder';
import i18n from '../../../i18n';
import {TestrayRoutine, testrayRoutineImpl} from '../../../services/rest';
import {getTimeFromNow} from '../../../util/date';
import useRoutineActions from './useRoutineActions';

const Routines = () => {
	const {actions, navigate} = useRoutineActions();
	const {projectId} = useParams();

	return (
		<Container>
			<ListViewRest
				initialContext={{
					columns: {
						inprogress: false,
						passed: false,
						total: false,
						untested: false,
					},
					columnsFixed: ['name'],
				}}
				managementToolbarProps={{
					addButton: () => navigate('create'),
					filterSchema: 'routines',
					title: i18n.translate('routines'),
				}}
				resource={testrayRoutineImpl.resource}
				tableProps={{
					actions,
					columns: [
						{
							clickable: true,
							key: 'name',
							size: 'md',
							sorteable: true,
							value: i18n.translate('routine'),
						},
						{
							clickable: true,
							key: 'dateCreated',
							render: (_, testrayRoutine: TestrayRoutine) =>
								testrayRoutine.builds[0]?.dateCreated
									? getTimeFromNow(
											testrayRoutine.builds[0]
												?.dateCreated
									  )
									: null,
							value: i18n.translate('execution-date'),
						},
						{
							clickable: true,
							key: 'failed',
							render: (_, testrayRoutine: TestrayRoutine) =>
								testrayRoutine.builds[0]?.caseResultFailed ?? 0,
							value: i18n.translate('failed'),
						},
						{
							clickable: true,
							key: 'blocked',
							render: (_, testrayRoutine: TestrayRoutine) =>
								testrayRoutine.builds[0]?.caseResultBlocked ??
								0,
							value: i18n.translate('blocked'),
						},
						{
							clickable: true,
							key: 'untested',
							render: (_, testrayRoutine: TestrayRoutine) =>
								testrayRoutine.builds[0]?.caseResultUntested ??
								0,
							value: i18n.translate('untested'),
						},
						{
							clickable: true,
							key: 'inprogress',
							render: (_, testrayRoutine: TestrayRoutine) =>
								testrayRoutine.builds[0]
									?.caseResultInProgress ?? 0,
							value: i18n.translate('in-progress'),
						},
						{
							clickable: true,
							key: 'passed',
							render: (_, testrayRoutine: TestrayRoutine) =>
								testrayRoutine.builds[0]?.caseResultPassed ?? 0,
							value: i18n.translate('passed'),
						},
						{
							clickable: true,
							key: 'testfix',
							render: (_, testrayRoutine: TestrayRoutine) =>
								testrayRoutine.builds[0]?.caseResultTestFix ??
								0,
							value: i18n.translate('test-fix'),
						},
						{
							clickable: true,
							key: 'total',
							render: (_, testrayRoutine: TestrayRoutine) =>
								[
									testrayRoutine.builds[0]?.caseResultBlocked,
									testrayRoutine.builds[0]?.caseResultFailed,
									testrayRoutine.builds[0]
										?.caseResultInProgress,
									testrayRoutine.builds[0]
										?.caseResultIncomplete,
									testrayRoutine.builds[0]?.caseResultPassed,
									testrayRoutine.builds[0]?.caseResultTestFix,
									testrayRoutine.builds[0]
										?.caseResultUntested,
								]
									.map((count) => (count ? Number(count) : 0))
									.reduce(
										(prevCount, currentCount) =>
											prevCount + currentCount
									),
							size: 'sm',
							value: i18n.translate('total'),
						},
						{
							clickable: true,
							key: 'metrics',
							render: (_, testrayRoutine: TestrayRoutine) => (
								<ProgressBar
									items={{
										blocked: Number(
											testrayRoutine.builds[0]
												?.caseResultBlocked
										),
										failed: Number(
											testrayRoutine.builds[0]
												?.caseResultFailed
										),
										incomplete: Number(
											testrayRoutine.builds[0]
												?.caseResultIncomplete
										),
										passed: Number(
											testrayRoutine.builds[0]
												?.caseResultPassed
										),
										test_fix: Number(
											testrayRoutine.builds[0]
												?.caseResultTestFix
										),
									}}
								/>
							),
							value: i18n.translate('metrics'),
							width: '300',
						},
					],
					navigateTo: ({id}) => id,
				}}
				transformData={(response) =>
					testrayRoutineImpl.transformDataFromList(response)
				}
				variables={{
					filter: SearchBuilder.eq('projectId', projectId as string),
				}}
			/>
		</Container>
	);
};

export default Routines;
