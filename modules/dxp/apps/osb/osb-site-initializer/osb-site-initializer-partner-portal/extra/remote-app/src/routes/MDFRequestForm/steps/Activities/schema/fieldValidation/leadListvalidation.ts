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

import {array, string} from 'yup';

const leadListFieldsValidation = {
	detailsLeadFollowUp: string().when('leadGenerated', {
		is: (leadGenerated: string) => leadGenerated === 'true',
		then: (schema) =>
			schema
				.max(255, 'You have exceeded the character limit')
				.trim()
				.required('Required'),
	}),
	leadFollowUpStrategies: array().when('leadGenerated', {
		is: (leadGenerated: string) => leadGenerated === 'true',
		then: (schema) => schema.min(1, 'Required'),
	}),
	leadGenerated: string().required('Required'),
	targetOfLeads: string().when('leadGenerated', {
		is: (leadGenerated: string) => leadGenerated === 'true',
		then: (schema) =>
			schema
				.max(255, 'You have exceeded the character limit')
				.trim()
				.required('Required'),
	}),
};

export default leadListFieldsValidation;
