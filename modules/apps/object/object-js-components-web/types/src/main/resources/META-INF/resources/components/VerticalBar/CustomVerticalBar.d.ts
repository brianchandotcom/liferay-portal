/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';
import './CustomVerticalBar.scss';
interface CustomVerticalBarProps {
	children: ReactNode;
	defaultActive: string;
	panelWidth?: number;
	panelWidthMax?: number;
	panelWidthMin?: number;
	position: 'left' | 'right';
	resize?: boolean;
	triggerSideBarAnimation: boolean;
	verticalBarItems: {
		title: string;
	}[];
}
export declare function CustomVerticalBar({
	children,
	defaultActive,
	panelWidth,
	panelWidthMax,
	panelWidthMin,
	position,
	resize,
	triggerSideBarAnimation,
	verticalBarItems,
}: CustomVerticalBarProps): JSX.Element;
export {};
