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

import React, {useContext, useMemo, useState} from 'react';

import {getFiltersParam} from '../../shared/components/filter/util/filterUtil.es';
import EmptyState from '../../shared/components/list/EmptyState.es';
import ReloadButton from '../../shared/components/list/ReloadButton.es';
import LoadingState from '../../shared/components/loading/LoadingState.es';
import PaginationBar from '../../shared/components/pagination/PaginationBar.es';
import PromisesResolver from '../../shared/components/request/PromisesResolver.es';
import Request from '../../shared/components/request/Request.es';
import {useProcessTitle} from '../../shared/hooks/useProcessTitle.es';
import InstanceListPageFilters from './InstanceListPageFilters.es';
import InstanceListPageItemDetail from './InstanceListPageItemDetail.es';
import {Table} from './InstanceListPageTable.es';
import {ModalContext} from './modal/ModalContext.es';
import {SingleReassignModal} from './modal/single-reassign/SingleReassignModal.es';
import {InstanceFiltersProvider} from './store/InstanceListPageFiltersStore.es';
import {
	InstanceListProvider,
	InstanceListContext
} from './store/InstanceListPageStore.es';

const InstanceListPage = ({page, pageSize, processId, query}) => {
	const {
		assigneeUserIds = [],
		slaStatuses = [],
		statuses = [],
		taskKeys = [],
		timeRange = []
	} = getFiltersParam(query);

	const [singleModal, setSingleModal] = useState({
		selectedItem: undefined,
		visible: false
	});

	useProcessTitle(processId, Liferay.Language.get('all-items'));

	return (
		<Request>
			<ModalContext.Provider value={{setSingleModal, singleModal}}>
				<InstanceFiltersProvider
					assigneeKeys={assigneeUserIds}
					processId={processId}
					processStatusKeys={statuses}
					processStepKeys={taskKeys}
					slaStatusKeys={slaStatuses}
					timeRangeKeys={timeRange}
				>
					<InstanceListProvider
						page={page}
						pageSize={pageSize}
						processId={processId}
						query={query}
					>
						<InstanceListPage.Header
							processId={processId}
							query={query}
						/>

						<InstanceListPage.Body
							page={page}
							pageSize={pageSize}
							processId={processId}
							query={query}
							singleModal={singleModal}
						/>
					</InstanceListProvider>
				</InstanceFiltersProvider>
			</ModalContext.Provider>
		</Request>
	);
};

const Body = ({page, pageSize, processId, query, singleModal}) => {
	const {fetchInstances, items, searching, totalCount} = useContext(
		InstanceListContext
	);

	const emptyMessageText = searching
		? Liferay.Language.get('no-results-were-found')
		: Liferay.Language.get(
				'once-there-are-active-processes-metrics-will-appear-here'
		  );
	const errorMessageText = Liferay.Language.get(
		'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
	);

	const promises = useMemo(() => {
		if (!singleModal.visible) {
			return [fetchInstances()];
		}

		return [];
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [page, pageSize, processId, query, singleModal.visible]);

	return (
		<>
			<div className="container-fluid-1280 mt-4">
				<PromisesResolver promises={promises}>
					<PromisesResolver.Pending>
						<LoadingState />
					</PromisesResolver.Pending>

					<PromisesResolver.Resolved>
						{items && items.length ? (
							<>
								<InstanceListPage.Body.Table items={items} />

								<PaginationBar
									page={page}
									pageCount={items.length}
									pageSize={pageSize}
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
				</PromisesResolver>
			</div>
			<InstanceListPage.SingleReassignModal />
			<InstanceListPageItemDetail processId={processId} />
		</>
	);
};

const Header = () => {
	const {totalCount} = useContext(InstanceListContext);

	return (
		<Request.Success>
			<InstanceListPageFilters totalCount={totalCount} />
		</Request.Success>
	);
};

Body.Table = Table;
InstanceListPage.Body = Body;
InstanceListPage.Header = Header;
InstanceListPage.SingleReassignModal = SingleReassignModal;

export default InstanceListPage;
