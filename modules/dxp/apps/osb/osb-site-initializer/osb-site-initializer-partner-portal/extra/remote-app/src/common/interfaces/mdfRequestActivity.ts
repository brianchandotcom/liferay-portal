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

import LiferayObject from './liferayObject';
import LiferayPicklist from './liferayPicklist';
import MDFRequestActivityDescription from './mdfRequestActivityDescription';
import MDFRequestBudget from './mdfRequestBudget';

export default interface MDFRequestActivity extends Partial<LiferayObject> {
	activityDescription?: MDFRequestActivityDescription;
	activityStatus?: LiferayPicklist;
	budgets: MDFRequestBudget[];
	currency?: LiferayPicklist;
	endDate?: string;
	externalReferenceCode?: string;
	externalReferenceCodeSF?: string;
	mdfRequestAmount: number;
	name: string;
	removed?: boolean;
	startDate?: string;
	tactic: LiferayPicklist;
	totalCostOfExpense: number;
	typeActivity: LiferayPicklist;
}
