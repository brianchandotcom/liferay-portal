/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	const compareRuns = useCompareRuns('details');
	const {setHeading} = useHeader({
		icon: 'drop',
		tabs: [],
	});
	const {pathname} = useLocation();
	const {runA, runB} = useParams();
	const navigate = useNavigate();

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
	}, [runs, setHeading]);

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
