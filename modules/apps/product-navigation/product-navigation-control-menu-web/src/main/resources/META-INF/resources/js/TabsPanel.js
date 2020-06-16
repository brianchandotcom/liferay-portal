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

import ClayTabs from '@clayui/tabs';
import React, {useMemo, useState} from 'react';

import {usePortletNamespaceContext} from './AddPanelContext';
import TabsContent from './TabsContent';

import 'product-navigation-control-menu/css/TabsPanel.scss';

let nextId = 0;

const useId = ({portletNamespace}) => {
	return useMemo(() => `${portletNamespace}_useId_${nextId++}`, [
		portletNamespace,
	]);
};

const TabsPanel = ({tabs}) => {
	const portletNamespace = usePortletNamespaceContext();

	const [activeTabId, setActiveTabId] = useState(0);
	const tabIdNamespace = useId({portletNamespace});

	const getTabId = (tabId) => `${tabIdNamespace}tab${tabId}`;
	const getTabPanelId = (tabId) => `${tabIdNamespace}tabPanel${tabId}`;

	return (
		<>
			<ClayTabs className="sidebar-content__add-panel__tabs" modern>
				{tabs.map((tab, index) => (
					<ClayTabs.Item
						active={activeTabId === index}
						innerProps={{
							'aria-controls': getTabPanelId(index),
							id: getTabId(index),
						}}
						key={index}
						onClick={() => setActiveTabId(index)}
					>
						{tab.label}
					</ClayTabs.Item>
				))}
			</ClayTabs>
			<ClayTabs.Content
				activeIndex={activeTabId}
				className="sidebar-content__add-panel__tab-content"
				fade
			>
				{tabs.map((tab, index) => (
					<ClayTabs.TabPane
						aria-labelledby={getTabId(index)}
						id={getTabPanelId(index)}
						key={index}
					>
						<TabsContent tab={tab} />
					</ClayTabs.TabPane>
				))}
			</ClayTabs.Content>
		</>
	);
};

export default TabsPanel;
