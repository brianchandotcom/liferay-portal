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

import ClayLayout from '@clayui/layout';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {usePrevious} from 'frontend-js-react-web';
import React, {useCallback, useContext, useEffect, useMemo} from 'react';

import filterConstants from '../../shared/components/filter/util/filterConstants.es';
import QuickActionKebab from '../../shared/components/quick-action-kebab/QuickActionKebab.es';
import ResultsBar from '../../shared/components/results-bar/ResultsBar.es';
import ToolbarWithSelection from '../../shared/components/toolbar-with-selection/ToolbarWithSelection.es';
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

const Header = ({
	filterKeys,
	items = [],
	routeParams,
	selectedFilters,
	totalCount,
}) => {
	const {userId} = useContext(AppContext);
	const {
		selectAll,
		selectedItems,
		setSelectAll,
		setSelectedItems,
	} = useContext(InstanceListContext);
	const {openModal} = useContext(ModalContext);
	const previousCount = usePrevious(totalCount);

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

	const selectedOnPage = useMemo(
		() => selectedItems.filter(({id}) => items.find(compareId(id))),
		[items, selectedItems]
	);

	const allPageSelected =
		items.length > 0 && items.length === selectedOnPage.length;

	const checkbox = {
		checked: allPageSelected || selectAll,
		indeterminate:
			selectedOnPage.length > 0 && !allPageSelected && !selectAll,
	};

	const isRemainingItem = useCallback(
		(clear) => ({assignees = [], id, status}) => {
			const assignedToUser = !!assignees.find(
				({id}) => id === Number(userId)
			);
			const completed = status === processStatusConstants.completed;
			const selected = clear && selectedItems.find(compareId(id));
			const {reviewer} = assignees.find(({id}) => id === -1) || {};

			return (reviewer || assignedToUser) && !completed && !selected;
		},
		[selectedItems, userId]
	);

	const remainingItems = useMemo(() => {
		return items.filter(isRemainingItem(true));
	}, [items, isRemainingItem]);

	const toolbarActive = useMemo(() => selectedItems.length > 0, [
		selectedItems,
	]);

	useEffect(() => {
		if (
			selectAll &&
			remainingItems.length > 0 &&
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

	const statusesFilterItem = useMemo(
		() => selectedFilters.find((filter) => filter.key === 'statuses'),
		[selectedFilters]
	);
	const {key} = statusesFilterItem ? statusesFilterItem.items[0] : {};
	const completedStatusSelected = useMemo(
		() =>
			selectedFilters.length > 0 && statusesFilterItem
				? key === processStatusConstants.completed
				: false,
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[key]
	);

	const selectedFilterItems = useMemo(
		() =>
			selectedFilters.filter(
				(filter) =>
					completedStatusSelected ||
					filter.key !== filterConstants.timeRange.key
			),
		[completedStatusSelected, selectedFilters]
	);

	return (
		<>
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
					<ClayManagementToolbar.Item className="navbar-nav-last">
						<ClayLayout.ContentCol data-testid="headerQuickAction">
							<QuickActionKebab items={kebabItems} />
						</ClayLayout.ContentCol>
					</ClayManagementToolbar.Item>
				) : (
					<>
						<ClayManagementToolbar.Item>
							<strong className="ml-0 mr-0 navbar-text">
								{Liferay.Language.get('filter-by')}
							</strong>
						</ClayManagementToolbar.Item>

						<SLAStatusFilter />

						<ProcessStatusFilter />

						{completedStatusSelected && (
							<TimeRangeFilter
								options={{
									withSelectionTitle: false,
								}}
							/>
						)}

						<ProcessStepFilter processId={routeParams.processId} />

						<AssigneeFilter processId={routeParams.processId} />
					</>
				)}
			</ToolbarWithSelection>

			{selectedFilterItems.length > 0 && (
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
		</>
	);
};

export {Header};
