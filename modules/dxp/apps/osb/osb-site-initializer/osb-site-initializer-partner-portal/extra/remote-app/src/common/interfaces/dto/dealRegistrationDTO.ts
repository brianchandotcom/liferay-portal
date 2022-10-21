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

import AccountEntry from '../accountEntry';
import DealRegistration from '../dealRegistration';
import LiferayPicklist from '../liferayPicklist';
import MDFRequestActivity from '../mdfRequestActivity';

export default interface DealRegistrationDTO
	extends Omit<
		DealRegistration,
		| 'projectCategories'
		| 'department'
		| 'jobRole'
		| 'country'
		| 'industry'
		| 'state'
		| 'mdfActivityAssociated'
		| 'partnerAccount'
		| 'projectNeed'
		| 'projectSolutionCategories'
		| 'prospect'
		| 'primaryProspect'
	> {
	additionalContactEmailAddress?: string;
	additionalContactFirstName?: string;
	additionalContactLastName?: string;
	additionalInformationAboutTheOpportunity?: string;
	primaryProspectBusinessUnit?: string;
	primaryProspectDepartment?: LiferayPicklist;
	primaryProspectEmailAddress?: string;
	primaryProspectFirstName?: string;
	primaryProspectJobRole?: LiferayPicklist;
	primaryProspectLastName?: string;
	primaryProspectPhone?: string;
	projectCategories?: string;
	projectNeed?: string;
	projectTimeline: string;
	prospectAccountName?: string;
	prospectAddress?: string;
	prospectCity?: string;
	prospectCountry?: LiferayPicklist;
	prospectIndustry?: LiferayPicklist;
	prospectPostalCode?: string;
	prospectState?: LiferayPicklist;
	r_accountToDealRegistrations_accountEntry?: AccountEntry;
	r_accountToDealRegistrations_accountEntryId?: number;
	r_activityToDealRegistrations_c_activity?: MDFRequestActivity;
	r_activityToDealRegistrations_c_activityId?: number;
}
