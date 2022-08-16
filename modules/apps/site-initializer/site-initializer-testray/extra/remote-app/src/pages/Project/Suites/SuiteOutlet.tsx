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

import {useFetch} from '../../../hooks/useFetch';
import useHeader from '../../../hooks/useHeader';
import i18n from '../../../i18n';
import {TestrayProject, TestraySuite} from '../../../services/rest';
import useSuiteActions from './useSuiteActions';

const SuiteOutlet = () => {
	const {actions} = useSuiteActions({isHeaderActions: true});
	const {suiteId} = useParams();
	const {
		testrayProject,
	}: {testrayProject: TestrayProject} = useOutletContext();

	const {data: testraySuite, mutate} = useFetch<TestraySuite>(
		`/suites/${suiteId}`
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

	if (testrayProject && testraySuite) {
		return (
			<Outlet
				context={{
					mutateTestraySuite: mutate,
					testrayProject,
					testraySuite,
				}}
			/>
		);
	}

	return null;
};

export default SuiteOutlet;
