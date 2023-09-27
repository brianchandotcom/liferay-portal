/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTabs from '@clayui/tabs';
import React from 'react';

const Tabs = ({currentTab, setCurrentTab, tabs = []}) => {
	return (
		<ClayTabs className="ml-3">
			{tabs.map(({name, tabKey}, index) => (
				<ClayTabs.Item
					active={currentTab === tabKey}
					key={index}
					onClick={() => setCurrentTab(tabKey)}
				>
					{name}
				</ClayTabs.Item>
			))}
		</ClayTabs>
	);
};

export default Tabs;
