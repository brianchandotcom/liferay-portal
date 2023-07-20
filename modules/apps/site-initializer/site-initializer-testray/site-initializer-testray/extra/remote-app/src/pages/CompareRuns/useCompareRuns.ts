/**
import { useFetch } from '../../hooks/useFetch';
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
