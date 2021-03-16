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

import React, {useState} from 'react';

import FieldRow from './FieldRow';

function FieldInput({
	configKey,
	defaultValue,
	disabled,
	id,
	indexFields,
	initialValue,
	onChange,
	showBoost,
	typeOptions,
}) {
	const [value, setValue] = useState(initialValue || defaultValue);

	const _handleBlur = () => {
		onChange(configKey, value);
	};

	const _handleChange = (newValue) => {
		setValue({...value, ...newValue});
	};

	return (
		<div className="single-field">
			<FieldRow
				boost={value.boost}
				configKey={configKey}
				defaultValue={defaultValue ? [defaultValue] : undefined}
				disabled={disabled}
				field={value.field}
				id={id}
				indexFields={indexFields}
				languageIdPosition={value.languageIdPosition}
				locale={value.locale}
				onBlur={_handleBlur}
				onChange={_handleChange}
				showBoost={showBoost}
				typeOptions={typeOptions}
			/>
		</div>
	);
}

export default FieldInput;
