/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
