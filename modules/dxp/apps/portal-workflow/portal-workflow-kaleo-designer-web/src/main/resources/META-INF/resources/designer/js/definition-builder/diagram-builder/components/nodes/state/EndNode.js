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

import BaseNode from '../BaseNode';

export default function EndNode({
	data: {description} = {},
	descriptionSidebar,
	...otherProps
}) {
	return (
		<BaseNode
			className="end-node"
			description={description}
			descriptionSidebar={descriptionSidebar}
			icon="flag-full"
			label={Liferay.Language.get('end')}
			type="end"
			{...otherProps}
		/>
	);
}
