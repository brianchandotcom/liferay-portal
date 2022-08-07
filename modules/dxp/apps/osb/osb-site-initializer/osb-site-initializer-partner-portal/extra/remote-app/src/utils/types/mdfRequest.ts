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

import {requestStatus} from '../constants/requestStatus';

interface LiferayObject {
	dateCreated: Date;
	dateModified: Date;
	externalReferenceCode: string;
	id: number;
}

interface Activity extends Partial<LiferayObject> {
	name: string;
}

export default interface MDFRequest extends Partial<LiferayObject> {
	activities: Activity[];
	country: string;
	liferayBusinessSalesGoals: string[];
	overallCampaign: string;
	r_additionalOption_mdfRequest: string;
	r_company_accountEntryId: string;
	requestStatus: requestStatus;
	targetsAudienceRole: string[];
	targetsMarket: string[];
}
