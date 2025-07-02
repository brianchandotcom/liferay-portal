/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import React from 'react';

interface SpaceAbstractHeaderProps {
	label: string;
	title: string;
	url: string;
}

export default function SpaceAbstractHeader({
	label,
	title,
	url,
}: SpaceAbstractHeaderProps) {
	return (
		<div className="align-items-center d-flex justify-content-between">
			<h2 className="font-weight-semi-bold m-0 text-4">{title}</h2>

			<ClayLink className="text-3 text-weight-semi-bold" href={url}>
				{label}
			</ClayLink>
		</div>
	);
}
