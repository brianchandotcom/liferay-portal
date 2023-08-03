/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import {FieldBase} from 'frontend-js-components-web';
import React, {ReactText} from 'react';

interface RadioFieldProps {
	defaultValue?: string;
	disabled?: boolean;
	id?: string;
	inline?: boolean;
	label?: string;
	onChange: (value: ReactText) => void;
	options: {
		label: string;
		value: string;
	}[];
	popover?: {
		alignPosition?: 'top' | 'bottom';
		content?: string;
		disableScroll?: boolean;
		header?: string;
	};
	required?: boolean;
	tooltip?: string;
}

export function RadioField({
	defaultValue,
	disabled,
	id,
	inline = true,
	label,
	onChange,
	options,
	popover,
	required,
	tooltip,
}: RadioFieldProps) {
	return (
		<FieldBase
			disabled={disabled}
			id={id}
			label={label}
			popover={popover}
			required={required}
			tooltip={tooltip}
		>
			<ClayRadioGroup
				defaultValue={defaultValue}
				inline={inline}
				onChange={(value: ReactText) => onChange(value as string)}
			>
				{options.map(({label, value}) => (
					<ClayRadio key={value} label={label} value={value} />
				))}
			</ClayRadioGroup>
		</FieldBase>
	);
}
