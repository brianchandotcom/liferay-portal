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

import mdfClaimActivity from '../../../common/interfaces/mdfClaimActivity';

export default function getTotalBudgetByClaim(
	mdfRequestActivities: mdfClaimActivity[]
) {
	return mdfRequestActivities.reduce(
		(previousValue: number, currentValue: mdfClaimActivity) => {
			let sumBudgets = 0;
			if (currentValue?.checkedPanel) {
				sumBudgets =
					currentValue.mdfClaimBudgets &&
					currentValue.mdfClaimBudgets.reduce(
						(previousValue, currentValue) =>
							previousValue + +(currentValue.cost || 0),
						0
					);
			}

			return previousValue + +sumBudgets;
		},
		0
	);
}
