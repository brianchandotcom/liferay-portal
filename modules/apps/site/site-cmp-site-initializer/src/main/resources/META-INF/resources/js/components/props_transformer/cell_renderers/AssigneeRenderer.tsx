/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {AssigneeAvatar} from '@liferay/object-dynamic-data-mapping-form-field-type';
import React from 'react';

const AssigneeRenderer = ({image, name}: {image: string; name: string}) => {
	return (
		<span className="align-items-center d-flex">
			<div className="c-mr-2">
				<AssigneeAvatar name={name} portrait={image} />
			</div>

			{name}
		</span>
	);
};

export default AssigneeRenderer;
