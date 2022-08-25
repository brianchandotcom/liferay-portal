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

import {MDFColumnKey} from '../../../common/enums/mdfColumnKey';
import useGetMDFRequest from '../../../common/services/liferay/object/mdf-requests/useGetMDFRequest';
import {customFormatDateOptions} from '../../../common/utils/constants/customFormatDateOptions';
import getDateCustomFormat from '../../../common/utils/getDateCustomFormat';
import getIntlNumberFormat from '../../../common/utils/getIntlNumberFormat';

const getActivityPeriod = (minDateActivity?: Date, maxDateActivity?: Date) => {
	if (minDateActivity && maxDateActivity) {
		const startDate = getDateCustomFormat(
			minDateActivity,
			customFormatDateOptions.SHORT_MONTH_YEAR
		);

		const endDate = getDateCustomFormat(
			maxDateActivity,
			customFormatDateOptions.SHORT_MONTH
		);

		return {
			[MDFColumnKey.activityPeriod]: `${startDate} - ${endDate}`,
		};
	}
};

const getBudgetInfos = (
	totalCostOfExpense: number | undefined,
	totalRequested: number | undefined
) => {
	if (totalCostOfExpense && totalRequested) {
		return {
			[MDFColumnKey.totalCost]: getIntlNumberFormat().format(
				totalCostOfExpense
			),
			[MDFColumnKey.requested]: getIntlNumberFormat().format(
				totalRequested
			),
		};
	}
};

const getDates = (dateCreated?: Date, dateModified?: Date) => {
	if (dateCreated && dateModified) {
		const dateSubmitted = getDateCustomFormat(
			dateCreated,
			customFormatDateOptions.SHORT_MONTH
		);

		const lastModified = getDateCustomFormat(
			dateModified,
			customFormatDateOptions.SHORT_MONTH
		);

		return {
			[MDFColumnKey.dateSubmitted]: `${dateSubmitted}`,
			[MDFColumnKey.lastModified]: `${lastModified}`,
		};
	}
};

export default function useGetMDFRequestListItems() {
	const swr = useGetMDFRequest();

	return {
		...swr,
		data: swr.data?.items.map((item) => {
			return {
				[MDFColumnKey.id]: `Request-${item.id}`,
				...getActivityPeriod(
					item.minDateActivity,
					item.maxDateActivity
				),
				[MDFColumnKey.partner]:
					item.r_accountToMDFRequests_accountEntry?.name,
				...getDates(item.dateCreated, item.dateModified),
				...getBudgetInfos(
					item.totalCostOfExpense,
					item.totalMdfRequestAmount
				),
			};
		}),
	};
}
