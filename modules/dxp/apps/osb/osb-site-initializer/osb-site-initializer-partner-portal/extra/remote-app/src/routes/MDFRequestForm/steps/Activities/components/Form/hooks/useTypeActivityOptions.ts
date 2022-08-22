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

import {useEffect, useMemo, useState} from 'react';

import TypeActivity from '../../../../../../../common/interfaces/typeActivity';
import useGetTacticsByTypeActivityId from '../../../../../../../common/services/liferay/object/type-activities/useGetTacticsByTypeActivityId';

export default function useTypeActivityOptions(
	typeActivities: TypeActivity[] | undefined,
	handleSelected: (typeActivity: TypeActivity) => void
) {
	const [selectedTypeActivity, setSelectedTypeActivity] = useState<
		TypeActivity
	>();

	const {data: tactics} = useGetTacticsByTypeActivityId(
		selectedTypeActivity?.id
	);

	useEffect(() => {
		if (selectedTypeActivity) {
			handleSelected(selectedTypeActivity);
		}
	}, [handleSelected, selectedTypeActivity]);

	const onTypeActivitySelected = (
		event: React.ChangeEvent<HTMLInputElement>
	) => {
		const optionSelected = typeActivities?.find(
			(typeActivity) => typeActivity.id === +event.target.value
		);

		setSelectedTypeActivity(optionSelected);
	};

	return {
		onTypeActivitySelected,
		selectedTypeActivity,
		tacticsBySelectedTypeActivity: tactics?.items,
		typeActivitiesOptions: useMemo(
			() =>
				typeActivities?.map((typeActivity) => ({
					label: typeActivity.name,
					value: typeActivity.id,
				})) as React.OptionHTMLAttributes<HTMLOptionElement>[],
			[typeActivities]
		),
	};
}
