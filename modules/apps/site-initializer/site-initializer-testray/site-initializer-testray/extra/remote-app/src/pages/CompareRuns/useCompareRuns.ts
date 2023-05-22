/**
import { useFetch } from '../../hooks/useFetch';
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

import {useParams} from 'react-router-dom';
import {useFetch} from '~/hooks/useFetch';
import {TestrayComponent, TestrayTeam} from '~/services/rest';

export type CompareRunsResponse = {
	component?: TestrayComponent;
	team?: TestrayTeam;
	values: number[][];
};

const mockComponent = {
	dateCreated: '',
	dateModified: '',
	externalReferenceCode: '',
	id: 0,
	name: 'Liferay',
	originationKey: '',
	r_teamToComponents_c_teamId: 0,
	status: '',
	teamId: 0,
};

const mockTeam = {
	dateCreated: '',
	dateModified: '',
	externalReferenceCode: '',
	id: 0,
	name: 'Solutions',
};

const values = [
	[1, 3, 5, 4, 5],
	[1, 2, 3, 4, 5],
	[1, 2, 3, 4, 5],
	[1, 2, 3, 4, 5],
	[1, 2, 3, 4, 5],
];

const useCompareRuns = (
	type: 'components' | 'details' | 'teams',
	{componentId, teamId}: {componentId?: string; teamId?: string} = {}
) => {
	const {runA, runB} = useParams();

	const operator = type === 'details' ? '' : type;

	const {data} = useFetch<CompareRunsResponse>(
		`/compare-runs/${runA}/${runB}/${operator}`,
		{
			params: {customParams: {componentId, teamId}},
		}
	);

	if (typeof data === 'object') {
		return [data];
	}

	return (
		data ?? [
			{
				values,
				...(type === 'components' && {component: mockComponent}),
				...(type === 'teams' && {team: mockTeam}),
			},
		]
	);
};

export default useCompareRuns;
