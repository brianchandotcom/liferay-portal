/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';
import RulesEmptyState from './RulesEmptyState';

export default function RulesSidebar() {
	return (
		<>
			<SidebarPanelHeader>
				{Liferay.Language.get('page-rules')}
			</SidebarPanelHeader>

			<div className="d-flex flex-column">
				<RulesEmptyState />
			</div>
		</>
	);
}
