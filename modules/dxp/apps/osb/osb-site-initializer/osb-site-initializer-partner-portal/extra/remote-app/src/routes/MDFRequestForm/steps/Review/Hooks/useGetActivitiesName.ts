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

import {useMemo} from 'react';

import {TypeActivity} from '../../../../../common/interfaces/typeActivity';
import useGetTypeActivities from '../../../../../common/services/liferay/object/type-activities/useGetTypeActivities';

export default function useGetActivitiesName(id: string) {
	const {data: typeOfActivities} = useGetTypeActivities();

	const typeOfActivitiesName = useMemo(
		() =>
			typeOfActivities?.items.find(
				(activitie: TypeActivity) => activitie.id === +id
			),
		[id, typeOfActivities?.items]
	);

	return typeOfActivitiesName?.name;
}
