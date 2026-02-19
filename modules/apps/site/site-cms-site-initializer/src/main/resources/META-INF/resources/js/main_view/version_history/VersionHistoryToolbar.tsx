/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import Toolbar from '../../common/components/Toolbar';
import BulkActionsMonitor from '../bulk_actions_monitor/BulkActionsMonitor';

export default function VersionHistoryToolbar({
	backURL,
	title,
}: {
	backURL: string;
	title: string;
}) {
	return (
		<Toolbar backURL={backURL} title={title}>
			<Toolbar.Item>
				<BulkActionsMonitor />
			</Toolbar.Item>
		</Toolbar>
	);
}
