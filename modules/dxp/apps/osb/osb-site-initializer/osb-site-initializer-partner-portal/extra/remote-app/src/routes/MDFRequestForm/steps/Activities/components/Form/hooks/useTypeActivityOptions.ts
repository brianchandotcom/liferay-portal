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

import {OptionHTMLAttributes, useEffect, useMemo, useState} from 'react';

import LiferayPicklist from '../../../../../../../common/interfaces/liferayPicklist';

export default function useTypeActivityOptions(
	typeActivities: OptionHTMLAttributes<HTMLOptionElement>[] | undefined,
	tactics: OptionHTMLAttributes<HTMLOptionElement>[] | undefined,
	handleSelected: (option: LiferayPicklist) => void
) {
	const [selectedTypeActivity, setSelectedTypeActivity] = useState<
		OptionHTMLAttributes<HTMLOptionElement>
	>();

	const tacticsBySelectedTypeActivity = useMemo(
		() =>
			tactics?.filter((tactic) => {
				return String(tactic.value).includes(
					String(selectedTypeActivity?.value)
				);
			}),
		[selectedTypeActivity?.value, tactics]
	);

	useEffect(() => {
		if (selectedTypeActivity) {
			handleSelected({
				key: selectedTypeActivity?.value as string,
				name: selectedTypeActivity?.label as string,
			});
		}
	}, [handleSelected, selectedTypeActivity]);

	const onTypeActivitySelected = (
		event: React.ChangeEvent<HTMLInputElement>
	) => {
		const optionSelected = typeActivities?.find((typeActivity) => {
			return typeActivity.value === event.target.value;
		});

		setSelectedTypeActivity(optionSelected);
	};

	return {
		onTypeActivitySelected,
		selectedTypeActivity,
		tacticsBySelectedTypeActivity,
		typeActivitiesOptions: typeActivities,
	};
}
