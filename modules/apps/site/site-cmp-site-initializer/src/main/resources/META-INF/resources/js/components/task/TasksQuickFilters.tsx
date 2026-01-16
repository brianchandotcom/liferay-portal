/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClaySticker from '@clayui/sticker';
import {IBaseFilterState, IFDSState} from '@liferay/frontend-data-set-web';
import {useLiferayState} from '@liferay/frontend-js-state-web/react';
import classNames from 'classnames';
import React from 'react';

import {cmpTasksFDSAtom} from '../props_transformer/atoms';

import './TasksQuickFilters.scss';

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

const TASK_QUICK_FILTER_TYPES = {
	BLOCKED: {
		label: Liferay.Language.get('blocked'),
		value: 'blocked',
	},
	IN_PROGRESS: {
		label: Liferay.Language.get('in-progress'),
		value: 'inProgress',
	},
	OVERDUE: {
		label: Liferay.Language.get('overdue'),
		value: 'overdue',
	},
	TOTAL: {
		label: Liferay.Language.get('total-tasks'),
		value: 'total',
	},
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
	const [tasksFDSState, setTasksFDSState] =
		useLiferayState<IFDSState>(cmpTasksFDSAtom);

	const isQuickFilterActive = (quickFilterType: {
		label: string;
		value: string;
	}) => {
		const stateFilter = tasksFDSState.filters.find(
			(filter: IBaseFilterState) => filter.id === 'state'
		);

		return (
			stateFilter?.active &&
			stateFilter.selectedData?.selectedItems?.length === 1 &&
			stateFilter.selectedData.selectedItems[0].value ===
				quickFilterType.value
		);
	};

	const handleClick = (quickFilterType: {label: string; value: string}) => {
		if (isQuickFilterActive(quickFilterType)) {
			setTasksFDSState({
				...tasksFDSState,
				filters: tasksFDSState.filters.map(
					(filter: IBaseFilterState) => {
						return {
							...filter,
							active: false,
							selectedData: {
								exclude: false,
								selectedItems: [],
							},
						};
					}
				),
			});
		}
		else {
			setTasksFDSState({
				...tasksFDSState,
				filters: tasksFDSState.filters.map(
					(filter: IBaseFilterState) => {
						if (filter.id === 'state') {
							return {
								...filter,
								active: true,
								selectedData: {
									exclude: false,
									selectedItems: [quickFilterType],
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
					}
				),
			});
		}
	};

	return (
		<div className="lfr-cmp__tasks-quick-filters-container">
			<ClayLayout.ContainerFluid className="px-0" size={false}>
				<ClayLayout.Row>
					<ClayLayout.Col className="px-2" size={3}>
						<QuickFilterButton
							active={isQuickFilterActive(
								TASK_QUICK_FILTER_TYPES.TOTAL
							)}
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
						<QuickFilterButton
							active={isQuickFilterActive(
								TASK_QUICK_FILTER_TYPES.IN_PROGRESS
							)}
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
						<QuickFilterButton
							active={isQuickFilterActive(
								TASK_QUICK_FILTER_TYPES.BLOCKED
							)}
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
						<QuickFilterButton
							active={isQuickFilterActive(
								TASK_QUICK_FILTER_TYPES.OVERDUE
							)}
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
