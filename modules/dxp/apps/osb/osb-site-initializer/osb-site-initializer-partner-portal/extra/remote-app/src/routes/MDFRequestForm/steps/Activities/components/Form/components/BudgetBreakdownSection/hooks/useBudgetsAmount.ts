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

import useDebounce from '../../../../../../../../../common/hooks/useDebounce';
import MDFRequestBudget from '../../../../../../../../../common/interfaces/mdfRequestBudget';

export default function useBudgetsAmount(
	budgets: MDFRequestBudget[],
	onAmountUpdate: (value: number) => void
) {
	const [budgetsAmount, setBudgetsAmount] = useState<number>(0);
	const debouncedBudgets = useDebounce<MDFRequestBudget[]>(budgets, 500);

	useEffect(() => {
		const amountValue = debouncedBudgets.reduce<number>(
			(previousValue, currentValue) => previousValue + +currentValue.cost,
			0
		);

		if (amountValue) {
			setBudgetsAmount(amountValue);
			onAmountUpdate(amountValue);
		}
	}, [debouncedBudgets, onAmountUpdate]);

	return budgetsAmount;
}
