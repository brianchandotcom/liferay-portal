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

import EmptyState from '../../shared/components/empty-state/EmptyState.es';
import ReloadButton from '../../shared/components/list/ReloadButton.es';
import LoadingState from '../../shared/components/loading/LoadingState.es';
import PaginationBar from '../../shared/components/pagination-bar/PaginationBar.es';
import PromisesResolver from '../../shared/components/promises-resolver/PromisesResolver.es';
import {Table} from './ProcessListPageTable.es';

const Body = ({data, search}) => {
	const {items, page, pageSize, totalCount} = data;

	return (
		<div className="container-fluid-1280 mt-4">
			<PromisesResolver.Pending>
				<Body.Loading />
			</PromisesResolver.Pending>

			<PromisesResolver.Resolved>
				{totalCount > 0 ? (
					<>
						<Body.Table items={items} />

						<PaginationBar
							page={page}
							pageBuffer={3}
							pageSize={pageSize}
							totalCount={totalCount}
						/>
					</>
				) : (
					<Body.Empty search={search} />
				)}
			</PromisesResolver.Resolved>

			<PromisesResolver.Rejected>
				<Body.Error />
			</PromisesResolver.Rejected>
		</div>
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
			message={message}
			title={!search && Liferay.Language.get('no-current-metrics')}
			type={search ? 'not-found' : 'empty'}
		/>
	);
};

const ErrorView = () => {
	return (
		<EmptyState
			actionButton={<ReloadButton />}
			hideAnimation={true}
			message={Liferay.Language.get(
				'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
			)}
		/>
	);
};

const LoadingView = () => {
	return <LoadingState className="border-0 pb-6 pt-6 sheet" />;
};

Body.Empty = EmptyView;
Body.Error = ErrorView;
Body.Loading = LoadingView;
Body.Table = Table;

export {Body};
