/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import './Panel.scss';
interface IPanelSimpleBodyProps extends React.HTMLAttributes<HTMLElement> {
	contentRight?: React.ReactNode;
	title: string;
}
export declare function PanelBody({
	children,
	className,
}: React.HTMLAttributes<HTMLElement>): JSX.Element;
export declare function PanelSimpleBody({
	children,
	contentRight,
	title,
}: IPanelSimpleBodyProps): JSX.Element;
export {};
