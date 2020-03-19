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

import React, {useContext, useMemo} from 'react';

import EmptyState from '../../shared/components/empty-state/EmptyState.es';
import ReloadButton from '../../shared/components/list/ReloadButton.es';
import LoadingState from '../../shared/components/loading/LoadingState.es';
import PaginationBar from '../../shared/components/pagination-bar/PaginationBar.es';
import PromisesResolver from '../../shared/components/promises-resolver/PromisesResolver.es';
import {Table} from './InstanceListPageTable.es';
import {ModalContext} from './modal/ModalProvider.es';
import InstanceDetailsModal from './modal/instance-details/InstanceDetailsModal.es';
import BulkReassignModal from './modal/reassign/bulk/BulkReassignModal.es';
import SingleReassignModal from './modal/reassign/single/SingleReassignModal.es';
import BulkTransitionModal from './modal/transition/bulk/BulkTransitionModal.es';
import SingleTransitionModal from './modal/transition/single/SingleTransitionModal.es';
import BulkUpdateDueDateModal from './modal/update-due-date/BulkUpdateDueDateModal.es';
import SingleUpdateDueDateModal from './modal/update-due-date/SingleUpdateDueDateModal.es';

const Body = ({
	data: {items, totalCount},
	fetchData,
	filtered,
	routeParams,
}) => {
	const emptyMessageText = filtered
		? Liferay.Language.get('no-results-were-found')
		: Liferay.Language.get(
				'once-there-are-active-processes-metrics-will-appear-here'
		  );
	const errorMessageText = Liferay.Language.get(
		'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
	);

	const {visibleModal} = useContext(ModalContext);

	const promises = useMemo(() => {
		if (!visibleModal) {
			return [fetchData()];
		}

		return [];
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [fetchData, visibleModal]);

	return (
		<PromisesResolver promises={promises}>
			<div className="container-fluid-1280 mt-4">
				<PromisesResolver.Pending>
					<LoadingState />
				</PromisesResolver.Pending>

				<PromisesResolver.Resolved>
					{items && items.length ? (
						<>
							<Body.Table items={items} totalCount={totalCount} />

							<PaginationBar
								pageBuffer={3}
								pageCount={items.length}
								{...routeParams}
								totalCount={totalCount}
							/>
						</>
					) : (
						<EmptyState
							className="border-1"
							hideAnimation={false}
							message={emptyMessageText}
							type="not-found"
						/>
					)}
				</PromisesResolver.Resolved>

				<PromisesResolver.Rejected>
					<EmptyState
						actionButton={<ReloadButton />}
						className="border-1"
						hideAnimation={true}
						message={errorMessageText}
						messageClassName="small"
						type="error"
					/>
				</PromisesResolver.Rejected>
			</div>

			<Body.ModalWrapper />
		</PromisesResolver>
	);
};

const ModalWrapper = () => {
	return (
		<>
			<BulkReassignModal />

			<BulkTransitionModal />

			<BulkUpdateDueDateModal />

			<InstanceDetailsModal />

			<SingleReassignModal />

			<SingleTransitionModal />

			<SingleUpdateDueDateModal />
		</>
	);
};

Body.Table = Table;
Body.ModalWrapper = ModalWrapper;

export {Body};
