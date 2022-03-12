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

import {ClayCheckbox} from '@clayui/form';
import {useCallback, useState} from 'react';
import DateFilter from '../DateFilter';

export default function ExpirationDate({hasDNE, setFilters}) {
	const [dneChecked, setDNEChecked] = useState(false);

	const getOnOrAfterValue = useCallback(
		(currentValue) => {
			if (dneChecked) {
				const today = new Date();
				today.setFullYear(today.getFullYear() + 100);

				return today;
			}

			return currentValue;
		},
		[dneChecked]
	);

	return (
		<DateFilter
			onOrAfterDisabled={dneChecked}
			onOrBeforeDisabled={dneChecked}
			updateFilters={(onOrAfter, onOrBefore) =>
				setFilters((previousFilters) => ({
					...previousFilters,
					expirationDate: {
						...previousFilters.expirationDate,
						value: {
							onOrAfter: getOnOrAfterValue(onOrAfter),
							onOrBefore,
						},
					},
				}))
			}
		>
			{hasDNE && (
				<ClayCheckbox
					checked={dneChecked}
					label="Does Not Expire"
					onChange={() =>
						setDNEChecked(
							(previousDNEChecked) => !previousDNEChecked
						)
					}
				/>
			)}
		</DateFilter>
	);
}
