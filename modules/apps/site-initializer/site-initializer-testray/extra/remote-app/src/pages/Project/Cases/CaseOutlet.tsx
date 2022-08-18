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

import {useEffect} from 'react';
import {
	Outlet,
	useLocation,
	useOutletContext,
	useParams,
} from 'react-router-dom';

import {useFetch} from '../../../hooks/useFetch';
import useHeader from '../../../hooks/useHeader';
import i18n from '../../../i18n';
import {
	TestrayCase,
	TestrayProject,
	testrayCaseRest,
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
	const {data: testrayCase, mutate} = useFetch<TestrayCase>(
		testrayCaseRest.getResource(caseId as string),
		(response) => testrayCaseRest.transformData(response)
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

	if (testrayProject && testrayCase) {
		return (
			<Outlet
				context={{
					mutateTestrayCase: mutate,
					testrayCase,
					testrayProject,
				}}
			/>
		);
	}

	return null;
};

export default CaseOutlet;
