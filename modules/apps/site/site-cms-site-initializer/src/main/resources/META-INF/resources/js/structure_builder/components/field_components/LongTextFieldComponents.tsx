/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {Field} from '../../utils/field';
import MaxLengthInput from '../MaxLengthInput';

export default function getLongTextFieldComponents(): {
	FirstSectionComponent?: React.FC<{field: Field; readOnly?: boolean}>;
	SecondSectionComponent?: React.FC<{field: Field; readOnly?: boolean}>;
} {
	return {
		SecondSectionComponent,
	};
}

function SecondSectionComponent({
	field,
	readOnly,
}: {
	field: Field;
	readOnly?: boolean;
}) {
	return <MaxLengthInput disabled={readOnly} field={field} />;
}
