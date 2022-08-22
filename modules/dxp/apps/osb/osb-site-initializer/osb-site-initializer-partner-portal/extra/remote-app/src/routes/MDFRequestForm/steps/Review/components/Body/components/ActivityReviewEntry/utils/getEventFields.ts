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

export default function getEventFields(mdfRequestActivity: MDFRequestActivity) {
	return [
		{
			title: 'Activity Description',
			value: mdfRequestActivity.description,
		},
		{
			title: 'Venue Name',
			value: mdfRequestActivity.venueName,
		},
		{
			title: 'Liferay Branding',
			value: mdfRequestActivity.liferayBranding,
		},
		{
			title: 'Liferay Participation / Requirements',
			value: mdfRequestActivity.liferayParticipationRequirements,
		},
		{
			title: 'Source and Size of Invitee List',
			value: mdfRequestActivity.sourceAndSizeOfInviteeList,
		},
		{
			title: 'Activity Promotion',
			value: mdfRequestActivity.activityPromotion,
		},
	];
}
