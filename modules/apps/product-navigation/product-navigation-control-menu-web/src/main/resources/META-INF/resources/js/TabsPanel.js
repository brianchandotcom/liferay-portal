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
import React, {useContext, useMemo, useState} from 'react';

import {AddPanelContext} from './AddPanel';
import TabsContent from './TabsContent';

let nextId = 0;

const useId = ({portletNamespace}) => {
	return useMemo(() => `${portletNamespace}_useId_${nextId++}`, [
		portletNamespace,
	]);
};

export default function TabsPanel({tabs}) {
	const {portletNamespace} = useContext(AddPanelContext);

	const [activeTabId, setActiveTabId] = useState(0);
	const tabIdNamespace = useId({portletNamespace});

	const getTabId = (tabId) => `${tabIdNamespace}tab${tabId}`;
	const getTabPanelId = (tabId) => `${tabIdNamespace}tabPanel${tabId}`;

	return (
		<>
			<ClayTabs className="sidebar-body__add-panel__tabs" modern>
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
				className="sidebar-body__add-panel__tab-content"
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
}

TabsPanel.propTypes = {
	tabs: PropTypes.arrayOf(
		PropTypes.shape({
			collections: PropTypes.arrayOf(PropTypes.shape({})),
			id: PropTypes.string.isRequired,
			label: PropTypes.string.isRequired,
		})
	).isRequired,
};
