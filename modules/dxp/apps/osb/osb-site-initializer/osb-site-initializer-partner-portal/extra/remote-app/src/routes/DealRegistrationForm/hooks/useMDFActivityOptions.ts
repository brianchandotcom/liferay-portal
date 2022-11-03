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

import MDFRequestActivity from '../../../common/interfaces/mdfRequestActivity';

export default function useMDFActivityOptions(
	mdfActivities: MDFRequestActivity[] | undefined,
	handleSelected: (selectedActivity?: MDFRequestActivity) => void
) {
	const onMDFActivitySelected = (
		event: React.ChangeEvent<HTMLInputElement>
	) => {
		const optionSelected = mdfActivities?.find(
			(mdfActivity) => mdfActivity.id === Number(event.target.value)
		);

		handleSelected(optionSelected);
	};

	return {
		mdfActivitiesOptions: useMemo(
			() =>
				mdfActivities?.map((mdfActivity) => ({
					label: mdfActivity.name,
					value: mdfActivity.id,
				})),
			[mdfActivities]
		),
		onMDFActivitySelected,
	};
}
