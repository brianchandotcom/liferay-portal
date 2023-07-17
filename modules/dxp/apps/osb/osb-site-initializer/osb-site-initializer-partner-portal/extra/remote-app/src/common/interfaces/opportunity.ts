/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OpportunityType} from '../enums/opportunityType';
import LiferayObject from './liferayObject';
import LiferayPicklist from './liferayPicklist';

export default interface Opportunity extends LiferayObject {
	accountExternalReferenceCode: string;
	accountName: string;
	amount: number;
	closeDate: string;
	currency: LiferayPicklist;
	expirationDays?: number;
	externalReferenceCode: string;
	growthArr: number;
	opportunityName: string;
	opportunityOwner: string;
	ownerName: string;
	partnerAccountName: string;
	renewalArr: number;
	stage: string;
	status: string;
	type: OpportunityType;
}
