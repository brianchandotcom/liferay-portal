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

import {OpportunityType} from '../../../enums/opportunityType';
import Opportunity from '../../../interfaces/opportunity';
import {stageStatus} from './constants/stageStatusRenewalsChart';

const EXPIRATION_DAYS = 30;
const TODAY = new Date();
const MILISECONDS_PER_DAY = 1000 * 3600 * 24;

export default function getFilteredRenewals(data: any) {
	const newRenewalsArray: Opportunity[] = [];

	data?.items?.forEach((renewal: Opportunity) => {
		const expirationDate = new Date(renewal.closeDate);
		const differenceOfTime = expirationDate.getTime() - TODAY.getTime();

		const differenceOfDays =
			Math.floor(differenceOfTime / MILISECONDS_PER_DAY) + 1;

		if (
			differenceOfDays > 0 &&
			differenceOfDays <= EXPIRATION_DAYS &&
			renewal.type !== OpportunityType.NEW_BUSINESS &&
			renewal.stage !== stageStatus.REJECTED &&
			renewal.stage !== stageStatus.ROLLED_INTO_ANOTHER_OPPORTUNITY &&
			renewal.stage !== stageStatus.CLOSEDLOST &&
			renewal.stage !== stageStatus.DISQUALIFIED
		) {
			newRenewalsArray.push({
				expirationDays: differenceOfDays,
				...renewal,
			});
		}
	});

	return newRenewalsArray.slice(0, 4);
}
