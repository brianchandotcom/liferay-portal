/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React, {useState} from 'react';

import './SectionHeader.scss';

export default function SectionHeader({
	lastScanDate: initialLastScanDate,
	title,
}: {
	lastScanDate: string | null;
	title: string;
}) {
	const [lastScanDate] = useState<string | null>(initialLastScanDate);

	const lastScanLabel = lastScanDate
		? new Date(lastScanDate).toLocaleString()
		: Liferay.Language.get('never');

	return (
		<div className="seo-studio-section-header">
			<h2 className="seo-studio-section-header-title">{title}</h2>

			<div className="seo-studio-section-header-actions text-right">
				<ClayButton
					disabled
					displayType="primary"
					title={Liferay.Language.get('run-scan-now')}
				>
					{Liferay.Language.get('run-scan-now')}
				</ClayButton>

				<div className="seo-studio-section-header-last-scan text-secondary">
					{Liferay.Language.get('last-scan')}: {lastScanLabel}
				</div>
			</div>
		</div>
	);
}
