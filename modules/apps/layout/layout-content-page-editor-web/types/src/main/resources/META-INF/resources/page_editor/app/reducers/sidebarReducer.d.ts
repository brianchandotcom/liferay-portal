/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import switchSidebarPanel from '../actions/switchSidebarPanel';
interface State {
	hidden: boolean;
	itemConfigurationOpen: boolean;
	open: boolean;
	panelId: string | null;
}
export declare const INITIAL_STATE: State;
export default function sidebarReducer(
	sidebarStatus: State | undefined,
	action: ReturnType<typeof switchSidebarPanel>
): State;
export {};
