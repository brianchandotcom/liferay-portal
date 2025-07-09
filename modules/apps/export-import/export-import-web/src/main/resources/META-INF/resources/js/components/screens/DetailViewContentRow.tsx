/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React from 'react';

interface DetailViewContentRowProps {
	body: React.ReactNode;
	title?: string;
}

export function DetailViewContentRow({
	body,
	title,
}: DetailViewContentRowProps): JSX.Element {
	return (
		<div className="sheet-text">
			{title && (
				<ClayLayout.ContentCol className="text-body">
					<strong>{title}</strong>
				</ClayLayout.ContentCol>
			)}

			<ClayLayout.ContentCol>{body}</ClayLayout.ContentCol>
		</div>
	);
}
