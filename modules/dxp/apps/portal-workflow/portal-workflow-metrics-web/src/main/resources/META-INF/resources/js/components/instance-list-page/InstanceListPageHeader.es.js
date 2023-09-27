/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import {usePrevious} from '@liferay/frontend-js-react-web';
import {ManagementToolbar} from 'frontend-js-components-web';
import React, {useCallback, useContext, useEffect, useMemo} from 'react';

import filterConstants from '../../shared/components/filter/util/filterConstants.es';
import MetricsCalculatedInfo from '../../shared/components/last-updated-info/MetricsCalculatedInfo.es';
import PromisesResolver from '../../shared/components/promises-resolver/PromisesResolver.es';
import QuickActionKebab from '../../shared/components/quick-action-kebab/QuickActionKebab.es';
import ResultsBar from '../../shared/components/results-bar/ResultsBar.es';
import ToolbarWithSelection from '../../shared/components/toolbar-with-selection/ToolbarWithSelection.es';
import {useDateModified} from '../../shared/hooks/useDateModified.es';
import {capitalize} from '../../shared/util/util.es';
import {AppContext} from '../AppContext.es';
import AssigneeFilter from '../filter/AssigneeFilter.es';
import ProcessStatusFilter, {
	processStatusConstants,
} from '../filter/ProcessStatusFilter.es';
import ProcessStepFilter from '../filter/ProcessStepFilter.es';
import SLAStatusFilter from '../filter/SLAStatusFilter.es';
import TimeRangeFilter from '../filter/TimeRangeFilter.es';
import {InstanceListContext} from './InstanceListPageProvider.es';
import {ModalContext} from './modal/ModalProvider.es';

export default function Header({
	filterKeys,
	items = [],
	processId,
	routeParams,
	selectedFilters,
	totalCount,
}) {
	const {dateModified, fetchData} = useDateModified({
		processId,
	});

	const {userId} = useContext(AppContext);
	const {
		selectAll,
		selectedItems,
		setSelectAll,
		setSelectedItems,
	} = useContext(InstanceListContext);
	const {openModal} = useContext(ModalContext);
	const previousCount = usePrevious(totalCount);

	const previousFetchData = usePrevious(fetchData);

	const promises = useMemo(() => {
		if (previousFetchData !== fetchData && items?.length) {
			return [fetchData()];
		}

		return [];

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [items?.length, routeParams]);

	const handleClick = useCallback(
		(bulkModal, singleModal) => {
			const bulkOperation =
				selectedItems.length > 1 ||
				selectedItems[0].taskNames.length > 1;

			openModal(bulkOperation ? bulkModal : singleModal);
		},
		[openModal, selectedItems]
	);

	const compareId = (itemId) => ({id}) => id === itemId;

	const kebabItems = [
		{
			icon: 'arrow-start',
			label: capitalize(Liferay.Language.get('transition')),
			onClick: () => {
				openModal('bulkTransition');
			},
		},
		{
			icon: 'date',
			label: Liferay.Language.get('update-tasks-due-dates'),
			onClick: () => handleClick('bulkUpdateDueDate', 'updateDueDate'),
		},
		{
			icon: 'change',
			label: Liferay.Language.get('reassign-task'),
			onClick: () => handleClick('bulkReassign', 'singleReassign'),
		},
	];

	const selectedOnPage = selectedItems.filter(({id}) =>
		items.find(compareId(id))
	);

	const allPageSelected =
		!!items.length && items.length === selectedOnPage.length;

	const checkbox = {
		checked: allPageSelected || selectAll,
		indeterminate:
			!!selectedOnPage.length && !allPageSelected && !selectAll,
	};

	const isRemainingItem = (clear) => ({assignees = [], id, status}) => {
		const assignedToUser = !!assignees.find(
			({id}) => id === Number(userId)
		);
		const completed = status === processStatusConstants.completed;
		const selected = clear && selectedItems.find(compareId(id));
		const {reviewer} = assignees.find(({id}) => id === -1) || {};

		return (reviewer || assignedToUser) && !completed && !selected;
	};

	const remainingItems = items.filter(isRemainingItem(true));
	const toolbarActive = !!selectedItems.length;

	useEffect(() => {
		if (
			selectAll &&
			!!remainingItems.length &&
			previousCount === totalCount
		) {
			setSelectedItems([
				...selectedItems,
				...items.filter(isRemainingItem()),
			]);
			setSelectAll(items.length === remainingItems.length);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [items]);

	useEffect(() => {
		setSelectAll(totalCount > 0 && totalCount === selectedItems.length);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [totalCount]);

	const handleClear = () => {
		setSelectedItems([]);
		setSelectAll(false);
	};

	const handleCheck = useCallback(
		(checked) => () => {
			const updatedItems = checked
				? [...selectedItems, ...remainingItems]
				: selectedItems.filter(({id}) => !items.find(compareId(id)));

			setSelectAll(totalCount > 0 && totalCount === updatedItems.length);
			setSelectedItems(updatedItems);
		},

		// eslint-disable-next-line react-hooks/exhaustive-deps
		[items, remainingItems, selectedItems]
	);

	const selectedFilter = selectedFilters.find(
		({key}) => key === filterConstants.processStatus.key
	);

	const completedSelected = selectedFilter?.items.some(
		({key}) => key === processStatusConstants.completed
	);

	const selectedFilterItems = selectedFilters.filter(
		({key}) => completedSelected || key !== filterConstants.timeRange.key
	);

	return (
		<PromisesResolver promises={promises}>
			<ToolbarWithSelection
				{...checkbox}
				active={toolbarActive}
				handleCheck={handleCheck(
					!checkbox.indeterminate && !selectAll && !allPageSelected
				)}
				handleClear={handleClear}
				handleSelectAll={() => {
					setSelectedItems(items);
					setSelectAll(true);
				}}
				selectAll={selectAll}
				selectedCount={selectedItems.length}
				totalCount={totalCount}
			>
				{toolbarActive ? (
					<ManagementToolbar.Item className="navbar-nav-last">
						<ClayLayout.ContentCol>
							<QuickActionKebab items={kebabItems} />
						</ClayLayout.ContentCol>
					</ManagementToolbar.Item>
				) : (
					<>
						<ManagementToolbar.Item>
							<strong className="ml-0 mr-0 navbar-text">
								{Liferay.Language.get('filter-by')}
							</strong>
						</ManagementToolbar.Item>

						<SLAStatusFilter
							options={{
								withSelectionTitle: false,
							}}
						/>

						<ProcessStatusFilter />

						<TimeRangeFilter
							options={{
								show: completedSelected,
								withSelectionTitle: false,
							}}
						/>

						<ProcessStepFilter processId={routeParams.processId} />

						<AssigneeFilter processId={routeParams.processId} />
					</>
				)}
			</ToolbarWithSelection>

			{!!selectedFilterItems.length && (
				<ResultsBar>
					<ResultsBar.TotalCount
						search={routeParams.search}
						totalCount={totalCount}
					/>

					<ResultsBar.FilterItems
						filters={selectedFilterItems}
						{...routeParams}
					/>

					<ResultsBar.Clear
						filterKeys={filterKeys}
						filters={selectedFilters}
						{...routeParams}
					/>
				</ResultsBar>
			)}

			<MetricsCalculatedInfo dateModified={dateModified} />
		</PromisesResolver>
	);
}
