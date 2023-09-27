/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayMultiSelect from '@clayui/multi-select';
import React, {useState} from 'react';

function MultiSelectInput({
	disabled,
	id,
	label,
	name,
	setFieldTouched,
	setFieldValue,
	value,
}) {
	const [inputValue, setInputValue] = useState('');

	const _handleKeyDown = (event) => {
		if (event.key === 'Enter') {
			event.preventDefault();
		}
	};

	return (
		<ClayMultiSelect
			aria-label={label}
			disabled={disabled}
			id={id}
			items={value || []}
			onBlur={() => {
				setFieldTouched(name);

				if (inputValue) {
					setFieldValue(name, [
						...value,
						{label: inputValue, value: inputValue},
					]);

					setInputValue('');
				}
			}}
			onChange={setInputValue}
			onItemsChange={(value) => setFieldValue(name, value)}
			onKeyDown={_handleKeyDown}
			value={inputValue}
		/>
	);
}

export default MultiSelectInput;
