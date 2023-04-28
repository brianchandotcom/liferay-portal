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
import PageRenderer from '~/components/PageRenderer';

import {useFetch} from '../../../../../../hooks/useFetch';
import useHeader from '../../../../../../hooks/useHeader';
import i18n from '../../../../../../i18n';
import {
	TestrayBuild,
	TestrayCaseResult,
	TestrayProject,
	TestrayRoutine,
	liferayMessageBoardImpl,
	testrayCaseResultImpl,
} from '../../../../../../services/rest';
import useCaseResultActions from './useCaseResultActions';

type OutletContext = {
	testrayBuild: TestrayBuild;
	testrayProject: TestrayProject;
	testrayRoutine: TestrayRoutine;
};

const CaseResultOutlet = () => {
	const {actions} = useCaseResultActions();
	const {pathname} = useLocation();
	const {buildId, caseResultId, projectId, routineId} = useParams();
	const {
		testrayBuild,
		testrayProject,
		testrayRoutine,
	}: OutletContext = useOutletContext();

	const {
		data: testrayCaseResult,
		error,
		loading,
		mutate: mutateCaseResult,
	} = useFetch<TestrayCaseResult>(
		testrayCaseResultImpl.getResource(caseResultId as string),
		{
			transformData: (response) =>
				testrayCaseResultImpl.transformData(response),
		}
	);

	const {data: mbMessage} = useFetch(
		testrayCaseResult?.mbMessageId
			? liferayMessageBoardImpl.getMessagesIdURL(
					testrayCaseResult.mbMessageId
			  )
			: null
	);

	const basePath = `/project/${projectId}/routines/${routineId}/build/${buildId}/case-result/${caseResultId}`;

	const {setHeaderActions, setHeading, setTabs} = useHeader({
		timeout: 300,
	});

	useEffect(() => {
		setHeaderActions({
			actions,
			item: testrayCaseResult,
			mutate: mutateCaseResult,
		});
	}, [actions, testrayCaseResult, mutateCaseResult, setHeaderActions]);

	useEffect(() => {
		if (testrayCaseResult?.case?.name) {
			setHeading([
				{
					category: i18n.translate('project').toUpperCase(),
					path: `/project/${testrayProject.id}/routines`,
					title: testrayProject?.name,
				},
				{
					category: i18n.translate('routine').toUpperCase(),
					path: `/project/${testrayProject.id}/routines/${testrayRoutine.id}`,
					title: testrayRoutine?.name,
				},
				{
					category: i18n.translate('build').toUpperCase(),
					path: `/project/${testrayProject.id}/routines/${testrayRoutine.id}/build/${testrayBuild.id}`,
					title: testrayBuild?.name,
				},
				{
					category: i18n.translate('case-result'),
					title: testrayCaseResult?.case?.name || '',
				},
			]);
		}
	}, [
		setHeading,
		testrayProject,
		testrayRoutine,
		testrayBuild,
		testrayCaseResult,
	]);

	useEffect(() => {
		setTabs([
			{
				active: pathname === basePath,
				path: basePath,
				title: i18n.translate('result'),
			},
			{
				active: pathname !== basePath,
				path: `${basePath}/history`,
				title: i18n.translate('history'),
			},
		]);
	}, [basePath, pathname, setTabs]);

	return (
		<PageRenderer error={error} loading={loading}>
			<Outlet
				context={{
					actions: testrayCaseResult?.actions,
					caseResult: testrayCaseResult,
					mbMessage,
					mutateCaseResult,
					projectId,
				}}
			/>
		</PageRenderer>
	);
};

export default CaseResultOutlet;
