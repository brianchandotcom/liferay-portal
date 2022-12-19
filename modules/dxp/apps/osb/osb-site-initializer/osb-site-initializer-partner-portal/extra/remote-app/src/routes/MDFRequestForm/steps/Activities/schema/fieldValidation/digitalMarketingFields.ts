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

const getDigitalMarketingFieldsValidation = (tactic: TacticKeys) => {
	const basicDigitalMarketingFields = {
		assetsLiferayDescription: string().when('assetsLiferayRequired', {
			is: (assetsLiferayRequired: string) =>
				assetsLiferayRequired === 'true',
			then: (schema) =>
				schema
					.max(255, 'You have exceeded the character limit')
					.trim()
					.required('Required'),
		}),
		assetsLiferayRequired: string().required('Required'),
		howLiferayBrandUsed: string()
			.trim()
			.max(255, 'You have exceeded the character limit')
			.required('Required'),
		overallMessageContentCTA: string()
			.trim()
			.max(255, 'You have exceeded the character limit')
			.required('Required'),
	};

	let targetFields = {};

	if (tactic === TacticKeys.EMAIL_CAMPAIGN) {
		targetFields = {
			...basicDigitalMarketingFields,
			landingPageCopy: string()
				.trim()
				.max(255, 'You have exceeded the character limit')
				.required('Required'),
			manySeries: string().when('nurtureDripCampaign', {
				is: (nurtureDripCampaign: string) =>
					nurtureDripCampaign === 'true',
				then: (schema) =>
					schema
						.max(255, 'You have exceeded the character limit')
						.trim()
						.required('Required'),
			}),
			nurtureDripCampaign: string().required('Required'),
			usingCIABAssets: string().required('Required'),
		};
	}
	else {
		targetFields = {
			...basicDigitalMarketingFields,
			ad: string()
				.trim()
				.max(255, 'You have exceeded the character limit'),
			keywordsForPPCCampaigns: string()
				.trim()
				.max(255, 'You have exceeded the character limit'),
			specificSites: string()
				.trim()
				.max(255, 'You have exceeded the character limit')
				.required('Required'),
		};
	}

	return targetFields;
};

export default getDigitalMarketingFieldsValidation;
