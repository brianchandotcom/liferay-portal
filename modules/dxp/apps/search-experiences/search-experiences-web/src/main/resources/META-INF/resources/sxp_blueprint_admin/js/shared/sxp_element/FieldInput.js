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

import React from 'react';

import FieldRow from './FieldRow';

function FieldInput({
	disabled,
	id,
	indexFields,
	name,
	setFieldTouched,
	setFieldValue,
	showBoost,
	value,
}) {
	const _handleBlur = () => {
		setFieldTouched(name);
	};

	const _handleChange = (newValue) => {
		setFieldValue(name, {...value, ...newValue});
	};

	return (
		<div className="single-field">
			<FieldRow
				boost={value?.boost || 1}
				disabled={disabled}
				field={value?.field || ''}
				id={id}
				indexFields={indexFields}
				languageIdPosition={value?.languageIdPosition || -1}
				locale={value?.locale || ''}
				onBlur={_handleBlur}
				onChange={_handleChange}
				showBoost={showBoost}
			/>
		</div>
	);
}

export default FieldInput;
