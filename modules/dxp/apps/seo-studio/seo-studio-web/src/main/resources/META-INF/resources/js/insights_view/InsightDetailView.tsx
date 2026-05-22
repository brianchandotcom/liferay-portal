/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React from 'react';

export default function InsightDetailView({
	insightName,
	onBack,
}: {
	insightName: string;
	onBack: () => void;
}) {
	return (
		<div className="seo-studio-insight-detail">
			<div className="seo-studio-insight-detail-header">
				<ClayButton displayType="secondary" onClick={onBack} small>
					{Liferay.Language.get('back')}
				</ClayButton>

				<h2>{insightName}</h2>
			</div>
		</div>
	);
}
