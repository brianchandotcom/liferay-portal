/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

const {
	default: SidebarPanelInfoView,
} = require('../components/SidebarPanelInfoView/SidebarPanelInfoView');
const {
	default: SidebarPanelInfoViewCollapsable,
} = require('../components/SidebarPanelInfoView/SidebarPanelInfoViewCollapsable');
const {OPEN_PANEL_VALUE} = require('../utils/constants');
const ActionsComponentPropsTransformer = require('./ActionsComponentPropsTransformer');

const handlePanelStateFromSession = ({
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
