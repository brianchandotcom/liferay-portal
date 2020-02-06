/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayManagementToolbar from '@clayui/management-toolbar';
import React, {useMemo} from 'react';

import PromisesResolver from '../../shared/components/promises-resolver/PromisesResolver.es';
import ResultsBar from '../../shared/components/results-bar/ResultsBar.es';
import {parse} from '../../shared/components/router/queryString.es';
import SearchField from '../../shared/components/search-field/SearchField.es';
import {useFetch} from '../../shared/hooks/useFetch.es';
import {usePageTitle} from '../../shared/hooks/usePageTitle.es';
import {Body} from './ProcessListPageBody.es';

const Header = ({page, pageSize, search, sort, totalCount}) => {
	return (
		<>
			<ClayManagementToolbar className="mb-0">
				<div className="navbar-form-autofit">
					<SearchField disabled={!search && totalCount === 0} />
				</div>
			</ClayManagementToolbar>

			{search && (
				<ResultsBar>
					<ResultsBar.TotalCount
						search={search}
						totalCount={totalCount}
					/>

					<ResultsBar.Clear
						page={page}
						pageSize={pageSize}
						sort={sort}
					/>
				</ResultsBar>
			)}
		</>
	);
};

const ProcessListPage = ({history, query, routeParams}) => {
	if (history.location.pathname === '/') {
		history.replace(
			`/processes/20/1/${encodeURIComponent('overdueInstanceCount:desc')}`
		);
	}

	usePageTitle(Liferay.Language.get('metrics'));

	const {page, pageSize, sort} = routeParams;
	const {search = null} = parse(query);

	const {data, fetchData} = useFetch({
		params: {
			title: search,
			...routeParams
		},
		url: '/processes'
	});

	const promises = useMemo(() => {
		if (page && pageSize && sort) {
			return [fetchData()];
		}

		return [new Promise(() => {})];
	}, [fetchData, page, pageSize, sort]);

	return (
		<PromisesResolver promises={promises}>
			<ProcessListPage.Header
				search={search}
				totalCount={data.totalCount}
				{...routeParams}
			/>

			<ProcessListPage.Body data={data} search={search} />
		</PromisesResolver>
	);
};

ProcessListPage.Body = Body;
ProcessListPage.Header = Header;

export default ProcessListPage;
