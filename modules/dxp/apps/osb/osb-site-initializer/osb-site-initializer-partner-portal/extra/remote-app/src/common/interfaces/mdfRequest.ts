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

import LiferayAccountBrief from './liferayAccountBrief';
import LiferayObject from './liferayObject';
import LiferayPicklist from './liferayPicklist';
import MDFRequestActivity from './mdfRequestActivity';

export default interface MDFRequest extends Partial<LiferayObject> {
	accountExternalReferenceCode?: string;
	activities: MDFRequestActivity[];
	additionalOption: LiferayPicklist;
	company?: LiferayAccountBrief;
	currency: LiferayPicklist;
	emailAddress?: string;
	externalReferenceCode?: string;
	externalReferenceCodeSF?: string;
	liferayBusinessSalesGoals?: string[];
	liferayBusinessSalesGoalsOther?: string;
	liferaysUserIdSF?: number;
	maxDateActivity?: string;
	mdfRequestStatus: LiferayPicklist;
	minDateActivity?: string;
	overallCampaignDescription: string;
	overallCampaignName: string;
	partnerCountry: LiferayPicklist;
	r_usrToMDFReqs_userId?: number;
	targetAudienceRoles?: string[];
	targetMarkets?: string[];
	totalCostOfExpense: number;
	totalMDFRequestAmount: number;
	totalRequested?: number;
}
