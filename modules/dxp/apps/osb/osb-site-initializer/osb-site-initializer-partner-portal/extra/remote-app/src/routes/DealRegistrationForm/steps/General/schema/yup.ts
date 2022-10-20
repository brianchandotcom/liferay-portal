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

const generalSchema = object({
	additionalContact: object({
		emailAddress: string()
			.max(255, 'reached max characters')
			.email('must be a valid email'),
		firstName: string().max(255, 'reached max characters'),
		lastName: string().max(255, 'reached max characters'),
	}),
	additionalInformationAboutTheOpportunity: string().max(
		500,
		'reached max characters'
	),
	categories: array().min(1, 'Required'),
	partnerAccountName: object({
		id: number(),
		name: string(),
	}).test('is-empty', 'Required', (value) => !isObjectEmpty(value)),
	primaryProspect: object({
		businessUnit: string()
			.max(255, 'reached max characters')
			.required('Required'),
		department: object({
			key: string(),
			name: string(),
		}).test('is-empty', 'Required', (value) => !isObjectEmpty(value)),
		emailAddress: string()
			.max(255, 'reached max characters')
			.email('must be a valid email')
			.required('Required'),
		firstName: string()
			.max(255, 'reached max characters')
			.required('Required'),
		jobRole: object({
			key: string(),
			name: string(),
		}).test('is-empty', 'Required', (value) => !isObjectEmpty(value)),
		lastName: string()
			.max(255, 'reached max characters')
			.required('Required'),
		phone: string().max(20, 'reached max characters').required('Required'),
	}),
	projectNeed: array().min(1, 'Required'),
	projectTimeline: string()
		.max(255, 'reached max characters')
		.required('Required'),
	prospect: object({
		accountName: string()
			.max(255, 'reached max characters')
			.required('Required'),
		address: string()
			.max(255, 'reached max characters')
			.required('Required'),
		city: string().max(255, 'reached max characters').required('Required'),
		country: object({
			key: string(),
			name: string(),
		}).test('is-empty', 'Required', (value) => !isObjectEmpty(value)),
		industry: object({
			key: string(),
			name: string(),
		}).test('is-empty', 'Required', (value) => !isObjectEmpty(value)),
		postalCode: string()
			.max(255, 'reached max characters')
			.required('Required'),
		state: object({
			key: string(),
			name: string(),
		}).test('is-empty', 'Required', (value, context) =>
			context.parent.country.name === 'US' ? !isObjectEmpty(value) : true
		),
	}),
});

export default generalSchema;
