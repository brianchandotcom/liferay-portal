/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
export default function Search({
	children,
	onlySearch,
	showMobile,
	...otherProps
}: IProps): JSX.Element;
interface IProps extends React.FormHTMLAttributes<HTMLFormElement> {
	onlySearch?: boolean;
	showMobile?: boolean;
}
export {};
