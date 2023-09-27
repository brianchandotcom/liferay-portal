/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useOutletContext, useParams} from 'react-router-dom';

import JiraLink from '../../../components/JiraLink';
import Container from '../../../components/Layout/Container';
import QATable from '../../../components/Table/QATable';
import SearchBuilder from '../../../core/SearchBuilder';
import useIssuesFound from '../../../hooks/data/useIssuesFound';
import i18n from '../../../i18n';
import {TestrayCase} from '../../../services/rest';
import dayjs from '../../../util/date';
import useCaseResultActions from '../Routines/Builds/Inner/CaseResult/useCaseResultActions';
import CaseResultHistory from './CaseResultHistory';

type CaseOutlet = {
	testrayCase: TestrayCase;
};

const Case = () => {
	const {actions} = useCaseResultActions();
	const {projectId} = useParams();
	const {testrayCase}: CaseOutlet = useOutletContext();
	const issues = useIssuesFound({caseId: testrayCase.id});

	return (
		<>
			<Container collapsable title={i18n.translate('details')}>
				<QATable
					items={[
						{
							title: i18n.translate('type'),
							value: testrayCase.caseType?.name,
						},
						{
							title: i18n.translate('priority'),
							value: testrayCase.priority,
						},
						{
							title: i18n.translate('main-component'),
							value: testrayCase.component?.name,
						},
						{
							title: i18n.translate('description'),
							value: testrayCase.description,
						},
						{
							title: i18n.translate('estimated-duration'),
							value: testrayCase.estimatedDuration,
						},
						{
							title: i18n.translate('steps'),
							value: testrayCase.steps,
						},
						{
							title: i18n.translate('date-created'),
							value: dayjs(testrayCase.dateCreated).format('lll'),
						},
						{
							title: i18n.translate('date-modified'),
							value: dayjs(testrayCase.dateModified).format(
								'lll'
							),
						},
						{
							title: i18n.translate('all-issues-found'),
							value: issues.length ? (
								<JiraLink issue={issues} />
							) : (
								'-'
							),
						},
					]}
				/>
			</Container>

			<Container className="mt-3">
				<CaseResultHistory
					listViewProps={{
						variables: {
							filter: SearchBuilder.eq('caseId', testrayCase.id),
						},
					}}
					tableProps={{
						actions,
						navigateTo: ({build, id}) =>
							`/project/${projectId}/routines/${build?.routine?.id}/build/${build?.id}/case-result/${id}`,
					}}
				/>
			</Container>
		</>
	);
};

export default Case;
