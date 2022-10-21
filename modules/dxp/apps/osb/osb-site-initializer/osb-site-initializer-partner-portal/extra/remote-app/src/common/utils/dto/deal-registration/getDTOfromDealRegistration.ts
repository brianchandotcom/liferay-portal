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

import DealRegistration from '../../../interfaces/dealRegistration';
import DealRegistrationDTO from '../../../interfaces/dto/dealRegistrationDTO';

export function getDTOFromDealRegistration(
	dealRegistration: DealRegistration
): DealRegistrationDTO {
	return {
		additionalContactEmailAddress:
			dealRegistration.additionalContact?.emailAddress,
		additionalContactFirstName:
			dealRegistration.additionalContact?.firstName,
		additionalContactLastName: dealRegistration.additionalContact?.lastName,
		additionalInformationAboutTheOpportunity:
			dealRegistration.additionalInformationAboutTheOpportunity,
		primaryProspectBusinessUnit:
			dealRegistration.primaryProspect.businessUnit,
		primaryProspectDepartment: dealRegistration.primaryProspect.department,
		primaryProspectEmailAddress:
			dealRegistration.primaryProspect.emailAddress,
		primaryProspectFirstName: dealRegistration.primaryProspect.firstName,
		primaryProspectJobRole: dealRegistration.primaryProspect.jobRole,
		primaryProspectLastName: dealRegistration.primaryProspect.lastName,
		primaryProspectPhone: dealRegistration.primaryProspect.phone,
		projectCategories: dealRegistration.projectCategories.join('; '),
		projectNeed: dealRegistration.projectNeed.join('; '),
		projectTimeline: dealRegistration.projectTimeline,
		prospectAccountName: dealRegistration.prospect.accountName,
		prospectAddress: dealRegistration.prospect.address,
		prospectCity: dealRegistration.prospect.city,
		prospectCountry: dealRegistration.prospect.country,
		prospectIndustry: dealRegistration.prospect.industry,
		prospectPostalCode: dealRegistration.prospect.postalCode,
		prospectState: dealRegistration.prospect.state,
		r_accountToDealRegistrations_accountEntryId:
			dealRegistration.partnerAccount.id,
		registrationStatus: dealRegistration.registrationStatus,
	};
}
