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

export default function getContentMarketFields(
	mdfRequestActivity: MDFRequestActivity
) {
	return [
		{
			title: 'Will this content be gated and have a landing page?',
			value: getBooleanValue(mdfRequestActivity.gatedLandingPage),
		},
		{
			title: 'Primary theme or message of your content',
			value: mdfRequestActivity.primaryThemeOrMessage,
		},

		{
			title: 'Goal of Content',
			value: mdfRequestActivity.goalOfContent,
		},
		{
			title:
				'Are you hiring an outside writer or agency to prepare the content?',
			value: getBooleanValue(
				mdfRequestActivity.hiringOutsideWriterOrAgency
			),
		},
	];
}
