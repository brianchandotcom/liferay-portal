/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClaySelectWithOption} from '@clayui/form';
import {FieldBase} from 'frontend-js-components-web';
import React from 'react';

export type FieldSelectWithOptionProps = {
	disabled?: boolean;
	errorMessage?: string;
	formGroupProps?: {className: string};
	helpMessage?: string;
	id?: string;
	label: string;
	name: string;
	required?: boolean;
	value?: string;
} & React.ComponentProps<typeof ClaySelectWithOption>;

const FieldSelectWithOption = ({
	disabled,
	errorMessage,
	formGroupProps,
	helpMessage,
	id,
	label,
	name,
	options,
	required,
	value = '',
	...restProps
}: FieldSelectWithOptionProps) => {
	const fieldId = id ?? name;

	return (
		<FieldBase
			className={formGroupProps?.className}
			disabled={disabled}
			errorMessage={errorMessage}
			helpMessage={helpMessage}
			id={fieldId}
			label={label}
			required={required}
		>
			<ClaySelectWithOption
				{...restProps}
				aria-describedby={
					errorMessage || helpMessage
						? `${fieldId}fieldFeedback`
						: undefined
				}
				aria-invalid={errorMessage ? true : undefined}
				disabled={disabled}
				id={fieldId}
				name={name}
				options={options}
				required={required}
				value={value}
			/>
		</FieldBase>
	);
};

export default FieldSelectWithOption;
