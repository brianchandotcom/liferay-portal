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

import {useOutletContext} from 'react-router-dom';

import {BoxItem} from '../../../components/Form/DualListBox';
import Container from '../../../components/Layout/Container';
import ListView from '../../../components/ListView';
import QATable from '../../../components/Table/QATable';
import i18n from '../../../i18n';
import {
	APIResponse,
	TestrayCase,
	TestraySuite,
	TestraySuiteCase,
	casesResource,
	suitesCasesResource,
} from '../../../services/rest';
import dayjs from '../../../util/date';
import useSuiteCaseFilter, {getCaseParameters} from './useSuiteCaseFilter';
import useSuiteCasesActions from './useSuiteCasesActions';

const transformData = (isSmartSuite: boolean) => (
	response: APIResponse<TestrayCase> | APIResponse<TestraySuiteCase>
): APIResponse<TestraySuiteCase> => {
	let items: TestraySuiteCase[] = (response?.items as any) || [];

	if (isSmartSuite) {
		items = (items as any[]).map((testrayCase) => ({
			...testrayCase,
			case: {
				...testrayCase,
				component: testrayCase.r_componentToCases_c_component,
			},
			id: testrayCase.id,
		}));
	} else {
		items = (items as any[]).map((suiteCase) => ({
			...suiteCase,
			case: suiteCase.r_caseToSuitesCases_c_case
				? {
						...suiteCase.r_caseToSuitesCases_c_case,
						component:
							suiteCase.r_caseToSuitesCases_c_case
								.r_componentToCases_c_component,
				  }
				: undefined,
			id: suiteCase.id,
			suite: suiteCase.r_suiteToSuitesCases_c_suite
				? {
						...suiteCase.r_suiteToSuitesCases_c_suite,
				  }
				: undefined,
		}));
	}

	return {
		...response,
		items,
	};
};

const Suite = () => {
	const {
		projectId,
		testraySuite,
	}: {projectId: number; testraySuite: TestraySuite} = useOutletContext();

	const isSmartSuite = !!testraySuite.caseParameters;

	const suiteCaseFilter = useSuiteCaseFilter(testraySuite);
	const suiteCaseActions = useSuiteCasesActions({isSmartSuite});
	const caseParameters = getCaseParameters(testraySuite);

	const getCaseParameterKey = (caseParameter: BoxItem[]) =>
		caseParameter?.map(({label}) => label).join(', ');

	return (
		<>
			<Container collapsable title={i18n.translate('details')}>
				<QATable
					items={[
						{
							title: i18n.translate('description'),
							value: testraySuite?.description,
						},
						{
							title: i18n.translate('create-date'),
							value: dayjs(testraySuite?.dateCreated).format(
								'lll'
							),
						},
						{
							title: i18n.translate('date-last-modified'),
							value: dayjs(testraySuite?.dateModified).format(
								'lll'
							),
						},
						{
							title: i18n.translate('created-by'),
							value: testraySuite.creator.name,
						},
					]}
				/>
			</Container>

			{testraySuite.caseParameters && (
				<Container
					className="mt-4"
					collapsable
					title={i18n.translate('case-parameters')}
				>
					<QATable
						items={[
							{
								title: i18n.translate('case-types'),
								value: getCaseParameterKey(
									caseParameters.testrayCaseTypes
								),
							},
							{
								title: i18n.translate('components'),
								value: getCaseParameterKey(
									caseParameters.testrayComponents
								),
							},
							{
								title: i18n.translate('subcomponents'),
								value: getCaseParameterKey(
									caseParameters.testraySubComponents
								),
							},
							{
								title: i18n.translate('priority'),
								value: getCaseParameterKey(
									caseParameters.testrayPriorities
								),
							},
							{
								title: i18n.translate('requirements'),
								value: getCaseParameterKey(
									caseParameters.testrayRequirements
								),
							},
						]}
					/>
				</Container>
			)}

			<Container className="mt-4">
				<ListView
					forceRefetch={suiteCaseActions.formModal.forceRefetch}
					managementToolbarProps={{visible: false}}
					resource={
						isSmartSuite ? casesResource : suitesCasesResource
					}
					tableProps={{
						actions: suiteCaseActions.actions,
						columns: [
							{
								key: 'priority',
								render: (_, suiteCase: TestraySuiteCase) =>
									suiteCase?.case?.priority,
								value: i18n.translate('priority'),
							},
							{
								key: 'component',
								render: (_, suiteCase: TestraySuiteCase) =>
									suiteCase?.case?.component?.name,
								value: i18n.translate('component'),
							},
							{
								clickable: true,
								key: 'name',
								render: (_, suiteCase: TestraySuiteCase) =>
									suiteCase.case?.name,
								size: 'lg',
								value: i18n.translate('case'),
							},
						],
						navigateTo: (suiteCase: TestraySuiteCase) =>
							`/project/${projectId}/cases/${suiteCase?.case?.id}`,
					}}
					transformData={transformData(isSmartSuite)}
					variables={{
						filter: suiteCaseFilter,
					}}
				/>
			</Container>
		</>
	);
};

export default Suite;
