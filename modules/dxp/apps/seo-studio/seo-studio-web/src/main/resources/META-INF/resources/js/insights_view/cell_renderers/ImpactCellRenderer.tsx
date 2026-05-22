/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import React from 'react';

function getDisplayType(severityKey: string) {
	switch (severityKey) {
		case 'critical':
			return 'danger';
		case 'high':
			return 'warning';
		case 'medium':
			return 'info';
		default:
			return 'secondary';
	}
}

export default function ImpactCellRenderer({
	itemData,
	value,
}: {
	itemData: {severity?: {key?: string}};
	value: string;
}) {
	if (!value) {
		return null;
	}

	return (
		<ClayLabel
			displayType={getDisplayType(itemData?.severity?.key ?? '')}
			withClose={false}
		>
			{value}
		</ClayLabel>
	);
}
