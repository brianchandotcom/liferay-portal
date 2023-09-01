/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {number, string} from 'yup';

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
				weeksAiring: number()
					.typeError('The input must be a number')
					.max(
						999999999,
						'The value cannot be greater than 9,999,999.99'
					)
					.min(1, 'Required')
					.required('Required'),
			};
		}
		else if (tactic === TacticKeys.DIRECT_MAIL) {
			targetFields = {
				...targetFields,
				targetOfSends: number()
					.typeError('The input must be a number')
					.max(
						999999999,
						'The value cannot be greater than 9,999,999.99'
					)
					.min(1, 'Required')
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
			quantity: number()
				.typeError('The input must be a number')
				.max(999999999, 'The value cannot be greater than 9,999,999.99')
				.min(1, 'Required')
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
