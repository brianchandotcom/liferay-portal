/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import React, {useEffect, useState} from 'react';

interface IProps {
	inputId: string;
	onCommit: (value: string) => void;
	value: string;
}

export default function LiteralInput({inputId, onCommit, value}: IProps) {
	const [localValue, setLocalValue] = useState(value);

	useEffect(() => {
		setLocalValue(value);
	}, [value]);

	return (
		<ClayInput
			id={inputId}
			onBlur={() => {
				if (localValue !== value) {
					onCommit(localValue);
				}
			}}
			onChange={(event) => setLocalValue(event.target.value)}
			sizing="sm"
			type="text"
			value={localValue}
		/>
	);
}
