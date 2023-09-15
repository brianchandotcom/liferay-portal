/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction} from 'react';
interface SidebarHeaderProps {
	navHistory: ObjectDefinition[][];
	onBackClick: voidReturn;
	setNavHistory: Dispatch<SetStateAction<ObjectDefinition[][]>>;
	setSearchKeyword: Dispatch<SetStateAction<string>>;
	setViewRelatedObjects: Dispatch<SetStateAction<boolean>>;
	viewRelatedObjects: boolean;
}
export default function SidebarHeader({
	navHistory,
	setNavHistory,
	setSearchKeyword,
	viewRelatedObjects,
}: SidebarHeaderProps): JSX.Element;
export {};
