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

import React, {useMemo, useState} from 'react';

import EmptyState from '../../shared/components/empty-state/EmptyState.es';
import ReloadButton from '../../shared/components/list/ReloadButton.es';
import LoadingState from '../../shared/components/loading/LoadingState.es';
import PaginationBar from '../../shared/components/pagination-bar/PaginationBar.es';
import PromisesResolver from '../../shared/components/promises-resolver/PromisesResolver.es';
import {useFetch} from '../../shared/hooks/useFetch.es';
import {useFilter} from '../../shared/hooks/useFilter.es';
import {useProcessTitle} from '../../shared/hooks/useProcessTitle.es';
import {processStatusConstants} from '../filter/ProcessStatusFilter.es';
import {useTimeRangeFetch} from '../filter/hooks/useTimeRangeFetch.es';
import {isValidDate} from '../filter/util/timeRangeUtil.es';
import {Header} from './InstanceListPageHeader.es';
import {Table} from './InstanceListPageTable.es';
import {ModalContext} from './modal/ModalContext.es';
import {BulkReassignModal} from './modal/bulk-reassign/BulkReassignModal.es';
import {InstanceDetailsModal} from './modal/instance-details/InstanceDetailsModal.es';
import {SingleReassignModal} from './modal/single-reassign/SingleReassignModal.es';
import {InstanceListProvider} from './store/InstanceListPageStore.es';

const InstanceListPage = ({routeParams}) => {
	useTimeRangeFetch();

	const {page, pageSize, processId} = routeParams;
	const [singleModal, setSingleModal] = useState({
		selectedItem: undefined,
		visible: false
	});

	const [bulkModal, setBulkModal] = useState({
		processId,
		reassignedTasks: [],
		reassigning: false,
		selectAll: false,
		selectedAssignee: null,
		selectedTasks: [],
		useSameAssignee: false,
		visible: false
	});

	const [instanceDetailsModal, setInstanceDetailsModal] = useState({
		processId,
		visible: false
	});

	const modalState = {
		bulkModal,
		instanceDetailsModal,
		setBulkModal,
		setInstanceDetailsModal,
		setSingleModal,
		singleModal
	};

	useProcessTitle(processId, Liferay.Language.get('all-items'));

	const filterKeys = [
		'assignee',
		'processStep',
		'processStatus',
		'slaStatus',
		'timeRange'
	];

	const {
		filterState: {timeRange},
		filterValues: {assigneeUserIds, slaStatuses, statuses = [], taskKeys},
		prefixedKeys,
		selectedFilters
	} = useFilter({filterKeys});

	const {dateEnd, dateStart} =
		timeRange && timeRange.length ? timeRange[0] : {};

	const completedStatus = statuses.some(
		status => status === processStatusConstants.completed
	);

	let completedAndDate = !completedStatus;

	let timeRangeParams = {};

	if (completedStatus && isValidDate(dateEnd) && isValidDate(dateStart)) {
		timeRangeParams = {
			dateEnd: dateEnd.toISOString(),
			dateStart: dateStart.toISOString()
		};
		completedAndDate = true;
	}

	const {data, fetchData} = useFetch({
		params: {
			assigneeUserIds,
			page,
			pageSize,
			slaStatuses,
			statuses,
			taskKeys,
			...timeRangeParams
		},
		url: `/processes/${processId}/instances`
	});

	const promises = useMemo(() => {
		if (!bulkModal.visible && !singleModal.visible && completedAndDate) {
			return [fetchData()];
		}

		return [];
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [bulkModal.visible, completedAndDate, fetchData, singleModal.visible]);

	return (
		<ModalContext.Provider value={modalState}>
			<InstanceListProvider>
				<InstanceListPage.Header
					filterKeys={prefixedKeys}
					items={data.items}
					processId={processId}
					routeParams={routeParams}
					selectedFilters={selectedFilters}
					totalCount={data.totalCount}
				/>

				<PromisesResolver promises={promises}>
					<InstanceListPage.Body
						data={data}
						filtered={selectedFilters.length > 0}
						routeParams={routeParams}
					/>
				</PromisesResolver>
			</InstanceListProvider>
		</ModalContext.Provider>
	);
};

const Body = ({data, filtered, routeParams}) => {
	const {items, totalCount} = data;

	const emptyMessageText = filtered
		? Liferay.Language.get('no-results-were-found')
		: Liferay.Language.get(
				'once-there-are-active-processes-metrics-will-appear-here'
		  );
	const errorMessageText = Liferay.Language.get(
		'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
	);

	return (
		<>
			<div className="container-fluid-1280 mt-4">
				<PromisesResolver.Pending>
					<LoadingState />
				</PromisesResolver.Pending>

				<PromisesResolver.Resolved>
					{items && items.length ? (
						<>
							<InstanceListPage.Body.Table
								items={items}
								totalCount={totalCount}
							/>

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

			<InstanceListPage.SingleReassignModal />

			<InstanceListPage.BulkReassignModal />

			<InstanceListPage.InstanceDetailsModal />
		</>
	);
};

InstanceListPage.Body = Body;
InstanceListPage.Body.Table = Table;
InstanceListPage.BulkReassignModal = BulkReassignModal;
InstanceListPage.Header = Header;
InstanceListPage.InstanceDetailsModal = InstanceDetailsModal;
InstanceListPage.SingleReassignModal = SingleReassignModal;

export default InstanceListPage;
