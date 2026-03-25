/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {navigate} from 'frontend-js-web';
import React from 'react';

interface RecentAssetsHeaderProps {
	ariaLevel?: number;
	label: string;
	title: string;
	url: string;
}

export default function RecentAssetsHeader({
	ariaLevel = 2,
	label,
	title,
	url,
}: RecentAssetsHeaderProps) {
	return (
		<div className="align-items-center d-flex justify-content-between">
			<span
				aria-level={ariaLevel}
				className="font-weight-semi-bold text-4"
				role="heading"
			>
				{title}
			</span>

			<ClayButton
				className="font-weight-semi-bold"
				displayType="link"
				onClick={() => navigate(url)}
				size="sm"
			>
				{label}
			</ClayButton>
		</div>
	);
}
