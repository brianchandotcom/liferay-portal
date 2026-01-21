/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayProgressBar from '@clayui/progress-bar';
import {FDS_EVENT} from '@liferay/frontend-data-set-web';
import classNames from 'classnames';
import {fetch} from 'frontend-js-web';
import React, {useCallback, useEffect, useState} from 'react';

import './TasksOverview.scss';
import {
	TASK_QUICK_FILTER_TYPES,
	UPDATE_TASKS_QUICK_FILTER_EVENT,
} from './TasksQuickFilters';

function StatisticButton({
	count,
	displayType,
	icon,
	label,
	onClick,
}: {
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
			className="statistic-button"
			displayType="secondary"
			onClick={onClick}
		>
			<ClayLabel
				className={classNames('inline-item inline-item-before text-3', {
					'border-0 text-dark px-0': displayType === 'unstyled',
				})}
				displayType={displayType}
			>
				{label}
			</ClayLabel>

			<span className={`inline-item inline-item text-${displayType}`}>
				<ClayIcon symbol={icon} />
			</span>

			<div className="text-7 text-dark text-left">{count || 0}</div>
		</ClayButton>
	);
}

export default function TasksOverview({cmpProjectId}: {cmpProjectId: string}) {
	const [blockedCount, setBlockedCount] = useState(0);
	const [completionRate, setCompletionRate] = useState(0);
	const [inProgressCount, setInProgressCount] = useState(0);
	const [overdueCount, setOverdueCount] = useState(0);
	const [totalCount, setTotalCount] = useState(0);

	const [loading, setLoading] = useState(true);

	const handleClick = (quickFilterType: string) => {
		const projectNavigationTabsFragment = document.querySelector(
			'[data-layout-structure-item-id="cmp-project-navigation-tabs"]'
		);

		if (projectNavigationTabsFragment) {
			const tabButtons =
				projectNavigationTabsFragment.querySelectorAll(
					'button[role="tab"]'
				);

			const tasksTab = Array.from(tabButtons).find(
				(button) =>
					button?.textContent?.trim() ===
					Liferay.Language.get('tasks')
			);

			if (tasksTab instanceof HTMLElement) {
				tasksTab.click();
			}

			Liferay.fire(UPDATE_TASKS_QUICK_FILTER_EVENT, {
				type: quickFilterType,
			});
		}
	};

	const fetchCounts = useCallback(async () => {
		fetch(`/o/cmp/projects/${cmpProjectId}`, {
			method: 'GET',
		}).then(async (response: Response) => {
			const data = await response.json();

			setBlockedCount(data.blockedCount);
			setCompletionRate(data.completionRate);
			setInProgressCount(data.inProgressCount);
			setOverdueCount(data.overdueCount);
			setTotalCount(data.totalCount);
		});
	}, [cmpProjectId]);

	useEffect(() => {
		fetchCounts();

		setLoading(false);
	}, [fetchCounts]);

	useEffect(() => {
		Liferay.on(FDS_EVENT.DISPLAY_UPDATED, fetchCounts);

		return () => {
			Liferay.detach(FDS_EVENT.DISPLAY_UPDATED, fetchCounts);
		};
	}, [fetchCounts]);

	return (
		<div className="lfr-cmp__tasks-overview-container">
			{loading ? (
				<ClayLoadingIndicator />
			) : (
				<>
					<div className="align-items-center d-flex justify-content-between mb-2">
						<h5 className="c-m-0">
							{Liferay.Language.get('tasks-overview')}
						</h5>

						<ClayButton
							className="c-p-0 text-3 text-decoration-underline text-weight-semi-bold"
							displayType="link"
							onClick={() =>
								handleClick(TASK_QUICK_FILTER_TYPES.TOTAL)
							}
						>
							{Liferay.Language.get('view-all-tasks')}
						</ClayButton>
					</div>

					<ClayProgressBar
						className="c-mb-3"
						value={completionRate}
					/>

					<ClayLayout.ContainerFluid className="c-px-0" size={false}>
						<ClayLayout.Row>
							<ClayLayout.Col className="c-px-2" size={3}>
								<StatisticButton
									count={totalCount}
									displayType="unstyled"
									icon="task-status"
									label={Liferay.Language.get('total-tasks')}
									onClick={() =>
										handleClick(
											TASK_QUICK_FILTER_TYPES.TOTAL
										)
									}
								/>
							</ClayLayout.Col>

							<ClayLayout.Col className="c-px-2" size={3}>
								<StatisticButton
									count={inProgressCount}
									displayType="info"
									icon="analytics"
									label={Liferay.Language.get('in-progress')}
									onClick={() =>
										handleClick(
											TASK_QUICK_FILTER_TYPES.IN_PROGRESS
										)
									}
								/>
							</ClayLayout.Col>

							<ClayLayout.Col className="c-px-2" size={3}>
								<StatisticButton
									count={blockedCount}
									displayType="danger"
									icon="block"
									label={Liferay.Language.get('blocked')}
									onClick={() =>
										handleClick(
											TASK_QUICK_FILTER_TYPES.BLOCKED
										)
									}
								/>
							</ClayLayout.Col>

							<ClayLayout.Col className="c-px-2" size={3}>
								<StatisticButton
									count={overdueCount}
									displayType="warning"
									icon="exclamation-full"
									label={Liferay.Language.get('overdue')}
									onClick={() =>
										handleClick(
											TASK_QUICK_FILTER_TYPES.OVERDUE
										)
									}
								/>
							</ClayLayout.Col>
						</ClayLayout.Row>
					</ClayLayout.ContainerFluid>
				</>
			)}
		</div>
	);
}
