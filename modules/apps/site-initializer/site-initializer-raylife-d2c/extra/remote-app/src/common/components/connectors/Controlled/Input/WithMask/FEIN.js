/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {ControlledInputWithMask} from '.';

import {FEIN_REGEX} from '../../../../../utils/patterns';

export function FEINControlledInput({rules = {}, inputProps = {}, ...props}) {
	return (
		<ControlledInputWithMask
			{...props}
			inputProps={{format: '##-#######', mask: '_', ...inputProps}}
			rules={{
				pattern: {
					message: 'Please enter a valid FEIN.',
					value: FEIN_REGEX,
				},
				...rules,
			}}
		/>
	);
}
