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

export type RequestFilterType = {
	finalCompanyId: string;
	finalRequestDate: string;
	fullName: string;
	initialCompanyId: string;
	initialRequestDate: string;
	liferayBranch: string[];
	organizationName: string;
	requestStatus: string[];
};

export type FinancialFilterType = {
	accountNumberCR: string;
	accountNumberDB: string;
	accountTypeCR: string;
	accountTypeDB: string;
	entityName: string;
	territoryId: string;
};

export type PaymentConfirmationFilterType = {
	accountNumberCR: string;
	accountNumberDB: string;
	accountTypeCR: string;
	accountTypeDB: string;
	entityName: string;
	finalPaymentDate: string;
	initialPaymentDate: string;
	paymentDate: string;
	paymentValue: string;
	r_financial_c_evpFinancial: FinancialFilterType;
	r_requestId_c_evpRequest: RequestType;
	r_requestId_c_evpRequestId: number;
	territoryId: string;
};

export type OrganizationFilterType = {
	city: string;
	contactEmail: string;
	contactName: string;
	contactPhone: string;
	country: string;
	externalReferenceCode: string;
	id: string;
	idNumber: Number;
	organizationName: string;
	organizationSiteSocialMediaLink: string;
	organizationStatus: string[];
	smallDescription: string;
	state: string;
	status: string[];
	street: string;
	taxId: string;
	zip: string;
};

export type Statustype = {
	dateCreated: string;
	dateModified: string;
	id: number;
	key: string;
	name: string;
};

export type LiferayBranchType = {
	dateCreated: string;
	dateModified: string;
	id: number;
	key: string;
	name: string;
};

export enum FIELDSREPORT {
	FINALCOMPANYID = 'finalCompanyId',
	FINALREQUESTDATE = 'finalRequestDate',
	FULLNAME = 'fullName',
	INITIALCOMPANYID = 'initialCompanyId',
	INITIALREQUESTDATE = 'initialRequestDate',
	LIFERAYBRANCH = 'liferayBranch',
	ORGANIZATIONNAME = 'organizationName',
	REQUESTSTATUS = 'requestStatus',
}

export enum FIELDS_PAYMENT_REPORT {
	FINALPAYMENTDATE = 'finalPaymentDate',
	INITIALPAYMENTDATE = 'initialPaymentDate',
	TERRITORYID = 'territoryId',
}

export type RequestType = {
	createDate: string;
	creator: {name: string};
	dateCreated: string;
	dateModified: string;
	emailAddress: string;
	endDate: string;
	externalReferenceCode: string;
	fullName: string;
	grantAmount: number;
	grantRequestType: {key: string; name: string};
	id: number;
	liferayBranch: {key: string; name: string};
	managerEmailAddress: string;
	modifiedDate: string;
	phoneNumber: string;
	r_organization_c_evpOrganization: OrganizationFilterType;
	r_organization_c_evpOrganizationERC: string;
	r_organization_c_evpOrganizationId: number;
	requestBehalf: {key: string; name: string};
	requestDescription: string;
	requestPurposes: {key: string; name: string};
	requestStatus: {key: string; name: string};
	requestType: {key: string; name: string};
	scopeKey: string;
	startDate: string;
	status: {code: number; label: string; label_i18n: string};
	totalHoursRequested: number;
};
