/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useParams} from 'react-router-dom';
import ProgressBar from '~/components/ProgressBar';

import Container from '../../../../../../components/Layout/Container';
import ListView from '../../../../../../components/ListView';
import SearchBuilder from '../../../../../../core/SearchBuilder';
import i18n from '../../../../../../i18n';
import {testrayRunImpl} from '../../../../../../services/rest';
import RunFormModal from './RunFormModal';
import useRunActions from './useRunActions';

const Runs = () => {
	const {actions, formModal} = useRunActions();
	const {buildId} = useParams();

	return (
		<Container className="mt-4">
			<ListView
				forceRefetch={formModal.forceRefetch}
				initialContext={{
					columns: {
						inprogress: false,
						passed: false,
						total: false,
						untested: false,
					},
					columnsFixed: ['number'],
				}}
				managementToolbarProps={{
					addButton: () => formModal.modal.open(),
					filterSchema: 'buildRuns',
					title: i18n.translate('runs'),
				}}
				resource="/runs"
				tableProps={{
					actions,
					columns: [
						{
							clickable: true,
							key: 'number',
							render: (number) =>
								number?.toString().padStart(2, '0'),
							value: i18n.translate('run'),
						},
						{
							clickable: true,
							key: 'applicationServer',
							value: i18n.translate('application-server'),
						},
						{
							clickable: true,
							key: 'browser',
							value: i18n.translate('browser'),
						},
						{
							clickable: true,
							key: 'database',
							value: i18n.translate('database'),
						},
						{
							clickable: true,
							key: 'javaJDK',
							value: 'javaJDK',
						},
						{
							clickable: true,
							key: 'operatingSystem',
							value: i18n.translate('operating-system'),
						},
						{
							clickable: true,
							key: 'caseResultFailed',
							value: i18n.translate('failed'),
						},
						{
							clickable: true,
							key: 'caseResultBlocked',
							value: i18n.translate('blocked'),
						},
						{
							clickable: true,
							key: 'caseResultsInProgress',
							value: i18n.translate('in-progress'),
						},
						{
							clickable: true,
							key: 'caseResultPassed',
							value: i18n.translate('passed'),
						},
						{
							clickable: true,
							key: 'caseResultTestFix',
							value: i18n.translate('test-fix'),
						},
						{
							clickable: true,
							key: 'total',
							render: (_, testrayRun) =>
								[
									testrayRun?.caseResultBlocked,
									testrayRun?.caseResultFailed,
									testrayRun?.caseResultInProgress,
									testrayRun?.caseResultIncomplete,
									testrayRun?.caseResultPassed,
									testrayRun?.caseResultTestFix,
									testrayRun?.caseResultUntested,
								].reduce(
									(prevCount, currentCount) =>
										prevCount + currentCount
								),
							size: 'sm',
							value: i18n.translate('total'),
						},
						{
							clickable: true,
							key: 'metrics',
							render: (_, testrayRun) => (
								<ProgressBar
									items={{
										blocked: testrayRun?.caseResultBlocked,
										failed: testrayRun?.caseResultFailed,
										incomplete:
											testrayRun?.caseResultIncomplete,
										passed: testrayRun?.caseResultPassed,
										test_fix: testrayRun?.caseResultTestFix,
									}}
								/>
							),
							value: i18n.translate('metrics'),
							width: '300',
						},
					],
					navigateTo: (run) => `..?runId=${run.id}`,
				}}
				transformData={(response) =>
					testrayRunImpl.transformDataFromList(response)
				}
				variables={{
					filter: SearchBuilder.eq('buildId', buildId as string),
				}}
			/>

			<RunFormModal modal={formModal.modal} />
		</Container>
	);
};

export default Runs;
