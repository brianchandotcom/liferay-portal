/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
