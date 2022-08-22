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

import MDFRequestActivity from '../../../../../../../../../common/interfaces/mdfRequestActivity';
import getBooleanValue from '../../../../../../../../../common/utils/getBooleanValue';

export default function getDigitalMarketFields(
	mdfRequestActivity: MDFRequestActivity
) {
	return [
		{
			title: 'Overall message, content or CTA',
			value: mdfRequestActivity.overallMessageContentCTA,
		},
		{
			title: 'Specific sites to be used',
			value: mdfRequestActivity.specificSites,
		},
		{
			title: 'Keywords for PPC campaigns',
			value: mdfRequestActivity.keywordsForPPCCampaigns,
		},
		{
			title: 'Ad (any size/type)',
			value: mdfRequestActivity.ad,
		},
		{
			title: 'Do you require any assets from Liferay?',
			value: getBooleanValue(mdfRequestActivity.assetsLiferayRequired),
		},
		{
			title: 'How will the Liferay brand be used in the campaign?',
			value: mdfRequestActivity.howLiferayBrandUsed,
		},
	];
}
