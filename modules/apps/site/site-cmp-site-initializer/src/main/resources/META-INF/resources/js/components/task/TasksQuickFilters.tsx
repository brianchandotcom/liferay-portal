/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClaySticker from '@clayui/sticker';
import classNames from 'classnames';
import React, {useState} from 'react';

import './TasksQuickFilters.scss';

function StatisticButton({
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

const TASK_QUICK_FILTER_TYPES = {
	BLOCKED: 'blocked',
	IN_PROGRESS: 'inProgress',
	OVERDUE: 'overdue',
	TOTAL: 'total',
};

export default function TasksQuickFilters({
	blockedCount,
	inProgressCount,
	overdueCount,
	totalCount,
}: {
	blockedCount: number;
	inProgressCount: number;
	overdueCount: number;
	totalCount: number;
}) {
	const [active, setActive] = useState('');

	const handleClick = (value: string) => {
		setActive(value);

		// TODO: Apply filter

	};

	return (
		<div className="lfr-cmp__tasks-quick-filters-container">
			<ClayLayout.ContainerFluid className="px-0" size={false}>
				<ClayLayout.Row>
					<ClayLayout.Col className="px-2" size={3}>
						<StatisticButton
							active={active === TASK_QUICK_FILTER_TYPES.TOTAL}
							count={totalCount}
							displayType="unstyled"
							icon="task-status"
							label={Liferay.Language.get('total-tasks')}
							onClick={() =>
								handleClick(TASK_QUICK_FILTER_TYPES.TOTAL)
							}
						/>
					</ClayLayout.Col>

					<ClayLayout.Col className="px-2" size={3}>
						<StatisticButton
							active={
								active === TASK_QUICK_FILTER_TYPES.IN_PROGRESS
							}
							count={inProgressCount}
							displayType="info"
							icon="analytics"
							label={Liferay.Language.get('in-progress')}
							onClick={() =>
								handleClick(TASK_QUICK_FILTER_TYPES.IN_PROGRESS)
							}
						/>
					</ClayLayout.Col>

					<ClayLayout.Col className="px-2" size={3}>
						<StatisticButton
							active={active === TASK_QUICK_FILTER_TYPES.BLOCKED}
							count={blockedCount}
							displayType="danger"
							icon="block"
							label={Liferay.Language.get('blocked')}
							onClick={() =>
								handleClick(TASK_QUICK_FILTER_TYPES.BLOCKED)
							}
						/>
					</ClayLayout.Col>

					<ClayLayout.Col className="px-2" size={3}>
						<StatisticButton
							active={active === TASK_QUICK_FILTER_TYPES.OVERDUE}
							count={overdueCount}
							displayType="warning"
							icon="exclamation-full"
							label={Liferay.Language.get('overdue')}
							onClick={() =>
								handleClick(TASK_QUICK_FILTER_TYPES.OVERDUE)
							}
						/>
					</ClayLayout.Col>
				</ClayLayout.Row>
			</ClayLayout.ContainerFluid>
		</div>
	);
}
