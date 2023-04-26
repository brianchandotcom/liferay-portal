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

import {array, number, object, string} from 'yup';

import isObjectEmpty from '../../../../../common/utils/isObjectEmpty';

const goalsSchema = object({
	company: object({
		id: number(),
		name: string(),
	})
		.default(undefined)
		.test('is-empty', 'Required', (value) => !isObjectEmpty(value)),
	currency: object({
		key: string(),
		name: string(),
	})
		.default(undefined)
		.test('is-empty', 'Required', (value) => !isObjectEmpty(value)),
	liferayBusinessSalesGoals: array()
		.min(1, 'Required')
		.max(3, 'You have exceed the choose limit'),
	liferayBusinessSalesGoalsOther: string().when(
		'liferayBusinessSalesGoals',
		(liferayBusinessSalesGoals, schema) => {
			return liferayBusinessSalesGoals.includes('Other - Please describe')
				? string().required('Required')
				: schema;
		}
	),
	overallCampaignDescription: string()
		.trim()
		.max(255, 'You have exceeded the character limit')
		.required('Required'),
	overallCampaignName: string()
		.trim()
		.max(24, 'You have exceeded the character limit')
		.required('Required'),
	partnerCountry: object({
		key: string(),
		name: string(),
	})
		.default(undefined)
		.test('is-empty', 'Required', (value) => !isObjectEmpty(value)),
	targetAudienceRoles: array().min(1, 'Required'),
	targetMarkets: array()
		.min(1, 'Required')
		.max(3, 'You have exceed the choose limit'),
});

export default goalsSchema;
