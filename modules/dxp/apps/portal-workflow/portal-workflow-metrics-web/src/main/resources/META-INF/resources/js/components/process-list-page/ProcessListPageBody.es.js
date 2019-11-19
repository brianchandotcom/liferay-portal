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

import React from 'react';

import EmptyState from '../../shared/components/list/EmptyState.es';
import ReloadButton from '../../shared/components/list/ReloadButton.es';
import LoadingState from '../../shared/components/loading/LoadingState.es';
import PaginationBar from '../../shared/components/pagination/PaginationBar.es';
import PromisesResolver from '../../shared/components/request/PromisesResolver.es';
import ProcessListPage from './ProcessListPage.es';

const Body = ({data, search}) => {
	return (
		<>
			<PromisesResolver.Pending>
				<ProcessListPage.Loading />
			</PromisesResolver.Pending>

			<PromisesResolver.Resolved>
				{data.totalCount ? (
					<>
						<ProcessListPage.Table items={data.items} />

						<PaginationBar
							page={data.page}
							pageCount={data.items.length}
							pageSize={data.pageSize}
							totalCount={data.totalCount}
						/>
					</>
				) : (
					<ProcessListPage.Empty search={search} />
				)}
			</PromisesResolver.Resolved>

			<PromisesResolver.Rejected>
				<ProcessListPage.Error />
			</PromisesResolver.Rejected>
		</>
	);
};

const EmptyView = ({search}) => {
	const message = search
		? Liferay.Language.get('no-results-were-found')
		: Liferay.Language.get(
				'once-there-are-active-processes-metrics-will-appear-here'
		  );

	return (
		<EmptyState
			className="border-1"
			hideAnimation={false}
			message={message}
			title={!search && Liferay.Language.get('no-current-metrics')}
			type={search && 'not-found'}
		/>
	);
};

const ErrorView = () => {
	return (
		<EmptyState
			actionButton={<ReloadButton />}
			className="border-1"
			hideAnimation={true}
			message={Liferay.Language.get(
				'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
			)}
			messageClassName="small"
			type="error"
		/>
	);
};

const LoadingView = () => {
	return <LoadingState />;
};

export {Body, EmptyView, ErrorView, LoadingView};
