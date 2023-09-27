/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import './Panel.scss';
interface IPanelHeaderProps extends React.HTMLAttributes<HTMLElement> {
	contentLeft?: React.ReactNode;
	contentRight?: React.ReactNode;
	disabled?: boolean;
	title: string;
	type: string;
}
export declare function PanelHeader({
	contentLeft,
	contentRight,
	disabled,
	title,
	type,
}: IPanelHeaderProps): JSX.Element;
export {};
