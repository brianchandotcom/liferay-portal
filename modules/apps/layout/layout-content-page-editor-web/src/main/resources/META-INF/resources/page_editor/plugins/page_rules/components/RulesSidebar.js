/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {useSelector} from '../../../app/contexts/StoreContext';
import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';
import RulesEmptyState from './RulesEmptyState';
import RulesList from './RulesList';

export default function RulesSidebar() {
	const rules = useSelector((state) => state.layoutData.pageRules);

	return (
		<>
			<SidebarPanelHeader>
				{Liferay.Language.get('page-rules')}
			</SidebarPanelHeader>

			<div className="p-3">
				{rules.length ? <RulesList /> : <RulesEmptyState />}
			</div>
		</>
	);
}
