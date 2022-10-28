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

import {useEffect} from 'react';

import useDebounce from '../../../../../../../common/hooks/useDebounce';
import MDFClaimBudget from '../../../../../../../common/interfaces/mdfClaimBudget';

export default function useBudgetsAmount(
	budgets: MDFClaimBudget[] | undefined,
	onAmountUpdate: (value: number) => void
) {
	const debouncedBudgets = useDebounce<MDFClaimBudget[] | undefined>(
		budgets,
		500
	);

	useEffect(() => {
		const amountValue = debouncedBudgets?.reduce<number>(
			(previousValue, currentValue) => {
				if (!currentValue.selected) {
					return previousValue;
				}

				return previousValue + Number(currentValue.invoiceAmount);
			},
			0
		);

		if (amountValue !== undefined) {
			onAmountUpdate(amountValue);
		}
	}, [debouncedBudgets, onAmountUpdate]);
}
