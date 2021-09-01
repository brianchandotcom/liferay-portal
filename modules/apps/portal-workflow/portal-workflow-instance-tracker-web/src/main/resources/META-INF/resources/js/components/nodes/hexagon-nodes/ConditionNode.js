/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayIcon from '@clayui/icon';
import React from 'react';

import HexagonNode from './HexagonNode';

export default function ConditionNode({
	data: {done = false, label, notifyVisibilityChange},
}) {
	return (
		<HexagonNode
			notifyVisibilityChange={notifyVisibilityChange}
			title={label}
		>
			<ClayIcon className="mr-2" symbol="bolt" />
			<span>{label}</span>
			{done && (
				<ClayIcon
					className="done-icon hexagon-done-icon"
					symbol="check"
				/>
			)}
		</HexagonNode>
	);
}
