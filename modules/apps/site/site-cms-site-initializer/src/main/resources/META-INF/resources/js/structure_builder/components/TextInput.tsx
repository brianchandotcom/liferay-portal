/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useId} from 'frontend-js-components-web';
import React, {useState} from 'react';

export default function TextInput({
	className,
	label,
	onValueChange,
	required = false,
	value: initialValue,
}: {
	className?: string;
	label: string;
	onValueChange: (value: string) => void;
	required?: boolean;
	value: string;
}) {
	const id = useId();

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
				id={id}
				onBlur={() => onValueChange(value)}
				onChange={(event) => setValue(event.target.value)}
				type="text"
				value={value}
			/>
		</ClayForm.Group>
	);
}
