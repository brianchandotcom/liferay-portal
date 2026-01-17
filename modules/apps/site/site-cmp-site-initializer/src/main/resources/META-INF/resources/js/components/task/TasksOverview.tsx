/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayProgressBar from '@clayui/progress-bar';
import classNames from 'classnames';
import React from 'react';

import './TasksOverview.scss';
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

export default function TasksOverview({
	blockedCount,
	doneCount,
	inProgressCount,
	overdueCount,
	totalCount,
}: {
	blockedCount: number;
	doneCount: number;
	inProgressCount: number;
	overdueCount: number;
	totalCount: number;
}) {
	const handleClick = () => {
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
		}
	};

	return (
		<div className="lfr-cmp__tasks-overview-container">
			<ClayProgressBar
				className="c-mb-3"
				value={
					totalCount > 0
						? Math.round((doneCount / totalCount) * 100)
						: 0
				}
			/>

			<ClayLayout.ContainerFluid className="px-0" size={false}>
				<ClayLayout.Row>
					<ClayLayout.Col className="px-2" size={3}>
						<StatisticButton
							count={totalCount}
							displayType="unstyled"
							icon="task-status"
							label={Liferay.Language.get('total-tasks')}
							onClick={handleClick}
						/>
					</ClayLayout.Col>

					<ClayLayout.Col className="px-2" size={3}>
						<StatisticButton
							count={inProgressCount}
							displayType="info"
							icon="analytics"
							label={Liferay.Language.get('in-progress')}
							onClick={handleClick}
						/>
					</ClayLayout.Col>

					<ClayLayout.Col className="px-2" size={3}>
						<StatisticButton
							count={blockedCount}
							displayType="danger"
							icon="block"
							label={Liferay.Language.get('blocked')}
							onClick={handleClick}
						/>
					</ClayLayout.Col>

					<ClayLayout.Col className="px-2" size={3}>
						<StatisticButton
							count={overdueCount}
							displayType="warning"
							icon="exclamation-full"
							label={Liferay.Language.get('overdue')}
							onClick={handleClick}
						/>
					</ClayLayout.Col>
				</ClayLayout.Row>
			</ClayLayout.ContainerFluid>
		</div>
	);
}
