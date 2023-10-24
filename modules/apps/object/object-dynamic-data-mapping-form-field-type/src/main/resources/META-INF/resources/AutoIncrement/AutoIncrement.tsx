/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import {ReactFieldBase as FieldBase} from 'dynamic-data-mapping-form-field-type';
import React from 'react';

interface AutoIncrementProps {
	label: string;
	name: string;
	value: string;
}

export default function AutoIncrement({
	label,
	name,
	value,
	...otherProps
}: AutoIncrementProps) {
	if (!value) {
		return null;
	}

	return (
		<FieldBase label={label} name={name} {...otherProps}>
			<ClayInput readOnly value={value} />
		</FieldBase>
	);
}
