/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SWITCH_SIDEBAR_PANEL} from './types';

export default function switchSidebarPanel({
	hidden = false,
	itemConfigurationOpen = true,
	sidebarOpen,
	sidebarPanelId,
}: {
	hidden?: boolean;
	itemConfigurationOpen?: boolean;
	sidebarOpen?: boolean;
	sidebarPanelId?: string | null;
}) {
	return {
		hidden,
		itemConfigurationOpen,
		sidebarOpen,
		sidebarPanelId,
		type: SWITCH_SIDEBAR_PANEL,
	} as const;
}
