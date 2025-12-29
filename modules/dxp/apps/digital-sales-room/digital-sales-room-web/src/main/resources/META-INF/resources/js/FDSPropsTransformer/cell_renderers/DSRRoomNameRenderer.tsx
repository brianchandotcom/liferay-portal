/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

const DSRRoomNameRenderer = ({
	itemData,
}: {
	itemData: {
		description: string;
		name: string;
	};
}) => {
	const {description, name} = itemData;

	return (
		<div>
			<div className="text-weight-semi-bold">{name}</div>

			<div className="text-2 text-truncate">{description}</div>
		</div>
	);
};

export default DSRRoomNameRenderer;
