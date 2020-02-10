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

import ClayModal from '@clayui/modal';
import React from 'react';

import EmptyState from '../../../../../shared/components/empty-state/EmptyState.es';
import RetryButton from '../../../../../shared/components/list/RetryButton.es';
import LoadingState from '../../../../../shared/components/loading/LoadingState.es';
import PaginationBar from '../../../../../shared/components/pagination-bar/PaginationBar.es';
import PromisesResolver from '../../../../../shared/components/promises-resolver/PromisesResolver.es';
import {Table} from './BulkReassignSelectTasksStepTable.es';

const Body = ({items, pagination, setRetry, totalCount}) => {
	return (
		<ClayModal.Body>
			<PromisesResolver.Pending>
				<Body.Loading />
			</PromisesResolver.Pending>

			<PromisesResolver.Resolved>
				{items && items.length > 0 ? (
					<>
						<Body.Table items={items} totalCount={totalCount} />

						<PaginationBar routing={false} {...pagination} />
					</>
				) : (
					<Body.Empty />
				)}
			</PromisesResolver.Resolved>

			<PromisesResolver.Rejected>
				<Body.Error onClick={() => setRetry(retry => retry + 1)} />
			</PromisesResolver.Rejected>
		</ClayModal.Body>
	);
};

const EmptyView = () => {
	return (
		<EmptyState
			className="border-0"
			message={Liferay.Language.get('no-results-were-found')}
			messageClassName="small"
			type="not-found"
		/>
	);
};

const ErrorView = ({onClick}) => {
	return (
		<EmptyState
			actionButton={<RetryButton onClick={onClick} />}
			className="border-0 pb-7 pt-8"
			hideAnimation={true}
			message={Liferay.Language.get('unable-to-retrieve-data')}
			messageClassName="small"
		/>
	);
};

const LoadingView = () => {
	return <LoadingState className="border-0 mb-4 mt-6 pb-8 pt-8" />;
};

Body.Empty = EmptyView;
Body.Error = ErrorView;
Body.Loading = LoadingView;
Body.Table = Table;

export {Body};
