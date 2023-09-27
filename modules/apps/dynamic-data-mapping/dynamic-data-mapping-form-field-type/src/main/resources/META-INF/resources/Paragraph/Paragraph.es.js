/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';

const Paragraph = ({name, text, ...otherProps}) => (
	<FieldBase {...otherProps} name={name} text={text}>
		<div
			className="form-group liferay-ddm-form-field-paragraph"
			data-field-name={name}
		>
			<div
				className="liferay-ddm-form-field-paragraph-text"
				dangerouslySetInnerHTML={{
					__html: text,
				}}
			/>
		</div>
	</FieldBase>
);

export default Paragraph;
