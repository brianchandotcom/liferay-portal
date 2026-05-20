/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import React from 'react';

interface RuleTextFieldProps {
	isNumber?: boolean;
	onBlur: () => void;
	onChange: (value: string) => void;
	value: string;
}

export default function RuleTextField({
	isNumber,
	onBlur,
	onChange,
	value,
}: RuleTextFieldProps) {
	return (
		<ClayInput
			aria-label={Liferay.Language.get('value')}
			className="w-auto"
			onBlur={onBlur}
			onChange={(event) => onChange(event.target.value)}
			onKeyDown={(event) => {
				if (event.key === 'Enter') {
					onBlur();
				}
			}}
			sizing="sm"
			step={isNumber ? 'any' : undefined}
			type={isNumber ? 'number' : 'text'}
			value={value}
		/>
	);
}
