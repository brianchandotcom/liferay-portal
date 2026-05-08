/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClaySticker from '@clayui/sticker';
import {IBaseFilterState, IFDSState} from '@liferay/frontend-data-set-web';
import {useLiferayState} from '@liferay/frontend-js-state-web/react';
import classNames from 'classnames';
import {fetch} from 'frontend-js-web';
import React, {
	ComponentProps,
	useCallback,
	useEffect,
	useRef,
	useState,
} from 'react';

import {
	CMSSiteInitializerFDSNames,
	EXPIRING_SOON_THRESHOLD_DAYS,
	FDS_EVENT_DISPLAY_UPDATED,
	FDS_FILTER_ID,
	WORKFLOW_STATUS,
} from '../../common/utils/constants';
import toDatePart from '../../common/utils/toDatePart';
import {allFDSAtom} from './atoms';
import {QUICK_FILTER_TYPES, QuickFilterType} from './constants';

import './AllQuickFilters.scss';

interface QuickFilterCounts {
	expired: number;
	expiringSoon: number;
	inDraft: number;
	reviewDateOverdue: number;
	total: number;
}

function clearedFilter(filter: IBaseFilterState): IBaseFilterState {
	return {
		...filter,
		active: false,
		odataFilterString: undefined,
		selectedData: undefined,
	};
}

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
	displayType: ComponentProps<typeof ClaySticker>['displayType'];
	icon: string;
	label: string;
	onClick: () => void;
}) {
	return (
		<ClayButton
			className={classNames('quick-filter-button', {active})}
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
					<div className="text-dark">{count}</div>

					<div className="text-3 text-secondary text-weight-normal">
						{label}
					</div>
				</div>
			</div>
		</ClayButton>
	);
}

export default function AllQuickFilters() {
	const [activeQuickFilter, setActiveQuickFilter] =
		useState<QuickFilterType | null>(null);

	const [allFDSState, setAllFDSState] =
		useLiferayState<IFDSState>(allFDSAtom);

	const isQuickFilterChangeRef = useRef(false);

	const [counts, setCounts] = useState<QuickFilterCounts>({
		expired: 0,
		expiringSoon: 0,
		inDraft: 0,
		reviewDateOverdue: 0,
		total: 0,
	});

	const fetchCounts = useCallback(() => {
		fetch('/o/headless-cms/v1.0/asset-statistics', {
			headers: {
				Accept: 'application/json',
			},
		})
			.then((response) => {
				if (!response.ok) {
					throw new Error(`HTTP ${response.status}`);
				}

				return response.json();
			})
			.then((data) => {
				setCounts({
					expired: data.expiredCount ?? 0,
					expiringSoon: data.expiringSoonCount ?? 0,
					inDraft: data.inDraftCount ?? 0,
					reviewDateOverdue: data.reviewDateOverdueCount ?? 0,
					total: data.totalCount ?? 0,
				});
			})
			.catch((error) => {
				console.error('Failed to fetch asset statistics', error);
			});
	}, []);

	useEffect(() => {
		fetchCounts();

		const handleDisplayUpdated = (event?: {id?: string}) => {
			if (event?.id?.endsWith(CMSSiteInitializerFDSNames.ALL_SECTION)) {
				fetchCounts();
			}
		};

		Liferay.on(FDS_EVENT_DISPLAY_UPDATED, handleDisplayUpdated);

		return () => {
			Liferay.detach(FDS_EVENT_DISPLAY_UPDATED, handleDisplayUpdated);
		};
	}, [fetchCounts]);

	const applyQuickFilter = useCallback(
		(
			quickFilterType: QuickFilterType,
			filterUpdates: Record<string, IBaseFilterState['selectedData']>
		) => {
			setActiveQuickFilter(quickFilterType);

			setAllFDSState({
				...allFDSState,
				filters: allFDSState.filters.map((filter: IBaseFilterState) => {
					const selectedData = filterUpdates[filter.id];

					if (selectedData) {
						return {
							...filter,
							active: true,
							selectedData,
						};
					}

					return clearedFilter(filter);
				}),
			});

			isQuickFilterChangeRef.current = true;
		},
		[allFDSState, setAllFDSState]
	);

	const handleInDraftClick = useCallback(() => {
		applyQuickFilter(QUICK_FILTER_TYPES.IN_DRAFT, {
			[FDS_FILTER_ID.STATUS]: {
				exclude: false,
				selectedItems: [
					{
						label: Liferay.Language.get('draft'),
						value: WORKFLOW_STATUS.DRAFT,
					},
				],
			},
		});
	}, [applyQuickFilter]);

	const handleExpiringSoonClick = useCallback(() => {
		const now = new Date();

		const threshold = new Date();

		threshold.setDate(now.getDate() + EXPIRING_SOON_THRESHOLD_DAYS);

		applyQuickFilter(QUICK_FILTER_TYPES.EXPIRING_SOON, {
			[FDS_FILTER_ID.DATE_EXPIRATION]: {
				exclude: false,
				from: toDatePart(now),
				to: toDatePart(threshold),
			},
			[FDS_FILTER_ID.STATUS]: {
				exclude: false,
				selectedItems: [
					{
						label: Liferay.Language.get('approved'),
						value: WORKFLOW_STATUS.APPROVED,
					},
				],
			},
		});
	}, [applyQuickFilter]);

	const handleExpiredClick = useCallback(() => {
		applyQuickFilter(QUICK_FILTER_TYPES.EXPIRED, {
			[FDS_FILTER_ID.STATUS]: {
				exclude: false,
				selectedItems: [
					{
						label: Liferay.Language.get('expired'),
						value: WORKFLOW_STATUS.EXPIRED,
					},
				],
			},
		});
	}, [applyQuickFilter]);

	const handleReviewDateOverdueClick = useCallback(() => {
		applyQuickFilter(QUICK_FILTER_TYPES.REVIEW_DATE_OVERDUE, {
			[FDS_FILTER_ID.DATE_REVIEW]: {
				exclude: false,
				from: null,
				to: toDatePart(new Date()),
			},
		});
	}, [applyQuickFilter]);

	useEffect(() => {
		if (isQuickFilterChangeRef.current) {
			isQuickFilterChangeRef.current = false;

			return;
		}

		setActiveQuickFilter(null);
	}, [allFDSState.filters]);

	if (counts.total === 0) {
		return null;
	}

	return (
		<div className="all-quick-filters-container">
			<ClayLayout.ContainerFluid
				className="c-pb-4 c-pt-2 c-px-4"
				size={false}
			>
				<ClayLayout.Row>
					<ClayLayout.Col className="c-px-2" size={3}>
						<QuickFilterButton
							active={
								activeQuickFilter ===
								QUICK_FILTER_TYPES.IN_DRAFT
							}
							count={counts.inDraft}
							displayType="secondary"
							icon="pencil"
							label={Liferay.Language.get('in-draft')}
							onClick={handleInDraftClick}
						/>
					</ClayLayout.Col>

					<ClayLayout.Col className="c-px-2" size={3}>
						<QuickFilterButton
							active={
								activeQuickFilter ===
								QUICK_FILTER_TYPES.EXPIRING_SOON
							}
							count={counts.expiringSoon}
							displayType="warning"
							icon="flag-full"
							label={Liferay.Language.get('expiring-soon')}
							onClick={handleExpiringSoonClick}
						/>
					</ClayLayout.Col>

					<ClayLayout.Col className="c-px-2" size={3}>
						<QuickFilterButton
							active={
								activeQuickFilter === QUICK_FILTER_TYPES.EXPIRED
							}
							count={counts.expired}
							displayType="danger"
							icon="warning-full"
							label={Liferay.Language.get('expired')}
							onClick={handleExpiredClick}
						/>
					</ClayLayout.Col>

					<ClayLayout.Col className="c-px-2" size={3}>
						<QuickFilterButton
							active={
								activeQuickFilter ===
								QUICK_FILTER_TYPES.REVIEW_DATE_OVERDUE
							}
							count={counts.reviewDateOverdue}
							displayType="info"
							icon="date-time"
							label={Liferay.Language.get('review-date-overdue')}
							onClick={handleReviewDateOverdueClick}
						/>
					</ClayLayout.Col>
				</ClayLayout.Row>
			</ClayLayout.ContainerFluid>
		</div>
	);
}
