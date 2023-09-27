/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import './LeftSidebar.scss';
import {LeftSidebarItem} from '../types';
export default function LeftSidebarTreeView({
	expandedKeys,
	leftSidebarOtherObjectFoldersItems,
	leftSidebarSelectedObjectFolderItem,
	setExpandedKeys,
	showActions,
}: {
	expandedKeys: Set<React.Key>;
	leftSidebarOtherObjectFoldersItems: LeftSidebarItem[];
	leftSidebarSelectedObjectFolderItem: LeftSidebarItem;
	setExpandedKeys: React.Dispatch<React.SetStateAction<Set<React.Key>>>;
	showActions?: boolean;
}): JSX.Element;
