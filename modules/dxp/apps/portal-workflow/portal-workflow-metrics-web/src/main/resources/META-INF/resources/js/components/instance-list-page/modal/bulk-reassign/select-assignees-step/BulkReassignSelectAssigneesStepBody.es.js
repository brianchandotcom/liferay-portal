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
import {usePaginationState} from '../../../../../shared/hooks/usePaginationState.es';
import {Table} from './BulkReassignSelectAssigneesStepTable.es';

const Body = ({data, setRetry, tasks}) => {
	const {paginatedItems, pagination} = usePaginationState({
		initialPageSize: 5,
		items: tasks
	});

	return (
		<ClayModal.Body>
			<div style={{maxHeight: '23rem'}}>
				<PromisesResolver.Pending>
					<Body.Loading />
				</PromisesResolver.Pending>

				<PromisesResolver.Resolved>
					<Body.Table data={data} items={paginatedItems} />

					<PaginationBar
						{...pagination}
						routing={false}
						totalCount={tasks.length}
					/>
				</PromisesResolver.Resolved>

				<PromisesResolver.Rejected>
					<Body.Error onClick={() => setRetry(retry => retry + 1)} />
				</PromisesResolver.Rejected>
			</div>
		</ClayModal.Body>
	);
};

const ErrorView = ({onClick}) => {
	return (
		<EmptyState
			actionButton={<RetryButton onClick={onClick} />}
			className="border-0 pb-8 pt-8"
			hideAnimation={true}
			message={Liferay.Language.get('unable-to-retrieve-assignees')}
			messageClassName="small"
		/>
	);
};

const LoadingView = () => {
	return (
		<LoadingState
			className="border-0 pb-8 pt-8"
			message={Liferay.Language.get('retrieving-all-possible-assignees')}
			messageClassName="small"
		/>
	);
};

Body.Error = ErrorView;
Body.Loading = LoadingView;
Body.Table = Table;

export {Body};
