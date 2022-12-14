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
		| 'mdfActivityAssociated'
		| 'partnerAccount'
		| 'projectNeed'
		| 'prospect'
		| 'primaryProspect'
	> {
	accountExternalReferenceCodeSF?: string;
	additionalContactEmailAddress?: string;
	additionalContactFirstName?: string;
	additionalContactLastName?: string;
	additionalContacts?: string;
	additionalInformationAboutTheOpportunity?: string;
	amount?: number;
	closeDate?: Date;
	leadExternalReferenceCode?: string;
	leadStatus?: string;
	mdfActivityExternalReferenceCodeSF?: string;
	opportunityOwner?: string;
	ownerName?: string;
	partnerAccountName?: string;
	partnerEmail?: string;
	partnerFirstName?: string;
	partnerLastName?: string;
	primaryProspectBusinessUnit?: string;
	primaryProspectDepartment?: LiferayPicklist;
	primaryProspectEmailAddress?: string;
	primaryProspectFirstName?: string;
	primaryProspectJobRole?: LiferayPicklist;
	primaryProspectLastName?: string;
	primaryProspectPhone?: string;
	projectCategories?: string;
	projectNeed?: string;
	projectSubscriptionEndDate?: Date;
	projectSubscriptionStartDate?: Date;
	projectTimeline: string;
	prospectAccountName?: string;
	prospectAddress?: string;
	prospectCity?: string;
	prospectCountry?: LiferayPicklist;
	prospectCountryCode?: string;
	prospectIndustry?: LiferayPicklist;
	prospectPostalCode?: string;
	prospectState?: LiferayPicklist;
	prospectStateCode?: string;
	r_accountToDealRegistrations_accountEntry?: AccountEntry;
	r_accountToDealRegistrations_accountEntryId?: number;
	r_activityToDealRegistrations_c_activity?: MDFRequestActivity;
	r_activityToDealRegistrations_c_activityId?: number;
	stage?: string;
}
