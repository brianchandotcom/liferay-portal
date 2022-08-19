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

import useGetMDFRequest from '../../../common/services/liferay/object/mdf-requests/useGetMDFRequest';
import {customFormatDateOptions} from '../../../common/utils/constants/customFormatDateOptions';
import getDateCustomFormat from '../../../common/utils/getDateCustomFormat';
import getIntlNumberFormat from '../../../common/utils/getIntlNumberFormat';

const getActivityPeriod = (
	minDateActivity: Date | undefined,
	maxDateActivity: Date | undefined
) => {
	if (minDateActivity && maxDateActivity) {
		const startDate = getDateCustomFormat(
			minDateActivity,
			customFormatDateOptions.SHORT_MONTH
		);
		const endDate = getDateCustomFormat(
			maxDateActivity,
			customFormatDateOptions.SHORT_MONTH_YEAR
		);

		return {
			activityPeriod: `${startDate} - ${endDate}`,
		};
	}
};

const getBudgetInfos = (
	totalCostOfExpense: number | undefined,
	totalRequested: number | undefined
) => {
	if (totalCostOfExpense && totalRequested) {
		return {
			totalCostOfExpense: getIntlNumberFormat().format(
				totalCostOfExpense
			),
			totalRequested: getIntlNumberFormat().format(totalRequested),
		};
	}
};

export default function useGetMDFRequestListItems() {
	const swr = useGetMDFRequest();

	return {
		...swr,
		data: swr.data?.items.map((item) => {
			return {
				...getActivityPeriod(
					item.minDateActivity,
					item.maxDateActivity
				),
				...getBudgetInfos(item.totalCostOfExpense, item.totalRequested),
				id: `Request-${item.id}`,
			};
		}),
	};
}
