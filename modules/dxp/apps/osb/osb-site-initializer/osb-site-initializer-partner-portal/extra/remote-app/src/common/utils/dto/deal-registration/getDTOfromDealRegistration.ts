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
	dealRegistration: DealRegistration,
	leadExternalReferenceCode?: string
): DealRegistrationDTO {
	return {
		accountExternalReferenceCode:
			dealRegistration.accountExternalReferenceCode,
		additionalContactEmailAddress:
			dealRegistration.additionalContact?.emailAddress,
		additionalContactFirstName:
			dealRegistration.additionalContact?.firstName,
		additionalContactLastName: dealRegistration.additionalContact?.lastName,
		additionalContacts: `${dealRegistration.additionalContact?.firstName} ${dealRegistration.additionalContact?.lastName}, ${dealRegistration.additionalContact?.emailAddress}`,
		additionalInformationAboutTheOpportunity:
			dealRegistration.additionalInformationAboutTheOpportunity,
		currency: dealRegistration.currency,
		leadExternalReferenceCode,
		leadStatusDetail: dealRegistration.leadStatusDetails,
		mdfActivityExternalReferenceCode:
			dealRegistration.mdfActivityAssociated.externalReferenceCode,
		mdfActivityName: dealRegistration.mdfActivityAssociated.name,
		partnerFirstName: dealRegistration.partnerFirstName,
		partnerLastName: dealRegistration.partnerLastName,
		primaryProspectBusinessUnit:
			dealRegistration.primaryProspect.businessUnit,
		primaryProspectDepartment:
			dealRegistration.primaryProspect.department.name,
		primaryProspectEmailAddress:
			dealRegistration.primaryProspect.emailAddress,
		primaryProspectFirstName: dealRegistration.primaryProspect.firstName,
		primaryProspectJobRole: dealRegistration.primaryProspect.jobRole.name,
		primaryProspectLastName: dealRegistration.primaryProspect.lastName,
		primaryProspectPhone: dealRegistration.primaryProspect.phone,
		projectCategories: dealRegistration.projectCategories.join('; '),
		projectNeed: dealRegistration.projectNeed.join('; '),
		projectTimeline: dealRegistration.projectTimeline,
		prospectAccountName: dealRegistration.prospect.accountName,
		prospectAddress: dealRegistration.prospect.address,
		prospectCity: dealRegistration.prospect.city,
		prospectCountry: dealRegistration.prospect.country,
		prospectCountryCode: dealRegistration.prospect.country.key,
		prospectIndustry: dealRegistration.prospect.industry.name,
		prospectPostalCode: dealRegistration.prospect.postalCode,
		prospectState: dealRegistration.prospect.state,
		prospectStateCode: dealRegistration.prospect.state.key,
		r_accToDealRegs_accountEntryId: dealRegistration.partnerAccount.id,
		r_actToDealRegs_c_activityId: dealRegistration.mdfActivityAssociated.id,
	};
}
