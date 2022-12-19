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

import {TacticKeys} from '../../../../../../common/enums/mdfRequestTactics';

const getEventFieldsValidation = (tactic: TacticKeys) => {
	const basicEventFields = {
		activityPromotion: string()
			.trim()
			.max(255, 'You have exceeded the character limit')
			.required('Required'),
		description: string()
			.trim()
			.max(255, 'You have exceeded the character limit')
			.required('Required'),
		liferayParticipationRequirements: string()
			.trim()
			.max(255, 'You have exceeded the character limit')
			.required('Required'),
		sourceAndSizeOfInviteeList: string()
			.trim()
			.max(255, 'You have exceeded the character limit')
			.required('Required'),
	};

	let targetFields = {};

	if (tactic === TacticKeys.WEBINAR) {
		targetFields = {
			...basicEventFields,
			liferayBranding: string()
				.trim()
				.max(255, 'You have exceeded the character limit'),
			webinarHostPlatform: string()
				.trim()
				.max(255, 'You have exceeded the character limit')
				.required('Required'),
			webinarTopic: string()
				.trim()
				.max(255, 'You have exceeded the character limit')
				.required('Required'),
		};
	}
	else {
		targetFields = {
			...basicEventFields,
			liferayBranding: string()
				.trim()
				.max(255, 'You have exceeded the character limit')
				.required('Required'),
			location: string()
				.trim()
				.max(255, 'You have exceeded the character limit')
				.required('Required'),
			venueName: string()
				.trim()
				.max(255, 'You have exceeded the character limit')
				.required('Required'),
		};
	}

	return targetFields;
};

export default getEventFieldsValidation;
