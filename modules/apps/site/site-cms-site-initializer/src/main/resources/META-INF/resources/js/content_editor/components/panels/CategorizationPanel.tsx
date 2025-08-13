/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import AssetCategorization from '../../../main_view/info_panel/components/AssetCategorization';

export default function CategorizationPanel({
	contentAPIURL,
	groupId,
}: {
	contentAPIURL: string;
	groupId: string;
}) {
	return (
		<div className="px-3">
			<AssetCategorization
				cmsGroupId={groupId}
				getObjectEntryURL={contentAPIURL}
				inputSize="sm"
				updateObjectEntryURL={contentAPIURL}
			/>
		</div>
	);
}
