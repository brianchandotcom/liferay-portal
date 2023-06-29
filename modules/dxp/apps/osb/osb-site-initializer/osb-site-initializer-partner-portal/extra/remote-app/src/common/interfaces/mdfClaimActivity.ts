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

import LiferayFile from './liferayFile';
import LiferayObject from './liferayObject';
import LiferayPicklist from './liferayPicklist';
import MDFClaimActivityDocument from './mdfClaimActivityDocument';
import MDFClaimBudget from './mdfClaimBudget';

export default interface MDFClaimActivity extends Partial<LiferayObject> {
	activityStatus?: LiferayPicklist;
	budgets?: MDFClaimBudget[];
	claimed?: boolean;
	currency?: LiferayPicklist;
	eventProgram?: LiferayFile & number;
	listOfQualifiedLeads?: LiferayFile;
	metrics: string;
	name?: string;
	proofOfPerformance?: MDFClaimActivityDocument;
	r_actToMDFClmActs_c_activityId?: number;
	r_mdfClmToMDFClmActs_c_mdfClaimId?: number;
	selected: boolean;
	telemarketingMetrics?: string;
	telemarketingScript?: LiferayFile;
	totalCost: number;
	typeActivity: LiferayPicklist;
	videoLink?: string;
}
