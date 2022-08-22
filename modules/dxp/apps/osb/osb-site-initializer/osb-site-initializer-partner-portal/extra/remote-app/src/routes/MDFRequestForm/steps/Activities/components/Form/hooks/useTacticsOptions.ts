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

import Tactic from '../../../../../../../common/interfaces/tactic';

export default function useTacticsOptions(
	tactics: Tactic[] | undefined,
	handleSelected: (selectedTactic?: Tactic) => void
) {
	const onTacticSelected = (event: React.ChangeEvent<HTMLInputElement>) => {
		const optionSelected = tactics?.find(
			(tactic) => tactic.id === +event.target.value
		);

		handleSelected(optionSelected);
	};

	return {
		onTacticSelected,
		tacticsOptions: useMemo(
			() =>
				tactics?.map((tatic) => ({
					label: tatic.name,
					value: tatic.id,
				})),
			[tactics]
		),
	};
}
