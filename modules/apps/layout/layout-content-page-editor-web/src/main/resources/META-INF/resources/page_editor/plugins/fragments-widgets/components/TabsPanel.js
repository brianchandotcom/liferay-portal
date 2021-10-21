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
import PropTypes from 'prop-types';
import React from 'react';

import {FRAGMENTS_DISPLAY_STYLES} from '../../../app/config/constants/fragmentsDisplayStyles';
import {useId} from '../../../app/utils/useId';
import {COLLECTION_IDS} from './FragmentsSidebar';
import TabCollection from './TabCollection';

const INITIAL_EXPANDED_ITEM_COLLECTIONS = 3;

export default function TabsPanel({
	activeTabId,
	displayStyle,
	setActiveTabId,
	tabs,
}) {
	const tabIdNamespace = useId();

	const getTabId = (tabId) => `${tabIdNamespace}tab${tabId}`;
	const getTabPanelId = (tabId) => `${tabIdNamespace}tabPanel${tabId}`;

	return (
		<>
			<ClayTabs
				className="page-editor__sidebar__fragments-widgets-panel__tabs"
				modern
			>
				{tabs.map((tab, index) => (
					<ClayTabs.Item
						active={tab.id === activeTabId}
						innerProps={{
							'aria-controls': getTabPanelId(index),
							'id': getTabId(index),
						}}
						key={index}
						onClick={() => setActiveTabId(tab.id)}
					>
						{tab.label}
					</ClayTabs.Item>
				))}
			</ClayTabs>

			<ClayTabs.Content
				activeIndex={tabs.findIndex((tab) => tab.id === activeTabId)}
				className="page-editor__sidebar__fragments-widgets-panel__tab-content"
				fade
			>
				{tabs.map((tab, index) => (
					<ClayTabs.TabPane
						aria-labelledby={getTabId(index)}
						id={getTabPanelId(index)}
						key={index}
					>
						<ul className="list-unstyled">
							{tab.collections.map((collection, index) => (
								<TabCollection
									collection={collection}
									displayStyle={
										tab.id === COLLECTION_IDS.widgets
											? FRAGMENTS_DISPLAY_STYLES.LIST
											: displayStyle
									}
									key={index}
									open={
										index <
										INITIAL_EXPANDED_ITEM_COLLECTIONS
									}
								/>
							))}
						</ul>
					</ClayTabs.TabPane>
				))}
			</ClayTabs.Content>
		</>
	);
}

TabsPanel.propTypes = {
	displayStyle: PropTypes.oneOf(Object.values(FRAGMENTS_DISPLAY_STYLES)),
	tabs: PropTypes.arrayOf(
		PropTypes.shape({
			collections: PropTypes.arrayOf(PropTypes.shape({})),
			id: PropTypes.string,
			label: PropTypes.string,
		})
	),
};
