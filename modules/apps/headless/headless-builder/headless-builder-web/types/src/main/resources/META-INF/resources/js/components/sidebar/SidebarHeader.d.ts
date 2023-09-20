/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction} from 'react';
interface SidebarHeaderProps {
	navHistory: ObjectDefinition[][];
	searchKeyword: string;
	setNavHistory: Dispatch<SetStateAction<ObjectDefinition[][]>>;
	setSearchKeyword: Dispatch<SetStateAction<string>>;
}
export default function SidebarHeader({
	navHistory,
	searchKeyword,
	setNavHistory,
	setSearchKeyword,
}: SidebarHeaderProps): JSX.Element;
export {};
