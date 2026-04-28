/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayRadio} from '@clayui/form';
import React from 'react';

import {OptionRow} from './OptionRow';

export function FieldRadio({
	checked,
	description,
	id,
	label,
	name,
	onChange,
	value,
	...restProps
}: {
	checked: boolean;
	description?: string;
	id?: string;
	label: string;
	name: string;
	onChange: () => void;
	value: string;
} & Omit<React.ComponentProps<typeof ClayRadio>, 'onChange' | 'value'>) {
	const fieldId = id ?? `${name}-${value}`;
	const labelId = `${fieldId}-label`;
	const descriptionId = `${fieldId}-description`;

	return (
		<OptionRow
			description={description}
			descriptionId={descriptionId}
			input={
				<ClayRadio
					{...restProps}
					aria-describedby={description ? descriptionId : undefined}
					aria-labelledby={labelId}
					checked={checked}
					id={fieldId}
					name={name}
					onChange={onChange}
					onClick={(event) => event.stopPropagation()}
					value={value}
				/>
			}
			label={label}
			labelHtmlFor={fieldId}
			labelId={labelId}
			onClick={onChange}
		/>
	);
}
