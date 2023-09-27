/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTabs from '@clayui/tabs';
import {useSessionState} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import {AddPanelContext} from './AddPanel';
import TabsContent from './TabsContent';

const TabsPanel = ({tabs}) => {
	const {portletNamespace} = useContext(AddPanelContext);

	const [activeTabId, setActiveTabId] = useSessionState(
		`${portletNamespace}_active-tab-id`,
		0
	);

	const getTabId = (tabId) => `${portletNamespace}_tab_${tabId}`;
	const getTabPanelId = (tabId) => `${portletNamespace}_tabPanel_${tabId}`;

	return (
		<>
			<ClayTabs className="mb-0 pl-3 sidebar-body__add-panel__tabs">
				{tabs.map((tab, index) => (
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
				className="sidebar-body__add-panel__tab-content"
				fade
			>
				{tabs.map((tab, index) => (
					<ClayTabs.TabPane
						aria-labelledby={getTabId(index)}
						className="p-3"
						id={getTabPanelId(index)}
						key={index}
					>
						<TabsContent tab={tab} tabIndex={index} />
					</ClayTabs.TabPane>
				))}
			</ClayTabs.Content>
		</>
	);
};

TabsPanel.propTypes = {
	tabs: PropTypes.arrayOf(
		PropTypes.shape({
			collections: PropTypes.arrayOf(PropTypes.shape({})),
			id: PropTypes.string.isRequired,
			label: PropTypes.string.isRequired,
		})
	).isRequired,
};

export default TabsPanel;
