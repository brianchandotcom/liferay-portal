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

import {getCaseResult} from '../../../../../../graphql/queries';
import useHeader from '../../../../../../hooks/useHeader';
import i18n from '../../../../../../i18n';

const CaseResultOutlet = () => {
	const {pathname} = useLocation();
	const {buildId, caseResultId, projectId, routineId} = useParams();

	const {data} = useQuery(getCaseResult, {
		variables: {
			caseResultId,
		},
	});

	const caseResult = data?.caseResult;

	const basePath = `/project/${projectId}/routines/${routineId}/build/${buildId}/case-result/${caseResultId}`;

	const {setHeading, setTabs} = useHeader({shouldUpdate: false});

	useEffect(() => {
		if (caseResult) {
			setHeading(
				[
					{
						category: i18n.translate('case-result'),
						title: caseResult.case?.name,
					},
				],
				true
			);
		}
	}, [setHeading, caseResult]);

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

	if (caseResult) {
		return <Outlet context={{caseResult}} />;
	}

	return null;
};

export default CaseResultOutlet;
