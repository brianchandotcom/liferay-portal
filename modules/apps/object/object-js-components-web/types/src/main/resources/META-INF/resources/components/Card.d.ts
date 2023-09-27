/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import './Card.scss';
interface CardProps extends React.HTMLAttributes<HTMLDivElement> {
	customHeader?: JSX.Element;
	disabled?: boolean;
	title?: string;
	tooltip?: ITooltip | null;
	viewMode?:
		| 'inline'
		| 'no-children'
		| 'no-header-border'
		| 'no-margin'
		| 'no-padding';
}
interface ITooltip {
	content: string;
	symbol: string;
}
export declare function Card({
	children,
	className,
	customHeader,
	disabled,
	title,
	tooltip,
	viewMode,
	...otherProps
}: CardProps): JSX.Element;
export {};
