/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';
import {
	Outlet,
	useLocation,
	useOutletContext,
	useParams,
} from 'react-router-dom';
import PageRenderer from '~/components/PageRenderer';

import {useFetch} from '../../../hooks/useFetch';
import useHeader from '../../../hooks/useHeader';
import i18n from '../../../i18n';
import {
	TestrayCase,
	TestrayProject,
	testrayCaseImpl,
} from '../../../services/rest';
import {isIncludingFormPage} from '../../../util';
import useCaseActions from './useCaseActions';

const CaseOutlet = () => {
	const {pathname} = useLocation();
	const {caseId, projectId, ...otherParams} = useParams();
	const basePath = `/project/${projectId}/cases/${caseId}`;
	const isFormPage = isIncludingFormPage(pathname);

	const {actions} = useCaseActions({isHeaderActions: true});
	const {
		testrayProject,
	}: {testrayProject: TestrayProject} = useOutletContext();

	const {data: testrayCase, error, loading, mutate} = useFetch<TestrayCase>(
		testrayCaseImpl.getResource(caseId as string),
		{
			transformData: (response) =>
				testrayCaseImpl.transformData(response),
		}
	);

	const hasOtherParams = !!Object.values(otherParams).length;

	const {setHeaderActions, setHeading, setTabs} = useHeader({
		shouldUpdate: !hasOtherParams,
		timeout: 100,
	});

	useEffect(() => {
		setHeaderActions({actions, item: testrayCase, mutate});
	}, [actions, mutate, setHeaderActions, testrayCase]);

	useEffect(() => {
		if (!isFormPage) {
			setTabs([
				{
					active: pathname === basePath,
					path: basePath,
					title: i18n.translate('case-details'),
				},
				{
					active: pathname === `${basePath}/requirements`,
					path: `${basePath}/requirements`,
					title: i18n.translate('requirements'),
				},
			]);
		}
	}, [basePath, isFormPage, pathname, setTabs]);

	useEffect(() => {
		if (testrayCase && testrayProject) {
			setHeading([
				{
					category: i18n.translate('project').toUpperCase(),
					path: `/project/${testrayProject.id}/cases`,
					title: testrayProject.name,
				},
				{
					category: i18n.translate('case').toUpperCase(),
					path: `/project/${testrayProject.id}/cases/${testrayCase.id}`,
					title: testrayCase.name,
				},
			]);
		}
	}, [setHeading, testrayProject, testrayCase]);

	return (
		<PageRenderer error={error} loading={loading}>
			<Outlet
				context={{
					actions: testrayCase?.actions,
					mutateTestrayCase: mutate,
					testrayCase,
					testrayProject,
				}}
			/>
		</PageRenderer>
	);
};

export default CaseOutlet;
