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
import {Outlet, useLocation, useParams} from 'react-router-dom';

import {getCase} from '../../../../../../graphql/queries';
import useHeader from '../../../../../../hooks/useHeader';
import i18n from '../../../../../../i18n';

const CaseResultOutlet = () => {
	const {pathname} = useLocation();
	const {
		projectId,
		routineId,
		testrayBuildId,
		testrayCaseResultId,
	} = useParams();

	const {data} = useQuery(getCase, {
		variables: {
			caseId: testrayCaseResultId,
		},
	});

	const testrayCaseResult = data?.c?.Case;

	const basePath = `/project/${projectId}/routines/${routineId}/build/${testrayBuildId}/case-result/${testrayCaseResultId}`;

	const {setHeading, setTabs} = useHeader({shouldUpdate: false});

	useEffect(() => {
		if (testrayCaseResult) {
			setHeading(
				[
					{
						category: i18n.translate('case-result'),
						title: testrayCaseResult.name,
					},
				],
				true
			);
		}
	}, [setHeading, testrayCaseResult]);

	useEffect(() => {
		setTimeout(() => {
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
		}, 0);
	}, [basePath, pathname, setTabs]);

	if (testrayCaseResult) {
		return <Outlet context={{testrayCaseResult}} />;
	}

	return null;
};

export default CaseResultOutlet;
