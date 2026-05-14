/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

export default function DashboardCardSkeleton() {
	return (
		<div
			aria-hidden="true"
			className="dashboard-card dashboard-card--skeleton"
		>
			<div className="dashboard-card__skeleton-icon" />

			<div className="dashboard-card__skeleton-line dashboard-card__skeleton-line--title" />

			<div className="dashboard-card__skeleton-line dashboard-card__skeleton-line--desc" />

			<div className="dashboard-card__skeleton-line dashboard-card__skeleton-line--tags" />
		</div>
	);
}
