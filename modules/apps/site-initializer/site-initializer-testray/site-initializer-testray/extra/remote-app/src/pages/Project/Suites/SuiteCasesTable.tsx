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

import ListView from '~/components/ListView';
import i18n from '~/i18n';
import {
	APIResponse,
	TestrayCase,
	TestraySuite,
	TestraySuiteCase,
	testrayCaseImpl,
	testraySuiteCaseImpl,
} from '~/services/rest';

import useSuiteCaseFilter from './useSuiteCaseFilter';
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
	}
	else {
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
			suite: suiteCase.r_suiteToSuitesCases_c_suite,
		}));
	}

	return {
		...response,
		items,
	};
};

type suiteCasesTableProps = {isSmartSuite: boolean; testraySuite: TestraySuite};

const SuitesCasesTable: React.FC<suiteCasesTableProps> = ({
	isSmartSuite,
	testraySuite,
}) => {
	const suiteCaseFilter = useSuiteCaseFilter(testraySuite);
	const suiteCaseActions = useSuiteCasesActions({isSmartSuite});

	return (
		<ListView
			forceRefetch={suiteCaseActions.formModal.forceRefetch}
			managementToolbarProps={{visible: false}}
			resource={
				isSmartSuite
					? testrayCaseImpl.resource
					: testraySuiteCaseImpl.resource
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
					`/project/${suiteCase.case.project?.id}/cases/${suiteCase?.case?.id}`,
			}}
			transformData={transformData(isSmartSuite)}
			variables={{
				filter: suiteCaseFilter,
			}}
		/>
	);
};

export default SuitesCasesTable;
