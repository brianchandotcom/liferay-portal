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

import {ClayCheckbox} from '@clayui/form';
import ClayManagementToolbar from '@clayui/management-toolbar';
import React, {useContext, useState, useEffect, useCallback} from 'react';

import FilterResultsBar from '../../shared/components/filter/FilterResultsBar.es';
import {filterKeys} from '../../shared/components/filter/util/filterConstants.es';
import {asFilterObject} from '../../shared/components/filter/util/filterUtil.es';
import QuickActionKebab from '../../shared/components/quick-action-kebab/QuickActionKebab.es';
import Request from '../../shared/components/request/Request.es';
import {sub} from '../../shared/util/lang.es';
import AssigneeFilter from '../process-metrics/filter/AssigneeFilter.es';
import ProcessStatusFilter from '../process-metrics/filter/ProcessStatusFilter.es';
import ProcessStepFilter from '../process-metrics/filter/ProcessStepFilter.es';
import SLAStatusFilter from '../process-metrics/filter/SLAStatusFilter.es';
import {TimeRangeFilter} from '../process-metrics/filter/TimeRangeFilter.es';
import {AssigneeContext} from '../process-metrics/filter/store/AssigneeStore.es';
import {ProcessStatusContext} from '../process-metrics/filter/store/ProcessStatusStore.es';
import {ProcessStepContext} from '../process-metrics/filter/store/ProcessStepStore.es';
import {SLAStatusContext} from '../process-metrics/filter/store/SLAStatusStore.es';
import {TimeRangeContext} from '../process-metrics/filter/store/TimeRangeStore.es';
import {ModalContext} from './modal/ModalContext.es';
import {InstanceListContext} from './store/InstanceListPageStore.es';

const Header = () => {
	const {
		items,
		selectAll,
		selectedItems,
		setSelectAll,
		setSelectedItems,
		totalCount
	} = useContext(InstanceListContext);
	const {assignees} = useContext(AssigneeContext);
	const {bulkModal, setBulkModal, setSingleModal} = useContext(ModalContext);
	const {isCompletedStatusSelected, processStatuses} = useContext(
		ProcessStatusContext
	);
	const {processSteps} = useContext(ProcessStepContext);
	const {slaStatuses} = useContext(SLAStatusContext);
	const {timeRanges} = useContext(TimeRangeContext);

	const [toolbarOptions, setToolbarOptions] = useState({
		active: false,
		indeterminateCheckbox: false
	});

	const completedStatusSelected = isCompletedStatusSelected();

	const kebabItems = [
		{
			action: () => {
				if (selectedItems.length > 1) {
					setBulkModal({...bulkModal, visible: true});
				} else {
					setSingleModal({
						selectedItem: selectedItems[0],
						visible: true
					});
				}
			},
			icon: 'change',
			title: Liferay.Language.get('reassign-task')
		}
	];

	useEffect(() => {
		const active = selectedItems.length > 0;

		const label = selectAll ? Liferay.Language.get('all-selected') : '';

		const selectedOnPage = selectedItems.filter(item =>
			items.find(({id}) => id === item.id)
		);
		const allPageSelected = items.length === selectedOnPage.length;

		setSelectAll(totalCount > 0 && totalCount === selectedItems.length);

		setToolbarOptions({
			active,
			checked: items.length > 0 && allPageSelected,
			indeterminateCheckbox: !allPageSelected && !selectAll && active,
			label
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [items, selectedItems, selectAll]);

	const getFilters = () => {
		const filters = [
			asFilterObject(
				slaStatuses,
				filterKeys.slaStatus,
				Liferay.Language.get('sla-status')
			),
			asFilterObject(
				processStatuses,
				filterKeys.processStatus,
				Liferay.Language.get('process-status')
			)
		];

		if (completedStatusSelected) {
			filters.push(
				asFilterObject(
					timeRanges,
					filterKeys.timeRange,
					Liferay.Language.get('completion-period'),
					true
				)
			);
		}

		filters.push(
			asFilterObject(
				processSteps,
				filterKeys.processStep,
				Liferay.Language.get('process-step')
			)
		);

		filters.push(
			asFilterObject(
				assignees,
				filterKeys.assignee,
				Liferay.Language.get('assignees')
			)
		);

		return filters;
	};

	const handleSelectAll = useCallback(
		checked => {
			let updatedItems;

			if (checked) {
				updatedItems = [
					...selectedItems,
					...items.filter(
						item => !selectedItems.find(({id}) => item.id === id)
					)
				];
			} else {
				updatedItems = selectedItems.filter(
					item => !items.find(({id}) => item.id === id)
				);
			}

			setSelectedItems(updatedItems);
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[items, selectedItems]
	);

	return (
		<Request.Success>
			<ClayManagementToolbar
				active={toolbarOptions.active}
				className="show-quick-actions-on-hover"
			>
				<ClayManagementToolbar.ItemList>
					<ClayManagementToolbar.Item
						className="ml-2"
						style={{padding: '1.2rem 0'}}
					>
						<ClayCheckbox
							checked={toolbarOptions.checked}
							data-testid="checkAllButton"
							indeterminate={toolbarOptions.indeterminateCheckbox}
							label={toolbarOptions.label}
							onChange={({target}) => {
								handleSelectAll(target.checked);
							}}
						/>
					</ClayManagementToolbar.Item>

					{!toolbarOptions.active ? (
						<>
							<ClayManagementToolbar.Item>
								<strong className="ml-0 mr-0 navbar-text">
									{Liferay.Language.get('filter-by')}
								</strong>
							</ClayManagementToolbar.Item>

							<SLAStatusFilter />

							<ProcessStatusFilter />

							{completedStatusSelected && <TimeRangeFilter />}

							<ProcessStepFilter />

							<AssigneeFilter />
						</>
					) : (
						!selectAll && (
							<>
								<ClayManagementToolbar.Item>
									<span className="ml-0 mr-0 navbar-text">
										{sub(
											Liferay.Language.get(
												'x-of-x-items-selected'
											),
											[selectedItems.length, totalCount]
										)}
									</span>
								</ClayManagementToolbar.Item>

								<ClayManagementToolbar.Item>
									<button
										className="btn btn-link btn-sm font-weight-bold pl-0 text-primary"
										data-testid="selectRemainingButton"
										onClick={() => {
											handleSelectAll(true);
										}}
									>
										{Liferay.Language.get(
											'select-all-remanining-items'
										)}
									</button>
								</ClayManagementToolbar.Item>
							</>
						)
					)}
				</ClayManagementToolbar.ItemList>

				{toolbarOptions.active && (
					<ClayManagementToolbar.Item>
						<div
							className="autofit-col"
							data-testid="headerQuickAction"
						>
							<QuickActionKebab items={kebabItems} />
						</div>
					</ClayManagementToolbar.Item>
				)}
			</ClayManagementToolbar>

			<FilterResultsBar filters={getFilters()} totalCount={totalCount} />
		</Request.Success>
	);
};

export {Header};
