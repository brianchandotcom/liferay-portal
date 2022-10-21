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

import LiferayPicklist from '../interfaces/liferayPicklist';

export default function getPicklistOptions<T>(
	options: React.OptionHTMLAttributes<HTMLOptionElement>[],
	handleSelected: (option: LiferayPicklist, optionalValue?: T) => void
) {
	const onSelected = (
		event: React.ChangeEvent<HTMLInputElement>,
		optionalValue?: T
	) => {
		const optionSelected = options.find(
			(option) => option.value === event.target.value
		);

		handleSelected(
			{
				key: optionSelected?.value as string,
				name: optionSelected?.label as string,
			},
			optionalValue
		);
	};

	return {
		onSelected,
		options,
	};
}
