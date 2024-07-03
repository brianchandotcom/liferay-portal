/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {VersionRow} from './VersionRow';

interface RevisionHistoryProps {
	version: number;
}

export function RevisionHistory({version}: RevisionHistoryProps) {
	const otherVersions = [];

	for (let i = version - 1; i > 0; i--) {
		otherVersions.push({versionNumber: i});
	}

	return (
		<>
			<div className="info-group">
				<label>
					{Liferay.Language.get('current-version')}: {version}
				</label>

				<div className="sheet-subtitle" />
			</div>

			{otherVersions.map(({versionNumber}, index) => (
				<VersionRow key={index} versionNumber={versionNumber} />
			))}
		</>
	);
}
