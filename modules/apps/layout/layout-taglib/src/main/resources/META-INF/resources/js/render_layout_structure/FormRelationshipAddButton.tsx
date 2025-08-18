/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React from 'react';

export default function FormRelationshipAddButton({
	addButtonLabel,
}: {
	addButtonLabel: string;
}) {
	return (
		<ClayButton borderless displayType="primary" size="sm">
			<ClayIcon
				className="mr-2 text-primary"
				style={{transform: 'rotate(45deg)'}}
				symbol="times-circle-full"
			/>

			{addButtonLabel}
		</ClayButton>
	);
}
