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

import {string} from 'yup';

const getMiscellaneousMarketingFieldsValidation = (tactic: string) => {
	const basicEventFields = {
		marketingActivity: string()
			.trim()
			.max(255, 'You have exceeded the character limit')
			.required('Required'),
	};

	let targetFields = {};

	const CTATactics = [
		'Broadcast Advertising',
		'Campaign with Industry Publication',
		'Direct Mail',
		'Print Advertising',
	];

	if (CTATactics.includes(tactic)) {
		targetFields = {
			...basicEventFields,
			cta: string()
				.trim()
				.max(255, 'You have exceeded the character limit')
				.required('Required'),
		};

		if (tactic === 'Broadcast Advertising') {
			targetFields = {
				...targetFields,
				broadcastChannel: string()
					.trim()
					.max(255, 'You have exceeded the character limit')
					.required('Required'),
				guaranteedImpressions: string()
					.trim()
					.max(255, 'You have exceeded the character limit')
					.required('Required'),
				weeksAiring: string()
					.trim()
					.max(255, 'You have exceeded the character limit'),
			};
		}
		else if (tactic === 'Direct Mail') {
			targetFields = {
				...targetFields,
				targetOfSends: string()
					.trim()
					.max(255, 'You have exceeded the character limit')
					.required('Required'),
			};
		}
		else {
			targetFields = {
				...targetFields,
				expectedImpressions: string()
					.trim()
					.max(255, 'You have exceeded the character limit')
					.required('Required'),
				publication: string()
					.trim()
					.max(255, 'You have exceeded the character limit')
					.required('Required'),
			};
		}
	}
	else if (tactic === 'Co-branded Merchandise') {
		targetFields = {
			...basicEventFields,
			quantity: string()
				.trim()
				.max(255, 'You have exceeded the character limit')
				.required('Required'),
			typeMerchandise: string()
				.trim()
				.max(255, 'You have exceeded the character limit')
				.required('Required'),
		};
	}
	else if (tactic === 'Outbound Telemarketing Sales') {
		targetFields = {
			...basicEventFields,
			audienceTarget: string()
				.trim()
				.max(255, 'You have exceeded the character limit')
				.required('Required'),
			resourcesNecessaryFromLiferay: string()
				.trim()
				.max(255, 'You have exceeded the character limit'),
			sourceAndSizeOfCallList: string()
				.trim()
				.max(255, 'You have exceeded the character limit')
				.required('Required'),
		};
	}
	else {
		targetFields = {
			...basicEventFields,
		};
	}

	return targetFields;
};

export default getMiscellaneousMarketingFieldsValidation;
