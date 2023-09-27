/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

declare const handlePanelStateFromSession: ({
	currentRowId,
	namespace,
	panelState,
	selectedItemFetchURL,
	selectedItemRowId,
	singlePageApplicationEnabled,
}: {
	currentRowId: string;
	namespace: string;
	panelState: string;
	selectedItemFetchURL: string;
	selectedItemRowId: string;
	singlePageApplicationEnabled: boolean;
}) => void;
declare const handleSessionOnSidebarOpen: ({
	panelState,
	rowId,
	selectedItemRowId,
}: {
	panelState: string;
	rowId: string;
	selectedItemRowId: string;
}) => void;
export {handlePanelStateFromSession, handleSessionOnSidebarOpen};
