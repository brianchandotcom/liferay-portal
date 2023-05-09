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
	accountExternalReferenceCode?: string;
	accountName?: string;
	additionalContactEmailAddress?: string;
	additionalContactFirstName?: string;
	additionalContactLastName?: string;
	additionalContacts?: string;
	additionalInformationAboutTheOpportunity?: string;
	amount?: number;
	closeDate?: string;
	leadExternalReferenceCode?: string;
	leadQualificationExternalReferenceCode?: string;
	leadStatus?: string;
	leadStatusDetail?: string;
	mdfActivityExternalReferenceCode?: string;
	mdfActivityName?: string;
	opportunityOwner?: string;
	ownerName?: string;
	partnerAccountName?: string;
	partnerEmail?: string;
	primaryProspectBusinessUnit?: string;
	primaryProspectDepartment?: string;
	primaryProspectEmailAddress?: string;
	primaryProspectFirstName?: string;
	primaryProspectJobRole?: string;
	primaryProspectLastName?: string;
	primaryProspectPhone?: string;
	projectCategories?: string;
	projectNeed?: string;
	projectSubscriptionEndDate?: string;
	projectSubscriptionStartDate?: string;
	projectTimeline: string;
	prospectAccountName?: string;
	prospectAddress?: string;
	prospectCity?: string;
	prospectCountry?: LiferayPicklist;
	prospectCountryCode?: string;
	prospectIndustry?: string;
	prospectPostalCode?: string;
	prospectState?: LiferayPicklist;
	prospectStateCode?: string;
	r_accToDealRegs_accountEntry?: AccountEntry;
	r_accToDealRegs_accountEntryId?: number;
	r_actToDealRegs_c_activity?: MDFRequestActivity;
	r_actToDealRegs_c_activityId?: number;
	stage?: string;
}
