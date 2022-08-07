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

import * as Yup from 'yup';

const yup = Yup.object({
	country: Yup.string().required('Required'),
	liferayBusinessSalesGoals: Yup.array()
		.min(1, 'Required')
		.max(3, 'You have exceed the choose limit'),
	overallCampaign: Yup.string()
		.max(350, 'You have exceeded the character limit')
		.required('Required'),
	r_company_accountEntryId: Yup.string().required('Required'),
	targetsAudienceRole: Yup.array().min(1, 'Required'),
	targetsMarket: Yup.array()
		.min(1, 'Required')
		.max(3, 'You have exceed the choose limit'),
});

export default yup;
