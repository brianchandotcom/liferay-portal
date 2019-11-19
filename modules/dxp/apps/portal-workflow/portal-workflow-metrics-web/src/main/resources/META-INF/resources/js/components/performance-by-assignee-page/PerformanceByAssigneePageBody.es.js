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
import PromisesResolver from '../../shared/components/request/PromisesResolver.es';
import {Table} from './PerformanceByAssigneePageTable.es';

const Body = ({data}) => {
	const {items} = data;

	return (
		<div className="container-fluid-1280 mt-4">
			<PromisesResolver.Pending>
				<Body.Loading />
			</PromisesResolver.Pending>

			<PromisesResolver.Resolved>
				{items && items.length > 0 ? (
					<Body.Table items={items} />
				) : (
					<Body.Empty />
				)}
			</PromisesResolver.Resolved>

			<PromisesResolver.Rejected>
				<Body.Error />
			</PromisesResolver.Rejected>
		</div>
	);
};

const EmptyView = () => {
	return (
		<EmptyState
			className="border-0"
			data-testid="emptyState"
			hideAnimation={true}
			message={Liferay.Language.get('there-is-no-data-at-the-moment')}
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
	return <LoadingState />;
};

Body.Empty = EmptyView;
Body.Error = ErrorView;
Body.Loading = LoadingView;
Body.Table = Table;

export {Body};
