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
import {Outlet, useLocation, useParams} from 'react-router-dom';

import {useFetch} from '../../../../../../hooks/useFetch';
import useHeader from '../../../../../../hooks/useHeader';
import i18n from '../../../../../../i18n';
import {testrayCaseResultRest} from '../../../../../../services/rest';
import useCaseResultActions from './useCaseResultActions';

const CaseResultOutlet = () => {
	const {actions} = useCaseResultActions();
	const {pathname} = useLocation();
	const {buildId, caseResultId, projectId, routineId} = useParams();

	const {
		data: caseResult,
		mutate: mutateCaseResult,
	} = useFetch(
		testrayCaseResultRest.getResource(caseResultId as string),
		(response) => testrayCaseResultRest.transformData(response)
	);

	const basePath = `/project/${projectId}/routines/${routineId}/build/${buildId}/case-result/${caseResultId}`;

	const {context, setHeaderActions, setHeading, setTabs} = useHeader({
		timeout: 300,
	});

	const maxHeads = context.heading.length >= 4;

	useEffect(() => {
		setHeaderActions({actions, item: caseResult, mutate: mutateCaseResult});
	}, [actions, caseResult, mutateCaseResult, setHeaderActions]);

	useEffect(() => {
		if (caseResult && !maxHeads) {
			setHeading(
				[
					{
						category: i18n.translate('case-result'),
						title: caseResult?.case?.name || '',
					},
				],
				true
			);
		}
	}, [caseResult, maxHeads, setHeading]);

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

	if (caseResult) {
		return <Outlet context={{caseResult, mutateCaseResult, projectId}} />;
	}

	return null;
};

export default CaseResultOutlet;
