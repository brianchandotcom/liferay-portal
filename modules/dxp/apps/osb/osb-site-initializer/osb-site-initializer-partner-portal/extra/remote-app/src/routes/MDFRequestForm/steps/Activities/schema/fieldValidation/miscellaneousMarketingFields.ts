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

const getMiscellaneousMarketingFieldsValidation = (tactic: TacticKeys) => {
	const basicEventFields = {
		marketingActivity: string()
			.trim()
			.max(255, 'You have exceeded the character limit')
			.required('Required'),
	};

	let targetFields = {};

	const CTATactics = [
		TacticKeys.BROADCAST_ADVERTISING,
		TacticKeys.CAMPAIGN_WITH_INDUSTRY_PUBLICATION,
		TacticKeys.DIRECT_MAIL,
		TacticKeys.PRINT_ADVERTISING,
	];

	if (CTATactics.includes(tactic)) {
		targetFields = {
			...basicEventFields,
			cta: string()
				.trim()
				.max(255, 'You have exceeded the character limit')
				.required('Required'),
		};

		if (tactic === TacticKeys.BROADCAST_ADVERTISING) {
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
		else if (tactic === TacticKeys.DIRECT_MAIL) {
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
	else if (tactic === TacticKeys.CO_BRANDED_MERCHANDISE) {
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
	else if (tactic === TacticKeys.OUTBOUND_TELEMARKETING_SALES) {
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
