/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {ControlledInputWithMask} from '.';

import {YEAR_REGEX} from '../../../../../utils/patterns';

export function YearControlledInput({rules = {}, inputProps = {}, ...props}) {
	return (
		<ControlledInputWithMask
			{...props}
			inputProps={{
				className: 'mb-5',
				format: '####',
				mask: '_',
				...inputProps,
			}}
			rules={{
				max: {
					message: 'You cannot enter a future year.',
					value: new Date().getFullYear(),
				},
				pattern: {
					message: 'Must be a valid year.',
					value: YEAR_REGEX,
				},
				...rules,
			}}
		/>
	);
}
