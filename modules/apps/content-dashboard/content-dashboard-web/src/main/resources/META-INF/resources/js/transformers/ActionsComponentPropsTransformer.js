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

import {render} from '@liferay/frontend-js-react-web';

import SidebarPanel from '../components/SidebarPanel';
import SidebarPanelInfoView from '../components/SidebarPanelInfoView/SidebarPanelInfoView';
import SidebarPanelInfoViewCollapsable from '../components/SidebarPanelInfoView/SidebarPanelInfoViewCollapsable';
import SidebarPanelMetricsView from '../components/SidebarPanelMetricsView';
import {OPEN_PANEL_VALUE} from '../utils/constants';

const ACTIVE_ROW_CSS_CLASS = 'table-active';

const handlePanelStateFromSession = ({
	currentRowId,
	namespace,
	panelState,
	selectedItemFetchURL,
	selectedItemRowId,
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

	showSidebar({
		View: SidebarPanelInfoView,
		fetchURL: selectedItemFetchURL,
		portletNamespace: namespace,
	});

	selectRow(namespace, selectedItemRowId);
};

const handleSessionOnSidebarOpen = ({panelState, rowId, selectedItemRowId}) => {
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

const deselectAllRows = (portletNamespace) => {
	const activeRows = document.querySelectorAll(
		`[data-searchcontainerid="${portletNamespace}content"] tr.${ACTIVE_ROW_CSS_CLASS}`
	);

	activeRows.forEach((row) => row.classList.remove(ACTIVE_ROW_CSS_CLASS));
};

const getRow = (portletNamespace, rowId) =>
	document.querySelector(
		`[data-searchcontainerid="${portletNamespace}content"] [data-rowid="${rowId}"]`
	);

const selectRow = (portletNamespace, rowId) => {
	deselectAllRows(portletNamespace);

	const currentRow = getRow(portletNamespace, rowId);

	if (!currentRow) {
		return;
	}

	currentRow.classList.add(ACTIVE_ROW_CSS_CLASS);
};

const showSidebar = ({View, fetchURL, portletNamespace}) => {
	const id = `${portletNamespace}sidebar`;

	const sidebarPanel = Liferay.component(id);

	if (!sidebarPanel) {
		const container = document.body.appendChild(
			document.createElement('div')
		);

		render(
			SidebarPanel,
			{
				fetchURL,
				onClose: () => {
					Liferay.component(id).close();

					deselectAllRows(portletNamespace);
				},
				ref: (element) => {
					Liferay.component(id, element);
				},
				viewComponent: View,
			},
			container
		);
	}
	else {
		sidebarPanel.open(fetchURL, View);
	}
};

const actions = {
	showInfo({
		fetchURL,
		panelState,
		portletNamespace,
		rowId,
		selectedItemRowId,
	}) {
		selectRow(portletNamespace, rowId);

		handleSessionOnSidebarOpen({
			fetchURL,
			panelState,
			rowId,
			selectedItemRowId,
		});

		showSidebar({
			View: Liferay.FeatureFlags['LPS-161013']
				? SidebarPanelInfoView
				: SidebarPanelInfoViewCollapsable,
			fetchURL,
			portletNamespace,
		});
	},
	showMetrics({fetchURL, portletNamespace, rowId}) {
		selectRow(portletNamespace, rowId);
		showSidebar({
			View: SidebarPanelMetricsView,
			fetchURL,
			portletNamespace,
		});
	},
};

export default function propsTransformer({
	additionalProps,
	items,
	portletNamespace,
	...otherProps
}) {
	const {panelState, selectedItemRowId} = additionalProps;

	handlePanelStateFromSession(additionalProps);

	return {
		...otherProps,
		items: items.map((item) => {
			return {
				...item,
				onClick(event) {
					const action = item.data?.action;

					if (action) {
						event.preventDefault();

						actions[action]({
							fetchURL: item.data.fetchURL,
							panelState,
							portletNamespace,
							rowId: item.data.classPK,
							selectedItemRowId,
						});
					}
				},
			};
		}),
	};
}
