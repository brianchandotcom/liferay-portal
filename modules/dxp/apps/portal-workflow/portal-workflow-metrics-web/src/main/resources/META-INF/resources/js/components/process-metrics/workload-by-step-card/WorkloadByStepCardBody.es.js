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

import Panel from '../../../shared/components/Panel.es';
import EmptyState from '../../../shared/components/empty-state/EmptyState.es';
import ReloadButton from '../../../shared/components/list/ReloadButton.es';
import LoadingState from '../../../shared/components/loading/LoadingState.es';
import PaginationBar from '../../../shared/components/pagination-bar/PaginationBar.es';
import PromisesResolver from '../../../shared/components/promises-resolver/PromisesResolver.es';
import {Table} from './WorkloadByStepCardTable.es';

const Body = ({items, page, pageSize, processId, totalCount}) => {
	return (
		<>
			<PromisesResolver.Pending>
				<Body.Loading />
			</PromisesResolver.Pending>

			<PromisesResolver.Resolved>
				{totalCount ? (
					<Panel.Body>
						<Body.Table items={items} processId={processId} />

						<PaginationBar
							page={page}
							pageSize={pageSize}
							totalCount={totalCount}
						/>
					</Panel.Body>
				) : (
					<Body.Empty />
				)}
			</PromisesResolver.Resolved>

			<PromisesResolver.Rejected>
				<Body.Error />
			</PromisesResolver.Rejected>
		</>
	);
};

const EmptyView = () => {
	return (
		<EmptyState
			className="border-0 mb-0"
			hideAnimation={true}
			message={Liferay.Language.get(
				'there-are-no-pending-items-at-the-moment'
			)}
			messageClassName="small"
		/>
	);
};

const ErrorView = () => {
	return (
		<EmptyState
			actionButton={<ReloadButton />}
			className="border-0"
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
	return <LoadingState className="border-0 pb-6 pt-6 sheet" />;
};

Body.Empty = EmptyView;
Body.Error = ErrorView;
Body.Loading = LoadingView;
Body.Table = Table;

export {Body};
