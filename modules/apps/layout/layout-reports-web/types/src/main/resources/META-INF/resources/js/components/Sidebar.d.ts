/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {MouseEventHandler} from 'react';
interface Props {
	children: React.ReactNode;
}
export declare function SidebarBody({children}: Props): JSX.Element;
interface HeaderProps {
	onBackButtonClick?: MouseEventHandler;
	title: string;
}
export declare function SidebarHeader({
	onBackButtonClick,
	title,
}: HeaderProps): JSX.Element;
export {};
