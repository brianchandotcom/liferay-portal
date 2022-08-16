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

import {useEffect, useState} from 'react';

import MDFRequestActivity from '../interfaces/mdfRequestActivity';
import {TypeActivity} from '../interfaces/typeActivity';

export default function useSelectedTypeActivity(
	currentActivity: MDFRequestActivity,
	typeOfActivities: TypeActivity[] | undefined
) {
	const [selectedTypeActivity, setSelectedTypeActivity] = useState<
		TypeActivity
	>();

	useEffect(() => {
		const selectedTypeActivityId =
			currentActivity?.r_typeActivityToActivities_c_typeActivityId;

		if (selectedTypeActivityId) {
			const typeActivity = typeOfActivities?.find(
				(typeOfActivity) =>
					String(typeOfActivity.id) === selectedTypeActivityId
			);

			setSelectedTypeActivity(typeActivity);
		}
	}, [
		currentActivity?.r_typeActivityToActivities_c_typeActivityId,
		typeOfActivities,
	]);

	return selectedTypeActivity;
}
