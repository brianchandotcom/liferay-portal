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

import {ClaySelect, ClaySelectWithOption} from '@clayui/form';
import React from 'react';

import {FieldBase} from './FieldBase';

export function SelectWithOption({
	ariaLabel,
	className,
	disabled,
	error,
	feedbackMessage,
	id,
	label,
	required,
	tooltip,
	...otherProps
}: IProps) {
	return (
		<FieldBase
			className={className}
			disabled={disabled}
			errorMessage={error}
			helpMessage={feedbackMessage}
			id={id}
			label={label}
			required={required}
			tooltip={tooltip}
		>
			<ClaySelectWithOption {...otherProps} aria-label={ariaLabel} />
		</FieldBase>
	);
}

interface IProps extends React.SelectHTMLAttributes<HTMLSelectElement> {
	ariaLabel?: string;
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	label?: string;
	options: Array<
		(
			| React.ComponentProps<typeof ClaySelect.Option>
			| React.ComponentProps<typeof ClaySelect.OptGroup>
		) & {
			options?: Array<React.ComponentProps<typeof ClaySelect.Option>>;
			type?: 'group';
		}
	>;
	required?: boolean;
	tooltip?: string;
}
