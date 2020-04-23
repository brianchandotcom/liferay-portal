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
import React, {useState} from 'react';

import ListCustomObjects from './pages/custom-object/ListCustomObjects.es';
import ListNativeObjects from './pages/native-object/ListNativeObjects.es';

export default () => {
	const [activeTabKeyValue, setActiveTabKeyValue] = useState(0);

	return (
		<>
			<div className="object-tabs">
				<div className="container-fluid container-fluid-max-xl nav-container">
					<ClayTabs modern>
						<ClayTabs.Item
							active={activeTabKeyValue === 0}
							innerProps={{
								'aria-controls': 'tabpanel-1',
							}}
							onClick={() => setActiveTabKeyValue(0)}
						>
							{Liferay.Language.get('custom')}
						</ClayTabs.Item>
						<ClayTabs.Item
							active={activeTabKeyValue === 1}
							innerProps={{
								'aria-controls': 'tabpanel-2',
							}}
							onClick={() => setActiveTabKeyValue(1)}
						>
							{Liferay.Language.get('native')}
						</ClayTabs.Item>
					</ClayTabs>
				</div>
			</div>
			<ClayTabs.Content activeIndex={activeTabKeyValue}>
				<ClayTabs.TabPane aria-labelledby="tab-1">
					<ListCustomObjects />
				</ClayTabs.TabPane>
				<ClayTabs.TabPane aria-labelledby="tab-2">
					<ListNativeObjects />
				</ClayTabs.TabPane>
			</ClayTabs.Content>
		</>
	);
};
