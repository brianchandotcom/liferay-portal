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

import {Liferay} from '../..';
import MDFRequestActivity from '../../../../interfaces/mdfRequestActivity';
import {LiferayAPIs} from '../../common/enums/apis';
import liferayFetcher from '../../common/utils/fetcher';

export default async function createMDFRequestActivities(
	mdfRequestId: string | undefined,
	mdfRequestActivities: MDFRequestActivity[]
) {
	const dtoMDFRequestActivities = mdfRequestActivities.map((activity) => {
		return {
			...activity,
			leadFollowUpStrategies: activity.leadFollowUpStrategies.join(', '),
			r_mdfRequestToActivities_c_mdfRequestId: mdfRequestId,
		};
	});

	return await Promise.all(
		dtoMDFRequestActivities.map((activity) =>
			liferayFetcher.post<typeof activity>(
				`/o/${LiferayAPIs.OBJECT}/activities`,
				Liferay.authToken,
				activity
			)
		)
	);
}
