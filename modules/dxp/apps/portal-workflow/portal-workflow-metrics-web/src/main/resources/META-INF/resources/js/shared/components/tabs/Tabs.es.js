/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayTabs from '@clayui/tabs';
import React from 'react';

const Tabs = ({currentTab, setCurrentTab, tabs = []}) => {
	return (
		<ClayTabs className="ml-3 pl-2">
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
