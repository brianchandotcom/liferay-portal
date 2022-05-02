/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
