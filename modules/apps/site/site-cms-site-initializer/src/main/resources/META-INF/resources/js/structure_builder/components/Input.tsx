/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useId} from 'frontend-js-components-web';
import React, {useState} from 'react';

export default function Input({
	className,
	disabled = false,
	helpMessage,
	inputProps,
	label,
	onValueChange,
	required = false,
	value: initialValue,
}: {
	className?: string;
	disabled?: boolean;
	helpMessage?: string;
	inputProps?: React.InputHTMLAttributes<HTMLInputElement>;
	label: string;
	onValueChange: (value: string) => void;
	required?: boolean;
	value: string;
}) {
	const id = useId();
	const helpMessageId = useId();
	const [value, setValue] = useState(initialValue);

	return (
		<ClayForm.Group className={className}>
			<label htmlFor={id}>
				{label}

				{required ? (
					<ClayIcon
						className="ml-1 reference-mark"
						focusable="false"
						role="presentation"
						symbol="asterisk"
					/>
				) : null}
			</label>

			<ClayInput
				aria-describedby={helpMessageId}
				disabled={disabled}
				id={id}
				onBlur={() => onValueChange(value)}
				onChange={(event) => setValue(event.target.value)}
				type="text"
				value={value}
				{...inputProps}
			/>

			{helpMessage ? (
				<p
					className="m-0 mt-1 text-3 text-secondary"
					id={helpMessageId}
				>
					{helpMessage}
				</p>
			) : null}
		</ClayForm.Group>
	);
}
