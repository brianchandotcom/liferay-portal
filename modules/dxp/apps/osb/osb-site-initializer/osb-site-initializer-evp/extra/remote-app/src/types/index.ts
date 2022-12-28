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
	finalCompanyId: string;
	finalRequestDate: string;
	fullName: string;
	initialCompanyId: string;
	initialRequestDate: string;
	liferayBranch: string[];
	organizationName: string;
	requestStatus: string[];
}
export interface OrganizationFilterType {
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
	taxIdentificationNumber: string;
	zip: string;
}

export type Statuses = {
	dateCreated: string;
	dateModified: string;
	id: number;
	key: string;
	name: string;
};

export type LiferayBranch = {
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
