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
import isDropDownEmpty from '../../../utils/isDropDownEmpty';

const generalSchema = object({
	partnerAccountName: object({
		id: number(),
		name: string(),
	}).test('is-empty', 'Required', (value) => !isDropDownEmpty(value)),
	projectNeed: array().min(1, 'Required'),
	categories: array().min(1, 'Required'),
	projectTimeline: string().required('Required'),
	prospect: object({
		accountName: string().required('Required'),
		industry: object({
			key: string(),
			name: string(),
		}).test('is-empty', 'Required', (value) => !isDropDownEmpty(value)),
		country: object({
			key: string(),
			name: string(),
		}).test('is-empty', 'Required', (value) => !isDropDownEmpty(value)),
		address: string().required('Required'),
		city: string().required('Required'),
		postalCode: string().required('Required'),
		state: object({
			key: string(),
			name: string(),
		}).test('is-empty', 'Required', (value, context) =>
			context.parent.country.name === 'US'
				? !isDropDownEmpty(value)
				: true
		),
	}),
	primaryProspect: object({
		businessUnit: string().required('Required'),
		department: object({
			key: string(),
			name: string(),
		}).test('is-empty', 'Required', (value) => !isDropDownEmpty(value)),
		emailAddress: string().required('Required'),
		firstName: string().required('Required'),
		jobRole: object({
			key: string(),
			name: string(),
		}).test('is-empty', 'Required', (value) => !isDropDownEmpty(value)),
		lastName: string().required('Required'),
		phone: string().required('Required'),
	}),
});

export default generalSchema;
