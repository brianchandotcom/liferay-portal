/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {ControlledSelect} from '.';
import {STATE_REGEX} from '../../../../../common/utils/patterns';

import {useLocation} from '../../../../../routes/get-a-quote/hooks/useLocation';

export function StatesControlledSelect({rules, ...props}) {
	const {states} = useLocation();

	return (
		<ControlledSelect
			{...props}
			defaultValue="AL"
			rules={{
				pattern: {
					message: 'Should be a two letter word.',
					value: STATE_REGEX,
				},
				...rules,
			}}
		>
			{states.map(({abbreviation}) => (
				<option key={abbreviation} value={abbreviation}>
					{abbreviation}
				</option>
			))}
		</ControlledSelect>
	);
}
