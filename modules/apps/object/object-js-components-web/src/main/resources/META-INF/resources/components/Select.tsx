/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClaySelect} from '@clayui/form';
import {FieldBase} from 'frontend-js-components-web';
import React from 'react';

interface ISelectProps extends React.SelectHTMLAttributes<HTMLSelectElement> {
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	label: string;
	options?: {key?: string; label?: string; name?: string}[];
	required?: boolean;
	tooltip?: string;
}

export function Select({
	className,
	disabled,
	error,
	feedbackMessage,
	id,
	label,
	onChange,
	options,
	required,
	tooltip,
	value,
	...otherProps
}: ISelectProps) {
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
			<ClaySelect
				{...otherProps}
				disabled={disabled}
				id={id}
				onChange={onChange}
			>
				<ClaySelect.Option
					disabled
					label={Liferay.Language.get('choose-an-option')}
					selected={!value}
				/>

				{options?.map(({key, label, name}, index) => (
					<ClaySelect.Option
						key={index}
						label={label ?? name}
						selected={
							value !== undefined &&
							(value === key ?? value === name ?? value === label)
						}
						value={key ?? name ?? label}
					/>
				))}
			</ClaySelect>
		</FieldBase>
	);
}
