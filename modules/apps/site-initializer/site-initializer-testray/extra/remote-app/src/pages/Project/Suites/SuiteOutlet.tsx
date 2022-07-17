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
import {Outlet, useOutletContext, useParams} from 'react-router-dom';

import {useHeader} from '../../../hooks';
import {useFetch} from '../../../hooks/useFetch';
import i18n from '../../../i18n';
import {TestraySuite} from '../../../services/rest';

const SuiteOutlet = () => {
	const {projectId, suiteId} = useParams();
	const {testrayProject}: any = useOutletContext();
	const {setHeading} = useHeader();

	const {data: testraySuite} = useFetch<TestraySuite>(`/suites/${suiteId}`);

	useEffect(() => {
		if (testraySuite && testrayProject) {
			setTimeout(() => {
				setHeading([
					{
						category: i18n.translate('project').toUpperCase(),
						path: `/project/${testrayProject.id}/suites`,
						title: testrayProject.name,
					},
					{
						category: i18n.translate('suite').toUpperCase(),
						title: testraySuite.name,
					},
				]);
			});
		}
	}, [testraySuite, testrayProject, setHeading]);

	if (!testraySuite) {
		return null;
	}

	return <Outlet context={{projectId, testraySuite}} />;
};

export default SuiteOutlet;
