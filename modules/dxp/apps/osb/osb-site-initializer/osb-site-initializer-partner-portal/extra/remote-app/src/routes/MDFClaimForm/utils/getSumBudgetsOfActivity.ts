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

import MDFClaim from '../../../common/interfaces/mdfClaim';
import getIntlNumberFormat from '../../../common/utils/getIntlNumberFormat';
import validateClaimBudget from './validateClaimBudget';

export default function getSumBudgetsOfActivity(
	values: MDFClaim,
	currentActivityIndex: number
) {
	const MDFClaimBudgets =
		validateClaimBudget(values, currentActivityIndex) &&
		values.mdfClaimActivities[currentActivityIndex].mdfClaimBudgets.reduce(
			(previousValue, currentValue) =>
				previousValue + +((currentValue && currentValue.cost) || 0),
			0
		);

	return MDFClaimBudgets && getIntlNumberFormat().format(MDFClaimBudgets);
}
