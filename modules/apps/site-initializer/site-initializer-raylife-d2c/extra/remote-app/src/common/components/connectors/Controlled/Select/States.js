/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
