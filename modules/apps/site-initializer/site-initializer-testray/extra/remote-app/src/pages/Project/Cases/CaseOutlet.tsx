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

import {TestrayCase, getTestrayCase} from '../../../graphql/queries';
import useHeader from '../../../hooks/useHeader';
import i18n from '../../../i18n';

const CaseOutlet = () => {
	const {testrayProject}: any = useOutletContext();
	const {projectId, testrayCaseId} = useParams();
	const {pathname} = useLocation();
	const basePath = `/project/${projectId}/cases/${testrayCaseId}`;

	const {setHeading} = useHeader({
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

	const {data} = useQuery<{testrayCase: TestrayCase}>(getTestrayCase, {
		variables: {
			testrayCaseId,
		},
	});

	const testrayCase = data?.testrayCase;

	useEffect(() => {
		if (testrayCase && testrayProject) {
			setTimeout(() => {
				setHeading([
					{
						category: i18n.translate('project').toUpperCase(),
						path: `/project/${testrayProject.id}/cases`,
						title: testrayProject.name,
					},
					{
						category: i18n.translate('case').toUpperCase(),
						title: testrayCase.name,
					},
				]);
			}, 0);
		}
	}, [testrayProject, testrayCase, setHeading]);

	if (testrayCase) {
		return <Outlet context={{testrayCase}} />;
	}

	return null;
};

export default CaseOutlet;
