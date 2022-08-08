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

import {array, object, string} from 'yup';

const goalsSchema = object({
	country: object({
		key: string().defined(),
		name: string().defined(),
	}).required('Required'),
	liferayBusinessSalesGoals: array()
		.min(1, 'Required')
		.max(3, 'You have exceed the choose limit'),
	overallCampaign: string()
		.max(350, 'You have exceeded the character limit')
		.required('Required'),
	r_company_accountEntryId: string().required('Required'),
	targetsAudienceRole: array().min(1, 'Required'),
	targetsMarket: array()
		.min(1, 'Required')
		.max(3, 'You have exceed the choose limit'),
});

export default goalsSchema;
