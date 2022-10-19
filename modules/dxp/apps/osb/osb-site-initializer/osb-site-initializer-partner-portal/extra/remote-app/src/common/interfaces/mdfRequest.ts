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

import {RequestStatus} from '../enums/requestStatus';
import LiferayAccountBrief from './liferayAccountBrief';
import LiferayObject from './liferayObject';
import LiferayPicklist from './liferayPicklist';
import MDFRequestActivity from './mdfRequestActivity';

export default interface MDFRequest extends Partial<LiferayObject> {
	accountExternalReferenceCodeSF?: string;
	activities: MDFRequestActivity[];
	additionalOption: LiferayPicklist;
	company: LiferayAccountBrief;
	country: LiferayPicklist;
	liferayBusinessSalesGoals: string[];
	maxDateActivity?: Date;
	minDateActivity?: Date;
	overallCampaignDescription: string;
	overallCampaignName: string;
	requestStatus?: RequestStatus;
	targetAudienceRoles: string[];
	targetMarkets: string[];
	totalCostOfExpense?: number;
	totalMDFRequestAmount?: number;
	totalRequested?: number;
}
