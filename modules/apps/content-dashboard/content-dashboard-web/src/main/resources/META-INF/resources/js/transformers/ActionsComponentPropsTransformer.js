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

export default function propsTransformer({items, namespace, ...otherProps}) {
	const actions = {
		showInfo(fetchURL) {
			showSidebar(fetchURL, SidebarPanelInfoView);
		},
		showMetrics(fetchURL) {
			showSidebar(fetchURL, SidebarPanelMetricsView);
		},
	};

	const showSidebar = (fetchURL, View) => {
		const sidebarPanel = _getSidebarPanel();

		if (!sidebarPanel) {
			const container = document.body.appendChild(
				document.createElement('div')
			);

			render(
				SidebarPanel,
				{
					fetchURL,
					ref: _setSidebarPanel,
					viewComponent: View,
				},
				container
			);
		}
		else {
			sidebarPanel.open(fetchURL, View);
		}
	};

	const _getSidebarPanel = () => {
		return Liferay.component(`${namespace}sidebar`);
	};

	const _setSidebarPanel = (element) => {
		Liferay.component(`${namespace}sidebar`, element);
	};

	return {
		...otherProps,
		items: items.map((item) => {
			return {
				...item,
				onClick(event) {
					const action = item.data?.action;

					if (action) {
						event.preventDefault();

						actions[action](item.data.fetchURL);
					}
				},
			};
		}),
	};
}
