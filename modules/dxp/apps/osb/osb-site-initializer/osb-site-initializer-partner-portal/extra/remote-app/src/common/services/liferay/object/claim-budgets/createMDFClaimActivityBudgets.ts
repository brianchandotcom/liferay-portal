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

import {Liferay} from '../..';
import MDFClaimBudget from '../../../../interfaces/mdfClaimBudget';
import getDTOFromMDFClaimBudget from '../../../../utils/dto/mdf-claim-budget/getDTOFromMDFClaimBudget';
import {LiferayAPIs} from '../../common/enums/apis';
import liferayFetcher from '../../common/utils/fetcher';

export default async function createMDFClaimActivityBudgets(
	activityId: number,
	budgets: MDFClaimBudget[],
	mdfClaimId?: number
) {
	return await Promise.all(
		budgets.map((budget) => {
			if (budget.invoice) {
				return liferayFetcher.post(
					`/o/${LiferayAPIs.OBJECT}/mdfclaimbudgets`,
					Liferay.authToken,
					getDTOFromMDFClaimBudget(budget, activityId, mdfClaimId)
				);
			}
		})
	);
}
