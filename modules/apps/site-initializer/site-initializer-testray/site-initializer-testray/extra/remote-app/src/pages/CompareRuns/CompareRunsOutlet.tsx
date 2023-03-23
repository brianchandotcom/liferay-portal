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

import ClayTabs from '@clayui/tabs';
import {useEffect, useMemo} from 'react';
import {Outlet, useLocation, useNavigate, useParams} from 'react-router-dom';
import {useFetch} from '~/hooks/useFetch';
import useSearchBuilder from '~/hooks/useSearchBuilder';
import {APIResponse, TestrayRun, testrayRunImpl} from '~/services/rest';

import CompareRunDetails from '.';
import Container from '../../components/Layout/Container';
import useHeader from '../../hooks/useHeader';
import i18n from '../../i18n';
import useCompareRuns from './useCompareRuns';

const CompareRunsOutlet = () => {
	const navigate = useNavigate();
	const {setDropdownIcon, setHeading, setTabs} = useHeader();
	const {runA, runB} = useParams();
	const {pathname} = useLocation();
	const compareRuns = useCompareRuns('details');

	const caseResultFilter = useSearchBuilder({useURIEncode: false});

	const filter = caseResultFilter
		.in('id', [runA as string, runB as string])
		.build();

	const {data} = useFetch<APIResponse<TestrayRun>>(testrayRunImpl.resource, {
		params: {
			filter,
		},
		transformData: (response) =>
			testrayRunImpl.transformDataFromList(response),
	});
	const runs = useMemo(() => {
		const items = data?.items ?? [];

		const getRun = (runId: string) =>
			items?.find(({id}) => runId === String(id)) as TestrayRun;

		return [getRun(runA as string), getRun(runB as string)];
	}, [data?.items, runA, runB]);

	useEffect(() => {
		const title = runs[0]?.build?.project?.name;

		if (title) {
			setTimeout(() => {
				setHeading([
					{
						category: i18n.translate('project'),
						title,
					},
				]);
			});
		}
		setTabs([]);
		setDropdownIcon('drop');
	}, [runs, setDropdownIcon, setHeading, setTabs]);

	return (
		<>
			<CompareRunDetails matrixData={compareRuns[0].values} runs={runs} />

			<Container className="mt-3">
				<ClayTabs className="header-container-tabs">
					{[
						{
							active: pathname.endsWith('/teams'),
							path: 'teams',
							title: i18n.translate('teams'),
						},
						{
							active: pathname.endsWith('/components'),
							path: 'components',
							title: i18n.translate('components'),
						},
						{
							active: pathname.endsWith('/cases'),
							path: 'cases',
							title: i18n.translate('cases'),
						},
					].map((tab, index) => (
						<ClayTabs.Item
							active={tab.active}
							innerProps={{
								'aria-controls': `tabpanel-${index}`,
							}}
							key={index}
							onClick={() => navigate(tab.path)}
						>
							{tab.title}
						</ClayTabs.Item>
					))}
				</ClayTabs>

				<Outlet context={{runs}} />
			</Container>
		</>
	);
};

export default CompareRunsOutlet;
