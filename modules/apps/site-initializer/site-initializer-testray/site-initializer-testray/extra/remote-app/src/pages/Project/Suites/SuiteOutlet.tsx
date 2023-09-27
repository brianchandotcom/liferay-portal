/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';
import {Outlet, useOutletContext, useParams} from 'react-router-dom';
import PageRenderer from '~/components/PageRenderer';
import useSearchBuilder from '~/hooks/useSearchBuilder';

import {useFetch} from '../../../hooks/useFetch';
import useHeader from '../../../hooks/useHeader';
import i18n from '../../../i18n';
import {
	APIResponse,
	TestrayProject,
	TestraySuite,
	TestraySuiteCase,
	testraySuiteCaseImpl,
} from '../../../services/rest';
import useSuiteActions from './useSuiteActions';

const SuiteOutlet = () => {
	const {actions} = useSuiteActions({isHeaderActions: true});
	const {suiteId} = useParams();
	const {
		testrayProject,
	}: {testrayProject: TestrayProject} = useOutletContext();

	const {data: testraySuite, error, loading, mutate} = useFetch<TestraySuite>(
		`/suites/${suiteId}`
	);

	const suiteCaseFilter = useSearchBuilder({useURIEncode: false});

	const filter = suiteCaseFilter.eq('suiteId', suiteId as string).build();

	const {data: testraySuiteCase} = useFetch<APIResponse<TestraySuiteCase>>(
		testraySuiteCaseImpl.resource,
		{
			params: {
				fields: 'r_caseToSuitesCases_c_caseId',
				filter,
				pageSize: 100,
			},
		}
	);

	const suiteCasesItems = testraySuiteCase?.items.map(
		(item) => item.r_caseToSuitesCases_c_caseId
	);

	const {setHeaderActions, setHeading} = useHeader({
		timeout: 100,
	});

	useEffect(() => {
		setHeaderActions({actions, item: testraySuite, mutate});
	}, [actions, mutate, setHeaderActions, testraySuite]);

	useEffect(() => {
		if (testrayProject && testraySuite) {
			setHeading([
				{
					category: i18n.translate('project').toUpperCase(),
					path: `/project/${testrayProject.id}/suites`,
					title: testrayProject.name,
				},
				{
					category: i18n.translate('suite').toUpperCase(),
					path: `/project/${testrayProject.id}/suites/${testraySuite.id}`,
					title: testraySuite.name,
				},
			]);
		}
	}, [setHeading, testrayProject, testraySuite]);

	return (
		<PageRenderer error={error} loading={loading}>
			<Outlet
				context={{
					actions: testraySuite?.actions,
					mutateTestraySuite: mutate,
					suiteCasesItems,
					testrayProject,
					testraySuite,
				}}
			/>
		</PageRenderer>
	);
};

export default SuiteOutlet;
