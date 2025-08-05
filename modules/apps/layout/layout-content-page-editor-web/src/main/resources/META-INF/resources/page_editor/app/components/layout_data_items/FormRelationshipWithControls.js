/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import ContainerWithControls from './ContainerWithControls';
import FormRelationship from './FormRelationship';

const FormRelationshipWithControls = React.forwardRef(
	({children, item, ...rest}, ref) => {
		return (
			<ContainerWithControls {...rest} item={item} ref={ref}>
				<FormRelationship item={item}>{children}</FormRelationship>
			</ContainerWithControls>
		);
	}
);

export default FormRelationshipWithControls;
