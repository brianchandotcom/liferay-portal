/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactFieldBase as FieldBase} from 'dynamic-data-mapping-form-field-type';
import React, {useState} from 'react';

import AttachmentBase, {AttachmentBaseProps} from './AttachmentBase';

export default function Attachment({
	readOnly,
	tip,
	...otherProps
}: AttachmentBaseProps) {
	const [error, setError] = useState({});

	return (
		<FieldBase
			readOnly={readOnly}
			tip={!readOnly ? tip : ''}
			{...otherProps}
			{...error}
		>
			<AttachmentBase
				{...otherProps}
				error={error}
				readOnly={readOnly}
				setError={setError}
				tip={tip}
			/>
		</FieldBase>
	);
}
