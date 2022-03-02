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
import classNames from 'classnames';
import React, {useEffect, useState} from 'react';

import {ITEM_TYPES} from '../../../app/config/constants/itemTypes';
import {
	useActiveItemId,
	useActiveItemType,
} from '../../../app/contexts/ControlsContext';
import {useId} from '../../../app/utils/useId';
import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';
import ContentsSidebar from './contents/components/ContentsSidebar';
import PageStructureSidebar from './page-structure/components/PageStructureSidebar';

const TABS_IDS = {
	pageContent: 1,
	pageElements: 0,
};

const TABS = [
	{
		component: <PageStructureSidebar />,
		label: Liferay.Language.get('page-elements'),
	},
	{
		component: <ContentsSidebar />,
		label: Liferay.Language.get('page-content'),
	},
];

export default function BrowserSidebar({title}) {
	const activeItemId = useActiveItemId();
	const activeItemType = useActiveItemType();
	const [activeTabId, setActiveTabId] = useState(TABS_IDS.pageElements);
	const tabIdNamespace = useId();

	const getTabId = (tabId) => `${tabIdNamespace}tab${tabId}`;
	const getTabPanelId = (tabId) => `${tabIdNamespace}tabPanel${tabId}`;

	useEffect(() => {
		if (activeItemId && activeItemType !== ITEM_TYPES.editable) {
			setActiveTabId(TABS_IDS.pageElements);
		}
	}, [activeItemType, activeItemId]);

	return (
		<div
			className={classNames('page-editor__sidebar__browser', {
				'first-tab--active': activeTabId === 0,
			})}
		>
			<SidebarPanelHeader>{title}</SidebarPanelHeader>

			<ClayTabs className="page-editor__sidebar__browser__tabs" modern>
				{TABS.map((tab, index) => (
					<ClayTabs.Item
						active={activeTabId === index}
						innerProps={{
							'aria-controls': getTabPanelId(index),
							'id': getTabId(index),
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
				className="page-editor__sidebar__browser__tab-content"
				fade
			>
				{TABS.map((tab, index) => (
					<ClayTabs.TabPane
						aria-labelledby={getTabId(index)}
						className="h-100"
						id={getTabPanelId(index)}
						key={index}
					>
						{tab.component}
					</ClayTabs.TabPane>
				))}
			</ClayTabs.Content>
		</div>
	);
}
