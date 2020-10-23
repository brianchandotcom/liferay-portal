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

import {render} from 'frontend-js-react-web';

import SidebarPanel from '../SidebarPanel';
import SidebarPanelInfoView from '../components/SidebarPanelInfoView';
import SidebarPanelMetricsView from '../components/SidebarPanelMetricsView';

const deselectAllRows = (portletNamespace) => {
	const activeRows = document.querySelectorAll(
		`[data-searchcontainerid="${portletNamespace}content"] tr.active`
	);

	activeRows.forEach((row) => row.classList.remove('active'));
};

const getRow = (portletNamespace, rowId) =>
	document.querySelector(
		`[data-searchcontainerid="${portletNamespace}content"] [data-rowid="${rowId}"]`
	);

const selectRow = (portletNamespace, rowId) => {
	deselectAllRows(portletNamespace);

	const currentRow = getRow(portletNamespace, rowId);
	currentRow.classList.add('active');
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
	showInfo({fetchURL, portletNamespace, rowId}) {
		selectRow(portletNamespace, rowId);
		showSidebar({
			View: SidebarPanelInfoView,
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
	items,
	portletNamespace,
	...otherProps
}) {
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
							portletNamespace,
							rowId: item.data.classPK,
						});
					}
				},
			};
		}),
	};
}
