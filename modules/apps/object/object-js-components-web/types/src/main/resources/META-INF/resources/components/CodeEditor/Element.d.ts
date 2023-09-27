/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MouseEventHandler} from 'react';
export declare function Element({
	helpText,
	label,
	onClick,
}: IProps): JSX.Element;
interface IProps {
	helpText: string;
	label: string;
	onClick?: MouseEventHandler;
}
export {};
