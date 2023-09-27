/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useOutletContext} from 'react-router-dom';
import SearchBuilder from '~/core/SearchBuilder';
import {TestrayBuild, testrayComponentImpl} from '~/services/rest';

import Container from '../../../../../components/Layout/Container';
import ListView from '../../../../../components/ListView';
import ProgressBar from '../../../../../components/ProgressBar';
import i18n from '../../../../../i18n';

type OutletContext = {
	testrayBuild: TestrayBuild;
};

const Components = () => {
	const {testrayBuild} = useOutletContext<OutletContext>();

	return (
		<Container className="mt-4">
			<ListView
				initialContext={{
					columns: {
						blocked: false,
						in_progress: false,
						test_fix: false,
						untested: false,
					},
					columnsFixed: ['name'],
				}}
				managementToolbarProps={{
					filterSchema: 'buildComponents',
					title: i18n.translate('component'),
				}}
				resource={`/components?filter=${SearchBuilder.eq(
					'componentToCaseResult/r_buildToCaseResult_c_buildId',
					testrayBuild.id
				)}`}
				tableProps={{
					columns: [
						{
							key: 'name',
							size: 'md',
							value: i18n.translate('component'),
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
							render: (_, testrayComponent) =>
								[
									testrayComponent?.caseResultBlocked,
									testrayComponent?.caseResultFailed,
									testrayComponent?.caseResultInProgress,
									testrayComponent?.caseResultIncomplete,
									testrayComponent?.caseResultPassed,
									testrayComponent?.caseResultTestFix,
									testrayComponent?.caseResultUntested,
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
							render: (_, testrayComponent) => (
								<ProgressBar
									items={{
										blocked:
											testrayComponent?.caseResultBlocked,
										failed:
											testrayComponent?.caseResultFailed,
										incomplete:
											testrayComponent?.caseResultIncomplete,
										passed:
											testrayComponent?.caseResultPassed,
										test_fix:
											testrayComponent?.caseResultTestFix,
									}}
								/>
							),
							value: i18n.translate('metrics'),
							width: '300',
						},
					],
				}}
				transformData={(response) =>
					testrayComponentImpl.transformDataFromList(response)
				}
			/>
		</Container>
	);
};

export default Components;
