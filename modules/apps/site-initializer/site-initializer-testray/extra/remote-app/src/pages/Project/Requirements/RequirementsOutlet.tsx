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

import {useQuery} from '@apollo/client';
import {useEffect} from 'react';
import {
	Outlet,
	useLocation,
	useOutletContext,
	useParams,
} from 'react-router-dom';

import Loading from '../../../components/Loading';
import {
	TestrayProject,
	TestrayRequirement,
	getRequirement,
} from '../../../graphql/queries';
import {useHeader} from '../../../hooks';
import i18n from '../../../i18n';

const RequirementsOutlet = () => {
	const {
		testrayProject,
	}: {testrayProject: TestrayProject} = useOutletContext();
	const {caseId, projectId, requirementId} = useParams();
	const {pathname} = useLocation();
	const basePath = `/project/${projectId}/cases/${caseId}`;

	const {setHeading, setTabs} = useHeader({
		shouldUpdate: false,
		useTabs: [
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
		],
	});

	const {data, loading} = useQuery<{requirement: TestrayRequirement}>(
		getRequirement,
		{
			variables: {
				requirementId,
			},
		}
	);

	const testrayRequirement = data;

	useEffect(() => {
		if (testrayRequirement) {
			setTimeout(() => {
				setHeading([{title: testrayRequirement.requirement.key}], true);
			}, 0);
		}
	}, [setHeading, testrayRequirement]);

	useEffect(() => {
		setTabs([]);
	}, [setTabs]);

	useEffect(() => {
		if (testrayRequirement && testrayProject) {
			setTimeout(() => {
				setHeading([
					{
						category: i18n.translate('project').toUpperCase(),
						path: `/project/${testrayProject.id}/cases`,
						title: testrayProject.name,
					},
					{
						category: i18n.translate('case').toUpperCase(),
						title: testrayRequirement?.requirement.components,
					},
				]);
			}, 0);
		}
	}, [testrayProject, setHeading, testrayRequirement]);

	if (loading) {
		return <Loading />;
	}

	if (!testrayRequirement) {
		return null;
	}

	return <Outlet context={testrayRequirement} />;
};

export default RequirementsOutlet;
