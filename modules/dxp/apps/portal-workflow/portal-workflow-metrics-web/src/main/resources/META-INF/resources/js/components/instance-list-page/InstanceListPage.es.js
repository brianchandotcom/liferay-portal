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
import {Header} from './InstanceListPageHeader.es';
import {ItemDetail} from './InstanceListPageItemDetail.es';
import {Table} from './InstanceListPageTable.es';
import {ModalContext} from './modal/ModalContext.es';
import {BulkReassignModal} from './modal/bulk-reassign/BulkReassignModal.es';
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

	const [bulkModal, setBulkModal] = useState({
		reassignedTasks: [],
		reassigning: false,
		selectedAssignee: null,
		selectedTasks: [],
		useSameAssignee: false,
		visible: false
	});

	const modalState = {bulkModal, setBulkModal, setSingleModal, singleModal};

	useProcessTitle(processId, Liferay.Language.get('all-items'));

	return (
		<Request>
			<ModalContext.Provider value={modalState}>
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
						<InstanceListPage.Header />

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

const Body = ({page, pageSize, processId, singleModal}) => {
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
	}, [fetchInstances, singleModal.visible]);

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
			<InstanceListPage.SingleReassignModal></InstanceListPage.SingleReassignModal>
			<InstanceListPage.BulkReassignModal></InstanceListPage.BulkReassignModal>
			<ItemDetail processId={processId} />
		</>
	);
};

InstanceListPage.Body = Body;
InstanceListPage.Body.Table = Table;
InstanceListPage.BulkReassignModal = BulkReassignModal;
InstanceListPage.Header = Header;
InstanceListPage.SingleReassignModal = SingleReassignModal;

export default InstanceListPage;
