/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';
import './RightSidebarRoot.scss';
interface IRightSidebarRoot {
	children: ReactNode;
}
export declare function RightSideBarRoot({
	children,
}: IRightSidebarRoot): JSX.Element;
export {};
