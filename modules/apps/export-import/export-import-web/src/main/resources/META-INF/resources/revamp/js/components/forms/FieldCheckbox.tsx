/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import React from 'react';

import {OptionRow} from './OptionRow';

export function FieldCheckbox({
	bordered = true,
	checked,
	description,
	id,
	label,
	name,
	onChange,
	...restProps
}: {
	bordered?: boolean;
	checked: boolean;
	description?: string;
	id?: string;
	label: string;
	name: string;
	onChange: (checked: boolean) => void;
} & React.ComponentProps<typeof ClayCheckbox>) {
	const fieldId = id ?? name;
	const labelId = `${fieldId}-label`;
	const descriptionId = `${fieldId}-description`;

	return (
		<OptionRow
			bordered={bordered}
			description={description}
			descriptionId={description ? descriptionId : undefined}
			input={
				<ClayCheckbox
					{...restProps}
					aria-describedby={description ? descriptionId : undefined}
					aria-labelledby={labelId}
					checked={checked}
					id={fieldId}
					onChange={() => onChange(!checked)}
				/>
			}
			label={label}
			labelId={labelId}
		/>
	);
}
