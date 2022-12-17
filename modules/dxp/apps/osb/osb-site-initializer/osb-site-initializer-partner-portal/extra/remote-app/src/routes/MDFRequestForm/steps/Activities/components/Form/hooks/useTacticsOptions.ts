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

import {OptionHTMLAttributes, useEffect, useState} from 'react';

import LiferayPicklist from '../../../../../../../common/interfaces/liferayPicklist';

export default function useTacticsOptions(
	tactics: OptionHTMLAttributes<HTMLOptionElement>[] | undefined,
	handleSelected: (option: LiferayPicklist) => void
) {
	const [selectedTactic, setSelectedTactic] = useState<
		OptionHTMLAttributes<HTMLOptionElement>
	>();

	useEffect(() => {
		if (selectedTactic) {
			handleSelected({
				key: selectedTactic?.value as string,
				name: selectedTactic?.label as string,
			});
		}
	}, [handleSelected, selectedTactic]);

	const onTacticSelected = (event: React.ChangeEvent<HTMLInputElement>) => {
		const optionSelected = tactics?.find(
			(tactic) => tactic.value === event.target.value
		);

		setSelectedTactic(optionSelected);
	};

	return {
		onTacticSelected,
		selectedTactic,
		tacticsOptions: tactics,
	};
}
