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

import MDFRequestActivityDTO from '../../../common/interfaces/dto/mdfRequestActivityDTO';
import LiferayPicklist from '../../../common/interfaces/liferayPicklist';
import MDFClaim from '../../../common/interfaces/mdfClaim';
import {Status} from '../../../common/utils/constants/status';

const getInitialFormValues = (
	mdfRequestId: number,
	currency: LiferayPicklist,
	activitiesDTO?: MDFRequestActivityDTO[],
	totalMDFRequestAmount?: number,
	mdfClaim?: MDFClaim
): MDFClaim => ({
	...mdfClaim,
	activities: activitiesDTO?.map((activity) => {
		const mdfClaimActivity = mdfClaim?.activities?.find(
			(claimActivity) =>
				claimActivity.r_actToMDFClmActs_c_activityId === activity.id
		);

		if (mdfClaimActivity) {
			return {
				...mdfClaimActivity,
				activityStatus: activity.activityStatus,
				budgets: activity?.actToBgts?.map((budget) => {
					const mdfClaimBudget = mdfClaimActivity.budgets?.find(
						(claimBudget) =>
							claimBudget.r_bgtToMDFClmBgts_c_budgetId ===
							budget.id
					);

					if (mdfClaimBudget) {
						return {
							...mdfClaimBudget,
							r_bgtToMDFClmBgts_c_budgetId: budget.id,
							requestAmount: budget.cost,
						};
					}

					return {
						expenseName: budget.expense.name,
						invoiceAmount: budget.cost,
						r_bgtToMDFClmBgts_c_budgetId: budget.id,
						requestAmount: budget.cost,
						selected: false,
					};
				}),
				claimed: activity.actToMDFClmActs
					?.map((mdfClaimActivity) => {
						return (
							mdfClaimActivity?.r_mdfClmToMDFClmActs_c_mdfClaim
								?.mdfClaimStatus.key !== 'draft' &&
							mdfClaimActivity.selected
						);
					})
					.includes(true),
				name: activity.name,
				r_actToMDFClmActs_c_activityId: activity.id,
				selected:
					mdfClaim?.mdfClaimStatus.key === Status.DRAFT.key
						? false
						: mdfClaimActivity.selected,
			};
		}

		return {
			activityStatus: activity.activityStatus,
			budgets: activity?.actToBgts?.map((budget) => {
				return {
					expenseName: budget.expense.name,
					invoiceAmount: budget.cost,
					r_bgtToMDFClmBgts_c_budgetId: budget.id,
					requestAmount: budget.cost,
					selected: false,
				};
			}),
			claimed: activity.actToMDFClmActs
				?.map((mdfClaimActivity) => {
					return (
						mdfClaimActivity?.r_mdfClmToMDFClmActs_c_mdfClaim
							?.mdfClaimStatus.key !== 'draft' &&
						mdfClaimActivity.selected
					);
				})
				.includes(true),
			currency: activity.currency,
			metrics: '',
			name: activity.name,
			r_actToMDFClmActs_c_activityId: activity.id,
			selected: false,
			totalCost: 0,
		};
	}),
	currency: mdfClaim?.currency ? mdfClaim?.currency : currency,
	mdfClaimStatus: mdfClaim?.mdfClaimStatus
		? mdfClaim.mdfClaimStatus
		: Status.PENDING,
	r_mdfReqToMDFClms_c_mdfRequestId: mdfRequestId,
	totalMDFRequestedAmount: mdfClaim?.totalMDFRequestedAmount
		? mdfClaim.totalMDFRequestedAmount
		: totalMDFRequestAmount,
});

export default getInitialFormValues;
