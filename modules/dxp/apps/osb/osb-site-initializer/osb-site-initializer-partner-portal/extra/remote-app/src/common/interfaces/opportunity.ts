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

import {OpportunityType} from '../enums/opportunityType';
import LiferayObject from './liferayObject';

export default interface Opportunity extends LiferayObject {
	accountExternalReferenceCode: string;
	accountName: string;
	amount: number;
	closeDate: string;
	currency: any;
	expirationDays?: number;
	externalReferenceCode: string;
	growthArr: number;
	opportunityName: string;
	opportunityOwner: string;
	ownerName: string;
	partnerAccountName: string;
	renewalArr: number;
	stage: string;
	status: any;
	type: OpportunityType;
}
