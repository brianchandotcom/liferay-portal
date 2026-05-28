/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

export default function InsightDetailView({
	insightTypeId,
}: {
	insightTypeId: number;
}) {
	return (
		<div className="seo-studio-insight-detail">
			<div className="seo-studio-insight-detail-header">
				<h2>{insightTypeId}</h2>
			</div>
		</div>
	);
}
