/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';
import PageStructureSidebar from './page_structure/components/PageStructureSidebar';
export default function BrowserSidebar({title}) {
	return (
		<div className={classNames('page-editor__sidebar__browser')}>
			<SidebarPanelHeader>{title}</SidebarPanelHeader>

			<PageStructureSidebar />
		</div>
	);
}
