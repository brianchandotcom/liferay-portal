/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {ControlledInputWithMask} from '.';

export function PercentageControlledInput({inputProps = {}, ...props}) {
	return (
		<ControlledInputWithMask
			{...props}
			inputProps={{
				className: 'mb-5',
				decimalScale: 2,
				mask: '_',
				placeholder: '%',
				suffix: '%',
				...inputProps,
			}}
		/>
	);
}
