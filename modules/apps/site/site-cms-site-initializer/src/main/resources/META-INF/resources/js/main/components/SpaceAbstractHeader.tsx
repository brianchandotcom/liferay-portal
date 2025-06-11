/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import React from 'react';

export default function SpaceAbstractHeader({
	label,
	title,
	url,
}: {
	label: string;
	title: string;
	url: string;
}) {
	return (
		<div>
			<div>
				<h2>{title}</h2>
			</div>

			<div>
				<ClayLink displayType="secondary" href={url}>
					{label}
				</ClayLink>
			</div>
		</div>
	);
}
