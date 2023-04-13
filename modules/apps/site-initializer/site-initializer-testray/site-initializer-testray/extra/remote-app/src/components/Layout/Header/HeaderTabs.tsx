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
import {useAtom} from 'jotai';
import {useNavigate} from 'react-router-dom';
import {headerAtom} from '~/atoms';

const HeaderTabs = () => {
	const [tabs] = useAtom(headerAtom.tabs);

	const navigate = useNavigate();

	return (
		<ClayTabs className="tr-header-container__tabs">
			{tabs.map((tab, index) => (
				<ClayTabs.Item
					active={tab.active}
					innerProps={{
						'aria-controls': `tabpanel-${index}`,
					}}
					key={index}
					onClick={() => navigate(tab.path)}
				>
					{tab.title}
				</ClayTabs.Item>
			))}
		</ClayTabs>
	);
};

export default HeaderTabs;
