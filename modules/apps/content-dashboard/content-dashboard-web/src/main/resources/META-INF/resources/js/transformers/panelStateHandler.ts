/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import SidebarPanelInfoView from '../components/SidebarPanelInfoView/SidebarPanelInfoView';

// @ts-ignore

import {OPEN_PANEL_VALUE} from '../utils/constants';

// @ts-ignore

import ActionsComponentPropsTransformer from './ActionsComponentPropsTransformer';

const handlePanelStateFromSession = ({
	contentPerformanceDataFetchURL,
	currentRowId,
	namespace,
	panelState,
	selectedItemFetchURL,
	selectedItemRowId,
	singlePageApplicationEnabled,
}: {
	contentPerformanceDataFetchURL: string;
	currentRowId: string;
	namespace: string;
	panelState: string;
	selectedItemFetchURL: string;
	selectedItemRowId: string;
	singlePageApplicationEnabled: boolean;
}) => {
	if (
		!selectedItemRowId ||
		panelState !== OPEN_PANEL_VALUE ||
		selectedItemRowId !== currentRowId
	) {
		return;
	}

	const allRequestValuesArePositive = [
		selectedItemFetchURL,
		namespace,
		selectedItemRowId,
	].every(Boolean);

	if (!allRequestValuesArePositive) {
		return;
	}

	ActionsComponentPropsTransformer.showSidebar({
		View: SidebarPanelInfoView,
		contentPerformanceDataFetchURL,
		fetchURL: selectedItemFetchURL,
		portletNamespace: namespace,
		singlePageApplicationEnabled,
	});

	ActionsComponentPropsTransformer.selectRow(namespace, selectedItemRowId);
};

const handleSessionOnSidebarOpen = ({
	panelState,
	rowId,
	selectedItemRowId,
}: {
	panelState: string;
	rowId: string;
	selectedItemRowId: string;
}) => {
	if (panelState !== OPEN_PANEL_VALUE) {
		Liferay.Util.Session.set(
			'com.liferay.content.dashboard.web_panelState',
			OPEN_PANEL_VALUE
		);
	}

	if (selectedItemRowId !== rowId) {
		Liferay.Util.Session.set(
			'com.liferay.content.dashboard.web_selectedItemRowId',
			rowId
		);
	}
};

export {handlePanelStateFromSession, handleSessionOnSidebarOpen};
