/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClaySticker from '@clayui/sticker';
import {
	FDS_EVENT,
	IBaseFilterState,
	IFDSState,
} from '@liferay/frontend-data-set-web';
import {useLiferayState} from '@liferay/frontend-js-state-web/react';
import classNames from 'classnames';
import {fetch} from 'frontend-js-web';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import {cmpTasksFDSAtom} from '../props_transformer/atoms';

import './TasksQuickFilters.scss';

export const UPDATE_TASKS_QUICK_FILTER_EVENT = 'cmp-update-tasks-quick-filter';

export const TASK_QUICK_FILTER_TYPES = {
	BLOCKED: 'blocked',
	IN_PROGRESS: 'inProgress',
	OVERDUE: 'overdue',
	TOTAL: 'total',
};

function QuickFilterButton({
	active,
	count,
	displayType,
	icon,
	label,
	onClick,
}: {
	active: boolean;
	count: number;
	displayType:
		| 'secondary'
		| 'success'
		| 'warning'
		| 'danger'
		| 'info'
		| 'unstyled';
	icon: string;
	label: string;
	onClick?: () => void;
}) {
	return (
		<ClayButton
			className={classNames('quick-filter-button', {
				active,
			})}
			displayType="secondary"
			onClick={onClick}
		>
			<div className="align-items-center d-flex">
				<ClaySticker
					className="rounded"
					displayType={displayType}
					size="lg"
				>
					<ClayIcon symbol={icon} />
				</ClaySticker>

				<div className="ml-2">
					<div className="text-dark">{count || 0}</div>

					<div className="text-3 text-secondary text-weight-normal">
						{label}
					</div>
				</div>
			</div>
		</ClayButton>
	);
}

export default function TasksQuickFilters({
	blockedCountURL,
	inProgressCountURL,
	overdueCountURL,
	totalCountURL,
}: {
	blockedCountURL: string;
	inProgressCountURL: string;
	overdueCountURL: string;
	totalCountURL: string;
}) {
	const [activeQuickFilter, setActiveQuickFilter] = useState<string | null>(
		null
	);

	const [blockedCount, setBlockedCount] = useState(0);
	const [inProgressCount, setInProgressCount] = useState(0);
	const [overdueCount, setOverdueCount] = useState(0);
	const [totalCount, setTotalCount] = useState(0);

	const [loading, setLoading] = useState(true);

	const [tasksFDSState, setTasksFDSState] =
		useLiferayState<IFDSState>(cmpTasksFDSAtom);

	const isQuickFilterChangeRef = useRef(false);

	const handleTotalTasksClick = () => {
		setActiveQuickFilter(TASK_QUICK_FILTER_TYPES.TOTAL);

		setTasksFDSState({
			...tasksFDSState,
			filters: tasksFDSState.filters.map((filter: IBaseFilterState) => {
				return {
					...filter,
					active: false,
					selectedData: {
						exclude: false,
						selectedItems: [],
					},
				};
			}),
		});

		isQuickFilterChangeRef.current = true;
	};

	const handleOverdueClick = () => {
		setActiveQuickFilter(TASK_QUICK_FILTER_TYPES.OVERDUE);

		setTasksFDSState({
			...tasksFDSState,
			filters: tasksFDSState.filters.map((filter: IBaseFilterState) => {
				if (filter.id === 'state') {
					return {
						...filter,
						active: true,
						selectedData: {
							exclude: true,
							selectedItems: [
								{
									label: Liferay.Language.get('done'),
									value: 'done',
								},
							],
						},
					};
				}

				if (filter.id === 'dueDate') {
					const currentDate = new Date();

					return {
						...filter,
						active: true,
						selectedData: {
							exclude: false,
							from: null,
							to: {
								day: currentDate.getDate(),
								month: currentDate.getMonth() + 1,
								year: currentDate.getFullYear(),
							},
						},
					};
				}

				return {
					...filter,
					active: false,
					selectedData: {
						exclude: false,
						selectedItems: [],
					},
				};
			}),
		});

		isQuickFilterChangeRef.current = true;
	};

	const handleBlockedClick = () => {
		setActiveQuickFilter(TASK_QUICK_FILTER_TYPES.BLOCKED);

		setTasksFDSState({
			...tasksFDSState,

			filters: tasksFDSState.filters.map((filter: IBaseFilterState) => {
				if (filter.id === 'state') {
					return {
						...filter,
						active: true,
						selectedData: {
							exclude: false,
							selectedItems: [
								{
									label: Liferay.Language.get('blocked'),
									value: 'blocked',
								},
							],
						},
					};
				}

				return {
					...filter,
					active: false,
					selectedData: {
						exclude: false,
						selectedItems: [],
					},
				};
			}),
		});

		isQuickFilterChangeRef.current = true;
	};

	const handleInProgressClick = () => {
		setActiveQuickFilter(TASK_QUICK_FILTER_TYPES.IN_PROGRESS);

		setTasksFDSState({
			...tasksFDSState,
			filters: tasksFDSState.filters.map((filter: IBaseFilterState) => {
				if (filter.id === 'state') {
					return {
						...filter,
						active: true,
						selectedData: {
							exclude: false,
							selectedItems: [
								{
									label: Liferay.Language.get('in-progress'),
									value: 'inProgress',
								},
							],
						},
					};
				}

				return {
					...filter,
					active: false,
					selectedData: {
						exclude: false,
						selectedItems: [],
					},
				};
			}),
		});

		isQuickFilterChangeRef.current = true;
	};

	const fetchCounts = useCallback(async () => {
		const fetchJSON = (url: string) =>
			fetch(url).then((response) => response.json());

		const [
			blockedCountData,
			inProgressCountData,
			overdueCountData,
			totalCountData,
		] = await Promise.all([
			fetchJSON(blockedCountURL),
			fetchJSON(inProgressCountURL),
			fetchJSON(overdueCountURL),
			fetchJSON(totalCountURL),
		]);

		setBlockedCount(blockedCountData.totalCount);
		setInProgressCount(inProgressCountData.totalCount);
		setOverdueCount(overdueCountData.totalCount);
		setTotalCount(totalCountData.totalCount);
	}, [blockedCountURL, inProgressCountURL, overdueCountURL, totalCountURL]);

	useEffect(() => {
		fetchCounts();

		setLoading(false);
	}, [fetchCounts]);

	/**
	 * Clear the active quick filter if the filters in the FDS changes.
	 * `isQuickFilterChangeRef` is used to prevent the active quick filter from
	 * immediately clearing when one of the quick filters are clicked.
	 */
	useEffect(() => {
		if (isQuickFilterChangeRef.current) {
			isQuickFilterChangeRef.current = false;

			return;
		}

		setActiveQuickFilter(null);
	}, [tasksFDSState.filters]);

	/**
	 * Listens for the `UPDATE_TASKS_QUICK_FILTER_EVENT` to allow external
	 * components, such as `TasksOverview`, to programmatically update the
	 * active quick filter.
	 */
	useEffect(() => {
		const handleUpdateTasksQuickFilter = (event: {type: string}) => {
			const {type} = event;

			if (type === TASK_QUICK_FILTER_TYPES.BLOCKED) {
				handleBlockedClick();
			}
			else if (type === TASK_QUICK_FILTER_TYPES.IN_PROGRESS) {
				handleInProgressClick();
			}
			else if (type === TASK_QUICK_FILTER_TYPES.OVERDUE) {
				handleOverdueClick();
			}
			else if (type === TASK_QUICK_FILTER_TYPES.TOTAL) {
				handleTotalTasksClick();
			}
		};

		Liferay.on(
			UPDATE_TASKS_QUICK_FILTER_EVENT,
			handleUpdateTasksQuickFilter
		);

		Liferay.on(FDS_EVENT.DISPLAY_UPDATED, fetchCounts);

		return () => {
			Liferay.detach(
				UPDATE_TASKS_QUICK_FILTER_EVENT,
				handleUpdateTasksQuickFilter
			);

			Liferay.detach(FDS_EVENT.DISPLAY_UPDATED, fetchCounts);
		};
	}, []);

	return (
		<div className="lfr-cmp__tasks-quick-filters-container">
			{loading ? (
				<ClayLoadingIndicator />
			) : (
				<ClayLayout.ContainerFluid className="px-0" size={false}>
					<ClayLayout.Row>
						<ClayLayout.Col className="px-2" size={3}>
							<QuickFilterButton
								active={
									activeQuickFilter ===
									TASK_QUICK_FILTER_TYPES.TOTAL
								}
								count={totalCount}
								displayType="unstyled"
								icon="task-status"
								label={Liferay.Language.get('total-tasks')}
								onClick={handleTotalTasksClick}
							/>
						</ClayLayout.Col>

						<ClayLayout.Col className="px-2" size={3}>
							<QuickFilterButton
								active={
									activeQuickFilter ===
									TASK_QUICK_FILTER_TYPES.IN_PROGRESS
								}
								count={inProgressCount}
								displayType="info"
								icon="analytics"
								label={Liferay.Language.get('in-progress')}
								onClick={handleInProgressClick}
							/>
						</ClayLayout.Col>

						<ClayLayout.Col className="px-2" size={3}>
							<QuickFilterButton
								active={
									activeQuickFilter ===
									TASK_QUICK_FILTER_TYPES.BLOCKED
								}
								count={blockedCount}
								displayType="danger"
								icon="block"
								label={Liferay.Language.get('blocked')}
								onClick={handleBlockedClick}
							/>
						</ClayLayout.Col>

						<ClayLayout.Col className="px-2" size={3}>
							<QuickFilterButton
								active={
									activeQuickFilter ===
									TASK_QUICK_FILTER_TYPES.OVERDUE
								}
								count={overdueCount}
								displayType="warning"
								icon="exclamation-full"
								label={Liferay.Language.get('overdue')}
								onClick={handleOverdueClick}
							/>
						</ClayLayout.Col>
					</ClayLayout.Row>
				</ClayLayout.ContainerFluid>
			)}
		</div>
	);
}
