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
import MDFRequestActivityDTO from './dto/mdfRequestActivityDTO';
import LiferayAccountBrief from './liferayAccountBrief';
import LiferayObject from './liferayObject';
import LiferayPicklist from './liferayPicklist';

interface PrimaryProspect {
	businessUnit: string;
	department: LiferayPicklist;
	emailAddress: string;
	firstName: string;
	jobRole: LiferayPicklist;
	lastName: string;
	phone: string;
}

interface Prospect {
	accountName: string;
	address: string;
	city: string;
	country: LiferayPicklist;
	industry: LiferayPicklist;
	postalCode: string;
	state: LiferayPicklist;
}

interface AdditionalContact {
	emailAddress: string;
	firstName: string;
	lastName: string;
}

export default interface DealRegistration extends Partial<LiferayObject> {
	additionalContact?: AdditionalContact;
	additionalInformationAboutTheOpportunity?: string;
	categories: string[];
	MdfActivityAssociated: MDFRequestActivityDTO;
	partnerAccount: LiferayAccountBrief;
	primaryProspect: PrimaryProspect;
	projectNeed: string[];
	projectTimeline: string;
	prospect: Prospect;
	requestStatus?: RequestStatus;
}
