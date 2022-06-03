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
import ListView from '../../../components/ListView/ListView';
import QATable from '../../../components/Table/QATable';
import {
	APIResponse,
	TestrayCase,
	TestraySuite,
	TypePagination,
	getCases,
} from '../../../graphql/queries';
import {
	TestraySuiteCase,
	getSuiteCases,
} from '../../../graphql/queries/testraySuiteCase';
import i18n from '../../../i18n';
import dayjs from '../../../util/date';
import useSuiteCaseFilter, {getCaseParameters} from './useSuiteCaseFilter';
import useSuiteCasesActions from './useSuiteCasesActions';

const transformData = (
	data:
		| TypePagination<'cases', TestrayCase>
		| TypePagination<'suitescaseses', TestraySuiteCase>
): APIResponse<TestraySuiteCase> => {
	const response: any =
		(data as TypePagination<'cases', TestrayCase>)?.cases ||
		(data as TypePagination<'suitescaseses', TestraySuiteCase>)
			?.suitescaseses;

	let items: TestraySuiteCase[] = response?.items || [];

	if ((data as TypePagination<'cases', TestrayCase>)?.cases) {
		items = response.items.map((item: TestrayCase) => ({
			case: item,
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
					query={
						testraySuite.caseParameters ? getCases : getSuiteCases
					}
					tableProps={{
						actions: suiteCaseActions.actions,
						columns: [
							{
								key: 'priority',
								render: (_, suiteCase: TestraySuiteCase) =>
									suiteCase.case.priority,
								value: i18n.translate('priority'),
							},
							{
								key: 'component',
								render: (_, suiteCase: TestraySuiteCase) =>
									suiteCase.case.component?.name,
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
							`/project/${projectId}/cases/${suiteCase.case.id}`,
					}}
					transformData={transformData}
					variables={{
						filter: suiteCaseFilter,
					}}
				/>
			</Container>
		</>
	);
};

export default Suite;
