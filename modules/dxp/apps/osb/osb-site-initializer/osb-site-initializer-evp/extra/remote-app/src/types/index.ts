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
export interface RequestFilterType {
	finalCompanyId: String;
	finalRequestDate: String;
	fullName: String;
	initialCompanyId: String;
	initialRequestDate: String;
	liferayBranch: String[];
	organizationName: String;
	requestStatus: String[];
}
export interface OrganizationFilterType {
	city: String;
	contactEmail: String;
	contactName: String;
	contactPhone: String;
	country: String;
	externalReferenceCode: String;
	id: String;
	idNumber: Number;
	organizationName: String;
	organizationSiteSocialMediaLink: String;
	organizationStatus: String[];
	smallDescription: String;
	state: String;
	status: String[];
	street: String;
	taxIdentificationNumber: String;
	zip: String;
}
