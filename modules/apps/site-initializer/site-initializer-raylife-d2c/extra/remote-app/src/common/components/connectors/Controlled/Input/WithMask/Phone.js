/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {ControlledInputWithMask} from '.';

import {PHONE_REGEX} from '../../../../../utils/patterns';

export function PhoneControlledInput({rules = {}, inputProps = {}, ...props}) {
	return (
		<ControlledInputWithMask
			{...props}
			inputProps={{
				className: 'd-flex mb-5 mr-0',
				format: '(###) ###-####',
				mask: '_',
				...inputProps,
				placeholder: '(___) ___-____',
			}}
			rules={{
				pattern: {
					message: 'Must be a valid phone number.',
					value: PHONE_REGEX,
				},
				...rules,
			}}
		/>
	);
}
