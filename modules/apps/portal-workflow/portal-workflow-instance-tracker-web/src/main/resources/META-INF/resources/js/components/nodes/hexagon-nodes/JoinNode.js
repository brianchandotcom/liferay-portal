/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React from 'react';

import HexagonNode from './HexagonNode';

export default function JoinNode({
	data: {done = false, label, notifyVisibilityChange},
}) {
	return (
		<HexagonNode
			notifyVisibilityChange={notifyVisibilityChange}
			title={label}
		>
			<ClayIcon className="mr-2" symbol="arrow-join" />

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
