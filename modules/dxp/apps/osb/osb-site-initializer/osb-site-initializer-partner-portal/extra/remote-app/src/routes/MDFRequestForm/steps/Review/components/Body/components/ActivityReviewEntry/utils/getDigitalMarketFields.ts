/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TacticKeys} from '../../../../../../../../../common/enums/mdfRequestTactics';
import MDFRequestActivity from '../../../../../../../../../common/interfaces/mdfRequestActivity';
import getBooleanValue from '../../../../../../../../../common/utils/getBooleanValue';

export default function getDigitalMarketFields(
	mdfRequestActivity: MDFRequestActivity
) {
	const digitalMarketingFields = [
		{
			title: 'Overall message, content or CTA',
			value:
				mdfRequestActivity.activityDescription
					?.overallMessageContentCTA,
		},
		{
			title: 'Do you require any assets from Liferay?',
			value: getBooleanValue(
				mdfRequestActivity.activityDescription
					?.assetsLiferayRequired as string
			),
		},
		{
			title: 'How will the Liferay brand be used in the campaign?',
			value: mdfRequestActivity.activityDescription?.howLiferayBrandUsed,
		},
	];
	if (mdfRequestActivity.activityDescription?.assetsLiferayRequired) {
		digitalMarketingFields.push({
			title: 'Please describe including specifications and due dates',
			value:
				mdfRequestActivity.activityDescription
					?.assetsLiferayDescription,
		});
	}
	if (mdfRequestActivity.tactic.key === TacticKeys.EMAIL_CAMPAIGN) {
		digitalMarketingFields.push(
			{
				title: 'Landing page copy',
				value: mdfRequestActivity.activityDescription?.landingPageCopy,
			},
			{
				title: 'Nurture or drip campaign?',
				value: getBooleanValue(
					mdfRequestActivity.activityDescription
						?.nurtureDripCampaign as string
				),
			}
		);
		if (mdfRequestActivity.activityDescription?.nurtureDripCampaign) {
			digitalMarketingFields.push({
				title: 'How many in series?',
				value: mdfRequestActivity.activityDescription?.manySeries,
			});
		}
		digitalMarketingFields.push({
			title: 'Are you using any CIAB assets?',
			value: getBooleanValue(
				mdfRequestActivity.activityDescription
					?.usingCIABAssets as string
			),
		});
	}
	else {
		digitalMarketingFields.push(
			{
				title: 'Specific sites to be used',
				value: mdfRequestActivity.activityDescription?.specificSites,
			},
			{
				title: 'Keywords for PPC campaigns',
				value:
					mdfRequestActivity.activityDescription
						?.keywordsForPPCCampaigns,
			},
			{
				title: 'Ad (any size/type)',
				value: mdfRequestActivity.activityDescription?.ad,
			}
		);
	}

	return digitalMarketingFields;
}
